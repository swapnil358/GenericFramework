package com.ms.pages;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.codehaus.plexus.util.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.ms.config.XmlRunner;
import com.ms.utilities.MSRunner;
import com.ms.utilities.MSWebElement;
import com.ms.utilities.MSWebElementList;
import com.ms.utilities.pageObjectRepository;

import cucumber.api.DataTable;
import cucumber.api.Scenario;

public class CommonMethods {
	public WebDriver driver = XmlRunner.driverMap.get(Thread.currentThread().getId());
	int objectWait = 2;
	String parentWindHandler = null;
	String parentWindHandler1 = null;
	File folder;
	private String id;
	String setYTDValue = null;
	public static Wait<WebDriver> wait;
	public static LinkedHashMap<String, String> testdataMap;
	public static JavascriptExecutor jse;
	public static String value = "";
	public String[] arrStr;
	public WebElement element = null, ele = null;
	public java.util.Date javaDate;
	public static String expectedResuLt = "";
	String expectedResult2 = null;
	String expectedResult3 = null;
	public static String formatDate = "", strDateformat = "";
	String updatedValue = "";
	String hexCode = "";
	String color = "";
	String getslotvalue = null;
	Set<String> browsers = new HashSet<>();
	private CommonMethods common;

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
	 * This mathod find list of webelements as MSWebElement Object
	 */
	public List<MSWebElement> getWebElementList(String locator_key_value, String page) throws Exception {
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
		MSWebElementList msList = new MSWebElementList(driver, locator);
		return msList;
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

	public void verifyIfSentenceContainTex(String text, String uiElement, String page) throws Exception {
		String textl = getEle(uiElement, page).getText();
		Assert.assertTrue(textl.toLowerCase().contains(text.toLowerCase()), "Text Not Found!");
	}

	public void selectDropdownByVisibleText(String OptionText, String uiElement, String page) throws Exception {
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

	public void SetDate(String date) {

		// String date = "20-Sep-2018";
		String dateArr[] = date.split("-");
		String day = dateArr[0];
		String month = dateArr[1];
		String year = dateArr[2];

		Select selectMonth = new Select(driver.findElement(By.xpath("i/select[@class='ui-datepicker-monthi")));
		selectMonth.selectByVisibleText(month);
		Select selectYear = new Select(driver.findElement(By.xpath("//select[@class='ui-datepicker-yearT")));
		selectYear.selectByVisibleText(year);

		String beforeXpath = "//*[@id='ui-datepicker-div']/table/tbodyitr[";
		String afterXpath = "]/td[";

		final int totalweekDays = 7;
		String dayVal = null;
		boolean flag = false;

		for (int rowNum = 2; rowNum <= 7; rowNum++) {
			for (int colNum = 1; colNum <= totalweekDays; colNum++) {
				try {

					dayVal = driver.findElement(By.xpath(beforeXpath + rowNum + afterXpath + colNum + "]")).getText();
				} catch (NoSuchElementException e) {
					System.out.println("please enter correct date");
					flag = false;
					break;
				}

				System.out.println(dayVal);
				if (dayVal.equals(day)) {
					driver.findElement(By.xpath(beforeXpath + rowNum + afterXpath + colNum + "]")).click();

					flag = true;
					break;
				}
			}
			if (flag)
				break;
		}
	}

	public void OpenMyApp(String url) throws Exception {
		try {
			driver.manage().deleteAllCookies();
			driver.get(url);
			Thread.sleep(5000);
		} catch (Exception e) {
			System.out.println("unable to enter url");
		}
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

	//////////////////////////////////////// Priyaaka////////////////////////////////////////////
	public void openMyApp(String url) throws Exception {
		driver.get(url);
		Thread.sleep(700);
	}

	public void enterText(String data, String uiElement, String page) throws Exception {
		getEle(uiElement, page).click();
		getEle(uiElement, page).sendKeys(data);
		// Thread.sleep(4000);
	}

	public void clickElement(String uiElement, String page) throws Exception {
		try {
			getEle(uiElement, page).click();
		} catch (Exception e) {

			this.clickOnAlertPopUpBtn(uiElement, page);
		}
		Thread.sleep(200);
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
		System.out.println("Open a new tab.");
		Set<String> browsers = driver.getWindowHandles();
		for (String i : browsers) {
			if (!i.equals(parentWindHandler)) {
				driver.switchTo().window(i);
				driver.get(url);
				Thread.sleep(1000);
			}
		}
	}

	public void verifyScrollAction(String page) throws Exception {
		JavascriptExecutor javascript = (JavascriptExecutor) driver;
		Boolean b2 = (Boolean) javascript
				.executeScript("return document.documentElement.scrollHeight>document.documentElement.clientHeight;");
		if (b2 == true) {
			Actions actions = new Actions(driver);

			// Scroll Down using Actions class
			actions.keyDown(Keys.CONTROL).sendKeys(Keys.END).perform();
			// Scroll Up using Actions class
			actions.keyDown(Keys.CONTROL).sendKeys(Keys.HOME).perform();
			/** @author vikulkar */
			// Scroll down using Actions class via Keys.Page Up
			actions.keyDown(Keys.CONTROL).sendKeys(Keys.PAGE_DOWN).perform();
			// Scroll Up using Actions class via Keys.Page Up
			actions.keyDown(Keys.CONTROL).sendKeys(Keys.PAGE_UP).perform();
			// Scroll down using Actions class using Arrow, Down key
			actions.keyDown(Keys.CONTROL).sendKeys(Keys.ARROW_DOWN).perform();
			// Scroll Up using Actions class using arrow Up Key
			actions.keyDown(Keys.CONTROL).sendKeys(Keys.ARROW_UP).perform();
		} else {
			Assert.fail("Scroll BAr not present");
		}
	}

	public void test_data_is_read_from_Excel_with_row(String SheetName, int rowNo) throws Throwable {
		readExcelData(SheetName, rowNo);
	}

	public void readExcelData(String SheetName, int rowNo) throws Exception {
		testdataMap = getRowDataHM(System.getProperty("user.dir") + "//resources//", "testData", SheetName, rowNo);
	}

	public LinkedHashMap<String, String> getRowDataHM1(String FilePath, String WorkBookName, String SheetName,
			int rowIndex) throws Exception {
		FileInputStream file = new FileInputStream(new File(FilePath + "\\" + WorkBookName + ".xlsx"));
		XSSFWorkbook book = new XSSFWorkbook(file);
		XSSFSheet sheet = book.getSheet(SheetName);
		XSSFRow row = sheet.getRow(rowIndex);
		XSSFRow headerRow = sheet.getRow(0);
		LinkedHashMap<String, String> data = new LinkedHashMap<String, String>();
		int firstCell = headerRow.getFirstCellNum();
		int lastCell = headerRow.getLastCellNum();
		XSSFCell cell1 = headerRow.getCell(firstCell);
		XSSFCell ce112 = row.getCell(firstCell);
		for (int i = firstCell + 2; i < lastCell; i++) {
			cell1 = headerRow.getCell(i);
			ce112 = row.getCell(i);
			String headerValue = cell1.getStringCellValue();
			String fieldValue;
			if (ce112 == null) {
				fieldValue = "";
			} else {
				fieldValue = ce112.getStringCellValue();
			}
			data.put(headerValue, fieldValue);
		}
		book.close();
		return data;
	}

	public void VerifyTextPresentOnPage(DataTable data) throws Exception {
		int j;

		List<List<String>> obj = data.raw();
		int h = obj.size();
		for (j = 0; j < obj.size(); j++) {

			String message = obj.get(1).get(j);
			MSWebElement ele = getEle(By.xpath("//*[contains(text()," + message + "))"));
			if (ele.isDisplayed()) {
				System.out.println("Found : " + message);
			} else {
				throw new Exception("Element with message *" + message + "* found but NOT displayed");
			}
		}
	}

	public void verifyRadioButtonl() {
		driver.findElement(By.xpath("//table[1]/tbody[1]/tr[3]/td[1]/div/cc-dropdown/div/button")).click();
		WebElement chboxl = driver
				.findElement(By.xpath("//table[1]/tbody[1]/tr[3]/td[1]/div/cc-dropdown/div/button/span[2]"));
		System.out.println(chboxl.isSelected());
	}

	public void Check1() {
		int count = 0;
		List<WebElement> ls = driver.findElements(By.xpath("//tbody/tr/td[11]"));
		if (ls.size() >= 0) {
			// System.cut.println("All active assignments"+ ls.size());
			for (WebElement e : ls) {
				String text = e.getText();
				// System.out.println(text);
				if (text.equalsIgnoreCase("FA")) {
					count++; // System.out.println("All active assignments"+ ls.size());
				}
			}
		}
		System.out.println(count + " FA active assignments");
	}

	public void typeAndSelectCCG(String uiElement, String page) throws Exception {
		WebDriverWait wait = new WebDriverWait(driver, 5);
		wait.until(ExpectedConditions.visibilityOf(getEle(uiElement, page)));
		getEle(uiElement, page).click();
		getEle(uiElement, page).sendKeys(setYTDValue);
	}

	public void waitTillVisibility(String uiElement, String page) throws Exception {
		boolean b = !(getEle(uiElement, page).isPresent());
		System.out.println(b);
		while (!(getEle(uiElement, page).isPresent()) == false) {
			Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(60, TimeUnit.SECONDS)
					.pollingEvery(5, TimeUnit.SECONDS).ignoring(NoSuchElementException.class);
		}
	}

	public void switchTab(String Text) throws Exception {
		if (Text.equals("parent")) {
			driver.switchTo().window(parentWindHandler);
		} else {
			for (String i : browsers) {
				if (!i.equals(parentWindHandler)) {
					driver.switchTo().window(i);
					// driver.get(url); Thread.sLeep(3000);
				}
			}
		}
	}
	// driver.switchTo().window(childWindHandler);

	public LinkedHashMap<String, String> getRowDataHM(String FilePath, String WorkBookName, String SheetName,
			int rowIndex) throws Exception {
		FileInputStream file = new FileInputStream(new File(FilePath + "\\" + WorkBookName + ".xlsx"));
		XSSFWorkbook book = new XSSFWorkbook(file);
		XSSFSheet sheet = book.getSheet(SheetName);
		XSSFRow row = sheet.getRow(rowIndex);
		XSSFRow headerRow = sheet.getRow(0);
		LinkedHashMap<String, String> data = new LinkedHashMap<String, String>();
		int firstCell = headerRow.getFirstCellNum();
		int lastCell = headerRow.getLastCellNum();
		XSSFCell cell1 = headerRow.getCell(firstCell);
		XSSFCell ce112 = row.getCell(firstCell);
		for (int i = firstCell + 2; i < lastCell; i++) {
			cell1 = headerRow.getCell(i);
			ce112 = row.getCell(i);
			String headerValue = cell1.getStringCellValue();
			String fieldValue;
			if (ce112 == null) {
				fieldValue = "";
			} else {
				fieldValue = ce112.getStringCellValue();
			}
			data.put(headerValue, fieldValue);
		}
		book.close();
		return data;
	}

	public void scrollToElement(WebElement ele) {
		try {
			jse.executeScript("arguments[0].scrollIntoView({block : \"center\", inline : \"center\" });", ele);
		} catch (NoSuchElementException e) {
			Assert.fail("Unable to locate element : ", e);
		}
	}

	public boolean validateElement(String testData, String eleId, String sPage) {
		MSWebElement ele = null;
		boolean assertVerifyStatus = false;
		value = "";
		String expValue = "";
		try {
			expValue = testdataMap.get(testData).trim();
			System.out.println("Expected Text to be present: " + expValue);

			ele = getEle(eleId, sPage);
			value = ele.getText();
			System.out.println("Actual text present is : " + value);
			if (value.equalsIgnoreCase(expValue)) {
				System.out.println("!!!Actual text present and expected text to be present both are equal!!!");
				assertVerifyStatus = true;
			} else {
				System.out.println("Element with message *" + expValue + "* NOT present");
				assertVerifyStatus = false;
			}
		} catch (Exception e) {
			System.out.println("Exception is: " + e.getMessage());
			assertVerifyStatus = false;
		}
		return assertVerifyStatus;
	}

	public boolean waitVisibilityStandarized(WebElement elementBy) {
		try {
			wait.until(ExpectedConditions.visibilityOf(elementBy));
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public String getAttributeValue(WebElement elementBy, String attribName) {
		waitVisibility(elementBy);
		return elementBy.getAttribute(attribName);
	}

	public void waitVisibility(WebElement elementBy) {
		wait.until(ExpectedConditions.visibilityOf(elementBy));
	}

	/**
	 * * @author yaglkK * Description This method retries and clicks on an element *
	 * 
	 * @param element, -attribute whose values is to be returned By name, xpAth.
	 *                 etc.
	 */
	public boolean retryingFindClick(WebElement el) {
		boolean result = false;
		int attempts = 0;
		while (attempts < 10) {
			try {
				el.click();
				result = true;
				break;
			} catch (Exception e) {

			}
			attempts++;
		}
		return result;
	}

	/**
	 * * @author yikmlkar, *
	 * 
	 * @Description This method return c5A, value of an element based on its *
	 *              properties like color, font etc *
	 * @param element, * c„ss,-attribute whose values is to be returned By name,
	 *                 kpatt * etc.
	 */ // Get CSS value
	public String getCssValue(WebElement elementBy, String cssValue) {
		waitVisibility(elementBy);
		return elementBy.getCssValue(cssValue);
	}

	public WebElement waitForElementVisibility(WebElement elementBy) {
		return (new WebDriverWait(driver, 30)).until(ExpectedConditions.visibilityOf(elementBy));
	}

	public void waitTillClickable(WebElement elementBy) {
		wait.until(ExpectedConditions.elementToBeClickable(elementBy));
	}

	/**
	 * * @author y#1111<ar. * @Description This method waits until ALL elements are
	 * visible * @param element By name, xpoth etc.
	 */

	public void waitAllVisibility(List<WebElement> elementAll) {
		wait.until(ExpectedConditions.visibilityOfAllElements(elementAll));
	}

	/**
	 * * @author yikklj,<K *
	 * 
	 * @Description This method checks if an element is ACTIVE or not *
	 * @param element By name, xpath etc.
	 */

	public boolean isElementActive(By by) {
		driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
		boolean exists = (driver.findElements(by).size() != 0);
		driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
		return exists;
	}

	public void validateSortingOrder(String uiElement, String page) throws Exception {
		List<MSWebElement> uiGridData = getWebElementList(uiElement, page);
		List<String> uiData = new ArrayList<String>();
		List<String> uiData1 = new ArrayList<String>();
		for (MSWebElement e : uiGridData) {
			String data = e.getText();
			uiData.add(data);
		}
		uiData1.addAll(uiData);
		Collections.sort(uiData1);
		Collections.sort(uiData1, String.CASE_INSENSITIVE_ORDER);
		Assert.assertTrue(uiData.equals(uiData1));
	}

	public void validateReverseSortingOrder(String uiElement, String page) throws Exception {
		List<MSWebElement> uiGridData = getWebElementList(uiElement, page);
		List<String> uiData = new ArrayList<String>();
		List<String> uiData1 = new ArrayList<String>();
		for (MSWebElement e : uiGridData) {
			String data = e.getText();
			uiData.add(data);
		}
		Collections.sort(uiData);
		uiData1.addAll(uiData);
		Collections.sort(uiData1);
		System.out.println("uiData1SortedData: " + uiData1);
		System.out.println("uiData::	" + uiData);
		Assert.assertTrue(uiData.equals(uiData1));
	}

	public void verifyTextPresentOnTextBox(String expectedText, String uiElement, String page) throws Exception {
		String getUIText = getEle(uiElement, page).getAttribute("value");
		System.out.println(getUIText);
		Assert.assertTrue(getUIText.contains(expectedText), "Text verified");
	}

	/*
	 * Get all tabs on navigation bar
	 */
	public void GetElementsList() throws Exception {
		WebElement navigation = driver.findElement(By.xpath("//div[@id='navigationl"));
		int i = 1;
		List<WebElement> ls = navigation.findElements(By.tagName("a"));

		if (ls.size() > 0) {
			for (WebElement e : ls) {
				String name = e.getText();
				System.out.println(i + "-" + name);
				i++;
			}
		} else {
			System.out.println("no match found");
		}
	}

	public void ValidateElementFromAutoSuggestion(String text) throws Exception {
		Actions actions = new Actions(driver);
		List<WebElement> ls = driver.findElements(By
				.xpath("//u1[@class='ui-menu ui-widget ui-widget-content ui-autocomplete ui-front ud-dropdown-menu']"));
		if (ls.size() > 0) {
			Iterator<WebElement> l = ls.iterator();
			while (l.hasNext()) {
				actions.sendKeys(Keys.ARROW_DOWN).build().perform();
				String getUIText = getEle((By.xpath("//inputHad='Enter_Company_Namer"))).getAttribute("value");
				if (getUIText.equalsIgnoreCase(text)) {
					break;
				}
				System.out.println("Suggestion found with " + text + " is: " + getUIText);
			}
		} else {
			System.out.println("no match found");
		}
	}

	public void setTodaysdate() {
		DateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
		Calendar cal = Calendar.getInstance();
		Date call = cal.getTime();
		String date = df.format(call);
		System.out.println(date); // SetDate(date);
	}

	/**
	 * * @author Trag4
	 */
	public void validateCurrentDate() {
		DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
		Calendar cal = Calendar.getInstance();
		Date call = cal.getTime();
		String date = df.format(call);
		System.out.println(date);
		String uiDate = driver
				.findElement(By.xpath("(//*[@class='display-inline ud-label.]//div[@class='ud-label'])[3]")).getText();
		Assert.assertTrue(date.equals(uiDate), "Date is different" + date);
	}

	public void validateTextStyle(String uiElement, String expectedStyle, String page) throws Exception {
		String getUITextStyle = getEle(uiElement, page).getCssValue("font-style");
		System.out.println(getUITextStyle);
		Assert.assertTrue(getUITextStyle.contains(expectedStyle), "Style verified");
	}

	public void valNdClickOnAlertPopUpBtn(String uiElement, String page) throws Exception {
		boolean b = getEle(uiElement, page).isPresent();
		System.out.println(b);
		if (!b) {
			Assert.assertTrue(true, "alert is present");
		} else {
			getEle(uiElement, page).click();
			Thread.sleep(1000);
		}
	}

	public void zoneDatel(String uiElement, String page) throws Exception {
		String getUlDate = getEle(uiElement, page).getText();
		Calendar cal = Calendar.getInstance();
		DateFormat dateFormat = new SimpleDateFormat("dd-tfl1-YYYY");
		dateFormat.setTimeZone(TimeZone.getTimeZone("US/Eastern"));
		String date1 = dateFormat.format(cal.getTime());
		String expectedDate = "As of " + date1;
		Assert.assertTrue(getUlDate.contains(expectedDate), "As of not verified");
	}

	/**
	 * * @author yAm,u9r. * @Description This method finds and verifies the X,Y
	 * coordinates *
	 * 
	 * @param element By name, xp.011 etc.
	 * @param fEle
	 * @param sEle
	 * @param pos
	 * @param sPage
	 * @return
	 */
	public boolean verifyX_YCoordinates(String fEle, String sEle, String pos, String sPage) {
		int firstElementposition_X = 0, secElementposition_X = 0;
		boolean assertVerifyStatus = false;
		try {
			element = getEle(fEle, sPage);
			firstElementposition_X = element.getLocation().getX();
			ele = getEle(sEle, sPage);
			secElementposition_X = ele.getLocation().getX();
			if (pos.equals("leftSide")) {
				if (firstElementposition_X < secElementposition_X) {
					System.out.println("Element is at LEFT position");
					assertVerifyStatus = true;
				} else {
					if (firstElementposition_X > secElementposition_X) {
						System.out.println("Element is at RIGHT position");
						assertVerifyStatus = true;
					}
				}
			}
		} catch (Exception e) {
			System.out.println("Exception is: " + e.getMessage());
			assertVerifyStatus = false;
		}
		return assertVerifyStatus;
	}

	/**
	 * * @author amalkor, *
	 * 
	 * @description - validate text value is present *
	 * @param 1. Text expected (from testDataSheet) value 2. UlElement - should
	 *           match with property file element name 3. page - should match with
	 *           property file name
	 * @return boolean
	 */

	public boolean VerifyListDropdownValue(String expName, String sEle, String sPage) {
		boolean assertVerifyStatus = false;
		String companyName = ""/* , expValue = "" */;
		try {
			// expValue = testdataMap.get(expName).trim();
			System.out.println("Expected Value is: " + expName);
			List<MSWebElement> uiGridData = getWebElementList(sEle, sPage);
			for (int i = 0; i < uiGridData.size(); i++) {
				companyName = uiGridData.get(i).getText().trim();
				Thread.sleep(2000);
				if (companyName.equalsIgnoreCase(expName) || companyName.contains(expName)) {
					System.out.println("Dropdown Name " + companyName + " exists. Clicking on it");
					assertVerifyStatus = true;
					uiGridData.get(i).click();
					break;
				} else {
					System.out.println("Dropdown Name " + companyName + " doesn't exists");
					assertVerifyStatus = false;
				}
			}
		} catch (Exception e) {
			System.out.println("Exception is: " + e.getMessage());
			assertVerifyStatus = false;
		}

		return assertVerifyStatus;
	}

	/**
	 * @author yikasgc. *
	 * @description - validate text value is present *
	 * @param 1. Text expected (from testDataSheet) value 2. UlElement - should
	 *           match with property file element name 3. page - should match with
	 *           property file name
	 * @return boolean
	 */
	public boolean validatetableElement(String testData, String sEle, String sPage) {
		boolean assertVerifyStatus = false;
		String companyName = "", expValue = "";
		try {
			expValue = testdataMap.get(testData).trim();
			System.out.println("Expected value is: " + expValue);
			List<MSWebElement> uiGridData = getWebElementList(sEle, sPage);
			for (int i = 0; i < uiGridData.size(); i++) {
				companyName = uiGridData.get(i).getText();
				Thread.sleep(2000);
				if (companyName.contains(expValue) || companyName.equalsIgnoreCase(expValue)) {
					System.out.println("Actual Name " + companyName + " contains " + expValue);
					assertVerifyStatus = true;
				} else {
					System.out.println("Actual Name " + companyName + " doesn't contains " + expValue);
					assertVerifyStatus = false;
				}
			}
		} catch (Exception e) {
			System.out.println("Exception is: " + e.getMessage());
			assertVerifyStatus = false;
		}
		return assertVerifyStatus;
	}

	/*
	 * @author yikulkar,
	 * 
	 * @description - validate text value is present in UI dropdown.
	 * 
	 * @param 1.
	 * 
	 * Text expected (from testDataSheet) value 2. UIElement - should match with
	 * property file element name 3. page - should match
	 * 
	 * . with property file name
	 * 
	 * @return boolean
	 */

	public boolean validateUiDropdpwnElement(String testData, String sEle, String sPage) {
		boolean assertVerifyStatus = false;
		String companyName = "", expValue = "";
		try {
			expValue = testdataMap.get(testData).trim();
			System.out.println("Expected value is: " + expValue);
			List<MSWebElement> uiGridData = getWebElementList(sEle, sPage);
			for (int i = 0; i < uiGridData.size(); i++) {
				companyName = uiGridData.get(i).getText();
				Thread.sleep(2000);
				if (companyName.contains(expValue) || companyName.equalsIgnoreCase(expValue)) {
					System.out.println("Company Name " + companyName + " contains " + expValue);
					assertVerifyStatus = true;
					break;
				} else {
					System.out.println("Company Name " + companyName + " doesn't contains " + expValue);
					assertVerifyStatus = false;
				}
			}
		} catch (Exception e) {

			System.out.println("Exception is: " + e.getMessage());
			assertVerifyStatus = false;

		}

		return assertVerifyStatus;
	}
	/*
	 * validate UI element is dialsyed as in perticular orders
	 */

	public boolean validateUIElement(String eleId, String condition, String testData, String sPage) throws Exception {
		MSWebElement ele = null;
		String value = "";
		// i,nt, count = 0;
		// 600lean status = false;
		switch (condition) {
		case "ascending order":
			expectedResuLt = testdataMap.get(testData);
			System.out.println("Value from excel file :" + expectedResuLt);
			ele = getEle(eleId, sPage);
			value = ele.getAttribute("class");
			System.out.println("Actual Value of attribute: " + value);
			if (expectedResuLt.equalsIgnoreCase(value)) {
				return true;
			} else {
				return false;
			}
		case "desending order":
			expectedResuLt = testdataMap.get(testData);
			System.out.println("Value from excel file :" + expectedResuLt);
			ele = getEle(eleId, sPage);
			value = ele.getAttribute("class");
			System.out.println("Actual Value of attribute: " + value);
			if (!expectedResuLt.equalsIgnoreCase(value)) {
				return true;
			} else {
				return false;
			}
		default:
			System.out.print("no case selected or Found for verifyElement");
			break;
		}
		scrollToElement(ele);
		boolean assertVerifyStatus = waitVisibilityStandarized(ele);
		return assertVerifyStatus;
	}

	public void checkSorting() {
		// *[starts-with(@id,'Opportunity')]//00y/t17[1]/4[1]
		String before_xpath = "//s[starts-with(@ad,'Opportunity')]//tbody/tr[";
		String after_xpath = "]/td[1]";

		ArrayList<Long> obtainedList = new ArrayList<Long>();
		for (int i = 1; i <= 50; i++) {
			String text = driver.findElement(By.xpath(before_xpath + i + after_xpath)).getText().toString().trim();
			String updatedText = text.replaceAll("\\D+", ""); // to remove non // digit

			long intText = Long.valueOf(updatedText);
			obtainedList.add(intText);
		}
		System.out.println(obtainedList);
		ArrayList<Long> sortedList = new ArrayList<Long>();
		for (Long s : obtainedList) {
			sortedList.add(s);
		}
		Collections.sort(sortedList);
		Collections.reverse(sortedList);
		Assert.assertTrue(sortedList.equals(obtainedList));
	}

	public void checkSortingString() {
		String before_xpath = "// starts-with(@id,'Opportunity')]//tbody/tr[";
		String after_xpath = "]/td[7]";
		ArrayList<String> obtainedList = new ArrayList<String>();
		for (int i = 1; i <= 50; i++) {
			String text = driver.findElement(By.xpath(before_xpath + i + after_xpath)).getText().toString();
			System.out.println(text);
			obtainedList.add(text);
		}
		System.out.println(obtainedList);
		ArrayList<String> sortedList = new ArrayList<String>();
		for (String s : obtainedList) {
			sortedList.add(s);
		} // Collections.sort(sortedList);
			// Collections.reverse(sortedList);
		Assert.assertTrue(sortedList.equals(obtainedList));

	}

	/*
	 * pegination
	 */
	public void validatefromList(String ExpectedText, String count, String page) throws Exception {
		int rowl;
		String msg = driver.findElement(By.xpath("//s.an ilid='viewin-RecordStart' /.arent::s.an")).getText();
		String[] msg1 = msg.split(" ");
		int no = Integer.parseInt(msg1[3]);

		rowl = Integer.parseInt(count);
		if (no > 50) {
			for (int i = 1; i < 50; i++) {
				String Text = driver.findElement(By
						.xpath("//td[peaders='companyName']/parent::tr/parent::tbody//tr[" + i + "]//td[" + rowl + "]"))
						.getText();
				System.out.println(Text);
				Assert.assertTrue(Text.contains(ExpectedText), "As of not verified");
			}
		} else if (no < 50 && no != 0) {
			for (int i = 1; i <= no; i++) {
				String Text = driver.findElement(By.xpath(
						"//td[Pleaders='companyName']/parent::tr/parent::tbody//tr[" + i + "]//td[" + rowl + "]"))
						.getText();
				System.out.println(Text);
				Assert.assertTrue(Text.contains(ExpectedText), "As of not verified");
			}
		} else if (no == 0) {
			System.out.println("No record found");
		}
	}

	/*
	 * To copy the id
	 */
	public String IdMethod() {
		String inputStringl = driver.findElement(By.xpath("//*[id=qastActivityMessage]")).getText();
		String ID1 = inputStringl.replaceAll("[a-z,A-Z.,:,/, ]", "");
		System.out.println(ID1);
		this.id = ID1;
		return ID1;
	}

	/*
	 * To paste the id
	 */
	public void copyAndEnterIdInAnotherTab() {
		IdMethod();
		driver.findElement(By.xpath("//inputRad='IDT']")).clear();
		driver.findElement(By.xpath("//inputHlid='IDT']")).sendKeys(id);
	}

	public void copyAndEnterAssignmentId() {
		IdMethod();
		driver.findElement(By.xpath("//input[@id='Assignment_IDT']")).clear();
		driver.findElement(By.xpath("//input[@id='Assignment_IDT']")).sendKeys(id);
	}

	/*
	 * 
	 */
	public void validateCallReports(String uiElement, String page) throws Exception {
		List<MSWebElement> uiGridData = getWebElementList(uiElement, page);
		List<String> uiData = new ArrayList<String>();
		List<String> uiData1 = new ArrayList<String>();
		for (MSWebElement e : uiGridData) {
			String data = e.getText();
			uiData.add(data);
		}
		uiData1.addAll(uiData);
		System.out.println(uiData);
		System.out.println(uiData1);
		Assert.assertTrue(uiData.equals(uiData1));
	}

	/*
	 * 
	 */

	public void checkdefaultsort() {
		String before_xpath = "//'[starts-with(@id,undefined')]//tbody/tr[";
		String after_xpath = "]/td[1]";
		ArrayList<Long> obtainedList = new ArrayList<Long>();
		for (int i = 1; i <= 50; i++) {
			String text = driver.findElement(By.xpath(before_xpath + i + after_xpath)).getText().toString().trim();
			String updatedText = text.replaceAll("\\D+", "");
			long intText = Long.valueOf(updatedText);
			obtainedList.add(intText);
		}
		System.out.println(obtainedList);
		ArrayList<Long> sortedList = new ArrayList<Long>();
		for (Long s : obtainedList) {
			sortedList.add(s);
		}
		Collections.sort(sortedList);
		Collections.reverse(sortedList);
		Assert.assertTrue(sortedList.equals(obtainedList));
	}

	// Sortingdate
	public void validateDatesAreInAscendingOrder() throws ParseException {
		boolean result2 = false;
		for (int i = 0; i < 49; i++) {
			String datel = driver.findElement(By.xpath("//*[@id='Opportunity_Grid_Submission_Date_" + i + "']"))
					.getText();

			String date2 = driver.findElement(By.xpath("//*[@id='Opportunity_Grid_Submission_Date_" + ++i + "']"))
					.getText();
			String strDateFormat = "dd-MMM-yyyy";
			SimpleDateFormat sdf = new SimpleDateFormat(strDateFormat);
			Date d1 = sdf.parse(datel);
			Date d2 = sdf.parse(date2);
			if (d1.compareTo(d2) >= 0) {
				result2 = true;
			}
			Assert.assertTrue(result2);
		}
	}

	public void uploadScrubFile(String fileName) throws InterruptedException {
		String excelAbsolutepath = new File("").getAbsolutePath();
		String FILE_NAME1 = excelAbsolutepath + "\\resources\\List_management_files\\";
		String filePath = FILE_NAME1 + fileName;
		WebElement choosefile = driver.findElement(By.xpath("//*[@type=*file]"));
		choosefile.sendKeys(filePath);
		System.out.println("Upoaded Successfully");
	}

	public void validateDueDate(String date, String page) throws Exception {
		String date1 = getEle(date, page).getText();
		Date d2 = new Date();
		String strDateFormat = "dd-MMM-yyyy";
		SimpleDateFormat sdf = new SimpleDateFormat(strDateFormat);
		Date d1 = sdf.parse(date1);
		long difference_in_time = d1.getTime() - d2.getTime();
		long difference_in_days = ((difference_in_time) / (1000 * 60 * 60 * 24)) % 365;
		System.out.println(d2);
		System.out.println(d1);
		System.out.println(difference_in_days);
	}

	public void verifyTextsPresentOnPage(DataTable data) throws Exception {
		int j;
		List<List<String>> obj = data.raw();
		int h = obj.size();
		for (j = 0; j < obj.size(); j++) {

			String message = obj.get(1).get(j);
			MSWebElement ele = getEle(By.xpath("//*[contains(text(),— + message + —)]"));
			if (ele.isDisplayed()) {
				System.out.println("Found : " + message);
			} else {
				throw new Exception("Element with message *" + message + "* found but NOT displayed");
			}
		}
	}

	public void validatecount(String uiElement, String uiElement1, String page) throws Exception {
		String Text = getEle(uiElement, page).getText();
		System.out.println(Text);
		String T = Text.replaceAll("[a-z,A-Z.,(,:,),]", "");
		System.out.println(T);
		String txt = getEle(uiElement1, page).getText();
		System.out.println(txt);
		String[] splittxt = txt.split(" ");
		String txt1 = splittxt[3];
		System.out.println(txt1);
		Assert.assertTrue(T.contains(txt1), "As of not verified");
	}

	/*
	 * scrolliUpAndDown
	 * 
	 * And I scroll "down" the dataGrid And I scroll "Up" the data grid
	 */
	public void scrollinsidepagedown(String Direction) throws InterruptedException {
		List<WebElement> rows = driver
				.findElements(By.xpath("//table[@class='ud-table scrollable cc-corp-db-table']/tbody/tr"));
		int count = rows.size();
		System.out.println(count);
		WebElement scrollDir = null;
		if (Direction.equals("down")) {
			scrollDir = driver.findElement(
					By.xpath("//table[@class=.ud-table scrollable cc-corp-db-table']/tbody/tr[" + count + "]"));
		} else if (Direction.equalsIgnoreCase("UP")) {
			scrollDir = driver.findElement(By.xpath("//*[@id='Individual Reservations Grid']/tbody/tr[1]"));
		}
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView( )", scrollDir);
		Thread.sleep(5000);
		System.out.println("scroll the grid " + Direction);
	}

	/*
	 * get the number from string
	 */

	public int numberFromString(String inputString) {
		int noFromString = 0;
		String temp = "";
		for (char ch : inputString.toCharArray()) {
			if (Character.isDigit(ch)) {
				temp = temp + ch;
			}
		}
		// System.out.println("No in string format: " + temp);
		noFromString = Integer.parseInt(temp);
		// System.out.println("Number in int format: " + noFromString);
		return noFromString;
	}

	/*
	 * Date sorting order
	 */
	public void validateDateSortingOrder(String uiElement, String sortOrder, String page) throws Exception {
		List<MSWebElement> uiGridData = getWebElementList(uiElement, page);
		List<String> uiData = new ArrayList<String>();
		List<String> uiData1 = new ArrayList<String>();
		for (MSWebElement e : uiGridData) {
			String data = e.getText();
			System.out.println("Date present in String format: " + data);
			uiData.add(data);
		}
		System.out.println("Data present in String format after adding all: " + uiData);
		uiData1.addAll(uiData);
		// sort the dates present in UI using Collections and comparator
		Collections.sort(uiData, new Comparator<String>() {
			DateFormat f = new SimpleDateFormat("dd-MMM-yyyy");

			public int compare(String o1, String o2) {
				try {
					return f.parse(o1).compareTo(f.parse(o2));
				} catch (ParseException e) {
					throw new IllegalArgumentException(e);
				}
			}
		});
		System.out.println("Data After sorting in ascending order: " + uiData1);
		Collections.sort(uiData1, String.CASE_INSENSITIVE_ORDER);
		Assert.assertTrue(uiData.equals(uiData1));
	}

	public void pageRefresh() throws Exception {
		ele = getEle("newsfeed", "Corporate_Reservations");
		driver.navigate().refresh();
		System.out.println("Page refreshed");
		Thread.sleep(8000);
	}

	public void waitTime() throws InterruptedException {
		for (int i = 0; i < 5; i++) {
			Thread.sleep(10000);
			Thread.sleep(10000);
		}
		System.out.println("Waited for some time");
	}

	public void zoomBrowser(String zoomCondition, String seleId, int zoomTimes, String sPage) throws Exception {
		Robot robot = new Robot();
		element = driver.findElement(By.tagName("html"));
		System.out.println("Zoom Times is: " + zoomTimes);
		if (zoomCondition.equalsIgnoreCase("zoom-In")) {
			for (int i = 0; i < zoomTimes; i++) {
				element.sendKeys(Keys.chord(Keys.CONTROL, Keys.ADD));
				// robot.keyPress(KeyEvent.VK_CONTROL); // robot.keyPress(KeyEvent.VK_ADD);
			}
			System.out.println("Browser Zoomed in " + zoomTimes + " times");
		} else if (zoomCondition.equalsIgnoreCase("zoom-out")) {
			for (int i = 0; i < zoomTimes; i++) {
				element.sendKeys(Keys.chord(Keys.CONTROL, Keys.SUBTRACT));
				robot.keyPress(KeyEvent.VK_CONTROL);
				robot.keyPress(KeyEvent.VK_SUBTRACT);
			}
			System.out.println("Browser Zoomed Out " + zoomTimes + " times");
		} else {
			element.sendKeys(Keys.chord(Keys.CONTROL, "0"));
		}
	}

	public boolean validateTableElementPresentAndClick(String testData, String fEle, String sEle, String sPage) {
		boolean assertVerifyStatus = false;
		int rowNumber = 0;
		String companyName = "", expValue = "";
		try {
			expValue = testdataMap.get(testData).trim();
			System.out.println("Expected value is: " + expValue);
			List<MSWebElement> uiGridData = getWebElementList(fEle, sPage);
			for (int i = 0; i < uiGridData.size(); i++) {
				companyName = uiGridData.get(i).getText();
				Thread.sleep(2000);
				if (companyName.contains(expValue) || companyName.equalsIgnoreCase(expValue)) {
					System.out.println("Element " + expValue + " is PRESENT ");
					rowNumber = i;
					assertVerifyStatus = true;
					break;
				} else {
					System.out.println("Element " + expValue + " is NOT PRESENT ");
					assertVerifyStatus = false;
				}
			}

			List<MSWebElement> uiAssigId = getWebElementList(sEle, sPage);
			while (rowNumber > 0) {
				Thread.sleep(2000);
				companyName = uiAssigId.get(rowNumber).getText();
				System.out.println("Assignment id to be clicked is: " + companyName);
				uiAssigId.get(rowNumber).click();
				assertVerifyStatus = true;
				break;
			}
		} catch (Exception e) {
			System.out.println("Exception is: " + e.getMessage());
			assertVerifyStatus = false;
		}
		return assertVerifyStatus;
	}

	public void VerifyEmptySearchbox(String uiElement, String page) throws Exception {
		MSWebElement text = getEle(uiElement, page);
		System.out.println(text.getAttribute("value"));
		String val = text.getAttribute("value");
		// String val2=text.getAttribute("multiple");
		if (val.isEmpty()) {
			System.out.println("no text found");
		} else {
			System.out.println("text found");
		}
	}

	public void verifyRadioButton() {
		driver.findElement(By.xpath("//table[1]/tbody[1]/tr[3]/td[1]/div/cc-dropdown/div/button")).click();
		WebElement chbox1 = driver
				.findElement(By.xpath("//table[1]/tbody[1]/tr[3]/td[1]/div/cc-dropdown/div/button/span[2]"));
		System.out.println(chbox1.isSelected());
	}

	public void Check() {
		WebElement table = driver.findElement(By.xpath("//tbody/tr/td[11]"));
		List<WebElement> allRows = table.findElements(By.tagName("tr"));
		for (WebElement row : allRows) {
			List<WebElement> cells = row.findElements(By.tagName("td"));
			for (WebElement cell : cells) {
				System.out.println("content >> " + cell.getText());
			}
		}
	}

	public void VerifyColorOfText(String text, String uiElement, String page) throws Exception {
		String rgb_colorString = getEle(uiElement, page).getCssValue("color");
		System.out.println(rgb_colorString);
		String[] hexcolor = Color.fromString(rgb_colorString).asHex().split("4");
		System.out.println(hexcolor[1]);
		Assert.assertTrue(hexcolor[1].equals(text));
	}

	// Aaankar For fetching and validating Date
	public void FetchingDateFromMessage(String uiElement, String page) throws Exception {
		String getUIDate = getEle(uiElement, page).getText();
		Calendar cal = Calendar.getInstance();
		String date[] = getUIDate.split(" ");
		String actual_date = date[2];
		System.out.println(actual_date);
		DateFormat dateFormat = new SimpleDateFormat("dd-MMM-YYYY HH:mm");
		dateFormat.setTimeZone(TimeZone.getTimeZone("GMT-04:00"));
		String date1 = dateFormat.format(cal.getTime());
		String[] date_1 = date1.split(" ");
		String expected_date = date_1[0];
		System.out.println(expected_date);
		Assert.assertTrue(actual_date.contains(expected_date), "As of not verified");
	}

	public void time_stamp(String uiElement, String page) throws Exception {
		String getUlDate = getEle(uiElement, page).getText();
		Calendar cal = Calendar.getInstance();
		DateFormat dateFormat = new SimpleDateFormat("dd-MMM-YYYY HH:mm:ss");
		dateFormat.setTimeZone(TimeZone.getTimeZone("GMT-04:00"));
		String datel = dateFormat.format(cal.getTime());
		String expectedDate = "As of " + datel + "AM EST";
		Assert.assertFalse(getUlDate.contains(expectedDate), "As of not verified");
		System.out.println(expectedDate);
	}

	public void getcurrentYTDValue(String uiElement, String page) throws Exception {
		setYTDValue = getEle(uiElement, page).getText();
		int earlierYTD = Integer.parseInt(setYTDValue);
		System.out.println(earlierYTD);
	}

	public void time_stampl(String uiElement, String page) throws Exception {
		String getUlDate = getEle(uiElement, page).getText();
		Calendar cal = Calendar.getInstance();
		DateFormat dateFormat = new SimpleDateFormat("dd-MMM-YYYY");
		dateFormat.setTimeZone(TimeZone.getTimeZone("GMT-04:00"));
		String datel = dateFormat.format(cal.getTime());
		String expectedDate = datel;
		Assert.assertTrue(getUlDate.contains(expectedDate), "As of not verified");
		System.out.println(expectedDate);
	}

	public void verifypatnershipslotvalue(String uiElement, String page) throws Exception {
		String slotValue = setYTDValue.substring(13, 15);
		int earlierSlot = Integer.parseInt(slotValue);
		String newSlotValue = getEle(uiElement, page).getText();
		newSlotValue = newSlotValue.substring(13, 15);
		int currentSlot = Integer.parseInt(newSlotValue);
		Assert.assertTrue(earlierSlot == currentSlot + 1, "Value different");
	}

	public void reverifypatnershipvalue(String uiElement, String page) throws Exception {
		String slotValue = setYTDValue.substring(13, 15);
		int earlierSlot = Integer.parseInt(slotValue);
		String newSlotValue = getEle(uiElement, page).getText();
		newSlotValue = newSlotValue.substring(13, 15);
		int currentSlot = Integer.parseInt(newSlotValue);
		Assert.assertTrue(earlierSlot == currentSlot, "Value different");
	}

	public void selectTextAfterClickingOnButtonEle(String Text, String page) {
		driver.findElement(By.xpath("//b(contains(text(),'Pending')Wancestor::tbody//div//button")).click();
		driver.findElement(By.cssSelector("#undefined_" + Text + " > a > h2")).click();
	}

	public void uncheckAllTheCheckboxes(String page) throws Exception {
		for (int i = 1; i <= 21; i++) {
			WebElement elel = driver.findElement(By.xpath("//*[@id='Eligible_Type']/cc-checkbox[" + i + "]//input"));
			if (elel.isSelected()) {
				elel.click();
			}
		}
	}

	public void validateColorRed() {
		String colour = driver.findElement(By.xpath("//div[@class='pagination-dropdown float-left']/div/span[3]"))
				.getCssValue("color");

		// System.out.println(font);
		String hex = Color.fromString(colour).asHex();
		String actual_color = "#cf3838";
		if (actual_color.equals(hex)) {
			System.out.println("color is red");
		} else {
			System.out.println("color is not red");
		}
	}

	public void validateCountofRecordswithDropdown(String text, String uiElement, String page) {
		String dropDopwnOption = driver.findElement(By.xpath("//span[contains(text(),'" + text + "')]")).getText();
		String recordCount = dropDopwnOption.substring(dropDopwnOption.indexOf("(") + 1, dropDopwnOption.indexOf(")"));
		String totalCount = driver.findElement(By.xpath("//*[@class='text']")).getText();
		String[] vals = totalCount.split(" ");
		boolean result = recordCount.equals(vals[3].trim());
		Assert.assertTrue(result);
	}

	/* IrRat */
	public void validateColorDate() throws ParseException {
		String color = driver.findElement(By.xpath("//td[@class.'ud-laber]/div/b[text().'12-Nov-2020']"))
				.getCssValue("color");
		String actual_color = "#006400"; // System.out.println("olour, "+color);
		String color_hex = org.openqa.selenium.support.Color.fromString(color).asHex();
		System.out.println(actual_color.equals(color_hex) + " dark green color matches");

		Assert.assertTrue(actual_color.equals(color_hex), "dark green color matches");
		SimpleDateFormat objSDF = new SimpleDateFormat("dd-mm-yyyy");
		Date dt_1 = objSDF.parse("12-11-2020");
		Date dt_2 = objSDF.parse("12-05-2019");
		System.out.println("Datel : " + objSDF.format(dt_1));
		System.out.println("Date2 : " + objSDF.format(dt_2));
	}

	public void validateUpdateDetails(String uiElementl, String uiElement2, String page) throws Exception {
		String elel = getEle(uiElementl, page).getText();
		String ele2 = getEle(uiElement2, page).getText();
		if (!elel.equals(ele2)) {
			Assert.assertTrue(!(elel.equals(ele2)), "Names are not updated");
		}
	}
	/* @author Trapti */

	public void clickOnMultipleEle(String uiElement, String page) throws Exception {
		List<MSWebElement> hideElel = common.getWebElementList(uiElement, page);
		System.out.println(hideElel.size());
		int e = hideElel.size();
		for (int i = 0; i < e; i++) {
			MSWebElement elell = hideElel.get(i);
			String ele111 = elell.getText();
			System.out.println(ele111);
			elell.click();
			Thread.sleep(5000);
		}
	}

	public void validateCountofRecords() {
		List<WebElement> gridRows = driver
				.findElements(By.xpath("//table[@id='Individual Reservations Grid']/tbody/tr"));
		int count = gridRows.size();
		String countl = Integer.toString(count);
		if (count == 0) {
			System.out.println("Grid is empty");
		}
		String totalcountView = driver.findElement(By.xpath("//*[@class='text1")).getText();
		String[] vals = totalcountView.split(" ");
		boolean result = countl.equals(vals[3].trim());
		Assert.assertTrue(result);
	}

	public void validateCovIeamMemberList(String uiElement, String id, String page) throws Exception {
		List<MSWebElement> uilist = getWebElementList(uiElement, page);
		List<String> nameList = new ArrayList<>();
		for (WebElement e : uilist) {
			String name = e.getText();
			nameList.add(name);
		}
		getEle(id, page).click();
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		executor.executeScript("window.scroll6y(0,450)", "");
		List<MSWebElement> uilist2 = getWebElementList(uiElement, page);

		System.out.println(uilist2.size());
		List<String> nameList2 = new ArrayList<>();
		for (WebElement el : uilist2) {
			String namel = el.getText();
			nameList2.add(namel);
		}
		boolean b = nameList2.equals(nameList);
		System.out.println(b);
		Assert.assertTrue(b, "team members are different");
	}

	public void validateDropdownOption(String Text, String Page) {
		String dropdcwnvalue = driver
				.findElement(
						By.xpath("//'[@id='View_UL']/Pqcontains(@id,'View')]//h2[contains(text(),'" + Text + " (')]"))
				.getText();
		boolean b = dropdcwnvalue.contains(Text);
		Assert.assertTrue(b, "data not correct");
	}

	
	
	/*
	 * public void ValidateStatus() throws Exception { MSWebElement elel =
	 * cs.getEle(status_ac); String name = ele1.getText();
	 * System.out.println("Status Under Asset Capture is : " + name); }
	 * 
	 * public void ValidateStage() throws Exception { MSWebElement elel =
	 * cs.getEle(stage_ac); String name = ele1.getText();
	 * System.out.println("Status Under Asset Capture is : " + name); }
	 */

	public void closeCurrentTab() throws Exception {
		Thread.sleep(1000);
		driver.switchTo().window(parentWindHandler);
		Robot robot = new Robot();
		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_W);
	}

}
