package parcelpoint.seleniumgluecode;

import org.apache.log4j.Logger;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import parcelpoint.dataBase.DatabaseCRUDOperations;


import java.sql.Connection;

import static parcelpoint.utility.util.*;

public class
Hooks  {
	private Logger Logger = org.apache.log4j.Logger.getLogger(Hooks.class.getName());
	//loading data from CSV file before any test run
	@Before
	public void launch() throws Exception
	{
		Logger.info("Loading data from CSV");
		String Profile = System.getProperty("Profile","Staging");
		loadCSV(Profile);

		//To Truncate table each time in the start of test scenario
		DatabaseCRUDOperations FD = new DatabaseCRUDOperations();
		Connection con = null;
		con = FD.getConnection("Staging");
		String query1 = "Truncate table sydney_weather ; ";
		FD.addValueInDB(con, query1);
	}

	//Just to inform closing the program after test run
	@After
	public void quit() throws Exception
	{
		Logger.info("Tearing Down");
		Logger.info("Closing the program");

	}
}
