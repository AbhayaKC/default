package common;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.text.DecimalFormat;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;

import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.StaleElementReferenceException;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DriverCommand;
import org.openqa.selenium.remote.RemoteExecuteMethod;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.hcsc.automation.framework.DataTable;
import com.hcsc.automation.framework.ExcelDataAccess;
import com.hcsc.automation.framework.FrameworkException;
import com.hcsc.automation.framework.Status;
import com.hcsc.automation.framework.Util;

import supportlibraries.ReusableLibrary;
import supportlibraries.ScriptHelper;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

import org.openqa.selenium.NoSuchWindowException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Action;

public class ActionComponents extends ReusableLibrary {

	public final int VERYSHORTWAIT = Integer.parseInt(properties.getProperty("VERYSHORTWAIT"));
	public final int SHORTWAIT = Integer.parseInt(properties.getProperty("SHORTWAIT"));
	public final int MEDIUMWAIT = Integer.parseInt(properties.getProperty("MEDIUMWAIT"));
	public final int LONGWAIT = Integer.parseInt(properties.getProperty("LONGWAIT"));
	public final int VERYLONGWAIT = Integer.parseInt(properties.getProperty("VERYLONGWAIT"));
	public final int IMPLICITWAIT = Integer.parseInt(properties.getProperty("IMPLICITWAIT"));
	protected final String tempDatatablePath = properties.getProperty("TEMP_DATA_FOLDER_PATH");
	protected final String tempDatatableName = properties.getProperty("TEMP_DATA_TABLE_NAME");
	protected final String tempDatasheetName = properties.getProperty("TEMP_DATA_SHEET_NAME");
	protected final String tempDataTestCase = properties.getProperty("TEMP_DATA_TEST_CASE_ID");

	public ActionComponents(ScriptHelper scriptHelper) {
		super(scriptHelper);
	}

	/**
	 * Constructor - Initializing Sync constructor to Invoke its methods
	 * 
	 * @param
	 */

	Sync sync = new Sync(scriptHelper);

	/**
	 * Method - Safe Method to launch the application by taking URL as input
	 * parameter
	 * 
	 * @author: Mahitha (I366760), Srikanth (I366759)
	 * @param URL String
	 */
	public void launchApplication(String sApplicationURL) {

		try {
			driver.get(sApplicationURL);// Opening URL
			Thread.sleep(2000);
			report.updateTestLog("Launching the Application", "is successfull", Status.PASS);

		} catch (Exception e) {
			report.updateTestLog("Launching the Application", "is not successfull" + e.getMessage(), Status.FAIL);
			throw new FrameworkException("Launching the Application", "is not successfull" + e.getMessage());
		}
	}

	/**
	 * Method - Safe Method for User Click, waits until the element is loaded and
	 * then performs a click action
	 * 
	 * @author: Mahitha (I366760), Srikanth (I366759)
	 * @param locator
	 * @param waitTime
	 * @param: reporting Reporting
	 */
	public void safeClick(By locator, Reporting reporting, int... optionWaitTime) {
		int waitTime = 0;

		waitTime = sync.getWaitTime(optionWaitTime);
		if (sync.waitUntilClickable(locator, waitTime)) {
			WebElement element = driver.findElement(locator);
			element.click();

			if (reporting.equals(Reporting.REPORTWITHSCREENSHOT)) {
				report.updateTestLog("Element is clicked ", "successfully", Status.PASS);
				report.updateTestLog("Element is clicked ", "Element : " + locator.toString(), Status.SCREENSHOT);
			} else if (reporting.equals(Reporting.REPORT))
				report.updateTestLog("Element is clicked ", "successfully", Status.PASS);
		} else {
			report.updateTestLog("Unable to find element", "Element :" + locator + "in time - " + waitTime + " Seconds",
					Status.FAIL);
			throw new FrameworkException("Unable to find element",
					"Element :" + locator + "in time - " + waitTime + " Seconds");
		}
	}

	/**
	 * Method- to enter text
	 * 
	 * @param value
	 * @param element
	 */
	public void enterText(By element, String value) {
		driver.findElement(element).clear();
		driver.findElement(element).sendKeys(value);
	}

	/**
	 * Method - Safe Method for User Double Click, waits until the element is loaded
	 * and then performs a double click action
	 * 
	 * @author:
	 * @param locator
	 * @param waitTime
	 * @param: reporting Reporting
	 */
	public void safeDblClick(By locator, Reporting reporting, int... optionWaitTime) {
		int waitTime = 0;

		waitTime = sync.getWaitTime(optionWaitTime);
		if (sync.waitUntilClickable(locator, waitTime)) {
			WebElement element = driver.findElement(locator);
			Actions userAction = new Actions(driver.getWebDriver()).doubleClick(element);
			userAction.build().perform();

			if (reporting.equals(Reporting.REPORTWITHSCREENSHOT)) {
				report.updateTestLog("Element is clicked ", "successfully", Status.PASS);
				report.updateTestLog("Element is clicked ", "Element  " + locator.toString(), Status.SCREENSHOT);
			}

			else if (reporting.equals(Reporting.REPORT))
				report.updateTestLog("Element is clicked ", "successfully", Status.PASS);
		}

		else {
			report.updateTestLog("Unable to find element", "Element :" + locator + "in time - " + waitTime + " Seconds",
					Status.FAIL);
			throw new FrameworkException("Unable to find element",
					"Element :" + locator + "in time - " + waitTime + " Seconds");
		}
	}

	/**
	 * Method - Safe Method for User Clear and Type, waits until the element is
	 * loaded and then enters some text
	 * 
	 * @author: Mahitha (I366760), Srikanth (I366759)
	 * @param locator
	 * @param sText
	 * @param waitTime
	 * @param: reporting Reporting
	 */
	public void safeClearAndType(By locator, String text, Reporting reporting, int... optionWaitTime) {

		int waitTime = 0;
		waitTime = sync.getWaitTime(optionWaitTime);
		if (sync.isElementPresent(locator, waitTime)) {

			WebElement element = driver.findElement(locator);
			element.clear();
			element.sendKeys(text);

			if (reporting.equals(Reporting.REPORTWITHSCREENSHOT)) {
				report.updateTestLog("Text entered successfully ", "Element " + locator.toString(), Status.PASS);
				report.updateTestLog("Text entered successfully ", "Element " + locator.toString(), Status.SCREENSHOT);
			} else if (reporting.equals(Reporting.REPORT))
				report.updateTestLog("Text entered successfully ", "Element " + locator.toString(), Status.PASS);
		} else {
			report.updateTestLog("Unable to find element",
					"Element :" + locator + " was not found in DOM in time - " + waitTime + " Seconds", Status.FAIL);
			throw new FrameworkException("Unable to find element",
					"Element :" + locator + " was not found in DOM in time - " + waitTime + " Seconds");
		}
	}

	/**
	 * Method - Safe Method for User Clear and Type, waits until the element is
	 * loaded and then enters some text
	 * 
	 * @author: Mahitha (I366760), Srikanth (I366759)
	 * @param locator
	 * @param sText
	 * @param waitTime
	 * @param: reporting Reporting
	 */
	public void ClearAndTypeValue(By locator, String text, Reporting reporting, int... optionWaitTime) {

		WebElement element = driver.findElement(locator);
		element.clear();
		element.sendKeys(text);

		if (reporting.equals(Reporting.REPORTWITHSCREENSHOT)) {
			report.updateTestLog("Text entered successfully ", "Element " + locator.toString(), Status.PASS);
			report.updateTestLog("Text entered successfully ", "Element " + locator.toString(), Status.SCREENSHOT);
		}

		else if (reporting.equals(Reporting.REPORT))
			report.updateTestLog("Text entered successfully ", "Element " + locator.toString(), Status.PASS);
	}

	/**
	 * 
	 * TODO scroll method to scroll the page down until expected element is visible
	 * *
	 * 
	 * @author: Mahitha (I366760), Srikanth (I366759)
	 * @param locator  - locator value by which an element is located
	 * @param waitTime - Time to wait for an element
	 * @return - returns the text value from element
	 * @param: reporting Reporting
	 */
	public void scrollIntoElementView(By locator, Reporting reporting) {

		((JavascriptExecutor) driver.getWebDriver()).executeScript("arguments[0].scrollIntoView();",
				driver.findElement(locator));

		if (reporting.equals(Reporting.REPORTWITHSCREENSHOT)) {
			report.updateTestLog("Scrolled the View to Element", "Element " + locator.toString(), Status.PASS);
			report.updateTestLog("Scrolled the View to Element", "Element " + locator.toString(), Status.SCREENSHOT);
		}

		else if (reporting.equals(Reporting.REPORT))
			report.updateTestLog("Scrolled the View to Element", "Element " + locator.toString(), Status.PASS);
	}

	/**
	 * Method - Safe Method for User Click using Actions.click, waits until the
	 * element is loaded and then performs a click action
	 * 
	 * @author: Mahitha (I366760), Srikanth (I366759)
	 * @param locator
	 * @param waitTime
	 * @param: reporting Reporting
	 */

	public void safeActionsClick(By locator, Reporting reporting, int waitTime) {

		if (sync.isElementVisible(locator, waitTime)) {
			WebElement element = driver.findElement(locator);
			setHighlight(element);
			Actions builder = new Actions(driver.getWebDriver());
			builder.moveToElement(element).click().build().perform();

			if (reporting.equals(Reporting.REPORTWITHSCREENSHOT)) {
				report.updateTestLog("Element is clicked ", "successful", Status.PASS);
				report.updateTestLog("Element is clicked ", "Element  " + locator.toString(), Status.SCREENSHOT);
			} else if (reporting.equals(Reporting.REPORT))
				report.updateTestLog("Element is clicked ", "successful", Status.PASS);

		} else {
			// report.updateTestLog("Element","Element :"+ locator + " was not
			// visible to click in time - "+waitTime+" Seconds", Status.FAIL);
			report.updateTestLog("Element was not visible to click in time",
					"Element :" + locator + waitTime + " Seconds", Status.FAIL);
			throw new FrameworkException("Element was not visible to click in time",
					"Element :" + locator + waitTime + " Seconds");
		}
	}

	/**
	 * Method - Safe Method for User Type, waits until the element is loaded and
	 * then enters some text
	 * 
	 * @author: Mahitha (I366760), Srikanth (I366759)
	 * @param locator
	 * @param sText
	 * @param waitTime
	 * @param: reporting Reporting
	 */
	public void safeType(By locator, String text, Reporting reporting, int... optionWaitTime) {

		int waitTime = 0;

		waitTime = sync.getWaitTime(optionWaitTime);
		if (sync.isElementPresent(locator, waitTime)) {

			WebElement element = driver.findElement(locator);
			setHighlight(element);
			element.sendKeys(text);
			if (reporting.equals(Reporting.REPORTWITHSCREENSHOT)) {
				report.updateTestLog("Text entered successfully ", "in field " + locator.toString(), Status.PASS);
				report.updateTestLog("Text entered successfully ", "in field " + locator.toString(), Status.SCREENSHOT);
			}

			else if (reporting.equals(Reporting.REPORT))
				report.updateTestLog("Text entered successfully ", "in field " + locator.toString(), Status.PASS);

		} else {
			// report.updateTestLog("Unable to enter","Unable to enter text in field"+
			// locator + "in time - "+waitTime+" Seconds", Status.FAIL);
			report.updateTestLog("Unable to enter the text in the field", "Element :" + locator + waitTime + " Seconds",
					Status.FAIL);
			throw new FrameworkException("Unable to enter the text in the field",
					"Element :" + locator + waitTime + " Seconds");
		}
	}

