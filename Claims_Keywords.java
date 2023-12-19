package CPARTS_Finance.businesscomponents;

import java.io.File;
import java.io.FileInputStream;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.hcsc.automation.framework.Status;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

import common.ActionComponents;
import common.Java_Utilities;
import common.Sync;
import supportlibraries.ReusableLibrary;
import supportlibraries.ScriptHelper;

public class Claims_Keywords extends ReusableLibrary {

	WebDriverWait wait = new WebDriverWait(driver.getWebDriver(), 10);
	ActionComponents AComp = new ActionComponents(scriptHelper);
	Sync sync = new Sync(scriptHelper);

	public Claims_Keywords(ScriptHelper scriptHelper) {
		super(scriptHelper);
		// TODO Auto-generated constructor stub
	}

	private String sTestDataSheetName = "EDI_Claims";

	static Java_Utilities JUtil = new Java_Utilities();
	// Variables
	public static BufferedWriter writer = null;
	static String fileName;
	File logFile;

	public static String SFTPHOST;
	public static String SFTPWORKINGDIR;
	public static String SFTPPASS;
//	public static String state;
	public static String SFTPUSER;
	
/*	String FirstName=AComp.getTempData("firstName");
	String LastName=AComp.getTempData("lastName");  
	String SubID = AComp.getTempData("subID");
	String DOB = AComp.getTempData("DOB");
	String DateOfService = dataTable.getData(sTestDataSheetName, "DateOfService");
	String Gender = AComp.getTempData("gender");
	String alphaPrefix = dataTable.getData(sTestDataSheetName, "alphaPrefix");
	String GroupNo = dataTable.getData(sTestDataSheetName, "GroupNo");
	String claimAmt = dataTable.getData(sTestDataSheetName, "claimAmt");*/
	//
	//Provider details
	
	String providerName=dataTable.getData(sTestDataSheetName,"providerName");
	String billingNPI=dataTable.getData(sTestDataSheetName,"billingNPI");
	String pAddress1=dataTable.getData(sTestDataSheetName,"pAddress1");
	String pCity=dataTable.getData(sTestDataSheetName,"pCity");
	String pState=dataTable.getData(sTestDataSheetName,"pState");
	String pZipcode=dataTable.getData(sTestDataSheetName,"pZipcode");
	String bProviderTaxID=dataTable.getData(sTestDataSheetName,"bProviderTaxID");
	String renderingProviderNPI=dataTable.getData(sTestDataSheetName,"renderingProviderNPI");
	String procedureCode1= dataTable.getData(sTestDataSheetName, "procedureCode1");
	String claimDiagnosisCode= dataTable.getData(sTestDataSheetName, "claimDiagnosisCode");
	String claimDiagnosisCode1=dataTable.getData(sTestDataSheetName, "claimDiagnosisCode1");
	String claimDiagnosisCode2=dataTable.getData(sTestDataSheetName, "claimDiagnosisCode2");
	String FirstName=dataTable.getData(sTestDataSheetName,"firstName");
	String LastName=dataTable.getData(sTestDataSheetName,"lastName"); 
	String SubID =dataTable.getData(sTestDataSheetName,"subID");
	String DOB = dataTable.getData(sTestDataSheetName,"DOB");
	String DateOfService = dataTable.getData(sTestDataSheetName, "DateOfService");
	String Gender = dataTable.getData(sTestDataSheetName, "gender");
	String alphaPrefix = dataTable.getData(sTestDataSheetName, "alphaPrefix");
	String GroupNo = dataTable.getData(sTestDataSheetName, "GroupNo");
	String claimAmt = dataTable.getData(sTestDataSheetName, "claimAmt");
	String state = dataTable.getData(sTestDataSheetName, "state");
	
	String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(Calendar.getInstance().getTime());
	String Claims_837_SFTP_Region = properties.getProperty("Claims_837_SFTP_Region");
	String EDI837ResultsPath = report.createResultsSubFolder("EDI837Files").getPath();

	public void sftpClaimRegionSetup() {

		switch (Claims_837_SFTP_Region) {

		case "UAT1":
			SFTPUSER = dataTable.getData("General_Data", "SFTPUserName");
			SFTPHOST = properties.getProperty("SFTHOST1_3");
			SFTPWORKINGDIR = properties.getProperty("SFTWORKINGDIR1");
			SFTPPASS = dataTable.getData("General_Data", "SFTPPassword1_3");
			break;
			
		case "UAT2":
			SFTPUSER = dataTable.getData("General_Data", "SFTPUserName");
			SFTPHOST = properties.getProperty("SFTHOST2");
			SFTPWORKINGDIR = properties.getProperty("SFTWORKINGDIR2");
			SFTPPASS = dataTable.getData("General_Data", "SFTPPassword2");
			break;

		case "UAT3":
			SFTPUSER = dataTable.getData("General_Data", "SFTPUserName");
			SFTPHOST = properties.getProperty("SFTHOST1_3");
			SFTPWORKINGDIR = properties.getProperty("SFTWORKINGDIR3");
			SFTPPASS = dataTable.getData("General_Data", "SFTPPassword1_3");
			break;
			
		case "UATNEW1":
			SFTPUSER=dataTable.getData("General_Data", "SFTPNewUser");
			SFTPHOST = properties.getProperty("SFTHOST4");
			SFTPWORKINGDIR = properties.getProperty("SFTWORKINGDIR4");
			SFTPPASS=AComp.getLANPassword();//Save your password in a file named LAN_Password.txt in C:\Automation_Password directory
			break;
			
		case "UATNEW2":
			SFTPUSER=dataTable.getData("General_Data", "SFTPNewUser");
			SFTPHOST = properties.getProperty("SFTHOST4");
			SFTPWORKINGDIR = properties.getProperty("SFTWORKINGDIR5");
			SFTPPASS=AComp.getLANPassword();//Save your password in a file named LAN_Password.txt in C:\Automation_Password directory
			break;
		
		case "UATNEW3":
			SFTPUSER=dataTable.getData("General_Data", "SFTPNewUser");
			SFTPHOST = properties.getProperty("SFTHOST4");
			SFTPWORKINGDIR = properties.getProperty("SFTWORKINGDIR6");
			SFTPPASS=AComp.getLANPassword();//Save your password in a file named LAN_Password.txt in C:\Automation_Password directory
			break;
		
		default:
			System.out.println("host null");
			break;

		}
	}

	public void sftpFileUpload() {
		String SFPORT = properties.getProperty("SFTPORT");
		int SFTPPORT = Integer.parseInt(SFPORT);
		Session session = null;
		Channel channel = null;
		ChannelSftp channelSftp = null;
		
		System.out.println("Preparing the host information for SFTP.");

		System.out.println(SFTPHOST);
		System.out.println(SFTPWORKINGDIR);
		
		try {
			JSch jsch = new JSch();
			session = jsch.getSession(SFTPUSER, SFTPHOST, SFTPPORT);
			session.setPassword(SFTPPASS);
			java.util.Properties config = new java.util.Properties();
			config.put("StrictHostKeyChecking", "no");
			config.put("PreferredAuthentications", "password");
			session.setConfig(config);
			session.connect();
			report.updateTestLog("Host connection", "is successful", Status.DONE);
			channel = session.openChannel("sftp");
			channel.connect();
			report.updateTestLog("SFTP channel opened and connection", "is successful", Status.DONE);
			channelSftp = (ChannelSftp) channel;
			channelSftp.cd(SFTPWORKINGDIR);
			File f = new File(fileName);
			channelSftp.put(new FileInputStream(f), f.getName());
			report.updateTestLog("File transfer to host", "is successful", Status.DONE);

		} catch (Exception e) {
			report.updateTestLog("Tranfering the response", "is unsuccessful" + stackTrace(e), Status.FAIL);

			e.printStackTrace();
		} finally {
			channelSftp.exit();
			report.updateTestLog("SFTP channel", "exited", Status.DONE);
			channel.disconnect();
			report.updateTestLog("Channel", "disconnected", Status.DONE);
			session.disconnect();
			report.updateTestLog("Host Session", "disconnected", Status.DONE);
		}

	}

