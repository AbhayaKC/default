package common;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.common.base.Function;
import com.hcsc.automation.framework.Status;

import supportlibraries.ReusableLibrary;
import supportlibraries.ScriptHelper;

public class CommonFunctions_Keywords extends ReusableLibrary
{

	public CommonFunctions_Keywords(ScriptHelper scriptHelper) 
	{
		super(scriptHelper);
	}

//############################################################# Start of Selenium Related Functions #############################################################	
	/**
	 * This method is used to simulate typing into an element, which may set its value.
	 * <br><br>
	 * @author 	  Gunasekaran Nallasamy(i365976)
	 * @param     oLocator  By - To Locate the object using which the elements to be found
	 * @param 	  sText  String - input values 
	 * @param 	  sReportText String - The Reporting Text which will be displayed in the HTML Report
	 * @return    boolean(true/false) Value - Whether the value is entered or not
	 * @exception Exception - this exception is marked as fail in the report
	 */   
	
	public boolean sendText(By oLocator ,String sText, String sReportText)
	{	
		if (sText.trim().isEmpty()) return true;
		try
		{
			driver.findElement(oLocator).sendKeys(sText);
			if (sReportText.contains("Password") ||sReportText.equalsIgnoreCase("Password"))
				report.updateTestLog("Entering the text in the " + sReportText, "The Password ******** is entered in " + sReportText , Status.PASS);
			else
				report.updateTestLog("Entering the text in the " + sReportText, "The Text " + sText + " is entered in " + sReportText , Status.PASS);
			return true;
		}
		catch (Exception e) 
		{
			report.updateTestLog("Entering the text in the " + sReportText, "Exception Occcured while Entering the text in " + sReportText + " "+e.getMessage() , Status.FAIL);			
			return false;
		}	
	}
	
	/**
	 * This method is used to clear the value in the text box and simulate typing into an element, which may set its value.
	 * <br><br>
	 * @author 	   Gunasekaran Nallasamy(i365976)
	 * @param 	   oLocator  By - To Locate the object using which the elements to be found
	 * @param 	   sText  String - input values 
	 * @param 	   sReportText String - The Reporting Text which will be displayed in the HTML Report
	 * @return     boolean(true/false) Value - Whether the value is entered or not
	 * @exception  Exception - this exception is marked as fail in the report
	 */   
	
	public boolean clearandSendText(By oLocator ,String sText, String sReportText)
	{		
		try
		{
			driver.findElement(oLocator).clear();
			driver.findElement(oLocator).sendKeys(sText);
			report.updateTestLog("Clearing and Entering the text in the " + sReportText, "The Text " + sText + " is entered in " + sReportText , Status.PASS);
			return true;
		}
		catch (Exception e) 
		{
			report.updateTestLog("Clearing and Entering the text in the " + sReportText, "Exception Occcured while clearing and Entering the text in " + sReportText + " "+e.getMessage(), Status.FAIL);			
			return false;
		}	
	}
	

	/**
	 * This method is used to clear the value in the text box 
	 * <br><br>
	 * @author 	   Gunasekaran Nallasamy(i365976)
	 * @param 	   oLocator  By - To Locate the object using which the elements to be found	 * 
	 * @param 	   sReportText String - The Reporting Text which will be displayed in the HTML Report
	 * @return     boolean(true/false) Value - Whether the value is entered or not
	 * @exception  Exception - this exception is marked as fail in the report
	 */   
	
	public boolean clearText(By oLocator , String sReportText)
	{		
		try
		{
			driver.findElement(oLocator).clear();			
			report.updateTestLog("Clearing the text in the " + sReportText, "The Text  is cleared in " + sReportText , Status.PASS);
			return true;
		}
		catch (Exception e) 
		{
			report.updateTestLog("Clearing the text in the " + sReportText, "Clearing the text in the " + sReportText + " "+e.getMessage(), Status.FAIL);			
			return false;
		}	
	}
	
	/**
	 * This method helps to Select all options that display the text matching with argument
	 * <br><br>
	 * @author Cognizant
	 * 
	 * @param oLocator  By - locator used to find the element
	 * @param sText  String - text to select
	 * @param sReportText String - Text which is used for the Reporting Purpose
	 *
	 *
	 * @exception           Exception - this exception is marked as fail in the report
	 */    
	public boolean selectByText(By oLocator, String sText, String sReportText)
	{	
		boolean ElementFindFlag = false;
		if (sText.isEmpty())
			return true;
		try
		{
			List<WebElement> oWebElementList = driver.getWebDriver().findElements(oLocator);
			for (WebElement Element:oWebElementList)
				if(!(Element.getAttribute("clientHeight").equalsIgnoreCase("0")))
				{
					new Select(Element).selectByVisibleText(sText);
					ElementFindFlag = true ;
					break;
				}
			if (ElementFindFlag)
				report.updateTestLog("Selecting value in " + sReportText , "'"+sText + "' is selected in the "+  sReportText ,Status.PASS);			
			else 
				report.updateTestLog("Selecting value in " + sReportText , sReportText + " Element not found in the APplication with the xpath " + oLocator ,Status.FAIL);
			return ElementFindFlag;
		}
		catch (Exception e) 
		{
			report.updateTestLog("Slecting the value in " + sReportText , "Exception while selecting the value '"+ sText +"' in " +  sReportText + e ,Status.FAIL);			
			return false;
		}	
	}
	
	/**
	 * This method is used to Click the Element with the Specified Locators
	 * <br><br>
	 * @author 	   Gunasekaran Nallasamy(i365976)
	 * @param 	   oLocator  By - The Locator using which we are going to identify the Element
	 * @param 	   sReportText String - The Reporting Text which will be displayed in the HTML Report
	 * @param 	   IgnoreReporting boolean - This is the boolean value which will decide whether the Reporting is needed in HTML Report or not
	 * @param 	   ScreenShot boolean - This is the boolean value which will decide whether the screenshot is needed in HTML Report or not
	 * @return     boolean(true/false) Value - Whether the Element with the Specified locator is clicked or not
	 * @exception  Exception - this exception is marked as fail in the report
	 */
	
	public boolean clickElement(By oLocator, String sReportText,boolean ScreenShot)
	{	
		try
		{	
			driver.findElement(oLocator).click();			
				report.updateTestLog("Clicking the " + sReportText, sReportText + " is clicked " , Status.PASS);
			if (ScreenShot)
				report.updateTestLog("Clicking the " + sReportText, sReportText + " is clicked " , Status.SCREENSHOT);
			return true;
		}
		catch (Exception e) 
		{
			report.updateTestLog("Clicking the " + sReportText, "Exception while clicking the "+ sReportText + " " + e.getMessage() , Status.FAIL);			
			return false;
		}		
	}
	
	/**
	 * This method is used to Click the Element with the Specified locator using the Javascript
	 * <br><br>
	 * @author 	   Gunasekaran Nallasamy(i365976)
	 * @param 	   oLocator  By - The Locator of the Element which needs to be clicked
	 * @param 	   sReportText String - The Reporting Text which will be displayed in the HTML Report
	 * @param 	   IgnoreReporting boolean - This is the boolean value which will decide whether the Reporting is needed in HTML Report or not
	 * @param 	   ScreenShot boolean - This is the boolean value which will decide whether the screenshot is needed in HTML Report or not
	 * @return     boolean(true/false) Value - Whether the Element with the Specified locator is clicked or not
	 * @exception  Exception - this exception is marked as fail in the report
	 */
	public boolean clickElement(By oLocator, String sReportText,boolean IgnoreReporting,boolean ScreenShot)
	{
		try
		{
			driver.findElement(oLocator).click();
			if(!IgnoreReporting)
			{
				if (ScreenShot)
					report.updateTestLog("Clicking the " + sReportText  , sReportText + " is clicked " ,Status.SCREENSHOT);
				else
					report.updateTestLog("Clicking the " + sReportText , sReportText + " is clicked " ,Status.PASS);
			}
			return true;
		}
		
		catch(Exception e)
		{
			if(!IgnoreReporting)
			{
				report.updateTestLog("Clicking the " + sReportText, "Exception while clicking the "+ sReportText + " " + e.getMessage() , Status.FAIL);
				//abortExecution(e.getMessage());
			}
			return false;
		}
	}
	
	/**
	 * This method is used to Click the Element with the Specified Object
	 * <br><br>
	 * @author 	   Gunasekaran Nallasamy(i365976)
	 * @param 	   oElement  WebElement - The WebElement which we need to click
	 * @param 	   sReportText String - The Reporting Text which will be displayed in the HTML Report
	 * @param 	   IgnoreReporting boolean - This is the boolean value which will decide whether the Reporting is needed in HTML Report or not
	 * @param 	   ScreenShot boolean - This is the boolean value which will decide whether the screenshot is needed in HTML Report or not
	 * @return     boolean(true/false) Value - Whether the Element with the Specified locator is clicked or not
	 * @exception  Exception - this exception is marked as fail in the report
	 */
	
