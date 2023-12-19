package CPARTS_Finance.businesscomponents;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Properties;
import com.hcsc.automation.framework.Status;
import com.hcsc.automation.framework.MainFrames.MainFrame;

import common.ActionComponents;
import supportlibraries.DriverScript;
import supportlibraries.ReusableLibrary;
import supportlibraries.ScriptHelper;


public class BlueGateway_Keywords extends ReusableLibrary {


	private String BlueGatewaySheet = null;//"BlueGateway";
	private String GeneralDataTableName = null;//"General_Data";
	int iIteration =  Integer.parseInt(dataTable.getData("BlueGateway", "Iteration"));
	
	Properties prop = new Properties();

//	String testname = dataTable.getData(TestDataSheetName, "TC_ID");
	ActionComponents AComp = null;
	
	/**
	 * Constructor to initialize the component library
	 * 
	 * @param scriptHelper
	 *            The {@link ScriptHelper} object passed from the
	 *            {@link DriverScript}
	 */

	public BlueGateway_Keywords(ScriptHelper scriptHelper) {
		super(scriptHelper);
		AComp = new ActionComponents(scriptHelper);
		BlueGatewaySheet = "BlueGateway";
		GeneralDataTableName = "General_Data";
		
		
	}

	public BlueGateway_Keywords(ScriptHelper scriptHelper,final String blueGatewaySheet,
			final String generalDataTableName)
	{
		super(scriptHelper);
		AComp = new ActionComponents(scriptHelper);
		
		
		
		this.BlueGatewaySheet = blueGatewaySheet;
		this.GeneralDataTableName = generalDataTableName;
		//this.ClaimsGui = claimsGui;
	}
	

	public void blueGateway_invokeApplication() {
		System.out.println("test");
		String session = dataTable.getData(BlueGatewaySheet, "SessionName");
		MainFrame.getInstance().invokeApplication(session);
		report.updateTestLog("Invoke Application", "BlueChip Application Invoked", Status.PASS, "MAINFRAME");
		}

	public void blueGateway_invokeCycleEnv() throws InterruptedException {
		String command = dataTable.getData(BlueGatewaySheet, "Command");
		MainFrame.getInstance().sendCommand(command);
		Thread.sleep(1000);
		MainFrame.getInstance().pressKey("Enter");
		report.updateTestLog("InvokeCycle", "Invoke BlueChip Environment", Status.PASS, "MAINFRAME");
	}
	

	public void blueGateway_login() throws InterruptedException, IOException {
		String userName = dataTable.getData(GeneralDataTableName, "UserName");
		String password = AComp.getTopSecretPassword(); //Save your password in a file named TopSecret_Password.txt in C:\Automation_Password directory
		Thread.sleep(1000);
		MainFrame.getInstance().login(userName, password);
		MainFrame.getInstance().pressKey("Enter");
		Thread.sleep(1000);
		report.updateTestLog("BlueGatewayLogin", "Login Completed", Status.PASS, "MAINFRAME");
	}

	public void blueGateway_browseMenu() throws InterruptedException {
		
		MainFrame.getInstance().sendCommand("tsok");
		Thread.sleep(1000);
		MainFrame.getInstance().pressKey("Enter");
		Thread.sleep(1000);
		MainFrame.getInstance().sendCommand("s");
		Thread.sleep(1000);
		MainFrame.getInstance().sendCommand("u");
		Thread.sleep(1000);
		MainFrame.getInstance().sendCommand("1");
		Thread.sleep(1000);
		report.updateTestLog("Browse Dataset", "Navigation Completed", Status.PASS, "MAINFRAME");
		
	}

	
	
