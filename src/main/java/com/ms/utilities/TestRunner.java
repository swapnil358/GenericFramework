package com.ms.utilities;

import org.junit.runner.RunWith;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import cucumber.api.CucumberOptions;

public class TestRunner {

	@CucumberOptions(

			features = { "resources/Features1" }, glue = { "com.ms.stepDefination" }, monochrome = true,

			plugin = { "pretty", "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:",
					"timeline:test-output-thread/", "json:Report/cucumber-reports/cucumber.json",
					"html:target/cucumber-html-report", "html:target/cucumber-reports/cucumber.xml"

			}

	)

	public class MyTestRunner {

		@BeforeClass
		public void beforeClass() {
			System.out.println("I am in before class");

		}

		@AfterClass
		public void afterClass() throws Exception {
			new Report_Helper().onFinish();
			GenerateEmailReportUpdated.generateReport();
		}
	}
}