	public boolean clickElement(WebElement oElement, String sReportText,boolean ScreenShot)
	{		
		try
		{	
			oElement.click();			
				report.updateTestLog("Clicking the " + sReportText, sReportText + " is clicked " , Status.PASS);
			if (ScreenShot)
				report.updateTestLog("Clicking the " + sReportText, sReportText + " is clicked " , Status.SCREENSHOT);
			return true;
		}
		catch (Exception e) 
		{
			report.updateTestLog("Clicking the " + sReportText, "Exception while clicking the "+ sReportText + " " + e.getMessage() , Status.FAIL);			
			return false;
		}		
	}
	
	public boolean checkElement(By oLocator, String sReportText,boolean IgnoreReporting,boolean ScreenShot)
	{	
		try
		{	
			if (driver.findElement(oLocator).isDisplayed())
			{
				if ((!IgnoreReporting) && (!ScreenShot))				
					report.updateTestLog("Validating the presence of " + sReportText, sReportText + " is displayed in the screen" , Status.PASS);
				else if ((!IgnoreReporting) && (ScreenShot))
					report.updateTestLog("Validating the presence of " + sReportText, sReportText + " is displayed in the screen" , Status.SCREENSHOT);
				return true;
			}
			else
			{
				if ((!IgnoreReporting))						
					report.updateTestLog("Validating the presence of " + sReportText, sReportText + " is not displayed in the screen" , Status.FAIL);
				return false;
			}
		}
				
		
		catch (NoSuchElementException e) 
		{
			if (!IgnoreReporting)			
				report.updateTestLog("Validating the presence of " + sReportText, sReportText + " is not displayed in the screen" , Status.FAIL);
			return false;
		}	
		catch (Exception e) 
		{
			if (!IgnoreReporting)
				report.updateTestLog("Validating the presence of " + sReportText, "Exception while checking the "+ sReportText + " " + e.getMessage() , Status.FAIL);			
			return false;
		}		
	}
	
	public boolean checkElementNegative(By oLocator, String sReportText,boolean IgnoreReporting,boolean ScreenShot)
	{	
		try
		{	
			if (driver.findElement(oLocator).isDisplayed())
			{
				if ((!IgnoreReporting) && (!ScreenShot))				
					report.updateTestLog("Validating the presence of " + sReportText, sReportText + " is displayed in the screen" , Status.FAIL);
				else if ((!IgnoreReporting) && (ScreenShot))
					report.updateTestLog("Validating the presence of " + sReportText, sReportText + " is displayed in the screen" , Status.SCREENSHOT);
				return false;
			}
			else
			{
				if ((!IgnoreReporting))						
					report.updateTestLog("Validating the presence of " + sReportText, sReportText + " is present but not displayed in the screen" , Status.WARNING);
				return false;
			}
		}
				
		
		catch (NoSuchElementException e) 
		{
			if (!IgnoreReporting)			
				report.updateTestLog("Validating the presence of " + sReportText, sReportText + " is not displayed in the screen" , Status.PASS);
			return true;
		}	
		catch (Exception e) 
		{
			if (!IgnoreReporting)
				report.updateTestLog("Validating the presence of " + sReportText, "Exception while checking the "+ sReportText + " " + e.getMessage() , Status.FAIL);			
			return false;
		}		
	}
	
	public boolean checkElement(WebElement OElement, String sReportText,boolean IgnoreReporting,boolean ScreenShot)
	{	
		try
		{	
			if (OElement.isDisplayed())
			{
				if ((!IgnoreReporting) && (!ScreenShot))				
					report.updateTestLog("Validating the presence of " + sReportText, sReportText + " is displayed in the screen" , Status.PASS);
				else if ((!IgnoreReporting) && (ScreenShot))
					report.updateTestLog("Validating the presence of " + sReportText, sReportText + " is displayed in the screen" , Status.SCREENSHOT);
				return true;
			}
			else
			{
				if ((!IgnoreReporting))						
					report.updateTestLog("Validating the presence of " + sReportText, sReportText + " is not displayed in the screen" , Status.FAIL);
				return false;
			}
		}
				
		
		catch (NoSuchElementException e) 
		{
			if (!IgnoreReporting)			
				report.updateTestLog("Validating the presence of " + sReportText, sReportText + " is not displayed in the screen" , Status.FAIL);
			return false;
		}	
		catch (Exception e) 
		{
			if (!IgnoreReporting)
				report.updateTestLog("Validating the presence of " + sReportText, "Exception while checking the "+ sReportText + " " + e.getMessage() , Status.FAIL);			
			return false;
		}		
	}
	
	/**
	 * This method is used to Click the Element with the Specified locator using the Javascript
	 * <br><br>
	 * @author 	   Gunasekaran Nallasamy(i365976)
	 * @param 	   oLocator  By - The Locator of the Element which needs to be clicked
	 * @param 	   sReportText String - The Reporting Text which will be displayed in the HTML Report
	 * @param 	   IgnoreReporting boolean - This is the boolean value which will decide whether the Reporting is needed in HTML Report or not
	 * @param 	   ScreenShot boolean - This is the boolean value which will decide whether the screenshot is needed in HTML Report or not
	 * @return     boolean(true/false) Value - Whether the Element with the Specified locator is clicked or not
	 * @exception  Exception - this exception is marked as fail in the report
	 */
	public boolean clickElementJS(By oLocator, String sReportText,boolean ScreenShot)
	{
		try
		{
			WebElement element = driver.getWebDriver().findElement(oLocator);
			JavascriptExecutor executor = (JavascriptExecutor) driver.getWebDriver();
			executor.executeScript("arguments[0].click();", element);
			report.updateTestLog("Clicking the " + sReportText + " using JavaScript" , sReportText + " is clicked " ,Status.PASS);
			if (ScreenShot)
				report.updateTestLog("Clicking the " + sReportText + " using JavaScript" , sReportText + " is clicked " ,Status.SCREENSHOT);
			return true;
		}
		
		catch(Exception e)
		{
			report.updateTestLog("Clicking the " + sReportText, "Exception while clicking the "+ sReportText + " " + e.getMessage() , Status.FAIL);
			return false;
		}
	}
	
	/**
	 * This method is used to Click the Element with the Specified locator using the Javascript
	 * <br><br>
	 * @author 	   Gunasekaran Nallasamy(i365976)
	 * @param 	   oLocator  By - The Locator of the Element which needs to be clicked
	 * @param 	   sReportText String - The Reporting Text which will be displayed in the HTML Report
	 * @param 	   IgnoreReporting boolean - This is the boolean value which will decide whether the Reporting is needed in HTML Report or not
	 * @param 	   ScreenShot boolean - This is the boolean value which will decide whether the screenshot is needed in HTML Report or not
	 * @return     boolean(true/false) Value - Whether the Element with the Specified locator is clicked or not
	 * @exception  Exception - this exception is marked as fail in the report
	 */
	public boolean clickElementJS(By oLocator, String sReportText,boolean IgnoreReporting,boolean ScreenShot)
	{
		try
		{
			WebElement element = driver.getWebDriver().findElement(oLocator);
			JavascriptExecutor executor = (JavascriptExecutor) driver.getWebDriver();
			executor.executeScript("arguments[0].click();", element);
			if(!IgnoreReporting)
			{
				if (ScreenShot)
					report.updateTestLog("Clicking the " + sReportText + " using JavaScript" , sReportText + " is clicked " ,Status.SCREENSHOT);
				else
					report.updateTestLog("Clicking the " + sReportText + " using JavaScript" , sReportText + " is clicked " ,Status.PASS);
			}
			return true;
		}
		
		catch(Exception e)
		{
			if(!IgnoreReporting)
			{
				report.updateTestLog("Clicking the " + sReportText, "Exception while clicking the "+ sReportText + " " + e.getMessage() , Status.FAIL);
				//abortExecution(e.getMessage());
			}
			return false;
		}
	}
	
	
	/**
	 * This method is used to Click the Element with the Specified locator using the Javascript
	 * <br><br>
	 * @author 	   Gunasekaran Nallasamy(i365976)
	 * @param 	   oElement  WebElement - The Element which needs to be clicked
	 * @param 	   sReportText String - The Reporting Text which will be displayed in the HTML Report
	 * @param 	   IgnoreReporting boolean - This is the boolean value which will decide whether the Reporting is needed in HTML Report or not
	 * @param 	   ScreenShot boolean - This is the boolean value which will decide whether the screenshot is needed in HTML Report or not
	 * @return     boolean(true/false) Value - Whether the Element with the Specified locator is clicked or not
	 * @exception  Exception - this exception is marked as fail in the report
	 */
	public boolean clickElementJS(WebElement oElement, String sReportText,boolean IgnoreReporting,boolean ScreenShot)
	{	
		try
		{	
			JavascriptExecutor executor = (JavascriptExecutor) driver.getWebDriver();
			executor.executeScript("arguments[0].click();", oElement);
			if ((!IgnoreReporting) && (!ScreenShot))
				report.updateTestLog("Clicking the " + sReportText + " using JavaScript" , sReportText + " is clicked " ,Status.PASS);
			else if ((!IgnoreReporting) && (ScreenShot))
				report.updateTestLog("Clicking the " + sReportText + " using JavaScript" , sReportText + " is clicked " ,Status.SCREENSHOT);
			return true;
		}			
		catch(Exception e)
		{
			report.updateTestLog("Clicking the " + sReportText, "Exception while clicking the "+ sReportText + " " + e.getMessage() , Status.FAIL);
			return false;
		}
	}
	/**
	* This method is used to Retrieve the web elements by object locator 
	* <br><br>
	*  @author Cognizant
	*
	*  @param oLocator               Object  - Locator value to be passed
	*  @return WebElement
	*/
	public List<WebElement> retrieveWebElementList(By oLocator, String sReportText,boolean IgnoreReporting ,boolean bScreenshot)
	{
		List<WebElement> oWebElementList=null;
		try
		{
			 oWebElementList = driver.findElements(oLocator);
			if(oWebElementList.size()>0)
			{
				if (bScreenshot)
					report.updateTestLog("Retrieve WebElementList", sReportText + " WebElements Retrieved", Status.SCREENSHOT);
				else
					report.updateTestLog("Retrieve WebElementList", sReportText + " WebElements Retrieved", Status.PASS);
			}
			else if(!IgnoreReporting)
				report.updateTestLog("Retrieve WebElementList", "Unable to Retrieve the WebElement List" + sReportText, Status.FAIL);				
		}
		catch (Exception e) 
		{
			if(!IgnoreReporting)
				report.updateTestLog("Retrieve WebElementList", "Exception Occured while retrieveing the webelement List" + e.getMessage() , Status.FAIL);
		}
		finally
		{
			return oWebElementList;
		}
		
	}
	