	public String stackTrace(Exception e) {

		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		e.printStackTrace(pw);
		String sStackTrace = sw.toString(); // stack trace as a string
		System.out.println(sStackTrace);

		return sStackTrace;

	}
	public void eDI837_FileCreation() throws IOException {
       
		String state = dataTable.getData(sTestDataSheetName, "state");
		
		fileName = EDI837ResultsPath + "\\AIX_" + state + "_" + timeStamp + "_" + Claims_837_SFTP_Region + ""
				+ "_V27.DAT";
		System.out.println("EDI File Location: " + fileName);
		logFile = new File(fileName);
		writer = new BufferedWriter(new FileWriter(logFile));
		report.updateTestLog("Name of file is ", fileName, Status.DONE);
	}
	
	public void write_EDIFile_NM1() throws IOException{
		writer.write("ISA*00*          *00*          *ZZ*ECLAIMS        *ZZ*EFENM_CHIP     *220817*0704*!*00501*100086026*0*T*:~" +  "\r\n");
		writer.write("GS*HC*ECLAIMS*EFENM_CHIP*20220817*07043659*86026*X*005010X223A2~" +  "\r\n");
		writer.write("ST*837*86026451*005010X223A2~" +  "\r\n");
		writer.write("BHT*0019*00*66LDXK*20220816*1220*CH~" +  "\r\n");
		writer.write("NM1*41*2*Waystar (Zirmed)*****46*12686~" +  "\r\n");
		writer.write("PER*IC*EDI OPERATIONS*TE*8774947633*EX*7*EM*PRODUCTION@ZIRMED.COM~" +  "\r\n");
		writer.write("NM1*40*2*HCSC - NEW MEXICO*****46*00790CHIP~" +  "\r\n");
		writer.write("HL*1**20*1~" +  "\r\n");
		writer.write("PRV*BI*PXC*261QA1903X~" +  "\r\n");
		writer.write("NM1*85*2*"+providerName+"*****XX*"+billingNPI+"~" +  "\r\n");
		writer.write("N3*"+pAddress1+"~" +  "\r\n");
		writer.write("N4*"+pCity+"*"+pState+"*"+pZipcode+"~" +  "\r\n");
		writer.write("REF*EI*"+bProviderTaxID+"~" +  "\r\n");
		writer.write("PER*IC*ANNA JOHNSTON*TE*5055642300~" +  "\r\n");
		writer.write("HL*2*1*22*0~" +  "\r\n");
		writer.write("SBR*P*18*" + GroupNo + "******BL~" +  "\r\n");
		writer.write("NM1*IL*1*" + LastName + "*" + FirstName + "*"+"***MI*" + alphaPrefix + SubID + "~" +  "\r\n");
		writer.write("N3*24194 RD L 2D~" +  "\r\n");
		writer.write("N4*CORTEZ*CO*81321~" +  "\r\n");
		writer.write("DMG*D8*"+ DOB +"*" + Gender + "~" +  "\r\n");
		writer.write("NM1*PR*2*HCSC - NEW MEXICO*****PI*00790CHIP~" +  "\r\n");
		writer.write("REF*FY*888-349-3706~" +  "\r\n");
		writer.write("CLM*589771*" + claimAmt + "***83:A:1**A*Y*Y~" +  "\r\n");
		writer.write("DTP*434*RD8*" + DateOfService + "-" + DateOfService + "~" +  "\r\n");
		writer.write("CL1*3*1*01~" +  "\r\n");
		writer.write("REF*G1*NOT NEEDED~" +  "\r\n");
		writer.write("REF*D9*66LDXK~" +  "\r\n");
		writer.write("REF*EA*58977V1~" +  "\r\n");
		writer.write("HI*ABK:"+claimDiagnosisCode+"~" +  "\r\n");
		writer.write("NM1*71*1*BROADBENT, DPM*BRYAN*K***XX*"+renderingProviderNPI+"~" +  "\r\n");
		writer.write("NM1*72*1*BROADBENT, DPM*BRYAN*K***XX*"+renderingProviderNPI+"~" +  "\r\n");
		writer.write("LX*1~" +  "\r\n");
		writer.write("SV2*0490*HC:"+procedureCode1+":RT*" + claimAmt + "*UN*1~" +  "\r\n");
		writer.write("REF*6R*1~" +  "\r\n");
		writer.write("SE*33*86026451~" +  "\r\n");
		writer.write("GE*1*86026~" +  "\r\n");
		writer.write("IEA*1*100086026~" +  "\r\n");
		writer.flush();
		writer.close();
		report.updateTestLog("Fle update", "is successful", Status.DONE);


	}
	public void write_EDIFile_NM2() throws IOException{
		writer.write("ISA*00*          *00*          *ZZ*ECLAIMS        *ZZ*EFENM_CHIP     *220908*0703*|*00501*100070384*0*T*:~" +  "\r\n");
		writer.write("GS*HC*ECLAIMS*EFENM_CHIP*20220908*07030894*70384*X*005010X222A1~" +  "\r\n");
		writer.write("ST*837*70384015*005010X222A1~" +  "\r\n");
		writer.write("BHT*0019*00*JZS3Z8*20220908*2305*CH~" +  "\r\n");
		writer.write("NM1*41*2*HCSC*****46*1945~" +  "\r\n");
		writer.write("PER*IC*VARTIC AND SONS LLC*TE*8002824548~" +  "\r\n");
		writer.write("NM1*40*2*HCSC - NEW MEXICO*****46*00790CHIP~" +  "\r\n");
		writer.write("HL*1**20*1~" +  "\r\n");
		writer.write("NM1*85*2*"+providerName+"*****XX*"+billingNPI+"~" +  "\r\n");
		writer.write("N3*"+pAddress1+"~" +  "\r\n");
		writer.write("N4*"+pCity+"*"+pState+"*"+pZipcode+"~" +  "\r\n");
		writer.write("REF*EI*"+bProviderTaxID+"~" +  "\r\n");
		writer.write("HL*2*1*22*0~" +  "\r\n");
		writer.write("SBR*P*18*"+GroupNo+"******BL~" +  "\r\n");
		writer.write("NM1*IL*1*"+LastName+"*"+FirstName+"****MI*"+alphaPrefix+SubID+"~" +  "\r\n");
		writer.write("N3*119 N ARMIJO LANE~" +  "\r\n");
		writer.write("N4*"+pCity+"*"+pState+"*"+pZipcode+"~" +  "\r\n");
		//writer.write("N4*SANTA FE*NM*875016159~" +  "\r\n");
		writer.write("DMG*D8*"+DOB+"*"+Gender+"~" +  "\r\n");
		writer.write("NM1*PR*2*HCSC - NEW MEXICO*****PI*00790CHIP~" +  "\r\n");
		writer.write("CLM*TEST*"+claimAmt+"***11:B:1*Y*A*Y*Y*P~" +  "\r\n");
		writer.write("REF*D9*53ZX2W~" +  "\r\n");
		writer.write("HI*ABK:"+claimDiagnosisCode+"*ABF:"+claimDiagnosisCode1+"~" +  "\r\n");
		writer.write("NM1*82*1*RENDERING*PROVIDER****XX*"+renderingProviderNPI+"~" +  "\r\n");
		writer.write("LX*1~" +  "\r\n");
		writer.write("SV1*HC:"+procedureCode1+"*"+claimAmt+"*UN*1*19**1~" +  "\r\n");
		//writer.write("SV1*HC:90837*150*UN*1*19**1~" +  "\r\n");
		writer.write("DTP*472*RD8*"+DateOfService+"-"+DateOfService+"~" +  "\r\n");
		writer.write("REF*6R*1~" +  "\r\n");
		writer.write("LX*2~" +  "\r\n");
		writer.write("SV1*HC:"+procedureCode1+"*"+claimAmt+"*UN*1*19**1~" +  "\r\n");
		writer.write("DTP*472*RD8*"+DateOfService+"-"+DateOfService+"~" +  "\r\n");
		writer.write("REF*6R*2~" +  "\r\n");
		writer.write("SE*30*70384015~" +  "\r\n");
		writer.write("GE*1*70384~" +  "\r\n");
		writer.write("IEA*1*100070384~" +  "\r\n");
		writer.flush();
		writer.close();
		report.updateTestLog("Fle update", "is successful", Status.DONE);
	}

			
		public void write_EDIFile_R_IL1() throws IOException {

			writer.write("ISA*00*          *00*          *ZZ*ECLAIMS        *ZZ*EFEIL_CHIP     *211216*0703*|*00501*100070384*0*T*:~"+ "\r\n");
			writer.write("GS*HC*ECLAIMS*EFEIL_CHIP*20211216*07030894*70384*X*005010X223A2~" + "\r\n");
			writer.write("ST*837*70384015*005010X223A2~" + "\r\n");
			writer.write("BHT*0019*00*59WFDL*20211216*2305*CH~" + "\r\n");
			writer.write("NM1*41*2*HCSC*****46*1945~" + "\r\n");
			writer.write("PER*IC*AVAILITY LLC*TE*8002824548*FX*8002824548*EM*SUPPORT@AVAILITY.COM~" + "\r\n");
			writer.write("NM1*40*2*HCSC - ILLINOIS*****46*00621CHIP~" + "\r\n");
			writer.write("HL*1**20*1~" + "\r\n");
			writer.write("PRV*BI*PXC*193400000X~" + "\r\n");
			writer.write("NM1*85*2*"+providerName+"*****XX*"+billingNPI+"~" + "\r\n");
			writer.write("N3*"+pAddress1+"~" + "\r\n");
			writer.write("N4*"+pCity+"*"+pState+"*"+pZipcode+"~" + "\r\n");
			writer.write("REF*EI*"+bProviderTaxID+"~" + "\r\n");
			writer.write("HL*2*1*22*0~" + "\r\n");
			writer.write("SBR*P*18*" + GroupNo + "******BL~" + "\r\n");
			writer.write("NM1*IL*1*" + LastName + "*" + FirstName + "*" + "***MI*" + alphaPrefix + SubID + "~" + "\r\n");
			writer.write("N3*1783 E UNIVERSITY AVE~" + "\r\n");
			writer.write("N4*VERONA*IL*60479~" + "\r\n");
			writer.write("DMG*D8*" + DOB + "*" + Gender + "~" + "\r\n");
			writer.write("NM1*PR*2*HCSC - ILLINOIS*****PI*00621CHIP~" + "\r\n");
			writer.write("CLM*FAC01*" + claimAmt + "***13:A:1**A*Y*Y~" + "\r\n");
			writer.write("DTP*434*RD8*20220114-20220114~" + "\r\n");
			writer.write("CL1*9*9*01~" + "\r\n");
			writer.write("REF*D9*59WFDL~" + "\r\n");
			writer.write("HI*ABK:"+claimDiagnosisCode+"~" + "\r\n");
			writer.write("NM1*71*1*Provider*****XX*"+renderingProviderNPI+"~" + "\r\n");
			writer.write("PRV*AT*PXC*193400000X~" + "\r\n");
			writer.write("LX*1~" + "\r\n");
			writer.write("SV2*"+procedureCode1+"**" + claimAmt + "*UN*1~" + "\r\n");
			writer.write("DTP*472*RD8*" + DateOfService + "-" + DateOfService + "~" + "\r\n");
			writer.write("REF*6R*1~" + "\r\n");
			writer.write("SE*30*70384015~" + "\r\n");
			writer.write("GE*1*70384~" + "\r\n");
			writer.write("IEA*1*100070384~" + "\r\n");
			writer.flush();
			writer.close();
			report.updateTestLog("Fle update", "is successful", Status.DONE);
		}