	public void blueGateway_browseDataset() throws InterruptedException {
		String DateOfService = dataTable.getData(BlueGatewaySheet, "DateOfService");
	    String dCN = AComp.getTempData("DCN",3);
		String HexAmtPaid3=AComp.getTempData("AmtPaidX100");
		String HexAmtPaid=(HexAmtPaid3 + "C");
		MainFrame.getInstance().pressKey("Tab");
        MainFrame.getInstance().pressKey("Tab");
        MainFrame.getInstance().pressKey("Tab");
		MainFrame.getInstance().clearAndType("'control.prod.copylib");
        MainFrame.getInstance().pressKey("Tab");
		MainFrame.getInstance().clearAndType("bgtrant ");
		MainFrame.getInstance().pressKey("Tab");
		MainFrame.getInstance().clearAndType(" ");
        MainFrame.getInstance().pressKey("Tab");
        MainFrame.getInstance().pressKey("Tab");
//		MainFrame.getInstance().clearAndType("61 eq 'c' and 603 eq 'p'");
		MainFrame.getInstance().clearAndType("1152 eq '" + DateOfService + "' and 36,20 co '" + dCN +"'");
		MainFrame.getInstance().pressKey("Tab");
		/*if (HexAmtPaid.length() % 2 != 0)
		{
			String HexAmtPaid2 = "0" + HexAmtPaid;
			MainFrame.getInstance().clearAndType("1164,10 co X'"+ HexAmtPaid2 +"'    ");
		}
		else
		{
			MainFrame.getInstance().clearAndType("1164,10 co X'"+ HexAmtPaid +"'     ");
		}*/
		MainFrame.getInstance().pressKey("Tab");
		MainFrame.getInstance().pressKey("Tab");
		report.updateTestLog("Details", "are entered successfully", Status.PASS, "MAINFRAME");
		Thread.sleep(2000);
		MainFrame.getInstance().sendCommand("m");
		Thread.sleep(2000);
		blueGateway_DcnSearch();
		/*
		Boolean noresultDisplayed = MainFrame.getInstance().searchText("No Records Selected".trim());
		Boolean rowDisplayed = MainFrame.getInstance().searchText("000001".trim());
	
		if (noresultDisplayed) 
		report.updateTestLog("Claim ", "is not found in the dataset", Status.FAIL, "MAINFRAME");
		else if (rowDisplayed)
		{
		report.updateTestLog("Claim", "is displayed in the dataset", Status.PASS, "MAINFRAME");	
		MainFrame.getInstance().pressKey("f3");
		}		
	else
		{
			report.updateTestLog("Claim ", "is not found in the dataset", Status.FAIL, "MAINFRAME");
			MainFrame.getInstance().pressKey("f3");
		}*/	
		
	}
	public void blueGateway_DcnSearch() throws InterruptedException {
		int iIteration =  Integer.parseInt(dataTable.getData("BlueGateway", "Iteration"));
		String dCN = AComp.getTempData("DCN",3);
		System.out.println(iIteration);
		System.out.println(dCN);
		//int iIteration =  Integer.parseInt(dataTable.getData("BlueGateway", "Iteration"));
		//String dCN = AComp.getTempData("DCN",iIteration);
		String region = dataTable.getData(BlueGatewaySheet, "Region");
		String cycleEnvironment= dataTable.getData(BlueGatewaySheet, "CycleEnvironment");
		String DateOfDisposition = dataTable.getData(BlueGatewaySheet, "DateOfDisposition");
		
		MainFrame.getInstance().sendCommand("f "+dCN);
		Thread.sleep(10000);
		Boolean noResult = MainFrame.getInstance().searchText("NO CHARS ".trim());
		Boolean resultDisplayed = MainFrame.getInstance().searchText("CHARS".trim());
		Boolean jobFileName5B=MainFrame.getInstance().searchText("BCHIP".trim());
		Boolean jobFileName6B=MainFrame.getInstance().searchText("TMP".trim());
		
		
		if(noResult)
			report.updateTestLog("Claim ", "is not found in the dataset", Status.FAIL, "MAINFRAME");
		else if(resultDisplayed)
		{
			Thread.sleep(10000);
			MainFrame.getInstance().pressKey("f3");
			if(jobFileName5B) {
				String dataTableName=("'HCMSG.BG." + cycleEnvironment + "." + region + ".TRANSNT.BCHIP." + DateOfDisposition + "d");
			    //String dataTableName= MainFrame.getInstance().getTextByField("Dataset name ");
			    Thread.sleep(1000);
			    dataTable.putData(BlueGatewaySheet, "5BJobFileName", dataTableName);
			    //AComp.putTempData("5BTableName",dataTableName);
			    Thread.sleep(1000);
			    System.out.println(dataTableName);
			    Thread.sleep(1000);
			    report.updateTestLog("Claim", "is displayed in the dataset and 5B job validation is done", Status.PASS, "MAINFRAME");	
			    
				
			}
			//if(jobFileName6B)
			else  {
				String dataTableName=("'HCMSG.BG." + cycleEnvironment + ".BGD05A.TRANSNT.TMP." + DateOfDisposition + "d");
			    //String dataTableName= MainFrame.getInstance().getTextByField("Dataset name ");
			    Thread.sleep(1000);
			    dataTable.putData(BlueGatewaySheet, "6BJobFileName", dataTableName);
			    //AComp.putTempData("6BTableName",dataTableName);
			    Thread.sleep(1000);
			    System.out.println(dataTableName);
			    Thread.sleep(1000);
			    report.updateTestLog("Claim", "is displayed in the dataset and 6B validation is done", Status.PASS, "MAINFRAME");	
			}
		
			
		    
		}
		else
		{
			report.updateTestLog("Claim ", "is not found in the dataset", Status.FAIL, "MAINFRAME");
			MainFrame.getInstance().pressKey("f3");
		}		
	}
	
	
	
