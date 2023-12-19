package CPARTS_Finance.businesscomponents;

import java.io.IOException;
import java.text.DecimalFormat;

import com.hcsc.automation.framework.Status;
import com.hcsc.automation.framework.MainFrames.MainFrame;

import CPARTS_Finance.uimap.MPUI_UIMap;
import common.ActionComponents;
import common.Reporting;
import supportlibraries.ReusableLibrary;
import supportlibraries.ScriptHelper;

/***************************************************************************************
 * PREREQUISITES TO EXECUTE MAINFRAME KEYWORDS
 *
 *
 * C:/Windows/SysWOW64/jacob-1.18-x86.dll
 * 
 * copy the file from Project
 * Folder\itqa-framework\SAFT\drivers\jacob-1.18-x86.dll
 * 
 * jdk version should be 1.8 or 1.7
 * 
 * 
 * 
 ****************************************************************************************/

public class ClaimsAdjustment_Keywords extends ReusableLibrary {

	ActionComponents AComp = new ActionComponents(scriptHelper);
	private String claimsAdjDataTable = "ClaimAdjustment";
	private String generalDataTable = "General_Data";
	BlueChip_Keywords blueChip_Keywords = null;

	public ClaimsAdjustment_Keywords(ScriptHelper scriptHelper) {
		super(scriptHelper);
		blueChip_Keywords = new BlueChip_Keywords(scriptHelper, claimsAdjDataTable, generalDataTable);
	}

	public void blueChip_claimAdj_invokeApplication() {
		blueChip_Keywords.blueChip_invokeApplication();
	}

	public void blueChip_claimAdj_invokeCycleEnv() throws InterruptedException {
		blueChip_Keywords.blueChip_invokeCycleEnv();
	}

	public void blueChip_claimAdj_login() throws InterruptedException, IOException {
		blueChip_Keywords.blueChip_login();
	}

	public void blueChip_claimAdj_invokeRegion() throws InterruptedException {
		blueChip_Keywords.blueChip_invokeRegion();
	}

	public void blueChip_claimAdj_setCycleEnvironment() throws InterruptedException {

		blueChip_Keywords.blueChip_setCycleEnvironment();
	}

	public void blueChip_claimAdj_getUP() throws InterruptedException {
		blueChip_Keywords.blueChip_getUP();
	}
	
	/**
	 * author:u440513 kavitha jaghni
	 * 
	 */
	public void claimAdjustment() throws InterruptedException {

		MainFrame.getInstance().setTextByField("SELECT ENTRY PATH :", "04");
		MainFrame.getInstance().pressKey("Enter");
		driverUtil.waitFor(2000);
		Thread.sleep(2000);

		String DCN = AComp.getTempData("DCN",5);
		if (DCN.length() > 16) {
			DCN = DCN.substring(4, 17);
		}

		MainFrame.getInstance().setTextByField("PLEASE ENTER CLAIM NUMBER:", DCN);
		MainFrame.getInstance().pressKey("Tab");
		Thread.sleep(4000);
		MainFrame.getInstance().setTextByField("PLEASE ENTER CLAIM OPTION:", "4a");
		report.updateTestLog("Dcn Number Entered", "ClaimAdjustment", Status.PASS, "MAINFRAME");

		Thread.sleep(2000);
		MainFrame.getInstance().pressKey("Enter");

		Thread.sleep(2000);
	}

	public void claimGeepAdjrecDate() throws InterruptedException {

		String ADJ_RECD_DT = dataTable.getData(claimsAdjDataTable, "ADJ RECD DT");
		MainFrame.getInstance().setTextByField("OP ADJ RSN CD:", "ANR");
		MainFrame.getInstance().setTextByField("ENTER ADJUSTMENT CODE ==>", "M");
		MainFrame.getInstance().pressKey("Tab");
		MainFrame.getInstance().pressKey("Tab");
		MainFrame.getInstance().pressKey("Tab");
		MainFrame.getInstance().setTextByField("IS FSS INVOLVED IN THIS ADJ ==>", "N");
		MainFrame.getInstance().setTextByField("ADJ RECD DT:", ADJ_RECD_DT);
		report.updateTestLog("GEEPSCREEN Adjustment RecvDate", "ADJ RECD DATE ANR ADJ code", Status.PASS, "MAINFRAME");

		MainFrame.getInstance().pressKey("Enter");
		Thread.sleep(2000);
		Boolean errorMessage = MainFrame.getInstance().searchText("ERROR MESSAGES".trim());
		if (errorMessage) {
			Thread.sleep(1000);
			MainFrame.getInstance().pressKey("F10");
			Thread.sleep(1000);

		}
	}

