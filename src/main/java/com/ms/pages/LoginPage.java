package com.ms.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage {

	private WebDriver driver;

	// 1. By locators: OR (Object Repositories)
	
	private By emailId = By.id("email");
	private By password = By.id("passwd");
	private By signInButton = By.id("SubmitLogin");
	private By forgotpwdLink = By.linkText("Forgot your password?");

	// 2. Create constructor of the page
	
	public LoginPage(WebDriver driver) {
		this.driver = driver;

	}

	// 3. page actions: feature(behavior) of the page form of method:
	
	public String getLoginPageTitle() {
		return driver.getTitle();
	}

	public boolean forgotPasswordLinkExist() {
		return driver.findElement(forgotpwdLink).isDisplayed();

	}

	public void enterUserName(String username) {
		driver.findElement(emailId).sendKeys(username);
	}

	public void enterPassword(String pwd) {
		driver.findElement(password).sendKeys(pwd);
	}

	public void clickOnLogin() {
		driver.findElement(signInButton).click();
	}

	public AccountPage doLogin(String us, String pwd) {
		
		System.out.println("login with "+ us + " and " + pwd );
		driver.findElement(emailId).sendKeys(us);
		driver.findElement(password).sendKeys(pwd);
		driver.findElement(signInButton).click();
		
		return new AccountPage(driver);
		
	}
	
}