	/**
	 * Method - Safe Method for Radio button selection, waits until the element is
	 * loaded and then selects Radio button
	 * 
	 * @author: Mahitha (I366760), Srikanth (I366759)i366760
	 * @param locator
	 * @param waitTime
	 * @param: reporting Reporting
	 */
	public void safeSelectRadioButton(By locator, Reporting reporting, int... optionWaitTime) {

		int waitTime = 0;

		waitTime = sync.getWaitTime(optionWaitTime);
		if (sync.waitUntilClickable(locator, waitTime)) {

			WebElement element = driver.findElement(locator);
			setHighlight(element);
			element.click();

			if (reporting.equals(Reporting.REPORTWITHSCREENSHOT)) {
				report.updateTestLog("Clicked on radio button ", "with locator" + locator.toString(), Status.PASS);
				report.updateTestLog("Clicked on radio button with locator", locator.toString(), Status.SCREENSHOT);
			} else if (reporting.equals(Reporting.REPORT))
				report.updateTestLog("Clicked on radio button ", "with locator" + locator.toString(), Status.PASS);

		} else {
			report.updateTestLog("Failed to select Radio button",
					"Unable to select Radio button :" + locator + waitTime + " Seconds", Status.FAIL);
			throw new FrameworkException("Failed to select Radio button",
					"Unable to select Radio button :" + locator + waitTime + " Seconds");
		}
	}

	/**
	 * Method - Safe Method for check box selection, waits until the element is
	 * loaded and then selects checkbox
	 * 
	 * @author: Mahitha (I366760), Srikanth (I366759)i366760
	 * @param locator
	 * @param waitTime
	 * @param: reporting Reporting
	 */
	public void safeCheck(By locator, Reporting reporting, int... optionWaitTime) {

		int waitTime = 0;
		waitTime = sync.getWaitTime(optionWaitTime);
		if (sync.isElementPresent(locator, waitTime)) {
			WebElement checkBox = driver.findElement(locator);
			setHighlight(checkBox);
			if (checkBox.isSelected())
				report.updateTestLog("CheckBox is already selected", " for" + locator.toString(), Status.WARNING);

			else {
				checkBox.click();
				if (reporting.equals(Reporting.REPORTWITHSCREENSHOT)) {
					report.updateTestLog("Checkbox is selected ", " for" + locator.toString(), Status.PASS);
					report.updateTestLog("Checkbox is selected ", " for" + locator.toString(), Status.SCREENSHOT);
				} else if (reporting.equals(Reporting.REPORT))
					report.updateTestLog("Checkbox is selected ", " for" + locator.toString(), Status.PASS);
			}
		} else {
			report.updateTestLog("Failed to select CheckBox",
					"Unable to select CheckBox :in time - " + locator + waitTime + " Seconds", Status.FAIL);
			throw new FrameworkException("Failed to select CheckBox",
					"Unable to select CheckBox :in time - " + locator + waitTime + " Seconds");
		}
	}

	/**
	 * Method - Safe Method for checkbox deselection, waits until the element is
	 * loaded and then deselects checkbox
	 * 
	 * @author: Mahitha (I366760), Srikanth (I366759)i366760
	 * @param locator
	 * @param waitTime
	 * @return - boolean (returns True when the checkbox is deselected else returns
	 *         false)
	 * @param: reporting Reporting
	 */
	public void safeUnCheck(By locator, Reporting reporting, int... optionWaitTime) {

		int waitTime = 0;
		waitTime = sync.getWaitTime(optionWaitTime);
		if (sync.isElementPresent(locator, waitTime)) {

			WebElement checkBox = driver.findElement(locator);
			setHighlight(checkBox);
			if (checkBox.isSelected()) {
				checkBox.click();
				if (reporting.equals(Reporting.REPORTWITHSCREENSHOT)) {
					report.updateTestLog("CheckBox is deselected ", "for" + locator.toString(), Status.PASS);
					report.updateTestLog("CheckBox is deselected ", "for" + locator.toString(), Status.SCREENSHOT);
				} else if (reporting.equals(Reporting.REPORT))
					report.updateTestLog("CheckBox is deselected ", "for" + locator.toString(), Status.PASS);
			} else
				report.updateTestLog("CheckBox is already deselected", " for" + locator.toString(), Status.PASS);
		} else {
			report.updateTestLog("Failed to deselect CheckBox",
					"Unable to find the locator in time - " + locator + waitTime + " Seconds", Status.FAIL);
			throw new FrameworkException("Failed to deselect CheckBox",
					"Unable to find the locator in time - " + locator + waitTime + " Seconds");
		}
	}

	/**
	 * Method - Safe Method for checkbox Selection or Deselection based on user
	 * input, waits until the element is loaded and then deselects/selects checkbox
	 * 
	 * @author: Mahitha (I366760), Srikanth (I366759)i366760
	 * @param locator
	 * @param checkOption
	 * @param waitTime
	 * @throws Exception
	 * @param: reporting Reporting
	 */
	public void safeCheckByOption(By locator, boolean checkOption, Reporting reporting, int... optionWaitTime) {

		int waitTime = 0;
		waitTime = sync.getWaitTime(optionWaitTime);
		if (sync.isElementPresent(locator, waitTime)) {

			WebElement checkBox = driver.findElement(locator);
			setHighlight(checkBox);
			if ((checkBox.isSelected() == true && checkOption == false)
					|| (checkBox.isSelected() == false && checkOption == true)) {
				checkBox.click();
				if (reporting.equals(Reporting.REPORTWITHSCREENSHOT)) {
					report.updateTestLog("CheckBox is selected or deselected ", "for " + locator.toString(),
							Status.PASS);
					report.updateTestLog("CheckBox is selected or deselected ", "for " + locator.toString(),
							Status.SCREENSHOT);
				} else if (reporting.equals(Reporting.REPORT))
					report.updateTestLog("CheckBox is selected or deselected ", "for" + locator.toString(),
							Status.PASS);
			} else {
				report.updateTestLog("CheckBox is already deselected ", "for" + locator.toString(), Status.SCREENSHOT);
			}
		} else {
			report.updateTestLog("Failed to select or deselect the CheckBox",
					"Unable to find in  Seconds to select/deselect - " + locator + +waitTime, Status.FAIL);
			throw new FrameworkException("Failed to select or deselect the CheckBox",
					"Unable to find in  Seconds to select/deselect - " + locator + +waitTime);

		}

	}

	/**
	 * Method - Safe Method for getting checkbox value, waits until the element is
	 * loaded
	 * 
	 * @author: Mahitha (I366760), Srikanth (I366759)
	 * @param locator
	 * @param checkOption
	 * @param waitTime
	 * @return - boolean (returns True when the checkbox is enabled else returns
	 *         false)
	 * @throws Exception
	 * @param: reporting Reporting
	 */
	public boolean safeGetCheckboxValue(By locator, Reporting reporting, int... optionWaitTime) {

		int waitTime = 0;
		boolean isSelected = false;
		waitTime = sync.getWaitTime(optionWaitTime);
		if (sync.isElementPresent(locator, waitTime)) {

			WebElement checkBox = driver.findElement(locator);
			setHighlight(checkBox);
			if (checkBox.isSelected()) {
				isSelected = true;
				if (reporting.equals(Reporting.REPORTWITHSCREENSHOT)) {
					report.updateTestLog("CheckBox is selected", "Element " + locator.toString(), Status.PASS);
					report.updateTestLog("CheckBox is selected", "Element " + locator.toString(), Status.SCREENSHOT);
				} else if (reporting.equals(Reporting.REPORT))
					report.updateTestLog("CheckBox is selected", "Element " + locator.toString(), Status.PASS);
			}
		} else {
			report.updateTestLog("Unable to get the status of checkbox",
					"Unable to find in  status in time for locator in Seconds" + locator + waitTime, Status.FAIL);
			throw new FrameworkException("Unable to get the status of checkbox",
					"Unable to find in  status in time for locator in Seconds" + locator + waitTime);
		}
		return isSelected;
	}

	/**
	 * Purpose- For selecting multiple check boxes at a time
	 * 
	 * @param waitTime
	 * @param locator
	 * @throws Exception
	 * @functionCall - SelectMultipleCheckboxs(MEDIUMWAIT,
	 *               By.id("Checkbox1"),By.id("Checkbox2"), By.xpath("checkbox")); u
	 *               can pass 'N' number of locators at a time
	 */
	public void selectCheckboxes(int waitTime, Reporting reporting, By... locator) throws Exception {
		// By check = null;

		if (locator.length > 0) {
			for (By currentLocator : locator) {
				// check = currentLocator;
				sync.waitUntilClickable(currentLocator, waitTime);
				WebElement checkBox = driver.findElement(currentLocator);
				setHighlight(checkBox);
				if (checkBox.isSelected())

				{
					if (reporting.equals(Reporting.REPORTWITHSCREENSHOT)) {

						report.updateTestLog("CheckBox is already selected", " Element " + locator.toString(),
								Status.WARNING);
						report.updateTestLog("CheckBox is already selected", " Element " + locator.toString(),
								Status.SCREENSHOT);
					}

					else if (reporting.equals(Reporting.REPORT))
						report.updateTestLog("CheckBox is already selected", " Element " + locator.toString(),
								Status.WARNING);
				} else {
					checkBox.click();
					if (reporting.equals(Reporting.REPORTWITHSCREENSHOT)) {

						report.updateTestLog("CheckBox is selected", " Element " + locator.toString(), Status.PASS);
						report.updateTestLog("CheckBox is selected ", "Element " + locator.toString(),
								Status.SCREENSHOT);
					}

					else if (reporting.equals(Reporting.REPORT))
						report.updateTestLog("CheckBox is selected ", "Element " + locator.toString(), Status.PASS);
				}
			}
		} else {
			report.updateTestLog("Expected atleast one locator as argument to safeSelectCheckboxes function",
					"No Locator has been provided", Status.FAIL);
			throw new FrameworkException("Expected atleast one locator as argument to safeSelectCheckboxes function",
					"No Locator has been provided");
		}
	}

