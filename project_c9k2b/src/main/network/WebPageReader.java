package network;
// Code is from http://zetcode.com/articles/javareadwebpage/

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONObject;
import weather.Weather;
import javax.imageio.*;

import javax.swing.*;


public class WebPageReader {

    private String stringUrl;
    private StringBuilder stringBuilder;

    public WebPageReader(String query, String apiKey) throws IOException {
        stringUrl = query + apiKey;
        stringBuilder = getData();
    }

    //MODIFIES: this
    //EFFECTS: retrieves data from the designated url
    private StringBuilder getData() throws IOException {
        BufferedReader br = null;
        StringBuilder sb = null;

        try {
            URL url = new URL(stringUrl);
            br = new BufferedReader(new InputStreamReader(url.openStream()));
            String line;
            sb = new StringBuilder();

            while ((line = br.readLine()) != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
            }

        } finally {
            if (br != null) {
                br.close();
            }
        }
        return sb;
    }

    public StringBuilder getStringBuilder() {
        return stringBuilder;
    }

    /// PARSING CODE FOUND FROM https://www.testingexcellence.com/how-to-parse-json-in-java/

    //EFFECTS: parses a JSON object to find the weather information from a JSON string
    public Weather parseWeather() {
        try {
            JSONObject object = new JSONObject(stringBuilder.toString());
            Double temperature = object.getJSONObject("main").getDouble("temp") - 273.15;
            Double maxTemperature = object.getJSONObject("main").getDouble("temp_max") - 273.15;
            Double minTemperature = object.getJSONObject("main").getDouble("temp_min") - 273.15;
            Double windSpeed = object.getJSONObject("wind").getDouble("speed");


            JSONArray jsonArray = object.getJSONArray("weather");
            String description = jsonArray.getJSONObject(0).getString("description");
            Image icon = getIcon(jsonArray.getJSONObject(0).getString("icon"));

            return new Weather(temperature, maxTemperature, minTemperature, windSpeed, description, icon);

        } catch (org.json.JSONException e) {
            System.out.println("couldn't parse Json Array");
        }
        return null;

    }

    //Effects: returns the related Image with the icon parsed from the web page
    private Image getIcon(String iconId) {
        Image image = null;
        try {
            URL url = new URL("http://openweathermap.org/img/wn/" + iconId + "@2x.png");
            image = ImageIO.read(url);
        } catch (IOException e) {
            System.out.println("Couldnt get Image");
        }

        return image;
    }

}
