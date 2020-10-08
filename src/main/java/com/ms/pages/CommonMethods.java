package com.ms.pages;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

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
import org.testng.Assert;

import com.ms.config.XmlRunner;
import com.ms.utilities.MSRunner;
import com.ms.utilities.MSWebElement;
import com.ms.utilities.MSWebElementList;
import com.ms.utilities.pageObjectRepository;

import cucumber.api.Scenario;

public class CommonMethods {
	public WebDriver driver = XmlRunner.driverMap.get(Thread.currentThread().getId());
	int objectWait = 2;
	String parentWindHandler = null;

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

	public void verifyCorrectListAvailable_Random(List<MSWebElement> listWebelement, String[] expectedListOption)
			throws Exception {
		String[] actualListOption = new String[listWebelement.size()];
		int i = 0;
		for (MSWebElement ele : listWebelement) {
			actualListOption[i] = ele.getText();
			i++;
		}
		Arrays.sort(expectedListOption);
		Arrays.sort(actualListOption);
		if (!Arrays.equals(expectedListOption, actualListOption)) {
			throw new Exception("Expected:" + Arrays.toString(expectedListOption) + "\nActual:"
					+ Arrays.toString(actualListOption));
		}
	}

	/**
	 * * @author warp. .9j1 This method returns the instance of current running
	 * scenario for the current thread * @return Scenario current running scenario
	 */

	public Scenario getCurrentScenario() {
		return MSRunner.scenarioMap.get(Thread.currentThread().getId());
	}

	//////////////////////////////////////// eriyaaka////////////////////////////////////////////
	public void openMyApp(String url) throws Exception {
		driver.get(url);
		Thread.sleep(700);
	}

	public void enterText(String data, String uiElement, String page) throws Exception {
		getEle(uiElement, page).click();
		getEle(uiElement, page).sendKeys(data);
		// Thread.sleep(4000);
	}

	public void verifyElementContainsText(String expectedText, String uiElement, String page) throws Exception {
		if (!getEle(uiElement, page).isDisplayed()) {
			try {

				String actualText = getEle(uiElement, page).getText();
				if (actualText.contains(expectedText)) {
					System.out.println(expectedText + "' found");
				} else {
					System.out.println("Validation failed\n		 Element does not contain*" + expectedText);
					throw new Exception("Element does not contain *" + expectedText + "\nActual value " + actualText);
				}
			} catch (Exception e) {
				throw new Exception("Element " + uiElement + " NOT displayed");
			}

		} else {
			String actualText = getEle(uiElement, page).getText();
			if (actualText.contains(expectedText)) {
				System.out.println(expectedText + "* found");
			} else {
				System.out.println("Validation failed\n Element does not contain *" + expectedText);
				throw new Exception("Element does not contain *" + expectedText + "\nActual value " + actualText);
			}
		}
	}

	public void clickAndSelect(String uiElement, String optionText, String page) throws Exception {

		getEle(By.xpath("//*[contains(text(),'" + optionText + "')]/../../../../button")).click();
		Thread.sleep(100);
		optionText = "//*[contains(text(),'" + optionText + "')]";
		MSWebElement options = getEle(By.xpath(optionText));
		if (getEle(By.xpath(optionText)).isPresent()) {
			getEle(By.xpath(optionText)).click();
		} else {
			if (getEle(By.xpath(optionText)).isPresent()) {
				options.scrollToView();
				options.click();
			} else
				throw new Exception("Element does not contain *" + optionText);
		}

	}

	public void waitTillPageLoad(String uiElement) {
		try {
			String eleString = "//img[starts-with(@src,'data:image/gif;base64,R01GOD1hIAAgAPMAAP')]";
			Thread.sleep(400);
			WebDriverWait wait = new WebDriverWait(driver, 6);
			// if(!wait.until(ExpectedConditions.invisibility0f(getEle(uiElement,
			// "common"))))
			// {
			// throw new Exception("Page load taking time ");
			// }

			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(eleString)));

