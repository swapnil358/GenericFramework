package com.ms.stepDefination;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;

import com.ms.utilities.Common_Steps;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class stepDefination1 {
	

	Common_Steps common = new Common_Steps();
	

	WebDriver driver;
	@Given("I want to launch the facebook in firefox browser")
	public void i_want_to_launch_the_facebook_in_firefox_browser() {
		System.setProperty("webdriver.chrome.driver", "D:\\\\Desktop_Data\\\\chromedriver_win32\\\\chromedriver.exe");
		new ChromeDriver();
		driver.get("http://automationpractice.com/");
		driver.findElement(By.xpath("//a[normalize-space()='Sign in']")).click();
		}

	@When("I want to authenticate with valid credentails")
	public void i_want_to_authenticate_with_valid_credentails() {
		driver.findElement(By.id("email")).sendKeys("swapniltestautomation@gmail.com");
		driver.findElement(By.id("passwd")).sendKeys("Qwerty@123");
		driver.findElement(By.id("SubmitLogin")).click();
		
	}

	@Then("I want to verify whether user navigated to home page or not")
	public void i_want_to_verify_whether_user_navigated_to_home_page_or_not() {
	    Assert.assertEquals(driver.getTitle(), "My account - My Store");
	    driver.quit();
	}
	
	

}
