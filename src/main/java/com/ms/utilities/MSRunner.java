package com.ms.utilities;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.ms.config.XmlRunner;

import cucumber.api.CucumberOptions;
import cucumber.api.Scenario;
import cucumber.api.testng.PickleEventWrapper;
import cucumber.api.testng.TestNGCucumberRunner;
import gherkin.events.PickleEvent;
import gherkin.pickles.Pickle;
import gherkin.pickles.PickleLocation;
import gherkin.pickles.PickleStep;
/*
 * @RunWith(Cucumber.class)
 * 
 * @CucumberOptions(features = "resource/Feature", glue =
 * {com.ms.stepDefination}, plugin = { "json", "json:Report/cucumber.json",
 * "rerun:rerun/failed_scenario.txt"}, tags= {"@NewFramework,@4444"})
 * 
 */
import gherkin.pickles.PickleTag;
import io.github.bonigarcia.wdm.WebDriverManager;


@CucumberOptions(features = "resources/Features/", glue = { "com.ms.stepDefination", "com.ms.utilities" }, plugin = {
		"json", "json:Report/cucumber.json", "rerun:rerun/failed_scenario.txt",
		"html:target/cucumber" }, dryRun = true, tags = { "@NewFramework,@4444" })

@Listeners(CustomTestNGListener.class)
public class MSRunner {
	public static WebDriver driver;
	public static String executionStartTime;
	public static String executionStarDate;
	
	private TestNGCucumberRunner testNGCucumberRunner;
	public static Map<Long, Scenario> scenarioMap = new HashMap<Long, Scenario>();

	/*
	 * 
	 * This method run first test case This open browser of your choice
	 * 
	 */

