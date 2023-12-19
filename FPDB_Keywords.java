package CPARTS_Finance.businesscomponents;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import com.hcsc.automation.framework.Status;
import com.hcsc.automation.framework.MainFrames.MainFrame;

import common.ActionComponents;
import supportlibraries.DriverScript;
import supportlibraries.ReusableLibrary;
import supportlibraries.ScriptHelper;


public class FPDB_Keywords extends ReusableLibrary {

	
	private String FPDBDataTable = "FPDB";
	private String generalDataTable = "General_Data";
	//BlueGateway_Keywords blueGateway_Keywords = null;

	Properties prop = new Properties();

//	String testname = dataTable.getData(TestDataSheetName, "TC_ID");
	ActionComponents AComp = new ActionComponents(scriptHelper);

	/**
	 * Constructor to initialize the component library
	 * 
	 * @param scriptHelper
	 *            The {@link ScriptHelper} object passed from the
	 *            {@link DriverScript}
	 */

	public FPDB_Keywords(ScriptHelper scriptHelper) {
		super(scriptHelper);
		
		
		AComp = new ActionComponents(scriptHelper);
		FPDBDataTable = "FPDB";
		generalDataTable = "General_Data";
		//blueGateway_Keywords = new BlueGateway_Keywords(scriptHelper, FPDBDataTable, generalDataTable);
	}
	
//	blueGateway_invokeApplication	blueGateway_invokeCycleEnv	blueGateway_login

	
	public void blueGateway_FPDB_invokeApplication() {
		System.out.println("test");
		String session = dataTable.getData(FPDBDataTable, "SessionName");
		MainFrame.getInstance().invokeApplication(session);
		report.updateTestLog("Invoke Application", "BlueChip Application Invoked", Status.PASS, "MAINFRAME");
	}
	
	public void blueGateway_FPDB_invokeCycleEnv() throws InterruptedException {
		String command = dataTable.getData(FPDBDataTable, "Command");
		MainFrame.getInstance().sendCommand(command);
		Thread.sleep(1000);
		MainFrame.getInstance().pressKey("Enter");
		report.updateTestLog("InvokeCycle", "Invoke BlueChip Environment", Status.PASS, "MAINFRAME");
	}
	
	public void blueGateway_FPDB_login() throws InterruptedException, IOException {
		String userName = dataTable.getData(generalDataTable, "UserName");
		String password = AComp.getTopSecretPassword(); //Save your password in a file named TopSecret_Password.txt in C:\Automation_Password directory
		Thread.sleep(1000);
		MainFrame.getInstance().login(userName, password);
		MainFrame.getInstance().pressKey("Enter");
		Thread.sleep(1000);
		report.updateTestLog("BlueGatewayLogin", "Login Completed", Status.PASS, "MAINFRAME");
	}

	public void blueGateway_browseSMenu() throws InterruptedException {
		
		MainFrame.getInstance().sendCommand("tsok");
		Thread.sleep(1000);
		MainFrame.getInstance().pressKey("Enter");
		Thread.sleep(1000);
		MainFrame.getInstance().sendCommand("s");
		Thread.sleep(1000);
		MainFrame.getInstance().sendCommand("d");
		Thread.sleep(1000);
		MainFrame.getInstance().sendCommand("1a");
		Thread.sleep(1000);
		MainFrame.getInstance().sendCommand("1");
		Thread.sleep(1000);
		report.updateTestLog("SPUFI", "Navigation Completed", Status.PASS, "MAINFRAME");
		
	}

