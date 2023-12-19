package CPARTS_Finance.businesscomponents;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.StringWriter;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.hcsc.automation.framework.Status;
import com.hcsc.automation.framework.Util;

import common.ActionComponents;
import common.Reporting;
import common.Sync;
import CPARTS_Finance.uimap.MPUI_UIMap;
import CPARTS_Finance.uimap.BAM_ObjRepository.WebRegistration_Page;
import CPARTS_Finance.uimap.BAM_ObjRepository.mpui_pages;
import CPARTS_Finance.uimap.MPUI_UIMap.HomePage_UIMap;
import CPARTS_Finance.uimap.MPUI_UIMap.Login_UIMap;
import supportlibraries.ReusableLibrary;
import supportlibraries.ScriptHelper;

public class MPUI_Keywords extends ReusableLibrary {
	private String sGeneralDataSheetName = "General_Data";
	private String sTestDataSheetName = "SearchParameters_Data";

	WebDriverWait wait = new WebDriverWait(driver.getWebDriver(), 10);
	ActionComponents AComp = new ActionComponents(scriptHelper);
	Sync sync = new Sync(scriptHelper);

	public MPUI_Keywords(ScriptHelper scriptHelper) {
		super(scriptHelper);
		// TODO Auto-generated constructor stub
		
		
	}
	

	public void launchWebApplication() throws InterruptedException

	{
		driver.get(properties.getProperty("MPUIURL"));
		
			report.updateTestLog("MPUI Web launch", "is successful", Status.PASS);

	}

	public void mpui_Login()

	{
		String userName = dataTable.getData("General_Data", "MPUserName");
		AComp.safeClearAndType(MPUI_UIMap.Login_UIMap.xuserName, userName, Reporting.NONE, 5);
		String password = AComp.getLANPassword(); //Save your password in a file named LAN_Password.txt in C:\Automation_Password directory
		AComp.safeClearAndType(MPUI_UIMap.Login_UIMap.xpassword, password, Reporting.NONE, 5);
			AComp.safeClick(MPUI_UIMap.Login_UIMap.xbtnSubmit, Reporting.NONE, 5);
					report.updateTestLog("MPUI Web Login", "is successful", Status.PASS);
		

	}

