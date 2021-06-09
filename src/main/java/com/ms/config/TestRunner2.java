package com.ms.config;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.ms.utilities.CustomTestNGListener;

import cucumber.api.CucumberOptions;
import cucumber.api.testng.CucumberFeatureWrapper;
import cucumber.api.testng.TestNGCucumberRunner;
import gherkin.events.PickleEvent;

@CucumberOptions(features = "resources\\Features\\Googlecheck.feature", glue = {
		"com.ms.stepDefination" }, plugin = { "pretty", "html:target/cucumber-reports/cucumber-pretty",
				"json:target/cucumber-reports/CucumberTestReport.json", "rerun:target/cucumber-reports/rerun.txt" })

//@Listeners(CustomTestNGListener.class)
public class TestRunner2 {

	private TestNGCucumberRunner testNGCucumberRunner;

	@BeforeClass(alwaysRun = true)
	public void setUpClass() {
		testNGCucumberRunner = new TestNGCucumberRunner(this.getClass());
		System.out.println("=======BeforeClass");
	}

	@Test(groups = "cucumber", description = "Runs cucmber Features", dataProvider = "features")
	public void runScenario(PickleEvent MSevent) throws Throwable {
		System.out.println(MSevent.pickle.getName());
		testNGCucumberRunner.runScenario(MSevent);
	}

	@DataProvider
	public Object[][] features() {
		return testNGCucumberRunner.provideFeatures();
	}

	@AfterClass(alwaysRun = true)
	public void testDownClass() {
		testNGCucumberRunner.finish();
		System.out.println("=======AfterClass");
	}

}
