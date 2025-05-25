import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONObject;

public class WeatherAPIClient {
    private static final String API_KEY = "7dd72423e2cd4b84ac381626252405";
    private static final String BASE_URL = "http://api.weatherapi.com/v1/current.json";

    public static void main(String[] args) {
        String city = "chennai"; // You can change the city name here
        getWeatherData(city);
    }

    public static void getWeatherData(String city) {
        try {
            String urlString = BASE_URL + "?key=" + API_KEY + "&q=" + city + "&aqi=no";
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("GET");
            conn.connect();

            int responseCode = conn.getResponseCode();
            if (responseCode != 200) {
                System.out.println("Error: Unable to fetch data. HTTP Code: " + responseCode);
                return;
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder jsonBuilder = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                jsonBuilder.append(line);
            }

            reader.close();
            parseAndDisplayWeather(jsonBuilder.toString());

        } catch (Exception e) {
            System.out.println("Exception occurred: " + e.getMessage());
        }
    }

    private static void parseAndDisplayWeather(String json) {
        JSONObject obj = new JSONObject(json);
        String cityName = obj.getJSONObject("location").getString("name");
        double temp = obj.getJSONObject("current").getDouble("temp_c");
        int humidity = obj.getJSONObject("current").getInt("humidity");
        String description = obj.getJSONObject("current").getJSONObject("condition").getString("text");


        System.out.println("Weather Report for " + cityName);
        System.out.println("Temperature: " + temp + " °C");
        System.out.println("Humidity: " + humidity + " %");
        System.out.println("Condition: " + description);
    }
}
