package parcelpoint.RESTApi;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.sql.Connection;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.testng.Assert;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import parcelpoint.dataBase.DatabaseCRUDOperations;
import parcelpoint.utility.util;

import static io.restassured.RestAssured.*;

public class RESTApi {

    private static Logger Logger = org.apache.log4j.Logger.getLogger(RESTApi.class.getName());
    public static String sFunName = "";


    /*
     * Function to get suitable weather
     * @param baseURI, Base URI of the REST API Call
     */

    public void getWeather(String baseURI, String postcode, String beach, String country, String apiKey) throws JSONException, IOException, ParseException {
        sFunName = "Get Weather";
        Logger.info("Inside : Get Weather");


        Logger.info(sFunName + " : " + baseURI + "  : " + apiKey);
        RestAssured.baseURI = baseURI;
        //to store Request in Request.txt
        OutputStream file = new FileOutputStream(System.getProperty("user.dir") + "/src/main/java/parcelpoint/resources/Request.txt");
        PrintStream stream = new PrintStream(file, true);
        RestAssured.filters(RequestLoggingFilter.logRequestTo(stream));

        //to store Response in Response.txt
        OutputStream file2 = new FileOutputStream(System.getProperty("user.dir") + "/src/main/java/parcelpoint/resources/Response.txt");
        PrintStream stream2 = new PrintStream(file2, true);
        RestAssured.filters(ResponseLoggingFilter.logResponseTo(stream2));
        Response response = given().log().all().

                queryParam("Key", apiKey).
                queryParam("postal_code", postcode).
                queryParam("country", country).
                when().
                post("/v2.0/forecast/daily").
                then().log().all().assertThat().statusCode(200).contentType(ContentType.JSON).extract().response();

        int size = response.jsonPath().getInt("data.size");
        //to verify the weather forecast is for 16 days and has 16 different records for next 16 days
        Assert.assertEquals(size, 16);
        for (int i = 0; i < size; i++) {

            String day = "";
            String date1 = response.jsonPath().get("data[" + i + "].valid_date");

            Date d = new Date();
            Date datefinal = new SimpleDateFormat("yyyy-mm-dd").parse(date1);
            System.out.println(datefinal);
            Calendar c = Calendar.getInstance();
            c.setTime(datefinal);

            String strDateFormat = "yyyy-mm-dd HH:mm:ss";
            DateFormat dateFormat = new SimpleDateFormat(strDateFormat);
            String formattedDate = dateFormat.format(datefinal);
            Logger.info("Date : " + formattedDate);
            int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
            if (dayOfWeek == 5) {
                day = "Friday";
            } else {
                day = "Monday";
            }
            ;
            if (dayOfWeek == 5 || dayOfWeek == 1) {
                //to store all values for sorting and reporting purpose for beaches
                String lon = response.jsonPath().get("lon");
                String lat = response.jsonPath().get("lat");
                String lowtemp = (response.jsonPath().get("data[" + i + "].low_temp")).toString();
                String hightemp = (response.jsonPath().get("data[" + i + "].max_temp")).toString();
                String windspeed = (response.jsonPath().get("data[" + i + "].wind_spd")).toString();
                String uv = (response.jsonPath().get("data[" + i + "].uv")).toString();


                if (Float.valueOf(lowtemp) >= 12 && Float.valueOf(hightemp) <= 30 && Float.valueOf(windspeed) >= 3 && Float.valueOf(windspeed) <= 9 && Float.valueOf(uv) <= 12) {

                    //To insert all the filtered records of beaches and suitable weather parameters in the table sydney_weather that has been created in the database
                    DatabaseCRUDOperations FD = new DatabaseCRUDOperations();
                    Connection con = null;
                    con = FD.getConnection(util.getTestData("environ"));
                    Connection con2 = null;
                    con2 = FD.getConnection("Staging");
                    String query2 = "insert into sydney_weather values('" + formattedDate + "','" + beach + "','" + day + "','" + postcode + "','" + hightemp + "','" + lowtemp + "','" + lat + "','" + lon + "','" + windspeed + "','" + uv + "');";
                    System.out.println(query2);
                    FD.addValueInDB(con2, query2);

                }


            }

        }


    }
}