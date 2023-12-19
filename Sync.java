
package common;

/*************************************** PURPOSE **********************************

 - This class contains all synchronization methods
*/

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import supportlibraries.ReusableLibrary;
import supportlibraries.ScriptHelper;

public class Sync extends ReusableLibrary {
	
	public final int VERYSHORTWAIT =Integer.parseInt(properties.getProperty("VERYSHORTWAIT"));
	public final int SHORTWAIT = Integer.parseInt(properties.getProperty("SHORTWAIT"));
	public final int MEDIUMWAIT =Integer.parseInt( properties.getProperty("MEDIUMWAIT"));
	public final int LONGWAIT = Integer.parseInt(properties.getProperty("LONGWAIT"));
	public final int VERYLONGWAIT = Integer.parseInt(properties.getProperty("VERYLONGWAIT"));
	public final int IMPLICITWAIT = Integer.parseInt(properties.getProperty("IMPLICITWAIT"));
		
	public Sync(ScriptHelper scriptHelper) {
		super(scriptHelper);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Sets implicitWait to ZERO. This helps in making explicitWait work...
	 * 
	 * @param driver
	 *            (WebDriver) The driver object to be used
	 * @return void
	 * @throws Exception
	 */
	public void nullifyImplicitWait()
	{
		driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS); //nullify implicitlyWait()
	}

	/**
	* Set driver's implicitlyWait() time according given waitTime.
	* @param driver (WebDriver) The driver object to be used
	* @param waitTimeInSeconds (int) The time in seconds to specify implicit wait
	* @return void
	* @throws Exception
	*/
	public void setImplicitWait(int waitTimeInSeconds)
	{
		driver.manage().timeouts().implicitlyWait(waitTimeInSeconds, TimeUnit.SECONDS);
	}
	/**
	 * Waits for an element till the timeout expires
	 * @param driver (WebDriver) The driver object to be used to wait and find the element
	 * @param bylocator (By) locator object of the element to be found
	 * @param waitTime (int) The time in seconds to wait until returning a failure
	 * @return - True (Boolean) if element is located within timeout period else false
	 */
    public boolean isElementPresent(By locator, int waitTime)
	{    	
    	boolean bFlag = false;	
    	nullifyImplicitWait();
			WebDriverWait wait = new WebDriverWait(driver.getWebDriver(), waitTime);
			wait.until(ExpectedConditions.presenceOfElementLocated(locator)); 			
			if(driver.findElement(locator).isDisplayed()||driver.findElement(locator).isEnabled())
			{
				bFlag = true;
			}
		return bFlag;
	}

    
    /**
	 * Waits for an alert till the timeout expires
	 * @param waitTime (int) The time in seconds to wait until returning a failure
	 * @return - True (Boolean) if element is located within timeout period else false
	 */
    public boolean isAlertPresent(int waitTime)
	{    	
    	boolean bFlag = false;	
    	nullifyImplicitWait();
			WebDriverWait wait = new WebDriverWait(driver.getWebDriver(), waitTime);
			wait.until(ExpectedConditions.alertIsPresent());//presenceOfElementLocated(locator)); 			
			Alert alert=driver.switchTo().alert();	
				bFlag = true;
		setImplicitWait(IMPLICITWAIT);
		return bFlag;
	}
   
	/**
	 * Method -  Waits for an element till the element is clickable
	 * @param driver (WebDriver) The driver object to be used to wait and find the element
	 * @param bylocator (By) locator object of the element to be found
	 * @param waitTime (int) The time in seconds to wait until returning a failure	 
	 * @return - True (Boolean) if element is located and is clickable within timeout period else false
	 * @throws Exception
	 */
	public boolean waitUntilClickable(By locator, int... optionWaitTime)
	{    	
		int waitTime =  getWaitTime(optionWaitTime);
		boolean bFlag = false;
    	nullifyImplicitWait();
			WebDriverWait wait = new WebDriverWait(driver.getWebDriver(), waitTime);
			wait.until(ExpectedConditions.elementToBeClickable(locator));
			 
			if(driver.findElement((locator)).isDisplayed())
			{
				bFlag = true;
			}
		return bFlag;
	}
	