	public void clickJSandSendKeys(By oLocator, Keys sKeys, String sReportText, boolean bScreenshot) 
	{
		try
		{
			WebElement oWebElement = driver.findElement(oLocator);
			ScrollTillWebElementVisible(oWebElement,sReportText);
			
			  WebElement element = driver.getWebDriver().findElement(oLocator);
			  JavascriptExecutor executor = (JavascriptExecutor) driver.getWebDriver();
			  executor.executeScript("arguments[0].click();", element);
			 
			Thread.sleep(1000);
			if (!sKeys.equals(null))
			{	
				element.sendKeys(sKeys);
				Thread.sleep(1000);
				oWebElement.sendKeys(sKeys);
			}
			oWebElement.sendKeys(Keys.ENTER);
		
			if (bScreenshot)
				report.updateTestLog("Clicking and Sending the Keys in " +sReportText, sReportText + "  is clicked and sent the Keys ", Status.SCREENSHOT);
			else
				report.updateTestLog("Clicking and Sending the Keys in " +sReportText, sReportText + "   is clicked and sent the Keys ", Status.PASS);
		}
		catch (Exception e) 
		{	
			report.updateTestLog("Clicking and Sending teh Keys in " +sReportText, "Exception Occured while Clicking " + sReportText + e.getMessage() , Status.FAIL);
		}
	}
	
	/**
	 * This method is used to retrieve the DropDownValue 
	 * <br><br>
	 * @author 	   Gunasekaran Nallasamy(i365976)
	 * @param 	   WebElement oWebElement - The  Element which needs to be highlighted 
	 * @param 	   sReportText String - The Reporting Text which will be displayed in the HTML Report
	 * @return 	   ArrayList<String> aActualWebList -  The List of items retrieved from the WebList
	 * @exception  Exception - this exception is marked as fail in the report
	 */
	public ArrayList<String> RetrieveWebList( WebElement oWebElement,String sReportText)
	{
		ArrayList<String> aActualWebList = new ArrayList<String>();
		try
		{
			
			Select objSelect=new Select(oWebElement);			
			List<WebElement> oActualList = objSelect.getOptions();
			
			for(int i = 0;i<oActualList.size();i++ )
			{
				String svalue = oActualList.get(i).getText();
				if(svalue.isEmpty())
					svalue = oActualList.get(i).getAttribute("innerText");
				if (!(svalue.isEmpty()))					
					aActualWebList.add(svalue);	
			}
			report.updateTestLog("Retrieving the Web List " + sReportText + " values",aActualWebList + " are retrieved in the " + sReportText ,Status.PASS);
		}		
		catch(Exception e)
		{
			report.updateTestLog("Retrieving the Web List " + sReportText + " values","Exception Occured while retrieveing the Drop Down List " + e ,Status.FAIL);				
		}
		return aActualWebList;			
	}
	/**
	 * This method is used to retrieve the DropDownValue 
	 * <br><br>
	 * @author 	   Gunasekaran Nallasamy(i365976)
	 * @param 	   oLocator  By - The Locator of the Element which needs to be highlighted 
	 * @param 	   sReportText String - The Reporting Text which will be displayed in the HTML Report
	 * @return 	   ArrayList<String> aActualWebList -  The List of items retrieved from the WebList
	 * @exception  Exception - this exception is marked as fail in the report
	 */
	public ArrayList<String> RetrieveWebList( By oLocator,String sReportText)
	{
		ArrayList<String> aActualWebList = new ArrayList<String>();
		try
		{
			
			Select objSelect=new Select(driver.findElement(oLocator));			
			List<WebElement> oActualList = objSelect.getOptions();
			
			for(int i = 0;i<oActualList.size();i++ )
			{
				String svalue = oActualList.get(i).getText();
				if(svalue.isEmpty())
					svalue = oActualList.get(i).getAttribute("innerText");
				if (!(svalue.isEmpty()))					
					aActualWebList.add(svalue);	
			}
			report.updateTestLog("Retrieving the Web List " + sReportText + " values",aActualWebList + " are retrieved in the " + sReportText ,Status.PASS);
		}		
		catch(Exception e)
		{
			report.updateTestLog("Retrieving the Web List " + sReportText + " values","Exception Occured while retrieveing the Drop Down List " + e ,Status.FAIL);				
		}
		return aActualWebList;			
	}
	public ArrayList<String> RetrieveTextWebList(By oLocator,String sReportText)
	{
		ArrayList<String> aActualWebList = new ArrayList<String>();
		try
		{
			List<WebElement> actualelement = retrieveWebElementList(oLocator,sReportText,false ,true);
			for(int i=0;i<actualelement.size();i++)
			{
				String svalue = actualelement.get(i).getText();
				if(svalue.isEmpty())
					svalue = actualelement.get(i).getAttribute("innerText");
				if (!(svalue.isEmpty()))					
					aActualWebList.add(svalue);	
			}
		}
		catch(Exception e)
		{
			report.updateTestLog("Retrieving the Web List " + sReportText + " values","Exception Occured while retrieveing the Drop Down List " + e ,Status.FAIL);
		}
		return aActualWebList;	
	}
	
	/**
	 * This method is used to Click the Element with the Specified Locators
	 * <br><br>
	 * @author 	   Gunasekaran Nallasamy(i365976)
	 * @param 	   oLocator  By - The Locator using which we are going to identify the Element
	 * @param 	   sReportText String - The Reporting Text which will be displayed in the HTML Report
	 * @param 	   IgnoreReporting boolean - This is the boolean value which will decide whether the Reporting is needed in HTML Report or not
	 * @param 	   ScreenShot boolean - This is the boolean value which will decide whether the screenshot is needed in HTML Report or not
	 * @return     boolean(true/false) Value - Whether the Element with the Specified locator is clicked or not
	 * @exception  Exception - this exception is marked as fail in the report
	 */
	
	public boolean clickVisibleElement(By oLocator, String sReportText,boolean IgnoreReporting,boolean ScreenShot)
	{
		try
		{	
			List<WebElement> oWebElementList = driver.getWebDriver().findElements(oLocator);
			for (WebElement Element:oWebElementList)
				if(!(Element.getAttribute("clientHeight").equalsIgnoreCase("0")))
				{
					Element.click();
					if ((!IgnoreReporting) && (!ScreenShot))
						report.updateTestLog("Clicking the " + sReportText, sReportText + " is clicked " , Status.PASS);
					else if ((!IgnoreReporting) && (ScreenShot))
						report.updateTestLog("Clicking the " + sReportText, sReportText + " is clicked " , Status.SCREENSHOT);
					 break;
				}
			return true;
		}
		catch (Exception e) 
		{
			report.updateTestLog("Clicking the " + sReportText, "Exception while clicking the "+ sReportText + " " + e.getMessage() , Status.FAIL);			
			return false;
		}		
	}
	
