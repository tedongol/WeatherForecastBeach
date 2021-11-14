package parcelpoint.dataBase;

import org.apache.log4j.Logger;
import parcelpoint.utility.util;

import java.sql.*;

public class DatabaseCRUDOperations {
	private Logger Logger = org.apache.log4j.Logger.getLogger(DatabaseCRUDOperations.class.getName());
	public String sFunName="";
	
	/*
	 * Function to create a Connection to the Database
	 * @param environment, environment to which the connection is established
	 * @return connection, connection that has been established for the DB operations
	 */
	public Connection getConnection(String environment){
		sFunName="getConnection";
		Logger.info("Inside : getConnection");
		try {
			String dbClass = "com.mysql.jdbc.Driver";
			String dbUrl=null;
			String username=null;
			String password=null;
			if(environment.equalsIgnoreCase("Staging")){
				Logger.info(sFunName+" : Setting up for environment : "+environment);
				 dbUrl = util.getTestData("dburl");
				 username=util.getTestData("username");
				 password=util.getTestData("password");

			}else if (environment.equalsIgnoreCase("Prod")){
				Logger.info(sFunName+" : Setting up for environment : "+environment);
				dbUrl = util.getTestData("dburl1");
				username=util.getTestData("username2");
				password=util.getTestData("password2");
			}

			Logger.info(sFunName+" : Setting up for Connection");
			Class.forName(dbClass);
			Connection con = DriverManager.getConnection (dbUrl,username,password);
			Logger.info(sFunName+" : Connection Established!!!");
			return con;
		}
		catch(ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/*
	 * Function to return a result set for the query being executed
	 * @param connection, connection that has been established for the DB operations
	 * @param query, query which is to be executed
	 * @return ResultSet, result that is been obtained from the execution of the query
	 */
	public ResultSet getResultSet(Connection con, String query){
		sFunName="getResultSet";
		Logger.info("Inside : getResultSet");
		try {
			Logger.info(sFunName+" : Query to be executed : "+query);
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			Logger.info(sFunName+" : Result Set Obtained : "+rs);
			return rs;
		}

		catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	

	/*
	 * Function to close the connection that has been established
	 * @param connection, connection that has been established for the DB operations
	 */
	public void closeConnection(Connection con){
		sFunName="closeConnection";
		Logger.info("Inside : closeConnection");
		try {
			Logger.info(sFunName+" : Closing Connection");
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	public void addValueInDB(Connection con, String query){
		sFunName="addValueInDB";
		Logger.info("Inside : addValueInDB");
		try {
			PreparedStatement preparedStmt = con.prepareStatement(query);
			Logger.info(sFunName+" : Executing the Query");
			
			preparedStmt.execute();
		
			Logger.info(sFunName+" : Updated the DB");
		}
		catch(SQLException e) {
			e.printStackTrace();
		}

	}
	

}
