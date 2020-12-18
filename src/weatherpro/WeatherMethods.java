package weatherpro;

import java.awt.Image;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import java.net.URL;
import java.sql.Date;

import org.json.JSONObject;

public class WeatherMethods {
	
	public static String iconID;
	public static String city;
	public static String country;
	public static String description;
	public static String sunriseTime;
	public static String sunsetTime;
	public static double temperature;
	public static double maxTemperature;
	public static double minTemperature;
	public static double windSpeed;
	public static int windDirection;
	public static int pressure;
	public static int humidity;
	public static int timezone;

	
	
	/*
	 * Convert Kelvin to Celsius and Fahrenheit
	 */
	private static void convertUnit(String unit) {
		if (unit == "C") {
			temperature -= 273.15;
			maxTemperature -= 273.15;
			minTemperature -= 273.15;
		} else if (unit == "F") {
			temperature = ((temperature - 273.15) * (1.8)) + 32;
			maxTemperature = ((maxTemperature - 273.15) * (1.8)) + 32;
			minTemperature = ((minTemperature - 273.15) * (1.8)) + 32;
		}
	}
	
	
	
	/*
	 * Set Weather Icon
	 */
	private static void iconSet(String iconID) throws IOException {
		URL url = new URL("http://openweathermap.org/img/wn/" + iconID + "@2x.png");
	    Image image = ImageIO.read(url);
	    WeatherProGUI.weatherIcon.setIcon(new ImageIcon(image));
	}
	
	
	
	/*
	 * Changes background based on what time it is in the city entered.
	 */
	private static void setBackground() {
		if (iconID.charAt(iconID.length() - 1) == 'd') {
			WeatherProGUI.backgroundImage.setIcon(new ImageIcon(WeatherMethods.class.getResource(("/day.jpg"))));
		} else if (iconID.charAt(iconID.length() - 1) == 'n'){
			WeatherProGUI.backgroundImage.setIcon(new ImageIcon(WeatherMethods.class.getResource(("/night.jpg"))));
		}
		WeatherProGUI.sunriseLabel.setIcon(new ImageIcon(WeatherMethods.class.getResource(("/sunrise.png"))));
		WeatherProGUI.sunsetLabel.setIcon(new ImageIcon(WeatherMethods.class.getResource(("/sunset.png"))));
		WeatherProGUI.sunrise.setText("Sunrise");
		WeatherProGUI.sunset.setText("Sunset");
	}

	
	
	/*
	 * Displays Weather Data on the App Window
	 */
	private static void displayWeatherData(String unit) {
		// Set precision of double value
		DecimalFormat dec = new DecimalFormat("#0.00");
		
		// Display City name with Country
		WeatherProGUI.cityName.setText(city + ", " + country);
		
		// Display description
		WeatherProGUI.descLabel.setText(description.substring(0, 1).toUpperCase()+description.substring(1)+".");
		
		// Display Sunrise and Sunset times
		WeatherProGUI.srTime.setText(sunriseTime);
		WeatherProGUI.ssTime.setText(sunsetTime);
		
		// Display Weather in Text Area of GUI
		WeatherProGUI.textArea.setText("Temperature: " + dec.format(temperature) + "°" + unit
				+ "\nMaximum Temperature: "+ dec.format(maxTemperature) 
				+ "°" + unit + "\nMinimum Temperature: " + dec.format(minTemperature)+ "°" + unit
				+ "\nPressure: " + pressure + "Pa" + "\nHumidity: " + humidity + "%"
				+ "\nWind Speed: " + windSpeed + "m/s" + "\nWind Direction: " + windDirection + "°");
	}
	
	
	
	/*
	 *  Converts Unix Format Time to Normal 12-hr Time
	 */
	public static String timeConvert(int rawTime, int timezone){
		DateFormat dateFormat = new SimpleDateFormat("hh:mm aa");
		dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
	    return dateFormat.format(new Date((rawTime+timezone) * 1000L)).toUpperCase();
	}
	
	
	
	/* 
	 * Get Weather Data
	 */
	public static void getData(String url, String unit) throws Exception {
		try {
			URL obj = new URL(url);
			
			// Read data from the URL.
			BufferedReader sc = 
		            new BufferedReader(new InputStreamReader(obj.openStream())); 
			
			// Trim method is used to remove leading and trailing blank spaces.
		    String response = sc.readLine().trim(); 
		    
		    // Convert the data read from the url into a JSON Object.
		    JSONObject weatherResponse = new JSONObject(response);
		    
		    city = weatherResponse.getString("name");
			country = weatherResponse.getJSONObject("sys").getString("country");
			
			pressure = weatherResponse.getJSONObject("main").getInt("pressure");
			humidity = weatherResponse.getJSONObject("main").getInt("humidity");
			windSpeed = weatherResponse.getJSONObject("wind").getInt("speed");
			windDirection = weatherResponse.getJSONObject("wind").getInt("deg");
			
			timezone = weatherResponse.getInt("timezone");
			sunriseTime = timeConvert(weatherResponse.getJSONObject("sys").getInt("sunrise"), timezone);
			sunsetTime = timeConvert(weatherResponse.getJSONObject("sys").getInt("sunset"), timezone);
			
			temperature = weatherResponse.getJSONObject("main").getDouble("temp");
			maxTemperature = weatherResponse.getJSONObject("main").getDouble("temp_max");
			minTemperature = weatherResponse.getJSONObject("main").getDouble("temp_min");
			iconID = weatherResponse.getJSONArray("weather").getJSONObject(0).getString("icon");
			description = weatherResponse.getJSONArray("weather").getJSONObject(0).getString("description");
			
			setBackground();
			convertUnit(unit);
			iconSet(iconID);
			displayWeatherData(unit);
		} 
		// Throws an Exception if the user enters an INVALID city name.
	    catch (Exception e) { 
	        JOptionPane.showMessageDialog(null, "City not found.");
	    }		
	}
}