	/**
	 * Purpose- For deselecting multiple check boxes at a time
	 * 
	 * @param waitTime
	 * @param locator
	 * @throws Exception
	 * @functionCall - DeselectMultipleCheckboxs(MEDIUMWAIT,
	 *               By.id("Checkbox1"),By.id("Checkbox2"), By.xpath("checkbox")); u
	 *               can pass 'N' number of locators at a time
	 */
	public void deselectCheckboxes(int waitTime, Reporting reporting, By... locator) {
		// By check = null;

		if (locator.length > 0) {
			for (By currentLocator : locator) {
				// check = currentLocator;
				sync.waitUntilClickable(currentLocator, waitTime);
				WebElement checkBox = driver.findElement(currentLocator);
				setHighlight(checkBox);
				if (checkBox.isSelected()) {
					checkBox.click();

					if (reporting.equals(Reporting.REPORTWITHSCREENSHOT)) {

						report.updateTestLog("CheckBox is deselected", "Element " + locator.toString(), Status.PASS);
						report.updateTestLog("CheckBox is deselected", "Element " + locator.toString(),
								Status.SCREENSHOT);
					}

					else if (reporting.equals(Reporting.REPORT))
						report.updateTestLog("CheckBox is deselected ", "Element " + locator.toString(), Status.PASS);

				} else {

					if (reporting.equals(Reporting.REPORTWITHSCREENSHOT)) {

						report.updateTestLog("CheckBox is already deselected", "Element " + locator.toString(),
								Status.WARNING);
						report.updateTestLog("CheckBox is already deselected", "Element " + locator.toString(),
								Status.SCREENSHOT);
					}

					else if (reporting.equals(Reporting.REPORT))
						report.updateTestLog("CheckBox is already deselected", "Element " + locator.toString(),
								Status.PASS);

				}
			}
		}

		else {
			report.updateTestLog("Expected atleast one locator as argument to safeDeselectCheckboxes function",
					"No Locator has been provided", Status.FAIL);
			throw new FrameworkException("Expected atleast one locator as argument to safeDeselectCheckboxes function",
					"No Locator has been provided");
		}
	}

	/**
	 * Method - Safe Method for User Select option from Drop down by option index,
	 * waits until the element is loaded and then selects an option from drop down
	 * 
	 * @param locator
	 * @param iIndexofOptionToSelect
	 * @param waitTime
	 * @return - boolean (returns True when option is selected from the drop down
	 *         else returns false)
	 * @throws Exception
	 */
	public void selectOptionByIndexValue(By locator, Reporting reporting, int iIndexofOptionToSelect,
			int... optionWaitTime) {

		int waitTime = 0;
		waitTime = sync.getWaitTime(optionWaitTime);
		if (sync.isElementPresent(locator, waitTime)) {
			WebElement selectElement = driver.findElement(locator);
			setHighlight(selectElement);
			Select select = new Select(selectElement);
			select.selectByIndex(iIndexofOptionToSelect);

			if (reporting.equals(Reporting.REPORTWITHSCREENSHOT)) {

				report.updateTestLog("Selected dropdown item by index option", "Locator :" + iIndexofOptionToSelect,
						Status.PASS);
				report.updateTestLog("Selected dropdown item by index option", "Locator :" + iIndexofOptionToSelect,
						Status.SCREENSHOT);
			}

			else if (reporting.equals(Reporting.REPORT))
				report.updateTestLog("Selected dropdown item by index option", "Locator :" + iIndexofOptionToSelect,
						Status.PASS);
		} else {
			report.updateTestLog("Unable to select dropdown item by index option", "Locator :" + iIndexofOptionToSelect,
					Status.FAIL);
			throw new FrameworkException("Unable to select dropdown item by index option",
					"Locator :" + iIndexofOptionToSelect);
		}

	}

	/**
	 * Method - Safe Method for User Select option from Drop down by option value,
	 * waits until the element is loaded and then selects an option from drop down
	 * 
	 * @param locator
	 * @param sValueOfOptionToSelect
	 * @param waitTime
	 * @param: reporting Reporting
	 * @return - boolean (returns True when option is selected from the drop down
	 *         else returns false)
	 * @throws Exception
	 */
	public void selectOptionByValue(By locator, Reporting reporting, String sValuefOptionToSelect,
			int... optionWaitTime) {

		int waitTime = 0;
		waitTime = sync.getWaitTime(optionWaitTime);
		if (sync.isElementPresent(locator, waitTime)) {
			WebElement selectElement = driver.findElement(locator);
			setHighlight(selectElement);
			Select select = new Select(selectElement);
			select.selectByValue(sValuefOptionToSelect);
			if (reporting.equals(Reporting.REPORTWITHSCREENSHOT)) {

				report.updateTestLog("Selected dropdown item by value option", "Locator :" + sValuefOptionToSelect,
						Status.PASS);
				report.updateTestLog("Selected dropdown item by value option", "Locator :" + sValuefOptionToSelect,
						Status.SCREENSHOT);
			}

			else if (reporting.equals(Reporting.REPORT))
				report.updateTestLog("Selected dropdown item by value option", "Locator :" + sValuefOptionToSelect,
						Status.PASS);
		} else {
			report.updateTestLog("Selected dropdown item by value option", "Locator :" + sValuefOptionToSelect,
					Status.FAIL);
			throw new FrameworkException("Selected dropdown item by value option", "Locator :" + sValuefOptionToSelect);
		}

	}

	/**
	 * Method - Safe Method for User Select option from Drop down by option label,
	 * waits until the element is loaded and then selects an option from drop down
	 * 
	 * @param locator
	 * @param sVisibleTextOptionToSelect
	 * @param waitTime
	 * @param: reporting Reporting
	 * @return - boolean (returns True when option is selected from the drop down
	 *         else returns false)
	 * @throws Exception
	 */
	public void selectOptionByVisibleText(By locator, Reporting reporting, String sVisibleTextOptionToSelect,
			int... optionWaitTime) {
		int waitTime = 0;

		waitTime = sync.getWaitTime(optionWaitTime);
		if (sync.isElementPresent(locator, waitTime)) {
			WebElement selectElement = driver.findElement(locator);
			setHighlight(selectElement);
			Select select = new Select(selectElement);
			select.selectByVisibleText(sVisibleTextOptionToSelect);

			if (reporting.equals(Reporting.REPORTWITHSCREENSHOT)) {

				report.updateTestLog("Selected dropdown item by visible text option",
						"Locator :" + sVisibleTextOptionToSelect, Status.PASS);
				report.updateTestLog("Selected dropdown item by visible text option",
						"Locator :" + sVisibleTextOptionToSelect, Status.SCREENSHOT);
			}

			else if (reporting.equals(Reporting.REPORT))
				report.updateTestLog("Selected dropdown item by visible text option",
						"Locator :" + sVisibleTextOptionToSelect, Status.PASS);
		} else {
			report.updateTestLog("Selected dropdown item by visible text option",
					"Locator :" + sVisibleTextOptionToSelect, Status.FAIL);
			throw new FrameworkException("Selected dropdown item by visible text option",
					"Locator :" + sVisibleTextOptionToSelect);
		}
	}

	public List<WebElement> getAllOptionsOfDropdown(By locator, Reporting reporting, int... optionWaitTime) {
		int waitTime = 0;
		waitTime = sync.getWaitTime(optionWaitTime);
		List<WebElement> options = Collections.<WebElement>emptyList();
		if (sync.isElementPresent(locator, waitTime)) {
			// First, get the WebElement for the select tag
			if (reporting.equals(Reporting.REPORTWITHSCREENSHOT)) {

				report.updateTestLog("Dropdown", "Locator :" + locator, Status.PASS);
				report.updateTestLog("Dropdown", "Locator :" + locator, Status.SCREENSHOT);
			}

			else if (reporting.equals(Reporting.REPORT))
				report.updateTestLog("Dropdown", "Locator :" + locator, Status.PASS);

			WebElement selectElement = driver.findElement(locator);
			setHighlight(selectElement);
			// Then instantiate the Select class with that WebElement
			Select select = new Select(selectElement);
			// Get a list of the options
			options = select.getOptions();
			return options;
		} else {
			report.updateTestLog("Dropdown with locator is not displayed", " Dropdown is not present", Status.FAIL);
			throw new FrameworkException("Dropdown with locator is not displayed", "Dropdown is not present");
		}
	}

	/**
	 * Method - Safe Method for User Select option from list menu, waits until the
	 * element is loaded and then selects an option from list menu
	 * 
	 * @param locator
	 * @param sOptionToSelect
	 * @param waitTime
	 * @param: reporting Reporting
	 * @throws Exception
	 */
	public void selectListBox(By locator, Reporting reporting, String sOptionToSelect, int... optionWaitTime) {

		int waitTime = 0;
		waitTime = sync.getWaitTime(optionWaitTime);
		List<WebElement> options = Collections.<WebElement>emptyList();
		if (sync.isElementPresent(locator, waitTime)) {
			// First, get the WebElement for the select tag
			WebElement selectElement = driver.findElement(locator);
			setHighlight(selectElement);
			// Then instantiate the Select class with that WebElement
			Select select = new Select(selectElement);
			// Get a list of the options
			options = select.getOptions();
			if (!options.isEmpty()) {
				boolean bExists = false;
				// For each option in the list, verify if it's the one you want
				// and then click it
				for (WebElement option : options) {
					if (option.getText().contains(sOptionToSelect)) {
						option.click();
						bExists = true;
						if (reporting.equals(Reporting.REPORTWITHSCREENSHOT)) {

							report.updateTestLog("Selected Listbox item", "from Listbox:" + sOptionToSelect + locator,
									Status.PASS);

							report.updateTestLog("Selected Listbox item", "from Listbox:" + sOptionToSelect + locator,
									Status.SCREENSHOT);

						}

						else if (reporting.equals(Reporting.REPORT))
							report.updateTestLog("Selected Listbox item", "from Listbox:" + sOptionToSelect + locator,
									Status.PASS);
						break;
					}
				}
				if (!bExists) {
					report.updateTestLog("Unable to select from locator",
							" from  List Box:" + sOptionToSelect + locator, Status.FAIL);
				}
			}
		} else {
			report.updateTestLog("List box with locator ", "is not displayed" + sOptionToSelect, Status.FAIL);
			throw new FrameworkException("List box with locator ", "is not displayed" + sOptionToSelect);
		}
	}

	/**
	 * Method - Method to hover on an element based on locator using Actions,it
	 * waits until the element is loaded and then hovers on the element
	 * 
	 * @param locator
	 * @param waitTime
	 * @param: reporting Reporting
	 * @throws Exception
	 */
	public void mouseHover(By locator, Reporting reporting, int waitTime) {

		if (sync.isElementVisible(locator, waitTime)) {
			Actions builder = new Actions(driver.getWebDriver());
			WebElement HoverElement = driver.findElement(locator);
			builder.moveToElement(HoverElement).build().perform();
			if (reporting.equals(Reporting.REPORTWITHSCREENSHOT)) {

				report.updateTestLog("Hovered on element", "Locator :" + locator, Status.PASS);
				report.updateTestLog("Hovered on element", "Locator :" + locator, Status.SCREENSHOT);
			}

			else if (reporting.equals(Reporting.REPORT))
				report.updateTestLog("Hovered on element", "Locator :" + locator, Status.PASS);

		} else {
			report.updateTestLog("Element was not visible to hover", "Locator :" + locator, Status.FAIL);
			throw new FrameworkException("Element was not visible to hover", "Locator :" + locator);
		}
	}

