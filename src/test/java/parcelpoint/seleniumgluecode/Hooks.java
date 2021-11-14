package parcelpoint.seleniumgluecode;

import org.apache.log4j.Logger;
import cucumber.api.java.After;
import cucumber.api.java.Before;


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
	}

	//Just to inform closing the program after test run
	@After
	public void quit() throws Exception
	{
		Logger.info("Tearing Down");
		Logger.info("Closing the program");

	}
}
