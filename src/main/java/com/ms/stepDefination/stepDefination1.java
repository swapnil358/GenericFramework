package com.ms.stepDefination;

import com.ms.utilities.Common_Steps;

import cucumber.api.java.en.Given;

public class stepDefination1 {
	

	Common_Steps common = new Common_Steps();
	

	@Given("^I launch \"(.*)\" url$")
	public void i_launch_url(String url) throws Exception {
		Thread.sleep(1000);
		common.OpenMyApp(url);
		Thread.sleep(5000);
	}
	
	@Given("^I enter \"([^\"]*)\" in \"([^\"]*)\" field in \"([^\"]*)\" page$")
	public void i_enter_in_field_in_page(String data, String uiElement, String page) throws Exception {
		common.waitTillPageLoad("page_load");
		common.enterText(data, uiElement, page);
	}

	
	

}
