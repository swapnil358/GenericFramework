package com.ms.utilities;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;

import com.ms.config.XmlRunner;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;

public class Hooks {

	@Before
	public void scenarioSetup(Scenario currentScenario) throws Throwable {
		currentScenario.write("Executing scenario:" + currentScenario.getName());
		// Add current running scenario to the map
		MSRunner.scenarioMap.put(Thread.currentThread().getId(), currentScenario);
	}

	@After
	public void Screenshot(Scenario currentScenario) throws Throwable {
		// Remove executed scenario from the map

		MSRunner.scenarioMap.remove(Thread.currentThread().getId());

		if (currentScenario.isFailed()) {

			WebDriver driver = XmlRunner.driverMap.get(Thread.currentThread().getId());
			try {
				currentScenario.write("Current Page URL is " + driver.getCurrentUrl());
				byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
				currentScenario.embed(screenshot, "image/png");
			} catch (WebDriverException somePlatformsDontSupportScreenshots) {
				// Reporter.addStepLog(somePlatformsDontSupportScreenshots.getMessage());
			} catch (Exception ex) {
				currentScenario.write(ex.getMessage());
			}
			throw new Exception("This scenario is failed");
		}
	}

}