	public void blueGateway_browseSDataset() throws InterruptedException {
		String EnvironmentNo = dataTable.getData(FPDBDataTable, "EnvironmentNumber");
		int iIteration =  Integer.parseInt(dataTable.getData("FPDB", "Iteration"));
		String AmtRecoup=AComp.getTempData("RecopAmt");
		String dCN =  AComp.getTempData("DCN",iIteration);
		if (dCN.length() > 16) 
		{
			dCN = dCN.substring(4, 17);		
		}
		MainFrame.getInstance().setTextByField("DATA SET NAME ... ===>", "'TEST.SHARE.CPARTS.SQL(SQL)'   ");
		blueGateway_TabX3();
		MainFrame.getInstance().sendCommand("TEST.SHARE.FOP11909.INS    ");
		Thread.sleep(1000);
		MainFrame.getInstance().pressKey("Enter");
		Thread.sleep(1000);
		MainFrame.getInstance().setTextByField("000100", "SET SCHEMA=CPYDB0" + EnvironmentNo + ";");
		
		String paymentType = dataTable.getData(FPDBDataTable, "TypeOfPayment");
		
		if (paymentType.equalsIgnoreCase("check")) {
		
		MainFrame.getInstance().setTextByField("000200", "SELECT A.CHK_NBR,CLM_NBR,A.CHK_ISS_DT,B.STA_CD,CORP_LIAB_AMT,GRP_NBR,");
		MainFrame.getInstance().setTextByField("000300", "SECT_NBR FROM CLAIM A,CHK B WHERE A.CHK_NBR = B.CHK_NBR AND B.STA_CD=0");
		MainFrame.getInstance().setTextByField("000400", "                                                    "); 
		MainFrame.getInstance().setTextByField("000500", "AND CLM_NBR LIKE '%" + dCN + "%'                      ");	
		
		}
		
		else if (paymentType.equalsIgnoreCase("eft")) {
		
		MainFrame.getInstance().setTextByField("000200", "SELECT CLM_NBR,CORP_LIAB_AMT,B.EFT_ISS_DT,B.STA_CD,B.EFT_RCUP_AMT,     ");
		MainFrame.getInstance().setTextByField("000300", "B.EFT_GRS_AMT,GRP_NBR,SECT_NBR,B.EFT_TRC_NBR FROM EFT_CLAIM A,EFT B    ");
		MainFrame.getInstance().setTextByField("000400", "WHERE A.EFT_TRC_NBR=B.EFT_TRC_NBR AND B.STA_CD=9");
		MainFrame.getInstance().setTextByField("000500", "AND CLM_NBR LIKE '%" + dCN + "%'                      ");	
		
		}
		
		else if (paymentType.equalsIgnoreCase("FinalEFT"))
		{
		MainFrame.getInstance().setTextByField("000200", "SELECT CLM_NBR,CORP_LIAB_AMT,B.EFT_ISS_DT,B.STA_CD,B.EFT_RCUP_AMT,     ");
		MainFrame.getInstance().setTextByField("000300", "B.EFT_GRS_AMT,GRP_NBR,SECT_NBR,B.EFT_TRC_NBR FROM EFT_CLAIM A,EFT B    ");
		MainFrame.getInstance().setTextByField("000400", "WHERE A.EFT_TRC_NBR=B.EFT_TRC_NBR AND B.STA_CD=9");	
		MainFrame.getInstance().setTextByField("000500", "AND CLM_NBR LIKE '%" + dCN + "%' AND B.EFT_RCUP_AMT=" + AmtRecoup);
		}
		
		MainFrame.getInstance().setTextByField("Command ===>", ";;;;;;;;;;;;");
		Thread.sleep(5000);
		MainFrame.getInstance().pressKey("F3");
		report.updateTestLog("Query", "is entered successfully", Status.PASS, "MAINFRAME");
		Thread.sleep(5000);
		MainFrame.getInstance().pressKey("F8");
		Thread.sleep(1000);
		
		Boolean resultDisplayed = MainFrame.getInstance().searchText("NUMBER OF ROWS DISPLAYED IS 1".trim());
		if (!resultDisplayed) 
			report.updateTestLog("Claim ", "is not found in the dataset", Status.FAIL, "MAINFRAME");
		else if (paymentType.equalsIgnoreCase("check")) 
			report.updateTestLog("Claim is displayed in the dataset", "and the check is in issued status", Status.PASS, "MAINFRAME");
		else if (paymentType.equalsIgnoreCase("eft"))
		{
			report.updateTestLog("Claim is displayed in the dataset", "and the EFT is in transmitted status", Status.PASS, "MAINFRAME");
		}    
		else	
		report.updateTestLog("Claim is displayed in the dataset with correct recoupment amount", "and the EFT is in transmitted status", Status.PASS, "MAINFRAME");
		
		MainFrame.getInstance().pressKey("f3");
		MainFrame.getInstance().pressKey("f3");
		
	}
	
	public void blueGateway_logout() throws InterruptedException {
		blueGateway_F3X3();
		MainFrame.getInstance().sendCommand("2");
		Thread.sleep(1000);
		MainFrame.getInstance().sendCommand("l");
		Thread.sleep(5000);
		Boolean endSession = MainFrame.getInstance().searchText("Session TSOK  has ended".trim());
	        
	        if (endSession)
	    	{
	    	report.updateTestLog("Session TSOK", "is ended successfully", Status.PASS, "MAINFRAME");	
	    	}
	    	else
	    	{
	    		report.updateTestLog("Session TSOK", "is not ended", Status.FAIL, "MAINFRAME");
	    	}	
	        
		MainFrame.getInstance().quitSession();
	}
	
	public void blueGateway_F3X3() throws InterruptedException{
		
		MainFrame.getInstance().pressKey("f3");
	    MainFrame.getInstance().pressKey("f3");
	    MainFrame.getInstance().pressKey("f3");
	}	
public void blueGateway_TabX3() throws InterruptedException{
		
		MainFrame.getInstance().pressKey("Tab");
        MainFrame.getInstance().pressKey("Tab");
        MainFrame.getInstance().pressKey("Tab");
		
	}

}


