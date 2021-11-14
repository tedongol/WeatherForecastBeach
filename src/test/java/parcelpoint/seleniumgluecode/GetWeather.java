package parcelpoint.seleniumgluecode;

import au.com.bytecode.opencsv.CSVWriter;
import cucumber.api.java.en.When;
import parcelpoint.RESTApi.RESTApi;
import parcelpoint.dataBase.DatabaseCRUDOperations;
import parcelpoint.utility.util;
import java.io.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;

public class GetWeather {

//For making an api call with n number of postcodes as defined in the feature file in WHEN condition
//API CAll for weather forecast for the next 16 days with 10 POSTAL CODES
    @When("^Get the Weather report with top (\\d+) postcode of Sydney")
    public void Get_the_Weather_report_with_top_postcode_of_Sydney(int count) throws Throwable {
        int i;
        for (i = 1; i <= count; i++) {
            RESTApi rApi = new RESTApi();
            String postcodefinal = "POSTCODE" + i;
            String beachname = "BEACH" + i;
            System.out.println(postcodefinal);
            rApi.getWeather(util.getTestData("APIURL"), util.getTestData(postcodefinal), util.getTestData(beachname), util.getTestData("COUNTRY"), util.getTestData("APIKEY"));
            Thread.sleep(3000);
        }
    }

//Function to pass the suitable condition of temperature that is nearest to 20 degree to find out the best spot
    @When("^Pick best suitable two spots based upon suitable weather$")
    public void Pick_bests_suitable_two_spots_based_upon_suitable_weather() throws Throwable {

        String suitablecond = "order by (ABS(maxtemp-mintemp)/2-20 ) desc";
        Pick_bests_suitable_two_spots_based_upon_suitable_condition(suitablecond);

    }
    //Function to pass the suitable condition of uv with descending order to find out the best spot
    @When("^Pick best suitable two spots based upon suitable uv$")
    public void Pick_bests_suitable_two_spots_based_upon_suitable_uv() throws Throwable {
        String suitableuv = "order by  uv desc";

        Pick_bests_suitable_two_spots_based_upon_suitable_condition(suitableuv);

    }
    /*Function to find out the best spot out of n number of list and with all parameter conditions
    surf on <Monday & Friday> in next 16 days
    temperature is between <12℃ and 30℃>
    wind speed to be between 3 and 9
     UV index is <= 12
     Storing the filtered records in database
     */
    private void Pick_bests_suitable_two_spots_based_upon_suitable_condition(String condition) throws Throwable {

        DatabaseCRUDOperations FD = new DatabaseCRUDOperations();
        Connection con = null;
        con = FD.getConnection("Staging");
        String query1 = "select distinct beach,postcode,lat,lon from sydney_weather " + condition + " limit 2; ";
        FD.getResultSet(con, query1);
        System.out.println(query1);
        ResultSet rs = FD.getResultSet(con, query1);
        ArrayList twobeach = new ArrayList();


        while (rs.next()) {
            String[] data1 = {"", "", "", ""};
            for (int i = 1; i <= 4; i++) {
                String columnValue = rs.getString(i);
                System.out.print(columnValue);
                System.out.print(" ");


                data1 = new String[]{rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4)};


            }

            twobeach.add(data1);
            System.out.println(" ");
            String location = System.getProperty("user.dir") + "/src/main/java/parcelpoint/resources/Top2Beaches.txt";
            System.out.println(location);

            CSVWriter writer = new CSVWriter(new FileWriter(location), ',', CSVWriter.NO_QUOTE_CHARACTER, '\\');

            System.out.println("result" + twobeach);
            writer.writeAll(twobeach);
            writer.close();


        }
        con.close();
    }


}