	public void claimCasemenu() throws InterruptedException {

		MainFrame.getInstance().pressKey("F8");
		MainFrame.getInstance().pressKey("Enter");
		MainFrame.getInstance().pressKey("Enter");
		Thread.sleep(2000);
		Boolean gcd3 = MainFrame.getInstance().searchText("GCD3".trim());
		if (gcd3) {
			Thread.sleep(1000);
			MainFrame.getInstance().pressKey("enter");
			Thread.sleep(1000);

		}
		MainFrame.getInstance().setTextByField("CASMENU:", "y");
		report.updateTestLog("Casemenu", "Case menu entered Screen", Status.PASS, "MAINFRAME");

		MainFrame.getInstance().pressKey("F1");
		Thread.sleep(2000);
	}

	public void claimGZAandGCLR() throws InterruptedException {

		MainFrame.getInstance().setTextByField("SELECT:", "17");
		MainFrame.getInstance().setTextByField("MAN CALC RSN CD:", "BENC");
		MainFrame.getInstance().pressKey("Enter");
		Thread.sleep(2000);
/*		MainFrame.getInstance().pressKey("Tab");
		MainFrame.getInstance().pressKey("Tab"); */
		MainFrame.getInstance().sendCommand("x");
		MainFrame.getInstance().pressKey("Enter");
		Thread.sleep(2000);

		String strRFCRAmount = dataTable.getData(claimsAdjDataTable, "RFCRAmount");
		AComp.putTempData("RecopAmt", strRFCRAmount);
		float RFCRAmount = Float.parseFloat(strRFCRAmount);

		String strELIGAMTBefore = MainFrame.getInstance().getTextByField("ELIGAMT");
		float ELIGAMTBefore = Float.parseFloat(strELIGAMTBefore);
		float ELIGAMTAfter = ELIGAMTBefore - RFCRAmount;// 50 Dollars we are deducting
		String strELIGAMTAfter = AComp.decimal(ELIGAMTAfter);

		MainFrame.getInstance().setTextByField("ELIGAMT", strELIGAMTAfter);

		System.out.println(strELIGAMTAfter);
		report.updateTestLog("claimGZAandGCLR", "Eligible amount enterd", Status.PASS, "MAINFRAME");

		String strINELAMTBefore = MainFrame.getInstance().getTextByField("INELAMT1").trim();

		// System.out.println(strINELAMTBefore);
		// System.out.println(strINELAMTBefore.concat("HI"));
		if (strINELAMTBefore.length() == 0) {
			// if INELAMT1, INELAMT, INEL RSN is empty we are entering from code
			String strInELIGAMTAfter = AComp.decimal(RFCRAmount);
			MainFrame.getInstance().setTextByField("INELAMT1", strInELIGAMTAfter);
			MainFrame.getInstance().setTextByField("INELAMT", strInELIGAMTAfter);
			MainFrame.getInstance().setTextByField("INEL RSN", "T42");
			report.updateTestLog("claimGZAandGCLR", "InEligible amount enterd", Status.PASS, "MAINFRAME");

		}

		else {
			float INELAMTBefore = Float.parseFloat(strINELAMTBefore);
			float INELAMTAfter = INELAMTBefore + RFCRAmount;
			String strInELIGAMTAfter = AComp.decimal(INELAMTAfter);

			System.out.println("ineligible amount after" + INELAMTAfter);
			MainFrame.getInstance().setTextByField("INELAMT1", strInELIGAMTAfter);
			MainFrame.getInstance().setTextByField("INELAMT", strInELIGAMTAfter);
			report.updateTestLog("claimGZAandGCLR", "In Eligible amount enterd", Status.PASS, "MAINFRAME");
		}
		Thread.sleep(3000);
		MainFrame.getInstance().pressKey("Enter");
		MainFrame.getInstance().pressKey("Enter");
		Thread.sleep(3000);
		MainFrame.getInstance().setTextByField("SELECT:", "99");

		report.updateTestLog("claimGZAandGCLR", "GZA screen displayed", Status.PASS, "MAINFRAME");
		MainFrame.getInstance().pressKey("Enter");
		Thread.sleep(3000);
		MainFrame.getInstance().pressKey("Enter");
		Thread.sleep(3000);
		MainFrame.getInstance().pressKey("Enter");
		Thread.sleep(3000);
		MainFrame.getInstance().pressKey("Enter");
		Thread.sleep(3000);
		MainFrame.getInstance().pressKey("Enter");
		Thread.sleep(3000);
		MainFrame.getInstance().pressKey("Enter");
		Thread.sleep(3000);

		Boolean GCLRscreen = MainFrame.getInstance().searchText("NO MORE SERVICES".trim());
		if (GCLRscreen) {
			MainFrame.getInstance().pressKey("f1");
			Thread.sleep(4000);
			report.updateTestLog("GCLR screen", "is displayed 2 times", Status.PASS, "MAINFRAME");
			dataTable.putData(claimsAdjDataTable, "GCLR * 2", "YES");
		}
		Boolean ScreenGZA5 = MainFrame.getInstance().searchText("GZA5".trim());
		if (ScreenGZA5) {
			
			MainFrame.getInstance().setTextByfieldAndOccurance("LEVEL", "C", 1);
			report.updateTestLog("ScreenGZA5 ", "ScreenGZA5 displayed", Status.PASS, "MAINFRAME");
			Thread.sleep(1000);
			MainFrame.getInstance().pressKey("Enter");
			Thread.sleep(9000);
		//	MainFrame.getInstance().sendCommand("y");
			Thread.sleep(3000);
		
		}
		
	}

