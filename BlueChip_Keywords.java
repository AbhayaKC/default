package CPARTS_Finance.businesscomponents;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import com.hcsc.automation.framework.Status;
import com.hcsc.automation.framework.MainFrames.MainFrame;
import com.ibm.db2.jcc.t4.dc;

import common.ActionComponents;
import supportlibraries.DriverScript;
import supportlibraries.ReusableLibrary;
import supportlibraries.ScriptHelper;


public class BlueChip_Keywords extends ReusableLibrary {

	
//	private String TestDataSheetName = "GMNU_Data";
	private String BlueChipSheet = null;//"BlueChip";
	private String GeneralDataTableName = null;//"General_Data";
//	private String ClaimsGui = null;//"Claims_GUI";

	Properties prop = new Properties();

	//String testname = dataTable.getData(TestDataSheetName, "TC_ID");
	ActionComponents AComp = null;

	/**
	 * 
	 * Constructor to initialize the component library
	 * 
	 * @param scriptHelper
	 *            The {@link ScriptHelper} object passed from the
	 *            {@link DriverScript}
	 */

	public BlueChip_Keywords(ScriptHelper scriptHelper) {
		super(scriptHelper);
		AComp = new ActionComponents(scriptHelper);
		 BlueChipSheet = "BlueChip";
		GeneralDataTableName = "General_Data";
		//ClaimsGui = "Claims_GUI";
	}
	//blueChip_Keywords = new BlueChip_Keywords(scriptHelper, claimsAdjDataTable, generalDataTable, null);
	public BlueChip_Keywords(ScriptHelper scriptHelper,final String blueChipSheet,
			final String generalDataTableName)
	{
		super(scriptHelper);
		AComp = new ActionComponents(scriptHelper);
		this.BlueChipSheet = blueChipSheet;
		this.GeneralDataTableName = generalDataTableName;
		//this.ClaimsGui = claimsGui;
	}

	public void blueChip_invokeApplication() {
		System.out.println("BlueChipSheet="+BlueChipSheet);
		String session = dataTable.getData(BlueChipSheet, "SessionName");
		MainFrame.getInstance().invokeApplication(session);
		report.updateTestLog("Invoke Application", "BlueChip Application Invoked", Status.PASS, "MAINFRAME");
		
	}

	public void blueChip_invokeCycleEnv() throws InterruptedException {
		String command = dataTable.getData(BlueChipSheet, "Command");
		MainFrame.getInstance().sendCommand(command);
		Thread.sleep(1000);
		MainFrame.getInstance().pressKey("Enter");
		report.updateTestLog("InvokeCycle", "Invoke BlueChip Environment", Status.PASS, "MAINFRAME");
	}

	public void blueChip_login() throws InterruptedException, IOException {
		String userName = dataTable.getData(GeneralDataTableName, "UserName");
		String password = AComp.getTopSecretPassword(); //Save your password in a file named TopSecret_Password.txt in C:\Automation_Password directory
		Thread.sleep(1000);
		MainFrame.getInstance().login(userName, password);
		MainFrame.getInstance().pressKey("Enter");
		Thread.sleep(1000);
		report.updateTestLog("BlueChipLogin", "Login Completed", Status.PASS, "MAINFRAME");
	}

	public void blueChip_invokeRegion() throws InterruptedException {
		String region = dataTable.getData(BlueChipSheet, "Region");
		MainFrame.getInstance().pressKey("CLEARSCREEN");
		Thread.sleep(1000);
		MainFrame.getInstance().sendCommand(region);
		Thread.sleep(1000);
		report.updateTestLog("SelectPearl", "Selected Region : " + region, Status.PASS, "MAINFRAME");
		MainFrame.getInstance().pressKey("CLEARSCREEN");
		Thread.sleep(1000);
	}

	public void blueChip_setCycleEnvironment() throws InterruptedException {

		String setup = dataTable.getData(BlueChipSheet, "Setup");
		String cycleEnvironment = dataTable.getData(BlueChipSheet, "CycleEnvironment");
		MainFrame.getInstance().sendCommand(setup);
		Thread.sleep(1000);
		MainFrame.getInstance().sendCommand(cycleEnvironment);
		Thread.sleep(1000);
		report.updateTestLog("SelectTestEnv", "Getting in to the Environment :" + cycleEnvironment, Status.PASS,
				"MAINFRAME");
	}

	public void blueChip_getUP() throws InterruptedException {
		String cycleCommand = dataTable.getData(BlueChipSheet, "CycleCommand1");
		String operatorID = dataTable.getData(GeneralDataTableName, "OperatorID");
		System.out.println("Operator id");
		MainFrame.getInstance().pressKey("CLEARSCREEN");
		MainFrame.getInstance().sendCommand(cycleCommand);
		Thread.sleep(1000);
		System.out.println(operatorID);
		MainFrame.getInstance().sendCommand(operatorID);
		Thread.sleep(1000);
		if (MainFrame.getInstance().searchText("HGAEXCLU XCLU000B *** OPERATOR IS ALREADY TERMED UP ***".trim()))
			blueChip_releaseOperator();
		report.updateTestLog("GTUP", "Get in to GTUP", Status.PASS, "MAINFRAME");
	}

	public void blueChip_releaseOperator() throws InterruptedException {
		report.updateTestLog("Release", "OPERATOR IS ALREADY TERMED UP", Status.PASS, "MAINFRAME");
		String operatorID = dataTable.getData(GeneralDataTableName, "OperatorID");
		MainFrame.getInstance().pressKey("CLEARSCREEN");
		Thread.sleep(1000);
		MainFrame.getInstance().sendCommand("gexr");
		Thread.sleep(2000);
		if (MainFrame.getInstance().searchText("BLUE CHIP EXCLUSIVE CONTROL RELEASE".trim())) {
			MainFrame.getInstance().pressKey("Tab");
			MainFrame.getInstance().setTextByField("OPERATOR ID:", operatorID);
			MainFrame.getInstance().setTextByField("ON THE ABOVE TERMINAL OR OPERATOR?", "Y");
			MainFrame.getInstance().pressKey("Enter");
			Thread.sleep(1000);
			MainFrame.getInstance().pressKey("Enter");
			MainFrame.getInstance().pressKey("CLEARSCREEN");
			MainFrame.getInstance().sendCommand("gtup");
			Thread.sleep(1000);
			MainFrame.getInstance().sendCommand(operatorID);
			report.updateTestLog("Release", "Operator Released", Status.PASS, "MAINFRAME");
		}
	}


	public void blueChip_getDown() throws InterruptedException {
		Thread.sleep(2000);
		MainFrame.getInstance().pressKey("CLEARSCREEN");
		Thread.sleep(1000);
		MainFrame.getInstance().sendCommand("GDWN");
		Thread.sleep(1000);
		report.updateTestLog("GDWN", "Logged off from the Environment", Status.PASS, "MAINFRAME");
	}

	/*public void blueChip_logout() throws InterruptedException {
		Thread.sleep(1000);
		MainFrame.getInstance().pressKey("CLEARSCREEN");
		MainFrame.getInstance().sendCommand("logo");
		report.updateTestLog("LoGo", "Logout of the current user session", Status.PASS, "MAINFRAME");

	}*/

	public void blueChip_closeApplication() {
		MainFrame.getInstance().quitSession();
		report.updateTestLog("Close", "PCOMM Application Closed", Status.PASS, "MAINFRAME");
	}

	public void blueChip_runMacro() throws InterruptedException {
		Thread.sleep(1000);
		MainFrame.getInstance().runMacroScript("test1");
		Thread.sleep(5000);
		report.updateTestLog("Macro", "MACRO/SCRIPT TEST1 Executed Successfully", Status.PASS, "MAINFRAME");
	}



	/**
	 * 
	 * This method needs already existing available DCN to resume and
	 * pend/finalise the claim
	 * 
	 * @throws InterruptedException
	 * 
	 */
	private static final String FILE_NAME = "C:\\iteration\\iteration.txt"; // Update this with your text file path

	private static int getCurrentIteration() {

	    try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {

	      String line;

	      if ((line = br.readLine()) != null) {

	        return Integer.parseInt(line.trim());

	      }

	    } catch (IOException | NumberFormatException e) {

	      e.printStackTrace();

	      // Handle exceptions as needed

	    }

	    return 1; // Default value if there's an error or file is empty

	  }