	/**
	 * Method - Method to hover on an element based on locator using Actions and
	 * click on given option,it waits until the element is loaded and then hovers on
	 * the element
	 * 
	 * @param locator
	 * @param waitTime
	 * @param: reporting Reporting
	 * @throws Exception
	 */
	public void mouseHoverAndSelectOption(By locator, By byOptionlocator, Reporting reporting, int waitTime) {

		if (sync.isElementPresent(locator, waitTime)) {
			Actions builder = new Actions(driver.getWebDriver());
			WebElement HoverElement = driver.findElement(locator);
			builder.moveToElement(HoverElement).build().perform();
			try {
				builder.wait(4000);
			} catch (InterruptedException e) {
				report.updateTestLog("Exception occurred while waiting", "Locator :" + locator, Status.FAIL);
			}
			WebElement element = driver.findElement(byOptionlocator);
			element.click();
			if (reporting.equals(Reporting.REPORTWITHSCREENSHOT)) {

				report.updateTestLog("Hovered on element and selected the Option", "Locator :" + locator, Status.PASS);
				report.updateTestLog("Hovered on element and selected the Option", "Locator :" + locator,
						Status.SCREENSHOT);
			}

			else if (reporting.equals(Reporting.REPORT))
				report.updateTestLog("Hovered on element and selected the Option", "Locator :" + locator, Status.PASS);
		} else {

			report.updateTestLog("Element was not visible to hover ", "Locator :" + locator, Status.FAIL);
			throw new FrameworkException("Element was not visible to hover ", "Locator :" + locator);
		}
	}

	/**
	 * Method - Method to hover on an element based on locator using JavaScript
	 * snippet,it waits until the element is loaded and then hovers on the element
	 * 
	 * @param locator
	 * @param Choice
	 * @param waitTime
	 * @param: reporting Reporting
	 * @throws Exception
	 */
	public void mouseHoverJScript(By locator, String Choice, Reporting reporting, int waitTime) {
		if (sync.isElementPresent(locator, waitTime)) {
			WebElement HoverElement = driver.findElement(locator);
			String mouseOverScript = "if(document.createEvent){var evObj = document.createEvent('MouseEvents');evObj.initEvent('mouseover', true, false); arguments[0].dispatchEvent(evObj);} else if(document.createEventObject) { arguments[0].fireEvent('onmouseover');}";
			((JavascriptExecutor) driver.getWebDriver()).executeScript(mouseOverScript, HoverElement);
			if (reporting.equals(Reporting.REPORTWITHSCREENSHOT)) {

				report.updateTestLog("Hovered on element and selected the choice",
						"Locator :" + locator + "," + "choice :" + Choice, Status.PASS);
				report.updateTestLog("Hovered on element and selected the choice",
						"Locator :" + locator + "," + "choice :" + Choice, Status.SCREENSHOT);
			}

			else if (reporting.equals(Reporting.REPORT))
				report.updateTestLog("Hovered on element and selected the choice", "Locator :" + locator, Status.PASS);
		} else {

			report.updateTestLog("Element was not visible to hover", "Locator :" + locator, Status.FAIL);
			throw new FrameworkException("Element was not visible to hover", "Locator :" + locator);
		}
	}

	/**
	 * Method - Safe Method for User Click, waits until the element is loaded and
	 * then performs a click action
	 * 
	 * @param locatorToClick
	 * @param locatorToCheck
	 * @param waitTime
	 * @param: reporting Reporting
	 * @return - boolean (returns True when click action is performed else returns
	 *         false)
	 * @throws Exception
	 */
	public void safeClick(By locatorToClick, By locatorToCheck, Reporting reporting, int waitElementToClick,
			int waitElementToCheck) throws Exception {

		boolean bResult = false;
		int iAttempts = 0;
		sync.nullifyImplicitWait();
		WebDriverWait wait = new WebDriverWait(driver.getWebDriver(), waitElementToClick);
		WebDriverWait wait2 = new WebDriverWait(driver.getWebDriver(), waitElementToCheck);
		while (iAttempts < 3) {
			// reporting.equals(Reporting.REPORT)
			wait.until(ExpectedConditions.visibilityOfElementLocated(locatorToClick));
			wait.until(ExpectedConditions.elementToBeClickable(locatorToClick));
			WebElement element = driver.findElement(locatorToClick);

			if (element.isDisplayed()) {
				setHighlight(element);
				element.click();
				sync.waitForPageToLoad();
				sync.waitForJQueryProcessing(waitElementToCheck);
				wait2.until(ExpectedConditions.visibilityOfElementLocated(locatorToCheck));
				WebElement elementToCheck = driver.findElement(locatorToCheck);
				if (elementToCheck.isDisplayed()) {

					if (reporting.equals(Reporting.REPORTWITHSCREENSHOT)) {

						report.updateTestLog("Clicked on element ",
								"Locator :" + locatorToClick + "," + "LocatorToCheck :" + locatorToCheck, Status.PASS);
						report.updateTestLog("Clicked on element ",
								"Locator :" + locatorToClick + "," + "LocatorToCheck :" + locatorToCheck,
								Status.SCREENSHOT);
					}

					else if (reporting.equals(Reporting.REPORT))
						report.updateTestLog("Clicked on element ",
								"Locator :" + locatorToClick + "," + "LocatorToCheck :" + locatorToCheck, Status.PASS);
					break;
				} else {
					driverUtil.waitFor(1000);
					continue;
				}
			}
			iAttempts++;
		}
		if (!bResult) {
			report.updateTestLog("Unable to click on element ", "LocatorToClick : " + locatorToClick, Status.FAIL);

		}
	}

	/**
	 * Purpose- Method For performing drag and drop operations
	 * 
	 * @param Sourcelocator,Destinationlocator
	 * @param waitTime
	 * @throws Exception
	 * @param: reporting Reporting
	 * @function_call - eg: DragAndDrop(By.id(Sourcelocator),
	 *                By.xpath(Destinationlocator), "MEDIUMWAIT");
	 */
	public void dragAndDrop(By Sourcelocator, By Destinationlocator, Reporting reporting, int waitTime) {

		if (sync.isElementPresent(Sourcelocator, waitTime)) {
			WebElement source = driver.findElement(Sourcelocator);
			if (sync.isElementPresent(Destinationlocator, waitTime)) {
				WebElement destination = driver.findElement(Destinationlocator);
				Actions action = new Actions(driver.getWebDriver());
				action.dragAndDrop(source, destination).build().perform();

				if (reporting.equals(Reporting.REPORTWITHSCREENSHOT)) {

					report.updateTestLog("Dragged the element and dropped into Destinationlocator",
							"Sourcelocator :" + Sourcelocator + "\n Destinationlocator:" + Destinationlocator,
							Status.PASS);
					report.updateTestLog("Dragged the element and dropped into Destinationlocator",
							"Sourcelocator :" + Sourcelocator + "\n Destinationlocator:" + Destinationlocator,
							Status.SCREENSHOT);
				}

				else if (reporting.equals(Reporting.REPORT))
					report.updateTestLog("Dragged the element and dropped into Destinationlocator",
							"Sourcelocator :" + Sourcelocator + "\n Destinationlocator:" + Destinationlocator,
							Status.PASS);
			} else {
				report.updateTestLog("Destination Element with locator was not displayed to drop",
						"Destinationlocator :" + Destinationlocator, Status.FAIL);
			}
		} else {
			report.updateTestLog("Source Element with locator was not displayed to drag",
					"Sourcelocator :" + Sourcelocator, Status.FAIL);
			throw new FrameworkException("Source Element with locator was not displayed to drag",
					"Sourcelocator :" + Sourcelocator);
		}
	}

	/**
	 * 
	 * Purpose- Method For waiting for an alert and accepting it
	 *
	 * @param waitTime
	 * @param: reporting Reporting
	 * @return returns true if alert is displayed and accepted, else returns false
	 */
	public boolean alertWaitAndAccepted(int waitTime, Reporting reporting) {

		boolean bAlert = false;

		if (sync.isAlertPresent(waitTime)) {
			Alert alert = driver.switchTo().alert();
			driverUtil.waitFor(2000);
			alert.accept();

			if (reporting.equals(Reporting.REPORTWITHSCREENSHOT)) {

				report.updateTestLog("Alert is displayed ", "Accepted successfully", Status.PASS);
				report.updateTestLog("Alert is displayed ", "Accepted successfully", Status.SCREENSHOT);
			}

			else if (reporting.equals(Reporting.REPORT))
				report.updateTestLog("Alert is displayed ", "Accepted successfully", Status.PASS);
			bAlert = true;
		} else {
			report.updateTestLog("Alert not present in time", waitTime + "- NoAlertPresentException", Status.FAIL);
			bAlert = false;
			throw new FrameworkException("Alert not present in time", waitTime + "- NoAlertPresentException");
		}
		return bAlert;
	}

	/**
	 * Method: for verifying if accept exists and accepting the alert
	 * 
	 * @throws Exception
	 */
	public void acceptAlert() {
		Alert alert = driver.switchTo().alert();
		String sText = alert.getText();
		alert.accept();
		report.updateTestLog("Accepted the alert", "Text present on Alert :" + sText, Status.PASS);
	}

	/**
	 * Method: for verifying if accept exists and accepting the alert
	 * 
	 * @return - String (returns text present on the Alert)
	 * @throws Exception
	 */
	public String getAlertMessage() {
		String sText = null;
		Alert alert = driver.switchTo().alert();
		sText = alert.getText();
		report.updateTestLog("Text present in the alert", "Value :" + sText, Status.PASS);
		return sText;
	}

	/**
	 * Method: for verifying if accept exists and rejecting/dismissing the alert
	 * 
	 * @return - boolean (returns True when dismiss action is performed else returns
	 *         false)
	 * @throws Exception
	 */
	public void dismissAlert() throws Exception {
		Alert alert = driver.switchTo().alert();
		String sText = alert.getText();
		alert.dismiss();
		report.updateTestLog("Dismissed the alert", "Text present on alert :" + sText, Status.PASS);
	}

	/**
	 * Method: for uploading file
	 * 
	 * @return - boolean (returns True when upload is successful else returns false)
	 * @throws Exception
	 */
	public boolean uploadFile(By locator, String filePath, int... optionWaitTime) throws Exception {
		boolean hasTyped = false;
		int waitTime = sync.getWaitTime(optionWaitTime);
		if (sync.isElementPresent(locator, waitTime)) {
			WebElement element = driver.findElement(locator);
			setHighlight(element);
			element.sendKeys(filePath);
			report.updateTestLog("Entered - in the element", "Locator :" + filePath + locator, Status.PASS);
			hasTyped = true;
		} else {
			report.updateTestLog("Element is not displayed", "Locator :" + locator, Status.FAIL);
			throw new FrameworkException("Element is not displayed", "Locator :" + locator);
		}
		return hasTyped;
	}

