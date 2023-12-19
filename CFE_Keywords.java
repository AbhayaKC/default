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
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.hcsc.automation.framework.Report;
import com.hcsc.automation.framework.Status;
import com.ibm.db2.jcc.am.re;

import common.ActionComponents;
import common.Reporting;
import common.Sync;
import CPARTS_Finance.uimap.CFE_UIMap;
import supportlibraries.ReusableLibrary;
import supportlibraries.ScriptHelper;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

public class CFE_Keywords extends ReusableLibrary {

	private String sGeneralDataSheetName = "General_Data";
	private String sTestDataSheetName = "CFE";

	public CFE_Keywords(ScriptHelper scriptHelper) {
		super(scriptHelper);

	}

	ActionComponents oAComp = new ActionComponents(scriptHelper);

	Sync oSync = new Sync(scriptHelper);

	public void appLaunchURL() throws Exception {
		String user = dataTable.getData(sGeneralDataSheetName, "UserName"); 
		String pwd = oAComp.getLANPassword(); //Save your password in a file named LAN_Password.txt in C:\Automation_Password directory
       
		oAComp.launchApplication(properties.getProperty("CFEURL"));

		oAComp.safeClearAndType(CFE_UIMap.login.userId, user, Reporting.NONE, 40);
		oAComp.safeClearAndType(CFE_UIMap.login.password, pwd, Reporting.NONE, 40);

		oAComp.safeClick(CFE_UIMap.login.login, Reporting.NONE, 10);
		Thread.sleep(2000);
		report.updateTestLog("Login to application", "is successfull", Status.PASS);
		

	}

	public void enterSearchFields() throws InterruptedException {
		
	/*	String firstName=oAComp.getTempData("firstName");
        String lastName=oAComp.getTempData("lastName");  */
        
    	String firstName=dataTable.getData(sTestDataSheetName,"firstName");
    	String lastName=dataTable.getData(sTestDataSheetName,"lastName"); 
        
        String timeStamp = new SimpleDateFormat("MM/dd/yyyy").format(Calendar.getInstance().getTime());

		oAComp.safeClearAndType(CFE_UIMap.homePage.firstName, firstName, Reporting.NONE, 10);
		oAComp.safeClearAndType(CFE_UIMap.homePage.lastName, lastName, Reporting.NONE, 10);

		oAComp.safeClearAndType(CFE_UIMap.homePage.dropBiginDate, timeStamp, Reporting.NONE, 20);
		oAComp.safeClearAndType(CFE_UIMap.homePage.dropEndDate,timeStamp, Reporting.NONE, 20);

		oAComp.safeClick(CFE_UIMap.homePage.searchBtn, Reporting.NONE, 50);
		report.updateTestLog("Search results", "are successfull", Status.PASS);
	
	}

	public void validateSearchField() throws Exception {
        Thread.sleep(3000);
        String expectedDateOfService = dataTable.getData(sTestDataSheetName, "DateOfService");
        System.out.println(expectedDateOfService);
        String expectedPriceAmount = "$"+ dataTable.getData(sTestDataSheetName, "claimAmt")+".00";    
        System.out.println(expectedPriceAmount);

        List rows = driver.findElements(By.xpath("//table[@id='results_table']//table[@id='medicalSearch']/tbody/tr"));

        int initialRowCnt = rows.size();
     

        for (int i = 1; i <= initialRowCnt; i++) {
            String dateOfService= driver.findElement(By.xpath("//table[@id='results_table']//table[@id='medicalSearch']/tbody/tr["+i+"]/td[7]")).getText();
            String paidAmount1=driver.findElement(By.xpath("//table[@id='results_table']//table[@id='medicalSearch']/tbody/tr["+i+"]/td[9]")).getText();
            System.out.println("expected data"+dateOfService);
            System.out.println("Paid data" +paidAmount1);

            if(dateOfService.equalsIgnoreCase(expectedDateOfService) && expectedPriceAmount.equals(paidAmount1)){
                System.out.println(driver.findElement(By.xpath("//table[@id='results_table']//table[@id='medicalSearch']/tbody/tr["+i+"]/td[3]")).getText());
                String dcn2=driver.findElement(By.xpath("//table[@id='results_table']//table[@id='medicalSearch']/tbody/tr["+i+"]/td[3]")).getText();
                oAComp.putTempData("DCN",dcn2 );
                report.updateTestLog("Expected Date of service and Actual date of service as well as Expected amount and Actual amount","are equal", Status.PASS);         

                Thread.sleep(100);
            }
   
        }
	}


    public void getPaidAmt() throws Exception{
        String dcnNumber=oAComp.getTempData("DCN",2);
         List<WebElement> dcn_rows = new ArrayList<WebElement>(driver.findElements(CFE_UIMap.homePage.dcnNumber));
     

         for (int i = 0; i < dcn_rows.size(); i++) {
                String dcnValue = dcn_rows.get(i).getText().trim();
                if(dcnValue.equals(dcnNumber))
                {
                
                dcn_rows.get(i).click();
                }

    }
        oAComp.switchToWindow(1, Reporting.REPORTWITHSCREENSHOT);
        oAComp.safeClick(CFE_UIMap.homePage.postTab,Reporting.REPORT,10);
        Thread.sleep(10000);
        String paidAmt1 = oAComp.safeGetText(CFE_UIMap.homePage.claimAmount, Reporting.REPORT, 10);
            oAComp.putTempData("AmtPaid",paidAmt1);
/*     try
     {
        String checkNum = oAComp.safeGetText(CFE_UIMap.homePage.checkNumber, Reporting.REPORT, 10);
    
        if(checkNum.length()!=0) {
                
        if(checkNum.contains("E")) {
        oAComp.putTempData("TypeOfPayment","EFT");
        System.out.println("Payment type is EFT");
        }
           
        else
        {
            oAComp.putTempData("TypeOfPayment","CHECK");
            System.out.println("Payment type is Check");
        }
                
}
        else
        {
            oAComp.putTempData("TypeOfPayment","UNKNOWN");
            System.out.println("Payment type is UNKNOWN");
        }    
     }
     
     catch (Exception e) {
			e.printStackTrace();
			 oAComp.putTempData("TypeOfPayment","Unknown");
	         System.out.println("Payment type is Unknown");
			
		} */
       
    }
    
    }