package CPARTS_Finance.businesscomponents;

import com.hcsc.automation.framework.Status;

import AceDesktop.uimap.ACEDesktop_UIMap.RTBDetails;
import CPARTS_Finance.uimap.FSS_UIMap;

import common.ActionComponents;
import common.Reporting;
import common.VerifyUtility;
import supportlibraries.ReusableLibrary;
import supportlibraries.ScriptHelper;


public class FSS_Keywords extends ReusableLibrary{
	ActionComponents AComp = new ActionComponents(scriptHelper);
	public FSS_Keywords(ScriptHelper scriptHelper) {
		super(scriptHelper);
		// TODO Auto-generated constructor stub
	}
	
	public static String docNumber;

	public void launchFSSApplication() throws InterruptedException

	{
		String loginUrlKey = properties.getProperty("FSS_ENVIRONMENT");
		driver.get(properties.getProperty(loginUrlKey));
		report.updateTestLog("FSS web launch", "is successful", Status.PASS);

	}

	public void fss_Login()

	{
		String userName = dataTable.getData("General_Data", "UserName");
		AComp.safeClearAndType(FSS_UIMap.Login_UIMap.userName,userName, Reporting.NONE, 5);
		String password = AComp.getLANPassword(); //Save your password in a file named LAN_Password.txt in C:\Automation_Password directory
		AComp.safeClearAndType(FSS_UIMap.Login_UIMap.password, password, Reporting.NONE, 5);
		AComp.safeClick(FSS_UIMap.Login_UIMap.btnSubmit, Reporting.NONE, 5);
	    report.updateTestLog("FSS Web Login", "is successful", Status.PASS);
	}
	
public void searchDCN ()
	
	{
		String DCN = AComp.getTempData("DCN",7);
		AComp.putTempData("OriginalDCN", DCN);
		AComp.safeClick(FSS_UIMap.HomePage_UIMap.inquiryLnk, Reporting.NONE, 5);
		AComp.safeClearAndType(FSS_UIMap.HomePage_UIMap.dcnClaimNumber,DCN,Reporting.NONE, 5);
		AComp.safeClick(FSS_UIMap.HomePage_UIMap.btnSearch, Reporting.NONE, 5);
		docNumber=AComp.safeGetText(FSS_UIMap.Inquiry_UIMap.docNumber, Reporting.NONE, 5);
		report.updateTestLog("Search result", "is successful", Status.PASS);
		
	}
public void searchDCN1 ()

{
	String DCN = AComp.getTempData("DCN",8);
	AComp.putTempData("OriginalDCN", DCN);
	AComp.safeClick(FSS_UIMap.HomePage_UIMap.inquiryLnk, Reporting.NONE, 5);
	AComp.safeClearAndType(FSS_UIMap.HomePage_UIMap.dcnClaimNumber,DCN,Reporting.NONE, 5);
	AComp.safeClick(FSS_UIMap.HomePage_UIMap.btnSearch, Reporting.NONE, 5);
	docNumber=AComp.safeGetText(FSS_UIMap.Inquiry_UIMap.docNumber, Reporting.NONE, 5);
	report.updateTestLog("Search result", "is successful", Status.PASS);
	
}
public void searchDCN2 ()

{
	String DCN = AComp.getTempData("OriginalDCN", 2);
	System.out.println("Original DCN: " +DCN);
	AComp.safeClick(FSS_UIMap.HomePage_UIMap.inquiryLnk, Reporting.NONE, 5);
	AComp.safeClearAndType(FSS_UIMap.HomePage_UIMap.dcnClaimNumber,DCN,Reporting.NONE, 5);
	AComp.safeClick(FSS_UIMap.HomePage_UIMap.btnSearch, Reporting.NONE, 5);
	docNumber=AComp.safeGetText(FSS_UIMap.Inquiry_UIMap.docNumber, Reporting.NONE, 5);
	report.updateTestLog("Search result", "is successful", Status.PASS);
	
}