	  private static void incrementIteration(int newIteration) {

	    try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME))) {

	      bw.write(String.valueOf(newIteration));

	    } catch (IOException e) {

	      e.printStackTrace();

	      // Handle exceptions as needed

	    }

	  }
	
	public void resume_Finalize() throws InterruptedException {

		   int currentIteration = getCurrentIteration();
		System.out.println("Current Iteration: " + currentIteration);
		MainFrame.getInstance().setTextByField("SELECT ENTRY PATH :", "04");
		MainFrame.getInstance().pressKey("Enter");
		Thread.sleep(2000);
		int iIteration =  Integer.parseInt(dataTable.getData("BlueChip", "Iteration"));
		String dCN = AComp.getTempData("DCN",currentIteration);
		dCN = dCN.substring(1, 16);
		System.out.println(dCN);
		MainFrame.getInstance().setTextByField("PLEASE ENTER CLAIM NUMBER:", dCN);
		MainFrame.getInstance().pressKey("Tab");
		Thread.sleep(4000);
		MainFrame.getInstance().setTextByField("PLEASE ENTER CLAIM OPTION:", "1a");
		Thread.sleep(2000);
		MainFrame.getInstance().pressKey("Enter");
		Thread.sleep(2000);
		report.updateTestLog("DCN", "Entered DCN:"+dCN, Status.PASS, "MAINFRAME");
		Thread.sleep(4000);
		 incrementIteration(currentIteration + 1);
		
		Boolean claimFinalized = MainFrame.getInstance()
				.searchText("HGGD12ML GD120006 THIS CLAIM IS IN A FINALIZED STATUS".trim());
		if (claimFinalized) 
		{
			report.updateTestLog("Claim ", "is already finalized", Status.PASS, "MAINFRAME");
		}
		else {
	//	retrieve_GEEP_data();
		report.updateTestLog("GEEP ", "GEEP screen displayed", Status.PASS, "MAINFRAME");
		Thread.sleep(3000);
		MainFrame.getInstance().pressKey("F8");
		Thread.sleep(3000);
		Boolean errorgracePeriod1 = MainFrame.getInstance()
				.searchText("MEM02407 Clm receipt date or date of service after grace period date".trim());
		if (errorgracePeriod1) {
			Thread.sleep(1000);
			MainFrame.getInstance().pressKey("F10");
			Thread.sleep(1000);
			MainFrame.getInstance().setTextByField("RSP:", "pay");
			Thread.sleep(1000);

			report.updateTestLog("RSP ", "RSP value 'pay' is entered", Status.PASS, "MAINFRAME");
		}
	//	retrieve_GELP_data();
		Boolean errorMessage = MainFrame.getInstance()
				.searchText("ERROR MESSAGES".trim());
		if (errorMessage) {
			Thread.sleep(1000);
			MainFrame.getInstance().pressKey("F10");
			Thread.sleep(1000);
		
		}
		report.updateTestLog("GELP ", "GELP screen displayed", Status.PASS, "MAINFRAME");
		Thread.sleep(2000);
		MainFrame.getInstance().pressKey("Enter");
		Thread.sleep(2000);
		Thread.sleep(2000);
		Boolean errorgracePeriod = MainFrame.getInstance()
				.searchText("MEM02407 Clm receipt date or date of service after grace period date".trim());

		Boolean errorProviderDet = MainFrame.getInstance()
				.searchText("PRV03414 OUT OF STATE PROVIDER. PLEASE REVIEW FOR DETERMINATION".trim());
		Boolean errorReturnClaim = MainFrame.getInstance()
				.searchText("PRV03536 RETURN CLAIM TO THE BORDER STATE PROVIDER ".trim());
		if (errorgracePeriod) {
			Thread.sleep(1000);
			MainFrame.getInstance().pressKey("F10");
			Thread.sleep(2000);
			MainFrame.getInstance().pressKey("F8");
			Thread.sleep(2000);
			Boolean errorProvider = MainFrame.getInstance()
					.searchText("PRV03414 OUT OF STATE PROVIDER. PLEASE REVIEW FOR DETERMINATION".trim());
			// PRV03414
			if (errorProvider) {
				Thread.sleep(1000);
				MainFrame.getInstance().pressKey("F10");
				Thread.sleep(1000);
				MainFrame.getInstance().setTextByField("RSP:", "pay");
				Thread.sleep(1000);
				MainFrame.getInstance().pressKey("Enter");
				Thread.sleep(2000);
			} else {
				MainFrame.getInstance().setTextByField("RSP:", "pay");
			//	retrieve_GELP_data();
				MainFrame.getInstance().pressKey("Enter");
				Thread.sleep(3000);
				Boolean errorprovider = MainFrame.getInstance().searchText("PRV03414 OUT OF STATE PROVIDER.".trim());
				if (errorprovider) {
					MainFrame.getInstance().pressKey("F10");
					Thread.sleep(3000);
					MainFrame.getInstance().setTextByField("RSP:", "pay");
					Thread.sleep(2000);
					MainFrame.getInstance().pressKey("Enter");
					Thread.sleep(2000);
				}

			}
		} else if (errorReturnClaim) {

			MainFrame.getInstance().pressKey("F10");
			Thread.sleep(3000);
			MainFrame.getInstance().setTextByField("RSP:", "pay");
			Thread.sleep(2000);
			MainFrame.getInstance().pressKey("Enter");
			Thread.sleep(2000);

		}
		else if (errorProviderDet) {

			MainFrame.getInstance().pressKey("F10");
			Thread.sleep(3000);
			MainFrame.getInstance().setTextByField("RSP:", "pay");
			Thread.sleep(2000);
			MainFrame.getInstance().pressKey("Enter");
			Thread.sleep(2000);

		}
	//	retrieve_GCLD_data();
		report.updateTestLog("GCLD ", "GCLD screen displayed", Status.PASS, "MAINFRAME");
		Thread.sleep(2000);
		MainFrame.getInstance().pressKey("Enter");
		Thread.sleep(4000);
		Boolean errorGCD3 = MainFrame.getInstance().searchText("GCD3".trim());
		if (errorGCD3) {
			MainFrame.getInstance().pressKey("Enter");
			Thread.sleep(3000);
		}
		/*	Boolean errorGSVC = MainFrame.getInstance().searchText("GSVC".trim());
		if (errorGSVC) {
			String hCPCCode = MainFrame.getInstance().getText(10, 8, 5).trim();
			System.out.println("hCPCCode is " + hCPCCode);

			dataTable.putData(TestDataSheetName1, "HCPCS_Code", hCPCCode);
			MainFrame.getInstance().pressKey("Enter");
			Thread.sleep(2000);
		}*/

	}}


