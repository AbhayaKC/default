package CPARTS_Finance.businesscomponents;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.Properties;

import org.openqa.selenium.Keys;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.interactions.Actions;
import com.hcsc.automation.framework.Status;
import CPARTS_Finance.uimap.Claims_Gui_UIMap;
import CPARTS_Finance.uimap.Claims_Gui_UIMap.Claims_Search;
import CPARTS_Finance.uimap.Claims_Gui_UIMap.LogIN_Page;
import common.ActionComponents;
import common.Reporting;
import common.Sync;
import supportlibraries.ReusableLibrary;
import supportlibraries.ScriptHelper;

public class Claims_Gui_Keywords extends ReusableLibrary {

	Properties prop = new Properties();
	private String sGeneralDataSheetName = "General_Data";
	private String sTestDataSheetName = "";
	ActionComponents AComp = new ActionComponents(scriptHelper);
	Sync sync = new Sync(scriptHelper);
	Actions action;
	protected final String tempDataTestCaseId = properties.getProperty("TEMP_DATA_TEST_CASE_ID");
	public Claims_Gui_Keywords(ScriptHelper scriptHelper) {
		super(scriptHelper);
		action = new Actions(driver.getWebDriver());

	}

	public void launchApplication() throws Exception {

		driver.get(properties.getProperty("ClAIMSURLUAT3"));
		// AComp.launchApplication(properties.getProperty("ClAIMSURLUAT2"));

		report.updateTestLog("ClAIMSURLUAT3 Web launch", "is successful", Status.PASS);

	}

	public void claims_gui_login() throws InterruptedException

	{
		String userName = dataTable.getData("General_Data", "UserName");
		AComp.safeClearAndType(LogIN_Page.userId, userName, Reporting.REPORT, 30);

		String password = AComp.getLANPassword(); // Save your password in a file named LAN_Password.txt in
													// C:\Automation_Password directory
		AComp.safeClearAndType(LogIN_Page.passWord, password, Reporting.REPORT, 30);

		AComp.safeClick(LogIN_Page.loginbtn, Reporting.REPORT, 30);
		report.updateTestLog("Claims Web Login", "is successful", Status.PASS);
		Dimension dimension=new Dimension(1024,460);
		driver.manage().window().setSize(dimension);

	}
	
	
	String searchBy = dataTable.getData("Claims_GUI", "searchBy");
	public void  claims_search() throws Exception {
//		String dcn =AComp.getTempData("DCN");
		String alphaPrefix = dataTable.getData("Claims_GUI", "alphaPrefix");
	

		if (searchBy.equalsIgnoreCase("dcn")) {
			
			search_By_DCN(searchBy);
			
		} else {
			
			claims_by_criteria(alphaPrefix);
		
		}
		

	}

	public void claims_by_criteria(String alphaPrefix) throws Exception {

		Thread.sleep(3000);
		Dimension dimension=new Dimension(1024,460);
		driver.manage().window().setSize(dimension);
		Robot robot = new Robot();
		robot.keyPress(KeyEvent.VK_F11);
		Thread.sleep(3000);

		AComp.safeClick(Claims_Search.searchdrdn, Reporting.REPORT, 15);
		Thread.sleep(5000);
		System.out.println("Search Button Clicked...!!!");

		String beginDate = dataTable.getData("Claims_GUI", "srvfrom");
		String endDate = dataTable.getData("Claims_GUI", "srvtill");

		String subId = dataTable.getData("Claims_GUI","subID");
		//String alphaPrefix = dataTable.getData("Claims", "alphaPrefix");

		AComp.safeClearAndType(Claims_Search.subid, subId, Reporting.REPORT, 10);
		AComp.safeClearAndType(Claims_Search.alphaprefix, alphaPrefix, Reporting.REPORT, 10);

		Thread.sleep(3000);
		AComp.safeClearAndType(Claims_Search.servicefrm, beginDate, Reporting.REPORT, 20);
		AComp.safeClearAndType(Claims_Search.serviceTo, endDate, Reporting.REPORT, 20);
		Thread.sleep(3000);
		String dispoDateFrom = dataTable.getData("Claims_GUI", "dispositionFrom");
		String dispoDateTo = dataTable.getData("Claims_GUI", "dispositionTo");
		Thread.sleep(3000);
		AComp.safeClearAndType(Claims_Search.dispofrm, dispoDateFrom, Reporting.REPORT, 20);
		AComp.safeClearAndType(Claims_Search.dispoTo, dispoDateTo, Reporting.REPORT, 20);
		
		Thread.sleep(3000);
		
		action.moveToElement(driver.findElement(Claims_Gui_UIMap.Claims_Search.searchclaimsbtn)).build().perform();
		AComp.safeClick(Claims_Gui_UIMap.Claims_Search.searchclaimsbtn, Reporting.NONE, 40);

		Thread.sleep(5000);

	}
	