	/**
	 * This method is used to Click the Element with the Specified locator using the Javascript
	 * <br><br>
	 * @author 	   Gunasekaran Nallasamy(i365976)
	 * @param 	   oLocator  By - The Locator of the Element which needs to be clicked
	 * @param 	   sReportText String - The Reporting Text which will be displayed in the HTML Report
	 * @param 	   IgnoreReporting boolean - This is the boolean value which will decide whether the Reporting is needed in HTML Report or not
	 * @param 	   ScreenShot boolean - This is the boolean value which will decide whether the screenshot is needed in HTML Report or not
	 * @return     boolean(true/false) Value - Whether the Element with the Specified locator is clicked or not
	 * @exception  Exception - this exception is marked as fail in the report
	 */
	public boolean clickVisibleElementJS(By oLocator, String sReportText,boolean IgnoreReporting,boolean ScreenShot)
	{	
		try
		{
			List<WebElement> oWebElementList = driver.getWebDriver().findElements(oLocator);
			if ((oWebElementList.size()==0) && (!IgnoreReporting))
				report.updateTestLog("Clicking the " + sReportText, "Unable to find the Element with the xpath " + oLocator.toString()  , Status.FAIL);
			for (WebElement Element:oWebElementList)
				if(!(Element.getAttribute("offsetHeight").equalsIgnoreCase("0")))
				{						
					JavascriptExecutor executor = (JavascriptExecutor) driver.getWebDriver();
					executor.executeScript("arguments[0].click();", Element);
					if ((!IgnoreReporting) && (!ScreenShot))
						report.updateTestLog("Clicking the " + sReportText + " using JavaScript" , sReportText + " is clicked " ,Status.PASS);
					else if ((!IgnoreReporting) && (ScreenShot))
						report.updateTestLog("Clicking the " + sReportText + " using JavaScript" , sReportText + " is clicked " ,Status.SCREENSHOT);
					break;
				}
					return true;
		}
		
		catch(Exception e)
		{
			if (!IgnoreReporting)
				report.updateTestLog("Clicking the " + sReportText, "Exception while clicking the "+ sReportText + " " + e.getMessage() , Status.FAIL);
			return false;
		}
	}
	
	/**
	 * This method is used to Retrieve the Attribute Value of the Specified Object using its Locator
	 * <br><br>
	 * @author 	   Gunasekaran Nallasamy(i365976)
	 * @param 	   oLocator  By - The Locator of the WebElement whose Attribute value needs to be retrieved
	 * @param 	   sAttribute String - This Specifies the Attribute which needs to be retrieved (ex: Text,InnerText)
	 * @param 	   sReportText String - The Reporting Text which will be displayed in the HTML Report
	 * @param 	   IgnoreReporting boolean - This is the boolean value which will decide whether the Reporting is needed in HTML Report or not
	 * @param 	   ScreenShot boolean - This is the boolean value which will decide whether the screenshot is needed in HTML Report or not
	 * @return     String Value - The Attribute value of the WebElement
	 * @exception  Exception - this exception is marked as fail in the report
	 */
	
	public String getAttribute(By oLocator, String sAttribute, String sReportText, boolean IgnoreReporting, boolean ScreenShot)
	{
		
		try
		{
			String sValue="";
						
			WebElement oWebElement = driver.findElement(oLocator);
			if (sAttribute.equalsIgnoreCase("text"))
				sValue = oWebElement.getText();
			else if (sAttribute.equalsIgnoreCase("tag"))
				sValue = oWebElement.getTagName();
			else
				sValue =  oWebElement.getAttribute(sAttribute);
			
			if ((!IgnoreReporting) && (!ScreenShot))
				report.updateTestLog("Retrieving the value of " + sReportText, sReportText + ", Attribute retrieved Successfully. " + sAttribute + " = " + sValue,Status.PASS);
			else if ((!IgnoreReporting) && (ScreenShot))
				report.updateTestLog("Retrieving the value of " + sReportText, sReportText + ", Attribute retrieved Successfully. " + sAttribute + " = " + sValue,Status.SCREENSHOT);				
			
			return sValue;			
		}
		catch (NoSuchElementException e) 
		{
			report.updateTestLog("Retrieving the value of " + sReportText, sReportText + " is not displayed in the screen" , Status.FAIL);
			
			return "";
		}	
		catch (Exception e) 
		{
			report.updateTestLog("Retrieving the value of " + sReportText, "Exception while Retrieving the value of "+ sReportText + " " + e.getMessage() , Status.FAIL);			
			return "";
		}
	}
	
	
	/**
	 * This method is used to Retrieve the Attribute Value of the Specified Object
	 * <br><br>
	 * @author 	   Gunasekaran Nallasamy(i365976)
	 * @param 	   oWebElement  WebElement - The WebElement whose Attribute value needs to be retrieved
	 * @param 	   sAttribute String - This Specifies the Attribute which needs to be retrieved (ex: Text,InnerText)
	 * @param 	   sReportText String - The Reporting Text which will be displayed in the HTML Report
	 * @param 	   IgnoreReporting boolean - This is the boolean value which will decide whether the Reporting is needed in HTML Report or not
	 * @param 	   ScreenShot boolean - This is the boolean value which will decide whether the screenshot is needed in HTML Report or not
	 * @return     String Value - The Attribute value of the WebElement
	 * @exception  Exception - this exception is marked as fail in the report
	 */
	
	public String getAttribute(WebElement oWebElement, String sAttribute, String sReportText, boolean IgnoreReporting, boolean ScreenShot)
	{			
		try
		{
			String sValue="";								
			if (sAttribute.equalsIgnoreCase("text"))
				sValue = oWebElement.getText();
			else if (sAttribute.equalsIgnoreCase("tag"))
				sValue = oWebElement.getTagName();
			else
				sValue =  oWebElement.getAttribute(sAttribute);				
			
			if ((!IgnoreReporting) && (!ScreenShot))
				report.updateTestLog("Retrieving the value of " + sReportText, sReportText + ", Attribute retrieved Successfully. " + sAttribute + " = " + sValue,Status.PASS);
			else if ((!IgnoreReporting) && (ScreenShot))
				report.updateTestLog("Retrieving the value of " + sReportText, sReportText + ", Attribute retrieved Successfully. " + sAttribute + " = " + sValue,Status.SCREENSHOT);
			
			return sValue;
		}
		catch (NoSuchElementException e) 
		{
			report.updateTestLog("Retrieving the value of " + sReportText, sReportText + " is not displayed in the screen" , Status.FAIL);			
			return "";
		}	
		catch (Exception e) 
		{
			report.updateTestLog("Retrieving the value of " + sReportText, "Exception while Retrieving the value of "+ sReportText + " " + e.getMessage() , Status.FAIL);			
			return "";
		}
	}
	
	/**
	 * This method is used to Retrieve the Attribute Value of the Specified Object using its Locator and Validate
	 * <br><br>
	 * @author 	   Gunasekaran Nallasamy(i365976)
	 * @param 	   oLocator  By - The Locator of the WebElement whose Attribute value needs to be retrieved
	 * @param 	   sAttribute String - This Specifies the Attribute which needs to be retrieved (ex: Text,InnerText)
	 * @param 	   sReportText String - The Reporting Text which will be displayed in the HTML Report
	 * @param 	   IgnoreReporting boolean - This is the boolean value which will decide whether the Reporting is needed in HTML Report or not
	 * @param 	   ScreenShot boolean - This is the boolean value which will decide whether the screenshot is needed in HTML Report or not
	 * @return     String Value - The Attribute value of the WebElement
	 * @exception  Exception - this exception is marked as fail in the report
	 */
	
	public String checkElementAttribute(By oLocator, String sAttribute,String sExpectedText, String sReportText, boolean ScreenShot)
	{
		
		try
		{
			String sActualValue="";
			
			WebElement oWebElement = driver.findElement(oLocator);
			if (sAttribute.equalsIgnoreCase("text"))
				sActualValue = oWebElement.getText();
			else if (sAttribute.equalsIgnoreCase("tag"))
				sActualValue = oWebElement.getTagName();
			else if (sAttribute.equalsIgnoreCase("isSelected"))
				sActualValue = String.valueOf(oWebElement.isSelected());
			else
				sActualValue =  oWebElement.getAttribute(sAttribute);
			
			if(sActualValue.equals(sExpectedText))
			{
				if (ScreenShot)
					report.updateTestLog("Validation of Element Attribute " + sReportText, sActualValue + " is successfully displayed for the Element " + sReportText  ,Status.SCREENSHOT);
				else 
					report.updateTestLog("Validation of Element Attribute " + sReportText, sActualValue + " is successfully displayed for the Element " + sReportText  ,Status.PASS);
			}
			else
				report.updateTestLog("Validation of Element Attribute " + sReportText, sReportText + "'s Expected Value : " + sExpectedText + " ; Actual Value : " + sActualValue  , Status.FAIL);
			
			return sActualValue;			
		}
		catch (NoSuchElementException e) 
		{
			report.updateTestLog("Retrieving the value of " + sReportText, sReportText + " is not displayed in the screen" , Status.FAIL);
			
			return "";
		}	
		catch (Exception e) 
		{
			report.updateTestLog("Retrieving the value of " + sReportText, "Exception while Retrieving the value of "+ sReportText + " " + e.getMessage() , Status.FAIL);			
			return "";
		}
	}
	
	/**
	 * This method is used to Retrieve the Attribute Value of the Specified Object using its Locator and Validate
	 * <br><br>
	 * @author 	   Gunasekaran Nallasamy(i365976)
	 * @param 	   oLocator  By - The Locator of the WebElement whose Attribute value needs to be retrieved
	 * @param 	   sAttribute String - This Specifies the Attribute which needs to be retrieved (ex: Text,InnerText)
	 * @param 	   sReportText String - The Reporting Text which will be displayed in the HTML Report
	 * @param 	   IgnoreReporting boolean - This is the boolean value which will decide whether the Reporting is needed in HTML Report or not
	 * @param 	   ScreenShot boolean - This is the boolean value which will decide whether the screenshot is needed in HTML Report or not
	 * @return     String Value - The Attribute value of the WebElement
	 * @exception  Exception - this exception is marked as fail in the report
	 */
	
