package parcelpoint.utility;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

import org.apache.log4j.Logger;
import org.testng.Assert;



public class util {

	public static BufferedReader br;
	public static HashMap<String, String> hData;
	private static Logger Logger = org.apache.log4j.Logger.getLogger(util.class.getName());

	
	/* Function to read Test Data from CSV and load into HashMap
	 * */
	public static void loadCSV(String Profile) throws IOException{
		Logger.info("Inside : loadCSV");
		String sLine;
		hData=new HashMap<String, String>();
		try{
			Logger.info("Loading the CSV File and Reading Contents");
			br = new BufferedReader(new FileReader(System.getProperty("user.dir")+"/src/main/java/parcelpoint/resources/"+Profile+"TestData.csv"));
			while ((sLine=br.readLine())!= null)	
			{
				Logger.info(sLine);
				// use comma as separator
				String[] sKeyValue = sLine.split(",");
				String sKey=sKeyValue[0];
				String sVal=sKeyValue[1];
				hData.put(sKey, sVal);
			}
		}catch(Exception e){
			Logger.info("Could not Read CSV File"+e);
		}

		br.close();
	}

	/* Function to get specific value from CSV for the given Key
	 * @param String, Key that needs to be searched in CSV
	 * @return String, Value found for the Key given
	 * */
	public static String getTestData(String sKey){
		Logger.info("Inside : getTestData");
		String sVal="";
		boolean bFound=false;
		if(hData.containsKey(sKey))
		{
			sVal=hData.get(sKey);
			bFound=true;
		}
		else
		{
			Logger.info("Value is not available");
			Assert.assertTrue(bFound);
		}
		return sVal;
	} 




}
