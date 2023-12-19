package common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import CPARTS_Finance.mappers.DcnResultMapper;

public class DbHelper {
	
	private static DbHelper dbHelper = null;
	private DbHelper() {};
	
	//make this class singleton
	public static DbHelper getInstance()
	{
		if(dbHelper == null)
		{
			dbHelper = new DbHelper();
		}
		return dbHelper;
		
	}
	
	
	/**
	 * 
	 *
	 */
	public Connection getOracleConnection(final String hostName, final String serviceName, final String userName, final String password) {
		try {
			 
			final String connectionString = String.format("jdbc:oracle:thin:@//%s:1521/%s", hostName,serviceName);
			Class.forName("oracle.jdbc.driver.OracleDriver");
			return DriverManager.getConnection(connectionString,userName,password);
			
			
		} catch (SQLException ex) {
			//TODO: add to report or log
			System.out.println(ex.getStackTrace());
		} catch (ClassNotFoundException ex) {
			//TOD: add to report or log
			System.out.println("Oracle driver class not found" + ex.getStackTrace());
		}
		return null;
	}
	
	public ResultSet executeQuery(final Connection connection, final String sqlStatement) throws SQLException
	{
		Statement stmt = connection.createStatement();
		ResultSet resultSet = stmt.executeQuery(sqlStatement);
		return resultSet;
		
	}

}