	/**
	 * 
	 * Method -  Waits for an element till the element is clickable
	 *
	 * @param locator (By) locator object of the element to be found
	 * @param optionWaitTime (int) The time in seconds to wait until returning a failure	
	 * @return True (Boolean) if element is located and is clickable on screen within timeout period else false
	 */
	public boolean isElementClickable(By locator, int... optionWaitTime)
	{    	
		int waitTime =  getWaitTime(optionWaitTime);
		boolean bFlag = false;
    	nullifyImplicitWait();
			WebDriverWait wait = new WebDriverWait(driver.getWebDriver(), waitTime);
			wait.until(ExpectedConditions.elementToBeClickable(locator));
			 
			if(driver.findElement((locator)).isDisplayed())
			{
				bFlag = true;
			}
		setImplicitWait(IMPLICITWAIT);
		return bFlag;
	}

	

	/**
	 * Method -  Waits for an element till the element is visible
	 * @param driver (WebDriver) The driver object to be used to wait and find the element
	 * @param bylocator (By) locator object of the element to be found
	 * @param waitTime (int) The time in seconds to wait until returning a failure	 
	 * @return - True (Boolean) if element is located and is visible on screen within timeout period else false
	 * @throws Exception
	 */
	public boolean isElementVisible(By locator, int... optionWaitTime)
	{
		int waitTime =  getWaitTime(optionWaitTime);
		boolean bFlag = false;
		nullifyImplicitWait(); 
			WebDriverWait wait = new WebDriverWait(driver.getWebDriver(), waitTime);
			wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
			setImplicitWait(IMPLICITWAIT); 
			if(driver.findElement((locator)).isDisplayed())
			{
				bFlag = true;
			}
		return bFlag;	
	}


	/**
	 * Purpose- Wait for an element till it is either invisible or not present on the DOM.
	 * @param driver (WebDriver) The driver object to be used to wait and find the element
	 * @param bylocator (By) locator object of the element to be found
	 * @param waitTime (int) The time in seconds to wait until returning a failure	 
	 * @return - True (Boolean) if the element disappears in specified waitTime.
	 * @throws Exception
	 */
	public boolean waitUntilElementDisappears(By locator,int... optionWaitTime)
    {   
		int waitTime =  getWaitTime(optionWaitTime);
		boolean isNotVisible = false;
			nullifyImplicitWait(); 
			WebDriverWait wait = new WebDriverWait(driver.getWebDriver(), waitTime);
			wait.until(ExpectedConditions.invisibilityOfElementLocated(locator)); 	
			isNotVisible = true;    
		setImplicitWait(IMPLICITWAIT); 
		return isNotVisible;		
    }
    
