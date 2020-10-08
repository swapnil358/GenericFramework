package com.ms.stepDefination;

import org.openqa.selenium.WebDriver;

import cucumber.api.java.en.Given;

public class stepDefinations {

	WebDriver driver;

	
	@Given("^I launch \"([^\"]*)\" url$")
	public void i_launch_url(String url) throws Exception {
	    // Write code here that turns the phrase above into concrete actions
		 driver.get(url);
		    System.out.println("launchhhhhhhhhhhh");
	}

}