		public void write_EDIFile_G_MT1() throws IOException {
//String claimDiagnosisCode1 = null;
		writer.write("ISA*00*          *00*          *ZZ*ECLAIMS        *ZZ*EFEMT_CHIP     *220503*0703*!*00501*100084997*0*T*:~" +  "\r\n");
		writer.write("GS*HC*ECLAIMS*EFEMT_CHIP*20220503*07035920*84997*X*005010X222A1~" +  "\r\n");
		writer.write("ST*837*84997008*005010X222A1~" +  "\r\n");
		writer.write("BHT*0019*00*65474Y*20220430*0010*CH~" +  "\r\n");
		writer.write("NM1*41*2*Realmed RCM - AVHCSC*****46*52169~" +  "\r\n");
		writer.write("PER*IC*REALMED*TE*8779278000~" +  "\r\n");
		writer.write("NM1*40*2*HCSC - MONTANA*****46*00751CHIP~" +  "\r\n");
		writer.write("HL*1**20*1~" +  "\r\n");
		writer.write("PRV*BI*PXC*193400000X~" +  "\r\n");
		writer.write("NM1*85*2*"+providerName+"*****XX*"+billingNPI+"~" +  "\r\n");
		writer.write("N3*"+pAddress1+"~" +  "\r\n");
		writer.write("N4*"+pCity+"*"+pState+"*"+pZipcode+"~" +  "\r\n");
		writer.write("REF*EI*"+bProviderTaxID+"~" +  "\r\n");
		writer.write("PER*IC*BILLING OFFICE*TE*4065357711~" +  "\r\n");
		writer.write("HL*2*1*22*0~" +  "\r\n");
		writer.write("SBR*P*18*" + GroupNo + "0000******BL~" + "\r\n");
		writer.write("NM1*IL*1*" + LastName + "*" + FirstName + "*" + "***MI*" + alphaPrefix + SubID + "~" + "\r\n");
//		writer.write("NM1*IL*1*MURPHY*TERRY****MI*YDD836912739~" +  "\r\n");
		writer.write("N3*111 PARK STREET~" +  "\r\n");
		writer.write("N4*LEWISTOWN*MT*594572222~" +  "\r\n");
		writer.write("DMG*D8*" + DOB + "*" + Gender + "~" + "\r\n");
		writer.write("NM1*PR*2*HCSC - MONTANA*****PI*00751CHIP~" +  "\r\n");
		writer.write("N3*PO BOX 7982~" +  "\r\n");
		writer.write("N4*HELENA*MT*59604~" +  "\r\n");
		writer.write("CLM*32223711*" + claimAmt + "***22:B:1*Y*A*Y*Y~" + "\r\n");
		writer.write("REF*D9*65474Y~" +  "\r\n");
		writer.write("REF*EA*8729219~" +  "\r\n");
		writer.write("HI*ABK:"+claimDiagnosisCode+"*ABF:"+claimDiagnosisCode1+"~" +  "\r\n");
		writer.write("NM1*DN*1*COMES*ANNETTE****XX*1346247087~" +  "\r\n");
		writer.write("NM1*P3*1*COMES*ANNETTE****XX*1346247087~" +  "\r\n");
		//writer.write("NM1*82*1*RENDERING*PROVIDER****XX*"+renderingProviderNPI+"~ "+"\r\n");
		writer.write("NM1*82*1*SPEED*SHANDA*E***XX*1619288487~" +  "\r\n");
		writer.write("PRV*PE*PXC*367H00000X~" +  "\r\n");
		writer.write("LX*1~" +  "\r\n");
		writer.write("SV1*HC:"+procedureCode1+":QZ*" + claimAmt + "*MJ*51***1:2~" + "\r\n");
		writer.write("DTP*472*RD8*" + DateOfService + "-" + DateOfService + "~" + "\r\n");
		writer.write("REF*6R*8255752037~" +  "\r\n");
		writer.write("SE*34*84997008~" +  "\r\n");
		writer.write("GE*1*84997~" +  "\r\n");
		writer.write("IEA*1*100084997~" +  "\r\n");
		writer.flush();
		writer.close();
		report.updateTestLog("Fle update", "is successful", Status.DONE);
	}
		public void write_EDIFile_G_IL1() throws IOException {

		writer.write("ISA*00*          *00*          *ZZ*ECLAIMS        *ZZ*EFEIL_CHIP     *220503*0703*!*00501*100084995*0*T*:~" +  "\r\n");
		writer.write("GS*HC*ECLAIMS*EFEIL_CHIP*20220503*07035739*84995*X*005010X222A1~" +  "\r\n");
		writer.write("ST*837*84995158*005010X222A1~" +  "\r\n");
		writer.write("BHT*0019*00*6548F5*20220430*0035*CH~" +  "\r\n");
		writer.write("NM1*41*2*PHICure Next, LLC*****46*364196~" +  "\r\n");
		writer.write("PER*IC*WAYNE KOCH*TE*8885016216~" +  "\r\n");
		writer.write("NM1*40*2*HCSC - ILLINOIS*****46*00621CHIP~" +  "\r\n");
		writer.write("HL*1**20*1~" +  "\r\n");
		writer.write("NM1*85*2*"+providerName+"*****XX*"+billingNPI+"~" +  "\r\n");
		writer.write("N3*"+pAddress1+"~" +  "\r\n");
		writer.write("N4*"+pCity+"*"+pState+"*"+pZipcode+"~" +  "\r\n");
		writer.write("REF*EI*"+bProviderTaxID+"~" +  "\r\n");
		writer.write("PER*IC*JOSE ORTIZ*TE*2095504610~" +  "\r\n");
		writer.write("HL*2*1*22*0~" +  "\r\n");
		writer.write("SBR*P*18*" + GroupNo + "******CI~" + "\r\n");
		writer.write("NM1*IL*1*" + LastName + "*" + FirstName + "*" + "***MI*" + alphaPrefix + SubID + "~" + "\r\n");
//		writer.write("NM1*IL*1*DIAZ*MARIA****MI*ZPK840167903~" +  "\r\n");
		writer.write("N3*BAD ADDR~" +  "\r\n");
		writer.write("N4*CHICAGO*IL*60639~" +  "\r\n");
		writer.write("DMG*D8*" + DOB + "*" + Gender + "~" + "\r\n");
		writer.write("NM1*PR*2*HCSC - ILLINOIS*****PI*00621CHIP~" +  "\r\n");
		writer.write("CLM*I1900029675001*" + claimAmt + "***23:B:1*Y*A*Y*Y*P~" + "\r\n");
		writer.write("REF*D9*6548F5~" +  "\r\n");
		writer.write("HI*ABK:"+claimDiagnosisCode+"*ABF:"+claimDiagnosisCode1+"~" +  "\r\n");
		writer.write("NM1*DN*1*LENSTROM*AMBER****XX*1881994432~" +  "\r\n");
		writer.write("NM1*82*1*OLSEN*MICHAEL****XX*"+renderingProviderNPI+"~" +  "\r\n");
		writer.write("PRV*PE*PXC*207P00000X~" +  "\r\n");
		writer.write("NM1*77*2*AMITA HLTH ST MARY EIZBTH MC*****XX*1922177229~" +  "\r\n");
		writer.write("N3*2233 W DIVISION ST~" +  "\r\n");
		writer.write("N4*CHICAGO*IL*606228150~" +  "\r\n");
		writer.write("LX*1~" +  "\r\n");
		writer.write("SV1*HC:"+procedureCode1+"*" + claimAmt + "*UN*1***1:2**Y~" + "\r\n");
		writer.write("DTP*472*D8*" + DateOfService + "~" + "\r\n");
		writer.write("REF*6R*00001~" +  "\r\n");
		writer.write("SE*32*84995158~" +  "\r\n");
		writer.write("GE*1*84995~" +  "\r\n");
		writer.write("IEA*1*100084995~" +  "\r\n");
		writer.flush();
		writer.close();
		report.updateTestLog("Fle update", "is successful", Status.DONE);
	}
	
		
	public void write_EDIFile_ERS_TRS_TX_Provider() throws IOException{
		writer.write("ISA*00*          *00*          *ZZ*ECLAIMS        *ZZ*EFETX_CHIP     *220825*0703*|*00501*100070384*0*T*:" +  "\r\n");
		writer.write("GS*HC*ECLAIMS*EFETX_CHIP*20220825*07030894*70384*X*005010X222A1" +  "\r\n");
		writer.write("ST*837*70384015*005010X222A1" +  "\r\n");
		writer.write("BHT*0019*00*JZS3Z8*20220825*2305*CH" +  "\r\n");
		writer.write("NM1*41*2*HCSC*****46*1945" +  "\r\n");
		writer.write("PER*IC*VARTIC AND SONS LLC*TE*8002824548" +  "\r\n");
		writer.write("NM1*40*2*HCSC - TEXAS*****46*84980CHIP" +  "\r\n");
		writer.write("HL*1**20*1" +  "\r\n");
		writer.write("NM1*85*2*"+providerName+"*****XX*"+billingNPI +  "\r\n");
		writer.write("N3*"+pAddress1+ "\r\n");
		writer.write("N4*"+pCity+"*"+pState+"*"+pZipcode+"" +  "\r\n");
		writer.write("REF*EI*"+bProviderTaxID+  "\r\n");
		writer.write("HL*2*1*22*0" +  "\r\n");
		writer.write("SBR*P*18*"+GroupNo+"******BL" +  "\r\n");
		writer.write("NM1*IL*1*" + LastName + "*"+FirstName+"****MI*"+ alphaPrefix + SubID +  "\r\n");
		writer.write("N3*3716 BLUE SKY HOLLY" +  "\r\n");
		writer.write("N4*SAN ANTONIO*TX*782592356" +  "\r\n");
		writer.write("DMG*D8*"+DOB+"*"+Gender+  "\r\n");
		writer.write("NM1*PR*2*HCSC - TEXAS*****PI*84980CHIP" +  "\r\n");
		writer.write("CLM*TEST*"+claimAmt+"***11:B:1*Y*A*Y*Y*P" +  "\r\n");
		writer.write("REF*D9*53ZX2W" +  "\r\n");
		writer.write("HI*ABK:"+claimDiagnosisCode+"\r\n");
		writer.write("NM1*82*1*RENDERING*PROVIDER****XX*"+renderingProviderNPI+  "\r\n");
		writer.write("LX*1" +  "\r\n");
		writer.write("SV1*HC:"+procedureCode1+"*"+claimAmt+"*UN*1*19**1" +  "\r\n");
		writer.write("DTP*472*RD8*"+DateOfService+"-"+DateOfService+ "\r\n");
		writer.write("REF*6R*1" +  "\r\n");
		writer.write("SE*26*70384015" +  "\r\n");
		writer.write("GE*1*70384" +  "\r\n");
		writer.write("IEA*1*100070384" +  "\r\n");
		writer.flush();
		writer.close();
		report.updateTestLog("Fle update", "is successful", Status.DONE);

	}
	public void write_EDIFile_ERS_TRS_TX() throws IOException {
		
		writer.write("ISA*00*          *00*          *ZZ*ECLAIMS        *ZZ*EFETX_CHIP     *220628*0703*|*00501*100070384*0*T*:~" +  "\r\n");
		writer.write("GS*HC*ECLAIMS*EFETX_CHIP*20220628*07030894*70384*X*005010X222A1~" +  "\r\n");
		writer.write("ST*837*70384015*005010X222A1~" +  "\r\n");
		writer.write("BHT*0019*00*JZS3Z8*20220628*2305*CH~" +  "\r\n");
		writer.write("NM1*41*2*HCSC*****46*1945~" +  "\r\n");
		writer.write("PER*IC*VARTIC AND SONS LLC*TE*8002824548~" +  "\r\n");
		writer.write("NM1*40*2*HCSC - TEXAS*****46*84980CHIP~" +  "\r\n");
		writer.write("HL*1**20*1~" +  "\r\n");
		writer.write("NM1*85*2*H016549801*****XX*1629117999~" +  "\r\n");
		writer.write("N3*300 E Randolph~" +  "\r\n");
		writer.write("N4*City*TX*123456789~" +  "\r\n");
		writer.write("REF*EI*654986431~" +  "\r\n");
		writer.write("HL*2*1*22*0~" +  "\r\n");
		writer.write("SBR*P*18*" + GroupNo + "******BL~" + "\r\n");
//		writer.write("NM1*IL*1*AADLANDTDM*AIDTTDM****MI*JEA9727429~" +  "\r\n");
		writer.write("NM1*IL*1*" + LastName + "*" + FirstName + "*" + "***MI*" + alphaPrefix + SubID + "~" + "\r\n");
		writer.write("N3*300 E Randolph~" +  "\r\n");
		writer.write("N4*City*TX*123456789~" +  "\r\n");
		writer.write("DMG*D8*" + DOB + "*" + Gender + "~" + "\r\n");
		writer.write("NM1*PR*2*HCSC - TEXAS*****PI*84980CHIP~" +  "\r\n");
		writer.write("CLM*TDM_BEV_24*" + claimAmt + "***11:B:1*Y*A*Y*Y*P~" + "\r\n");
		writer.write("REF*D9*53ZX2W~" +  "\r\n");
		writer.write("HI*ABK:R69~" +  "\r\n");
		writer.write("NM1*82*1*RENDERING*PROVIDER****XX*1629117999~" +  "\r\n");
		writer.write("LX*1~" +  "\r\n");
		writer.write("SV1*HC:99215*" + claimAmt + "*UN*1*11**1~" + "\r\n");
		writer.write("DTP*472*RD8*" + DateOfService + "-" + DateOfService + "~" + "\r\n");
		writer.write("REF*6R*1~" +  "\r\n");
		writer.write("SE*26*70384015~" +  "\r\n");
		writer.write("GE*1*70384~" +  "\r\n");
		writer.write("IEA*1*100070384~" +  "\r\n");
		writer.flush();
		writer.close();
		report.updateTestLog("Fle update", "is successful", Status.DONE);
	}
	public void write_EDIFile_MT() throws IOException{
		writer.write("ISA*00*          *00*          *ZZ*ECLAIMS        *ZZ*EFEMT_CHIP     *220817*0704*!*00501*100086030*0*T*:~" +  "\r\n");
		writer.write("GS*HC*ECLAIMS*EFEMT_CHIP*20220817*07043991*86030*X*005010X222A1~" +  "\r\n");
		writer.write("ST*837*86030010*005010X222A1~" +  "\r\n");
		writer.write("BHT*0019*00*66K78Z*20220816*0015*CH~" +  "\r\n");
		writer.write("NM1*41*2*Eligible API*****46*410952~" +  "\r\n");
		writer.write("PER*IC*KATELYN GLEASON*TE*1234567890~" +  "\r\n");
		writer.write("NM1*40*2*HCSC - MONTANA*****46*00751CHIP~" +  "\r\n");
		writer.write("HL*1**20*1~" +  "\r\n");
		writer.write("PRV*BI*PXC*193200000X~" +  "\r\n");
		writer.write("NM1*85*2*"+providerName+"*****XX*"+billingNPI+"~" +  "\r\n");
		writer.write("N3*"+pAddress1+"~" +  "\r\n");
		writer.write("N4*"+pCity+"*"+pState+"*"+pZipcode+"~" +  "\r\n");
		writer.write("REF*EI*"+bProviderTaxID+"~" +  "\r\n");
		writer.write("PER*IC*SWEETGRASS PSYCHOLOGICAL SERVICES*TE*4062985728~" +  "\r\n");
		writer.write("HL*2*1*22*0~" +  "\r\n");
		writer.write("SBR*P*18*"+GroupNo+"******CI~" +  "\r\n");
		writer.write("NM1*IL*1*"+LastName+"*"+FirstName+"****MI*"+alphaPrefix+SubID+"~" +  "\r\n");
		writer.write("N3*158 JACKSON HILL TRAIL~" +  "\r\n");
		writer.write("N4*COLUMBIA FALLS*MT*599128654~" +  "\r\n");
		writer.write("DMG*D8*"+DOB+"*"+Gender+"~" +  "\r\n");
		writer.write("NM1*PR*2*BLUE CROSS BLUE SHIELD OF*****PI*00751CHIP~" +  "\r\n");
		writer.write("CLM*9QGMS1FU81EQFW*"+claimAmt+"***11:B:1*Y*A*Y*Y~" +  "\r\n");
		writer.write("REF*D9*66K78Z~" +  "\r\n");
		writer.write("HI*ABK:"+claimDiagnosisCode+"~" +  "\r\n");
		writer.write("NM1*82*1*DAVIS-TIMMS*COLLEEN****XX*"+renderingProviderNPI+"~" +  "\r\n");
		writer.write("LX*1~" +  "\r\n");
		writer.write("SV1*HC:"+procedureCode1+"*"+claimAmt+"*UN*1***1~" +  "\r\n");
		writer.write("DTP*472*D8*"+DateOfService+"~" +  "\r\n");
		writer.write("REF*6R*1~" +  "\r\n");
		writer.write("LX*2~" +  "\r\n");
		writer.write("SV1*HC:"+procedureCode1+"*"+claimAmt+"*UN*1***1~" +  "\r\n");
		writer.write("DTP*472*D8*"+DateOfService+"~" +  "\r\n");
		writer.write("REF*6R*2~" +  "\r\n");
		writer.write("SE*32*86030010~" +  "\r\n");
		writer.write("GE*1*86030~" +  "\r\n");
		writer.write("IEA*1*100086030~" +  "\r\n");
		writer.flush();
		writer.close();
		report.updateTestLog("Fle update", "is successful", Status.DONE);

	}
	