	public void search_By_DCN(String searchBy) throws InterruptedException, AWTException
	{
		Thread.sleep(3000);
		Dimension dimension=new Dimension(1024,460);
		driver.manage().window().setSize(dimension);
		Robot robot = new Robot();
		robot.keyPress(KeyEvent.VK_F11);
		Thread.sleep(3000);

		AComp.safeClearAndType(Claims_Search.searchbydrdn, AComp.getTempData("DCN"), Reporting.REPORT, 20);
		
		AComp.safeClick(Claims_Search.viewclaimByDCN, Reporting.REPORT, 15);
		Thread.sleep(3000);
		
	}
	
	
	

	public void getPaidDetails() throws InterruptedException {

		
	//	if(sync.isElementPresent(Claims_Gui_UIMap.Claims_Search.tbabydcn)) ///total billed amount
			
			if (searchBy.equalsIgnoreCase("dcn"))
		{
			
			action.moveToElement(driver.findElement(Claims_Gui_UIMap.Claims_Search.tba)).build().perform();
			String tba1 = AComp.safeGetText(Claims_Gui_UIMap.Claims_Search.tba, Reporting.NONE, 40);

			String totalBillAmount = tba1.substring(1);
			System.out.println(totalBillAmount);

		
			if (tba1.equals(dataTable.getData("Claims_GUI", "totalBilledAmount"))) {

				System.out.println("Total Bill Amount validated");
				report.updateTestLog("Total Bill Amount is validated", "successfully", Status.PASS);
				
			}
			
			else
			{
				report.updateTestLog("Total Bill Amount is not validated", "successfully", Status.FAIL);
			}

			String tpa1 = AComp.safeGetText(Claims_Gui_UIMap.Claims_Search.tpa, Reporting.NONE, 40);
			String totalPaidAmount = tpa1.substring(1);
			AComp.putTempData("AmtPaid", totalPaidAmount);
			System.out.println(totalPaidAmount);

			if (tpa1.equals("<$0.00>")) {

				System.out.println("Claim is in unpaid status");				
			
			}	
		}
		else
		{
			String dcn = AComp.safeGetText(Claims_Gui_UIMap.Claims_Search.dcnnumber, Reporting.NONE, 40); 
			dcn = dcn.substring(0, 17);	
			System.out.println(dcn);
			int iIteration = Integer.parseInt(dataTable.getData("Claims_GUI", "Iteration"));
			
			//AComp.putTempData("TC_ID", tempDataTestCaseId,iIteration);
			AComp.putTempData("DCN", dcn,6);
			//
			AComp.putTempData("Iteration", String.valueOf(iIteration),iIteration);
			AComp.putTempData("SubIteration", "1",iIteration);
			
			action.moveToElement(driver.findElement(Claims_Gui_UIMap.Claims_Search.tba)).build().perform();
			String tba1 = AComp.safeGetText(Claims_Gui_UIMap.Claims_Search.tba, Reporting.NONE, 40);

			String totalBillAmount = tba1.substring(1);
			System.out.println("totalBillAmount is"+totalBillAmount);

		if (tba1.equals(dataTable.getData("Claims_GUI", "totalBilledAmount"))) 
			report.updateTestLog("Total Bill Amount is validated", "successfully", Status.PASS);
		
		
		else
		{
			report.updateTestLog("Total Bill Amount is not validated", "successfully", Status.FAIL);
		}
			
			String tpa1 = AComp.safeGetText(Claims_Gui_UIMap.Claims_Search.tpa, Reporting.NONE, 40);
			String totalPaidAmount = tpa1.substring(1);
			AComp.putTempData("AmtPaid", totalPaidAmount);
			System.out.println("total paid amount is"+ totalPaidAmount);
			
			if (tpa1.equals("<$0.00>")) {

				System.out.println("Claim is not in paid status");
				
					}	
			
		}

	}
	public void closeGUI()
	{
		String CurrentURL=driver.getCurrentUrl();
        driver.navigate().to(CurrentURL);
        driver.manage().deleteAllCookies();
		//String URL="https://claimsgui-uat2.test.fyiblue.com/";
        //String URL="https://claimsgui-uat2.test.fyiblue.com/#home";
        //driver.navigate().to(URL);
        //driver.manage().deleteAllCookies();
		//driver.quit();
		//Actions actions = new Actions(driver);
	/*	action.keyDown(Keys.ALT);
		action.sendKeys(Keys.F4);
		action.keyUp(Keys.ALT);
		action.perform();*/
	}
	
}