	@BeforeClass(alwaysRun = true)
	public void beforeClass() throws InterruptedException, IOException {
		
		pageObjectRepository.initializeObjectRepository();

		System.out.println("*******************Started Execution*********************");

		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss:a");
		DateFormat dateFormat1 = new SimpleDateFormat("MM/dd/yyyy");
		Date date = new Date();
		executionStartTime = dateFormat.format(date);
		executionStarDate = dateFormat1.format(date);

		System.out.println("Execution Start time : " + executionStartTime);

		String browser = null;
		try {
			testNGCucumberRunner = new TestNGCucumberRunner(this.getClass());
		} catch (Exception e) {
			System.out.println(e);
			throw new RuntimeException(e);
		}
		
		browser= XmlRunner.browserMap.get(Thread.currentThread().getId());
		//browser = XmlRunner.browserMap.get(Thread.currentThread().getId());
		if (browser.toLowerCase().contains("headlesschrome")) {
			browser = "headlesschrome";
			XmlRunner.browserMap.replace(Thread.currentThread().getId(), "headlesschrome");
		} else if (browser.toLowerCase().contains("ie")) {
			browser = "ie";
			XmlRunner.browserMap.replace(Thread.currentThread().getId(), "ie");
		} else if (browser.toLowerCase().contains("chrome")) {
			browser = "chrome";
			XmlRunner.browserMap.replace(Thread.currentThread().getId(), "chrome");
		}
		System.setProperty("webdriver.chrome.driver", new ConfigJsonReader().chromedriverPath);
		/*
		 * System.setProperty("webdriver.chrome.driver",
		 * "C:\\Users\\n\\Desktop\\chromedriver_win32\\chromedriver.exe");
		 */
		//WebDriverManager.chromedriver().setup();
		ChromeOptions options = new ChromeOptions();
		System.setProperty("webdriver.chrome.driver", new ConfigJsonReader().chromedriverPath);

		switch (browser) {
		case "headlesschrome":
			options.addArguments("--headless");
			options.addArguments("start-maximized");
			options.addArguments("--disable-gpu");
			options.addArguments("--disable-extensions");
			options.addArguments("--no-sandbox");
			driver = new ChromeDriver(options);
			long threadId = Thread.currentThread().getId();
			WebDriver driverName = driver;
			XmlRunner.driverMap.put(threadId, driverName);
			break;
		case "chrome":
			options.setCapability("capability_name", "capability_value");
			/*
			 * options.addArguments("disable-popup-blocking");
			 * options.addArguments("test-type"); options.addArguments("start-maximized");
			 * options.addArguments("disable-infobars");
			 * options.addArguments("--no-sandbox");
			 */
			driver = new ChromeDriver(options);
			
			/*
			 * WebDriverManager.chromedriver().clearPreferences();
			 * WebDriverManager.chromedriver().setup();
			 */
			
			threadId = Thread.currentThread().getId();
			driverName = driver;
			XmlRunner.driverMap.put(threadId, driverName);
			break;
		case "ie":
			InternetExplorerOptions ieoptions = new InternetExplorerOptions();
			ieoptions.setCapability("capability_name", "capability_value");
			driver = new InternetExplorerDriver(ieoptions);
			driver.manage().window().maximize();
			threadId = Thread.currentThread().getId();
			driverName = driver;
			XmlRunner.driverMap.put(threadId, driverName);
			break;
		default:
			throw new RuntimeException(browser + "is not correct browser");
		}
		XmlRunner.actions = new Actions(driver);
		driver.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS);
	}

	int scenarioCounter = -1;
	List<Integer> scenarioCounterList = new ArrayList<>();

	/*
	 * @para- MSpickle event
	 */
	@Test(groups = "cucumber", description = "Runs cucumber scenario", dataProvider = "scenarios")
	public void runScenario(PickleEvent MSpickleEvent) throws Throwable {
		for (PickleTag tag : MSpickleEvent.pickle.getTags()) {
			if (tag.getName().toLowerCase().contains("almid")) {
				try {
					CustomTestNGListener.CurrentALMID = tag.getName().split("_")[1];
				} catch (Exception e) {
					System.out.println("No ALM ID found");
				}
				break;
			}
		}
		System.out.println(MSpickleEvent.pickle.getName());
		scenarioCounter = scenarioCounter + 1;
		scenarioCounterList.add(scenarioCounter);
		XmlRunner.ScenarioAllNumberMap.put(Thread.currentThread().getId(), scenarioCounterList);
		XmlRunner.scenarioNumberMap.put(Thread.currentThread().getId(), scenarioCounter);

		testNGCucumberRunner.runScenario(MSpickleEvent);
		System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
	}

	@DataProvider
	public Object[][] scenarios()
			throws NoSuchFieldException, IllegalArgumentException, IllegalAccessException, IOException {
		if (testNGCucumberRunner == null) {
			return new Object[0][0];
		}
		List<Object> MSScenarios = new ArrayList<>();
		Object[][] originalScenarios = testNGCucumberRunner.provideScenarios();

		for (Object[] scenario : originalScenarios) {
			PickleEventWrapper pickleWrapper = (PickleEventWrapper) scenario[0];
			Pickle pickle = pickleWrapper.getPickleEvent().pickle;
			List<PickleStep> stepList = pickle.getSteps();
			List<PickleTag> tagList = pickle.getTags();
			List<PickleLocation> locationList = pickle.getLocations();
			List<PickleStep> MSstepList;
			List<PickleTag> MStagsList = new ArrayList<>();
			File feature = new File(pickleWrapper.getPickleEvent().uri);
			MStagsList.addAll(tagList);
			MStagsList.add(
					new PickleTag(locationList.get(0), " " + XmlRunner.browserMap.get(Thread.currentThread().getId())));

			try {
				String[] pickleLine = Files.readAllLines(Paths.get(feature.getPath()), StandardCharsets.UTF_8)
						.get(pickle.getLocations().get(0).getLine() - 1).trim().split("\\|");
				List<Map<String, Object>> testData = new ExcelUtils().findRequiredDataForTestCase(pickleLine[1].trim(),
						pickleLine[2].trim());

				List<String> columnNames = new ArrayList<>();
				for (Entry<String, Object> entry : testData.get(0).entrySet()) {
					columnNames.add(entry.getKey());

				}

				for (int dataRowNumber = 0; dataRowNumber < testData.size(); dataRowNumber++) {
					MSstepList = new ArrayList<PickleStep>();
					for (int i = 0; i < stepList.size(); i++) {
						PickleStep step = stepList.get(i);
						String newStepText = step.getText().replace("<$SKey$>",
								">" + pickleLine[1].trim() + ">" + pickleLine[2].trim() + ">" + dataRowNumber + ">");

						for (String colName : columnNames) {
							newStepText = newStepText.replace("<" + colName + ">",
									testData.get(dataRowNumber).get(colName).toString());
						}
						MSstepList.add(i, new PickleStep(newStepText, step.getArgument(), step.getLocations()));

					}
					Pickle MSPickle = new Pickle(pickle.getName(), pickle.getLanguage(), MSstepList, MStagsList,
							locationList);
					PickleEvent MSPickleEvent = new PickleEvent(pickleWrapper.getPickleEvent().uri, MSPickle);
					MSScenarios.add(new Object[] { MSPickleEvent });
				}

			} catch (Exception e) {
				Pickle MSPickle = new Pickle(pickle.getName(), pickle.getLanguage(), stepList, MStagsList,
						locationList);
				PickleEvent MSpickleEvent = new PickleEvent(pickleWrapper.getPickleEvent().uri, MSPickle);
				MSScenarios.add(new Object[] { MSpickleEvent });

			}

		}

		return MSScenarios.toArray(new Object[][] {});
	}

	@AfterClass
	public void afterClass() {
		XmlRunner.driverMap.get(Thread.currentThread().getId()).close();
		XmlRunner.driverMap.get(Thread.currentThread().getId()).quit();
		testNGCucumberRunner.finish();
		new Report_Helper().onFinish();

	}

}