	public void write_EDIFile_G_OK1() throws IOException {
		
		writer.write("ISA*00*          *00*          *ZZ*ECLAIMS        *ZZ*EFEOK_CHIP     *210716*0418*|*00501*103204998*0*P*:~" +  "\r\n");
		writer.write("GS*HC*ECLAIMS*EFEOK_CHIP*20210717*04180278*3204998*X*005010X222A1~" +  "\r\n");
		writer.write("ST*837*204998399*005010X222A1~" +  "\r\n");
		writer.write("BHT*0019*00*PY79CR*20210717*0320*CH~" +  "\r\n");
		writer.write("NM1*41*2*AthenaHealth*****46*6628~" +  "\r\n");
		writer.write("PER*IC*CLAIMSEDI*TE*8009815084*EM*ECSTBUSINESS@ATHENAHEALTH.COM~" +  "\r\n");
		writer.write("NM1*40*2*HCSC - OKLAHOMA*****46*00840CHIP~" +  "\r\n");
		writer.write("HL*1**20*1~" +  "\r\n");
		writer.write("NM1*85*2*"+providerName+"*****XX*"+billingNPI+"~" +  "\r\n");
		writer.write("N3*"+pAddress1+"~" +  "\r\n");
		writer.write("N4*"+pCity+"*"+pState+"*"+pZipcode+"~" +  "\r\n");
		writer.write("REF*EI*"+bProviderTaxID+"~" +  "\r\n");
		writer.write("PER*IC*OKLAHOMA CITY GYNECOLOGY AND OBSTETRICS LLC*TE*4059361008~" +  "\r\n");
		writer.write("NM1*87*2~" +  "\r\n");
		writer.write("N3*PO BOX 8385~" +  "\r\n");
		writer.write("N4*BELFAST*ME*049158300~" +  "\r\n");
		writer.write("HL*2*1*22*0~" +  "\r\n");
		writer.write("SBR*P*18*" + GroupNo + "******BL~" + "\r\n");
//		writer.write("NM1*IL*1*BLACK*TAREN*M***MI*YUP927819212~" +  "\r\n");   823743308
		writer.write("NM1*IL*1*" + LastName + "*" + FirstName + "*" + "***MI*" + alphaPrefix + SubID + "~" + "\r\n");
		writer.write("N3*15929 LOUTAIN CT~" +  "\r\n");
		writer.write("N4*EDMOND*OK*73013~" +  "\r\n");
		writer.write("DMG*D8*" + DOB + "*" + Gender + "~" + "\r\n");
		writer.write("REF*SY*445888806~" +  "\r\n");
		writer.write("NM1*PR*2*HCSC - OKLAHOMA*****PI*00840CHIP~" +  "\r\n");
		writer.write("N3*PO BOX 3283~" +  "\r\n");
		writer.write("N4*TULSA*OK*741023283~" +  "\r\n");
		writer.write("CLM*703386V3062*176***11:B:1*Y*A*Y*Y~" +  "\r\n");
//		writer.write("DTP*431*D8*20220102~" +  "\r\n");
		writer.write("DTP*431*D8*" + DateOfService + "~" + "\r\n");
		writer.write("REF*D9*PY79CR~" +  "\r\n");
		writer.write("HI*ABK:"+claimDiagnosisCode+"*ABF:"+claimDiagnosisCode1+"*ABF:"+claimDiagnosisCode2+"~" +  "\r\n");
		writer.write("NM1*82*1*WEBBER*REGINA****XX*"+renderingProviderNPI+"~" +  "\r\n");
		writer.write("PRV*PE*PXC*207V00000X~" +  "\r\n");
		writer.write("LX*1~" +  "\r\n");
//		writer.write("SV1*HC:99395*161*UN*1***1:2:3~" +  "\r\n");
		writer.write("SV1*HC:99395*" + claimAmt + "*UN*1***1:2:3~" + "\r\n");
//		writer.write("DTP*472*D8*20220102~" +  "\r\n");
		writer.write("DTP*472*D8*" + DateOfService + "~" + "\r\n");
		writer.write("REF*6R*4150817P3062B60872~" +  "\r\n");
		writer.write("LX*2~" +  "\r\n");
//		writer.write("SV1*HC:99000*15*UN*1***1:2~" +  "\r\n");
		writer.write("SV1*HC:"+procedureCode1+"*" + claimAmt + "*UN*1***1:2~" + "\r\n");
//		writer.write("DTP*472*D8*20220102~" +  "\r\n");
		writer.write("DTP*472*D8*" + DateOfService + "~" + "\r\n");
		writer.write("REF*6R*4150818P3062B60872~" +  "\r\n");
		writer.write("SE*39*204998399~" +  "\r\n");
		writer.write("GE*1*3204998~" +  "\r\n");
		writer.write("IEA*1*103204998~" +  "\r\n");
		writer.flush();
		writer.close();
		report.updateTestLog("Fle update", "is successful", Status.DONE);
			
}
	public void write_EDIFile_R_IL() throws IOException {

		writer.write("ISA*00*          *00*          *ZZ*ECLAIMS        *ZZ*EFEIL_CHIP     *211216*0703*|*00501*100070384*0*T*:~"+ "\r\n");
		writer.write("GS*HC*ECLAIMS*EFEIL_CHIP*20211216*07030894*70384*X*005010X223A2~" + "\r\n");
		writer.write("ST*837*70384015*005010X223A2~" + "\r\n");
		writer.write("BHT*0019*00*59WFDL*20211216*2305*CH~" + "\r\n");
		writer.write("NM1*41*2*HCSC*****46*1945~" + "\r\n");
		writer.write("PER*IC*AVAILITY LLC*TE*8002824548*FX*8002824548*EM*SUPPORT@AVAILITY.COM~" + "\r\n");
		writer.write("NM1*40*2*HCSC - ILLINOIS*****46*00621CHIP~" + "\r\n");
		writer.write("HL*1**20*1~" + "\r\n");
		writer.write("PRV*BI*PXC*193400000X~" + "\r\n");
		writer.write("NM1*85*2*Provider*****XX*1053367375~" + "\r\n");
		writer.write("N3*300 E Randolph~" + "\r\n");
		writer.write("N4*City*IL*123456789~" + "\r\n");
		writer.write("REF*EI*362167060~" + "\r\n");
		writer.write("HL*2*1*22*0~" + "\r\n");
		writer.write("SBR*P*18*" + GroupNo + "******BL~" + "\r\n");
		writer.write("NM1*IL*1*" + LastName + "*" + FirstName + "*" + "***MI*" + alphaPrefix + SubID + "~" + "\r\n");
		writer.write("N3*1783 E UNIVERSITY AVE~" + "\r\n");
		writer.write("N4*VERONA*IL*60479~" + "\r\n");
		writer.write("DMG*D8*" + DOB + "*" + Gender + "~" + "\r\n");
		writer.write("NM1*PR*2*HCSC - ILLINOIS*****PI*00621CHIP~" + "\r\n");
		writer.write("CLM*FAC01*" + claimAmt + "***13:A:1**A*Y*Y~" + "\r\n");
		writer.write("DTP*434*RD8*20220114-20220114~" + "\r\n");
		writer.write("CL1*9*9*01~" + "\r\n");
		writer.write("REF*D9*59WFDL~" + "\r\n");
		writer.write("HI*ABK:L255~" + "\r\n");
		writer.write("NM1*71*1*Provider*****XX*1053367375~" + "\r\n");
		writer.write("PRV*AT*PXC*193400000X~" + "\r\n");
		writer.write("LX*1~" + "\r\n");
		writer.write("SV2*0450**" + claimAmt + "*UN*1~" + "\r\n");
		writer.write("DTP*472*RD8*" + DateOfService + "-" + DateOfService + "~" + "\r\n");
		writer.write("REF*6R*1~" + "\r\n");
		writer.write("SE*30*70384015~" + "\r\n");
		writer.write("GE*1*70384~" + "\r\n");
		writer.write("IEA*1*100070384~" + "\r\n");
		writer.flush();
		writer.close();
		report.updateTestLog("Fle update", "is successful", Status.DONE);
	}
	