			// Dimension size = driver.findElement(By.xpath(eleString)).
			// if( driver.findElements(By.xpath(eleString)).size() > e){
			// WebDriverWait wait = new WebDriverWait(driver, 2);
			// if
			// wait.until(ExpectedConditions.invisibility0fElementLocated(By.xpath(eleString))))
			// throw new Exception("Page load taking time ");
			// // else // { // //
		} catch (Exception e) {
		}
	}

	public void clickOnWindowPopUpBtn(String uiElement, String page) throws Exception {
		Set<String> handle = driver.getWindowHandles();
		Iterator<String> it = handle.iterator();
		String parentWin = it.next();
		System.out.println("ParentWin handle : " + parentWin);
		String childWin = it.next();
		System.out.println("ChildWin handle : " + childWin);
		driver.switchTo().window(childWin);
		getEle(uiElement, page).click();
	}

	public void clickOnAlertPopUpBtn(String uiElementl, String page) throws Exception {
		String parentWindowHandler = driver.getWindowHandle();
		String subWindowHandler = null;
		Set<String> handles = driver.getWindowHandles();
		Iterator<String> iterator = handles.iterator();
		while (iterator.hasNext()) {
			subWindowHandler = iterator.next();
		}
		driver.switchTo().window(subWindowHandler);

		getEle(uiElementl, page).click();
		driver.switchTo().window(parentWindowHandler);
		Thread.sleep(1000);
	}

	public void ValidateTitle(String title, String page) throws Exception {
		Thread.sleep(1000);

		/*
		 * try { Set<String> handle = driver.getWindowHandles(); Iterator<String> it =
		 * handle.iterator(); String parentWin = it.next();
		 * System.out.println("ParentWin handle : " + parentWin); String childWin =
		 * it.next(); System.out.println("ChildWin handle : " + childWin);
		 * driver.switchTo().window(childWin); String pageTitle = driver.getTitle();
		 * Assert.assertEquals(title, pageTitle, "Title does not match"); }
		 * catch(Exception e) { String pagetitle = driver.getTitle();
		 * Assert.assertEquals(title, pagetitle,"title does not match")
		 */

		String parentWindowHandler = driver.getWindowHandle();
		String subWindowHandler = null;
		Set<String> handles = driver.getWindowHandles();
		Iterator<String> iterator = handles.iterator();
		while (iterator.hasNext()) {
			subWindowHandler = iterator.next();
			System.out.println("subWindowHandler: " + subWindowHandler);
			System.out.println("parentWindowHandler: " + parentWindowHandler);
		}
		driver.switchTo().window(subWindowHandler);
		String pageTitle = driver.getTitle();
		Assert.assertEquals(pageTitle, title, "Title does not match");
	}

	public void clearInput(String uiElement, String page) throws Exception {
		getEle(uiElement, page).click();
		getEle(uiElement, page).clear();
	}

	public void clickIfObjectExist(String uiElement, String page) throws Exception {
		if (getEle(uiElement, page).isPresent())
			;
		getEle(uiElement, page).click();
	}

	public void mouseHover(String uiElement, String page) throws Exception {
		Actions action = new Actions(driver);
		/*
		 * action.moveToElement( new WebDriverWait(driver, 2)
		 * .until(ExpectedConditions.elementToBeClickable(getEle(uiElement, page))))
		 * .build().perform();
		 */

		action.moveByOffset(5, 5).perform(); // moves cursor to point (5,5)
		action.moveByOffset(10, 15).perform();
	}

	public void mouseHoverAndClick(String uiElement, String page) throws Exception {
		String[] arrStr = null;
		if (uiElement.contains("->"))
			arrStr = uiElement.split("->");
		else
			arrStr[0] = uiElement;
		Actions action = new Actions(driver);
		for (int i = 0; i < arrStr.length; i++) {
			action.moveToElement(new WebDriverWait(driver, objectWait)
					.until(ExpectedConditions.elementToBeClickable(getEle(uiElement, page)))).build().perform();
			if (i == arrStr.length - 1)
				getEle(uiElement, page).click();
		}
	}

	public void selectRadioButton(String uiElement, String page) throws Exception {
		/*
		 * * if (getEle(uiElement, page).isSelected()) getEle(uiElement, *
		 * page).click();
		 */
		if (!getEle(uiElement, page).isSelected()) {
			getEle(uiElement, page).click();
		}
	}

	public void verifyElementNotPresent(String uiElement, String page) throws Exception {
		if (getEle(uiElement, page).isPresent())
			throw new Exception(uiElement + " element present on " + page + " page");
	}

	public void scrollTillElementVisible(String uiElement, String page) throws Exception {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].scrollIntoView();", getEle(uiElement, page));
	}

	public void SelectFirstRecordFromTable(String uiElement, String page) throws Throwable {
		WebDriverWait wait = new WebDriverWait(driver, 15);
		wait.until(ExpectedConditions.elementToBeClickable(getEle(uiElement, page)));
		if (getEle(uiElement, page).isDisplayed()) {
			JavascriptExecutor jse = (JavascriptExecutor) driver;
			jse.executeScript("arguments[0].click();", getEle(uiElement, page));
			System.out.println("Successfully selected 1st record from Table");
		} else
			Assert.fail("Failed to select 1st record from table");
	}

	public void NavigatetoTab(String uiElement, String page) throws Exception {
		try {
			if (getEle(uiElement, page).isDisplayed()) {
				JavascriptExecutor jse = (JavascriptExecutor) driver;
				jse.executeScript("arguments[0].click();", getEle(uiElement, page));
				System.out.println("Successfully selected 1st record from Table");
			}
		} catch (Exception e) {
			// T000 Auto-generated catch block
			e.printStackTrace();
			Assert.fail("Failed to click on App Launcher : " + e);
		}
	}

	public void verifyElementDisplayOnPage(String uiElement, String page) throws Exception {
		try {
			if (!getEle(uiElement, page).isDisplayed())
				throw new Exception("Element NOT displayed" + getEle(uiElement, page));
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * @author Swapnil *
	 * @param uiElement • - object operation -
	 *                  enabled/disable/Select/Unchecked/present/display/not
	 *                  display/not present page - Page name * @throws Exception
	 */

	public boolean validateStateOfObject(String uiElement, String operation, String page) throws Exception {
		switch (operation.toLowerCase()) {
		case "enabled":
			if (getEle(uiElement, page).isEnabled())
				return true;
			break;
		case "selected":
			if (getEle(uiElement, page).isSelected())
				return true;
			break;
		case "disabled":
			if (!getEle(uiElement, page).isEnabled())
				return true;
			break;
		case "present":
			if (getEle(uiElement, page).isPresent())
				return true;
			break;
		case "display":
			if (getEle(uiElement, page).isDisplayed())
				return true;
			break;
		case "not display":
			if (!getEle(uiElement, page).isDisplayed())
				return true;
			break;
		case "not present":
			if (!getEle(uiElement, page).isPresent())
				return true;
			break;
		default:
			break;
		}
		return false;
	}

	/**
	 * * @author SKapail *
	 * 
	 * @param url * - 4..r1 *
	 * @throws Exception
	 */

	public void openNewTab(String url) throws Exception {
		parentWindHandler = driver.getWindowHandle();

		Robot r = new Robot();
		r.keyPress(KeyEvent.VK_CONTROL);
		r.keyPress(KeyEvent.VK_T);

		Thread.sleep(1000);
		System.out.println("Open a newtab.");
		Set<String> browsers = driver.getWindowHandles();
		for (String i : browsers) {
			if (!i.equals(parentWindHandler)) {
				driver.switchTo().window(i);
				driver.get(url);
				Thread.sleep(1000);
			}
		}
	}

	public void closeCurrentTab() throws Exception {
		Thread.sleep(1000);
		driver.switchTo().window(parentWindHandler);
		Robot robot = new Robot();
		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_W);
	}

}