	public String checkElementAttribute(WebElement oWebElement, String sAttribute,String sExpectedText, String sReportText, boolean ScreenShot)
	{
		
		try
		{
			String sActualValue="";
			if (sAttribute.equalsIgnoreCase("text"))
				sActualValue = oWebElement.getText();
			else if (sAttribute.equalsIgnoreCase("tag"))
				sActualValue = oWebElement.getTagName();
			else
				sActualValue =  oWebElement.getAttribute(sAttribute);
			
			if(sActualValue.equals(sExpectedText))
			{
				if (ScreenShot)
					report.updateTestLog("Validation of Element Attribute " + sReportText, sActualValue + " is successfully displayed for the Element " + sReportText  ,Status.SCREENSHOT);
				else 
					report.updateTestLog("Validation of Element Attribute " + sReportText, sActualValue + " is successfully displayed for the Element " + sReportText  ,Status.PASS);
			}
			else
				report.updateTestLog("Validation of Element Attribute " + sReportText, sReportText + "'s Expected Value : " + sExpectedText + " ; Actual Value : " + sActualValue  , Status.FAIL);
			
			return sActualValue;			
		}
		catch (NoSuchElementException e) 
		{
			report.updateTestLog("Retrieving the value of " + sReportText, sReportText + " is not displayed in the screen" , Status.FAIL);
			
			return "";
		}	
		catch (Exception e) 
		{
			report.updateTestLog("Retrieving the value of " + sReportText, "Exception while Retrieving the value of "+ sReportText + " " + e.getMessage() , Status.FAIL);			
			return "";
		}
	}
	
	public boolean enterTextInVisibleElement(By oLocator, String text,boolean IgnoreReporting,boolean ScreenShot)
	{
		try
		{	
			List<WebElement> oWebElementList = driver.getWebDriver().findElements(oLocator);
			for (WebElement Element:oWebElementList)
				if(!(Element.getAttribute("clientHeight").equalsIgnoreCase("0")))
				{
					Element.clear();
					Element.sendKeys(text);
					if ((!IgnoreReporting) && (!ScreenShot))
						report.updateTestLog("Entering the text "  + text, "The Text " + text + " is entered " , Status.PASS);
					else if ((!IgnoreReporting) && (ScreenShot))
						report.updateTestLog("Entering the text " + text , "The Text " + text + " is entered  " , Status.PASS);
					 break;
				}
			return true;
		}
		catch (Exception e) 
		{
			report.updateTestLog("Entering the " + text, "Exception while entering the "+ text + " " + e.getMessage() , Status.FAIL);			
			return false;
		}		
	}
	 public boolean ValidateCurrentURL(String sURL, String sReportText) 
     {
         try
         {             	 
      	  if(sURL.contentEquals(driver.getCurrentUrl()))
      		  report.updateTestLog("Validating the link " + sReportText, "The URL <b>" + sURL + "</b> is opened"  , Status.SCREENSHOT);
      	  else
      		  report.updateTestLog("Validating the link " + sReportText,"Expected URL : "+ sURL + "\n Actual URL : " +driver.getCurrentUrl() , Status.FAIL);            	              	  
      	   return true;
         }
         catch(Exception e)
         {
               report.updateTestLog("Validating the link " + sReportText, "Exception while Validating the link "+ sReportText + " " + e.getMessage() , Status.FAIL);
               return false;
         }
			
		}
	 
	 public void closeChildWindows()
		{
			
			try
			{
				driver.getWebDriver();
				Thread.sleep(2000);				
				String sParentWindow =driver.getWindowHandle();
				for (String Child_Window : driver.getWindowHandles()) 
				{
					Thread.sleep(2000);
					if ((!(Child_Window.equals(sParentWindow)))) 
					{
						driver.switchTo().window(Child_Window);
						driver.close();
						driver.switchTo().window(sParentWindow);
						//driver.manage().window().maximize();
						report.updateTestLog("Closing all the Child Windows", "Focus Switched to Child Window" , Status.PASS);
					}
					else
						report.updateTestLog("Closing all the Child Windows", "There is no Child Window" , Status.PASS);
				}
				
			}
			catch(Exception e)
			{
				report.updateTestLog("Closing all the Child Windows", "Unable to close Child Window " + e.getMessage() , Status.PASS);
			}
		}
	 
	public String switchToChildWindow()
	{
		
		try
		{
			Thread.sleep(2000);				
			String sParentWindow =driver.getWindowHandle();
			for (String Child_Window : driver.getWindowHandles()) 
			{
				Thread.sleep(2000);
				if ((!(Child_Window.equals(sParentWindow)))) 
				{
					driver.switchTo().window(Child_Window);
					//driver.manage().window().maximize();
					report.updateTestLog("Switching Control to Child Window", "Focus Switched to Child Window" , Status.PASS);
				}
				else
					report.updateTestLog("Switching Control to Child Window", "There is no Child Window" , Status.PASS);
			}
			return sParentWindow; 
		}
		catch(Exception e)
		{
			report.updateTestLog("Switching Control to Child Window", "Unable to Switch the focus to Child Window " + e.getMessage() , Status.WARNING);
			return "false";
		}
	}
	
	public boolean switchtoparentWindow(String sParentWindow)
	{	
		try
		{
			driver.switchTo().window(sParentWindow);
			report.updateTestLog("Switching Control to Parent Window", "Focus Switched to Parent Window" , Status.PASS);
			return true;
		}
		catch(Exception e)
		{
			report.updateTestLog("Switching Control to Parent Window", "Unable to Switch the focus to Parent Window " + e.getMessage() , Status.FAIL);
			return false;
		}
	}
		
	
	public boolean switchtoDefaultContent()
	{	
		try
		{
			driver.switchTo().defaultContent();				
			report.updateTestLog("Switching Control to Default Window", "Focus Switched to Default Window" , Status.PASS);
			return true;
		}
		catch(Exception e)
		{
			report.updateTestLog("Switching Control to Default Window", "Unable to Switch the focus to Default Window " + e.getMessage() , Status.PASS);
			return false;
		}
	}
	
	public boolean switchtoFrame(By oLocator, String sFrameName) 
	{	
		try
		{	
			driver.switchTo().frame(driver.findElement(oLocator));
			report.updateTestLog("Switching Control to the Frame" + sFrameName, "Focus Switched to the Frame :" + sFrameName , Status.PASS);
			return true;
		}
		catch(Exception e)
		{
			report.updateTestLog("Switching Control to the Frame" + sFrameName, "Unable to Switch the focus to the Frame :" + sFrameName + e.getMessage() , Status.FAIL);
			return false;
		}
		
	}
	
	public boolean checkandAcceptAlert() 
	{
		boolean alertAccepted = false;
		try 
		{	
			String sAlertText = driver.switchTo().alert().getText();			
			report.updateTestLog("Alert Window ", sAlertText + " is displayed in the Alert Popup" , Status.WARNING);
			driver.switchTo().alert().accept();
			alertAccepted = true;
		} 
		catch (Exception e)
		{
			alertAccepted = false;
		}
		return alertAccepted;
	}
	
	/**
	 * This method is to wait for checking that an element is either invisible or not present on the DOM
	 * <br><br>
	 * @author Cognizant
	 * 
	 * @param oLocator  By 			- locator used to find the element
	 * @paramsReportText  String  - reporting Text
	 * @param lTimeOut - long
	 *  
	 * @see Report#StepPass
	 * @see Report#StepFail
	 * @exception           Exception - this exception is marked as fail in the report
	 */ 
	
	public boolean invisibilityOfElementLocated(By oLocator, String sReportText, long lTimeOut)
	{
				
		if (lTimeOut == 0)
			lTimeOut = 60;			
		try		
		{
			ResetWaitTime(5);
			WebDriverWait wait;
			wait = new WebDriverWait(driver.getWebDriver() , lTimeOut);
			wait.until(ExpectedConditions.invisibilityOfElementLocated(oLocator));
			//WaitTillElemtToBeClickable(By.id("buttonShowGlobalHelp"), "Help Button", 0);				

			report.updateTestLog("Waiting for the " + sReportText + " to Disappear", sReportText + " is disappeared ",Status.PASS);
			return true;
		}
		catch (TimeoutException e) 
		{	
			report.updateTestLog("Waiting for the " + sReportText + " to Disappear", "Time out reached while waiting for "+ sReportText + " to  disappear "+ e.getMessage(),Status.WARNING);
			return false;
		}
		catch (Exception e) 
		{
			report.updateTestLog("Waiting for the " + sReportText + " to Disappear", "Exception Occured while waiting for "+ sReportText + " to disappear "+ e.getMessage(),Status.FAIL);
			return false;
		}
		finally
		{
			long objectSyncTimeout = Long.parseLong(properties.get("ObjectSyncTimeout").toString());
			ResetWaitTime(objectSyncTimeout);	
		}
	}
	