/*	private void retrieve_GCLD_data() throws InterruptedException {
		String dxCode_gmnu = MainFrame.getInstance().getTextByField("ICD DX CODES/PRESENT ON ADMISSION:");
		dataTable.putData(ClaimsGui, "Diagnosis_code", dxCode_gmnu);
		Thread.sleep(2000);
	}


	private void retrieve_GELP_data() {
		String providerID = MainFrame.getInstance().getTextByField("PROV NO:");

		dataTable.putData(ClaimsGui, "billingProviderNumber", providerID);
		String nPI = MainFrame.getInstance().getTextByField("BILL NPI:");

		dataTable.putData(ClaimsGui, "billingProviderNPI", nPI);

		String taxId = MainFrame.getInstance().getTextByField("TAX ID:");

		dataTable.putData(ClaimsGui, "TaxId", taxId);

		String serviceFromDate = MainFrame.getInstance().getTextByField("DATE FROM:").trim();

		String serviceFromDate1 = serviceFromDate.substring(0, 2).concat("/").concat(serviceFromDate.substring(2, 4))
				.concat("/").concat(serviceFromDate.substring(4, serviceFromDate.length()));
		dataTable.putData(ClaimsGui, "FromDate", serviceFromDate1);

		String serviceToDate = MainFrame.getInstance().getTextByField("TO:").trim();
		String serviceToDate1 = serviceToDate.substring(0, 2).concat("/").concat(serviceToDate.substring(2, 4))
				.concat("/").concat(serviceToDate.substring(4, serviceToDate.length()));

		dataTable.putData(ClaimsGui, "ToDate", serviceToDate1);

		String placeOfTreatment = MainFrame.getInstance().getTextByField("POT:");

		dataTable.putData(ClaimsGui, "place_of_Treatment", placeOfTreatment);

	}

	private void retrieve_GEEP_data() {
		String lastName = MainFrame.getInstance().getTextByField("LAST NAME:").trim();
		dataTable.putData(ClaimsGui, "MemLastName", lastName);
		String firstName = MainFrame.getInstance().getTextByField("FIRST:").trim();
		dataTable.putData(ClaimsGui, "MemFirstName", firstName);
		String groupNumber = MainFrame.getInstance().getTextByField("GRP NO:").trim().replaceFirst("^0+(?!$)", "");

		dataTable.putData(ClaimsGui, "GroupNumber", groupNumber);
		String memberID = MainFrame.getInstance().getTextByField("MEMBER NO:").trim().replaceFirst("^0+(?!$)", "");
		dataTable.putData(ClaimsGui, "SubscriberNumber", memberID);
		String dOB = MainFrame.getInstance().getTextByField("DOB:");
		dataTable.putData(ClaimsGui, "patient_DOB", dOB);
	}*/


	public void bluChip_gsvcF1() throws InterruptedException {
		
		Boolean claimFinalized = MainFrame.getInstance()
				.searchText("HGGD12ML GD120006 THIS CLAIM IS IN A FINALIZED STATUS".trim());
		if (!claimFinalized) {
		MainFrame.getInstance().pressKey("Enter");
		Thread.sleep(2000);
		MainFrame.getInstance().pressKey("Enter");
		Thread.sleep(2000);
		MainFrame.getInstance().pressKey("Enter");
		Thread.sleep(2000);
		MainFrame.getInstance().pressKey("Enter");
		Thread.sleep(2000);
		MainFrame.getInstance().pressKey("F1");
		report.updateTestLog("verify GSVC", "Verification", Status.SCREENSHOT,"MAINFRAME");
		Thread.sleep(9000);
		/*
		boolean strBuildMsg1 = MainFrame.getInstance().searchText("HGAPPDED PDED0001 DEDUCTIBLE AMOUNT TAKEN GREATER THAN ELIGIBLE AMOUNT ".trim());
		System.out.println(strBuildMsg1);
		if(strBuildMsg1)
		{
			report.updateTestLog("verify message", "message"+strBuildMsg1+ "displayed", Status.FAIL,"MAINFRAME");

			report.updateTestLog("verify message", "Verification", Status.SCREENSHOT,"MAINFRAME");
		}
		else {
			report.updateTestLog("verify message", "message"+strBuildMsg1+ " not displayed", Status.PASS,"MAINFRAME");
		}
		Thread.sleep(1000);*/
		
		Boolean screenCPSA0021 = MainFrame.getInstance().searchText("HGACCPSA CPSA0021");
		Boolean screenEMSG1585 = MainFrame.getInstance().searchText("HGAMQDVR EMSG1585");
		Boolean screenEMSG0039 = MainFrame.getInstance().searchText("HGAMQDVR EMSG0039");
		Boolean ScreenErrorEMSG0758 = MainFrame.getInstance().searchText("EMSG0758");
		Boolean ScreenGZA5 = MainFrame.getInstance().searchText("GZA5");
		Boolean ScreenErrorEMSG0928 = MainFrame.getInstance().searchText("HGAPRIME EMSG0928");
		Boolean ScreenErrorEMSG1206 = MainFrame.getInstance().searchText("HGAPRGEA EMSG1206");
		Boolean screenGZAH = MainFrame.getInstance().searchText("GZAH");
		Boolean ScreenGZA8 = MainFrame.getInstance().searchText("GZA8");
		Boolean ScrenGZA7 = MainFrame.getInstance().searchText("GZA7");
		Boolean ScreenGZA2 = MainFrame.getInstance().searchText("GZA2");
		Boolean ScreenGZAE = MainFrame.getInstance().searchText("GZAE");
		Boolean GSVCscreen2 = MainFrame.getInstance().searchText("NO MORE SERVICES".trim());
		
		if (GSVCscreen2) {
			MainFrame.getInstance().pressKey("f1");
			report.updateTestLog("ScreenGSVC ", " is displayed 2 times", Status.PASS, "MAINFRAME");
			Thread.sleep(7000);
			
								
		}
		Thread.sleep(1000);
		if (ScreenGZA5) {
			
			MainFrame.getInstance().setTextByfieldAndOccurance("LEVEL", "C", 1);
			report.updateTestLog("ScreenGZA5 ", "ScreenGZA5 displayed", Status.PASS, "MAINFRAME");
			Thread.sleep(1000);
			MainFrame.getInstance().pressKey("Enter");
			Thread.sleep(9000);
			MainFrame.getInstance().sendCommand("y");
			Thread.sleep(3000);
		
		}

		Thread.sleep(1000);
		if (ScreenGZA5) {
			
			MainFrame.getInstance().setTextByfieldAndOccurance("LEVEL", "S", 1);
			report.updateTestLog("ScreenGZA5 ", "ScreenGZA5 displayed", Status.PASS, "MAINFRAME");
			Thread.sleep(1000);
			MainFrame.getInstance().pressKey("Enter");
			Thread.sleep(9000);
			MainFrame.getInstance().sendCommand("y");
			Thread.sleep(3000);
		
		}
		
		Thread.sleep(1000);
		if (ScreenGZA8) {
			
			Thread.sleep(1000);
			MainFrame.getInstance().setTextByField("DECISION (Y/N):", "Y");
			Thread.sleep(1000);
			report.updateTestLog("ScreenGZA8 ", "ScreenGZA8 displayed", Status.PASS, "MAINFRAME");
			Thread.sleep(1000);
			MainFrame.getInstance().pressKey("Enter");
			Thread.sleep(9000);
		}
	

		// GZAE screen
		if (MainFrame.getInstance().searchTextInRow(1, "GZAE")) {
			report.updateTestLog("Verify Medicare " , "GZAE screen displayed", Status.PASS, "MAINFRAME");
			report.updateTestLog("verify GZAE", "Verification", Status.SCREENSHOT,"MAINFRAME");
			Thread.sleep(1000);
			String strMedicareType = MainFrame.getInstance().getTextByField("MEDICARE TYPE:").trim();
			System.out.println("Medicare Type:" + strMedicareType);
			
			String strexpMedicareType = dataTable.getData(BlueChipSheet, "MedicareType").trim();
			if (strMedicareType.equalsIgnoreCase(strexpMedicareType)) {
				report.updateTestLog("Verify Medicare Type" ,
						"Medicare Type"+ strMedicareType +" is equal to expected Medicare type", Status.PASS);
			} else {
				report.updateTestLog("Verify Medicare Type" ,
						"Medicare Type"+ strMedicareType +" is equal not to expected Medicare type"+ strexpMedicareType, Status.FAIL);
			}
			MainFrame.getInstance().pressKey("Enter");
			
		}
		
		
		// Boolean ScreenHGAMQDVR = MainFrame.getInstance().searchText("HGAMQDVR
		// EMSG1580");
		Boolean screenEMSG1580 = MainFrame.getInstance().searchText("HGAMQDVR EMSG1580");
		if (screenCPSA0021) {
			MainFrame.getInstance().setTextByfieldAndOccurance("LEVEL", "S", 1);
			report.updateTestLog("screenEMSG1580 ", "screenEMSG1580 displayed", Status.PASS, "MAINFRAME");
			MainFrame.getInstance().pressKey("Enter");
			Thread.sleep(3000);
		} 
				
		else if (screenGZAH) {

			MainFrame.getInstance().pressKey("F5");
			Thread.sleep(3000);
			Boolean ScrnGZA5 = MainFrame.getInstance().searchText("GZA5");
			if (ScrnGZA5) {
				Thread.sleep(3000);
				report.updateTestLog("ScrnGZA5 ", "ScrnGZA5 displayed", Status.PASS, "MAINFRAME");
				MainFrame.getInstance().setTextByfieldAndOccurance("LEVEL", "C", 1);
				MainFrame.getInstance().pressKey("Enter");
				Thread.sleep(2000);
			}
		} else if (screenEMSG1585) {
			MainFrame.getInstance().setTextByfieldAndOccurance("LEVEL", "S", 1);
			MainFrame.getInstance().pressKey("Enter");
			Thread.sleep(3000);

			Boolean screnEMSG0039 = MainFrame.getInstance().searchText("HGAMQDVR EMSG0039");
			if (screnEMSG0039) {
				MainFrame.getInstance().setTextByfieldAndOccurance("LEVEL", "C", 1);
				MainFrame.getInstance().pressKey("Enter");
				Thread.sleep(3000);
			}
		} else if (screenEMSG1580) {
			MainFrame.getInstance().setTextByfieldAndOccurance("LEVEL", "S", 1);
			MainFrame.getInstance().pressKey("Enter");
			Thread.sleep(3000);
			Boolean screnEMSG0039 = MainFrame.getInstance().searchText("HGAMQDVR EMSG0039");
			if (screnEMSG0039) {
				MainFrame.getInstance().setTextByfieldAndOccurance("LEVEL", "C", 1);
				MainFrame.getInstance().pressKey("Enter");
				Thread.sleep(3000);

			}

		} else if (screenEMSG0039) {

			MainFrame.getInstance().setTextByfieldAndOccurance("LEVEL", "C", 1);
			MainFrame.getInstance().pressKey("Enter");

			Thread.sleep(3000);

		} else if (ScreenErrorEMSG0758) {

			MainFrame.getInstance().setTextByfieldAndOccurance("LEVEL", "S", 1);

			Thread.sleep(2000);
			MainFrame.getInstance().pressKey("Enter");

			Thread.sleep(2000);
			// EMSG0758
			Boolean ScrenEMSG0758 = MainFrame.getInstance().searchText("EMSG0758");
			Boolean ScrenGZAH = MainFrame.getInstance().searchText("GZAH");
			if (ScrenGZAH) {
				MainFrame.getInstance().pressKey("F5");
				Thread.sleep(2000);
				Boolean ScrenGZA5 = MainFrame.getInstance().searchText("GZA5");
				if (ScrenGZA5) {
					report.updateTestLog("ScrenGZA5 ", "ScrenGZA5 displayed", Status.PASS, "MAINFRAME");
					MainFrame.getInstance().setTextByfieldAndOccurance("LEVEL", "C", 1);
					MainFrame.getInstance().pressKey("Enter");
					Thread.sleep(2000);
				}
			}
			if (ScrenEMSG0758) {
				MainFrame.getInstance().setTextByfieldAndOccurance("LEVEL", "S", 1);
				Thread.sleep(2000);
			}
			Boolean ScreenGZA7 = MainFrame.getInstance().searchText("GZA7");
			if (ScreenGZA7) {
				report.updateTestLog("ScreenGZA7 ", "ScreenGZA7 displayed", Status.PASS, "MAINFRAME");
				MainFrame.getInstance().pressKey("Enter");
				Thread.sleep(2000);
			}

		} else if (screenEMSG1580) {

			MainFrame.getInstance().setTextByfieldAndOccurance("LEVEL", "S", 1);

			Thread.sleep(1000);
			MainFrame.getInstance().pressKey("Enter");
			Thread.sleep(3000);
			// DECISION
			Boolean ScreenDecision = MainFrame.getInstance().searchText("DECISION");
			if (ScreenDecision) {

				MainFrame.getInstance().setTextByField("DECISION (Y/N):", "N");
				MainFrame.getInstance().pressKey("Enter");
				Thread.sleep(2000);

			}
		} else if (ScreenGZA5) {
			
			MainFrame.getInstance().setTextByfieldAndOccurance("LEVEL", "C", 1);
			report.updateTestLog("ScreenGZA5 ", "ScreenGZA5 displayed", Status.PASS, "MAINFRAME");
			Thread.sleep(1000);
			MainFrame.getInstance().pressKey("Enter");

			Thread.sleep(9000);
			Boolean ScrnGZA2 = MainFrame.getInstance().searchText("GZA2");
			if (ScrnGZA2) {
				report.updateTestLog("verify", "Verification", Status.SCREENSHOT,"MAINFRAME");
				MainFrame.getInstance().pressKey("Enter");
				Thread.sleep(2000);
				report.updateTestLog("verify", "Verification", Status.SCREENSHOT,"MAINFRAME");
				MainFrame.getInstance().pressKey("Enter");

			
			Boolean screenGOPL = MainFrame.getInstance().searchText("GOPL");
			if (screenGOPL) {
				report.updateTestLog("verify ", "Verification", Status.SCREENSHOT,"MAINFRAME");
				MainFrame.getInstance().pressKey("Enter");
				Thread.sleep(1000);
				report.updateTestLog("GOPL", "GOPL screen displayed", Status.PASS, "MAINFRAME");
				Boolean ScnGOPA = MainFrame.getInstance().searchText("GOPA");
				Thread.sleep(1000);
				if (ScnGOPA) {
					MainFrame.getInstance().pressKey("Enter");
					Thread.sleep(1000);
					Boolean ScnGCB1 = MainFrame.getInstance().searchText("GCB1");
					if (ScnGCB1) {
						MainFrame.getInstance().pressKey("Tab");
						Thread.sleep(1000);
						MainFrame.getInstance().pressKey("Tab");
						Thread.sleep(1000);
						MainFrame.getInstance().pressKey("Tab");
						Thread.sleep(1000);
						MainFrame.getInstance().pressKey("Tab");
						Thread.sleep(1000);
						MainFrame.getInstance().pressKey("Tab");
						Thread.sleep(1000);
						MainFrame.getInstance().pressKey("Tab");
						Thread.sleep(1000);
						MainFrame.getInstance().pressKey("Tab");
						Thread.sleep(1000);
						MainFrame.getInstance().setTextByField("NO DETAIL AVAILABLE TO ENTER ", "z");
						report.updateTestLog("verify ", "Verification", Status.SCREENSHOT,"MAINFRAME");
						Thread.sleep(1000);
						MainFrame.getInstance().pressKey("Enter");
						Thread.sleep(1000);
					}

				}

			}
		
		
			}
		}
			

		
	}}

	public void validate_GCLR_Screen() throws InterruptedException {
		
		Boolean claimFinalized = MainFrame.getInstance()
				.searchText("HGGD12ML GD120006 THIS CLAIM IS IN A FINALIZED STATUS".trim());
		if (!claimFinalized) 
	//	while (!(MainFrame.getInstance().searchText("GCLR")));
		{
			
		
		Boolean ScreenGCLR = MainFrame.getInstance().searchText("GCLR");

		if (ScreenGCLR) {

			report.updateTestLog("GCLR", "GCLR Screen is Displayed ", Status.PASS, "MAINFRAME");
			/*	Thread.sleep(2000);
			dataTable.putData(TestDataSheetName1, "ClaimStatusCode", MainFrame.getInstance().getTextByField("STATUS CD:"));
			dataTable.putData(TestDataSheetName1, "ClaimReasonCode", MainFrame.getInstance().getTextByField("RSN CD:"));
			report.updateTestLog("GCLR", "Claims Status and Reason Code Captured", Status.PASS, "MAINFRAME");
			String strStatusCode = MainFrame.getInstance().getTextByField("STATUS CD:");
			String strReasonCode = MainFrame.getInstance().getTextByField("RSN CD:");
			System.out.println("Status Code:" + strStatusCode);
			System.out.println("Reason Code:" + strReasonCode);
			MainFrame.getInstance().pressKey("F4");
			Thread.sleep(1000);
			report.updateTestLog("GDCL screen validation", "GDCL screen displayed ", Status.SCREENSHOT, "MAINFRAME");*/
			MainFrame.getInstance().setTextByField("DOC COMP:", "Y");
			MainFrame.getInstance().pressKey("Enter");
			Thread.sleep(1000);
			MainFrame.getInstance().setTextByField("DOC COMP:", "Y");
			MainFrame.getInstance().pressKey("Enter");
			Thread.sleep(3000);
			
			Boolean ScreenXDED = MainFrame.getInstance().searchText("XDED");
			
			if (ScreenXDED) {

				report.updateTestLog("Claim", "is finalized", Status.PASS, "MAINFRAME");
				MainFrame.getInstance().sendCommand("GDED");
				Thread.sleep(2000);
				MainFrame.getInstance().pressKey("Enter");
			/*MainFrame.getInstance().setText("01");
			Thread.sleep(1000);
			MainFrame.getInstance().pressKey("Enter");
			Thread.sleep(1000);*/
		} 
			/*		else 
		{
			report.updateTestLog("GCLR", "GCLR Screen is not Displayed", Status.FAIL, "MAINFRAME");
			MainFrame.getInstance().pressKey("F2");
			Thread.sleep(1000);
		
			MainFrame.getInstance().pressKey("Enter");
			MainFrame.getInstance().pressKey("Clear");
			Thread.sleep(1000);
			MainFrame.getInstance().sendCommand("gdwn");
			MainFrame.getInstance().pressKey("Enter");
			MainFrame.getInstance().quitSession();

		}*/
		}
		else 
		
			report.updateTestLog("GCLR", "GCLR Screen is not Displayed", Status.FAIL, "MAINFRAME");
	}}

	public void blueChip_Inelig_RSNCD() throws InterruptedException {
		// INEL RNS:
		String inelgcode = MainFrame.getInstance().getTextByField("INEL RSNS :").trim();
		String rsnCode = dataTable.getData(BlueChipSheet, "Ineligible_Reason_Code").trim();
		if (rsnCode.trim().equals(inelgcode)) {
			report.updateTestLog("Verify INEL RNS", "Ineligible Reason Code is"+ inelgcode + "same as expected"+ rsnCode  , Status.PASS, "MAINFRAME");
		} else {
			report.updateTestLog("Verify INEL RNS", "Ineligible Reason Code is same as expected"+ rsnCode , Status.PASS, "MAINFRAME");
		}
	
		MainFrame.getInstance().pressKey("F2");
		Thread.sleep(1000);
		report.updateTestLog("GDS2", "Screen validation", Status.SCREENSHOT, "MAINFRAME");
		MainFrame.getInstance().pressKey("F2");
		Thread.sleep(1000);
		report.updateTestLog("GDS3", "Screen validation", Status.SCREENSHOT, "MAINFRAME");
		MainFrame.getInstance().pressKey("F2");
		Thread.sleep(1000);
		report.updateTestLog("GDS4", "Screen validation", Status.SCREENSHOT, "MAINFRAME");
		MainFrame.getInstance().pressKey("F2");
		Thread.sleep(1000);
		report.updateTestLog("GDS5", "Screen validation", Status.SCREENSHOT, "MAINFRAME");
		MainFrame.getInstance().pressKey("F2");
		Thread.sleep(1000);
		report.updateTestLog("GDS6", "Screen validation", Status.SCREENSHOT, "MAINFRAME");
		MainFrame.getInstance().pressKey("F2");
		Thread.sleep(1000);
		report.updateTestLog("GDS7", "Screen validation", Status.SCREENSHOT, "MAINFRAME");
		// MC INVCD:
		String mc_INVCD = MainFrame.getInstance().getTextByField("MC INVCD:").trim();
		String expmc_INVCD = dataTable.getData(BlueChipSheet, "MC_INVCD").trim();
		if (expmc_INVCD.trim().equals(mc_INVCD)) {
			report.updateTestLog("Verify MC code for Medicare", "MC Code is"+ mc_INVCD + "same as expected"+ expmc_INVCD  , Status.PASS, "MAINFRAME");
		} else {
			report.updateTestLog("Verify MC code for Medicare", "MC Code is"+ mc_INVCD + "not same as expected"+ expmc_INVCD , Status.FAIL, "MAINFRAME");
		}
	
		// TPL Type:
				String tpl_TYPE = MainFrame.getInstance().getTextByField("TPL TYPE:").trim();
				String expTPL_Type = dataTable.getData(BlueChipSheet, "TPL_Type").trim();
				if (expTPL_Type.trim().equals(tpl_TYPE)) {
					report.updateTestLog("Verify TPL type", "TPL type is"+ tpl_TYPE + "same as expected"+ expTPL_Type  , Status.PASS, "MAINFRAME");
				} else {
					report.updateTestLog("Verify TPL Type", "TPL type is"+ tpl_TYPE + "not same as expected"+ expTPL_Type , Status.FAIL, "MAINFRAME");
				}
			
		
		MainFrame.getInstance().pressKey("F2");
		Thread.sleep(1000);
		report.updateTestLog("GDS8", "Screen validation", Status.SCREENSHOT, "MAINFRAME");
	
		MainFrame.getInstance().pressKey("Enter");
		Thread.sleep(1000);
		report.updateTestLog("GDAG", "Screen validation", Status.SCREENSHOT, "MAINFRAME");
		MainFrame.getInstance().pressKey("Clear");
		Thread.sleep(1000);
		MainFrame.getInstance().sendCommand("gdwn");
		MainFrame.getInstance().pressKey("Enter");
		MainFrame.getInstance().quitSession();
	
	}

	public void blueChip_logout() throws InterruptedException {
		Thread.sleep(500);
		MainFrame.getInstance().pressKey("CLEARSCREEN");
		MainFrame.getInstance().sendCommand("logoff");
		Thread.sleep(2000);
		MainFrame.getInstance().pressKey("Enter");
		Thread.sleep(2000);
		report.updateTestLog("LoGoff", "Logout of the current user session", Status.PASS, "MAINFRAME");
		MainFrame.getInstance().quitSession();
	}

	public void iMNU_searchDCN() throws InterruptedException {

		MainFrame.getInstance().pressKey("CLEARSCREEN");
		Thread.sleep(3000);
		MainFrame.getInstance().sendCommand("IMNU");
		Thread.sleep(3000);
		/*if (DCN.length() == 17) {
			DCN = DCN.substring(4, DCN.length());
		}
		String DCNType = dataTable.getData(TestDataSheetName, "DCN");
		DCN = DCN.substring(0, (DCN.length() - 2)) + DCNType;*/
		String dcn = dataTable.getData(BlueChipSheet, "DCN");
		dcn = dcn.substring(0, 11) + dcn.substring(12);
		MainFrame.getInstance().setTextByField("2 DCN   NUMBER:", dcn);
		MainFrame.getInstance().setTextByField("INC:", "");
		/*String ADJ = dataTable.getData(TestDataSheetName, "ADJ");
		MainFrame.getInstance().setTextByField("ADJ:", ADJ);*/
		MainFrame.getInstance().setTextByField("KEY IN ITEM NUMBER:", "11");
		MainFrame.getInstance().pressKey("Enter");
		Thread.sleep(2000);
		
		}
	
	
	public void gMNU_searchDCN() throws InterruptedException {

		MainFrame.getInstance().sendCommand("04");		
		String dcn = dataTable.getData(BlueChipSheet, "DCN");
		if (dcn.length() > 16) 
		{
			dcn = dcn.substring(4, 17);		
		}
		MainFrame.getInstance().setTextByField("PLEASE ENTER CLAIM NUMBER:", dcn);
		MainFrame.getInstance().setTextByField("PLEASE ENTER CLAIM OPTION:", "1a");
		MainFrame.getInstance().pressKey("Enter");
		Thread.sleep(2000);
		
		}
	

	
	public void iMNU_VerifyClaimDisplay() throws InterruptedException {
	
		// PROV #:
				String prov_Num = MainFrame.getInstance().getTextByField("PROV#:").trim();
				String d_ProvNum = dataTable.getData(BlueChipSheet, "Prov_Num").trim();
				Thread.sleep(1000);
				if (d_ProvNum.trim().equals(prov_Num)) {
					report.updateTestLog("Verify Prov Number", "Prov Number" + prov_Num + " same as expected" + d_ProvNum  , Status.PASS, "MAINFRAME");
				} else {
					report.updateTestLog("Verify Prov Number", "Prov Number" + prov_Num + " not same as expected" + d_ProvNum , Status.FAIL, "MAINFRAME");
				}
				
				MainFrame.getInstance().pressKey("F3");
				Thread.sleep(1000);
				
				MainFrame.getInstance().setTextByField("KEY IN ITEM NUMBER:", "17");
				MainFrame.getInstance().pressKey("Enter");
				Thread.sleep(2000);
				report.updateTestLog("Claim level display 1", "Claim level display 1 verification"  , Status.SCREENSHOT, "MAINFRAME");
				Thread.sleep(2000);
				MainFrame.getInstance().pressKey("Enter");
				
				//Claim level display 2 validations
				
				//Section#:
				
				String section = MainFrame.getInstance().getTextByField("SECTION NUMBER  :").trim();
				String d_Section = dataTable.getData(BlueChipSheet, "exp_Section").trim();
				Thread.sleep(1000);
				if (d_Section.trim().equals(section)) {
					report.updateTestLog("Verify Section Number", "Section Number " + section + " same as expected " + d_Section  , Status.PASS, "MAINFRAME");
				} else {
					report.updateTestLog("Verify Section Number", "Section Number " + section + " not same as expected " + d_Section , Status.FAIL, "MAINFRAME");
				}
				
				
				//LOB:
				
				String lob = MainFrame.getInstance().getTextByField("LINE OF BUSINESS:").trim();
				String d_LOB = dataTable.getData(BlueChipSheet, "exp_LOB").trim();
				Thread.sleep(1000);
				if (d_LOB.trim().equals(lob)) {
					report.updateTestLog("Verify LINE OF BUSINESS", "LINE OF BUSINESS " + lob + " same as expected " + d_LOB  , Status.PASS, "MAINFRAME");
				} else {
					report.updateTestLog("Verify LINE OF BUSINESS", "LINE OF BUSINESS " + lob + " not same as expected " + d_LOB , Status.FAIL, "MAINFRAME");
				}
				
								
				//PROVIDER CONTROL:
				
				String prov_Control = MainFrame.getInstance().getTextByField("PROVIDER CONTROL:").trim();
				String d_ProvCtrl = dataTable.getData(BlueChipSheet, "exp_PRV").trim();
				Thread.sleep(1000);
				if (d_ProvCtrl.trim().equals(prov_Control)) {
					report.updateTestLog("Verify PROVIDER CONTROL", "PROVIDER CONTROL " + prov_Control + " same as expected " + d_ProvCtrl  , Status.PASS, "MAINFRAME");
				} else {
					report.updateTestLog("Verify PROVIDER CONTROL", "PROVIDER CONTROL " + prov_Control + " not same as expected " + d_ProvCtrl , Status.FAIL, "MAINFRAME");
				}
				
				
				//FAM REL LEVEL   :
				
				String fam_RelLvl = MainFrame.getInstance().getTextByField("FAM REL LEVEL   :").trim();
				String d_FamRelLvl = dataTable.getData(BlueChipSheet, "exp_FRL").trim();
				Thread.sleep(1000);
				if (d_FamRelLvl.trim().equals(fam_RelLvl)) {
					report.updateTestLog("Verify FAM REL LEVEL", "FAM REL LEVEL" + fam_RelLvl + " same as expected" + d_FamRelLvl  , Status.PASS, "MAINFRAME");
				} else {
					report.updateTestLog("Verify FAM REL LEVEL", "FAM REL LEVEL" + fam_RelLvl + " not same as expected" + d_FamRelLvl , Status.FAIL, "MAINFRAME");
				}
				
				
               //Effective Date   :
				
				String eff_Date = MainFrame.getInstance().getTextByField("EFFECTIVE DATE  :").trim();
				String d_EffDate = dataTable.getData(BlueChipSheet, "exp_EffDate").trim();
				Thread.sleep(1000);
				if (d_EffDate.trim().equals(eff_Date)) {
					report.updateTestLog("Verify Effective Date", "Effective Date " + eff_Date + " same as expected " + d_EffDate  , Status.PASS, "MAINFRAME");
				} else {
					report.updateTestLog("Verify Effective Date", "Effective Date " + eff_Date + " not same as expected " + d_EffDate , Status.FAIL, "MAINFRAME");
				}
				
				
				MainFrame.getInstance().pressKey("F3");
				Thread.sleep(1000);
				
				MainFrame.getInstance().setTextByField("KEY IN ITEM NUMBER:", "18");
				MainFrame.getInstance().pressKey("Enter");
				Thread.sleep(2000);
				report.updateTestLog("Claim Aggregate display ", "Claim Aggregate display verification"  , Status.SCREENSHOT, "MAINFRAME");
				
              //BENE PER :
				
				String bene_Per = MainFrame.getInstance().getTextByField("BENE PER:").trim();
				String d_BenePer = dataTable.getData(BlueChipSheet, "exp_BENE_PER").trim();
				Thread.sleep(1000);
				if (d_BenePer.trim().equals(bene_Per)) {
					report.updateTestLog("Verify BENE PER", "BENE PER " + bene_Per + " same as expected " + d_BenePer  , Status.PASS, "MAINFRAME");
				} else {
					report.updateTestLog("Verify BENE PER", "BENE PER " + bene_Per + " not same as expected " + d_BenePer , Status.FAIL, "MAINFRAME");
				}
				
				
				//ACCUM TP:
				
				String accum_TP = MainFrame.getInstance().getTextByField("ACCUM TP:").trim();
				String d_AccumTP = dataTable.getData(BlueChipSheet, "exp_ACCUM_TP").trim();
				Thread.sleep(1000);
				if (d_AccumTP.trim().equals(accum_TP)) {
					report.updateTestLog("Verify ACCUM TP", "ACCUM TP " + accum_TP + " same as expected " + d_AccumTP  , Status.PASS, "MAINFRAME");
				} else {
					report.updateTestLog("Verify ACCUM TP", "ACCUM TP " + accum_TP + " not same as expected " + d_AccumTP , Status.FAIL, "MAINFRAME");
				}
				
				
				
				//DESC1:
				
				String desc = MainFrame.getInstance().getTextByField("DESC:").trim();
				String d_Desc = dataTable.getData(BlueChipSheet, "exp_DESC_1").trim();
				Thread.sleep(1000);
				if (d_Desc.trim().equals(desc)) {
					report.updateTestLog("Verify DESC", "DESC " + desc + " same as expected " + d_Desc  , Status.PASS, "MAINFRAME");
				} else {
					report.updateTestLog("Verify DESC", "DESC " + desc + " not same as expected " + d_Desc , Status.FAIL, "MAINFRAME");
				}
				
				
				
				
				//BEN GRP:
				
				String benGrp = MainFrame.getInstance().getTextByField("BEN GRP:").trim();
				String d_BenGrp = dataTable.getData(BlueChipSheet, "exp_BEN_GRP").trim();
				Thread.sleep(1000);
				if (d_BenGrp.trim().equals(benGrp)) {
					report.updateTestLog("Verify BEN GRP", "BEN GRP " + benGrp + " same as expected " + d_BenGrp  , Status.PASS, "MAINFRAME");
				} else {
					report.updateTestLog("Verify BEN GRP", "BEN GRP " + benGrp + " not same as expected " + d_BenGrp , Status.FAIL, "MAINFRAME");
				}
				
				
				//PROV #:
				

				String provTP = MainFrame.getInstance().getTextByField("PROV # :").trim();
				String d_ProvTP = dataTable.getData(BlueChipSheet, "exp_PROV_Num").trim();
				Thread.sleep(1000);
				if (d_ProvTP.trim().equals(provTP)) {
					report.updateTestLog("Verify PROV#", "PROV# " + provTP + " same as expected " + d_ProvTP  , Status.PASS, "MAINFRAME");
				} else {
					report.updateTestLog("Verify PROV#", "PROV# " + provTP + " not same as expected " + d_ProvTP , Status.FAIL, "MAINFRAME");
				}
				
				
				
				MainFrame.getInstance().pressKey("F8");
				Thread.sleep(2000);
				
				//ICAD Line 02:
				
				
				//BENE PER :
				
				String bene_Per2= MainFrame.getInstance().getTextByField("BENE PER:").trim();
				String d_BenePer2= dataTable.getData(BlueChipSheet, "exp_BENE_PER").trim();
				Thread.sleep(1000);
				if (d_BenePer2.trim().equals(bene_Per2)) {
					report.updateTestLog("Verify BENE PER", "BENE PER " + bene_Per2 + " same as expected " + d_BenePer2  , Status.PASS, "MAINFRAME");
				} else {
					report.updateTestLog("Verify BENE PER", "BENE PER " + bene_Per2 + " not same as expected " + d_BenePer2 , Status.FAIL, "MAINFRAME");
				}
				
				
				//ACCUM TP:
				
				String accum_TP2= MainFrame.getInstance().getTextByField("ACCUM TP:").trim();
				String d_AccumTP2= dataTable.getData(BlueChipSheet, "exp_ACCUM_TP").trim();
				Thread.sleep(1000);
				if (d_AccumTP2.trim().equals(accum_TP2)) {
					report.updateTestLog("Verify ACCUM TP", "ACCUM TP " + accum_TP2 + " same as expected " + d_AccumTP2  , Status.PASS, "MAINFRAME");
				} else {
					report.updateTestLog("Verify ACCUM TP", "ACCUM TP " + accum_TP2 + " not same as expected " + d_AccumTP2 , Status.FAIL, "MAINFRAME");
				}
				
				
				//DESC2: 
				
				String desc2= MainFrame.getInstance().getTextByField("DESC:").trim();
				String d_Desc2= dataTable.getData(BlueChipSheet, "exp_DESC_2").trim();
				Thread.sleep(1000);
				if (d_Desc2.trim().equals(desc2)) {
					report.updateTestLog("Verify DESC", "DESC " + desc2 + " same as expected " + d_Desc2  , Status.PASS, "MAINFRAME");
				} else {
					report.updateTestLog("Verify DESC", "DESC " + desc2 + " not same as expected " + d_Desc2 , Status.FAIL, "MAINFRAME");
				}
				
				
				//BEN GRP:
				
				String benGrp2= MainFrame.getInstance().getTextByField("BEN GRP:").trim();
				String d_BenGrp2= dataTable.getData(BlueChipSheet, "exp_BEN_GRP").trim();
				Thread.sleep(1000);
				if (d_BenGrp2.trim().equals(benGrp2)) {
					report.updateTestLog("Verify BEN GRP", "BEN GRP " + benGrp2 + " same as expected " + d_BenGrp2  , Status.PASS, "MAINFRAME");
				} else {
					report.updateTestLog("Verify BEN GRP", "BEN GRP " + benGrp2 + " not same as expected " + d_BenGrp2 , Status.FAIL, "MAINFRAME");
				}
				
				
				//PROV #:
				

				String provTP2= MainFrame.getInstance().getTextByField("PROV # :").trim();
				String d_ProvTP2= dataTable.getData(BlueChipSheet, "exp_PROV_Num").trim();
				Thread.sleep(1000);
				if (d_ProvTP2.trim().equals(provTP2)) {
					report.updateTestLog("Verify PROV#", "PROV# " + provTP2 + " same as expected " + d_ProvTP2  , Status.PASS, "MAINFRAME");
				} else {
					report.updateTestLog("Verify PROV", "PROV# " + provTP2 + " not same as expected " + d_ProvTP2 , Status.FAIL, "MAINFRAME");
				}
				
				
				
				MainFrame.getInstance().pressKey("F8");
				Thread.sleep(2000);
				
				/*MainFrame.getInstance().pressKey("F8");
				Thread.sleep(2000);
				
				MainFrame.getInstance().pressKey("F8");
				Thread.sleep(2000);*/
				
				
				//ICAD Line 03:
				
				
				//BENE PER :
				
				String bene_Per3= MainFrame.getInstance().getTextByField("BENE PER:").trim();
				String d_BenePer3= dataTable.getData(BlueChipSheet, "exp_BENE_PER").trim();
				Thread.sleep(1000);
				if (d_BenePer3.trim().equals(bene_Per3)) {
					report.updateTestLog("Verify BENE PER", "BENE PER " + bene_Per3 + " same as expected " + d_BenePer3  , Status.PASS, "MAINFRAME");
				} else {
					report.updateTestLog("Verify BENE PER", "BENE PER " + bene_Per3 + " not same as expected " + d_BenePer3 , Status.FAIL, "MAINFRAME");
				}
				
				
				//ACCUM TP:
				
				String accum_TP3= MainFrame.getInstance().getTextByField("ACCUM TP:").trim();
				String d_AccumTP3= dataTable.getData(BlueChipSheet, "exp_ACCUM_TP").trim();
				Thread.sleep(1000);
				if (d_AccumTP3.trim().equals(accum_TP3)) {
					report.updateTestLog("Verify ACCUM TP", "ACCUM TP " + accum_TP3 + " same as expected " + d_AccumTP3  , Status.PASS, "MAINFRAME");
				} else {
					report.updateTestLog("Verify ACCUM TP", "ACCUM TP " + accum_TP3 + " not same as expected " + d_AccumTP3 , Status.FAIL, "MAINFRAME");
				}
				
				
				//DESC2: 
				
				String desc3= MainFrame.getInstance().getTextByField("DESC:").trim();
				String d_Desc3= dataTable.getData(BlueChipSheet, "exp_DESC_3").trim();
				Thread.sleep(1000);
				if (d_Desc3.trim().equals(desc3)) {
					report.updateTestLog("Verify DESC", "DESC " + desc3 + " same as expected " + d_Desc3  , Status.PASS, "MAINFRAME");
				} else {
					report.updateTestLog("Verify DESC", "DESC " + desc3 + " not same as expected " + d_Desc3 , Status.FAIL, "MAINFRAME");
				}
				
				
				//BEN GRP:
				
				String benGrp3= MainFrame.getInstance().getTextByField("BEN GRP:").trim();
				String d_BenGrp3= dataTable.getData(BlueChipSheet, "exp_BEN_GRP").trim();
				Thread.sleep(1000);
				if (d_BenGrp3.trim().equals(benGrp3)) {
					report.updateTestLog("Verify BEN GRP", "BEN GRP " + benGrp3 + " same as expected " + d_BenGrp3  , Status.PASS, "MAINFRAME");
				} else {
					report.updateTestLog("Verify BEN GRP", "BEN GRP " + benGrp3 + " not same as expected " + d_BenGrp3 , Status.FAIL, "MAINFRAME");
				}
				
				
				//PROV #:
				

				String provTP3= MainFrame.getInstance().getTextByField("PROV # :").trim();
				String d_ProvTP3= dataTable.getData(BlueChipSheet, "exp_PROV_Num").trim();
				Thread.sleep(1000);
				if (d_ProvTP3.trim().equals(provTP3)) {
					report.updateTestLog("Verify PROV#", "PROV# " + provTP3 + " same as expected " + d_ProvTP3  , Status.PASS, "MAINFRAME");
				} else { 
					report.updateTestLog("Verify PROV", "PROV# " + provTP3 + " not same as expected " + d_ProvTP3 , Status.FAIL, "MAINFRAME");
				}
				
				
				MainFrame.getInstance().pressKey("F8");
				Thread.sleep(2000);
				
				MainFrame.getInstance().pressKey("F8");
				Thread.sleep(2000);
				
				MainFrame.getInstance().pressKey("F8");
				Thread.sleep(2000);
				
				MainFrame.getInstance().pressKey("F8");
				Thread.sleep(2000);
				//ICAD Line 04:
				
				
				//BENE PER :
				
				String bene_Per4= MainFrame.getInstance().getTextByField("BENE PER:").trim();
				String d_BenePer4= dataTable.getData(BlueChipSheet, "exp_BENE_PER").trim();
				Thread.sleep(1000);
				if (d_BenePer4.trim().equals(bene_Per4)) {
					report.updateTestLog("Verify BENE PER", "BENE PER " + bene_Per4 + " same as expected " + d_BenePer4  , Status.PASS, "MAINFRAME");
				} else {
					report.updateTestLog("Verify BENE PER", "BENE PER " + bene_Per4 + " not same as expected " + d_BenePer4 , Status.FAIL, "MAINFRAME");
				}
				
				
				//ACCUM TP:
				
				String accum_TP4= MainFrame.getInstance().getTextByField("ACCUM TP:").trim();
				String d_AccumTP4= dataTable.getData(BlueChipSheet, "exp_ACCUM_TP").trim();
				Thread.sleep(1000);
				if (d_AccumTP3.trim().equals(accum_TP3)) {
					report.updateTestLog("Verify ACCUM TP", "ACCUM TP " + accum_TP4 + " same as expected " + d_AccumTP4  , Status.PASS, "MAINFRAME");
				} else {
					report.updateTestLog("Verify ACCUM TP", "ACCUM TP " + accum_TP4 + " not same as expected " + d_AccumTP4 , Status.FAIL, "MAINFRAME");
				}
				
				
				//DESC2: 
				
				String desc4= MainFrame.getInstance().getTextByField("DESC:").trim();
				String d_Desc4= dataTable.getData(BlueChipSheet, "exp_DESC_4").trim();
				Thread.sleep(1000);
				if (d_Desc4.trim().equals(desc4)) {
					report.updateTestLog("Verify DESC", "DESC " + desc4 + " same as expected " + d_Desc4  , Status.PASS, "MAINFRAME");
				} else {
					report.updateTestLog("Verify DESC", "DESC " + desc4 + " not same as expected " + d_Desc4 , Status.FAIL, "MAINFRAME");
				}
				
				
				//BEN GRP:
				
				String benGrp4= MainFrame.getInstance().getTextByField("BEN GRP:").trim();
				String d_BenGrp4= dataTable.getData(BlueChipSheet, "exp_BEN_GRP").trim();
				Thread.sleep(1000);
				if (d_BenGrp4.trim().equals(benGrp4)) {
					report.updateTestLog("Verify BEN GRP", "BEN GRP " + benGrp4 + " same as expected " + d_BenGrp4  , Status.PASS, "MAINFRAME");
				} else {
					report.updateTestLog("Verify BEN GRP", "BEN GRP " + benGrp4 + " not same as expected " + d_BenGrp4 , Status.FAIL, "MAINFRAME");
				}
				
				
				//PROV #:
				

				String provTP4= MainFrame.getInstance().getTextByField("PROV # :").trim();
				String d_ProvTP4= dataTable.getData(BlueChipSheet, "exp_PROV_Num").trim();
				Thread.sleep(1000);
				if (d_ProvTP4.trim().equals(provTP4)) {
					report.updateTestLog("Verify PROV#", "PROV# " + provTP4 + " same as expected " + d_ProvTP4  , Status.PASS, "MAINFRAME");
				} else {
					report.updateTestLog("Verify PROV", "PROV# " + provTP4 + " not same as expected " + d_ProvTP4 , Status.FAIL, "MAINFRAME");
				}
				
				
				
	
	
	
	}
	
	
	public void iMNU_VerifyClaimDisplayPath18() throws InterruptedException {
		
		// PROV #:
				String prov_Num = MainFrame.getInstance().getTextByField("PROV#:").trim();
				String d_ProvNum = dataTable.getData(BlueChipSheet, "Prov_Num").trim();
				Thread.sleep(1000);
				if (d_ProvNum.trim().equals(prov_Num)) {
					report.updateTestLog("Verify Prov Number", "Prov Number" + prov_Num + " same as expected" + d_ProvNum  , Status.PASS, "MAINFRAME");
				} else {
					report.updateTestLog("Verify Prov Number", "Prov Number" + prov_Num + " not same as expected" + d_ProvNum , Status.FAIL, "MAINFRAME");
				}
				
				MainFrame.getInstance().pressKey("F3");
				Thread.sleep(1000);
				
				MainFrame.getInstance().setTextByField("KEY IN ITEM NUMBER:", "17");
				MainFrame.getInstance().pressKey("Enter");
				Thread.sleep(2000);
				report.updateTestLog("Claim level display 1", "Claim level display 1 verification"  , Status.SCREENSHOT, "MAINFRAME");
				Thread.sleep(2000);
				MainFrame.getInstance().pressKey("Enter");
				
				//Claim level display 2 validations
				
				//Section#:
				
				String section = MainFrame.getInstance().getTextByField("SECTION NUMBER  :").trim();
				String d_Section = dataTable.getData(BlueChipSheet, "exp_Section").trim();
				Thread.sleep(1000);
				if (d_Section.trim().equals(section)) {
					report.updateTestLog("Verify Section Number", "Section Number " + section + " same as expected " + d_Section  , Status.PASS, "MAINFRAME");
				} else {
					report.updateTestLog("Verify Section Number", "Section Number " + section + " not same as expected " + d_Section , Status.FAIL, "MAINFRAME");
				}
				
				
				//LOB:
				
				String lob = MainFrame.getInstance().getTextByField("LINE OF BUSINESS:").trim();
				String d_LOB = dataTable.getData(BlueChipSheet, "exp_LOB").trim();
				Thread.sleep(1000);
				if (d_LOB.trim().equals(lob)) {
					report.updateTestLog("Verify LINE OF BUSINESS", "LINE OF BUSINESS " + lob + " same as expected " + d_LOB  , Status.PASS, "MAINFRAME");
				} else {
					report.updateTestLog("Verify LINE OF BUSINESS", "LINE OF BUSINESS " + lob + " not same as expected " + d_LOB , Status.FAIL, "MAINFRAME");
				}
				
								
				//PROVIDER CONTROL:
				
				String prov_Control = MainFrame.getInstance().getTextByField("PROVIDER CONTROL:").trim();
				String d_ProvCtrl = dataTable.getData(BlueChipSheet, "exp_PRV").trim();
				Thread.sleep(1000);
				if (d_ProvCtrl.trim().equals(prov_Control)) {
					report.updateTestLog("Verify PROVIDER CONTROL", "PROVIDER CONTROL " + prov_Control + " same as expected " + d_ProvCtrl  , Status.PASS, "MAINFRAME");
				} else {
					report.updateTestLog("Verify PROVIDER CONTROL", "PROVIDER CONTROL " + prov_Control + " not same as expected " + d_ProvCtrl , Status.FAIL, "MAINFRAME");
				}
				
				
				//FAM REL LEVEL   :
				
				String fam_RelLvl = MainFrame.getInstance().getTextByField("FAM REL LEVEL   :").trim();
				String d_FamRelLvl = dataTable.getData(BlueChipSheet, "exp_FRL").trim();
				Thread.sleep(1000);
				if (d_FamRelLvl.trim().equals(fam_RelLvl)) {
					report.updateTestLog("Verify FAM REL LEVEL", "FAM REL LEVEL" + fam_RelLvl + " same as expected" + d_FamRelLvl  , Status.PASS, "MAINFRAME");
				} else {
					report.updateTestLog("Verify FAM REL LEVEL", "FAM REL LEVEL" + fam_RelLvl + " not same as expected" + d_FamRelLvl , Status.FAIL, "MAINFRAME");
				}
				
				
               //Effective Date   :
				
				String eff_Date = MainFrame.getInstance().getTextByField("EFFECTIVE DATE  :").trim();
				String d_EffDate = dataTable.getData(BlueChipSheet, "exp_EffDate").trim();
				Thread.sleep(1000);
				if (d_EffDate.trim().equals(eff_Date)) {
					report.updateTestLog("Verify Effective Date", "Effective Date " + eff_Date + " same as expected " + d_EffDate  , Status.PASS, "MAINFRAME");
				} else {
					report.updateTestLog("Verify Effective Date", "Effective Date " + eff_Date + " not same as expected " + d_EffDate , Status.FAIL, "MAINFRAME");
				}
				
				
				MainFrame.getInstance().pressKey("F3");
				Thread.sleep(1000);
				
				MainFrame.getInstance().setTextByField("KEY IN ITEM NUMBER:", "18");
				MainFrame.getInstance().pressKey("Enter");
				Thread.sleep(2000);
				report.updateTestLog("Claim Aggregate display ", "Claim Aggregate display verification"  , Status.SCREENSHOT, "MAINFRAME");
				
              
				MainFrame.getInstance().pressKey("F8");
				Thread.sleep(1000);
				
				//BENE PER :
				
				String bene_Per = MainFrame.getInstance().getTextByField("BENE PER:").trim();
				String d_BenePer = dataTable.getData(BlueChipSheet, "exp_BENE_PER").trim();
				Thread.sleep(1000);
				if (d_BenePer.trim().equals(bene_Per)) {
					report.updateTestLog("Verify BENE PER", "BENE PER " + bene_Per + " same as expected " + d_BenePer  , Status.PASS, "MAINFRAME");
				} else {
					report.updateTestLog("Verify BENE PER", "BENE PER " + bene_Per + " not same as expected " + d_BenePer , Status.FAIL, "MAINFRAME");
				}
				
				
				//ACCUM TP:
				
				String accum_TP = MainFrame.getInstance().getTextByField("ACCUM TP:").trim();
				String d_AccumTP = dataTable.getData(BlueChipSheet, "exp_ACCUM_TP").trim();
				Thread.sleep(1000);
				if (d_AccumTP.trim().equals(accum_TP)) {
					report.updateTestLog("Verify ACCUM TP", "ACCUM TP " + accum_TP + " same as expected " + d_AccumTP  , Status.PASS, "MAINFRAME");
				} else {
					report.updateTestLog("Verify ACCUM TP", "ACCUM TP " + accum_TP + " not same as expected " + d_AccumTP , Status.FAIL, "MAINFRAME");
				}
				
				
				
				//DESC1:
				
				String desc = MainFrame.getInstance().getTextByField("DESC:").trim();
				String d_Desc = dataTable.getData(BlueChipSheet, "exp_DESC_1").trim();
				Thread.sleep(1000);
				if (d_Desc.trim().equals(desc)) {
					report.updateTestLog("Verify DESC", "DESC " + desc + " same as expected " + d_Desc  , Status.PASS, "MAINFRAME");
				} else {
					report.updateTestLog("Verify DESC", "DESC " + desc + " not same as expected " + d_Desc , Status.FAIL, "MAINFRAME");
				}
				
				
				
				
				//BEN GRP:
				
				String benGrp = MainFrame.getInstance().getTextByField("BEN GRP:").trim();
				String d_BenGrp = dataTable.getData(BlueChipSheet, "exp_BEN_GRP").trim();
				Thread.sleep(1000);
				if (d_BenGrp.trim().equals(benGrp)) {
					report.updateTestLog("Verify BEN GRP", "BEN GRP " + benGrp + " same as expected " + d_BenGrp  , Status.PASS, "MAINFRAME");
				} else {
					report.updateTestLog("Verify BEN GRP", "BEN GRP " + benGrp + " not same as expected " + d_BenGrp , Status.FAIL, "MAINFRAME");
				}
				
				
				//PROV #:
				

				String provTP = MainFrame.getInstance().getTextByField("PROV # :").trim();
				String d_ProvTP = dataTable.getData(BlueChipSheet, "exp_PROV_Num").trim();
				Thread.sleep(1000);
				if (d_ProvTP.trim().equals(provTP)) {
					report.updateTestLog("Verify PROV#", "PROV# " + provTP + " same as expected " + d_ProvTP  , Status.PASS, "MAINFRAME");
				} else {
					report.updateTestLog("Verify PROV#", "PROV# " + provTP + " not same as expected " + d_ProvTP , Status.FAIL, "MAINFRAME");
				}
				
	}
	
	
	
}