	public void ymemberSearch() throws Exception{
		//	String ssn = dataTable.getData("SearchParameters_Data", "ssno");
			String sUBid = dataTable.getData("SearchParameters_Data", "sUBid");
		//	AComp.safeClearAndType(MPUI_UIMap.SearchMember.xssn, ssn, Reporting.NONE, 10);
			AComp.safeClearAndType(mpui_pages.mpui_subID, sUBid, Reporting.NONE, 10);
		//	AComp.selectListBox(mpui_pages.mpui_membershipstatus, Reporting.NONE, "Active", 0);
			AComp.safeClick(MPUI_UIMap.SearchMember.xbtnSubmit, Reporting.NONE, 5);
			
						
			Boolean searchResultCount = sync.isElementPresent(mpui_pages.mpui_morethanOneSearchResults);
			if (searchResultCount) {
				Boolean memberName = sync.isElementPresent(mpui_pages.mpui_memberName);
				if(memberName){
					report.updateTestLog("MPUI Search result", "More than one search results displayed", Status.PASS);
					String sMembershipName = AComp.safeGetText(mpui_pages.mpui_memberName, Reporting.REPORTWITHSCREENSHOT, 1);
					String[] splited = sMembershipName.split("\\s+");
					System.out.println(splited.length);
					int size=splited.length;
					
					
					if(size==2){				
						AComp.putTempData("firstName",splited[0]);
						AComp.putTempData("lastName",splited[1]);			
					}else if(size==3){
						AComp.putTempData("firstName",splited[0]);
						AComp.putTempData("lastName",splited[2]);
					}else if(size>3){
						AComp.putTempData("firstName",splited[0]);
						AComp.putTempData("lastName",splited[3]);
					}
					Thread.sleep(3000);
					AComp.safeClick(mpui_pages.mpui_memberName, Reporting.NONE);
					report.updateTestLog("Member details", "are displayed", Status.PASS);
					Thread.sleep(2000);
					
					
					String idnum = AComp.safeGetText(MPUI_UIMap.SearchMember.idnumber, Reporting.NONE, 5);	
					String idnum1 = removeZero(idnum);
																                        
			        //sub id
			        AComp.putTempData("subID", idnum1);
					
					String sMemberDOB = AComp.safeGetText(mpui_pages.mpui_memberDOB1, Reporting.REPORTWITHSCREENSHOT, 1);
					
					 String aMemberDob[] = sMemberDOB.split("/");
				        String aMemberYear=aMemberDob[2];
				        String aMemberMonth=aMemberDob[0];
				        String aMemberDay=aMemberDob[1];
				        String xMemberDOB = aMemberYear + aMemberMonth + aMemberDay;
				        AComp.putTempData("DOB", xMemberDOB);
				
			
					
					Thread.sleep(4000);
					
					
					  String sMemberGender1 = AComp.safeGetText(mpui_pages.mpui_memberGender, Reporting.NONE, 1);
				        String sMemberGender =sMemberGender1.substring(0,1);
				        AComp.putTempData("gender",sMemberGender);
				        
				        String sMemberState1 = AComp.safeGetText(mpui_pages.mpui_memberstate, Reporting.NONE, 1);
				        String sMemberState =sMemberState1.substring(0,2);
				        AComp.putTempData("state", sMemberState);
					
									
					Thread.sleep(2000);
				}else{
					report.updateTestLog("MPUI Search result", "Nothing to display - Incorrect SUB ID", Status.FAIL);
				}
			}
			
					
			else{
				report.updateTestLog("MPUI Search result", "only one result is displayed", Status.PASS);
				String sMembershipName = AComp.safeGetText(mpui_pages.mpui_memberName1, Reporting.REPORTWITHSCREENSHOT, 1);
				String[] splited = sMembershipName.split("\\s+");
				int size=splited.length;
				
				String idnum = AComp.safeGetText(MPUI_UIMap.SearchMember.idnumber, Reporting.NONE, 5);	
				String idnum1 = removeZero(idnum);
															                        
		        //sub id
		        AComp.putTempData("subID", idnum1);
		        
		        String sMemberDOB = AComp.safeGetText(mpui_pages.mpui_memberDOB1, Reporting.NONE, 1);
		        String aMemberDob[] = sMemberDOB.split("/");
		        String aMemberYear=aMemberDob[2];
		        String aMemberMonth=aMemberDob[0];
		        String aMemberDay=aMemberDob[1];
		        String xMemberDOB = aMemberYear + aMemberMonth + aMemberDay;
		        AComp.putTempData("DOB", xMemberDOB);
		        
		        String sMemberGender1 = AComp.safeGetText(mpui_pages.mpui_memberGender, Reporting.NONE, 1);
		        String sMemberGender =sMemberGender1.substring(0,1);
		        AComp.putTempData("gender",sMemberGender);
		        
		        String sMemberState1 = AComp.safeGetText(mpui_pages.mpui_memberstate, Reporting.NONE, 1);
		        String sMemberState =sMemberState1.substring(0,2);
		        AComp.putTempData("state", sMemberState);
		        
		  		        
				if(size==2){				
					AComp.putTempData("firstName",splited[0]);
					AComp.putTempData("lastName",splited[1]);			
				}else if(size==3){
					AComp.putTempData("firstName",splited[0]);
					AComp.putTempData("lastName",splited[2]);
				}else if(size>3){
					AComp.putTempData("firstName",splited[0]);
					AComp.putTempData("lastName",splited[3]);
				}
				Thread.sleep(2000);
				
			}}
	
	 public static String removeZero(String str)
	    {
	        // Count leading zeros
	        int i = 0;
	        while (i < str.length() && str.charAt(i) == '0')
	            i++;
	  
	        // Convert str into StringBuffer as Strings
	        // are immutable.
	        StringBuffer sb = new StringBuffer(str);
	  
	        // The  StringBuffer replace function removes
	        // i characters from given index (0 here)
	        sb.replace(0, i, "");
	  
	        return sb.toString();  // return in String
	    }
		
		public void setPlanNumber() {
		String planNumber = AComp.safeGetText(MPUI_UIMap.HomePage_UIMap.planNumber, Reporting.NONE, 0);
		AComp.putTempData("planNumber",planNumber );
		System.out.println("plan number is"+ planNumber);
	//	String planNumber = driver.findElement(MPUI_UIMap.HomePage_UIMap.planNumber).getText();
		;
	/*	File file = new File("." + Util.getFileSeparator() + "CPARTS_Group" + Util.getFileSeparator() + "Datatables"
				+ Util.getFileSeparator() + "tempdata" + Util.getFileSeparator() + "planID.txt");
		String planIDController = file.getAbsolutePath();

		AComp.setValueToTextFile(planNumber, planIDController);*/
	}
		
		
		}

	


