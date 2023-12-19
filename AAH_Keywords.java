package CPARTS_Finance.businesscomponents;

import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;

import com.hcsc.automation.framework.Status;
import com.hcsc.automation.framework.TimeStamp;
import com.hcsc.automation.framework.DBUtilities.DBManager;
import com.hcsc.automation.framework.delimitedFile.delimitedFileUtilities;

import supportlibraries.ReusableLibrary;
import supportlibraries.ScriptHelper;
import CPARTS_Finance.dbobjects.DcnResult;
import CPARTS_Finance.mappers.DcnResultMapper;
import CPARTS_Finance.queries.AAHDbQueries.DcnValidation;
import common.DbHelper;
import common.VerifyUtility;

public class AAH_Keywords extends ReusableLibrary {
	private String generalData = "General_Data";
	private String stgData = "STG_Data";

	
	public AAH_Keywords(ScriptHelper scriptHelper) {
		super(scriptHelper);

	}

	public void stgDcnValidation() throws SQLException {
		 
		final String host = dataTable.getData(generalData, "Host"); //twauslapora003.app.test.hcscint.net
		final String serviceid = dataTable.getData(generalData, "Servicename"); //aptdbtst
		final String userId = dataTable.getData(generalData, "Username"); // stn
		final String password = dataTable.getData(generalData,"Password"); //stnf02
		
		final String lifeCycleId = dataTable.getData(stgData,"lifecycle_id"); // load from data table
		final String expected_CLM_NBR =  dataTable.getData(stgData,"CLM_NBR"); // load from data table
		final String expected_GROUP_NUMBER = dataTable.getData(stgData,"GROUP_NUMBER"); // load from data table
		final String expected_SECTION_ID = dataTable.getData(stgData,"SECTION_ID"); // load from data table
		final String expected_SUBSCRIBER_ID = dataTable.getData(stgData,"SUBSCRIBER_ID"); // load from data table
		

		String sqlStatement = String.format(DcnValidation.stgDcnValidation, lifeCycleId);
		System.out.println("sqlStatement " +sqlStatement);
		
		Connection conn = DbHelper.getInstance().getOracleConnection(host, serviceid,
				userId, password);
		ResultSet resultSet = DbHelper.getInstance().executeQuery(conn, sqlStatement);
		List<DcnResult> results = DcnResultMapper.stgDcnResultMapper(resultSet);
		
		if (!results.isEmpty()) {
		
			VerifyUtility.VerifyResult(expected_CLM_NBR, results.get(0).getClaimNumber(), report, "stgClaimNumber",
					String.format(" Claim number matched in STG layer-%s", expected_CLM_NBR), "stgClaimNumber", String.format(" Claim number has not matched in STG layer", expected_CLM_NBR));
				System.out.println(results.get(0).getClaimNumber());
			
			VerifyUtility.VerifyResult(expected_GROUP_NUMBER, results.get(0).getGrpNumber(), report, "stgGroupNumber",
					String.format(" Group number matched in STG layer-%s", expected_GROUP_NUMBER), "stgGroupNumber", String.format(" Group number not matched in STG layer-%s", expected_GROUP_NUMBER));
			System.out.println(results.get(0).getGrpNumber());
			VerifyUtility.VerifyResult(expected_SECTION_ID, results.get(0).getSectionId(), report, "stgSectionNumber",
					String.format("Section number matched in STG layer-%s", expected_SECTION_ID), "stgSectionNumber",String.format("Section number not  matched in STG layer-%s", expected_SECTION_ID));
			System.out.println(results.get(0).getSectionId());

			VerifyUtility.VerifyResult(expected_SUBSCRIBER_ID, results.get(0).getSubscriberId(), report, "stgSubId",
					String.format("subscriber matched in STG layer-%s", expected_SUBSCRIBER_ID), "stgSubId", String.format("subscriber not matched in STG layer-%s", expected_SUBSCRIBER_ID));
			System.out.println(results.get(0).getSubscriberId());

		}else {
			System.out.println("empty result");
		}
		}	
		
		
		public void stnDcnValidation() throws SQLException {
			 
			final String host = dataTable.getData(generalData, "Host"); //twauslapora003.app.test.hcscint.net
			final String serviceid = dataTable.getData(generalData, "Servicename"); //aptdbtst
			final String userId = dataTable.getData(generalData, "Username"); // stn
			final String password = dataTable.getData(generalData,"Password"); //stnf02
			
			final String lifeCycleId = dataTable.getData(stgData,"lifecycle_id"); // load from data table
			final String expected_Event_status = dataTable.getData(stgData,"EVENT_STATUS"); // load from data table

			String sqlStatement = String.format(DcnValidation.stnDcnValidation, lifeCycleId);
			System.out.println("sqlStatement " +sqlStatement);
			
			Connection conn = DbHelper.getInstance().getOracleConnection(host, serviceid,
					userId, password);
			ResultSet resultSet = DbHelper.getInstance().executeQuery(conn, sqlStatement);
			List<DcnResult> results = DcnResultMapper.stnDcnResultMapper(resultSet);
			
			if (!results.isEmpty()) {
				VerifyUtility.VerifyResult(expected_Event_status, results.get(0).getEVENT_STATUS(), report, "stgEventStatus",
						String.format("event status matched in STN layer-%s", expected_Event_status), "stgEventStatus", String.format("Event status not matched in STN layer-%s", expected_Event_status));
				System.out.println(results.get(0).getEVENT_STATUS());

			}else 
				System.out.println("empty result");
	}	
		
		
		public void fdrDcnValidation() throws SQLException {
			
			final String host = dataTable.getData(generalData, "Host"); //twauslapora003.app.test.hcscint.net
			final String serviceid = dataTable.getData(generalData, "Servicename"); //aptdbtst
			final String userId = dataTable.getData(generalData, "Username"); // stn
			final String password = dataTable.getData(generalData,"Password"); //stnf02
			
			final String lifeCycleId = dataTable.getData(stgData,"lifecycle_id"); // load from data table
			
			final String expected_accountEventType = dataTable.getData(stgData,"ACC_EVENT_TYPE"); // load from data table
			final String expected_subEventId = dataTable.getData(stgData,"SUB_EVENT_ID"); // load from data table
			final String expected_dimension4 = dataTable.getData(stgData,"DIMENSION_4"); // load from data table
			final String expected_dimension6 = dataTable.getData(stgData,"DIMENSION_6"); // load from data table
			final String expected_clientAmount1 = dataTable.getData(stgData,"CLIENT_AMOUNT1"); // load from data table


			String sqlStatement = String.format(DcnValidation.fdrValidation, lifeCycleId);
			System.out.println("sqlStatement " +sqlStatement);
			
			Connection conn = DbHelper.getInstance().getOracleConnection(host, serviceid,
					userId, password);
			ResultSet resultSet = DbHelper.getInstance().executeQuery(conn, sqlStatement);
			List<DcnResult> results = DcnResultMapper.fdrDcnResultMapper(resultSet);
			
			if (!results.isEmpty()) {
				VerifyUtility.VerifyResult(expected_accountEventType, results.get(0).getACC_EVENT_TYPE(), report, "stgEventStatus",
						String.format("event matched in STG layer-%s", expected_accountEventType), "stgEventStatus", String.format("event type not matched in STG layer-%s", expected_accountEventType));
				
				VerifyUtility.VerifyResult(expected_subEventId, results.get(0).getSUB_EVENT_ID(), report, "stgEventStatus",
						String.format("seb event matched in STG layer-%s", expected_subEventId), "stgEventStatus", String.format("sub event not matched in STG layer-%s", expected_subEventId));
				
				VerifyUtility.VerifyResult(expected_dimension4, results.get(0).getDIMENSION_4(), report, "expected_dimension4",
						String.format("dimension 4 matched in STG layer-%s", expected_dimension4), "expected_dimension4", String.format("dimension 6 not matched in STG layer-%s", expected_dimension4));
				
				VerifyUtility.VerifyResult(expected_dimension6, results.get(0).getDIMENSION_6(), report, "expected_dimension6",
						String.format("Dimension 6 matched in STG layer-%s", expected_dimension6), "expected_dimension6", String.format("Dimension 6 not matched in STG layer-%s", expected_dimension6));
				VerifyUtility.VerifyResult(expected_clientAmount1, results.get(0).getCLIENT_AMOUNT1(), report, "expected_clientAmount1",
						String.format("Client amount matched in STG layer-%s", expected_clientAmount1), "stgEventStatus", String.format("Client Amount not matched in STG layer-%s", expected_clientAmount1));

			}else 
				System.out.println("empty result");
	}
		public void slrDcnValidation() throws SQLException {
			 
			final String host = dataTable.getData(generalData, "Host"); //twauslapora003.app.test.hcscint.net
			final String serviceid = dataTable.getData(generalData, "Servicename"); //aptdbtst
			final String userId = dataTable.getData(generalData, "Username"); // stn
			final String password = dataTable.getData(generalData,"Password"); //stnf02
			
			final String lifeCycleId = dataTable.getData(stgData,"lifecycle_id"); // load from data table
			
			final String expected_JL_ENTITY = dataTable.getData(stgData,"JL_ENTITY"); // load from data table
			final String expected_JL_ACCOUNT = dataTable.getData(stgData,"JL_ACCOUNT"); // load from data table
			final String expected_JL_TRAN_AMOUNT = dataTable.getData(stgData,"JL_TRAN_AMOUNT"); // load from data table


			String sqlStatement = String.format(DcnValidation.slrValidation, lifeCycleId);
			System.out.println("sqlStatement " +sqlStatement);
			
			Connection conn = DbHelper.getInstance().getOracleConnection(host, serviceid,
					userId, password);
			ResultSet resultSet = DbHelper.getInstance().executeQuery(conn, sqlStatement);
			List<DcnResult> results = DcnResultMapper.slrDcnResultMapper(resultSet);
			
			if (!results.isEmpty()) {
				VerifyUtility.VerifyResult(expected_JL_ENTITY, results.get(0).getJL_ENTITY(), report, "expected_JL_ENTITY",
						String.format("ENTITY matched in SLR layer ", expected_JL_ENTITY), "expected_JL_ENTITY", String.format("ENTITY not matched in SLR layer-%s", expected_JL_ENTITY));
				System.out.println(results.get(0).getEVENT_STATUS());
				
				VerifyUtility.VerifyResult(expected_JL_ACCOUNT, results.get(0).getJL_ACCOUNT(), report, "expected_JL_ACCOUNT",
						String.format("ACCOUNT matched in SLR layer ", expected_JL_ENTITY), "expected_JL_ACCOUNT", String.format("ACCOUNT not matched in SLR layer-%s", expected_JL_ACCOUNT));
				System.out.println(results.get(0).getJL_ACCOUNT());
				VerifyUtility.VerifyResult(expected_JL_TRAN_AMOUNT, results.get(0).getJL_TRAN_AMOUNT(), report, "expected_JL_TRAN_AMOUNT",
						String.format(" TRAN_AMOUNT matched in SLR layer ", expected_JL_ENTITY), "expected_JL_TRAN_AMOUNT", String.format("TRAN_AMOUNT not matched in SLR layer-%s", expected_JL_TRAN_AMOUNT));
				System.out.println(results.get(0).getJL_TRAN_AMOUNT());

			}else 
				System.out.println("empty result");
	}
		
}