	/**
	 * This method is to wait for checking that an element is Visible on the DOM
	 * 
	 * 52396 SP30
	 * */
	
	public boolean WaitTillElemtToBeVisible(By oLocator, String sReportText, long lTimeOut)
	{
		
		if (lTimeOut == 0)
			lTimeOut = 60;
		try		
		{	
			WebDriverWait wait;
			wait = new WebDriverWait(driver.getWebDriver(), lTimeOut);
			wait.until(ExpectedConditions.visibilityOfElementLocated(oLocator));
			report.updateTestLog("Waiting for the " + sReportText + " to Appear", sReportText + " is Appeared ",Status.PASS);
			return true;
		}
		catch (TimeoutException e) 
		{	
			report.updateTestLog("Waiting for the " + sReportText + " to Appear", "Time out reached while waiting for "+ sReportText + " to  Appear "+ e.getMessage(),Status.FAIL);
			return false;
		}
		catch (Exception e) 
		{
			report.updateTestLog("Waiting for the " + sReportText + " to Appear", "Exception Occured while waiting for "+ sReportText + " to Appear "+ e.getMessage(),Status.FAIL);
			return false;
		}	
	}
	
	/**
	 * This method is to wait for checking that an element is Selectable on the DOM
	 * <br><br>
	 * @author Gunasekaran Nallasamy/i365976
	 * 
	 * @param oLocator  By 			- locator used to find the element
	 * @paramsReportText  String  - reporting Text
	 * @param lTimeOut - long
	 *  
	 * @see Report#StepPass
	 * @see Report#StepFail
	 * @exception           Exception - this exception is marked as fail in the report
	 */ 
	
	public boolean WaitTillElemtToBeClickable(By oLocator, String sReportText, long lTimeOut)
	{
		
		if (lTimeOut == 0)
			lTimeOut = 60;
		try		
		{	
			WebDriverWait wait;
			wait = new WebDriverWait(driver.getWebDriver(), lTimeOut);
			wait.until(ExpectedConditions.elementToBeClickable(oLocator));
			report.updateTestLog("Waiting for the " + sReportText + " to Appear", sReportText + " is Appeared ",Status.PASS);
			return true;
		}
		catch (TimeoutException e) 
		{	
			report.updateTestLog("Waiting for the " + sReportText + " to Appear", "Time out reached while waiting for "+ sReportText + " to  Appear "+ e.getMessage(),Status.FAIL);
			return false;
		}
		catch (Exception e) 
		{
			report.updateTestLog("Waiting for the " + sReportText + " to Appear", "Exception Occured while waiting for "+ sReportText + " to Appear "+ e.getMessage(),Status.FAIL);
			return false;
		}	
	}
	
	/**
	 * This method is to wait for checking that an element is either visible or present on the DOM
	 * <br><br>
	 * @author Cognizant
	 * 
	 * @param oLocator  By 			- locator used to find the element
	 * @paramsReportText  String  - reporting Text
	 * @param lTimeOut - long
	 *  
	 * @see Report#StepPass
	 * @see Report#StepFail
	 * @exception           Exception - this exception is marked as fail in the report
	 */ 
	
	public boolean visibilityOfElementLocated(By oLocator, String sReportText, long lTimeOut)
	{	
		if (lTimeOut == 0)
			lTimeOut = 30;
		
		try		
		{
			WebDriverWait wait;
			wait = new WebDriverWait(driver.getWebDriver(), lTimeOut);
			wait.until(ExpectedConditions.visibilityOfElementLocated(oLocator));
			report.updateTestLog("Waiting for the " + sReportText + " to Appear", sReportText + " is Appeared ",Status.PASS);
			return true;
		}
		catch (TimeoutException e) 
		{	
			report.updateTestLog("Waiting for the " + sReportText + " to Appear", "Time out reached while waiting for "+ sReportText + " to  Appear "+ e.getMessage(),Status.FAIL);
			return false;
		}
		catch (Exception e) 
		{
			report.updateTestLog("Waiting for the " + sReportText + " to Appear", "Exception Occured while waiting for "+ sReportText + " to Appear "+ e.getMessage(),Status.FAIL);
			return false;
		}		
	}
	
	/**
	* This method is used to scroll the page till the web element identify 
	* <br><br>
	*  @author 
	
	*  @param objLocator              Object - locator value to be passed
	*  @return none	
	*/
	
	public void ScrollTillWebElementVisible(WebElement OElement, String sReportText)
	{
		//String sMethodName = new Object(){}.getClass().getEnclosingMethod().getName();
		//WebElement element = driver.getWebDriver().findElement(oLocator);
		JavascriptExecutor executor = (JavascriptExecutor) driver.getWebDriver();		
		executor.executeScript("arguments[0].scrollIntoView(true);", OElement);
	}
	
	public void ScrollTillWebElementVisible(By oLocator, String sReportText)
	{
		//String sMethodName = new Object(){}.getClass().getEnclosingMethod().getName();
		WebElement element = driver.getWebDriver().findElement(oLocator);
		JavascriptExecutor executor = (JavascriptExecutor) driver.getWebDriver();		
		executor.executeScript("arguments[0].scrollIntoView(true);", element);
	}
	public boolean ScrolltoBottomfPage()
	{	
		JavascriptExecutor js = ((JavascriptExecutor) driver.getWebDriver());
		js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
		report.updateTestLog("Scrolling to the bottom of the page" , "Scrolled to the bottom of the page" ,Status.PASS);
		return true;
	}
	