	public void blueGateway_browse5bDataset() throws InterruptedException {

		String region = dataTable.getData(BlueGatewaySheet, "Region");
		String cycleEnvironment= dataTable.getData(BlueGatewaySheet, "CycleEnvironment");
		String DateOfDisposition = dataTable.getData(BlueGatewaySheet, "DateOfDisposition");
		MainFrame.getInstance().pressKey("Tab");
		MainFrame.getInstance().clearAndType("'HCMSG.BG." + cycleEnvironment + "." + region + ".TRANSNT.BCHIP." + DateOfDisposition + "d   ");
		blueGateway_browseDataset();
		
	}

	public void blueGateway_browse6bDataset() throws InterruptedException {
	String cycleEnvironment= dataTable.getData(BlueGatewaySheet, "CycleEnvironment");
	String DateOfDisposition = dataTable.getData(BlueGatewaySheet, "DateOfDisposition");
	MainFrame.getInstance().pressKey("Tab");
	MainFrame.getInstance().clearAndType("'HCMSG.BG." + cycleEnvironment + ".BGD05A.TRANSNT.TMP." + DateOfDisposition + "d");
	blueGateway_browseDataset();

}

	public void blueGateway_DbValidation() throws InterruptedException{
		String EnvironmentNo = dataTable.getData(BlueGatewaySheet, "EnvironmentNumber");
		String userName = dataTable.getData(GeneralDataTableName, "UserName");
		String DateOfService = dataTable.getData(BlueGatewaySheet, "DateOfService");
		String AmtPaid=AComp.getTempData("AmtPaid");
		String dCN = AComp.getTempData("DCN",3);
		MainFrame.getInstance().pressKey("f3");
		MainFrame.getInstance().pressKey("f3");
		MainFrame.getInstance().sendCommand("d");
		Thread.sleep(1000);
		MainFrame.getInstance().sendCommand("3");
		Thread.sleep(1000);
		MainFrame.getInstance().sendCommand("1");
		Thread.sleep(1000);
		MainFrame.getInstance().clearAndType("claim");
		blueGateway_TabX3();
		MainFrame.getInstance().clearAndType("s");
		MainFrame.getInstance().pressKey("Tab");
		MainFrame.getInstance().clearAndType("no ");
		MainFrame.getInstance().pressKey("Tab");
		MainFrame.getInstance().clearAndType("Yes");
		MainFrame.getInstance().pressKey("Tab");
	     MainFrame.getInstance().pressKey("Tab");
	     MainFrame.getInstance().clearAndType(userName);
	     MainFrame.getInstance().pressKey("Tab");
	     MainFrame.getInstance().clearAndType("dc01");
	     MainFrame.getInstance().pressKey("Tab");
	     report.updateTestLog("Details", "are entered successfully", Status.PASS, "MAINFRAME");
	     MainFrame.getInstance().sendCommand("cbgdb01" + EnvironmentNo);
		 Thread.sleep(1000);
		 blueGateway_TabX5();
	     MainFrame.getInstance().clearAndType("like'%"+ dCN +"'");
	     
        
        blueGateway_TabX5();
        blueGateway_TabX5();
       
       // MainFrame.getInstance().clearAndType("=" + AmtPaid);
        report.updateTestLog("Details", "are entered successfully", Status.PASS, "MAINFRAME");
        MainFrame.getInstance().pressKey("F8");	
              
        blueGateway_TabX5();
        blueGateway_TabX5();
        
        //MainFrame.getInstance().sendCommand("='" + DateOfService + "'");
        MainFrame.getInstance().pressKey("Enter");
        MainFrame.getInstance().waitUntilScreenDisplayed("Optim: Browse");
 //     Thread.sleep(15000);
        Boolean dCNDisplayed = MainFrame.getInstance().searchText(dCN.trim());
        
        if (dCNDisplayed)
    	{
    	report.updateTestLog("Claim", "is displayed in the datatable", Status.PASS, "MAINFRAME");	
    	}
    	else
    	{
    		report.updateTestLog("Claim ", "is not found in the datatable", Status.FAIL, "MAINFRAME");
    	}	
        
        blueGateway_F3X3();
        
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

	public void blueGateway_TabX5() throws InterruptedException{
		
		MainFrame.getInstance().pressKey("Tab");
        MainFrame.getInstance().pressKey("Tab");
        MainFrame.getInstance().pressKey("Tab");
        MainFrame.getInstance().pressKey("Tab");
        MainFrame.getInstance().pressKey("Tab");
		
	}
	
public void blueGateway_TabX3() throws InterruptedException{
		
		MainFrame.getInstance().pressKey("Tab");
        MainFrame.getInstance().pressKey("Tab");
        MainFrame.getInstance().pressKey("Tab");
		
	}

public void blueGateway_F3X3() throws InterruptedException{
	
	MainFrame.getInstance().pressKey("f3");
    MainFrame.getInstance().pressKey("f3");
    MainFrame.getInstance().pressKey("f3");
	
}


}


