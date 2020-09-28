package com.ms.pages;

import java.util.Arrays;
import java.util.List;

import org.apache.poi.poifs.crypt.dsig.KeyInfoKeySelector;
import org.codehaus.plexus.util.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.ms.config.XmlRunner;
import com.ms.utilities.MSWebElement;
import com.ms.utilities.MSWebElementList;
import com.ms.utilities.pageObjectRepository;

public class CommonMethods {
	public WebDriver driver = XmlRunner.driverMap.get(Thread.currentThread().getId());
	int objectWait = 2;
	
	public MSWebElement getEle(String locator_key_value, String page) throws Exception {
		String locator_type;
		String locator_value;
		locator_key_value = pageObjectRepository.smartObjectFind(page, locator_key_value);
		By locator = null;
		try {
			locator_type = locator_key_value.split(">")[0];
			locator_value = locator_key_value.split(">")[1];
		} catch (ArrayIndexOutOfBoundsException e) {
			e.printStackTrace();
			return null;

		}
		switch (locator_type.toLowerCase()) {
		case "id":
			locator = By.id(locator_value);
			break;
		case "name":
			locator = By.name(locator_value);
			break;
		case "xpath":
			locator = By.xpath(locator_value);
			break;
		case "css":
			locator = By.cssSelector(locator_value);
			break;
		case "link":
			locator = By.linkText(locator_value);
			break;
		case "partiallink":
			locator = By.partialLinkText(locator_value);
			break;
		case "class":
			locator = By.className(locator_value);
			break;
		case "tag":
			locator = By.tagName(locator_value);
			break;
		}
		MSWebElement msw = new MSWebElement(driver, locator);
		return msw;
	}

	public MSWebElement getEle(By loc) {
		MSWebElement msw = new MSWebElement(driver, loc);
		return msw;
	}

	/*
	 * this method find list of webelement As WebElelemnt object
	 */

	public List<MSWebElement> getEleList(By loc) throws Exception {
		MSWebElementList mswList = new MSWebElementList(driver, loc);
		return mswList;

	}

	public void verifyElementPresentOnPage(MSWebElement ele) throws Exception {
		try {
			if (!ele.isPresent())
				throw new Exception("Element NOT Dsiplayed " + ele.toString());
		} catch (Exception e) {
			throw e;
		}
	}

	public void verifyTextPresetOnPage(String message, String page) throws Exception {
		MSWebElement ele = getEle(By.xpath("//*[contains(text(),'" + message + "')]"));
		if (ele.isDisplayed()) {
			System.out.println("Found: " + message);
		} else {
			throw new Exception("Element with message *" + message + "*found but not Displayed");
		}
	}

	public void verifyElementContainsText(MSWebElement ele, String expectedText) throws Exception {
		if (!ele.isDisplayed()) {
			try {
				ele.scrollToView();
				String actualText = ele.getText();
				if (actualText.contains(expectedText)) {
					System.out.println(expectedText + "* found");
				} else {
					System.out.println("Validation failed\n   Element doesn not contain *" + expectedText);
					throw new Exception("Element does not contain *" + expectedText + "\n Actual value " + actualText);
				}

			} catch (Exception e) {
				throw new Exception("Element " + ele.toString() + "NOT displayed");
			}
		} else {
			String actualText = ele.getText();
			if (actualText.contains(expectedText)) {
				System.out.println(expectedText + "* found");
			} else {
				System.out.println("Validation failed\n Element does not contain *" + expectedText);
				throw new Exception("Element does not contain *" + expectedText + "\n Actual value " + actualText);
			}

		}
	}

	public void typeAndSelectFromDropdown(String text, String uiElement, String page) throws Exception {
		getEle(uiElement, page).click();
		getEle(uiElement, page).sendKeys(text);
		Actions action = new Actions(driver);

		action.sendKeys(Keys.ENTER);
		action.build().perform();
	}

	public void typeAndSelectTextBox(String text, String uiElement, String page) throws Exception {
		WebDriverWait wait = new WebDriverWait(driver, 5);
		wait.until(ExpectedConditions.visibilityOf(getEle(uiElement, page)));

		getEle(uiElement, page).click();
		getEle(uiElement, page).sendKeys(text);
		Actions action = new Actions(driver);
		Thread.sleep(2000);

		action.sendKeys(Keys.DOWN).perform();
		Thread.sleep(2000);

		action.sendKeys(Keys.ENTER).perform();

		action.build().perform();
		Thread.sleep(1000);

	}

	public void SelecTDropdownByVisibleText(String OptionText, String uiElement, String page) throws Exception {
		try {
			Select drpElement = new Select(getEle(uiElement, page));
			drpElement.selectByVisibleText(OptionText);
		} catch (Exception e) {
			try {
				getEle(uiElement, page).click();
				getEle(uiElement, page).sendKeys(OptionText);

				JavascriptExecutor executor = (JavascriptExecutor) driver;
				executor.executeScript("argument[0].click()", OptionText);

				getEle(By.xpath(OptionText)).click();

			} catch (Exception e1) {
				try {
					getEle(By.xpath("//*[contains(text(), '" + OptionText + "')]")).click();
					Thread.sleep(500);
					Actions action = new Actions(driver);
					action.sendKeys(Keys.ESCAPE);
					action.build().perform();

				} catch (Exception e2) {
					OptionText = "//h2[contains(text(), + '" + OptionText + "')]";
					System.out.println("xpath is " + OptionText);
					MSWebElement options = getEle(By.xpath(OptionText));

					if (getEle(By.xpath(OptionText)).isPresent()) {
						options.click();

					} else {
						if (getEle(By.xpath(OptionText)).isPresent()) {
							options.scrollTillEnd();
							options.click();
						} else {
							throw new Exception("Element not contains *" + OptionText);
						}
					}
				}

			}
		}
	}

	public void selectDropdownByIndex(int index, String uiElement, String page) throws Exception {
		Select drpElement = new Select(getEle(uiElement, page));
		drpElement.selectByIndex(index);
	}

	public void selectDropdownByValue(String value, String uiElement, String page) throws Exception {
		Select drpElement = new Select(getEle(uiElement, page));
		drpElement.selectByValue(value);
	}

	public void OpenMyApp(String url) throws Exception {
		driver.get(url);
		Thread.sleep(500);

	}

	public void checkCheckBox(String uiElement, String page) throws Exception {
		if (!getEle(uiElement, page).isSelected()) {
			getEle(uiElement, page).click();
		} else if (getEle(uiElement, page).isSelected()) {
			System.out.println("CheckBox already selected");

		}
	}

	public List<MSWebElement> dynamicLocListCreation(String commonLocator, String variableValue) throws Exception {
		List<MSWebElement> locators = getEleList(By.xpath(StringUtils.replace(commonLocator, "%s", variableValue)));
		return locators;

	}

	public void verifyOptionAvailableInDropdownList(MSWebElement dropdownWebelement, String[] expectedListOption)
			throws Exception {
		dropdownWebelement.click();

		List<WebElement> options = dropdownWebelement.findElements(By.tagName("option"));
		String[] actualListOption = new String[options.size()];
		int i = 0;
		for (WebElement ele : options) {
			actualListOption[i] = ele.getText();
			i++;
		}
		Arrays.sort(expectedListOption);
		Arrays.sort(actualListOption);
		if (Arrays.equals(expectedListOption, actualListOption)) {
			throw new Exception("Expected:" + Arrays.toString(expectedListOption) + "\nActual:"
					+ Arrays.toString(actualListOption));

		}

	}
}
