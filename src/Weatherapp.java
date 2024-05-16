import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.Scanner;

public class Weatherapp {
    private static final String KEY = "19973ca3d53c2abd5a96db44d027f971";
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter city \n>");
        String city = scanner.nextLine();
        getWeather(city);
    }

    public static void getWeather(String city){
        try {
            String urlString = "http://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=" + KEY;
            URL url = new URL(urlString);
            JSONObject jsonObject = getJsonObject(url);
            JSONObject main = (JSONObject)jsonObject.get("main");
            double temp = KelvinoFarenheit((double)main.get("temp"));
            System.out.println("Temperature in "+city + " is " + temp);
        } catch (Exception e) {
            System.out.println("Error");
        }
    }

    private static JSONObject getJsonObject(URL url) throws IOException, ParseException {
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder response = new StringBuilder();
        String line;
        while((line=reader.readLine())!= null){
            response.append(line);
        }
        reader.close();
        JSONParser parser = new JSONParser();
        return (JSONObject) parser.parse(response.toString());
    }

    public static double KelvinoFarenheit(double temp){
        return ((double)((int)((temp-273.15)*9/5+32)*100))/100;
    }

}