	public void scrollUntilVisibleDynamicLoading(By elem) 
	{
		JavascriptExecutor js = (JavascriptExecutor) driver.getWebDriver();
		for (int i = 0; i < 20; i++) 
		{
			js.executeScript("window.scrollBy(0,400)");
			if (driver.findElements(elem).size() > 0) 
			{
				break;
			}
		}
	}
	public void ResetWaitTime(long objectSyncTimeout) 
	{
		//Long.parseLong(properties.get("ObjectSyncTimeout").toString());
		driver.manage().timeouts().implicitlyWait(objectSyncTimeout, TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(objectSyncTimeout, TimeUnit.SECONDS);
	}

	/**
	 * This method is used to check the DropDownValue equals the Expected Items
	 * <br><br>
	 * @author 	   Gunasekaran Nallasamy(i365976)
	 * @param 	   oLocator  By - The Locator of the Element which needs to be highlighted
	 * @param 	   aExpectedItems ArrayList<String> - tHE Array List which contains the Expected Item list
	 * @param 	   sReportText String - The Reporting Text which will be displayed in the HTML Report
	 * @exception  Exception - this exception is marked as fail in the report
	 */
	public void ValidateWebListNotContains( By oLocator,ArrayList<String> aExpectedItems,String sReportText)
	{	
		try
		{
			ArrayList<String> aActualItems = RetrieveWebList(oLocator,sReportText);
			ValidateArralyListNotContains(aActualItems,aExpectedItems,sReportText);
		}		
		catch(Exception e)
		{
			report.updateTestLog("validating the Web List " + sReportText,"Exception Occured while Validating the Drop Down List " + sReportText ,Status.FAIL);
		}
	}
	
	/**
	 * This method is used to check the DropDownValue equals the Expected Items
	 * <br><br>
	 * @author 	   Gunasekaran Nallasamy(i365976)
	 * @param 	   oLocator  By - The Locator of the Element which needs to be highlighted
	 * @param 	   aExpectedItems ArrayList<String> - tHE Array List which contains the Expected Item list
	 * @param 	   sReportText String - The Reporting Text which will be displayed in the HTML Report
	 * @exception  Exception - this exception is marked as fail in the report
	 */
	public void ValidateWebListContains( By oLocator,ArrayList<String> aExpectedItems,String sReportText)
	{	
		try
		{
			ArrayList<String> aActualItems = RetrieveWebList(oLocator,sReportText);
			ValidateArralyListContains(aActualItems,aExpectedItems,sReportText);
		}		
		catch(Exception e)
		{
			report.updateTestLog("validating the Web List " + sReportText,"Exception Occured while Validating the Drop Down List " + sReportText ,Status.FAIL);
		}
	}
	
	/**
	 * This method is used to check the DropDownValue equals the Expected Items
	 * <br><br>
	 * @author 	   Gunasekaran Nallasamy(i365976)
	 * @param 	   oLocator  By - The Locator of the Element which needs to be highlighted
	 * @param 	   aExpectedItems ArrayList<String> - tHE Array List which contains the Expected Item list
	 * @param 	   sReportText String - The Reporting Text which will be displayed in the HTML Report
	 * @exception  Exception - this exception is marked as fail in the report
	 */
	public void ValidateWebListEquals( By oLocator,ArrayList<String> aExpectedItems,String sReportText)
	{	
		try
		{
			ArrayList<String> aActualItems = RetrieveWebList(oLocator,sReportText);
			ValidateArralyListEquals(aActualItems,aExpectedItems,sReportText);
		}		
		catch(Exception e)
		{
			report.updateTestLog("validating the Web List " + sReportText,"Exception Occured while Validating the Drop Down List " + sReportText ,Status.FAIL);
		}
	}

	public void ValidateWebListEqualsOrder( By oLocator,ArrayList<String> aExpectedItems,String sReportText)
	{	
		try
		{
			ArrayList<String> aActualItems = RetrieveWebList(oLocator,sReportText);
			ValidateArralyListEqualsOrder(aActualItems,aExpectedItems,sReportText);
		}		
		catch(Exception e)
		{
			report.updateTestLog("validating the Web List " + sReportText,"Exception Occured while Validating the Drop Down List " + sReportText ,Status.FAIL);
		}
	}
	
	public void abortExecution(String sExceptionMessage) throws Exception 
	{
			report.updateTestLog("Aborting the Execution","Exception occured when clicking the element : " + sExceptionMessage,Status.FAIL);
			throw new Exception("Automation Test Status: FAILURE. Please view html reports and screenshots for the failure");
		
	}
	
	/**
	 * This method is used to Select the Check box after checking if it is already selectde
	 * <br><br>
	 * @author 	   Gunasekaran Nallasamy(i365976)
	 * @param 	   oLocator  By - The Locator using which we are going to identify the Element
	 * @param 	   sReportText String - The Reporting Text which will be displayed in the HTML Report
	 * @param 	   IgnoreReporting boolean - This is the boolean value which will decide whether the Reporting is needed in HTML Report or not
	 * @param 	   ScreenShot boolean - This is the boolean value which will decide whether the screenshot is needed in HTML Report or not
	 * @return     boolean(true/false) Value - Whether the Element with the Specified locator is clicked or not
	 * @exception  Exception - this exception is marked as fail in the report
	 */
	
	public boolean selectCheckBox(By oLocator, String sReportText,boolean ScreenShot)
	{	
		try
		{	
			if(!driver.findElement(oLocator).isSelected())
			{				
				 WebElement element = driver.getWebDriver().findElement(oLocator);
				 JavascriptExecutor executor = (JavascriptExecutor) driver.getWebDriver();
				 executor.executeScript("arguments[0].click();", element);
				report.updateTestLog("Clicking the " + sReportText, sReportText + " is Checked " , Status.PASS);
			}
			else
				report.updateTestLog("Clicking the " + sReportText, sReportText + " is already Checked " , Status.WARNING);
			if (ScreenShot)
				report.updateTestLog("Clicking the " + sReportText, sReportText + " is clicked " , Status.SCREENSHOT);
			return true;
		}
		catch (Exception e) 
		{
			report.updateTestLog("Clicking the " + sReportText, "Exception while clicking the "+ sReportText + " " + e.getMessage() , Status.FAIL);			
			return false;
		}		
	}
	
	
	/**
	 * This method is used to Select the Check box after checking if it is already selectde
	 * <br><br>
	 * @author 	   Gunasekaran Nallasamy(i365976)
	 * @param 	   oLocator  By - The Locator using which we are going to identify the Element
	 * @param 	   sReportText String - The Reporting Text which will be displayed in the HTML Report
	 * @param 	   IgnoreReporting boolean - This is the boolean value which will decide whether the Reporting is needed in HTML Report or not
	 * @param 	   ScreenShot boolean - This is the boolean value which will decide whether the screenshot is needed in HTML Report or not
	 * @return     boolean(true/false) Value - Whether the Element with the Specified locator is clicked or not
	 * @exception  Exception - this exception is marked as fail in the report
	 */
	
	public boolean selectCheckBox(WebElement oWebElement, String sReportText,boolean ScreenShot)
	{	
		try
		{	
			if(!oWebElement.isSelected())
			{	 
				 JavascriptExecutor executor = (JavascriptExecutor) driver.getWebDriver();
				 executor.executeScript("arguments[0].click();", oWebElement);
				report.updateTestLog("Clicking the " + sReportText, sReportText + " is Checked " , Status.PASS);
			}
			else
				report.updateTestLog("Clicking the " + sReportText, sReportText + " is already Checked " , Status.WARNING);
			if (ScreenShot)
				report.updateTestLog("Clicking the " + sReportText, sReportText + " is clicked " , Status.SCREENSHOT);
			return true;
		}
		catch (Exception e) 
		{
			report.updateTestLog("Clicking the " + sReportText, "Exception while clicking the "+ sReportText + " " + e.getMessage() , Status.FAIL);			
			return false;
		}		
	}
	
	/**
	 * This method is used to Select the Check box after checking if it is already selectde
	 * <br><br>
	 * @author 	   Gunasekaran Nallasamy(i365976)
	 * @param 	   oLocator  By - The Locator using which we are going to identify the Element
	 * @param 	   sReportText String - The Reporting Text which will be displayed in the HTML Report
	 * @param 	   IgnoreReporting boolean - This is the boolean value which will decide whether the Reporting is needed in HTML Report or not
	 * @param 	   ScreenShot boolean - This is the boolean value which will decide whether the screenshot is needed in HTML Report or not
	 * @return     boolean(true/false) Value - Whether the Element with the Specified locator is clicked or not
	 * @exception  Exception - this exception is marked as fail in the report
	 */
	
	public boolean UnCheckBox(WebElement oWebElement, String sReportText,boolean ScreenShot)
	{	
		try
		{	
			if( oWebElement.isSelected())
			{	 
				 JavascriptExecutor executor = (JavascriptExecutor) driver.getWebDriver();
				 executor.executeScript("arguments[0].click();", oWebElement);				
				
				report.updateTestLog("Clicking the " + sReportText, sReportText + " is Unchecked " , Status.PASS);
			}
			else
				report.updateTestLog("Clicking the " + sReportText, sReportText + " is already UnChecked" , Status.WARNING);
			if (ScreenShot)
				report.updateTestLog("Clicking the " + sReportText, sReportText + " is clicked " , Status.SCREENSHOT);
			return true;
		}
		catch (Exception e) 
		{
			report.updateTestLog("Clicking the " + sReportText, "Exception while clicking the "+ sReportText + " " + e.getMessage() , Status.FAIL);			
			return false;
		}		
	}
	
	/**
	 * This method is used to Select the Check box after checking if it is already selectde
	 * <br><br>
	 * @author 	   Gunasekaran Nallasamy(i365976)
	 * @param 	   oLocator  By - The Locator using which we are going to identify the Element
	 * @param 	   sReportText String - The Reporting Text which will be displayed in the HTML Report
	 * @param 	   IgnoreReporting boolean - This is the boolean value which will decide whether the Reporting is needed in HTML Report or not
	 * @param 	   ScreenShot boolean - This is the boolean value which will decide whether the screenshot is needed in HTML Report or not
	 * @return     boolean(true/false) Value - Whether the Element with the Specified locator is clicked or not
	 * @exception  Exception - this exception is marked as fail in the report
	 */
	
	public boolean UnCheckBox(By oLocator, String sReportText,boolean ScreenShot)
	{	
		try
		{	
			if(driver.findElement(oLocator).isSelected())
			{
				
				 WebElement element = driver.getWebDriver().findElement(oLocator);
				 JavascriptExecutor executor = (JavascriptExecutor) driver.getWebDriver();
				 executor.executeScript("arguments[0].click();", element);				
				
				report.updateTestLog("Clicking the " + sReportText, sReportText + " is Unchecked " , Status.PASS);
			}
			else
				report.updateTestLog("Clicking the " + sReportText, sReportText + " is already UnChecked" , Status.WARNING);
			if (ScreenShot)
				report.updateTestLog("Clicking the " + sReportText, sReportText + " is clicked " , Status.SCREENSHOT);
			return true;
		}
		catch (Exception e) 
		{
			report.updateTestLog("Clicking the " + sReportText, "Exception while clicking the "+ sReportText + " " + e.getMessage() , Status.FAIL);			
			return false;
		}		
	}

	/**
	 * This method is used to compare the values between Array Lists
	 * <br><br>
	 * @author 	    Guna(i365976)
	 * @param 	   aActualItems ArrayList<String> - The Array List which contains the Actual Values
	 * @param 	   aExpectedItems ArrayList<String> - The Array List which contains the Expected Item list
	 * @param 	   sReportText String - The Reporting Text which will be displayed in the HTML Report
	 * @exception  Exception - this exception is marked as fail in the report
	 */
	void ValidateArralyListEquals( ArrayList<String> aActualItems,ArrayList<String> aExpectedItems,String sReportText)
	{	
		try
		{
			
			ArrayList<String> aTempExpectedItems = new ArrayList<String>();				

			//report.updateTestLog("validating the Web List " + sReportText,"Actual : " + aActualItems.size() + " Expected : "+aExpectedItems.size(),Status.PASS);
			/*if ( (aActualItems.size()>(aExpectedItems.size())) || (aActualItems.size()<(aExpectedItems.size())))
			{*/
				report.updateTestLog("validating the Web List " + sReportText,"Expected : "+  aExpectedItems.size() + " " +Arrays.asList(aExpectedItems) + " are Expected in the " + sReportText ,Status.DONE);
				//report.updateTestLog("validating the Web List " + sReportText,"Actual : "+  aActualItems.size() + " " +Arrays.asList(aActualItems) + " are available in the " + sReportText ,Status.DONE);
			//}
			
			if (aActualItems.containsAll(aExpectedItems) && aExpectedItems.containsAll(aActualItems) )
				report.updateTestLog("validating the Web List " + sReportText,"ItemsCount : "+ aActualItems.size()+" --> " +Arrays.asList(aExpectedItems) + " are available in the " + sReportText ,Status.PASS);
			else
			{	
				for(int i = 0;i<aActualItems.size();i++ )
				{						
					if (aExpectedItems.contains(aActualItems.get(i)))
					{															
						aExpectedItems.remove(aActualItems.get(i));
						aTempExpectedItems.add(aActualItems.get(i));
					}												
				}
				
				aActualItems.removeAll(aTempExpectedItems);
				if (!(aExpectedItems.isEmpty()))
					report.updateTestLog( sReportText,aExpectedItems.size() + " "+ Arrays.asList(aExpectedItems) + " are not available in the " + sReportText ,Status.FAIL);
				
				if (!(aActualItems.isEmpty()))
					report.updateTestLog(sReportText,aActualItems.size() + " "+ Arrays.asList(aActualItems) + " are available Extra in the " + sReportText ,Status.FAIL);
			}
		}		
		catch(Exception e)
		{
			report.updateTestLog("validating the Web List " + sReportText,"Exception Occured while Validating the Drop Down List " + sReportText ,Status.FAIL);
		}
	
	}
	
	void ValidateArralyListEqualsOrder( ArrayList<String> aActualItems,ArrayList<String> aExpectedItems,String sReportText)
	{	
		try
		{	
			report.updateTestLog("validating the List " + sReportText,"Actal : "+  aActualItems.size() + " " +Arrays.asList(aActualItems) + " are available in the " + sReportText ,Status.DONE);
			report.updateTestLog("validating the List " + sReportText,"Expected : "+  aExpectedItems.size() + " " +Arrays.asList(aExpectedItems) + " are Expected in the " + sReportText ,Status.DONE);			
			if(aActualItems.equals(aExpectedItems))
				report.updateTestLog("validating the List " + sReportText,"Items Count : "+  aExpectedItems.size() + " " +Arrays.asList(aExpectedItems) + " are available in the " + sReportText ,Status.PASS);	
			else
			{	
				for(int i = 0;i<aActualItems.size();i++ )
				{						
					if (!(aExpectedItems.get(i).equals(aActualItems.get(i))))
						report.updateTestLog("validating the List " + sReportText,"Expected : "+ aExpectedItems.get(i) + " Actual : " + aActualItems.get(i) ,Status.FAIL);
				}
			}
		}		
		catch(Exception e)
		{
			report.updateTestLog("validating the Web List " + sReportText,"Exception Occured while Validating the Drop Down List " + sReportText+e,Status.FAIL);
		}
	}
	/**
	 * This method is used to compare the values between Array Lists
	 * <br><br>
	 * @author 	   Guna(i365976)
	 * @param 	   aActualItems ArrayList<String> - The Array List which contains the Actual Values
	 * @param 	   aExpectedItems ArrayList<String> - The Array List which contains the Expected Item list
	 * @param 	   sReportText String - The Reporting Text which will be displayed in the HTML Report
	 * @exception  Exception - this exception is marked as fail in the report
	 */
	public void ValidateArralyListContains( ArrayList<String> aActualItems,ArrayList<String> aExpectedItems,String sReportText)
	{	
		try
		{
			report.updateTestLog("validating the Web List " + sReportText,"Expected : "+  aExpectedItems.size() + Arrays.asList(aExpectedItems) + " are expected in the " + sReportText ,Status.DONE);
			report.updateTestLog("validating the Web List " + sReportText,"Actual : "+ aActualItems.size() + Arrays.asList(aActualItems) + " are available in the " + sReportText ,Status.DONE);
			
			if (aActualItems.containsAll(aExpectedItems))				
				report.updateTestLog("validating the Web List " + sReportText,Arrays.asList(aExpectedItems) + " are available in the " + sReportText ,Status.PASS);				
			else
			{	
				for(int i = 0;i<aExpectedItems.size();i++ )
				{
					if (aActualItems.contains(aExpectedItems.get(i)))
						report.updateTestLog("validating the Web List "+ sReportText,  aExpectedItems.get(i) + "' is available in the '" + sReportText +"'",Status.PASS);																
					else						
						report.updateTestLog("validating the Web List " + sReportText,aExpectedItems.get(i) + " is not available in the " + sReportText ,Status.FAIL);					
				}
			}			
		}		
		catch(Exception e)
		{
			report.updateTestLog("validating the Web List " + sReportText,"Exception Occured while Validating the Drop Down List " + sReportText ,Status.FAIL);
		}
	}
	
	/**
	 * This method is used to compare the values between Array Lists
	 * <br><br>
	 * @author 	   Guna(i365976)
	 * @param 	   aActualItems ArrayList<String> - The Array List which contains the Actual Values
	 * @param 	   aExpectedItems ArrayList<String> - The Array List which contains the Expected Item list
	 * @param 	   sReportText String - The Reporting Text which will be displayed in the HTML Report
	 * @exception  Exception - this exception is marked as fail in the report
	 */
	public void ValidateArralyListNotContains(ArrayList<String> aActualItems,ArrayList<String> aNotExpectedItems,String sReportText)
	{		
		try
		{		
			ArrayList<String> aTempExpectedItems = new ArrayList<String>();
			report.updateTestLog("validating the Web List " + sReportText,"Not Expected : "+  aNotExpectedItems.size() + " " +Arrays.asList(aNotExpectedItems) + " are not Expected in the " + sReportText ,Status.DONE);
			report.updateTestLog("validating the Web List " + sReportText,"Actual : "+  aActualItems.size() + " " +Arrays.asList(aActualItems) + " are available in the " + sReportText ,Status.DONE);
			
			for(int i = 0;i<aNotExpectedItems.size();i++ )
			{						
				if (aActualItems.contains(aNotExpectedItems.get(i)))						
					aTempExpectedItems.add(aNotExpectedItems.get(i));
			}
			if (!(aTempExpectedItems.isEmpty()))
				report.updateTestLog("validating the Web List " + sReportText, Arrays.asList(aTempExpectedItems) + " are available in the " + sReportText ,Status.FAIL);
			else
				report.updateTestLog("validating the Web List " + sReportText, Arrays.asList(aNotExpectedItems) + " are not availble in the " + sReportText ,Status.PASS);
		}		
		catch(Exception e)
		{
			report.updateTestLog("validating the Web List " + sReportText,"Exception Occured while Validating the Drop Down List " + sReportText ,Status.FAIL);
		}
	}
	
	public boolean isEnabled(By oLocator, String sReportText,boolean ScreenShot)
	{
		try
		{		
			if(driver.findElement(oLocator).isEnabled())
			{
				report.updateTestLog("isEnabled " + sReportText, sReportText + " is enabled in the page" ,Status.PASS);
				return true;	
			}
			else
			{
				report.updateTestLog("isEnabled " + sReportText, sReportText + " is not enabled in the page"  ,Status.PASS);
				return false;
			}
		}		
		catch(Exception e)
		{
			report.updateTestLog("isEnabled " + sReportText,"Exception Occured while Validating the" + sReportText ,Status.FAIL);
			return false;
		}
	}
//############################################################# End of Selenium Related Functions #############################################################

//############################################################# Start of String and Date Related  Functions #############################################################

	

public boolean mouseHover(By oLocator, String sReportText,boolean IgnoreReporting,boolean ScreenShot)
{
//if (bSkipExecution) return false;
try
{
WebElement element = driver.getWebDriver().findElement(oLocator);
Actions builder = new Actions(driver.getWebDriver());
builder.moveToElement(element).build().perform(); 
//if ((!IgnoreReporting) && (!ScreenShot))
//report.updateTestLog("Clicking the " + sReportText  , sReportText + " is clicked " ,Status.PASS);
//else if ((!IgnoreReporting) && (ScreenShot))
//report.updateTestLog("Clicking the " + sReportText  , sReportText + " is clicked " ,Status.SCREENSHOT);
return true;
} 
catch(Exception e)
{
//report.updateTestLog("Clicking the " + sReportText, "Exception while clicking the "+ sReportText + " " + e.getMessage() , Status.FAIL);
return false;
}
}
	
public String getToday() 
{
	Date date = new Date();
	SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/YYYY");
	String TimeStamp = sdf.format(date);
	return TimeStamp;//01/30/2018		
}
//############################################################# End of String and Date Related  Functions #############################################################

public static String convertToProducerCase(String name) {

	String str1 = name.substring(name.indexOf("-")).trim();
	String str2 = name.substring(0,name.indexOf("-")).trim().toUpperCase();
	String str3 = null;
	String[] str4 = str1.split(" ");
	String result = str2;
			
	for(int i=0; i<str4.length; i++) {
		
		StringBuilder sb = new StringBuilder();
		String word = str4[i].toLowerCase();
		char firstChar = word.charAt(0);
	
		if (!Character.isUpperCase(firstChar))
			sb.append(Character.toUpperCase(firstChar)).append(word.substring(1));
		else
			sb.append(word.substring(0));
	
		result = result+" "+sb.toString().trim();
	}
	return result;
}

}