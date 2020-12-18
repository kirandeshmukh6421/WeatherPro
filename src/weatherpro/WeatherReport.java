package weatherpro;

import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class WeatherReport {
	/*
	 * Create Weather Report
	 */
    public static void createReport(String unit){
    	
    	DecimalFormat dec = new DecimalFormat("#0.00");
    	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");  
    	LocalDateTime now = LocalDateTime.now();  
    	
        String message = "<html lang='en' style='text-align: center;'>" + 
        		  "<head>" + 
        		    "<meta charset='UTF-8' />" + 
        		    "<meta name='viewport' content='width=device-width, initial-scale=1.0' />" + 
        		    "<title>Weather Report</title>" + 
        		  "</head>" + 
        		  "<body style='background-color: #cfcfcf;'>" +
        		    "<h1>Weather Report</h1>" +
        		    "<h3 style='text-align: center;'>" + WeatherMethods.city + ", " + WeatherMethods.country + "</h2>" +
        		    "<h4 style='text-align: center;'>" + dtf.format(now) + "</h4>" +
        		    "<div style='border: 2px solid;  width:50%; margin:auto; margin-top: 20px;'>" +
        		        "<p>Temperature: " + dec.format(WeatherMethods.temperature) + "<sup>&#8728</sup>" + unit + "</p>" +
        		        "<p>Maximum Temperature: " + dec.format(WeatherMethods.maxTemperature) + "<sup>&#8728</sup>" + unit + "</p>" +
        		        "<p>Minimum Temperature: " + dec.format(WeatherMethods.minTemperature) + "<sup>&#8728</sup>" + unit + "</p>" +
        		        "<p>Pressure: " + WeatherMethods.pressure + "Pa" + "</p>" +
        		        "<p>Humidity: " + WeatherMethods.humidity + "%" + "</p>" +
        		        "<p>Wind Speed: " + WeatherMethods.windSpeed + "m/s" + "</p>" +
        		        "<p>Wind Direction: " + WeatherMethods.windDirection + "<sup>&#8728</sup>" + "</p>" +
        		        "<p>Sunrise Time: " + WeatherMethods.sunriseTime + "</p>"+
        		        "<p>Sunset Time: " + WeatherMethods.sunsetTime + "</p>"+
        		    "</div>" + 
        		"</body>" + 
        		"</html>";
        
        
        // Write into file
        String fileName = "src/Weather-Report.html"; 
        
        try {
        FileWriter fw = new FileWriter(fileName);
        for (int i = 0; i < message.length(); i++) {
            fw.write(message.charAt(i));
        	}
        fw.close();
        } catch (IOException e) {
        	e.printStackTrace();
        }
        
    }
}


