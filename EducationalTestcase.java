package CPARTS_Finance.businesscomponents;

import com.hcsc.automation.framework.Status;

import common.ActionComponents;
import common.Sync;
import supportlibraries.ReusableLibrary;
import supportlibraries.ScriptHelper;

public class EducationalTestcase  extends ReusableLibrary{ 
	public EducationalTestcase(ScriptHelper scriptHelper)
	{
		super(scriptHelper);
		//TODO Auto-generated constructor stub
	}
	ActionComponents oAComp = new ActionComponents(scriptHelper);

	//Sync oSync = new Sync(scriptHelper);

	public void totalNumOfClaims() {
		float totalClaims;
		float tpa = Float.parseFloat(oAComp.getTempData("AmtPaid"));//60   	
		//$110.48
		float rfcr = Float.parseFloat(oAComp.getTempData("RecopAmt"));//50 //60
		float remEleAmt = tpa - 50;//60.48
		if(remEleAmt>=rfcr)
		{
			totalClaims=1;
		}
		else
		{
        totalClaims = rfcr / remEleAmt; // == 5 = quotient
		float rem = rfcr % remEleAmt;// =2=reminder
		if (rem > 0)
			totalClaims++;
		}
		System.out.println("total claims is "+totalClaims);
		String strTotalClaims = Float.toString(totalClaims);
		oAComp.putTempData("TotalClaims", Float.toString(totalClaims));
		report.updateTestLog("Total number of claims to be posted is ",strTotalClaims , Status.PASS);
		report.updateTestLog("Claims_EDI_Create Medical Claim testcase to be executed ",strTotalClaims , Status.PASS);
		report.updateTestLog("Claims_GUI_VerifyDCN_B_2 testcase to be executed",strTotalClaims , Status.PASS);
		report.updateTestLog("Claims_Bluechip_Finalize Medical Claim testcase to be executed ",strTotalClaims , Status.PASS);
		report.updateTestLog("Claims_BlueGateway_Claim Inquiry testcase to be executed ",strTotalClaims , Status.PASS);
		report.updateTestLog("Claims_FPDB_Claim Payment Status testcase to be executed ",strTotalClaims , Status.PASS);
//		Claims_EDI_Create Medical Claim
//		Claims_GUI_VerifyDCN_B_2
//		Claims_Bluechip_Finalize Medical Claim
//		Claims_BlueGateway_Claim Inquiry
//		Claims_FPDB_Claim Payment Status
		
		
		}

	
}