	public void write_EDIFile_G_MT() throws IOException {

		writer.write("ISA*00*          *00*          *ZZ*ECLAIMS        *ZZ*EFEMT_CHIP     *220503*0703*!*00501*100084997*0*T*:~" +  "\r\n");
		writer.write("GS*HC*ECLAIMS*EFEMT_CHIP*20220503*07035920*84997*X*005010X222A1~" +  "\r\n");
		writer.write("ST*837*84997008*005010X222A1~" +  "\r\n");
		writer.write("BHT*0019*00*65474Y*20220430*0010*CH~" +  "\r\n");
		writer.write("NM1*41*2*Realmed RCM - AVHCSC*****46*52169~" +  "\r\n");
		writer.write("PER*IC*REALMED*TE*8779278000~" +  "\r\n");
		writer.write("NM1*40*2*HCSC - MONTANA*****46*00751CHIP~" +  "\r\n");
		writer.write("HL*1**20*1~" +  "\r\n");
		writer.write("PRV*BI*PXC*193400000X~" +  "\r\n");
		writer.write("NM1*85*2*CENTRAL MONTANA MEDICAL CENTER*****XX*1336252758~" +  "\r\n");
		writer.write("N3*408 WENDELL AVE~" +  "\r\n");
		writer.write("N4*LEWISTOWN*MT*594572261~" +  "\r\n");
		writer.write("REF*EI*237169043~" +  "\r\n");
		writer.write("PER*IC*BILLING OFFICE*TE*4065357711~" +  "\r\n");
		writer.write("HL*2*1*22*0~" +  "\r\n");
		writer.write("SBR*P*18*" + GroupNo + "0000******BL~" + "\r\n");
		writer.write("NM1*IL*1*" + LastName + "*" + FirstName + "*" + "***MI*" + alphaPrefix + SubID + "~" + "\r\n");
//		writer.write("NM1*IL*1*MURPHY*TERRY****MI*YDD836912739~" +  "\r\n");
		writer.write("N3*111 PARK STREET~" +  "\r\n");
		writer.write("N4*LEWISTOWN*MT*594572222~" +  "\r\n");
		writer.write("DMG*D8*" + DOB + "*" + Gender + "~" + "\r\n");
		writer.write("NM1*PR*2*HCSC - MONTANA*****PI*00751CHIP~" +  "\r\n");
		writer.write("N3*PO BOX 7982~" +  "\r\n");
		writer.write("N4*HELENA*MT*59604~" +  "\r\n");
		writer.write("CLM*32223711*" + claimAmt + "***22:B:1*Y*A*Y*Y~" + "\r\n");
		writer.write("REF*D9*65474Y~" +  "\r\n");
		writer.write("REF*EA*8729219~" +  "\r\n");
		writer.write("HI*ABK:Z1211*ABF:K5730~" +  "\r\n");
		writer.write("NM1*DN*1*COMES*ANNETTE****XX*1346247087~" +  "\r\n");
		writer.write("NM1*P3*1*COMES*ANNETTE****XX*1346247087~" +  "\r\n");
		writer.write("NM1*82*1*SPEED*SHANDA*E***XX*1619288487~" +  "\r\n");
		writer.write("PRV*PE*PXC*367H00000X~" +  "\r\n");
		writer.write("LX*1~" +  "\r\n");
		writer.write("SV1*HC:00812:QZ*" + claimAmt + "*MJ*51***1:2~" + "\r\n");
		writer.write("DTP*472*RD8*" + DateOfService + "-" + DateOfService + "~" + "\r\n");
		writer.write("REF*6R*8255752037~" +  "\r\n");
		writer.write("SE*34*84997008~" +  "\r\n");
		writer.write("GE*1*84997~" +  "\r\n");
		writer.write("IEA*1*100084997~" +  "\r\n");
		writer.flush();
		writer.close();
		report.updateTestLog("Fle update", "is successful", Status.DONE);
	}
	
