package com.ms.utilities;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import org.openqa.selenium.Point;
import org.openqa.selenium.Rectangle;
import org.openqa.selenium.TimeoutException;

public class MSWebElement extends RemoteWebElement implements WebElement {

	private WebDriver driver;
	private static Map<Object, String> mapFields = new HashMap<Object, String>();
	public WebElement ele;
	private By loc = null;

	/**
	 * * @author swapnil
	 * 
	 * @param driver
	 * @param loc
	 */

	public MSWebElement(WebDriver driver, By loc) {
		this.driver = driver;
		if (mapFields.size() <= 0) {
			getAllClasses();
		}
		this.loc = loc;
	}

	/**
	 * * @author swapnil
	 * 
	 * @param driver
	 * @param ele    .
	 */

	public MSWebElement(WebDriver driver, WebElement ele) {
		if (mapFields.size() <= 0) {
			getAllClasses();

		}
		this.driver = driver;
		this.ele = ele;
	}

	@Override
	public String toString() {
		if (loc != null) {
			return loc.toString();
		} else {
			return ele.toString();
		}
	}

	/** * @author advt. */
	private void getAllClasses() {
		File folder = new File("src//com//ms//pages");
		File[] files = folder.listFiles();
		for (File file : files) {
			try {
				Class<?> c = Class.forName("com.ms.pages." + file.getName().split(".java")[0]);
				c.getDeclaredFields();
				Object obj = c.newInstance();
				for (Field fld : c.getDeclaredFields()) {
					try {
						mapFields.put(fld.get(obj), fld.getName());
					} catch (Exception e) {
					}
				}
			} catch (Exception el) {
				el.printStackTrace();
			}

		}
	}

	private void setElement() {
		if (loc != null) {
			ele = findElement(loc);
		}
	}

	public WebElement getElement() {
		if (loc != null) {
			try {
				ele = findElement(loc);
			} catch (Exception e) {
				System.out.println(e);
			}
		}
		return ele;
	}

	@Override
	public <X> X getScreenshotAs(OutputType<X> arge) throws WebDriverException {
		setElement();
		return ele.getScreenshotAs(arge);
	}

	@Override
	public void clear() {
		setElement();
		try {
			new WebDriverWait(driver, 3).until(ExpectedConditions.elementToBeClickable(ele)).clear();
		} catch (Exception e) {
			throw new RuntimeException("Unable to clear " + customException());
		}
	}

	@Override
	public void click() {
		setElement();
		try {
			try {
				WebElement clickableEle = new WebDriverWait(driver, 5)
						.until(ExpectedConditions.elementToBeClickable(ele));
				clickableEle.click();
			} catch (Exception e) {
				JavascriptExecutor js = (JavascriptExecutor) driver;
				js.executeScript("arguments[0].scrollIntoView()", ele);
				WebElement clickableEle = new WebDriverWait(driver, 5)
						.until(ExpectedConditions.elementToBeClickable(ele));
				clickableEle.click();
			}
		} catch (Exception e) {
			throw new RuntimeException("Unable to click " + customException());
		}
	}

	@Override
	public WebElement findElement(By arg0) {
		if (!waitForJSandJQueryToLoad())
			throw new RuntimeException("Page not loaded after waititng for 30 seconds");
		try {
			ele = driver.findElement(arg0);
		} catch (Exception el) {
			if (mapFields.get(arg0) == null) {
				throw new RuntimeException("Unable to find element " + arg0);
			} else {
				// String[] fldName = mapFields.get(arg0).splitn?=[A-Z])");
				String[] fldName = mapFields.get(arg0).split("_");
				String error;
				try {
					error = "Unable to find " + fldName[1] + " " + fldName[0] + " on " + fldName[2] + " page";
				} catch (Exception e2) {
					error = mapFields.get(arg0);

				}
				throw new RuntimeException(error);
			}
		}
		return ele;
	}

	@Override
	public List<WebElement> findElements(By arg0) {
		return ele.findElements(arg0);
	}

	@Override
	public String getAttribute(String arg0) {
		setElement();
		return ele.getAttribute(arg0);

	}

	// @Override
	public String getCSSvalue(String arg0) {
		setElement();
		return ele.getCssValue(arg0);
	}

	@Override
	public Point getLocation() {
		setElement();
		return ele.getLocation();
	}

	@Override
	public Rectangle getRect() {
		setElement();
		return ele.getRect();
	}

	@Override
	public Dimension getSize() {
		setElement();
		return ele.getSize();
	}

	@Override
	public String getTagName() {
		setElement();
		return ele.getTagName();

	}

