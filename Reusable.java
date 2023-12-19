package common;

import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.hcsc.automation.framework.FrameworkException;
import com.hcsc.automation.framework.Report;
import com.hcsc.automation.framework.Status;


///import AceDesktop.uimap.SmartUM_UIMap;
import supportlibraries.ReusableLibrary;
import supportlibraries.ScriptHelper;

public class Reusable extends ReusableLibrary {



	public static boolean bSkipExecution;

//Test
	public Reusable(ScriptHelper scriptHelper)
	{
		super(scriptHelper);
	}

	WebDriverWait wait = new WebDriverWait(driver.getWebDriver(),15); 


	//******************************Reusable boolean Methods***********************************************************//

	/****************************************************************************************
					  Function Name: smartUM_RC_ScrolltoBottomfPage
					  Description : ScrollDown to the bottom of the page using Javascript
					  Created By : Karthikeyan.J
					  Created Date :08-6-2019
					  Modified By:  Nil
					  Modified Date: Nil	   
	 ***************************************************************************************/
	public boolean smartUM_RC_ScrolltoBottomfPage() {
		try {
			JavascriptExecutor js = ((JavascriptExecutor) driver.getWebDriver());
			js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
			return true;
		} catch (Exception e) {
			return false;
		}
	}


	/****************************************************************************************
					  Function Name: smartUM_RC_ScrolltoElement
					  Description : ScrollUp/Down to element using Javascript
					  Created By : Karthikeyan.J
					  Created Date :08-6-2019
					  Modified By:  Nil
					  Modified Date: Nil	   
	 ***************************************************************************************/
	public boolean smartUM_RC_ScrolltoElement(By Locator) {
		try {
			JavascriptExecutor js = ((JavascriptExecutor) driver.getWebDriver());	

			// Scroll up/down in order to view of element:
			//===========================================
			WebElement element = driver.getWebDriver().findElement(Locator);
			js.executeScript("arguments[0].scrollIntoView(true);", element);
			// Reporting is not given to avoid unnecessary Report steps:
			smartUM_ReusableComponents_scrollupPage();
			return true;
		} catch (Exception e) {			
			return false;
		}
	}


	/****************************************************************************************
					  Function Name: smartUM_RC_ScrolltoTopofPage
					  Description : ScrollUp to the top of the page using Javascript
					  Created By : Karthikeyan.J
					  Created Date :08-6-2019
					  Modified By:  Nil
					  Modified Date: Nil	   
	 ***************************************************************************************/
	public boolean smartUM_RC_ScrolltoTopofPage() {
		try {
			JavascriptExecutor js = ((JavascriptExecutor) driver.getWebDriver());
			js.executeScript("window.scrollTo(0, 0)");
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/****************************************************************************************
					  Function Name: smartUM_RC_RegEx
					  Description : Return value using Regular Expression by matching the pattern.
					  Created By : Karthikeyan.J
					  Created Date :08-6-2019
					  Modified By:  Nil
					  Modified Date: Nil	   
	 ***************************************************************************************/
	public String smartUM_RC_RegEx(String fpattern, String value) {

		// "[0-9]{5}[A-Z]{5}" RequestID Pattern for AerialRequest
		// "U[0-9]{5}[A-Z]{5}" RequestID Pattern for SmartUMRequest

		String Value = "";
		try {
			Pattern pattern = Pattern.compile(fpattern);
			Matcher match = pattern.matcher(value);
			while (match.find()) {

				// Return value based on matching the given pattern:
				// =================================================
				return match.group(0);
			}
			return Value;
		} catch (Exception e) {
			return Value;
		}
	}
	/****************************************************************************************
	  Function Name: smartUM_RC_ExplicitWait
	  Description : Dynamic wait for an Element based on given timeout
	  Created By : Karthikeyan.J
	  Created Date :08-6-2019
	  Modified By:  Nil
	  Modified Date: Nil	   
	 ***************************************************************************************/
	public void smartUM_RC_ExplicitWait(By Locator, int timeout, String sReportText) {
		try {
			// Wait until Element is located:
			//===============================			
			WebDriverWait wait = new WebDriverWait(driver.getWebDriver(), timeout);
			wait.until(ExpectedConditions.presenceOfElementLocated(Locator));
			// Reporting is not given to avoid unnecessary Report steps:
		} catch (Exception e) {
		}
	}
	/****************************************************************************************
	  Function Name: smartUM_RC_highlightElement
	  Description : Highlight the Element
	  Created By : Karthikeyan.J
	  Created Date :08-6-2019
	  Modified By:  Nil
	  Modified Date: Nil	   
	 ***************************************************************************************/
	public void smartUM_RC_HighlightElement(By Locator) {
		try {
			JavascriptExecutor js = (JavascriptExecutor) driver.getWebDriver();
			for (int iloop = 0; iloop <= 1; iloop++)
			{
				//js.executeScript("arguments[0].setAttribute('style', arguments[1]);", driver.getWebDriver().findElement(Locator),"color: black; border: 2px solid black;");

				js.executeScript("arguments[0].style.border='5px solid green';",driver.getWebDriver().findElement(Locator));

				Thread.sleep(200); //Hardcode wait for highlighting

				//js.executeScript("arguments[0].style.border='1px solid grey';",driver.getWebDriver().findElement(Locator));
				Thread.sleep(200); //Hardcode wait for highlighting
			}
		} catch (Exception e) {

		}
	}
	

	/****************************************************************************************
					  Function Name: smartUM_ReusableComponents_ClickElement
					  Description : Click on element based on Locator value
					  Created By : Karthikeyan.J
					  Created Date :08-6-2019
					  Modified By:  Nil
					  Modified Date: Nil	   
	 ***************************************************************************************/
	public boolean smartUM_RC_ClickElement(By Locator, String sReportText)
	{
		boolean found = false;
		try
		{
			smartUM_RC_ExplicitWait(Locator, 10, "Waiting for element" + sReportText);
			smartUM_RC_ScrolltoElement(Locator);

			// Click on element after verifying element is enabled & displayed:
			// ================================================================
			if (smartUM_RC_isDisplayed(Locator))
			{
				if (smartUM_RC_isEnabled(Locator, sReportText))
				{
					driver.getWebDriver().findElement(Locator).click();
					report.updateTestLog("Clicking the button" + sReportText, sReportText+" is clicked." , Status.DONE);
					found = true;
				}

			}
			Thread.sleep(2000);
			return found;
		} catch (Exception e) 
		{
			report.updateTestLog("Exception occured ", e.getMessage(), Status.FAIL);
			return found;
		}
	}
	/****************************************************************************************
					  Function Name: smartUM_RC_clickJavascript
					  Description : Click on element using Javascript
					  Created By : Karthikeyan.J
					  Created Date :08-6-2019
					  Modified By:  Nil
					  Modified Date: Nil	   
	 ***************************************************************************************/
	public void smartUM_RC_Click_Javascript(By Locator,String sReportText) {
		try {			
			smartUM_RC_ExplicitWait(Locator, 10, "Waiting for element" + "wait");
			smartUM_RC_ScrolltoElement(Locator);
			WebElement element = driver.getWebDriver().findElement(Locator);
			JavascriptExecutor js = ((JavascriptExecutor) driver.getWebDriver());
			js.executeScript("arguments[0].click()", element);		
			report.updateTestLog("Click on Element","Click on"+sReportText+"successfully", Status.DONE);
		} catch (Exception e) {
			report.updateTestLog("Exception occured in Clicking", e.getMessage(), Status.FAIL);
		}
	}


	/****************************************************************************************
					  Function Name: smartUM_RC_ClickButton
					  Description : Click button based on value
					  Created By : Karthikeyan.J
					  Created Date :08-6-2019
					  Modified By:  Nil
					  Modified Date: Nil	   
	 ***************************************************************************************/
	public void smartUM_RC_ClickButton(String containsvalue, String sReportText) {
		try {			
			//Button common Locator based on value:
			//======================================
			String Locator = "//button[contains(text(),'" + containsvalue + "')]";
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(Locator)));
			smartUM_RC_ClickElement(By.xpath(Locator),containsvalue);
			report.updateTestLog("Clicking the button" + sReportText, sReportText + " is clicked ", Status.DONE);
		} catch (Exception e) {
			report.updateTestLog("Clicking the button" + sReportText, sReportText + " is clicked ", Status.FAIL);
		}
	}