	public void write_EDIFile_G_IL() throws IOException {

			writer.write("ISA*00*          *00*          *ZZ*ECLAIMS        *ZZ*EFEIL_CHIP     *220503*0703*!*00501*100084995*0*T*:~" +  "\r\n");
			writer.write("GS*HC*ECLAIMS*EFEIL_CHIP*20220503*07035739*84995*X*005010X222A1~" +  "\r\n");
			writer.write("ST*837*84995158*005010X222A1~" +  "\r\n");
			writer.write("BHT*0019*00*6548F5*20220430*0035*CH~" +  "\r\n");
			writer.write("NM1*41*2*PHICure Next, LLC*****46*364196~" +  "\r\n");
			writer.write("PER*IC*WAYNE KOCH*TE*8885016216~" +  "\r\n");
			writer.write("NM1*40*2*HCSC - ILLINOIS*****46*00621CHIP~" +  "\r\n");
			writer.write("HL*1**20*1~" +  "\r\n");
			writer.write("NM1*85*2*CEP AMERICA IL LLP*****XX*1851849095~" +  "\r\n");
			writer.write("N3*1601 CUMMINS DR STE D~" +  "\r\n");
			writer.write("N4*MODESTO*CA*953586411~" +  "\r\n");
			writer.write("REF*EI*263711283~" +  "\r\n");
			writer.write("PER*IC*JOSE ORTIZ*TE*2095504610~" +  "\r\n");
			writer.write("HL*2*1*22*0~" +  "\r\n");
			writer.write("SBR*P*18*" + GroupNo + "******CI~" + "\r\n");
			writer.write("NM1*IL*1*" + LastName + "*" + FirstName + "*" + "***MI*" + alphaPrefix + SubID + "~" + "\r\n");
	//		writer.write("NM1*IL*1*DIAZ*MARIA****MI*ZPK840167903~" +  "\r\n");
			writer.write("N3*BAD ADDR~" +  "\r\n");
			writer.write("N4*CHICAGO*IL*60639~" +  "\r\n");
			writer.write("DMG*D8*" + DOB + "*" + Gender + "~" + "\r\n");
			writer.write("NM1*PR*2*HCSC - ILLINOIS*****PI*00621CHIP~" +  "\r\n");
			writer.write("CLM*I1900029675001*" + claimAmt + "***23:B:1*Y*A*Y*Y*P~" + "\r\n");
			writer.write("REF*D9*6548F5~" +  "\r\n");
			writer.write("HI*ABK:N750*ABF:N390~" +  "\r\n");
			writer.write("NM1*DN*1*LENSTROM*AMBER****XX*1881994432~" +  "\r\n");
			writer.write("NM1*82*1*OLSEN*MICHAEL****XX*1144423526~" +  "\r\n");
			writer.write("PRV*PE*PXC*207P00000X~" +  "\r\n");
			writer.write("NM1*77*2*AMITA HLTH ST MARY EIZBTH MC*****XX*1922177229~" +  "\r\n");
			writer.write("N3*2233 W DIVISION ST~" +  "\r\n");
			writer.write("N4*CHICAGO*IL*606228150~" +  "\r\n");
			writer.write("LX*1~" +  "\r\n");
			writer.write("SV1*HC:99284*" + claimAmt + "*UN*1***1:2**Y~" + "\r\n");
			writer.write("DTP*472*D8*" + DateOfService + "~" + "\r\n");
			writer.write("REF*6R*00001~" +  "\r\n");
			writer.write("SE*32*84995158~" +  "\r\n");
			writer.write("GE*1*84995~" +  "\r\n");
			writer.write("IEA*1*100084995~" +  "\r\n");
			writer.flush();
			writer.close();
			report.updateTestLog("Fle update", "is successful", Status.DONE);
		}
	
		
	