	public void claimFastTrack()
	{
		searchDCN ();
		String docNumber=AComp.safeGetText(FSS_UIMap.Inquiry_UIMap.docNumber, Reporting.REPORT, 5);
		AComp.safeClick(FSS_UIMap.HomePage_UIMap.fssHomeLnk, Reporting.REPORT, 5);
		AComp.safeClick(FSS_UIMap.HomePage_UIMap.rfcrMaintainanceLnk, Reporting.REPORT, 5);
		AComp.safeClick(FSS_UIMap.RfcrMaintainance_UIMap.fastTrakRFCRLnk, Reporting.REPORT, 5);
		AComp.ClearAndTypeValue(FSS_UIMap.RfcrFastTrack_UIMap.documentTxtbox, docNumber,  Reporting.REPORT, 5);
		AComp.ClearAndTypeValue(FSS_UIMap.RfcrFastTrack_UIMap.lineTxtbox, "001",  Reporting.REPORT, 5);
		AComp.safeClick(FSS_UIMap.RfcrFastTrack_UIMap.nextBtn, Reporting.REPORT, 5);
		AComp.safeClick(FSS_UIMap.RfcrFastTrack_UIMap.radioBtn, Reporting.REPORT, 5);
		AComp.safeClick(FSS_UIMap.RfcrFastTrack_UIMap.rfcrNextBtn, Reporting.REPORT, 5);
		AComp.safeClick(FSS_UIMap.RfcrFastTrack_UIMap.confirmFastTrackBtn, Reporting.REPORT, 5);
		AComp.safeGetText(FSS_UIMap.RfcrFastTrack_UIMap.rfcrFastTrackMsg, Reporting.REPORT, 5);
		if (driverUtil.objectExists(FSS_UIMap.RfcrFastTrack_UIMap.rfcrFastTrackMsg)) {
			VerifyUtility.VerifyResult("RFCR has been FASTTRAKed",
					driver.findElement(FSS_UIMap.RfcrFastTrack_UIMap.rfcrFastTrackMsg).getText(), report, "rfcr fast tracked msg",
					"RFCR has been FASTTRAKed","rfcr fast tracked msg",
					"rfcr has not been fast racked");
		}
		
	}
	
	public void verifyLockAmount()
	{
		searchDCN2 ();
		String slockAmount=AComp.getTempData("RecopAmt",2);
		AComp.safeClick(FSS_UIMap.Inquiry_UIMap.lineNumber, Reporting.REPORT, 5);
		AComp.safeClick(FSS_UIMap.RFCRDocumentLineSummary_UIMap.lockSummaryLink, Reporting.REPORT, 5);
		String lockAmountBefore=AComp.safeGetText(FSS_UIMap.RFCRDocumentLineSummary_UIMap.lockAmount, Reporting.REPORT, 5);
		String lockAmount=lockAmountBefore.replace("$","");
		//int i=Integer.parseInt(replaceString); 
		System.out.println("lockAmount after removing $ from string "+lockAmount);
	//System.out.println("lockAmount is"+lockAmount);
		VerifyUtility.VerifyResult(slockAmount, lockAmount, report, "lock Amount matched",
				String.format("lock Amount  matched-%s", slockAmount), "lock Amount not matched",
				String.format("lock Amount not matched-%s", slockAmount));
		

}
	public void verifyRecoupment() throws InterruptedException
	{
		searchDCN1 ();
		
		AComp.safeClick(FSS_UIMap.Inquiry_UIMap.lineNumber, Reporting.REPORT, 5);
	
		AComp.scrollIntoElementView(FSS_UIMap.RfcrSummary_UIMap.stat);
		String status = AComp.safeGetText(FSS_UIMap.RfcrSummary_UIMap.stat, Reporting.NONE, 5);
		String balance = AComp.safeGetText(FSS_UIMap.RfcrSummary_UIMap.bal, Reporting.NONE, 5);
		String actCode = AComp.safeGetText(FSS_UIMap.RfcrSummary_UIMap.actcd, Reporting.NONE, 5);
		Thread.sleep(2000);
		
		
		if ((status.equals("CLOS")) & (balance.equals("$0.00")) & (actCode.contains("MATC"))) {
			System.out.println("Recoupment is done");
			report.updateTestLog("Recoupment", "is successfully completed", Status.PASS);
		}
		
		else 
		{
			System.out.println("Recoupment is not done");
			report.updateTestLog("Recoupment", "is not completed", Status.FAIL);
		}
}
	
	

}