	/**
	 * Method - Wait until a particular text appears on the screen
	 * @param driver (WebDriver) The driver object to be used to wait and find the element
	 * @param text (String) - text until which the WebDriver should wait.
	 * @return void
	 * @throws Exception
	 */
	public void waitForPageToLoad(final String text) throws Exception
	{
		//Waiting for page to be loaded.....
		nullifyImplicitWait(); 
		(new WebDriverWait(driver.getWebDriver(), LONGWAIT)).until(new ExpectedCondition<WebElement>() {
		    public WebElement apply(WebDriver d) 
		    {
		        return d.findElement(By.partialLinkText(text));
		    }
		});
		setImplicitWait(IMPLICITWAIT); 
	}
	
	
	/**
	* Waits until page is loaded.
	*
	* @param driver - The driver object to use to perform this element search
	* @param int - timeout in seconds
	* @return True (boolean)
	* @throws Exception
	*/
	public boolean waitForPageToLoad(int timeOutInSeconds)
	{
		//Waiting for page to be loaded...
		boolean isPageLoadComplete = false;
		nullifyImplicitWait(); //nullify implicitlyWait()
			new WebDriverWait(driver.getWebDriver(), timeOutInSeconds).until(new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver driverObject) {
			return (Boolean) ((String)((JavascriptExecutor)driver.getWebDriver()).executeScript("return document.readyState")).equals("complete");
			}
			});
			isPageLoadComplete = (Boolean) ((String)((JavascriptExecutor)driver.getWebDriver()).executeScript("return document.readyState")).equals("complete");
		setImplicitWait(IMPLICITWAIT);
		return isPageLoadComplete;
	}
	
	/**
	* Waits until page is loaded. Default timeout is 250 seconds. Poll time is every 500 milliseconds.
	*
	* @param driver - The driver object to use to perform this element search
	* @return void
	* @throws Exception
	*/
	
	public void waitForPageToLoad() throws Exception 
	{
		//Waiting for page to be loaded.....
			int waitTime = 0;
			boolean isPageLoadComplete = false;
			do 
			{

				isPageLoadComplete = ((String)((JavascriptExecutor)driver.getWebDriver()).executeScript("return document.readyState")).equals("complete");
				System.out.print(".");
				Thread.sleep(500);
				waitTime++;
				if(waitTime > 500)
				{
					break;
				}
			} 
			while(!isPageLoadComplete);

			if(!isPageLoadComplete)
			{	

			}
		

	}

    public boolean isElementPresent(By locator)
    {
    	setImplicitWait(IMPLICITWAIT);
    	return driver.findElements(locator).size()>0;	
    }
    public boolean isElementEnabled(By locator)
    	{
    		//Verifying if element is enabled..
    		boolean isEnabled = false;
    		setImplicitWait(IMPLICITWAIT);
    			if(isElementPresent(locator))
    			{
    				isEnabled = driver.findElement(locator).isEnabled();
    			}
    		
    		return isEnabled;
    }
    
	public boolean isElementSelected(By locator)
	{
		//Verifying if element is selected");
		boolean isSelected = false;
		setImplicitWait(IMPLICITWAIT);
			if(isElementPresent(locator))
			{
				isSelected = driver.findElement(locator).isSelected();
			}
		return isSelected;
	}
    
    
	public int getWaitTime(int[] optionalWaitArray)
	{
		if(optionalWaitArray.length<=0)
		{
			return SHORTWAIT;
		}
		else
		{
			return optionalWaitArray[0];
		}
	}
	
	/**
	 * 
	 * This method is used to verify whether required locator is displayed on screen or not using sikuli 
	 *
	 * @param sImagePath
	 * @param sLocatorName
	 * @param waitTime, time in seconds
	 * @return returns true if required locator is displayed else returns false
	 */
	public boolean isElementDisplayed(By locator)
		{
			//Verifying if element is displayed..
			boolean isDisplayed = false;
			setImplicitWait(IMPLICITWAIT);
	
				if(isElementPresent(locator))
				{
					isDisplayed = driver.findElement(locator).isDisplayed();	
				}
			return isDisplayed;
		}
	/** Waits for the completion of Ajax jQuery processing by checking "return jQuery.active == 0" condition.
    *
    * @param driver - The driver object to be used to wait and find the element
    * @param timeOutInSeconds - The time in seconds to wait until returning a failure
    * @return True (Boolean) if jquery/ ajax is loaded within specified timeout.
    * @throws Exception
    * */
	public boolean waitForJQueryProcessing(int timeOutInSeconds)
	{
		//Waiting ajax processing to complete..
		boolean jQcondition = false;
			new WebDriverWait(driver.getWebDriver(), timeOutInSeconds).until(new ExpectedCondition<Boolean>() 
			{
				@Override
				public Boolean apply(WebDriver driverObject)
				{
					return (Boolean) ((JavascriptExecutor) driverObject).executeScript("return jQuery.active == 0");
				}
			});
			jQcondition = (Boolean) ((JavascriptExecutor) driver).executeScript("return window.jQuery != undefined && jQuery.active === 0");
//			System.out.println(jQcondition);
			return jQcondition;
		
	}
	
	
	public boolean waitUntilElementAppears(By locator,int... optionWaitTime)
    {   
		int waitTime =  getWaitTime(optionWaitTime);
		boolean isVisible = false;
			nullifyImplicitWait(); 
			WebDriverWait wait = new WebDriverWait(driver.getWebDriver(), waitTime);
			wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
					//invisibilityOfElementLocated(locator)); 	
			isVisible = true;    
		setImplicitWait(IMPLICITWAIT); 
		return isVisible;		
    }
	
}