	@Override
	public String getText() {
		setElement();
		return ele.getText();
	}

	@Override
	public boolean isDisplayed() {
		setElement();
		return ele.isDisplayed();
	}

	@Override
	public boolean isEnabled() {
		setElement();
		return ele.isEnabled();

	}

	@Override
	public boolean isSelected() {
		setElement();
		return ele.isSelected();
	}

	@Override
	public void sendKeys(CharSequence... arg0) {
		setElement();
		try {
			ele.sendKeys(arg0);
		} catch (Exception weex) {
			try {
				new WebDriverWait(driver, 3).until(ExpectedConditions.visibilityOf(ele));
				ele.sendKeys(arg0);
			} catch (TimeoutException tiex) {
				ele.sendKeys(arg0);
			} catch (Exception e) {
				customException();
			}
		}
	}

	public void sendKeysAfterCLear(CharSequence... arg0) {
		setElement();
		try {
			WebElement eleNew = new WebDriverWait(driver, 3).until(ExpectedConditions.elementToBeClickable(ele));
			eleNew.clear();
			eleNew.sendKeys(arg0);
		} catch (Exception e) {
			throw new RuntimeException("Unable to set value " + arg0 + "on" + customException());
		}
	}

	@Override
	public void submit() {
		setElement();
		ele.submit();
	}

	public boolean isPresent() {
		try {
			setElement();
			WebDriverWait wait = new WebDriverWait(driver, 2);
			try {
				wait.until(ExpectedConditions.presenceOfElementLocated(loc));
			} catch (Exception e) {
				wait.until(ExpectedConditions.presenceOfElementLocated(loc));
			}

			return true;
		} catch (Exception e) {
			return false;
		}
	}

	private String customException() {
		Object arg0 = "Skey";
		String error;
		try {
			String[] locArray = ele.toString().split("->")[1].split(":");
			Method m = By.class.getDeclaredMethod(locArray[0].trim(), String.class);
			arg0 = m.invoke(ele, locArray[1].trim().replaceFirst(".$", ""));
		} catch (Exception e) {
			System.out.println(e);
		}
		if (mapFields.get(arg0) == null) {
			error = arg0.toString();
		} else {
			// String[] fldName = mapFields.get(arg0).split("(?=[A-2])");
			String[] fldName = mapFields.get(arg0).split("_");
			try {
				error = fldName[1] + " " + fldName[0] + " on " + fldName[2] + " page";
			} catch (Exception e2) {
				error = mapFields.get(arg0);
			}
		}
		return error;
	}

	public boolean waitForJSandJQueryToLoad() {
		WebDriverWait wait = new WebDriverWait(driver, 20);
		// wait for jQuery to load
		ExpectedCondition<Boolean> jQueryLoad = new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver driver) {
				try {
					return ((Long) ((JavascriptExecutor) driver).executeScript("return jQuery.active") == 0);
				} catch (Exception e) {
					// nc jQuery present return true; }
					return true;
				}
			}
		};
		ExpectedCondition<Boolean> jsLoad = new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver driver) {
				return ((JavascriptExecutor) driver).executeScript("return document.readyState").toString()
						.equals("complete");
			}
		};
		return wait.until(jQueryLoad) && wait.until(jsLoad);
	}

	public void higlight() {
		setElement();
		((JavascriptExecutor) driver).executeScript("arguments[0].style.border='3px solid red'", ele);
	}

	public void scrollToView() {
		setElement();
		((JavascriptExecutor) driver).executeScript("argument[0].scrollIntoView()", ele);
	}

	public void scrollTillEnd() {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollTo(0, document.body.scrollHeight)");

	}

	public WebElement findChildElement(By arg0) {
		if (!waitForJSandJQueryToLoad())
			throw new RuntimeException("Page not loaded after waititng for 30 seconds");
		try {
			ele = driver.findElement(arg0);
		} catch (Exception el) {
			if (mapFields.get(arg0) == null) {
				throw new RuntimeException("Unable to find element " + arg0);
			} else {
				// String[] fldName = mapFields.get(arg0).split("(?=[A-Z])");
				String[] fldName = mapFields.get(arg0).split("_");
				String error;
				try {
					error = "Unable to find " + fldName[1] + " " + fldName[0] + " on " + fldName[2] + " page";
				} catch (Exception e2) {
					error = mapFields.get(arg0);
				}
				throw new RuntimeException(error);
			}
		}
		return ele;
	}

	public void switchToFrame() {
		driver.switchTo().frame(ele);
	}

}
