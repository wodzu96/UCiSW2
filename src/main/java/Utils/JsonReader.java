package Utils;

import Models.TransitRequest;
import Models.TransitResult;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by wodzu on 05.03.2018.
 *
 * API key = AIzaSyC0zgm7x8Og0UhZ7N7ra5p_jNnrYslcS3g
 */
public class JsonReader {
    private static String streamToString(InputStream inputStream) {
        String text = new Scanner(inputStream, "UTF-8").useDelimiter("\\Z").next();
        return text;
    }

    public static JSONObject jsonGetRequest(String urlQueryString) {
        String json = null;
        try {
            URL url = new URL(urlQueryString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setInstanceFollowRedirects(false);
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("charset", "utf-8");
            connection.connect();
            InputStream inStream = connection.getInputStream();
            json = streamToString(inStream); // input stream to string
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return new JSONObject(json);
    }

    public static TransitResult getTransit(TransitRequest transitRequest){
        String url = "https://maps.googleapis.com/maps/api/directions/json?";
        url += "origin=" + transitRequest.getOrygin();
        url += "&destination=" + transitRequest.getDestination();
        url += "&mode=transit";
        if(transitRequest.getDepartureTime()!=0)
            url += "&departure_time="+transitRequest.getDepartureTime();
        url += "&key=AIzaSyC0zgm7x8Og0UhZ7N7ra5p_jNnrYslcS3g";

        JSONObject response = jsonGetRequest(url);
        System.out.print(url);
        JSONArray steps = response.getJSONArray("routes").getJSONObject(0).getJSONArray("legs").getJSONObject(0).getJSONArray("steps");

        for(Object stepObject : steps){
            JSONObject step = new JSONObject(stepObject.toString());
            if(step.getString("travel_mode").equals("TRANSIT")) {
                JSONObject transitDetails = step.getJSONObject("transit_details");
                TransitResult result = new TransitResult(transitDetails.getJSONObject("line").getString("short_name"),
                        transitDetails.getString("headsign"),
                        transitDetails.getJSONObject("departure_time").getString("text"),
                        transitDetails.getJSONObject("departure_time").getInt    ("value"));
              //  result.print();
                return result;
            }
        }
        return null;
    }
}
