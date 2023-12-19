package CPARTS_Finance.businesscomponents;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.bouncycastle.jcajce.provider.asymmetric.rsa.AlgorithmParametersSpi.OAEP;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.hcsc.automation.framework.Report;
import com.hcsc.automation.framework.Status;
import com.ibm.db2.jcc.am.re;

import common.ActionComponents;
import common.Reporting;
import common.Sync;
import CPARTS_Finance.uimap.CASHD_UIMap;
import CPARTS_Finance.uimap.CASHD_UIMap.dailyInterface;
import CPARTS_Finance.uimap.CFE_UIMap;
import supportlibraries.ReusableLibrary;
import supportlibraries.ScriptHelper;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

public class CASHD_Keywords extends ReusableLibrary {

	private String sGeneralDataSheetName = "General_Data";
	private String sTestDataSheetName = "CASHD";

	public CASHD_Keywords(ScriptHelper scriptHelper) {
		super(scriptHelper);

	}

	ActionComponents oAComp = new ActionComponents(scriptHelper);

	Sync oSync = new Sync(scriptHelper);

	public void cashd_Launch() throws Exception {
		String user = dataTable.getData(sGeneralDataSheetName, "UserName"); 
		String pwd = oAComp.getLANPassword(); //Save your password in a file named LAN_Password.txt in C:\Automation_Password directory
       
		oAComp.launchApplication(properties.getProperty("CASHDURL"));

		oAComp.safeClearAndType(CASHD_UIMap.login.userId, user, Reporting.NONE, 40);
		oAComp.safeClearAndType(CASHD_UIMap.login.password, pwd, Reporting.NONE, 40);

		oAComp.safeClick(CASHD_UIMap.login.login, Reporting.NONE, 10);
		Thread.sleep(2000);
		report.updateTestLog("Login to application", "is successfull", Status.PASS);
		

	}

	public void cashd_Home () throws InterruptedException {
		
		String drpdown=dataTable.getData(sTestDataSheetName,"homeDropdwn");
    		
		oAComp.safeClick(CASHD_UIMap.homePage.cashd, Reporting.REPORT, 10);
		oAComp.safeType(CASHD_UIMap.homePage.homedrpdwn, drpdown, Reporting.REPORT, 10);
		oAComp.safeClick(CASHD_UIMap.homePage.homeSubmit, Reporting.REPORT, 10);
		
		report.updateTestLog("Search results", "are successfull", Status.PASS);
	
	}
	public void dcn_Validation() throws Exception {
		Robot robot=new Robot();
		String dilyinterfcdwn=dataTable.getData(sTestDataSheetName,"DailyInterfacedrpdwn");
		String inrfceDrpdwn=dataTable.getData(sTestDataSheetName,"InterfaceDate");
		String timeStamp = new SimpleDateFormat("MM/dd/yyyy").format(Calendar.getInstance().getTime());
		
		
		oAComp.safeClick(CASHD_UIMap.dailyInterface.dailyintf, Reporting.REPORT, 10);
		oAComp.safeType(CASHD_UIMap.dailyInterface.interfaceDrpdwn,dilyinterfcdwn, Reporting.REPORT, 10);
		oAComp.safeType(CASHD_UIMap.dailyInterface.dateSearch,inrfceDrpdwn, Reporting.NONE, 10);
		Thread.sleep(4000);
		oAComp.safeClick(CASHD_UIMap.dailyInterface.getRprtbtn, Reporting.REPORT, 10);
		Thread.sleep(4000);
		List<WebElement> toneRows = driver.findElements(dailyInterface.interfaceTable1);
		String dcnSearch=dataTable.getData(sTestDataSheetName, "DCN");
		
		int initialRowCnt = toneRows.size();
		System.out.println("row " + initialRowCnt);
		for (int i = 1; i <= initialRowCnt; i++) {
			//oAComp.safeDblClick(By.xpath("//*[@id='table1']/tbody/tr["+i+"]"),Reporting.REPORT,30);
			try {
				oAComp.safeDblClick(By.xpath("//*[@id='table1']/tbody/tr["+i+"]"),Reporting.REPORT,30);
				System.out.println("dble click is working");
				if(oSync.isElementPresent(CASHD_UIMap.dailyInterface.ttwoSearch)) 
			    {
					oAComp.safeClearAndType(CASHD_UIMap.dailyInterface.ttwoSearch,dcnSearch,Reporting.REPORT, 10);
					
					 List<WebElement> ttwoRows = driver.findElements(dailyInterface.interfaceTable2);
					 if(ttwoRows.size()>=1)
					 {
						 String frstrow=oAComp.safeGetText(CASHD_UIMap.dailyInterface.frstrow,Reporting.REPORT,10);
						          if(frstrow.contains("No matching records found"))
						              {
							           System.out.println("No such dcn found in Table1 "+i+ "row interface");
						              }
						          else
						              {
						        	  String findDCN=oAComp.safeGetText(CASHD_UIMap.dailyInterface.findDCN,Reporting.REPORT,10);
						        	  Thread.sleep(4000);
						        	  System.out.println(findDCN);
						        	  System.out.println("required dcn is found:"+findDCN+"in row"+i+" interface");
						        	  
						        	  break;
						              }
					 }		
				}
			 else
			 {
				 System.out.println("dcn not found");
			 }
			}
				catch(Exception e)
			{
					System.out.println("alert exception");
			        robot.keyPress(KeyEvent.VK_ENTER);
				    robot.keyRelease(KeyEvent.VK_ENTER);  					
	         }
		}
}
	
}