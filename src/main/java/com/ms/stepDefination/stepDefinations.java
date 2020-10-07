package com.ms.stepDefination;

import org.openqa.selenium.WebDriver;

import cucumber.api.java.en.Given;

public class stepDefinations {

	WebDriver driver;

	@Given("^I launch \"(.*)\" url$")
	public void openApplication(String url) {
		driver.get(url);

	}

}
