package com.ms.stepDefination;

import com.ms.utilities.Common_Steps;
import com.ms.utilities.MSWebElement;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class stepDefinations {

	Common_Steps common = new Common_Steps();

	
	@Given("^I launch \"(.*)\" url$")
	public void i_launch_url(String url) throws Exception {
	    // Write code here that turns the phrase above into concrete actions
		common.OpenMyApp(url);
	}

	@When("^I enterr \"(.*)\" in \"(.*)\" field in \"(.*)\" page$")
	public void enterTextlnput(String data, String uiElement, String page) throws Exception {
		common.waitTillPageLoad("page_load");
		common.enterText(data, uiElement, page);
	}

	@And("^I enter \"(.*)\" in \"(.*)\" field and select \"(.*)\" page$")
	public void typeAndSelectText(String data, String uiElement, String page) throws Exception {
		common.waitTillPageLoad("page_load");
		common.typeAndSelectTextBox(data, uiElement, page);
	}

	@And("^I select date \"(.*)\" successfully")
	public void selectDate(String date) throws Exception {
		common.waitTillPageLoad("page_load");
		common.SetDate(date);
	}

	@And("^I click on reservation and go to reservation details page$")
	public void sendReservationld() throws InterruptedException {
		common.waitTillPageLoad("page_load"); // common.getReservationIdFromLastActivityBar(); ip.grabReservationId();
	}

	@And("^I verify reservation details page$")
	public void verifyReservation() throws Exception {
		common.waitTillPageLoad("page_load");

	}

	@And("^I click on \"([*\"]*)\" pop up in \"([A\"]*)\" pages")
	public void clickOnPopUpBtn(String uiElement, String page) throws Exception {
		common.waitTillPageLoad("page_load");
		common.clickOnAlertPopUpBtn(uiElement, page);
	}

	

	@Then("^I clear \"(.*)\" input in \"(.*)\" pages")
	public void clearinputField(String uiElement, String page) throws Exception {
		common.waitTillPageLoad("page_load");
		common.clearInput(uiElement, page);
	}

	@And("^I click on \"(.*)\" in \"(.*)\" page$")
	public void clickStep(String uiElement, String page) throws Exception {
		common.waitTillPageLoad("page_load");
		common.clickElement(uiElement, page);
	}

	@And("^I validate status \"(.*)\" Text in \"(.*)\" page$")
	public void verifyElementContainsTextIgnoreCase(String expectedText, String page) throws Exception {
		common.waitTillPageLoad("page_load");
		common.verifyTextPresetOnPage(expectedText, page);
	}

	@And("^I verify \"(.*)\" is displayed in \"(.*)\" page$")
	public void verifyElementDisplay(String expectedText, String page) throws Exception {
		common.waitTillPageLoad("page_load");
		common.verifyElementDisplayOnPage(expectedText, page);
	}

	@When("^I mouse over on \"(.*)\" field in \"(.*)\" page$")
	public void mouseHover(String uiElement, String page) throws Exception {
		common.waitTillPageLoad("page_load");
		common.mouseHover(uiElement, page);
	}

	@When("^I validate \"(.*)\" is \"(.*)\" in \"(.*)\" page$")
	public void validateStateOfObject(String uiElement, String operation, String page) throws Exception {
		common.waitTillPageLoad("page_load");
		if (!common.validateStateOfObject(uiElement, operation, page)) {
			throw new Exception(uiElement + " element NOT is not " + operation);
		}
	}

	@When("^I select the option \"(.*)\" in \"(.*)\" input in \"(.*)\" page$")
	public void selectUsingText(String text, String uiElement, String page) throws Exception {
		common.waitTillPageLoad("page_load");
		common.selectDropdownByVisibleText(text, uiElement, page);
	}

	@When("^I verify \"(.*)\" is opened in new tab$")
	public void openNewTab(String url) throws Exception {
		common.waitTillPageLoad("page_load");
		common.openNewTab(url);
	}

	@When("^I close the current tab$")
	public void closeCurrentTab() throws Exception {
		common.waitTillPageLoad("page_load");
		common.closeCurrentTab();
	}

	@When("^I select \"([^\"]*)\" radio button in \"(.*)\" page$")
	public void selectUsingText(String uiElement, String page) throws Exception {
		common.waitTillPageLoad("page_load");
		common.selectRadioButton(uiElement, page);
	}

	@And("^I verify option \"(.*)\" available in \"(.*)\" page$")
	public void VerifyDropdownOption(MSWebElement dropdownWebelement, String[] expectedListOption) throws Throwable {
		common.waitTillPageLoad("page_load");
		common.verifyOptionAvailableInDropdownList(dropdownWebelement, expectedListOption);
	}

	@And("^I select the lead option \"(.*)\" in \"(.*)\" page$")
	public void SelectLead(String optionText, String page) throws Throwable {
		common.waitTillPageLoad("page_load");
		// scm.SelectLead(optionText, page);
	}

	@And("^I check the text \"(.*)\" contain sentence \"(.*)\" in \"(.*)\" page$")
	public void checkText(String text, String uiElement, String page) throws Throwable {
		common.waitTillPageLoad("page_load");
		common.verifyIfSentenceContainTex(text, uiElement, page);
	}

	@And("^I select the o.tion by index \"(.*)\" of \"(.*)\" in \"(.*)\" page$")
	public void SelectDropdownOptionByIndex(int index, String uiElement, String page) throws Throwable {
		common.waitTillPageLoad("page_load");
		common.selectDropdownByIndex(index, uiElement, page);
	}

	@Given("^I click on \"(.*)\" and select \"(.*)\" text in \"(.*)\" page$")
	public void clickAndSelect(String uiElement, String sValue, String page) throws Exception {
		common.waitTillPageLoad("page_load");
		common.clickAndSelect(uiElement, sValue, page);
	}

	@And("^I click on \"(.*)\" if present in \"(.*)\" page$")
	public void clickStepIfPresent(String uiElement, String page) throws Exception {
		common.waitTillPageLoad("page_load");
		common.clickIfObjectExist(uiElement, page);
	}

	@When("^I mouse hover and click on \"([^\"]*)\" field on \"([^\"]*)\" page$")
	public void mouseHoverAndClick(String uiElement, String page) throws Exception {
		common.waitTillPageLoad("page_load");
		common.mouseHoverAndClick(uiElement, page);

	}
	
	
	
	 @When("AI verify \"([^\"]*)\" not present on \"([^\"]*)\" page$") 
	 public void verifyElementNotPresent(String uiElement,String page)throws Exception { 
		 common.waitTillPageLoad("page_load"); 
		 common.verifyElementNotPresent(uiElement, page); 
		 } 
	 
	 @When("AI scroll \"([^\"]*)\" till on \"([^\"]*)\" page$") 
	 public void scrollTillElementVisible(String uiElement,String page)throws Exception { 
		 common.waitTillPageLoad("page_lcad"); 
		 common.scrollTillElementVisible(uiElement, page);  
	 }

	
}
