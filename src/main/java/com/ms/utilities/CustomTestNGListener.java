package com.ms.utilities;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.ms.config.XmlRunner;

public class CustomTestNGListener implements ITestListener {

	Report_Helper rh = new Report_Helper();
	public static String CurrentALMID;
	public Map<Long, WebDriver> driverMap = new HashMap<>();
	MSRunner MSRunner = new MSRunner();

	@Override
	public void onTestStart(ITestResult result) {
		System.out.println("******************Test case started****************	");

	}

	@Override
	public void onTestSuccess(ITestResult result) {
		List<Integer> list = XmlRunner.scenarioAllNumberMap.get(Thread.currentThread().getId());
		list.remove((Integer)XmlRunner.scenarioNumberMap.get(Thread.currentThread().getId()));
		/*
		 * if(CurrentALMID != null) {
		 * 
		 * }
		 */

	}

	@Override
	public void onTestFailure(ITestResult result) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTestSkipped(ITestResult result) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStart(ITestContext context) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onFinish(ITestContext context) {
		List<Integer> failedScenList = XmlRunner.scenarioAllNumberMap.get(Thread.currentThread().getId());
		if (ConfigJsonReader.Rerun_Flag && ConfigJsonReader.Rerun_NumberOfTimes > 0 && failedScenList != null
				&& failedScenList.size() > 0) {
			ConfigJsonReader.Rerun_NumberOfTimes--;
			new XmlRunner().failedRerun(XmlRunner.browserMap.get(Thread.currentThread().getId()),
					Thread.currentThread().getId());

		}
		System.out.println("*****************Clean up after execution******************");
	}

}