	public void highlight_WebElement(WebElement element) {
		for (int i = 0; i < 1; i++) {
			JavascriptExecutor js = (JavascriptExecutor) driver.getWebDriver();
			js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element,
					"color: black; border: 2px solid black;");
		}
	}

	/****************************************************************************************
					  Function Name: smartUM_RC_isDisplayed
					  Description : Verify the Element is displayed or not
					  Created By : Karthikeyan.J
					  Created Date :08-6-2019
					  Modified By:  Nil
					  Modified Date: Nil	   
	 ***************************************************************************************/
	public boolean smartUM_RC_isDisplayed(By Locator) {
		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(Locator));
			WebElement element = driver.getWebDriver().findElement(Locator);

			// Verify Element is Displayed or not:
			// ==================================
			if (element.isDisplayed())
			{
				highlight_WebElement(element);
				return true;
			}
			else
				return false;
		} catch (Exception e)
		{
			return false;
		}
	}
	/****************************************************************************************
	  Function Name: smartUM_RC_isNotDisplayed
	  Description : Verify the Element is not displayed
	  Created By : Karthikeyan.J
	  Created Date :08-6-2019
	  Modified By:  Nil
	  Modified Date: Nil	   
	 ***************************************************************************************/

	public boolean smartUM_RC_isNotDisplayed(By Locator)
	{
		try
		{
			WebElement element = driver.getWebDriver().findElement(Locator);
			// Verify Element is Displayed or not:
			// ==================================
			if (element.isDisplayed())
				return false;
			else
				return true;
		}
		catch (Exception e) 
		{

			return true;
		}
	}


	/****************************************************************************************
					  Function Name: smartUM_RC_isEnabled
					  Description : Verify the Element is enabled or not
					  Created By : Karthikeyan.J
					  Created Date :08-6-2019
					  Modified By:  Nil
					  Modified Date: Nil	   
	 ***************************************************************************************/
	public boolean smartUM_RC_isEnabled(By Locator, String sReportText) {
		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(Locator));
			WebElement element = driver.getWebDriver().findElement(Locator);
			if(element.isDisplayed())
			{
				report.updateTestLog("Check Element Enabled.", sReportText + " is Displayed. ",Status.DONE);
				// Verify Element is Enabled:
				// ===========================
				if (element.isEnabled())
				{
					report.updateTestLog("Check Element Enabled.", sReportText + " is Enabled ",Status.DONE);
					return true;
				} else
				{
					report.updateTestLog("Check Element Enabled.", sReportText + " is not Enabled ", Status.DONE);
					return false;
				}
			}
			else
				return false;
		} catch (Exception e) 
		{
			report.updateTestLog("Exception Occured","Exception Occured in isDisabled - "+ e.getMessage(), Status.FAIL);
			return false;
		}
	}
	/****************************************************************************************
	  Function Name: smartUM_RC_isDisabled
	  Description : Verify the Element is Disabled or not
	  Created By : Karthikeyan.J
	  Created Date :08-6-2019
	  Modified By:  Nil
	  Modified Date: Nil	   
	 ***************************************************************************************/
	public boolean smartUM_RC_isDisabled(By Locator, String sReportText)
	{
		try 
		{
			wait.until(ExpectedConditions.visibilityOfElementLocated(Locator));
			WebElement element = driver.getWebDriver().findElement(Locator);

			if(element.isDisplayed())
			{
				report.updateTestLog("Check Element Disabled.", sReportText + " is Displayed. ",Status.DONE);
				// Verify Element is Disabled:
				// ===========================
				if (!element.isEnabled())
				{
					report.updateTestLog("Check Element Disabled.", sReportText + " is Disabled.",Status.DONE);
					return true;
				} else 
				{
					report.updateTestLog("Check Element Disabled.", sReportText + " is not Disabled.", Status.DONE);
					return false;
				}
			}
			else
				return false;	

		} catch (Exception e) 
		{
			report.updateTestLog("Exception Occured in isDisabled", e.getMessage(), Status.FAIL);
			return false;
		}
	}


	/****************************************************************************************
					  Function Name: smartUM_RC_Weblist
					  Description : Select dropdown based on value
					  Created By : Karthikeyan.J
					  Created Date :08-6-2019
					  Modified By:  Nil
					  Modified Date: Nil	  
	 ***************************************************************************************/
	public void smartUM_RC_Weblist(By Dropdown, By UlLocator, String Value) throws InterruptedException {
		try {
			// Click Dropdown to load Ordered List(//li) in DOM at runtime:
			// ===========================================================
			driver.getWebDriver().findElement(Dropdown).click();

			// Based on Dropdown value click on Ordered List(//li) element:
			// =========================================================
			List<WebElement> list = driver.getWebDriver().findElements(UlLocator);
			for (int iloop = 0; iloop < list.size(); iloop++) {
				if (list.get(iloop).getText().trim().equalsIgnoreCase(Value)) {
					list.get(iloop).click();
					report.updateTestLog("Dropdown", "Dropdown value:" + Value + "is selected", Status.DONE);
					break;
				}
			}
		} catch (Exception e) {
			report.updateTestLog("Dropdown", "Dropdown is not displayed" + e.getMessage(), Status.FAIL);
		}
	}
	/****************************************************************************************
					  Function Name: smartUM_RC_ValidateWeblist
					  Description : Verify dropdown values
					  Created By : Karthikeyan.J
					  Created Date :08-6-2019
					  Modified By:  Nil
					  Modified Date: Nil	  
	 ***************************************************************************************/
	public void smartUM_RC_ValidateWeblist(By Dropdown, By UlLocator, String Value) throws InterruptedException {
		try {
			ArrayList<String> actualList = new ArrayList<String>();
			ArrayList<String> expectedList = new ArrayList<String>();

			// Click Dropdown to load Ordered List(//li) in DOM at runtime:
			// ===========================================================
			driver.getWebDriver().findElement(Dropdown).click();

			// Based on Dropdown value click on Ordered List(//li) element:
			// ===========================================================	
			List<WebElement> list = driver.getWebDriver().findElements(UlLocator);

			//Fetch values from Dropdown:
			//==========================
			for (int iloop = 0; iloop < list.size(); iloop++) {
				actualList.add(list.get(iloop).getText().trim());
			}
			//Fetch values from Datasheet:
			//===========================
			String temp[]=Value.split(";");  // In datasheet give the dropdown values with' ;' delimiter
			for(String value:temp){
				expectedList.add(value);				
			}
			//Sort and compare the Dropdown values:
			//====================================
			Collections.sort(actualList);
			Collections.sort(expectedList);
			if(actualList.equals(expectedList)){
				report.updateTestLog("Validate Weblist", "Expected values are displayed in dropdown"+actualList, Status.DONE);
			}else{
				report.updateTestLog("Validate Weblist", "Expected values are not displayed in dropdown"+actualList, Status.FAIL);
			}
		} catch (Exception e) {
			report.updateTestLog("Exception Occured in Dropdown",e.getMessage(), Status.FAIL);
		}
	}


	/****************************************************************************************
					  Function Name: smartUM_RC_SelectCheckbox
					  Description : Select/Unselect checkbox based on value
					  Created By : Karthikeyan.J
					  Created Date :08-6-2019
					  Modified By:  Nil
					  Modified Date: Nil	  
	 ***************************************************************************************/

	public void smartUM_RC_SelectCheckbox(String containsvalue, String sReportText) {
		try {
			// Checkbox common Locator based on value:
			// ======================================
			String Locator = "//label[@class='CheckboxGroup---choice_label'][contains(text(),'" + containsvalue + "')]";
			smartUM_RC_ClickElement(By.xpath(Locator), containsvalue);
			report.updateTestLog("Select checkbox: " + containsvalue, sReportText + " is selected ", Status.DONE);
		} catch (Exception e) {
			report.updateTestLog("Select checkbox: " + containsvalue, sReportText + " is not selected ", Status.FAIL);
		}
	}
	/****************************************************************************************
					  Function Name: smartUM_RC_SetText
					  Description : Set value in Textbox
					  Created By : Karthikeyan.J
					  Created Date :08-6-2019
					  Modified By:  Nil
					  Modified Date: Nil	   
	 ***************************************************************************************/
	public boolean smartUM_RC_SetText(By Locator, String sText, String sReportText) {
		try {
			//Set Clipboard data:
			//===================
			StringSelection stringSelection = new StringSelection(sText);
			Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection, null);

			//Paste Clipboard data:
			//=====================
			smartUM_RC_ScrolltoElement(Locator);
			smartUM_RC_ExplicitWait(Locator, 5, "");
			driver.getWebDriver().findElement(Locator).clear();
			if (smartUM_RC_isDisplayed(Locator)) {				
				driver.getWebDriver().findElement(Locator).sendKeys(Keys.chord(Keys.CONTROL, "v"));
				Thread.sleep(2000); // Hardcode wait to perform key tab
				driver.getWebDriver().findElement(Locator).sendKeys(Keys.chord(Keys.TAB));
			}
			return true;
		} catch (Exception e) {
			report.updateTestLog("Exception Occured in SetText" , e.getMessage(), Status.FAIL);
			return false;
		}
	}

	/****************************************************************************************
					  Function Name: smartUM_RC_getAttribute
					  Description : Get Attribute value for the element
					  Created By : Karthikeyan.J
					  Created Date :08-6-2019
					  Modified By:  Nil
					  Modified Date: Nil	   
	 ***************************************************************************************/
	public String smartUM_RC_getAttribute(By Locator, String Attribute, String sReportText) {
		String value = "";
		try {
			WebElement element = driver.getWebDriver().findElement(Locator);

			// Retrieve Attribute value from Element:
			// =====================================
			if (element.isDisplayed()) {
				value = element.getAttribute(Attribute);
				return value;
			} else {
				return value;
			}
		} catch (Exception e) {
			report.updateTestLog("Exception Occured.","Exception Occured in getAttibute - "+e.getMessage(), Status.FAIL);
			return value;
		}
	}
	/****************************************************************************************
					  Function Name: smartUM_RC_getText
					  Description : Get text value for the element
					  Created By : Karthikeyan.J
					  Created Date :08-6-2019
					  Modified By:  Nil
					  Modified Date: Nil	   
	 ***************************************************************************************/
	public String smartUM_RC_getText(By Locator, String sReportText) {
		String value = "";
		try {
			WebElement element = driver.getWebDriver().findElement(Locator);

			// Retrieve innertext from Element:
			// ================================
			if (element.isDisplayed()) 
			{
				if (!element.getText().trim().isEmpty())
				{
					value = element.getText().trim();
				}
				else 
				{	
					if (!element.getText().trim().contains("Select "))
					{
						value = element.getText().trim();
					}
					else
					{
						if (!element.getAttribute("value").isEmpty())
							value = element.getAttribute("value").trim();
					}
				}
			}
		} catch (Exception e)
		{
			report.updateTestLog("Get Element Test or Value.","Exception Occured in getText - "+ e.getMessage(), Status.FAIL);

		}
		return value;
	}
	public boolean smartUM_RC_UpdateDataInSheet(By Locator, String SheetName,String ColumnName)
	{
		boolean value = false;
		try {
			WebElement element = driver.getWebDriver().findElement(Locator);

			// Retrieve innertext from Element:
			// ================================
			if (element.isDisplayed())
			{
				String Text = element.getText().trim();
				dataTable.putData(SheetName, ColumnName, Text);
				value = true;
			} 
			return value;
		} catch (Exception e)
		{
			report.updateTestLog("Exception Occured.","Exception occurred when updating the test in Datasheet - "+ e.getMessage(), Status.FAIL);
			return value;
		}
	}

	public boolean smartUM_RC_ElementisEmpty(By Locator, String sReportText)
	{
		try 
		{
			wait.until(ExpectedConditions.visibilityOfElementLocated(Locator));
			WebElement element = driver.getWebDriver().findElement(Locator);			
			// Retrieve innertext from Element:
			// ================================
			if (element.isDisplayed()) 
			{
				if (element.getText().trim().isEmpty())
				{
					return true;
				} else 
				{
					if (element.getText().trim().contains("Select"))
					{
						return true;
					}
					else
					{
						report.updateTestLog("Check Element is Empty." + sReportText+" is Empty.", sReportText + " is not Empty.", Status.DONE);
						return false;
					}
				}
			}
			else
				return false;
		} catch (Exception e)
		{
			report.updateTestLog("Exception Occured.","Validating the Element is Empty - "+ e.getMessage(), Status.FAIL);
			return false;
		}


	}

	public boolean smartUM_RC_ElementisNotEmpty(By Locator, String sReportText)
	{		
		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(Locator));
			WebElement element = driver.getWebDriver().findElement(Locator);
			// Retrieve innertext from Element:
			// ================================
			if (element.isDisplayed()) 
			{
				if (element.getText().trim().isEmpty())
				{
					report.updateTestLog("Check Element is not Empty.", sReportText + " is Empty.",Status.DONE);
					return false;
				} else
				{
					if (element.getText().trim().contains("Select"))
					{
						report.updateTestLog("Check Element is Empty.", sReportText + " is Empty.",Status.DONE);
						return false;
					}
					else
					{
						report.updateTestLog("Check Element is not Empty.", sReportText + " is not Empty.", Status.DONE);
						return true;
					}
				}
			}

		} catch (Exception e) {
			report.updateTestLog("Exception Occured.","Validating Element is not empty - "+e.getMessage(), Status.FAIL);
		}
		return false;
	}



	/****************************************************************************************
					  Function Name: smartUM_RC_isSelected
					  Description : Validate whether checkbox is selected or not
					  Created By : Karthikeyan.J
					  Created Date :08-6-2019
					  Modified By:  Nil
					  Modified Date: Nil	   
	 ***************************************************************************************/
	public boolean smartUM_RC_isSelected(String containsvalue, String sReportText) {
		try {
			String Locator = "//label[@class='CheckboxGroup---choice_label'][contains(text(),'" + containsvalue + "')]";
			boolean flag = driver.findElement(By.xpath(Locator)).isSelected();
			if (flag) {
				report.updateTestLog("Validate CheckBox", sReportText+"Checkbox is selected", Status.DONE);
			} else {
				report.updateTestLog("Validate CheckBox", sReportText+"Checkbox is not selected", Status.FAIL);
			}
			return true;
		} catch (Exception e) {
			report.updateTestLog("Exception Occured in SetText", e.getMessage(), Status.FAIL);
			return false;
		}
	}
	public boolean smartUM_RC_isSelected(By Locator, String sReportText) {
		try {
			WebElement element = driver.getWebDriver().findElement(Locator);

			// Verify Element is Displayed or not:
			// ==================================
			if (element.isSelected()) 
			{
				report.updateTestLog("Check element is selected.", sReportText + " is Selected.",
						Status.DONE);
				return true;
			} else {
				report.updateTestLog("Check element is selected.", sReportText + " is not Selected.",
						Status.DONE);
				return false;
			}
		} catch (Exception e) {
			report.updateTestLog("Exception Occured.","Exception occured while checking element is selected - "+ e.getMessage(), Status.FAIL);
			return false;
		}
	}

	public void smartUM_checkPageLoad() throws InterruptedException {
		String string = "//div[@id='appian-nprogress-parent']";
		boolean flag = true;
		Thread.sleep(1000);
		while (flag) {
			if (driver.getWebDriver().findElements(By.xpath(string)).size() > 0) {
				//System.out.println("Size=" + driver.getWebDriver().findElements(By.xpath(string)).size());
				Thread.sleep(1000);
			} else {
				//System.out.println("Size=" + driver.getWebDriver().findElements(By.xpath(string)).size());
				flag = false;
			}

		}
		// //System.out.println("count="+count);
		Thread.sleep(500);
	}


	/****************************************************************************************
					  Function Name: smartUM_RC_ProbingQuestion
					  Description : Select Probing Question
					  Created By : Karthikeyan.J
					  Created Date :08-6-2019
					  Modified By:  Nil
					  Modified Date: Nil  
	 ***************************************************************************************/
	public void smartUM_RC_ProbingQuestion(String Question, String Answer) throws Exception {
		try {
			// Dynamic Locator based on any Probing Question:
			// ==============================================
			if (Question != "") {
				String Locator = driver.getWebDriver()
						.findElement(By.xpath("//strong[contains(text(),'" + Question + "')]//parent::p//parent::div"))
						.getAttribute("aria-labelledby");
				Locator = "//div[@aria-labelledby='" + Locator + "']";
				
				smartUM_RC_ExplicitWait(By.xpath(Locator), 5, "wait");
				smartUM_RC_ScrolltoElement(By.xpath(Locator + "//p//a[1]"));

				// Selecting Yes or No Answer based on UserInput:
				// =============================================
				if (smartUM_RC_isDisplayed(By.xpath(Locator + "//p//a[1]")))
				{
					if (Answer.equalsIgnoreCase("Yes")) 
					{
						smartUM_RC_Click_Javascript(By.xpath(Locator + "//p//a[1]"),"Probing");
					} 
					else if (Answer.equalsIgnoreCase("No"))
						smartUM_RC_Click_Javascript(By.xpath(Locator + "//p//a[2]"),"Probing");
					else
						smartUM_RC_Click_Javascript(By.xpath(Locator + "//p//a[3]"),"Probing");
				}
			}
			report.updateTestLog("ProbingQuestion",
					"Probing Question:" + Question + "-->Answer:" + Answer + "is Selected", Status.DONE);
		} catch (Exception e) {
			report.updateTestLog("ProbingQuestion", "ProbingQuestion not displayed" + e.getMessage(), Status.DONE);}}
					
			
			
			
			
	
	/****************************************************************************************
					  Function Name: columnIndex
					  Description : Fetch column number based on column name
					  Created By : Karthikeyan.J
					  Created Date :08-6-2019
					  Modified By:  Nil
					  Modified Date: Nil	  
	 ***************************************************************************************/
	public int columnIndex(By Locator, String columnName) {
		try {
			int flag = 0;

			//Iterating through Column header in table: [//Locator value to table/th:]
			//=========================================			
			for (WebElement th : driver.getWebDriver().findElements(Locator)) {
				flag = flag + 1;
				if (!th.getText().trim().equalsIgnoreCase("Select") && !th.getText().trim().isEmpty()) {
					if (columnName.contains(th.getText().trim())) {
						return flag;
					}
				}
			}
			return 0;
		} catch (Exception e) {
			return 0;
		}
	}
	/****************************************************************************************
					  Function Name: verifySortingInTable
					  Description : Verify sorting in any table based on ColumnName
					  Created By : Karthikeyan.J
					  Created Date :08-6-2019
					  Modified By:  Nil
					  Modified Date: Nil	  
	 ***************************************************************************************/

	public boolean verifySortingInTable(String table, String columnName) {
		try {
			int column = columnIndex(By.xpath(table + "//th"), columnName);
			String tabledata = table + "//tbody//td[" + column + "]";
			String tableheader = table + "//th[" + column + "]";

			// Click on column header to verify sorting:
			// =========================================
			smartUM_ReusableComponents_ClickElement(By.xpath(tableheader),
					"Click on column header to verify sorting" + columnName);

			// Fetch table data list:
			// ======================
			ArrayList<String> tableList = new ArrayList<String>();
			List<WebElement> elementList = driver.getWebDriver().findElements(By.xpath(tabledata));
			for (WebElement td : elementList) {
				tableList.add(td.getText().trim());
			}

			// Creating sorted List from table data:
			// =====================================
			ArrayList<String> sortedList = new ArrayList<String>();
			for (String s : tableList) {
				sortedList.add(s);
			}
			Collections.sort(sortedList);

			// For checking Ascending order:
			// ==============================
			if (sortedList.equals(tableList)) {
				report.updateTestLog("Verify sorting in Ascending order",
						columnName + "is displayed in Ascending order", Status.PASS);
			} else {
				report.updateTestLog("Verify sorting in Ascending order",
						columnName + "is not displayed in Ascending order", Status.FAIL);
			}

			// For checking Descending order:
			// ==============================
			Collections.reverse(sortedList);
			if (sortedList.equals(tableList)) {
				report.updateTestLog("Verify sorting in Descending order",
						columnName + "is displayed in Descending order", Status.PASS);
			} else {
				report.updateTestLog("Verify sorting in Descending order",
						columnName + "is not displayed in Descending order", Status.FAIL);
			}
			return true;
		} catch (Exception e) {
			report.updateTestLog("Exception in Sorting", e.getMessage(), Status.FAIL);
			return false;
		}
	}


	//*****************************************************************************************************************************************************************************************************************************/

	//******************************Reusable Components***********************************************************//

	//Verify the Specified element is Clickable
	public boolean smartUM_ReusableComponents_VerifyElementIsClickable(By Locator,String sReportText) 
	{

		try{
			wait.until(ExpectedConditions.visibilityOfElementLocated(Locator));
			if (driver.findElement(Locator).isDisplayed())
			{
				report.updateTestLog("Verify element is displayed.", sReportText + " is displayed." , Status.DONE);
				WebDriverWait wait1 = new WebDriverWait(driver.getWebDriver(),6); 
				wait1.until(ExpectedConditions.elementToBeClickable(Locator));
				report.updateTestLog("Verify element is displayed.", sReportText + " is Clickable." , Status.DONE);
				return true;
			}
			else
				return false;

		}
		catch(Exception e)
		{
			report.updateTestLog("Verify element is displayed.", sReportText + " is not Clickable." , Status.FAIL);
			return false;	
		}
	}

	//Verify the Specified element is Not Clickable
	public boolean smartUM_ReusableComponents_VerifyElementIsNotClickable(By Locator,String sReportText) 
	{

		try{
			wait.until(ExpectedConditions.visibilityOfElementLocated(Locator));
			if (driver.findElement(Locator).isDisplayed())
			{
				report.updateTestLog("Verify element is Clickable.", sReportText + " is displayed." , Status.DONE);
				WebDriverWait wait1 = new WebDriverWait(driver.getWebDriver(),6); 
				wait1.until(ExpectedConditions.elementToBeClickable(Locator));
				//System.out.println(driver.findElement(Locator).getAttribute("aria-readonly"));
				//System.out.println(driver.findElement(Locator).getAttribute("aria-disabled"));
				//System.out.println(driver.findElement(Locator).getAttribute("readonly"));
				//System.out.println(driver.findElement(Locator).getAttribute("disabled"));
				report.updateTestLog("Verify element is Clickable.", sReportText + " is Clickable." , Status.FAIL);
				return true;
			}
			else
				return false;

		}
		catch(Exception e)
		{
			report.updateTestLog("Verify element is Clickable.", sReportText + " is not Clickable." , Status.DONE);
			return false;	
		}
	}

	public boolean smartUM_ReusableComponents_Click_WebElement(By Locator, int timeout,String sReportText,boolean IgnoreReporting,boolean ScreenShot) 
	{

		try{
			wait.until(ExpectedConditions.visibilityOfElementLocated(Locator));
			wait.until(ExpectedConditions.elementToBeClickable(Locator));
			if (driver.findElement(Locator).isDisplayed())
			{
				driver.findElement(Locator).click();
				if ((IgnoreReporting) && (ScreenShot))
					report.updateTestLog("Clicking the " + sReportText, sReportText + " is clicked " , Status.DONE);
				else if ((!IgnoreReporting) && (ScreenShot))
					report.updateTestLog("Clicking the " + sReportText, sReportText + " is clicked " , Status.SCREENSHOT);
				return true;}
			else				
				report.updateTestLog("Clicking the " + sReportText, "WebElement "+ sReportText +"is not exists", Status.FAIL);
			return false;
		}
		catch(Exception e)
		{
			report.updateTestLog("Clicking the " + sReportText+" element Text.", "<FONT COLOR=RED>"+sReportText+" is not Displayed.</FONT>", Status.FAIL);
			return false;	
		}
	}


	/**
	 * This method is to clear the value in the text box and simulate typing
	 * into an element, which may set its value. <br>
	 * <br>
	 * 
	 * @author Cognizant
	 * @param Locator
	 *            By - To Locate the object in which the elements to be Located
	 * @param sText
	 *            String - input values
	 * @para sReportText String - Reporting Text which needs to be displayed in
	 *       the Text
	 * 
	 * 
	 * @see Selenium#sendKeys(Keys)
	 * @see Selenium#skipExecution()
	 * @exception Exception
	 *                - this exception is marked as fail in the report
	 */
	public boolean smartUM_ReusableComponents_ClearTextbox(By Locator, String sReportText) 
	{
		driver.findElement(Locator).clear();

		report.updateTestLog("Clearing and Entering the text.", "The Text  is entered in "
				+ sReportText, Status.DONE);
		return true;
	}

	public boolean smartUM_ReusableComponents_ClearAndSendText(By Locator, String Value,String sReportText) 
	{
		wait.until(ExpectedConditions.visibilityOfElementLocated(Locator));
		driver.findElement(Locator).clear();
		//System.out.println(sText);
		driver.findElement(Locator).sendKeys(Value);
		report.updateTestLog("Clearing and Entering the text.", "The Text "+Value+ " is entered in "+ sReportText, Status.DONE);
		driver.findElement(Locator).sendKeys(Keys.TAB);
		return true;
	}


	public boolean smartUM_ReusableComponents_ValidateCheckBox(By Locator,String sReportText) {

		boolean bol=driver.findElement(Locator).isSelected();
		if(bol)
			report.updateTestLog("Verify Checkbox is selected." ,  sReportText + " is selected successfully ", Status.DONE);
		else
			report.updateTestLog("Verify Checkbox is selected." ,  sReportText + " is not selected successfully ", Status.FAIL);
		return true;
	}

	/**
	 * Abort the execution
	 * 
	 * @author Cognizant
	 */
	public void smartUM_ReusableComponents_SkipExecution() {
		// bSkipExecution = true;
		report.updateTestLog("skipExecution", "~~ Execution Aborted ~~",
				Status.FAIL);
		// frameworkParameters.setStopExecution(true);
	}

	/**
	 * This method helps to find the element and click it <br>
	 * <br>
	 * 
	 * @author Cognizant
	 * 
	 * @param element
	 *            - Locator used to find the element
	 *
	 * @exception Exception
	 * @see Selenium#skipExecution()
	 * 
	 */

	public void  smartUM_ReusableComponents_ValidateText(By Locator,String val)
	{
		String text = driver.findElement(Locator).getText().trim();
		if(text.contains(val)||text.equalsIgnoreCase(val))
		{
			report.updateTestLog("Validate "+ val,"expedted is::<FONT COLOR=GREEN> "+val+"</FONT>Actual is :: <FONT COLOR=BLUE>"+text+"</FONT>",Status.DONE);
		}
		else{
			report.updateTestLog("Validate "+ val,"expedted is::<FONT COLOR=GREEN> "+val+"</FONT>Actual is :: <FONT COLOR=RED>"+text+"</FONT>",Status.FAIL);
		}

	}

	public void  smartUM_ReusableComponents_Validate_FieldValue(By Locator,String FieldName,String Col_Header) {
		String value =driver.findElement(Locator).getText().trim();
		String text = dataTable.getData("General_Data",Col_Header);
		//System.out.println(text);

		if(value.equalsIgnoreCase(text)){
			report.updateTestLog("Validate the Text","Verified value of "+FieldName+" is displayed as "+text,Status.DONE);
		}else{
			report.updateTestLog("Validate the Text","Verified value of "+FieldName+" is not displayed as "+text,Status.FAIL);
		}

	}




	/**
	 * This method is to wait for checking that an element is either invisible
	 * or not present on the DOM <br>
	 * <br>
	 * 
	 * @author Cognizant
	 * 
	 * @param Locator
	 *            By - Locator used to find the element
	 * @paramsReportText String - reporting Text
	 * @param lTimeOut
	 *            - long
	 * 
	 * @see Report#StepPass
	 * @see Report#StepFail
	 * @exception Exception
	 *                - this exception is marked as fail in the report
	 */

	boolean smartUM_ReusableComponents_visibilityOfElementLocated(By Locator, String sReportText,
			long lTimeOut) {

		if (lTimeOut == 0)
			lTimeOut = 360;
		WebDriverWait wait;
		wait = new WebDriverWait(driver.getWebDriver(), lTimeOut);
		wait.until(ExpectedConditions.visibilityOfElementLocated(Locator));
		report.updateTestLog(
				"Waiting for the " + sReportText + " to Disappear", sReportText
				+ " is disappeared ", Status.DONE);
		return true;

	}

	/**
	 * This method helps to Select all options that display the text matching
	 * with argument <br>
	 * <br>
	 * 
	 * @author Cognizant
	 * 
	 * @param Locator
	 *            By - Locator used to find the element
	 * @param sText
	 *            String - text to select
	 * @param sReportText
	 *            String - Text which is used for the Reporting Purpose
	 *
	 *
	 * @exception Exception
	 *                - this exception is marked as fail in the report
	 */
	public boolean smartUM_ReusableComponents_SelectByText(By Locator, String sText, String sReportText) {
		new Select(driver.findElement(Locator)).selectByVisibleText(sText);
		report.updateTestLog("Select Value by Text.", sText
				+ " is selected in the " + sReportText, Status.DONE);
		return true;
	}

	public boolean smartUM_ReusableComponents_SelectByText_Name(By Locator,By Locator1, String sText,
			String sReportText) throws InterruptedException  {
		driver.findElement(Locator).click();				
		driver.findElement(Locator1).click();		

		report.updateTestLog("Select Value by Name.", sText
				+ " is selected in the " + sReportText, Status.DONE);
		return true;
	}

	public boolean smartUM_ReusableComponents_SelectByText_Tab(By Locator, String sText,
			String sReportText) throws InterruptedException  {
		//driver.findElement(Locator).click();				
		driver.findElement(Locator).sendKeys(sText);		
		//driver.findElement(Locator).sendKeys(Keys.CONTROL.toString());
		driver.findElement(Locator).sendKeys(Keys.ENTER);
		//Thread.sleep(1000);
		driver.findElement(Locator).sendKeys(Keys.TAB);
		Thread.sleep(1000);
		report.updateTestLog("Select Value", sText
				+ " is selected in the " + sReportText, Status.DONE);
		return true;
	}




	public boolean mouseHover(WebElement oElement, String sReportText)
	{

		try
		{

			Actions builder = new Actions(driver.getWebDriver());
			builder.moveToElement(oElement).perform();		

			report.updateTestLog("Clicking the " + sReportText  , sReportText + " is clicked " ,Status.DONE);
			return true;
		}

		catch(Exception e)
		{
			report.updateTestLog("Clicking the " + sReportText, "Exception while clicking the "+ sReportText + " " + e.getMessage() , Status.FAIL);
			return false;
		}

	}




	public boolean smartUM_ReusableComponents_SelectByText_Tab2(By Locator, String sText,
			String sReportText) throws InterruptedException  {
		//driver.findElement(Locator).click();
		//Thread.sleep(1000);
		driver.findElement(Locator).sendKeys(sText);		
		Actions results1 = new Actions(driver.getWebDriver());
		results1.clickAndHold().sendKeys(Keys.ENTER);
		//results1.moveToElement(driver.findElement(Locator)).doubleClick().build().perform();
		driver.findElement(Locator).sendKeys(Keys.TAB);
		Thread.sleep(1000);
		report.updateTestLog("Select Value.", sText
				+ " is selected in the " + sReportText, Status.DONE);
		return true;
	}


	public boolean smartUM_ReusableComponents_SelectByText_Tab1(By Locator,By Locator1,String sText,
			String sReportText) throws InterruptedException  {
		driver.findElement(Locator).click();	

		WebDriverWait wait = new WebDriverWait(driver.getWebDriver(),30);
		WebElement element = wait.until(
				ExpectedConditions.elementToBeClickable(Locator1));
		element.getAttribute("attribute name");

		// driver.findElement(Locator1).click();
		Thread.sleep(1000);
		//driver.findElement(By.xpath("//div[text()='" + sText + "']")).click();		

		driver.findElement(Locator).sendKeys(Keys.TAB);
		Thread.sleep(1000);
		report.updateTestLog("Select Value.", sText
				+ " is selected in the " + sReportText, Status.DONE);
		return true;
	}



	/**
	 * This method is used to validate the text of the element against the
	 * expected text <br>
	 * <br>
	 * 
	 * @author Cognizant
	 * 
	 * @param Locator
	 *            By - Locator used to find the element
	 * @param sText
	 *            String - text to validate
	 * @param sReportText
	 *            String - Text which is used for the Reporting Purpose
	 *
	 *
	 * @exception Exception
	 *                - this exception is marked as fail in the report
	 */

	public boolean smartUM_ReusableComponents_CheckWebElementText_Split(By Locator, String sText,String sReportText) 
	{
		String sActualText = driver.findElement(Locator).getText().trim();

		if (sText.equalsIgnoreCase(sActualText.substring(0, 1).trim())) {
			report.updateTestLog("Check Element Text.", sActualText
					+ " is displayed as the " + sReportText, Status.DONE);
			return true;
		} else {
			report.updateTestLog("Check Element Text.", sActualText
					+ " is displayed as the " + sReportText + " instead of "
					+ sText, Status.FAIL);
			return false;
		}
	}



	public boolean smartUM_ReusableComponents_CheckWebElementText_upper(By Locator, String sText,String sReportText)
	{
		String sActualText = driver.findElement(Locator).getText().trim();
		if (sText.toUpperCase().trim().equalsIgnoreCase(sActualText.trim())) {
			report.updateTestLog("Check Element Text.", sActualText
					+ " is displayed as the " + sReportText, Status.SCREENSHOT);

			return true;
		} else {
			report.updateTestLog("Check Element Text.", sActualText
					+ " is displayed as the " + sReportText + " instead of "
					+ sText, Status.FAIL);
			return false;
		}
	}


	/**
	 * This method is used to Close the current Window <br>
	 * <br>
	 * 
	 * @author Cognizant
	 *
	 *
	 * @exception Exception
	 *                - this exception is marked as fail in the report
	 */
	void smartUM_ReusableComponents_CloseWindow() 
	{
		String sTitle = driver.getTitle();
		driver.close();
		report.updateTestLog("Closing Window", "Current Window Closed."
				+ sTitle, Status.PASS);

	}
	boolean smartUM_ReusableComponents_ValidatePageTitle(String pageTitle) {
		boolean flag=false;
		String sTitle = driver.getTitle();
		if(sTitle.equalsIgnoreCase(pageTitle.trim())){
			report.updateTestLog("Page Title Validation", "Current window is in "+ sTitle, Status.DONE);
			flag=true;

		}else{
			report.updateTestLog("Page Title Validation", "Current window is not  in "+ sTitle, Status.FAIL);
			flag=false;
		}
		return flag;


	}


	public boolean smartUM_ReusableComponents_CheckNegativeElement(By Locator, String sReportText)
	{
		try {
			driver.findElement(Locator);
			report.updateTestLog("Check Element not displayed.",
					sReportText + " is displayed in the screen", Status.FAIL);
			return true;
		} catch (NoSuchElementException e) {
			report.updateTestLog("Check Element not displayed.",
					sReportText + " is not displayed in the screen",
					Status.PASS);
			smartUM_ReusableComponents_SkipExecution();
			return false;
		}

	}

	public int smartUM_ReusableComponents_GetNoOfWebElements(By Locator, String sReportText) {
		List<WebElement> oWebElementList = driver.findElementsBy(Locator);
		int iCount = oWebElementList.size();
		report.updateTestLog(
				"Retrieving the number of elements " + sReportText, iCount
				+ " number of " + sReportText + "items displayed ",
				Status.PASS);
		return iCount;
	}

	// Scroll the Page up
	public boolean smartUM_ReusableComponents_scrollupPage()
	{
		JavascriptExecutor js = ((JavascriptExecutor) driver.getWebDriver());
		js.executeScript("window.scrollBy(0, -250)");
		report.updateTestLog("Scrolling up the page", "Screen Scrolled up", Status.DONE);
		return true;
	}

	// Scroll the Page down
	public boolean smartUM_ReusableComponents_scrolldownPage()
	{
		JavascriptExecutor js = ((JavascriptExecutor) driver.getWebDriver());
		js.executeScript("window.scrollBy(0, 250)");
		report.updateTestLog("Scrolling to the bottom of the page", "Screen Scrolled Down", Status.DONE);
		return true;
	}

	// Scroll the Page to Middle
	public boolean smartUM_ReusableComponents_scrollToMiddleOfPage() 
	{
		JavascriptExecutor js = ((JavascriptExecutor) driver.getWebDriver());
		js.executeScript("window.scrollTo(0, document.body.scrollHeight/2)");
		report.updateTestLog("Scrolling to the middle of the page", "Scrolled to the middle of the page", Status.DONE);
		return true;
	}

	public boolean smartUM_ReusableComponents_ScrolltoTopofPage() {
		JavascriptExecutor js = ((JavascriptExecutor) driver.getWebDriver());
		js.executeScript("window.scrollTo(0, 0)");
		report.updateTestLog("Scroll to top of page","Scrolled to top of the page",Status.DONE);
		return true;
	}

	public boolean smartUM_ReusableComponents_ScrolltoBottomfPage()
	{
		JavascriptExecutor js = ((JavascriptExecutor) driver.getWebDriver());
		js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
		report.updateTestLog("Scroll to bottom of page","Scrolled to bottom of the page", Status.DONE);
		return true;
	}

	public boolean smartUM_ReusableComponents_ScrolltoElement(By Locator)
	{
		JavascriptExecutor js = ((JavascriptExecutor) driver.getWebDriver());
		wait.until(ExpectedConditions.visibilityOfElementLocated(Locator));
		WebElement element = driver.findElement(Locator);
		js.executeScript("arguments[0].scrollIntoView(true);", element);
		report.updateTestLog("Scroll to specified element of page",
				"Scrolled to expected element of the page", Status.DONE);
		smartUM_RC_HighlightElement(Locator);
		smartUM_ReusableComponents_scrollupPage();
		return true;
	}

	//Scroll to the Webelement that visible
	public void ScrollTillWebElementVisible(By Locator, String sReportText)
	{
		//String sMethodName = new Object(){}.getClass().getEnclosingMethod().getName();
		WebElement element = driver.getWebDriver().findElement(Locator);
		JavascriptExecutor executor = (JavascriptExecutor) driver.getWebDriver(); 
		executor.executeScript("arguments[0].scrollIntoView(true);", element);
		smartUM_RC_HighlightElement(Locator);
		smartUM_ReusableComponents_scrollupPage();
		report.updateTestLog("Scroll to Element.", "Scroll to the Element "+sReportText, Status.DONE);

	}

	public boolean smartUM_ReusableComponents_CheckandAcceptAlert() {
		boolean alertAccepted = false;
		try {
			driver.switchTo().alert().accept();
			alertAccepted = true;
		} catch (Exception e) {
			alertAccepted = false;
		}
		return alertAccepted;
	}

	public void smartUM_ReusableComponents_ClickLinkByHref(String href)
	{
		try {
			List<WebElement> anchors = driver.findElementsBy(By.tagName("a"));
			Iterator<WebElement> i = anchors.iterator();
			while (i.hasNext()) {
				WebElement anchor = i.next();
				// if(anchor.getAttribute("href").contains(href)) {
				if (anchor.getText().trim().contains(href)) {

					anchor.click();
					break;
				}
			}

		} catch (Exception e) {
			//System.out.println(e.getMessage());
		}

	}
	public void smartUM_ReusableComponents_ExplicitWait(By Locator, int timeout, String sReportText) {
		try {
			WebDriverWait wait = new WebDriverWait(driver.getWebDriver(), timeout);
			wait.until(ExpectedConditions.presenceOfElementLocated(Locator));
			report.updateTestLog("Explicit Wait for element ", sReportText, Status.DONE);
		} catch (Exception e) {
			report.updateTestLog("Exception occured ", e.getMessage(), Status.FAIL);
		}

	} 


	public String smartUM_ReusableComponents_GetCurrentTimestamp() {
		// TODO Auto-generated method stub
		String strDateFormat = "MM/dd/yyyy";// Date format is Specified
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat(strDateFormat);
		String TimeStamp = sdf.format(date);
		return TimeStamp;
	}


	public String smartUM_ReusableComponents_GetTimestamp_Days_Set_nam(String days) {

		// TODO Auto-generated method stub
		Date objDate = new Date(); // Current System Date and time is assigned
		// to objDate
		//System.out.println(objDate);
		String strDateFormat = "MM/dd/yyyy";// Date format is Specified
		SimpleDateFormat objSDF = new SimpleDateFormat(strDateFormat);// Date
		// format
		// string
		// is
		// passed
		// as an
		// argument
		// to
		// the
		// Date
		// format
		// object
		//System.out.println(objSDF.format(objDate));// Date formatting is applied
		// to the current date
		Calendar c = Calendar.getInstance();
		String dateValue = days;
		String datesubValue = dateValue.substring(8, 12);
		if (datesubValue.contains("Plus")) {

			String finalDateValue = dateValue.substring(12);

			switch (finalDateValue) {
			
			case "Zero":
				c.add(Calendar.DATE, 0);
				String days_plusZero = objSDF.format(c.getTime());
				return days_plusZero;

			case "One":
				c.add(Calendar.DATE, 1);
				String days_plusOne = objSDF.format(c.getTime());
				return days_plusOne;
			case "Two":
				c.add(Calendar.DATE, 2);
				String days_plusTwo = objSDF.format(c.getTime());
				return days_plusTwo;

			case "Three":
				c.add(Calendar.DATE, 3);
				String days_plusThree = objSDF.format(c.getTime());
				return days_plusThree;

			case "Four":
				c.add(Calendar.DATE, 4);
				String days_plusFour = objSDF.format(c.getTime());
				return days_plusFour;

			case "Five":
				c.add(Calendar.DATE, 5);
				String days_plusFive = objSDF.format(c.getTime());
				return days_plusFive;
			default:

				// String strDateFormat = "MM/dd/yyyy";//Date format is
				// Specified
				Date date = new Date();
				SimpleDateFormat sdf = new SimpleDateFormat(strDateFormat);
				String TimeStamp = sdf.format(date);
				return TimeStamp;

			} // end of switch

		} else {

			String finalDateValue = dateValue.substring(13);
			String days_Minus = null;
			switch (finalDateValue) {

			case "One":
				c.add(Calendar.DATE, -1);
				String days_MinusOne = objSDF.format(c.getTime());
				return days_MinusOne;
			case "Two":
				c.add(Calendar.DATE, -2);
				String days_MinusTwo = objSDF.format(c.getTime());
				return days_MinusTwo;

			case "Three":
				c.add(Calendar.DATE, -3);
				String days_MinusThree = objSDF.format(c.getTime());
				return days_MinusThree;

			case "Four":
				c.add(Calendar.DATE, -4);
				String days_MinusFour = objSDF.format(c.getTime());
				return days_MinusFour;

			case "Five":
				c.add(Calendar.DATE, -5);
				String days_MinusFive = objSDF.format(c.getTime());
				return days_MinusFive;

			}// end of switch

		} // end of else
		return null;

	}

	// Inpatient National Pricing File Inquiry - Output web Table
	public void smartUM_ReusableComponents_VerifyWebTableResultsForInpatientnpfInquiryOutput(By xpath,String sExpected, String sExpected1)
	{
		//boolean bPassFalg = false;

		WebElement table = driver.findElement(xpath);
		List<WebElement> rows = table.findElements(By.tagName("tr"));
		for (int i = 0; i < rows.size(); i++)
		{
			List<WebElement> columns = rows.get(i).findElements(
					By.tagName("td"));

			String sColValue = columns.get(1).getText().trim();
			String sColValue1 = columns.get(2).getText().trim();

			if (sColValue.equals(sExpected) & sColValue1.equals(sExpected1)) 
			{
				driver.findElement(xpath).click();
				//bPassFalg = true;
				//return;
			}

		}

	}

	//Click and Paste the Text
	public boolean smartUM_ReusableComponents_ClickandPaste(String Value,By Locator) throws InterruptedException
	{
		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(Locator));
			wait.until(ExpectedConditions.elementToBeClickable(Locator));
			Thread.sleep(2000);
			smartUM_ReusableComponents_SetClipboardData(Value);
			driver.findElement(Locator).click();
			Thread.sleep(1000);
			driver.findElement(Locator).sendKeys(Keys.chord(Keys.CONTROL, "v"));
			Thread.sleep(2000);
			return true;
		} catch (Exception exp)
		{
			report.updateTestLog("pasteClipBoardData",
					"Error while pasting data - Error Message - "+ exp.getMessage(), Status.FAIL);
			return false;
		}
	}



	/**
	 * This method helps to change string into specified date format <br>
	 * <br>
	 * 
	 * @author Cognizant
	 * 
	 * @param date
	 *            - date value
	 * @throws ParseException 
	 *
	 * @exception Exception
	 * @see Selenium#skipExecution()
	 * 
	 */
	public String smartUM_DateFormat(String date) throws ParseException{
		//System.out.println( "givend ate"+date);

		String date1=date;
		SimpleDateFormat dateformat=new SimpleDateFormat("MM/dd/yyyy");
		String newdate=dateformat.format(dateformat.parse(date1));
		return newdate;

	}

	public String smartUM_ThroughDate(String requestunits) throws ParseException {

		int val = Integer.parseInt(requestunits);

		SimpleDateFormat dateformat = new SimpleDateFormat("MM/dd/yyyy");
		Calendar cal = Calendar.getInstance();

		cal.add(Calendar.DATE, val);

		String Todate = dateformat.format(cal.getTime());
		return Todate;

	}

	public void Weblist(By Dropdown, By Locator, String Value) throws InterruptedException 
	{
		driver.findElement(Dropdown).click();
		List<WebElement> list = driver.getWebDriver().findElements(Locator);
		//System.out.println("WebList===" + list.size());
		for (int iloop = 0; iloop < list.size(); iloop++) 
		{
			if (list.get(iloop).getText().trim().equalsIgnoreCase(Value)) 
			{
				list.get(iloop).click();
				break;
			}
		}
	}


	public void smartUM_SelectCheckboxes(By Locator, String locatorName) throws InterruptedException
	{
		try
		{
			wait.until(ExpectedConditions.visibilityOfElementLocated(Locator));
			List<WebElement> chkBxs = driver.findElements(Locator);			
			Thread.sleep(1000);
			for (int i = 0; i < chkBxs.size(); i++) 
			{

				chkBxs.get(i).click();
				Thread.sleep(1000);
			}
			report.updateTestLog("Select Checkboxes - "+locatorName,"Selected the Checkboxes - "+locatorName, Status.DONE);
		}
		catch(Exception e)
		{

		}

	}
	/********************************************************************************
	 * Description : Click View	Request link	     
	 * @param  : No		    
	 * @throws Exception
	 *********************************************************************************/

	public boolean smartUM_ReusableComponents_WebElementText_getAttribute(By Locator, String sText,String sReportText) {
		String sActualText = driver.findElement(Locator).getAttribute("value");				
		if (sText.toUpperCase().trim().equalsIgnoreCase(sActualText.trim())) {	

			report.updateTestLog("Check Element.", sActualText
					+ " is displayed as the " + sReportText, Status.PASS);
			return true;
		} else {
			report.updateTestLog("Check Element.", sActualText
					+ " is displayed as the " + sReportText + " instead of "
					+ sText, Status.FAIL);
			return false;
		}
	}

	/**
	 * This method is used to Check whether the Element with the given Locator is available or not
	 * <br><br>
	 * @param 	   Locator  By - The Locator using which we are going to identify the Element
	 * @param 	   sReportText String - The Reporting Text which will be displayed in the HTML Report
	 * @param 	   IgnoreReporting boolean - This is the boolean value which will decide whether the Reporting is needed in HTML Report or not
	 * @param 	   ScreenShot boolean - This is the boolean value which will decide whether the screenshot is needed in HTML Report or not
	 * @return     boolean(true/false) Value - Whether the Element with the Specified Locator is available in the Application or not
	 * @exception  Exception - this exception is marked as fail in the report
	 */   

	public boolean checkElement(By Locator, String sReportText,boolean IgnoreReporting,boolean ScreenShot)
	{
		if (bSkipExecution) return false;	
		try
		{	
			wait.until(ExpectedConditions.visibilityOfElementLocated(Locator));
			if (driver.findElement(Locator).isDisplayed())
			{
				if ((!IgnoreReporting) && (!ScreenShot))				
					report.updateTestLog("Check Element" + sReportText, sReportText + " is displayed in the screen" , Status.PASS);
				else if ((!IgnoreReporting) && (ScreenShot))
					report.updateTestLog("Check Element" + sReportText, sReportText + " is displayed in the screen" , Status.SCREENSHOT);
				return true;
			}
			else
			{
				if ((!IgnoreReporting) && (!ScreenShot))				
					report.updateTestLog("Check Element" + sReportText, sReportText + " is displayed in the screen" , Status.PASS);
				else if ((!IgnoreReporting) && (ScreenShot))
					report.updateTestLog("Check Element" + sReportText, sReportText + " is displayed in the screen" , Status.SCREENSHOT);
				return false;
			}
		}


		catch (NoSuchElementException e) 
		{
			if (!IgnoreReporting)
			{
				report.updateTestLog("Validating the presence of " + sReportText, sReportText + " is not displayed in the screen" , Status.FAIL);
				skipExecution();
			}
			return false;
		}	
		catch (Exception e) 
		{
			if (!IgnoreReporting)
			{
				report.updateTestLog("Validating the presence of " + sReportText, "Exception while checking the "+ sReportText + " " + e.getMessage() , Status.FAIL);
				skipExecution();
			}
			return false;
		}		
	}


	/********************************************************************************
	 * Description :  This method is used to skip the execution 
	 * @param  : No     
	 * @throws Exception

	 *********************************************************************************/	 

	public void skipExecution()
	{
		bSkipExecution = true;
		//report.updateTestLog("skipExecution", "~~Execution Stopped due to the Exception~~" , Status.FAIL);
		frameworkParameters.setStopExecution(true);
	}

	/********************************************************************************
	 * Description :  This method is Double Click
	 * @param  : No     
	 * @throws :Exception
	 * @author :        
	 *********************************************************************************/                                                         
	public boolean doubleclickElement(By Locator, String sReportText,boolean IgnoreReporting,boolean ScreenShot)
	{                                                                                                                                                                                  
		if (bSkipExecution) return false;
		try
		{
			WebElement element = driver.getWebDriver().findElement(Locator);
			Actions builder = new Actions(driver.getWebDriver());                                                                                                                   
			builder.doubleClick(element).build().perform();                                                         
			if ((!IgnoreReporting) && (!ScreenShot))
				report.updateTestLog("Clicking the " + sReportText  , sReportText + " is clicked " ,Status.DONE);
			else if ((!IgnoreReporting) && (ScreenShot))
				report.updateTestLog("Clicking the " + sReportText  , sReportText + " is clicked " ,Status.SCREENSHOT);
			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
			return true;

		}                    
		catch(Exception e)
		{
			report.updateTestLog("Clicking the " + sReportText, "Exception while clicking the "+ sReportText + " " + e.getMessage() , Status.FAIL);
			return false;
		}
	}
	//OLD**************************************************************************************************************


	//Get the boolean value - Verify Element text is displayed as expected.
	public boolean smartUM_RC_CheckElementText(By Locator, String sText) 
	{
		boolean value = true;
		try
		{
			String sActualText = driver.findElement(Locator).getText().trim();
			//System.out.println("ExpectedText:"+sText+"ActualText:"+sActualText+":"+sText.trim().contains(sActualText.trim()));
			if(!(sActualText.isEmpty()||sActualText==null))
			{
				if (sText.trim().contains(sActualText.trim())||sActualText.trim().equalsIgnoreCase(sText.trim()))
				{				

					value = true;
				}
				else 
				{
					value = false;
				}
			}
			else
				report.updateTestLog("Validating element Text.", sActualText+" <FONT COLOR=RED>Element Value is Empty.</FONT>", Status.FAIL);

		}
		catch(Exception e)
		{
			report.updateTestLog("Validating the element Text.", "<FONT COLOR=RED>Element is not displayed.</FONT>", Status.FAIL);
			value = false;
		}
		return value;
	}

	//Get the boolean status for Text or String is empty.		
	public boolean smartUM_RC_StringorText_isEmpty(String Value,String Name) 
	{
		if (Value.isEmpty()||Value==null)
		{
			report.updateTestLog("Verify Text is Empty.",Name+" Text is Empty",Status.DONE);		
			return true;
		}
		else 
		{
			report.updateTestLog("Verify Text is Empty.",Name+" Text is not Empty",Status.DONE);
			return false;
		}
	}

	//Get the boolean status for Text or String is not empty.		
	public boolean smartUM_RC_StringorText_isNotEmpty(String Value,String Name) 
	{
		if (Value.isEmpty()||Value==null)
		{
			report.updateTestLog("Verify Text is Not Empty.",Name+" Text is Empty",Status.DONE);
			return false;
		}
		else 
		{	
			report.updateTestLog("Verify Text is Not Empty.",Name+" Text is not Empty",Status.DONE);			
			return true;
		}
	}

	public boolean smartUM_RC_StringorText_isDisplayed(String Actual, String Expected,String Fieldname) 
	{
		if (Actual.trim().equalsIgnoreCase(Expected.trim()))
		{
			report.updateTestLog("Verify StringorText Displayed.",Fieldname+" Text is Displayed",Status.DONE);
			return true;
		}
		else if (Actual.trim().contains(Expected.trim()))
		{
			report.updateTestLog("Verify StringorText Displayed.",Fieldname+" Text is Displayed",Status.DONE);
			return true;
		}
		else 
		{
			report.updateTestLog("Verify StringorText Displayed.",Fieldname+" Text is not Displayed",Status.DONE);
			return false;
		}
	}

	public boolean smartUM_RC_StringorText_isNotDisplayed(String Actual, String Expected,String Fieldname) 
	{
		if (Actual.trim().equalsIgnoreCase(Expected.trim()))
		{
			report.updateTestLog("Verify StringorText not Displayed.",Fieldname+" Text is not Displayed",Status.DONE);
			return true;
		}
		else if (!Actual.trim().contains(Expected.trim()))
		{
			report.updateTestLog("Verify StringorText not Displayed.",Fieldname+" Text is not Displayed",Status.DONE);
			return true;
		}
		else 
		{
			report.updateTestLog("Verify StringorText not Displayed.",Fieldname+" Text is Displayed",Status.DONE);
			return false;
		}
	}

	//Check the specified Element is present
	public boolean smartUM_ReusableComponents_CheckElement(By Locator, String sReportText) 
	{
		try
		{
			driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
			driver.findElement(Locator).isDisplayed();
			report.updateTestLog("Check Element.",sReportText + " is displayed in the screen", Status.PASS);
			return true;
		}
		catch(Exception e)
		{
			report.updateTestLog("Check Element.",sReportText + " is not displayed.", Status.FAIL);	
			return false;
		}

	}


	//Verify the Specified Element is displayed
	public boolean smartUM_ReusableComponents_Displayed(By Locator, String sReportText) 
	{
		try
		{
			driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
			if(driver.findElement(Locator).isDisplayed())
			{
				smartUM_RC_HighlightElement(Locator);
				report.updateTestLog("Check Element.", sReportText + " is Displayed ",Status.PASS);
			}
			return true;
		}
		catch(Exception e)
		{
			report.updateTestLog("Check Element.", sReportText + " is not Displayed ",Status.FAIL);
			return false;
		}		
	}


	public boolean smartUM_ReusableComponents_NotDisplayed(By Locator, String sReportText) 
	{
		try
		{

			if(driver.findElement(Locator).isDisplayed())
				report.updateTestLog("Check Element not displayed.",sReportText + " is displayed in the screen", Status.FAIL);
			return false;
		}
		catch(Exception e)
		{
			report.updateTestLog("Check Element not Disabled.",sReportText + " is not displayed.", Status.PASS);	
			return true;
		}		
	}



	public boolean smartUM_ReusableComponents_IsEnabled(By Locator, String sReportText)
	{
		try{
			wait.until(ExpectedConditions.visibilityOfElementLocated(Locator));
			WebElement element  =driver.findElement(Locator);
			if(element.isDisplayed())
			{
				report.updateTestLog("Check Element is Enabled.",sReportText + " is displayed in the screen", Status.PASS);

				if(element.isEnabled())
				{
					report.updateTestLog("Check Element is Enabled.", sReportText	+ " is Enabled ", Status.PASS);
					return true;
				}
				else	
				{
					report.updateTestLog("Check Element is Enabled.", sReportText	+ " is Disabled ", Status.FAIL);
					return false;
				}			
			}
			else
				return false;
		}
		catch(Exception e)
		{
			report.updateTestLog("Check Element is Enabled.",sReportText + " is not displayed.", Status.FAIL);	
			return false;	
		}	

	}

	public boolean smartUM_ReusableComponents_IsDisabled(By Locator, String sReportText)
	{

		try{
			wait.until(ExpectedConditions.visibilityOfElementLocated(Locator));
			WebElement element  =driver.findElement(Locator);
			if(element.isDisplayed())
			{
				report.updateTestLog("Check Element is Disabled.",sReportText + " is displayed in the screen", Status.PASS);

				if(element.isEnabled())
				{
					report.updateTestLog("Check Element is Disabled.", sReportText	+ " is not Disabled ", Status.FAIL);
					return false;
				}
				else	
				{
					report.updateTestLog("Check Element is Disabled.", sReportText	+ " is Disabled ", Status.PASS);
					return true;
				} }
			else
				return false;
		}
		catch(Exception e)
		{
			report.updateTestLog("Check Element is Disabled.",sReportText + " is not displayed.", Status.FAIL);	
			return false;	
		}
	}


	public boolean smartUM_ReusableComponents_ElementisEmpty(By Locator, String sReportText){
		boolean value = false;
		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(Locator));
			WebElement element = driver.getWebDriver().findElement(Locator);

			// Retrieve innertext from Element:
			// ================================
			if (element.isDisplayed()) 
			{
				if (element.getText().trim().isEmpty())
				{
					report.updateTestLog("Check Element is Empty.", sReportText + " is Empty.",Status.PASS);
					return true;
				} else 
				{
					if (element.getText().trim().contains("Select "))
					{
						report.updateTestLog("Check Element is Empty.", sReportText + " is Empty.",Status.PASS);
						return true;
					}
					else
					{
						report.updateTestLog("Check Element is Empty." + sReportText+" is Empty.", sReportText + " is not Empty.", Status.FAIL);
						return false;
					}
				}
			}
		} catch (Exception e) 
		{
			report.updateTestLog("Check Element is Empty","Unable to locate the element "+sReportText, Status.FAIL);
			value = false;
		}
		return value;
	}

	public boolean smartUM_ReusableComponents_ElementisNotEmpty(By Locator, String sReportText){
		boolean value = false;
		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(Locator));
			WebElement element = driver.getWebDriver().findElement(Locator);

			// Retrieve innertext from Element:
			// ================================

			if (element.isDisplayed()) 
			{
				smartUM_RC_HighlightElement(Locator);
				if (element.getText().trim().isEmpty())
				{
					report.updateTestLog("Check Element is not Empty.", sReportText + " is Empty.",Status.DONE);
					if(element.getAttribute("value").trim().isEmpty())
					{
						report.updateTestLog("Check Element is not Empty",sReportText+" Element is Empty", Status.FAIL);
						value =  false;
					}
					else
						value =  true;

				} else
				{
					if (element.getText().trim().contains("Select"))
					{
						report.updateTestLog("Check Element is not Empty.", sReportText + " is Empty.",Status.FAIL);
						return false;
					}
					else
					{
						report.updateTestLog("Check Element is not Empty.", sReportText + " is not Empty.", Status.PASS);
						return true;
					}
				}
			}
		} 
		catch (Exception e) 
		{
			report.updateTestLog("Check Element is not Empty","Unable to locate the element "+sReportText, Status.FAIL);
			value =  false;
		}
		return value;
	}

	//Get the boolean status for Text or String is not empty.		
	public boolean smartUM_ReusableComponents_StringorText_isNotEmpty(String Value,String Name) 
	{
		if (Value.isEmpty()||Value==null)
		{
			report.updateTestLog("Check Text is not Empty",Name+" Text is Empty",Status.FAIL);
			return false;
		}
		else 
		{	
			report.updateTestLog("Check Text is not Empty",Name+" Text is not Empty",Status.PASS);			
			return true;
		}
	}

	public boolean smartUM_ReusableComponents_Compare_StringorText(String Actual, String Expected,String Fieldname) 
	{
		if (Actual.trim().contains(Expected.trim()))
		{
			report.updateTestLog("Compare two StringorText.",Fieldname+" Text is Displayed as expected.",Status.DONE);
			return true;
		}
		else 
		{
			report.updateTestLog("Compare two StringorText.",Fieldname+" Text is not Displayed  as expected.",Status.FAIL);
			return false;
		}
	}

	//Verify the Element is available and check the element value
	public void smartUM_ReusableComponents_CheckElementandVerifyValue(By Locator,String ElementName,String SheetName, String DataColumn)
	{
		//Check Element is available
		if(smartUM_RC_isDisplayed(Locator))
		{
			//Check Element is not Empty
			if(smartUM_ReusableComponents_ElementisNotEmpty(Locator,ElementName))
			{

				String Element_SheetValue = dataTable.getData(SheetName,DataColumn).trim();
				String Element_UIValue = driver.findElement(Locator).getText().trim();

				//System.out.println(Element_UIValue);
				//Check the Sheet value for the Element
				if(!Element_SheetValue.isEmpty())
				{
					smartUM_ReusableComponents_CheckWebElementText(Locator,Element_SheetValue,ElementName);
				}
				else
					dataTable.putData(SheetName,DataColumn, Element_UIValue);

			}
			else
			{
				report.updateTestLog("Verify the Member Info",ElementName+" is Empty.",Status.FAIL);	
			}
		}
	}

	public void smartUM_ReusableComponents_CheckElementValue(By Locator,String ElementName,String SheetName, String DataColumn)
	{
		//Check Element is available
		if(smartUM_RC_isDisplayed(Locator))
		{
			String Element_SheetValue = dataTable.getData(SheetName,DataColumn).trim();
			String Element_UIValue = driver.findElement(Locator).getText().trim();

			//System.out.println(Element_UIValue);
			//Check the Sheet value for the Element
			if(!Element_SheetValue.isEmpty())
			{
				smartUM_ReusableComponents_CheckWebElementText(Locator,Element_SheetValue,ElementName);
			}
			else
				dataTable.putData(SheetName,DataColumn, Element_UIValue);
		}
	}
	


	//Select the option from the Drop down list
	public void Weblist_Selection(By Dropdown,String Value) throws InterruptedException
	{
		try{
			By Locator=By.xpath("//li//*[text()='"+Value+"']");

			if(smartUM_RC_isDisplayed(Dropdown))
			{
				driver.findElement(Dropdown).click();
				if(smartUM_RC_isDisplayed(Locator))
				{
					Thread.sleep(2000);
					driver.findElement(Locator).click();
					report.updateTestLog("Select dropdown Value.",Value+" is selected.",Status.PASS);
				}
				else
				{
					Locator=By.xpath("//li//*[contains(text(),'"+Value+"')]");	
					if(smartUM_RC_isDisplayed(Locator))
					{
						Thread.sleep(2000);
						driver.findElement(Locator).click();
						report.updateTestLog("Select dropdown Value.",Value+" is selected.",Status.PASS);
					}
					else
						report.updateTestLog("Select dropdown Value.",Value+" is not selected - Dropdown option not displayed.",Status.FAIL);

				}
			}
			else
				report.updateTestLog("Select dropdown Value.",Value+" is not selected - Dropdown not displayed.",Status.FAIL);

		}
		catch(Exception e)
		{
			report.updateTestLog("Select dropdown Value.",Value+" is not selected."+e.getMessage(),Status.FAIL);
		}
	}

	//Select the option from the Drop down list
	public void Weblist_Selection_IfValueFound(By Dropdown,String Value) throws InterruptedException
	{
		try{
			By Locator=By.xpath("//li//*[text()='"+Value+"']");
			if(smartUM_RC_isDisplayed(Dropdown))
			{
				driver.findElement(Dropdown).click();
				if(smartUM_RC_isDisplayed(Locator))
				{
					Thread.sleep(2000);
					driver.findElement(Locator).click();
					report.updateTestLog("Select dropdown Value.",Value+" is selected.",Status.PASS);
				}
			}
		}
		catch(Exception e)
		{
			report.updateTestLog("Select dropdown Value.",Value+" is not selected."+e.getMessage(),Status.FAIL);	
		}
	} 

	//Seect multiple options from the dropdown list
	public void smartUM_ReusableComponents_MultiWeblistSelect(By Dropdown, By Locator, String Value) throws Exception {
		try {
			String[] list1 = Value.split(";");
			driver.getWebDriver().findElement(Dropdown).click();
			//System.out.println(Value);
			//System.out.println(list1[0]);
			for (String temp : list1) {
				//System.out.println(temp);
				List<WebElement> list = driver.getWebDriver().findElements(Locator);
				//System.out.println(list);

				for (int iloop = 0; iloop < list.size(); iloop++) {
					//System.out.println(list.get(iloop).getText());
					if (list.get(iloop).getText().trim().equalsIgnoreCase(temp)) {
						list.get(iloop).click();
						break;
					}
				}
				smartUM_checkPageLoad();
			}
			report.updateTestLog("Worklist Validation", "MultiWeblistSelect", Status.PASS);
		} catch (Exception e) {
			report.updateTestLog("Worklist Validation", "MultiWeblistSelect", Status.FAIL);
			//System.out.println(e);
		}
	}

	/*
	 * =========================================================================
	 * ============ Function Name:
	 * smartUM_ReusableComponents_MultiWeblistDeselect Description : It will
	 * deselect the selected values fromt th multi dropdown Author : Dileep K
	 * Created Date : 05-08-2019
	 * =========================================================================
	 * ============
	 */

	public void smartUM_ReusableComponents_MultiWeblistDeselect(By Dropdown, By locator) throws InterruptedException {
		try {
			driver.getWebDriver().findElement(Dropdown).click();
			List<WebElement> list = driver.getWebDriver().findElements(locator);
			//System.out.println(list.size());
			for (int iloop = 0; iloop < list.size(); iloop++) {
				// list = driver.findElements(locator);
				String area_Selected = list.get(iloop).getAttribute("aria-checked");
				//System.out.println(area_Selected);
				if (area_Selected.contentEquals("true")) {
					list.get(iloop).click();
					smartUM_checkPageLoad();
				}
			}
			report.updateTestLog("Worklist Validation", "MultiWeblistDeselect", Status.PASS);
		} catch (Exception e) {
			report.updateTestLog("Worklist Validation", "MultiWeblistDeselect", Status.FAIL);
			//System.out.println(e);
		}
	}

	//Validate the Drop down list values	
	void ValidateWebList_Div(String[] aExpectedItems, By xpath,String sReportText) 
	{
		List<String> lExpectedItemsList = new ArrayList<String>(Arrays.asList(aExpectedItems));
		//click on the Dropdown value to expand the drop down list	
		driver.findElement(xpath).click();
		List<WebElement> oDDlist =driver.findElementsBy(By.xpath("//ul/li[contains(@class,'MenuWidgetItem---default_direction')]/div"));
		int iActualList = oDDlist.size();	
		//Select objSelect = new Select(driver.findElement(xpath));
		//List<WebElement> oActualList = objSelect.getOptions();

		//System.out.println("lExpectedItemsList.size():"+lExpectedItemsList.size());
		//System.out.println("iActualList:"+iActualList);

		if (!(lExpectedItemsList.size() == iActualList))
			report.updateTestLog("validate Web List " + sReportText,
					"Count mismatch between the actual and the expected  for the Drop Down : "
							+ sReportText + "Expected Count :"
							+ lExpectedItemsList.size() + "Actual Count :"
							+ iActualList, Status.FAIL);
		else
		{

		}


		for (int i = 0; i < iActualList; i++)
		{
			String sDDValue=oDDlist.get(i).getText().trim();

			//System.out.println("lExpectedItemsList:"+lExpectedItemsList);
			//System.out.println("sDDValue:"+sDDValue);
			//System.out.println("lExpectedItemsList.contains(sDDValue):"+lExpectedItemsList.contains(sDDValue));

			if (lExpectedItemsList.contains(sDDValue))
				lExpectedItemsList.remove(sDDValue);	
			else
				report.updateTestLog("validate Web List " + sReportText,"'" + sDDValue + "' is not available in the '" + sReportText + "'", Status.FAIL);
		}

		if (!(lExpectedItemsList.isEmpty()))
			report.updateTestLog("validatie Web List " + sReportText,
					Arrays.asList(lExpectedItemsList)
					+ " are not available in the " + sReportText,
					Status.FAIL);
		else
			report.updateTestLog("validate Web List " + sReportText,
					Arrays.asList(aExpectedItems) + " are  available in the "
							+ sReportText, Status.PASS);
		//click on the Dropdown value to close the drop down list	
		driver.findElement(xpath).click();

	}

	//Verify that listed values are present in drop down values.	 
	public void Verify_Dropdown_Values(String CheckValues, By xpath,String sReportText) 
	{
		String aExpectedItems[] = CheckValues.split(",");
		List<String> lExpectedItemsList = new ArrayList<String>(Arrays.asList(aExpectedItems));
		//System.out.println("Expected List:"+lExpectedItemsList);
		//click on the Dropdown value to expand the drop down list	
		driver.findElement(xpath).click();
		List<WebElement> oDDlist =driver.findElementsBy(By.xpath("//ul/li[contains(@class,'MenuWidgetItem---default_direction')]/div"));
		int iActualList = oDDlist.size();	

		if (lExpectedItemsList.size()==0)
			report.updateTestLog("validate Web List " + sReportText,"Count of the Dropdown is Null", Status.FAIL);

		for (int i = 0; i < iActualList; i++)
		{
			String sDDValue=oDDlist.get(i).getText().trim();
			//System.out.println("Actual:"+sDDValue);
			//System.out.println();
			if (lExpectedItemsList.contains(sDDValue))
			{
				lExpectedItemsList.remove(sDDValue);
				//System.out.println("Expected List:"+lExpectedItemsList);
			}
			if (lExpectedItemsList.isEmpty())
				break;
		}

		if (!(lExpectedItemsList.isEmpty()))
			report.updateTestLog("validate Web List " + sReportText,
					Arrays.asList(lExpectedItemsList)
					+ " are not available in the " + sReportText,
					Status.FAIL);
		else
			report.updateTestLog("validate Web List " + sReportText,
					Arrays.asList(aExpectedItems) + " are  available in the "
							+ sReportText, Status.PASS);

	}


	public void weblist(By Dropdown, By Locator, String Value) throws InterruptedException 
	{
		// ul[@id='']lidiv

		driver.findElement(Dropdown).click();
		Thread.sleep(2000);
		List<WebElement> list = driver.getWebDriver().findElements(Locator);
		for (int iloop = 0; iloop < list.size(); iloop++) {

			if (list.get(iloop).getText().trim().equalsIgnoreCase(Value)) {
				list.get(iloop).click();
				break;
			}
		}
	}

	//Copy the string value from the specified location
	public void smartUM_ReusableComponents_SetClipboardData(String string) 
	{
		StringSelection stringSelection = new StringSelection(string);
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection, null);
	}

	//Paste the Text in specified Element location
	public boolean smartUM_ReusableComponents_PasteClipBoardData(By Locator) throws InterruptedException
	{
		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(Locator));
			wait.until(ExpectedConditions.elementToBeClickable(Locator));
			//driver.findElement(Locator).click();
			driver.findElement(Locator).sendKeys(Keys.chord(Keys.CONTROL, "v"));
			return true;
		} catch (Exception exp) {
			report.updateTestLog("pasteClipBoardData ",
					"Error while pasting data - Error Message - "+ exp.getMessage(), Status.FAIL);
			return false;
		}
	}

	//Click and Paste the Text
	public boolean smartUM_ReusableComponents_CopyandPaste(By Locator,String Value,String FieldName) throws InterruptedException
	{
		try {
			Thread.sleep(2000);
			driver.findElement(Locator).clear();
			smartUM_ReusableComponents_SetClipboardData(Value);
			driver.findElement(Locator).sendKeys(Keys.chord(Keys.CONTROL, "v"));
			report.updateTestLog("Copy and Pasted",Value+" is pasted in the Field "+FieldName, Status.DONE);
			return true;
		} catch (Exception exp)
		{
			report.updateTestLog("Copy and Pasted",
					"Error while pasting data - Error Message - "+ exp.getMessage(), Status.FAIL);
			return false;
		}
	}



	//Click and Enter the Text
	public boolean smartUM_ReusableComponents_ClickandEnter(String Value,By Locator) throws InterruptedException
	{
		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(Locator));
			wait.until(ExpectedConditions.elementToBeClickable(Locator));
			driver.findElement(Locator).click();
			driver.findElement(Locator).sendKeys(Value);
			report.updateTestLog("Click and Enter Data","Entered - "+ Value, Status.DONE);

			return true;
		} catch (Exception exp)
		{
			report.updateTestLog("Click and Enter Data","Error while pasting data - Error Message - "+ exp.getMessage(), Status.FAIL);
			return false;
		}
	}


	// Click on Specified Date Element
	public void clickOnDate(By element) {
		try {
			Actions action = new Actions(driver.getWebDriver());
			WebElement Date = driver.findElement(element);
			action.moveToElement(Date).click().build().perform();
			Date.click();
			Date.sendKeys(Keys.chord(Keys.ARROW_LEFT));
			Date.sendKeys(Keys.chord(Keys.ARROW_LEFT));
			Date.sendKeys(Keys.chord(Keys.ARROW_LEFT));
			report.updateTestLog("Verify Date is clicked","Date is Clicked.", Status.DONE);
		} catch (Exception e) {
			report.updateTestLog("Verify Date is clicked","Unable to locate the element",Status.FAIL);
		}
	}
	// Clear the Element Field
	public void clear_DateField(By DateField,String FieldName)
	{
		try{
			Actions action = new Actions(driver.getWebDriver());
			WebElement Date = driver.findElement(DateField);
			action.moveToElement(Date).click().build().perform();
			Date.click();
			driver.switchTo().activeElement().clear();
			driver.switchTo().activeElement().sendKeys(Keys.BACK_SPACE);
			Date.sendKeys(Keys.chord(Keys.ARROW_LEFT));
			Thread.sleep(1000);
			report.updateTestLog("Clear the "+FieldName+" Field.", FieldName + "field is Cleared", Status.DONE);
		}
		catch(Exception e)
		{
			report.updateTestLog("Clear the "+FieldName+" Field.","Unable to locate the element "+FieldName, Status.DONE);
		}
	}

	// Clear the Element Field
		public void clear_DateField_ByKeys(By DateField,String FieldName)
		{
			try{
				Actions action = new Actions(driver.getWebDriver());
				WebElement Date = driver.findElement(DateField);
				action.moveToElement(Date).click().build().perform();
				Date.click();
				driver.switchTo().activeElement().clear();
				driver.findElement(DateField).sendKeys(Keys.ARROW_RIGHT);
				driver.findElement(DateField).sendKeys(Keys.BACK_SPACE);
				driver.findElement(DateField).sendKeys(Keys.BACK_SPACE);
				driver.findElement(DateField).sendKeys(Keys.BACK_SPACE);
				driver.findElement(DateField).sendKeys(Keys.BACK_SPACE);
				driver.findElement(DateField).sendKeys(Keys.BACK_SPACE);
				driver.findElement(DateField).sendKeys(Keys.BACK_SPACE);
				driver.findElement(DateField).sendKeys(Keys.BACK_SPACE);
				driver.findElement(DateField).sendKeys(Keys.BACK_SPACE);
				driver.findElement(DateField).sendKeys(Keys.BACK_SPACE);
				driver.findElement(DateField).sendKeys(Keys.BACK_SPACE);
				driver.findElement(DateField).sendKeys(Keys.BACK_SPACE);
				driver.findElement(DateField).sendKeys(Keys.TAB);
				driver.switchTo().activeElement().sendKeys(Keys.BACK_SPACE);
				Date.sendKeys(Keys.chord(Keys.ARROW_LEFT));
				Thread.sleep(1000);
				report.updateTestLog("Clear the "+FieldName+" Field.", FieldName + "field is Cleared", Status.DONE);
			}
			catch(Exception e)
			{
				report.updateTestLog("Clear the "+FieldName+" Field.","Unable to locate the element "+FieldName, Status.DONE);
			}
		}


	//validate the specified Element text by "getAttribute - textContent" method
	public boolean smartUM_ReusableComponents_CheckWebElementText_Value(By Locator, String ExpectedText,String FieldName)
	{
		String ActualText = driver.findElement(Locator).getAttribute("value").trim();
		System.out.println(ExpectedText+" "+ActualText);

		if (ExpectedText.trim().equalsIgnoreCase(ActualText.trim())) {
			report.updateTestLog("Check Element Text.",FieldName+" Field value is displayed as "+ActualText+" as expected Value "+ExpectedText, Status.DONE);
			return true;
		} else
		{
			report.updateTestLog("Check Element Text.", FieldName+" Field value is displayed as " + ActualText + " instead of "+ ExpectedText, Status.FAIL);
			return false;
		}
	}
	public String smartUM_ReusableComponents_GetText(By Locator, long timeout) {			
		WebElement element = null;
		WebDriverWait wait = new WebDriverWait(driver.getWebDriver(), timeout);
		element = wait.until(ExpectedConditions.visibilityOfElementLocated(Locator));
		String Text_Msg=element.getText().trim();
		//report.updateTestLog("Validating the presence of " + sReportText,	sReportText + " is displayed in the screen", Status.PASS);

		return Text_Msg;
	}

	public void smartUM_ReusableComponents_Probing_Questions(By Locator,By Locator1,By Locator2,String Question,String Answer,String sReportText) throws Exception {				
		try{
			if(!Question.isEmpty()){	

				String probingQues= smartUM_ReusableComponents_GetText(Locator,4);    		    	 			    	
				if(probingQues.trim().contains(Question.trim()))
				{
					report.updateTestLog("Validating the " + sReportText,"<FONT COLOR=BLUE>"+probingQues+"</FONT>"+ " is displayed as the " + sReportText, Status.DONE);	    		  		 	
					if(Answer.equalsIgnoreCase("Yes"))
						driver.findElement(Locator1).click();							    	
					else
						driver.findElement(Locator2).click();
					report.updateTestLog("Probing Question", "Probing Question Verification", Status.SCREENSHOT);
				}
				else{
					report.updateTestLog("Validating the " + sReportText,"<FONT COLOR=BLUE>"+probingQues+"</FONT>"+ " is not displayed as the " + sReportText, Status.FAIL);
					report.updateTestLog("Probing Question", "Probing Question Verification", Status.SCREENSHOT);	}

			}   
		}catch (Exception e) 
		{	}
	}


	public void smartUM_ReusableComponents_Click_Link_WebTableResults(By xpath,By xpath1,String sExpected, String sExpected1)
	{
		//boolean bPassFalg = false;
		//List<WebElement> link=driver.findElementsBy(By.cssSelector("tbody>tr>td>a"));
		List<WebElement> link=driver.findElementsBy(xpath1);
		WebElement table = driver.findElement(xpath);
		List<WebElement> rows = table.findElements(By.tagName("tr"));
		for (int i = 0; i < rows.size(); i++) {
			List<WebElement> columns = rows.get(i).findElements(By.tagName("td"));
			String sColValue = columns.get(0).getText().trim();
			String sColValue1 = columns.get(1).getText().trim();

			if (sColValue.equalsIgnoreCase(sExpected) & sColValue1.equalsIgnoreCase(sExpected1)) {
				link.get(i).click();

				//bPassFalg = true;
				//return;
			}

		}

	}


	//Switch to Main Window
	public  void switchTo_MainWindow()
	{
		String WindowId = null;		
		Set<String> WindowIds = driver.getWindowHandles();
		Iterator<String> iter = WindowIds.iterator();
		WindowId = iter.next();
		driver.switchTo().window(WindowId);
		//System.out.println(driver.getTitle());
		report.updateTestLog("Switched To MainWindow","Switched to " + WindowId, Status.PASS);
	}

	//Switch to Main Window
	public  void switchTo_ChildWindow(int ChildCount)
	{
		String WindowId = null;		
		Set<String> WindowIds = driver.getWindowHandles();
		Iterator<String> iter = WindowIds.iterator();
		WindowId = iter.next();
		for(int i=1;i<=ChildCount;i++)
		{
			WindowId = iter.next();	
		}

		driver.switchTo().window(WindowId);
		//System.out.println(driver.getTitle());
		report.updateTestLog("Switched To Child Window","Switched to " + WindowId, Status.PASS);
	}



	//Click on specified element
	public boolean smartUM_ReusableComponents_ClickElement(By Locator, String sReportText) throws InterruptedException
	{
		try{
			wait.until(ExpectedConditions.visibilityOfElementLocated(Locator));
			smartUM_ReusableComponents_ScrolltoElement(Locator);
			smartUM_RC_HighlightElement(Locator);
			driver.findElement(Locator).click();
			report.updateTestLog("Click the Element.", sReportText+ " is clicked ", Status.DONE);
			Thread.sleep(1000);
			return true;
		}
		catch(Exception e)
		{
			report.updateTestLog("Click the Element.", sReportText+ " is unavailable.", Status.FAIL);
			return false;
		}
	}


	//click on Element when its Ready - 2 arguments
	public void smartUM_ReusableComponents_ClickElementWhenReady(By Locator, int timeout) throws InterruptedException
	{
		try{
			WebDriverWait wait1 = new WebDriverWait(driver.getWebDriver(),timeout);
			smartUM_ReusableComponents_ScrolltoElement(Locator);
			wait1.until(ExpectedConditions.visibilityOfElementLocated(Locator));
			wait1.until(ExpectedConditions.elementToBeClickable(Locator));		
			smartUM_RC_HighlightElement(Locator);
			driver.findElement(Locator).click();
			Thread.sleep(1000);
		}
		catch(Exception e)
		{	
			report.updateTestLog("Click the Element.","Unable to locate the element.", Status.FAIL);
		}
	}


	public boolean smartUM_ReusableComponents_ClickElementWhenReady(By Locator, int timeout,String sReportText)
	{
		try{
			WebDriverWait wait1 = new WebDriverWait(driver.getWebDriver(),timeout);
			wait1.until(ExpectedConditions.visibilityOfElementLocated(Locator));
			wait1.until(ExpectedConditions.elementToBeClickable(Locator));
			smartUM_ReusableComponents_ScrolltoElement(Locator);
			smartUM_RC_HighlightElement(Locator);
			driver.findElement(Locator).click();
			report.updateTestLog("Click the Element.", sReportText+ " is clicked ", Status.DONE);
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			return true;

		}
		catch(Exception e)
		{
			report.updateTestLog("Click the Element.",sReportText+" is unavailable.",Status.FAIL);
			return false;
		}
	}

	public boolean smartUM_ReusableComponents_SendText(By Locator, String sText, String sReportText)
	{
		driver.findElement(Locator).sendKeys(sText + Keys.TAB);
		if (sReportText.contains("Password")
				|| sReportText.equalsIgnoreCase("Password"))
			report.updateTestLog("Enter the text.",
					"The Password ******** is entered in " + sReportText,
					Status.DONE);
		else
			report.updateTestLog("Enter the text.",
					"The Text " + sText + " is entered in " + sReportText,
					Status.DONE);
		return true;
	}

	//validate the specified Element text by getText method
	public boolean smartUM_ReusableComponents_CheckWebElementText(By Locator, String sText,String sReportText) 
	{
		boolean value = true;
		try
		{
			String sActualText = driver.findElement(Locator).getText().trim();
			System.out.println(sText+":"+sActualText+":"+sText.trim().contains(sActualText.trim()));
			
			if(!sText.isEmpty()||sText!=null)
			{
				if (sText.trim().contains(sActualText.trim()) || sActualText.trim().equalsIgnoreCase(sText.trim()))
				{				
					report.updateTestLog("Check Element Text.",sReportText+" Element Text is displayed as <FONT COLOR=GREEN>"+sActualText+"</FONT>"+ " expected as <FONT COLOR=BLUE>" + sText+"</FONT>", Status.DONE);
					value = true;
				}
				else 
				{
					report.updateTestLog("Check Element Text.", sReportText+" Element Text is displayed as <FONT COLOR=RED>"+sActualText+"</FONT>"+ " instead of <FONT COLOR=BLUE>"+ sText+"</FONT>", Status.FAIL);
					value = false;
				}
			}
			return value;
		}
		catch(Exception e)
		{
			report.updateTestLog("Check Element Text.", "<FONT COLOR=RED>"+sReportText+" is not Displayed</FONT>", Status.FAIL);
			value = false;
		}
		return value;
	}


	public boolean smartUM_ReusableComponents_CheckWebElementText_getAttribute(By Locator, String sText,
			String sReportText) {
		String sActualText = driver.findElement(Locator).getAttribute("value");

		//System.out.println(sReportText+" :::  "+sActualText );
		if (sText.trim().equalsIgnoreCase(sActualText.trim())) {
			report.updateTestLog("Check Element Text.", sActualText
					+ " is displayed as the " + sReportText, Status.DONE);
			return true;
		} else {
			report.updateTestLog("Check Element Text.", sActualText
					+ " is displayed as the " + sReportText + " instead of "
					+ sText, Status.FAIL);
			return false;
		}
	}


	//validate the specified Element text by "getAttribute - textContent" method
	public boolean smartUM_ReusableComponents_CheckWebElementText_TextContent(By Locator, String sText,String sReportText)
	{
		try
		{
			String sActualText = driver.findElement(Locator).getAttribute("textContent").trim();
			//System.out.println(sText+" "+sActualText);

			if (sText.trim().equalsIgnoreCase(sActualText.trim())) {
				report.updateTestLog("Check Element Text.",sReportText+" Element Text is displayed as <FONT COLOR=GREEN>"+sActualText+"</FONT>"+ " expected as <FONT COLOR=BLUE>" + sText+"</FONT>", Status.PASS);				
				return true;
			} else
			{
				report.updateTestLog("Check Element Text.",sReportText+" Element Text is displayed as <FONT COLOR=RED>"+sActualText+"</FONT>"+ " expected as <FONT COLOR=BLUE>" + sText+"</FONT>", Status.FAIL);				
				
				return false;
			}
		}
		catch(Exception e)
		{
			report.updateTestLog("Check Element Text.", "<FONT COLOR=RED>"+sReportText+" is not Displayed</FONT>", Status.FAIL);
			return false;	
		}
	}


	public void smartUM_ReusableComponents_Select_DropDwn(By Locator, String SheetName, String ColumnName) throws Exception 

	{
		String SheetValue  = dataTable.getData(SheetName, ColumnName);

		if(!SheetValue.isEmpty())
			Weblist_Selection(Locator, SheetValue);			  	                      

	}

	//Get the Current Date
	public String smartUM_ReusableComponents_GetCurrentDate()
	{
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		Calendar calendar = new GregorianCalendar();//Calendar.getInstance();
		calendar.add(Calendar.DATE, 1);
		String date = sdf.format(calendar.getTime());
		return date;
	}

	//Date, Day and Time calculator
	public String smartUM_ReusableComponents_GetTimestamp_Days_Set(String days)
	{
		// TODO Auto-generated method stub
		Date objDate = new Date(); // Current System Date and time is assigned to objDate
		//System.out.println(objDate);
		String strDateFormat = "MM/dd/yyyy";// Date format is Specified
		SimpleDateFormat objSDF = new SimpleDateFormat(strDateFormat);
		// Date format string is passed as an argument to the Date format object
		//System.out.println(objSDF.format(objDate));
		// Date formatting is applied to the current date
		Calendar c = Calendar.getInstance();
		String dateValue = days;

		if (dateValue.substring(0, 8).equals("WorkDate"))
		{
			Date date = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat(strDateFormat);
			String TimeStamp = sdf.format(date);
			return TimeStamp;

		} 

		else if (dateValue.substring(0, 12).equals("WrksDatePlus"))
		{
			String finalDateValue = dateValue.substring(12);

			switch (finalDateValue) 
			{

			case "One":
				c.add(Calendar.DATE, 1);
				String days_plusOne = objSDF.format(c.getTime());
				return days_plusOne;
			case "Two":
				c.add(Calendar.DATE, 2);
				String days_plusTwo = objSDF.format(c.getTime());
				return days_plusTwo;

			case "Three":
				c.add(Calendar.DATE, 3);
				String days_plusThree = objSDF.format(c.getTime());
				return days_plusThree;

			case "Four":
				c.add(Calendar.DATE, 4);
				String days_plusFour = objSDF.format(c.getTime());
				return days_plusFour;

			case "Five":
				c.add(Calendar.DATE, 5);
				String days_plusFive = objSDF.format(c.getTime());
				return days_plusFive;

			case "Six":
				c.add(Calendar.DATE, 6);
				String days_plussix = objSDF.format(c.getTime());
				return days_plussix;

			case "Ten":
				c.add(Calendar.DATE, 10);
				String days_plusTen = objSDF.format(c.getTime());
				return days_plusTen;	


			case "Thirty":
				c.add(Calendar.DATE, 30);
				String days_plusThirty = objSDF.format(c.getTime());
				return days_plusThirty;

			case "Sixty":
				c.add(Calendar.DATE, 60);
				String days_plusSixty = objSDF.format(c.getTime());
				return days_plusSixty;

			default:

				// String strDateFormat = "MM/dd/yyyy";
				//Date format is Specified
				Date date = new Date();
				SimpleDateFormat sdf = new SimpleDateFormat(strDateFormat);
				String TimeStamp = sdf.format(date);
				return TimeStamp;

			} // end of switch

		} // end of if

		else if (dateValue.substring(0, 13).equals("WrksDateMinus")) 
		{

			String finalDateValue = dateValue.substring(13);
			String days_Minus = null;

			switch (finalDateValue)
			{

			case "One":
				c.add(Calendar.DATE, -1);
				String days_MinusOne = objSDF.format(c.getTime());
				return days_MinusOne;
			case "Two":
				c.add(Calendar.DATE, -2);
				String days_MinusTwo = objSDF.format(c.getTime());
				return days_MinusTwo;

			case "Three":
				c.add(Calendar.DATE, -3);
				String days_MinusThree = objSDF.format(c.getTime());
				return days_MinusThree;

			case "Four":
				c.add(Calendar.DATE, -4);
				String days_MinusFour = objSDF.format(c.getTime());
				return days_MinusFour;

			case "Five":
				c.add(Calendar.DATE, -5);
				String days_MinusFive = objSDF.format(c.getTime());
				return days_MinusFive;

			case "Six":
				c.add(Calendar.DATE, -6);
				String days_MinusSix = objSDF.format(c.getTime());
				return days_MinusSix;

			case "Ten":
				c.add(Calendar.DATE, -10);
				String days_MinusTen = objSDF.format(c.getTime());
				return days_MinusTen;

			case "Thirty":
				c.add(Calendar.DATE, -30);
				String days_MinusThirty = objSDF.format(c.getTime());
				return days_MinusThirty;

			}// end of switch

		} // end of else

		else
		{

		}

		return null;

	}

	public String DateFormat_Conversion(String DateValueinText, String DateFormat_From,String DateFormat_To) throws Exception
	{
		DateValueinText = DateValueinText.trim();
		String date = "";
		Calendar c = Calendar.getInstance();
		SimpleDateFormat sdf1 = new SimpleDateFormat(DateFormat_From);
		SimpleDateFormat sdf2 = new SimpleDateFormat(DateFormat_To);
		SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy hh:mm a");

		try {
			if(!DateFormat_From.isEmpty() && !DateFormat_To.isEmpty())
			{

				//System.out.println(sdf1.parse(DateValueinText));
				c.setTime(sdf1.parse(DateValueinText));
				//System.out.println(sdf2.format(c.getTime()));
				date  = sdf2.format(c.getTime());
			}

		}
		catch(Exception e)
		{
			report.updateTestLog("Verify String in Date Format.", "String is not in Correct Date Format to convert to Date.",Status.FAIL);

		}
		try {
			if(DateFormat_From.isEmpty() && !DateFormat_To.isEmpty())
			{
				//System.out.println(sdf2.parse(DateValueinText));
				c.setTime(sdf2.parse(DateValueinText));
				//System.out.println(sdf2.format(c.getTime()));
				date  = sdf2.format(c.getTime());
			}
		}
		catch(Exception e)
		{
			report.updateTestLog("Verify String in Date Format.", "Conversion Date format is not Valid.",Status.FAIL);

		}

		try {
			if(DateFormat_From.isEmpty() && DateFormat_To.isEmpty())
			{

				//System.out.println(sdf.parse(DateValueinText));
				c.setTime(sdf.parse(DateValueinText));
				//System.out.println(sdf.format(c.getTime()));
				date  = sdf.format(c.getTime());
			}
		}
		catch(Exception e)
		{
			report.updateTestLog("Verify String in Date Format.", "Date format is not Valid.",Status.FAIL);		
		}
		//System.out.println(date);
		return date.trim();
	}

	public String[] GetCountofDays(String Value)
	{
		Value = Value.trim().toLowerCase();
		String arr[] = new String[2];
		int Days = 0;	

		//System.out.println("Integer Value:"+Long.parseLong(Value.replaceAll("[^0-9]", "").trim()));
		//System.out.println("Integer Value:"+Value.replaceAll("[^0-9]", "").trim());
		arr[0] = Value.replaceAll("[^0-9]", "").trim();

		if(Value.contains("hour")||Value.contains("hours")||Value.contains("hrs"))
		{
			long intValue = Long.parseLong(Value.replaceAll("[^0-9]", "").trim());
			//System.out.println("Hours:"+intValue);
			if(intValue>=24)
				Days = (int)TimeUnit.DAYS.convert(intValue, TimeUnit.HOURS);
			arr[1] = "Hrs";
		}
		else if (Value.contains("day")||Value.contains("days"))
		{
			long intValue = Long.parseLong(Value.replaceAll("[^0-9]", "").trim());
			//System.out.println("Days:"+intValue);
			Days = (int)intValue;
			arr[1] = "Days";
		}
		else
		{

		}

		return arr;
	}

	public String getDate_AddorMinus(String DateValueinText, String AddorMinus, int Value, String Units) throws ParseException
	{
		DateValueinText = DateValueinText.trim();
		String newDate = "";
		SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy hh:mm a");
		Calendar c = Calendar.getInstance();

		try
		{
			//System.out.println(sdf.parse(DateValueinText));
			c.setTime(sdf.parse(DateValueinText));
		}
		catch(Exception e){}

		if(Units.contains("Hrs")||Units.contains("hrs"))
		{
			if(AddorMinus.contains("Add"))
				c.add(Calendar.HOUR_OF_DAY, Value);
			else
				c.add(Calendar.HOUR_OF_DAY, -Value);
		}
		else
		{
			if(AddorMinus.contains("Add"))
				c.add(Calendar.DAY_OF_MONTH, Value);
			else
				c.add(Calendar.DAY_OF_MONTH, -Value);
		}

		newDate = sdf.format(c.getTime());

		System.out.println(newDate);
		return newDate.trim();
	}


	public List<LocalDate> holidays()
	{
		List<LocalDate> holidays = new ArrayList<>();
		holidays.add(LocalDate.of(2021,1,1));
		holidays.add(LocalDate.of(2021,1,18));
		holidays.add(LocalDate.of(2021,5,31));
		holidays.add(LocalDate.of(2021,7,5));
		holidays.add(LocalDate.of(2021,9,6));
		holidays.add(LocalDate.of(2021,11,11));
		holidays.add(LocalDate.of(2021,11,25));
		holidays.add(LocalDate.of(2021,12,24));
		holidays.add(LocalDate.of(2021,12,31));

		return holidays;
	}


	public static LocalDate calculate_BusinessDays(LocalDate localdate, String AddorMinus, int days, Optional<List<LocalDate>> holidays)
	{
		if(localdate == null || days<0 || holidays == null)
		{
			throw new IllegalArgumentException("Invalid method arguments(s)");
		}

		Predicate<LocalDate> isHoliday = date -> holidays.isPresent() ? holidays.get().contains(date):false;

		Predicate<LocalDate> isWeekend = date -> date.getDayOfWeek() == DayOfWeek.SATURDAY 
				||date.getDayOfWeek() == DayOfWeek.SUNDAY;

		LocalDate result = localdate;

		if(AddorMinus.equalsIgnoreCase("Add"))
		{
			while(days>0)
			{
				result = result.plusDays(1);
				if(isHoliday.or(isWeekend).negate().test(result))
				{
					System.out.println(days+":"+result);
					days--;
				}
			}
		}
		else
		{
			while(days>0)
			{
				result = result.minusDays(1);
				if(isHoliday.or(isWeekend).negate().test(result))
				{
					System.out.println(days+":"+result);
					days--;
				}
			}
		}

		return result;
	}


	public String getDate_AddorMinus_BusinessDays(String DateValueinText, String AddorMinus, int Value, String Units) throws Exception
	{
		//System.out.println(DateValueinText);
		//System.out.println(AddorMinus);
		//System.out.println(Value);
		//System.out.println(Units);

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		DateValueinText = DateValueinText.trim();
		String date = null;
		String ExpectedDate = null;
		try
		{
			c.setTime(sdf.parse(DateValueinText));
		}
		catch(Exception e)
		{
			date  = DateFormat_Conversion(DateValueinText,"MMM dd, yyyy hh:mm a","yyyy-MM-dd").trim();	
		}

		//System.out.println(date);

		LocalDate Calculated = LocalDate.parse(date);
		//LocalDate StartDate = LocalDate.parse("2021-05-04");

		//System.out.println(Calculated);
		/*
		if(AddorMinus.equalsIgnoreCase("Add"))
		{
			int addedDays=0;
			while (addedDays< Value)
			{
				Calculated = Calculated.plusDays(1);
				if(!( Calculated.getDayOfWeek()== DayOfWeek.SATURDAY ||Calculated.getDayOfWeek()== DayOfWeek.SUNDAY ) )
					++addedDays;
			}
		}
		else
		{
			int MinusedDays =0;
			while (MinusedDays< Value)
			{
				Calculated = Calculated.minusDays(1);
				if(!( Calculated.getDayOfWeek()== DayOfWeek.SATURDAY ||Calculated.getDayOfWeek()== DayOfWeek.SUNDAY ) )
					++MinusedDays;
			}	
		}
		 */
		if(AddorMinus.equalsIgnoreCase("Add"))
			Calculated = calculate_BusinessDays(Calculated,"Add",Value,Optional.of( holidays() ) );
		else
			Calculated = calculate_BusinessDays(Calculated,"Minus",Value,Optional.of( holidays() ) );


		//System.out.println(Calculated.toString());
		ExpectedDate = Calculated.toString();
		ExpectedDate  = DateFormat_Conversion(ExpectedDate,"yyyy-MM-dd","MMM dd, yyyy hh:mm a").trim();
		System.out.println(ExpectedDate);
		return ExpectedDate.trim();
	}

	public String get_ClientCode(String get_Full_ClientName)
	{
		String ClientCode = "";
		get_Full_ClientName = get_Full_ClientName.toUpperCase();
		switch(get_Full_ClientName)
		{

		case "ILLINOIS":			
			ClientCode =  "IL";
			break;

		case "MONTANA":					
			ClientCode =  "MT";
			break;		
		case "NEW MEXICO":			
			ClientCode =  "NM";
			break;
		case "OKLAHOMA":			
			ClientCode =  "OK";
			break;
		case "TEXAS":			
			ClientCode =  "TX";
			break;
		}
		return ClientCode;
	}
	public String get_Full_ClientName(String ClientCode)
	{
		String Full_ClientName = "";
		ClientCode = ClientCode.toUpperCase();

		switch(ClientCode){

		case "IL":			
			Full_ClientName =  "Illinois";
			break;

		case "MT":					
			Full_ClientName =  "Montana";
			break;		
		case "NM":			
			Full_ClientName =  "New Mexico";
			break;
		case "OK":			
			Full_ClientName =  "Oklahoma";
			break;
		case "TX":			
			Full_ClientName =  "Texas";
			break;
		}
		return Full_ClientName;
	}



	/**
	 * This method helps to add days/months/years to the calander <br>
	 * <br>
	 * 
	 * @author Cognizant
	 * 
	 * @param date
	 *            - date value
	 * @throws ParseException 
	 *
	 * @exception Exception
	 * @see Selenium#skipExecution()
	 * 
	 */
	public String smartUM_ThroughDate(String duration,String Durationtype) throws ParseException{

		int val=Integer.parseInt(duration);
		//System.out.println(val);

		SimpleDateFormat dateformat=new SimpleDateFormat("MM/dd/yyyy");
		Calendar cal= Calendar.getInstance();

		switch(Durationtype)
		{
		case "Days":
			cal.add(Calendar.DATE, val);
			break;
		case "Months":
			cal.add(Calendar.MONTH, val);
			break;

		case "Years":
			cal.add(Calendar.YEAR, val);
			break; 

		}


		String Todate=dateformat.format(cal.getTime());
		return Todate;

	}

	public void SmartUM_ClearDate(By Locator)
	{

		driver.findElement(Locator).sendKeys(Keys.ARROW_RIGHT);
		driver.findElement(Locator).sendKeys(Keys.BACK_SPACE);
		driver.findElement(Locator).sendKeys(Keys.BACK_SPACE);
		driver.findElement(Locator).sendKeys(Keys.BACK_SPACE);
		driver.findElement(Locator).sendKeys(Keys.BACK_SPACE);
		driver.findElement(Locator).sendKeys(Keys.BACK_SPACE);
		driver.findElement(Locator).sendKeys(Keys.BACK_SPACE);
		driver.findElement(Locator).sendKeys(Keys.BACK_SPACE);
		driver.findElement(Locator).sendKeys(Keys.BACK_SPACE);
		driver.findElement(Locator).sendKeys(Keys.BACK_SPACE);
		driver.findElement(Locator).sendKeys(Keys.BACK_SPACE);
		driver.findElement(Locator).sendKeys(Keys.BACK_SPACE);
		driver.findElement(Locator).sendKeys(Keys.TAB);


	}



	public void  smartUM_ReusableComponents_Wtbl_Validation(String val,String val1,String val2,String val3,String val4,String val5,String val6,String val7) {

		WebElement addButton = driver.findElement(By.xpath("//*[contains(text(), '"+val+"')]/../..//*[contains(text(), '"+val1+"')]/../..//*[contains(text(), '"+val2+"')]/../..//*[contains(text(), '"+val3+"')]/../..//*[contains(text(), '"+val4+"')]/../..//*[contains(text(), '"+val5+"')]/../..//*[contains(text(), '"+val6+"')]/../..//*[contains(text(), '"+val7+"')]"));
		if (addButton.isDisplayed())                   

			report.updateTestLog("Verify data", "Required data :" + val1 + ":" +  val2 + ":" + val3 + ":" + val4 + ":"+val5+":" +val6+":" +val7+":"+" is available in results", Status.SCREENSHOT);

		else
			report.updateTestLog("Verify data", "Required data :" + val + ":" +  val1 + ":" + val2 + ":" + val3 + ":"  +" is not available in results", Status.FAIL);

	}


	public int getColumnIndex(String TableWebEle,String ColumnName)
	{
		//System.out.println(TableWebEle+"//thead//tr/th//*[contains(@id,'header')]");

		List<WebElement> cols = driver.findElementsBy(By.xpath(TableWebEle+"//thead//tr/th//*[contains(@id,'header')]"));	
		int subcount = cols.size();

		int ColInd = 0;

		for (int i = 0; i <= subcount; i++) 
		{
			String act = cols.get(i).getText();

			if(act.trim().equalsIgnoreCase(ColumnName.trim()))
			{			
				ColInd = i+1;
				//System.out.println(act.trim()+":"+ColumnName.trim()+":"+ColInd);
				break;
			}
		}
		//System.out.println(ColInd);
		return ColInd;		
	}


	public String getColumnValue(String TableWebEle,String ColumnName,int RowNum) throws Exception
	{

		String ColVal = null;
		int ColInd = getColumnIndex(TableWebEle,ColumnName);
		//System.out.println(TableWebEle+"//tbody//tr["+RowNum+"]/td["+ColInd+"]//p");

		if(!((ColInd==0)&&(RowNum==0)))
		{	
			WebElement ele = driver.findElement(By.xpath("("+TableWebEle+"//tbody//tr["+RowNum+"]/td["+ColInd+"]//p)[1]"));
			ColVal = driver.findElement(By.xpath("("+TableWebEle+"//tbody//tr["+RowNum+"]/td["+ColInd+"]//p)[1]")).getAttribute("textContent").trim();			
		}
		Thread.sleep(1000);

		//System.out.println(ColVal);
		return ColVal;		
	}

	public String getString_RemovingTimeZoneCode(String DatewithTimeZone)
	{
		String DatewithoutTimeZone = DatewithTimeZone.trim().replaceAll(" CDT","");
		DatewithoutTimeZone = DatewithoutTimeZone.replaceAll(" CST","");
		DatewithoutTimeZone = DatewithoutTimeZone.replaceAll(" IST","");
		return DatewithoutTimeZone;
	}


	/********************************************************************************
	 * Description : Validating web table      
	 * @param  : No            
	 * @throws Exception
	 *********************************************************************************/

	public void  smartUM_ReusableComponents_Webelement_Displayed(String val) {



		WebElement addButton = driver.findElement(By.xpath("//*[contains(text(), '"+val+"')]"));
		if (addButton.isDisplayed())                   

			report.updateTestLog("Verify data", "Required data :" + val + ":" + " is available", Status.SCREENSHOT);

		else
			report.updateTestLog("Verify data", "Required data :" + val + ":" + " is not available", Status.FAIL);

	}

	/********************************************************************************
	 * Description : Validating web table      
	 * @param  : No            
	 * @throws Exception
	 *********************************************************************************/

	public void  smartUM_ReusableComponents_Wtbl_Validation(String val,String val1,String val2) {

		WebElement addButton = driver.findElement(By.xpath("(//*[contains(text(), '"+val+"')])[2]//..//..//..//..//*[contains(text(), '"+val1+"')]/../..//*[contains(text(), '"+val2+"')]"));
		if (addButton.isDisplayed())                   

			report.updateTestLog("Verify data", "Required data :" + val1 + ":" +  val2 +  ":" + " is available in results", Status.SCREENSHOT);

		else
			report.updateTestLog("Verify data", "Required data :" + val1 + ":" +  val2 +  ":" + " is not available in results", Status.FAIL);

	}
	public String smartUM_DateFormats(String date) throws ParseException{
        //System.out.println( "givend ate"+date);

        String date1=date;
        SimpleDateFormat dateformat=new SimpleDateFormat("M/d/yyyy");
        String newdate=dateformat.format(dateformat.parse(date1));
        return newdate;

 }


    

}
