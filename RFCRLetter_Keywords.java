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


public class RFCRLetter_Keywords extends ReusableLibrary {

	
	private String RFCRDataTable = "RFCR_Letter";
	private String generalDataTable = "General_Data";
	BlueGateway_Keywords blueGateway_Keywords = null;


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

	public RFCRLetter_Keywords(ScriptHelper scriptHelper) {
		super(scriptHelper);
		AComp = new ActionComponents(scriptHelper);
		RFCRDataTable = "RFCR_Letter";
		generalDataTable = "General_Data";
	}
	
	
	public void blueGateway_RFCR_invokeApplication() {
		System.out.println("test");
		String session = dataTable.getData(RFCRDataTable, "SessionName");
		MainFrame.getInstance().invokeApplication(session);
		report.updateTestLog("Invoke Application", "BlueChip Application Invoked", Status.PASS, "MAINFRAME");
	}
	
	public void blueGateway_RFCR_invokeCycleEnv() throws InterruptedException {
		String command = dataTable.getData(RFCRDataTable, "Command");
		MainFrame.getInstance().sendCommand(command);
		Thread.sleep(1000);
		MainFrame.getInstance().pressKey("Enter");
		report.updateTestLog("InvokeCycle", "Invoke BlueChip Environment", Status.PASS, "MAINFRAME");
	}
	
	public void blueGateway_RFCR_login() throws InterruptedException, IOException {
		String userName = dataTable.getData(generalDataTable, "UserName");
		String password = AComp.getTopSecretPassword(); //Save your password in a file named TopSecret_Password.txt in C:\Automation_Password directory
		Thread.sleep(1000);
		MainFrame.getInstance().login(userName, password);
		MainFrame.getInstance().pressKey("Enter");
		Thread.sleep(1000);
		report.updateTestLog("BlueGatewayLogin", "Login Completed", Status.PASS, "MAINFRAME");
	}
public void blueGateway_RFCR_browseMenu() throws InterruptedException {
		
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

	public void blueGateway_browseRLDataset() throws InterruptedException {
		String dCN = AComp.getTempData("DCN");
		String cycleEnvironment= dataTable.getData(RFCRDataTable, "CycleEnvironment");
		MainFrame.getInstance().pressKey("Tab");
		MainFrame.getInstance().clearAndType("'CNS.FCS." + cycleEnvironment + ".NEWMCC.RFCR.LETTER(0)'");
		blueGateway_TabX3();
		MainFrame.getInstance().clearAndType("'control.prod.copylib'");
		MainFrame.getInstance().pressKey("Tab");
		MainFrame.getInstance().clearAndType("FCCMCC01");
		blueGateway_TabX3();
		MainFrame.getInstance().clearAndType("610,20 co '" + dCN +"'");
		MainFrame.getInstance().pressKey("Tab");
		MainFrame.getInstance().clearAndType(" ");
        MainFrame.getInstance().pressKey("Tab");
        MainFrame.getInstance().pressKey("Tab");
		Thread.sleep(2000);
		MainFrame.getInstance().sendCommand("m");
		Thread.sleep(2000);
		Boolean noresultDisplayed = MainFrame.getInstance().searchText("No Records Selected".trim());
		Boolean rowDisplayed = MainFrame.getInstance().searchText("000001".trim());
	
		if (noresultDisplayed) 
		report.updateTestLog("Claim ", "is not found in the dataset", Status.FAIL, "MAINFRAME");
		else if (rowDisplayed)
		{
		report.updateTestLog("Claim", "is displayed in the dataset", Status.PASS, "MAINFRAME");	
		MainFrame.getInstance().pressKey("F3");
		}		
	else
		{
			report.updateTestLog("Claim ", "is not found in the dataset", Status.FAIL, "MAINFRAME");
			MainFrame.getInstance().pressKey("F3");
		}	
		
	}
	public void blueGateway_RFCR_logout() throws InterruptedException {
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


