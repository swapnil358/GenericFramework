package com.ms.config;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.poi.ss.usermodel.Sheet;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.ITestNGListener;
import org.testng.TestListenerAdapter;
import org.testng.TestNG;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import com.google.gson.JsonElement;
import com.ms.utilities.ConfigJsonReader;
import com.ms.utilities.ExcelUtils;
import com.ms.utilities.FailedRerunner;
import com.ms.utilities.MSRunner;
import com.ms.utilities.Report_Helper;

import cucumber.api.CucumberOptions;

public class XmlRunner {

	public static Actions actions;
	ExcelUtils eu = new ExcelUtils();
	public static Map<String, Sheet> sheetsInMap;
	private static final String FILE_NAME = "resources//testData.xlsx";
	public static Map<Long, String> browserMap = new HashMap<Long, String>();
	public static Map<Long, WebDriver> driverMap = new HashMap<Long, WebDriver>();
	public static Map<Long, Integer> scenarioNumberMap = new HashMap<Long, Integer>();
	public static Map<Long, List<Integer>> scenarioAllNumberMap = new HashMap<Long, List<Integer>>();
	private static Map<Long, String[]> tagsMap = new HashMap<Long, String[]>();
	
	/*
	@Swapnil
	
	*/
		
	public static void main(String[] arg) throws Exception {
		new XmlRunner().beforeSuite();
		new ConfigJsonReader();
		Set<Entry<String, JsonElement>> brConfig = ConfigJsonReader.brConfig;
		int runCount = brConfig.size();
		int i = 0;
		Thread[] threads = new Thread[runCount];
		for (Entry<String, JsonElement> entry : brConfig) {
			i++;
			final CucumberOptions classAnnotation = MSRunner.class.getAnnotation(CucumberOptions.class);
			String[] currentTags = new String[] {
					entry.getValue().getAsJsonObject().entrySet().iterator().next().getValue().getAsString() };
			changeAnnotationValue(classAnnotation, "tags", currentTags);
			changeAnnotationValue(classAnnotation, "plugin", 
					new String[] { "json",
					// "json:Report/cucumber" + i + ".json",
					// "rerun:rerun/failed_scenarios" + i + ".txt" });
					"json:Report/cucumber-reports/cucumber" + i + ".json", "rerun:rerun/failed_scenarios" + i + ".txt" });
			threads[i - 1] = new Thread(new XmlRunner().new MyRunnable());
			threads[i - 1].start();
			browserMap.put(threads[i - 1].getId(), entry.getKey());
			tagsMap.put(threads[i - 1].getId(), currentTags);
			Thread.sleep(5000);
		}
		for (Thread thread : threads) {
			synchronized (thread) {
				thread.wait();
				System.out.println("Thread " + thread.getId() + " is closed");
			}
		}
		System.out.println("All threads are completed");
	}

	public void failedRerun(String browser, Long oldThread) {
		final CucumberOptions classAnnotation = FailedRerunner.class.getAnnotation(CucumberOptions.class);
		changeAnnotationValue(classAnnotation, "tags", tagsMap.get(oldThread));
		changeAnnotationValue(classAnnotation, "plugin", 
				new String[] { "json",
				// "json:Report/cucumber" + i + ".json",
				// "rerun:rerun/failed_scenarios" + i + ".txt" });
				"json:Report/cucumber-reports/cucumber" + oldThread + 1 + ".json",
				"rerun:rerun/failed_scenarios" + oldThread + 1 + ".txt" });
		Thread thread_Failed = new Thread(new XmlRunner().new failedRunnable());
		thread_Failed.start();
		browserMap.put(thread_Failed.getId(), browser);
		tagsMap.put(thread_Failed.getId(), tagsMap.get(oldThread));
		scenarioAllNumberMap.put(thread_Failed.getId(), scenarioAllNumberMap.get(oldThread));
		scenarioNumberMap.put(thread_Failed.getId(), scenarioNumberMap.get(oldThread));
		// M55cenariosMap.put(thread_Failed.getId(),
		// M55cenariosMap.get(oldThread));
		try {
			Thread.sleep(5000);
		} catch (Exception e) {

		}
	}

	private class MyRunnable implements Runnable {
		public void run() {
			
			TestListenerAdapter tla = new TestListenerAdapter();
			TestNG testng = new TestNG();
			testng.setTestClasses(new Class[] { MSRunner.class });
			testng.addListener((ITestNGListener) tla);
			testng.run();
		}
	}

	private class failedRunnable implements Runnable {
		public void run() {
			
			
			TestListenerAdapter tla = new TestListenerAdapter();
			TestNG testng = new TestNG();
			testng.setTestClasses(new Class[] { FailedRerunner.class });
			testng.addListener((ITestNGListener) tla);
			testng.run();
		}
	}

	private void beforeSuite() throws InterruptedException, IOException {
		try {
			for (File fileEntry : new File("Report").listFiles()) {
				if (fileEntry.getName().endsWith(".json")) {
					fileEntry.delete();
				}
			}
		} catch (Exception e) {
			System.out.println("Error in beforesuite" + e);
		}

		try {
			sheetsInMap = eu.sheetsInMap(FILE_NAME);
			Runtime.getRuntime().exec("taskkill /F /IM IEDriverServer.exe");
			Thread.sleep(1000);
			Runtime.getRuntime().exec("taskkill /F /IM chromedriver.exe");
			Thread.sleep(1000);
			/*
			 *  Runtime.getRuntime().mgctaAjsill IF /IM chrome.exe"); 
			 *  Thread.sleep(1000);
			 */
		} catch (Exception e) {
			System.out.println(e);

		}
	}

	/*@AfterSuite
	public void cleanUp() throws IOException {
	//	new Report_Helper().onFinish();
		System.out.println("");
	}*/

	private static Object changeAnnotationValue(Annotation annotation, String key, Object newValue) {
		Object handler = Proxy.getInvocationHandler(annotation);
		Field f;
		try {
			f = handler.getClass().getDeclaredField("memberValues");
		} catch (NoSuchFieldException | SecurityException e) {
			throw new IllegalStateException(e);
		}
		f.setAccessible(true);
		Map<String, Object> memberValues;
		try {
			memberValues = (Map<String, Object>) f.get(handler);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			throw new IllegalStateException(e);
		}
		Object oldValue = memberValues.get(key);
		if (oldValue == null || oldValue.getClass() != newValue.getClass()) {
			throw new IllegalArgumentException();
		}
		memberValues.put(key, newValue);
		return oldValue;
	}

}