	/**
	 * Method to paste the text using key strokes
	 * 
	 * @param robot
	 */
	private void pasteCopiedText(Robot robot) {
		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_V);
		robot.keyRelease(KeyEvent.VK_V);
		robot.keyRelease(KeyEvent.VK_CONTROL);
	}

	/**
	 * Method to copy the given text to clip board
	 * 
	 * @param sText
	 */
	private void copyTextToClipboard(String sText) {
		StringSelection stringSelection = new StringSelection(sText);
		Clipboard _clpbrd = Toolkit.getDefaultToolkit().getSystemClipboard();
		_clpbrd.setContents(stringSelection, null);
	}

	/**
	 * Method: for uploading file by using Robot class
	 * 
	 * @return - null
	 * @param: reporting Reporting
	 * @throws Exception
	 */
	public void uploadFileRobot(By slocator, String sFileLocation, Reporting reporting, int waitTime) throws Exception {
		if (sync.isElementPresent(slocator, waitTime)) {
			copyTextToClipboard(sFileLocation);
			if (sync.isElementClickable(slocator, waitTime)) {
				driverUtil.waitFor(2000);
				Actions builder = new Actions(driver.getWebDriver());

				Action myAction = builder.click(driver.findElement(slocator)).release().build();

				myAction.perform();
				driverUtil.waitFor(15000);
				Robot robot = new Robot();
				pasteCopiedText(robot);
				robot.keyPress(KeyEvent.VK_ENTER);
				robot.keyRelease(KeyEvent.VK_ENTER);
				robot.keyPress(KeyEvent.VK_ENTER);
				robot.keyRelease(KeyEvent.VK_ENTER);
				driverUtil.waitFor(3000);

				if (reporting.equals(Reporting.REPORTWITHSCREENSHOT)) {

					// report.updateTestLog("File at location is Uploaded by upload field" +
					// sFileLocation ,"Locator :"+slocator, Status.PASS);
					report.updateTestLog("File at location is Uploaded by upload field",
							"Locator :" + slocator + "\n" + "FileLocation :" + sFileLocation, Status.PASS);
					// report.updateTestLog("File at location " + sFileLocation + " is Uploaded by
					// upload field","Locator :"+slocator, Status.SCREENSHOT);
					report.updateTestLog("File at location is Uploaded by upload field",
							"Locator :" + slocator + "\n" + "FileLocation :" + sFileLocation, Status.SCREENSHOT);

				}

				else if (reporting.equals(Reporting.REPORT))
					report.updateTestLog("File at location is Uploaded by upload field",
							"Locator :" + slocator + "\n" + "FileLocation :" + sFileLocation, Status.PASS);

			} else {
				report.updateTestLog("Unable to click on element with locator for uploading a file",
						"Locator :" + slocator, Status.FAIL);
				throw new FrameworkException("Unable to click on element with locator for uploading a file",
						"Locator :" + slocator);
			}
		} else {
			report.updateTestLog("Unable to upload file - using upload field ",
					"File Location :" + sFileLocation + "\n" + "Locator :" + slocator, Status.FAIL);
			throw new FrameworkException("Unable to upload file - using upload field ",
					"File Location :" + sFileLocation + "\n" + "Locator :" + slocator);
		}
	}

	/**
	 * 
	 * TODO JavaScript method for clicking on an element
	 *
	 * @param locator  - locator value by which element is recognized
	 * @param waitTime - Time to wait for an element
	 * @return
	 * @param: reporting Reporting
	 * @throws Exception
	 */
	public void safeJavaScriptClick(By locator, Reporting reporting, int waitTime) throws Exception {
		if (sync.isElementPresent(locator, waitTime)) {
			sync.setImplicitWait(waitTime);
			WebElement element = driver.findElement(locator);
			setHighlight(element);
			((JavascriptExecutor) driver.getWebDriver()).executeScript("arguments[0].click();", element);
			sync.nullifyImplicitWait();

			if (reporting.equals(Reporting.REPORTWITHSCREENSHOT)) {

				report.updateTestLog("Clicked on element ", "Locator :" + locator, Status.PASS);
				report.updateTestLog("Clicked on element ", "Locator :" + locator, Status.SCREENSHOT);
			}

			else if (reporting.equals(Reporting.REPORT))
				report.updateTestLog("Clicked on element ", "Locator :" + locator, Status.PASS);

		} else {

			report.updateTestLog("Unable to click on element ", "Locator :" + locator, Status.FAIL);
			throw new FrameworkException("Unable to click on element ", "Locator :" + locator);
		}

	}

	/**
	 * 
	 * TODO JavaScript method for entering a text in a field
	 * 
	 * @param locator  - locator value by which text field is recognized
	 * @param sText    - Text to be entered in a field
	 * @param waitTime - Time to wait for an element
	 * @return
	 * @param: reporting Reporting
	 * @throws Exception
	 */
	public void safeJavaScriptType(By locator, String sText, Reporting reporting, int waitTime) throws Exception {
		if (sync.isElementPresent(locator, waitTime)) {
			WebElement element = driver.findElement(locator);
			setHighlight(element);
			((JavascriptExecutor) driver.getWebDriver())
					.executeScript("arguments[0].setAttribute('value', '" + sText + "');", element);
			if (reporting.equals(Reporting.REPORTWITHSCREENSHOT)) {

				report.updateTestLog("Entered text into the field", "Locator :" + locator + "," + "textValue :" + sText,
						Status.PASS);
				report.updateTestLog("Entered text into the field", "Locator :" + locator + "," + "textValue :" + sText,
						Status.SCREENSHOT);
			}

			else if (reporting.equals(Reporting.REPORT))
				report.updateTestLog("Entered text into the field", "Locator :" + locator + "," + "textValue :" + sText,
						Status.PASS);

		} else {
			report.updateTestLog("Unable to enter text into the field",
					"Locator :" + locator + "," + "textValue :" + sText, Status.FAIL);
			throw new FrameworkException("Unable to enter text into the field",
					"Locator :" + locator + "," + "textValue :" + sText);
		}
	}

	/**
	 * TODO JavaScript Safe Method for Clear and Type
	 * 
	 * @param locator  - locator value by which text field is recognized
	 * @param sText    - Text to be entered in a field
	 * @param waitTime - Time to wait for an element
	 * @return
	 * @param: reporting Reporting
	 * @throws Exception
	 */
	public void safeJavaScriptClearType(By locator, String sText, Reporting reporting, int waitTime) {

		if (sync.isElementPresent(locator, waitTime)) {
			JavascriptExecutor jse = (JavascriptExecutor) driver.getWebDriver();
			// jse.executeScript("arguments[0].value='"+phno+"';", wb);

			WebElement element = driver.findElement(locator);
			setHighlight(element);
			// jse.executeScript("arguments[0].value='';", element);
			// jse.executeScript("arguments[0].value='"+sText+"';", element);
			jse.executeScript("arguments[0].setAttribute('value', '');", element);
			jse.executeScript("arguments[0].setAttribute('value', '" + sText + "');", element);
			// ((JavascriptExecutor)
			// driver.getWebDriver()).executeScript("arguments[0].value='');",element);
			// ((JavascriptExecutor)
			// driver.getWebDriver()).executeScript("arguments[0].value='"+sText+"');",element);

			if (reporting.equals(Reporting.REPORTWITHSCREENSHOT)) {

				report.updateTestLog("Cleared the field and entered",
						"Locator :" + locator + "," + "textValue :" + sText, Status.PASS);
				report.updateTestLog("Cleared the field and entered",
						"Locator :" + locator + "," + "textValue :" + sText, Status.SCREENSHOT);
			}

			else if (reporting.equals(Reporting.REPORT))
				report.updateTestLog("Cleared the field and entered",
						"Locator :" + locator + "," + "textValue :" + sText, Status.PASS);
		} else {
			report.updateTestLog("Unable to enter in field", "Locator :" + locator + "," + "textValue :" + sText,
					Status.FAIL);
			throw new FrameworkException("Unable to enter in field",
					"Locator :" + locator + "," + "textValue :" + sText);
		}
	}

	/**
	 * 
	 * TODO Safe method to get the attribute value
	 * 
	 * @param locator         - locator value by which an element is located
	 * @param sAttributeValue - attribute type
	 * @param waitTime        - Time to wait for an element
	 * @param: reporting Reporting
	 * @return - returns the attribute value of type string
	 */
	public String safeGetAttribute(By locator, String sAttributeValue, Reporting reporting, int waitTime) {
		String sValue = null;

		if (sync.isElementPresent(locator, waitTime))

		{
			sValue = driver.findElement(locator).getAttribute(sAttributeValue);

			if (reporting.equals(Reporting.REPORTWITHSCREENSHOT)) {

				report.updateTestLog("Retreived attribute value of the type", "Locator :" + locator, Status.PASS);
				report.updateTestLog("Retreived attribute value of the type", "Locator :" + locator, Status.SCREENSHOT);
			}

			else if (reporting.equals(Reporting.REPORT))
				report.updateTestLog("Retreived attribute value of the type", "Locator :" + locator, Status.PASS);
		} else {

			report.updateTestLog("Unable to find locator", "In time - " + waitTime + "\n" + "Locator :" + locator,
					Status.FAIL);
			throw new FrameworkException("Unable to find locator",
					"In time - " + waitTime + "\n" + "Locator :" + locator);
		}
		return sValue;
	}

	/**
	 * 
	 * TODO Safe method to get text from an element
	 * 
	 * @param locator  - locator value by which an element is located
	 * @param waitTime - Time to wait for an element
	 * @param: reporting Reporting
	 * @return - returns the text value from element
	 */
	public String safeGetText(By locator, Reporting reporting, int waitTime) {
		String sValue = null;
		if (sync.isElementPresent(locator, waitTime)) {
			sValue = driver.findElement(locator).getText();
		} else {
			report.updateTestLog("Unable to find Element ", "Locator :" + locator, Status.FAIL);
			throw new FrameworkException("Unable to find Element ", "Locator :" + locator);
		}
		return sValue;
	}

	/**
	 * TODO scroll method to scroll the page down until expected element is visible
	 * *
	 * 
	 * @param locator  - locator value by which an element is located
	 * @param waitTime - Time to wait for an element
	 * @return - returns the text value from element
	 */
	public void scrollIntoElementView(By locator) {
		((JavascriptExecutor) driver.getWebDriver()).executeScript("arguments[0].scrollIntoView();",
				driver.findElement(locator));
	}

	/**
	 * TODO JavaScript Safe method to get the attribute value
	 * 
	 * @param locator         - locator value by which an element is located
	 * @param sAttributeValue - attribute type
	 * @param waitTime        - Time to wait for an element
	 * @param: reporting Reporting
	 * @return - returns the attribute value of type string
	 */
	public String safeJavaScriptGetAttribute(By locator, String sAttributeValue, Reporting reporting, int waitTime) {
		String sValue = "";
		if (sync.isElementPresent(locator, waitTime)) {
			final String scriptGetValue = "return arguments[0].getAttribute('" + sAttributeValue + "')";
			WebElement element = driver.findElement(locator);
			sValue = (String) ((JavascriptExecutor) driver).executeScript(scriptGetValue, element);

			if (reporting.equals(Reporting.REPORTWITHSCREENSHOT)) {

				report.updateTestLog("Retrived attribute value of the type",
						"Locator value :" + sAttributeValue + "," + "AttributeValue :" + sValue, Status.PASS);
				report.updateTestLog("Retrived attribute value of the type",
						"Locator value :" + sAttributeValue + "," + "AttributeValue :" + sValue, Status.SCREENSHOT);
			}

			else if (reporting.equals(Reporting.REPORT))
				report.updateTestLog("Retrived attribute value of the type",
						"Locator value :" + sAttributeValue + "," + "AttributeValue :" + sValue, Status.PASS);
		} else {
			report.updateTestLog("Unable to find locator ", "Locator :" + locator, Status.FAIL);
			throw new FrameworkException("Unable to find locator ", "Locator :" + locator);
		}
		return sValue;
	}

	/**
	 * 
	 * TODO Safe method to retrieve the option selected in the drop down list
	 *
	 * @param locator  - locator value by which an element is located
	 * @param waitTime - Time to wait for an element
	 * @param: reporting Reporting
	 * @return - returns the option selected in the drop down list
	 */
	public String safeGetSelectedOptionInDropDown(By locator, Reporting reporting, int sWaitTime) throws Exception {
		String dropDownSelectedValue = null;

		// return getSelectedOptionInDropDown(locator, sWaitTime);
		if (sync.isElementPresent(locator, sWaitTime)) {
			Select dropDownName = new Select(driver.findElement(locator));
			// setHighlight(driver, dropDownName);
			dropDownSelectedValue = dropDownName.getFirstSelectedOption().getText();
			if (reporting.equals(Reporting.REPORTWITHSCREENSHOT)) {

				report.updateTestLog("Value  has been selected from dropdown field ",
						"Selected value" + dropDownSelectedValue + "," + "LocatorToCheck :" + locator, Status.PASS);
				report.updateTestLog("Value  has been selected from dropdown field ",
						"Selected value" + dropDownSelectedValue + "," + "LocatorToCheck :" + locator,
						Status.SCREENSHOT);
			}

			else if (reporting.equals(Reporting.REPORT))
				report.updateTestLog("Value  has been selected from dropdown field ",
						"Selected value" + dropDownSelectedValue + "," + "LocatorToCheck :" + locator, Status.PASS);

		} else {
			report.updateTestLog("Dropdown is not displayed", "Locator :" + locator, Status.FAIL);
			throw new FrameworkException("Dropdown is not displayed", "Locator :" + locator);
		}
		return dropDownSelectedValue;
	}

	/**
	 * 
	 * TODO Safe method to verify whether the element is exists in the list box or
	 * not
	 *
	 * @param locator  - locator value by which an element is located
	 * @param waitTime - Time to wait for an element
	 * @param: reporting Reporting
	 * @return - returns 'true' if the mentioned value exists in the list box else
	 *         returns 'false'
	 * 
	 */
	public boolean safeVerifyListBoxValue(By locator, String value, Reporting reporting, int sWaitTime)
			throws Exception {
		boolean isExpected = false;
		if (sync.isElementPresent(locator, sWaitTime)) {
			WebElement listBox = driver.findElement(locator);
			java.util.List<WebElement> listBoxItems = listBox.findElements(By.tagName("li"));
			for (WebElement item : listBoxItems) {
				if (item.getText().equals(value))
					isExpected = true;
			}

			if (reporting.equals(Reporting.REPORTWITHSCREENSHOT)) {

				report.updateTestLog("Listbox item is existed in listbox ",
						"Value :" + value + "," + "Locator :" + locator, Status.PASS);
				report.updateTestLog("Listbox item is existed in listbox ",
						"Value :" + value + "," + "Locator :" + locator, Status.SCREENSHOT);
			}

			else if (reporting.equals(Reporting.REPORT))
				report.updateTestLog("Listbox item is existed in listbox ",
						"Value :" + value + "," + "Locator :" + locator, Status.PASS);
		} else {
			report.updateTestLog("Listbox is not displayed", "Locator :" + locator, Status.FAIL);
			throw new FrameworkException("Listbox is not displayed", "Locator :" + locator);
		}

		return isExpected;
	}

	/**
	 * 
	 * TODO Safe method to verify whether the element is exists in the list box or
	 * not
	 *
	 * @param locator  - locator value by which an element is located
	 * @param waitTime - Time to wait for an element
	 * @param: reporting Reporting
	 * @return - returns 'true' if the mentioned value exists in the list box else
	 *         returns 'false'
	 * 
	 */
	public void safeClickListBoxValue(By locator, String value, Reporting reporting, int sWaitTime) throws Exception {
		if (sync.isElementPresent(locator, sWaitTime)) {
			WebElement listBox = driver.findElement(locator);
			java.util.List<WebElement> listBoxItems = listBox.findElements(By.tagName("li"));
			for (WebElement item : listBoxItems) {
				if (item.getText().trim().equals(value))
					item.click();
			}

			if (reporting.equals(Reporting.REPORTWITHSCREENSHOT)) {
				report.updateTestLog("Listbox item is clicked in listbox ",
						"Value :" + value + "," + "Locator :" + locator, Status.PASS);
				report.updateTestLog("Listbox item is clicked in listbox ",
						"Value :" + value + "," + "Locator :" + locator, Status.SCREENSHOT);
			}

			else if (reporting.equals(Reporting.REPORT))
				report.updateTestLog("Listbox item is clicked in listbox ",
						"Value :" + value + "," + "Locator :" + locator, Status.PASS);
		} else {
			report.updateTestLog("Listbox is not clicked", "Locator :" + locator, Status.FAIL);
			throw new FrameworkException("Listbox is not clicked", "Locator :" + locator);
		}
	}

	/**
	 * Method for switching to frame using frame id
	 * 
	 * @param driver
	 * @param: reporting Reporting
	 * @param frame
	 */
	public void selectFrame(String frame, Reporting reporting) {
		driver.switchTo().frame(frame);
		if (reporting.equals(Reporting.REPORTWITHSCREENSHOT)) {

			report.updateTestLog("Navigated to frame ", "FrameID :" + frame, Status.PASS);
			report.updateTestLog("Navigated to frame ", "FrameID :" + frame, Status.SCREENSHOT);
		} else if (reporting.equals(Reporting.REPORT))
			report.updateTestLog("Navigated to frame ", "FrameID :" + frame, Status.PASS);
	}

	/**
	 * Method - Method for switching to frame in a frame
	 * 
	 * @param driver
	 * @param ParentFrame
	 * @param: reporting Reporting
	 * @param ChildFrame
	 */
	public void selectFrame(String ParentFrame, String ChildFrame, Reporting reporting) {
		driver.switchTo().frame(ParentFrame).switchTo().frame(ChildFrame);

		if (reporting.equals(Reporting.REPORTWITHSCREENSHOT)) {

			report.updateTestLog("Navigated to innerframe ", "FrameID :" + ChildFrame, Status.PASS);
			report.updateTestLog("Navigated to innerframe ", "FrameID :" + ChildFrame, Status.SCREENSHOT);
		}

		else if (reporting.equals(Reporting.REPORT))
			report.updateTestLog("Navigated to innerframe ", "FrameID :" + ChildFrame, Status.PASS);
	}

	/**
	 * Method - Method for switching to frame using any locator of the frame
	 * 
	 * @param driver
	 * @param ParentFrame
	 * @param: reporting Reporting
	 * @param ChildFrame
	 */
	public void selectFrame(By Framelocator, int waitTime, Reporting reporting) {
		if (sync.isElementPresent(Framelocator, waitTime)) {
			WebElement Frame = driver.findElement(Framelocator);
			driver.switchTo().frame(Frame);

			if (reporting.equals(Reporting.REPORTWITHSCREENSHOT)) {

				report.updateTestLog("Navigated to frame", " with locator " + Framelocator, Status.PASS);
				report.updateTestLog("Navigated to frame", " with locator " + Framelocator, Status.SCREENSHOT);
			}

			else if (reporting.equals(Reporting.REPORT))
				report.updateTestLog("Navigated to frame", " with locator " + Framelocator, Status.PASS);
		} else {

			report.updateTestLog("Unable to navigate to frame", " with locator " + Framelocator, Status.FAIL);
			throw new FrameworkException("Unable to navigate to frame", " with locator " + Framelocator);
		}
	}

	/**
	 * Method - Method for switching back to webpage from frame
	 * 
	 * @param driver
	 * @param: reporting Reporting
	 */
	public void defaultFrame(Reporting reporting) {
		driver.switchTo().defaultContent();

		if (reporting.equals(Reporting.REPORTWITHSCREENSHOT)) {

			report.updateTestLog("Navigated to back to webpage", "from frame", Status.PASS);
			report.updateTestLog("Navigated to back to webpage", "from frame", Status.SCREENSHOT);
		}

		else if (reporting.equals(Reporting.REPORT))
			report.updateTestLog("Navigated to back to webpage", "from frame", Status.PASS);
	}

	/**
	 * Method: Gets a UI element attribute value and compares with expected value
	 * 
	 * @param locator
	 * @param attributeName
	 * @param expected
	 * @param contains
	 * @param waitTime
	 * @param: reporting Reporting
	 * @return - Boolean (true if matches)
	 */
	public boolean getAttributeValue(By locator, String attributeName, String expected, boolean contains,
			Reporting reporting, int... waitTime) {
		boolean bvalue = false;
		if (sync.isElementPresent(locator, sync.getWaitTime(waitTime))) {
			String sTemp = driver.findElement(locator).getAttribute(attributeName);
			if (!(sTemp.contains(expected) ^ contains)) {
				bvalue = true;
				if (reporting.equals(Reporting.REPORTWITHSCREENSHOT)) {

					// report.updateTestLog("Got attribute value " + sTemp + " of type " +
					// attributeName," from the element "+ locator + " and it is matched with
					// expected", Status.PASS);
					report.updateTestLog("Got attribute value of type " + attributeName,
							"Value is matched with expected \n" + " locator" + locator, Status.PASS);
					report.updateTestLog("Got attribute value of type " + attributeName,
							"Value is matched with expected \n" + " locator" + locator, Status.SCREENSHOT);
				}

				else if (reporting.equals(Reporting.REPORT))
					report.updateTestLog("Got attribute value of type " + attributeName,
							"Value is matched with expected \n" + " locator" + locator, Status.PASS);
			}
		} else {
			report.updateTestLog("Element was not found in DOM", "locator :" + locator, Status.FAIL);
			throw new FrameworkException("Element was not found in DOM", "locator :" + locator);
		}

		return bvalue;
	}

	/**
	 * Method: Gets an UI element attribute value
	 * 
	 * @param locator
	 * @param attributeName
	 * @param waitTime
	 * @param: reporting Reporting
	 * @return - String (Attribute Value)
	 */
	public String getAttributeValue(By locator, String attributeName, Reporting reporting, int... waitTime) {

		String sValue = null;
		if (sync.isElementPresent(locator, sync.getWaitTime(waitTime))) {
			sValue = driver.findElement(locator).getAttribute(attributeName);

			if (reporting.equals(Reporting.REPORTWITHSCREENSHOT)) {

				report.updateTestLog("Got attribute value of the type ",
						"AttributeName -" + attributeName + "," + " value is: " + sValue, Status.PASS);
				report.updateTestLog("Got attribute value of the type ",
						"AttributeName -" + attributeName + "," + " value is: " + sValue, Status.SCREENSHOT);
			}

			else if (reporting.equals(Reporting.REPORT))
				report.updateTestLog("Got attribute value of the type ",
						"AttributeName " + attributeName + "," + " value is: " + sValue, Status.PASS);
		} else {
			report.updateTestLog("Element was not found in DOM" + locator, " was not found in DOM", Status.FAIL);
			throw new FrameworkException("Element was not found in DOM" + locator, " was not found in DOM");
		}

		return sValue;
	}

	/**
	 * Method Highlights on current working element or locator param Webdriver
	 * instance param WebElement
	 * 
	 * @param: reporting Reporting return void (nothing)
	 */
	public void setHighlight(WebElement element) {
		if (properties.getProperty("HighlightElements").equalsIgnoreCase("true")) {
			String attributevalue = "border:3px solid red;";
			JavascriptExecutor executor = (JavascriptExecutor) driver;
			String getattrib = element.getAttribute("style");
			executor.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, attributevalue);

			driverUtil.waitFor(100);

			executor.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, getattrib);
		}
	}

	/**
	 * 
	 * This method is used to switch to windows based on provided number.
	 *
	 * @param num , Window number starting at 0
	 * @param: reporting Reporting
	 */
	public void switchToWindow(int num, Reporting reporting) {

		int numWindow = driver.getWindowHandles().size();
		String[] window = (String[]) driver.getWindowHandles().toArray(new String[numWindow]);
		driver.switchTo().window(window[num]);
		if (reporting.equals(Reporting.REPORTWITHSCREENSHOT)) {

			report.updateTestLog("Navigated successfully to window", "with sepcified number: " + num + "", Status.PASS);
			report.updateTestLog("Navigated successfully to window", "with sepcified number: " + num + "",
					Status.SCREENSHOT);
		}

		else if (reporting.equals(Reporting.REPORT))
			report.updateTestLog("Navigated successfully to window", "with sepcified number: " + num + "", Status.PASS);
	}

	/**
	 * 
	 * This method is used to switch to windows based on provided window title
	 * 
	 * @param title, Window title to which @param, waitTime
	 * @param: reporting Reporting
	 *
	 **/
	public void switchToWindow(String title, Reporting reporting) {

		int numWindows = driver.getWindowHandles().size();
		int i = 0;
		while (numWindows < 2) {
			driverUtil.waitFor(500);
			if (++i > 21) {
				break;
			}
		}
		if (numWindows >= 2) {

			boolean bFlag = false;
			for (String handle : driver.getWindowHandles()) {
				driver.switchTo().window(handle);
				if (driver.getTitle().contains(title)) {

					if (reporting.equals(Reporting.REPORTWITHSCREENSHOT)) {

						report.updateTestLog("Navigated successfully to new window", "with title " + title + "",
								Status.PASS);
						report.updateTestLog("Navigated successfully to new window", "with title " + title + "",
								Status.SCREENSHOT);
					}

					else if (reporting.equals(Reporting.REPORT))
						report.updateTestLog("Navigated successfully to new window", "with title " + title + "",
								Status.PASS);

					bFlag = true;
					break;
				}
			}
			if (!bFlag) {
				report.updateTestLog("Window with sepcified title",
						" doesn't exists. Please check the window title or wait until the new window appears",
						Status.FAIL);

			}
		} else {

			report.updateTestLog("Can not switch to new window as there is only one window",
					"wait until the new window appears", Status.PASS);
			throw new FrameworkException("Can not switch to new window as there is only one window",
					"wait until the new window appears");
		}
	}

	/**
	 * 
	 * This method is used to switch to windows based on provided unique element
	 * locator
	 * 
	 * @param locater, unique element locater
	 * @param: reporting Reporting @param, waitTime
	 *
	 **/
	public void switchToWindow(By locator, Reporting reporting, int... waitTime) {
		int numWindows = driver.getWindowHandles().size();
		int i = 0;
		while (numWindows < 2) {
			driverUtil.waitFor(500);
			if (++i > 21) {
				break;
			}
		}
		if (numWindows >= 2) {
			boolean bFlag = false;
			for (String handle : driver.getWindowHandles()) {
				driver.switchTo().window(handle);
				if (sync.isElementPresent(locator, sync.getWaitTime(waitTime))) {

					if (reporting.equals(Reporting.REPORTWITHSCREENSHOT)) {

						report.updateTestLog("Navigated succesfsully to new window", "with locator " + locator + "",
								Status.PASS);
						report.updateTestLog("Navigated successfully to new window", "with locator " + locator + "",
								Status.SCREENSHOT);
					}

					else if (reporting.equals(Reporting.REPORT))
						report.updateTestLog("Navigated successfully to new window", "with locator " + locator + "",
								Status.PASS);

					bFlag = true;
					break;
				}
			}
			if (!bFlag) {
				report.updateTestLog("Window with sepcified title ",
						" doesn't exists. Please check the window title or wait until the new window appears",
						Status.FAIL);

			}
		} else {
			report.updateTestLog("Cannot switch to new window as there is only one window",
					"wait until the new window appears", Status.WARNING);
			throw new FrameworkException("Cannot switch to new window as there is only one window",
					"wait until the new window appears");
		}
	}

	/**
	 * 
	 * This method is used to return number of locators found
	 *
	 * @param Locator
	 * @return , returns number of locators
	 */
	public int getLocatorCount(By Locator) {

		int iCount = 0;
		iCount = driver.findElements(Locator).size();
		return iCount;
	}

	/**
	 * 
	 * This method is used to return list of WebElements matched by locator
	 *
	 * @param Locator
	 * @return
	 */
	public List<WebElement> LocatorWebElements(By Locator) {
		List<WebElement> list = null;
		list = driver.findElements(Locator);
		return list;
	}

	public void findText(String LabelText, WebDriver driver) {

		String needle = "productX";
		String findCommand = "mobile:text:find"; // The proprietary function call
		Map<String, Object> params = new HashMap<>();
		params.put("content", needle); // The text we're looking for
		params.put("scrolling", "scroll"); // Add the scroll and search
		params.put("next", "SWIPE_UP"); // Next is mandatory if using scroll and search
										// Can also use customized swipe like:
										// "SWIPE=(50%,80%),(50%,60%);WAIT=1000"
		params.put("maxscroll", 3); // Not mandatory, default is 5
		params.put("threshold", 100); // Adding threshold
		// remoteWebdriver.executeScript(findCommand, params);
		Object result2 = ((RemoteWebDriver) driver).executeScript(findCommand, params);

	}

	/**
	 * 
	 * This method is used to switch the context between Web View and Native App
	 * View in mobile
	 * 
	 * @param driver-- RemoteWebDriver instance
	 * @param context, example:NATIVE_APP, WEBVIEW
	 * @author i372636 Monalisa Barik
	 */
	public void switchToContext(RemoteWebDriver driver, String context) {
		RemoteExecuteMethod executeMethod = new RemoteExecuteMethod(driver);
		Map<String, String> params = new HashMap<String, String>();
		params.put("name", context);
		executeMethod.execute(DriverCommand.SWITCH_TO_CONTEXT, params);
	}

	/**
	 * 
	 * This method is used to click Android mobile device BACK button
	 * 
	 * @param driver-- RemoteWebDriver instance
	 * @author i372636 Monalisa Barik
	 */
	public void clickDeviceBackButton(RemoteWebDriver driver) {
		Map<String, Object> Parms = new HashMap<>();
		Parms.put("keySequence", "BACK");
		driver.executeScript("mobile:presskey", Parms);
	}

	/**
	 * 
	 * This method is used to click Safari Browser BACK button
	 * 
	 * @param driver-- RemoteWebDriver instance
	 * @author i372636 Monalisa Barik
	 */
	public void clickSafariBackButton(RemoteWebDriver driver) {
		driver.findElement(By.xpath("//*[@label=\"Back\"]")).click();
	}

	/**
	 * 
	 * This method is used to click Chrome MENU button
	 * 
	 * @param driver--         RemoteWebDriver instance
	 * @param sTextToValidate, iTimeout, reporting
	 * @author i372636 Monalisa Barik
	 */
	public void clickChromeMenuBtn(RemoteWebDriver driver, String sTextToValidate, int iTimeout, Reporting reporting) {
		driver.findElement(By.xpath("//*[@resource-id='com.android.chrome:id/menu_button']")).click();
		driver.findElement(By.xpath("//*[@content-desc='Downloads']")).click();
		stringCheckpoint(driver, sTextToValidate, iTimeout, reporting);
		driver.findElement(By.xpath("//*[@resource-id='com.android.chrome:id/close_menu_id']")).click();
	}

	/**
	 * This method is used to click pages label and select the previous tab in
	 * safari
	 * 
	 * @param driver--         RemoteWebDriver instance
	 * @param sTextToValidate, iTimeout, reporting
	 * @author i372636 Monalisa Barik
	 */
	public void navigateBackFrmNewWebPageSafari(RemoteWebDriver driver, int iTimeout) {
		driver.findElement(By.xpath("//*[@label='Pages']")).click();
		driver.findElement(
				By.xpath("//AppiumAUT/UIAApplication[1]/UIAWindow[2]/UIAScrollView[1]/UIAButton[2]/UIAButton[1]"))
				.click();
		driver.findElement(By.xpath("//AppiumAUT/UIAApplication[1]/UIAWindow[2]/UIAScrollView[1]/UIAButton[1]"))
				.click();
	}

	/**
	 * 
	 * This method is used to validate appearance of expected text on the device.
	 * 
	 * @param driver--         RemoteWebDriver instance
	 * @param sTextToValidate, iTimeout, reporting
	 * @author i372636 Monalisa Barik
	 */
	public void stringCheckpoint(RemoteWebDriver driver, String sTextToValidate, int iTimeout, Reporting reporting) {
		System.out.println(" string Checkpoint to find: " + sTextToValidate);
		String command = "mobile:checkpoint:text";
		Map<String, Object> Parms = new HashMap<String, Object>();
		Parms.put("content", sTextToValidate);
		Parms.put("timeout", iTimeout);
		// Parms.put("report", report);
		String sIsFound = (String) driver.executeScript(command, Parms);

		if (sIsFound.equalsIgnoreCase("true")) {
			if (reporting.equals(Reporting.REPORTWITHSCREENSHOT)) {

				report.updateTestLog("Native App pop up", "contains text " + sTextToValidate + "", Status.PASS);
				report.updateTestLog("Native App pop up", "contains text " + sTextToValidate + "", Status.SCREENSHOT);
			}

			else if (reporting.equals(Reporting.REPORT))
				report.updateTestLog("Native App pop up", "contains text " + sTextToValidate + "", Status.PASS);

		} else {
			report.updateTestLog("Native App pop up", "does not contain text " + sTextToValidate + "", Status.PASS);
			report.updateTestLog("Native App pop up", "does not contain text " + sTextToValidate + "",
					Status.SCREENSHOT);
		}
		// return sIsFound;
	}

	/**
	 * This method is used to Clear the cache in Safari
	 * 
	 * @param Rwebdriver-- RemoteWebDriver instance
	 * @author i372636 Monalisa Barik
	 */
	public void ClearSafariCache(RemoteWebDriver Rwebdriver) throws InterruptedException {

		// Go to Home Page
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("keySequence", "HOME ");
		Rwebdriver.executeScript("mobile:presskey", param);

		// Launch Settings Menu
		startApp(Rwebdriver, "Settings");

		switchToContext(Rwebdriver, "NATIVE_APP");

		// Check If already any Settings opened inside Settings Menu
		if (waitForElementToAppear(Rwebdriver, By.xpath("//*[@label=\"Back\"]"), 5)) {

			// If is safari setting menu opened Go ahead and clear the safari
			// cache
			if (waitForElementToAppear(Rwebdriver, By.xpath("//*[@label=\"Safari\"]"), 5)) {

				processClearing(Rwebdriver, false);

				// else come back to main Settings menu and Clear the Cache
			} else {
				Rwebdriver.findElement(By.xpath("//*[@label=\"Back\"]")).click();
				processClearing(Rwebdriver, false);
			}

		} else {

			processClearing(Rwebdriver, false);
		}

		closeApp("Settings", Rwebdriver);
		switchToContext(Rwebdriver, "WEBVIEW");

	}

	/**
	 * This method is used to clear perform all the actions to clear the cache in
	 * Safari
	 * 
	 * @param Rwebdriver--            RemoteWebDriver instance
	 * @param bAlreadyInSafariSetting
	 * @author i372636 Monalisa Barik
	 */
	public void processClearing(RemoteWebDriver Rwebdriver, Boolean bAlreadyInSafariSetting)
			throws InterruptedException {

		// skipping if screen already in safari page
		if (bAlreadyInSafariSetting == false) {

			// remoteDriver.findElement(By.xpath("//UIATableCell[@name='Safari']")).click();

			ScrolltoText(Rwebdriver, "Safari");
			Thread.sleep(2000);
			switchToContext(Rwebdriver, "NATIVE_APP");

			// Some times safari wont clicked trying again if right page not turned
			if (!waitForElementToAppear(Rwebdriver, By.xpath("//*[@label=\"Back\"]"), 5)) {

				driver.findElement(By.xpath("//*[@value=\"Safari\"]")).click();
			}
		}

		ScrolltoText(Rwebdriver, "Clear History");

		// Clear only if Clear History is enabled
		if (waitForElementToAppear(Rwebdriver,
				By.xpath("//UIATableCell[@label=\"Clear History and Website Data\" and @enabled = 'true']"), 5)) {

			// Some times Clear History Widget not opened. Checking and trying again if not
			// opened
			if (!waitForElementToAppear(Rwebdriver, By.xpath("//*[@label=\"Cancel\"]"), 5)) {

				driver.findElement(By.xpath("//*[@value=\"Clear History and Website Data\"]")).click();
			}

			driver.findElement(By.xpath("//*[@label=\"Clear History and Data\"]")).click();
			/*
			 * //For IPAD try {
			 * driver.findElement(By.xpath("//*[@label=\"Clear\"]")).click(); } catch
			 * (Exception e) { // TODO Auto-generated catch block e.printStackTrace(); }
			 */
		} else
			System.out.println("Cache Already Cleared. Nothing to clear");
	}

	/**
	 * This method is used to wait for a element to appear on mobile
	 * 
	 * @param Rwebdriver-- RemoteWebDriver instance
	 * @param locator,     lTimeOutInSeconds
	 * @author i372636 Monalisa Barik
	 */
	public static Boolean waitForElementToAppear(RemoteWebDriver Rwebdriver, By locator, long lTimeOutInSeconds) {

		Rwebdriver.manage().timeouts().implicitlyWait(0, TimeUnit.MILLISECONDS);

		try {
			Rwebdriver.findElement(locator);
		} catch (Exception e) {
			Rwebdriver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
			return false;
		}
		Rwebdriver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		return true;
	}

	/**
	 * This method is used to scroll to a text on mobile
	 * 
	 * @param Rwebdriver-- RemoteWebDriver instance
	 * @param sLabelText
	 * @author i372636 Monalisa Barik
	 */
	public static void ScrolltoText(RemoteWebDriver Rwebdriver, String sLabelText) {

		Map<String, Object> params2 = new HashMap<String, Object>();

		params2.put("content", sLabelText);
		params2.put("scrolling", "scroll");
		params2.put("maxscroll", "5");
		params2.put("next", "SWIPE_UP");
		params2.put("threshold", "90");
		params2.put("words", "words");
		Object result2 = Rwebdriver.executeScript("mobile:text:select", params2);
	}

	/**
	 * This method is used to start an app on mobile
	 * 
	 * @param Rwebdriver-- RemoteWebDriver instance
	 * @param sAppName
	 * @author i372636 Monalisa Barik
	 */
	public static void startApp(RemoteWebDriver Rwebdriver, String sAppName) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("name", sAppName);
		Rwebdriver.executeScript("mobile:application:open", params);
	}

	/**
	 * This method is used to close an app on mobile
	 * 
	 * @param Rwebdriver-- RemoteWebDriver instance
	 * @param sAppName
	 * @author i372636 Monalisa Barik
	 */
	public static void closeApp(String sAppName, RemoteWebDriver Rwebdriver) {
		Map<String, String> params = new HashMap<String, String>();
		params.clear();
		params.put("name", sAppName);
		Rwebdriver.executeScript("mobile:application:close", params);
	}

	/**
	 * /** Method - Method for User Clear waits until the element is loaded and then
	 * clears the text
	 * 
	 * @author: Monalisa (i372636)
	 * @param locator
	 * @param waitTime
	 * @param: reporting Reporting
	 */
	public void Clear(By locator, Reporting reporting, int... optionWaitTime) {

		WebElement element = driver.findElement(locator);
		element.clear();

		if (reporting.equals(Reporting.REPORTWITHSCREENSHOT)) {
			report.updateTestLog("Text cleared successfully ", "Element " + locator.toString(), Status.PASS);
			report.updateTestLog("Text cleared successfully ", "Element " + locator.toString(), Status.SCREENSHOT);
		}

		else if (reporting.equals(Reporting.REPORT))
			report.updateTestLog("Text cleared successfully ", "Element " + locator.toString(), Status.PASS);
	}

	public void touchAction(RemoteWebDriver Rwebdriver, String sCoordinates) {
		Map<String, Object> params = new HashMap<>();
		params.put("location", sCoordinates);
		params.put("operation", "single");
		params.put("duration", "3");
		Object res = Rwebdriver.executeScript("mobile:touch:tap", params);
	}

	/**
	 * Method - Safe Method for User get the default option from Drop down by option
	 * label, waits until the element is loaded and then selects an option from drop
	 * down
	 * 
	 * @param locator
	 * @param waitTime
	 * @param: reporting Reporting
	 * @return - boolean (returns True when option is selected from the drop down
	 *         else returns false)
	 * @throws Exception
	 */
	public String getDefaultOptionFromDropdown(By locator, Reporting reporting, int... optionWaitTime) {
		int waitTime = 0;
		String sDefaultValue = "";
		waitTime = sync.getWaitTime(optionWaitTime);
		if (sync.isElementPresent(locator, waitTime)) {
			WebElement selectElement = driver.findElement(locator);
			setHighlight(selectElement);
			Select select = new Select(selectElement);
			sDefaultValue = select.getFirstSelectedOption().getText();

			if (reporting.equals(Reporting.REPORTWITHSCREENSHOT)) {

				report.updateTestLog("Selected dropdown item by default text option", "Locator :" + sDefaultValue,
						Status.PASS);
				report.updateTestLog("Selected dropdown item by default text option", "Locator :" + sDefaultValue,
						Status.SCREENSHOT);
			}

			else if (reporting.equals(Reporting.REPORT))
				report.updateTestLog("Selected dropdown item by default text option", "Locator :" + sDefaultValue,
						Status.PASS);
		} else {
			report.updateTestLog("Selected dropdown item by default text option", "Locator :" + locator, Status.FAIL);
			throw new FrameworkException("Selected dropdown item by default text option", "Locator :" + locator);
		}
		return sDefaultValue;
	}

	public void saveFile(Robot robot) {
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);
	}

	public List<String> convertWebElementListToStringList(By locator) {

		List<WebElement> allElements = driver.findElements(locator);
		List<String> strList = new LinkedList<String>();
		for (WebElement ele : allElements) {
			String eleText = ele.getText().trim();
			if (!eleText.isEmpty()) {
				strList.add(eleText);
			} else {
				continue;
			}
		}
		return strList;

	}

	/**
	 * Method to get all the window handles
	 */
	public void safeGetWindowHandles() {
		String ParentWindow = driver.getWindowHandle();
		java.util.Set<String> allWindows = driver.getWindowHandles();

		Iterator<String> windowIterator = allWindows.iterator();
		while (windowIterator.hasNext()) {

			String ChildWindow = windowIterator.next();
			if (!ParentWindow.equals(ChildWindow)) {
				driver.switchTo().window(ChildWindow);
			}

		}
	}

	public String getValuefromTextFile(String file) {

		BufferedReader br;
		String value = null;

		try {
			br = new BufferedReader(new FileReader(file));

			value = br.readLine();// read only line#1 where the value is present
			if (value == null || value.equalsIgnoreCase("0")) {
				report.updateTestLog("Value is", " Null. Check the file and update the correct value there.",
						Status.FAIL);
			}

			br.close();

		} catch (Exception e) {

			report.updateTestLog("File", " is not available. Check the location and place the appropriate file.",
					Status.FAIL);
			e.printStackTrace();
		}

		return value;

	}

	public String getTopSecretPassword() {

		File file = new File("C:" + Util.getFileSeparator() + "Automation_Password" + Util.getFileSeparator()
				+ "TopSecret_Password.txt");
		String filePath = file.getAbsolutePath();
		String password = getValuefromTextFile(filePath);
		return password;
	}

	public String getLANPassword() {

		File file = new File(
				"C:" + Util.getFileSeparator() + "Automation_Password" + Util.getFileSeparator() + "LAN_Password.txt");
		String filePath = file.getAbsolutePath();
		String password = getValuefromTextFile(filePath);
		return password;
	}

	public void putTempData(final String fieldName, final String dataValue) {

		putTempData(fieldName, dataValue, 1);
	}

	public void putTempData(final String fieldName, final String dataValue, int iRow) {

		ExcelDataAccess testDataAccess = new ExcelDataAccess(tempDatatablePath, tempDatatableName);
		testDataAccess.setDatasheetName(tempDatasheetName);

		int rowNum = testDataAccess.getRowNum(tempDataTestCase, 0, iRow);
		if (rowNum == -1) {
			throw new FrameworkException("The temp data testcase \"" + tempDataTestCase + "\""
					+ "is not found in the test data sheet \"" + tempDatasheetName + "\"!");
		}

		synchronized (DataTable.class) {
			testDataAccess.setValue(rowNum, fieldName, dataValue);
		}
	}

	public String getTempData(final String fieldName) {

		return getTempData(fieldName,1);
	}

	public String getTempData(final String fieldName,int iRow) {

		ExcelDataAccess testDataAccess = new ExcelDataAccess(tempDatatablePath, tempDatatableName);
		testDataAccess.setDatasheetName(tempDatasheetName);

		int rowNum = testDataAccess.getRowNum(tempDataTestCase, 0, iRow);
		if (rowNum == -1) {
			throw new FrameworkException("The test case \"" + tempDataTestCase + "\""
					+ "is not found in the test data sheet \"" + tempDatasheetName + "\"!");
		}

		String dataValue = testDataAccess.getValue(rowNum, fieldName);

		if (dataValue.startsWith("#")) {
			dataValue = getCommonData(fieldName, dataValue, tempDatatablePath);
		}

		return dataValue;
	}
	private String getCommonData(final String fieldName, final String dataValue, final String dataTablePath) {
		ExcelDataAccess commonDataAccess = new ExcelDataAccess(dataTablePath, "Common Testdata");
		commonDataAccess.setDatasheetName("Common_Testdata");

		String dataReferenceId = dataValue.split("#")[1];

		int rowNum = commonDataAccess.getRowNum(dataReferenceId, 0, 1);
		if (rowNum == -1) {
			throw new FrameworkException("The common test data row identified by \"" + dataReferenceId + "\""
					+ "is not found in the common test data sheet!");
		}

		return commonDataAccess.getValue(rowNum, fieldName);
	}

	public String decimal(float x) {
		String s = Float.toString(x);
		DecimalFormat df = new DecimalFormat("#.##");
		if (s.contains(".")) {
			int a = s.length() - s.indexOf(".") - 1;
			if (a >= 2)
				return df.format(x);
			else if (a == 1)
				return s + "0";
		}
		return s;
	}

}