	public void claimRFCR() throws InterruptedException {
		Thread.sleep(2000);
		String RFCRCode = dataTable.getData(claimsAdjDataTable, "RFCRCode");
		MainFrame.getInstance().setTextByField("RFCR CD:", RFCRCode);
		MainFrame.getInstance().setTextByField("DOC COMP:", "y");
		report.updateTestLog("claimRFCRcode", "RFCR code and doc comp entered", Status.PASS, "MAINFRAME");
		MainFrame.getInstance().pressKey("Enter");
		Thread.sleep(2000);
	}

	public void claimStatus() throws InterruptedException {

		MainFrame.getInstance().setTextByField("TEXT ( WHY ):", "OverpaidClaim");
		report.updateTestLog("claimStatus", "text entered", Status.PASS, "MAINFRAME");

		MainFrame.getInstance().pressKey("Enter");
		Thread.sleep(2000);
		MainFrame.getInstance().setTextByField("CROSS/SHIELD IND:", "1");

		MainFrame.getInstance().pressKey("Enter");
		Thread.sleep(2000);

		MainFrame.getInstance().pressKey("f3");
		Thread.sleep(2000);
		// MainFrame.getInstance().pressKey("tab");
		MainFrame.getInstance().sendCommand("G");
		MainFrame.getInstance().pressKey("Enter");
	}

	public void claimAdj_logout() throws InterruptedException {
		Thread.sleep(500);
		MainFrame.getInstance().pressKey("CLEARSCREEN");
		MainFrame.getInstance().sendCommand("logoff");
		Thread.sleep(2000);
		MainFrame.getInstance().pressKey("Enter");
		Thread.sleep(2000);
		report.updateTestLog("LoGoff", "Logout of the current user session", Status.PASS, "MAINFRAME");
		MainFrame.getInstance().quitSession();
	}
	
}