	public void write_EDIFile_G_OK() throws IOException {
		
		writer.write("ISA*00*          *00*          *ZZ*ECLAIMS        *ZZ*EFEOK_CHIP     *210716*0418*|*00501*103204998*0*P*:~" +  "\r\n");
		writer.write("GS*HC*ECLAIMS*EFEOK_CHIP*20210717*04180278*3204998*X*005010X222A1~" +  "\r\n");
		writer.write("ST*837*204998399*005010X222A1~" +  "\r\n");
		writer.write("BHT*0019*00*PY79CR*20210717*0320*CH~" +  "\r\n");
		writer.write("NM1*41*2*AthenaHealth*****46*6628~" +  "\r\n");
		writer.write("PER*IC*CLAIMSEDI*TE*8009815084*EM*ECSTBUSINESS@ATHENAHEALTH.COM~" +  "\r\n");
		writer.write("NM1*40*2*HCSC - OKLAHOMA*****46*00840CHIP~" +  "\r\n");
		writer.write("HL*1**20*1~" +  "\r\n");
		writer.write("NM1*85*2*OKLAHOMA CITY GYNECOLOGY AND OBSTETRICS LLC*****XX*1114987633~" +  "\r\n");
		writer.write("N3*11200 N PORTLAND AVE 2ND FLOOR~" +  "\r\n");
		writer.write("N4*OKLAHOMA CITY*OK*731205045~" +  "\r\n");
		writer.write("REF*EI*201863237~" +  "\r\n");
		writer.write("PER*IC*OKLAHOMA CITY GYNECOLOGY AND OBSTETRICS LLC*TE*4059361008~" +  "\r\n");
		writer.write("NM1*87*2~" +  "\r\n");
		writer.write("N3*PO BOX 8385~" +  "\r\n");
		writer.write("N4*BELFAST*ME*049158300~" +  "\r\n");
		writer.write("HL*2*1*22*0~" +  "\r\n");
		writer.write("SBR*P*18*" + GroupNo + "******BL~" + "\r\n");
//		writer.write("NM1*IL*1*BLACK*TAREN*M***MI*YUP927819212~" +  "\r\n");   823743308
		writer.write("NM1*IL*1*" + LastName + "*" + FirstName + "*" + "***MI*" + alphaPrefix + SubID + "~" + "\r\n");
		writer.write("N3*15929 LOUTAIN CT~" +  "\r\n");
		writer.write("N4*EDMOND*OK*73013~" +  "\r\n");
		writer.write("DMG*D8*" + DOB + "*" + Gender + "~" + "\r\n");
		writer.write("REF*SY*445888806~" +  "\r\n");
		writer.write("NM1*PR*2*HCSC - OKLAHOMA*****PI*00840CHIP~" +  "\r\n");
		writer.write("N3*PO BOX 3283~" +  "\r\n");
		writer.write("N4*TULSA*OK*741023283~" +  "\r\n");
		writer.write("CLM*703386V3062*176***11:B:1*Y*A*Y*Y~" +  "\r\n");
//		writer.write("DTP*431*D8*20220102~" +  "\r\n");
		writer.write("DTP*431*D8*" + DateOfService + "~" + "\r\n");
		writer.write("REF*D9*PY79CR~" +  "\r\n");
		writer.write("HI*ABK:Z01419*ABF:Z1151*ABF:Z113~" +  "\r\n");
		writer.write("NM1*82*1*WEBBER*REGINA****XX*1952650400~" +  "\r\n");
		writer.write("PRV*PE*PXC*207V00000X~" +  "\r\n");
		writer.write("LX*1~" +  "\r\n");
//		writer.write("SV1*HC:99395*161*UN*1***1:2:3~" +  "\r\n");
		writer.write("SV1*HC:99395*" + claimAmt + "*UN*1***1:2:3~" + "\r\n");
//		writer.write("DTP*472*D8*20220102~" +  "\r\n");
		writer.write("DTP*472*D8*" + DateOfService + "~" + "\r\n");
		writer.write("REF*6R*4150817P3062B60872~" +  "\r\n");
		writer.write("LX*2~" +  "\r\n");
//		writer.write("SV1*HC:99000*15*UN*1***1:2~" +  "\r\n");
		writer.write("SV1*HC:99000*" + claimAmt + "*UN*1***1:2~" + "\r\n");
//		writer.write("DTP*472*D8*20220102~" +  "\r\n");
		writer.write("DTP*472*D8*" + DateOfService + "~" + "\r\n");
		writer.write("REF*6R*4150818P3062B60872~" +  "\r\n");
		writer.write("SE*39*204998399~" +  "\r\n");
		writer.write("GE*1*3204998~" +  "\r\n");
		writer.write("IEA*1*103204998~" +  "\r\n");
		writer.flush();
		writer.close();
		report.updateTestLog("Fle update", "is successful", Status.DONE);
			
}
      
	
	
	
}
