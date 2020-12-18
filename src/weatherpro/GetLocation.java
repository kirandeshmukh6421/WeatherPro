package weatherpro;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

import org.json.JSONObject;

public class GetLocation {
	
	/*
	 * Get User's IP Address
	 */
	private static String getIP() throws Exception 
    { 
        String IPAddress = ""; 
        try
        { 
            URL url = new URL("http://bot.whatismyipaddress.com");
            BufferedReader sc = 
            new BufferedReader(new InputStreamReader(url.openStream())); 
            // Reads system IPAddress 
            IPAddress = sc.readLine().trim(); 
        } 
        catch (Exception e) 
        { 
            IPAddress = "Cannot Execute Properly"; 
        }  
        return IPAddress;
    } 
	
	
	
	/*
	 * Get User's Location
	 */
	public static String getCity() throws Exception {
			String url = "http://ip-api.com/json/" + getIP();
			URL obj = new URL(url);
			
			// Read data from the URL.
			BufferedReader sc = 
		            new BufferedReader(new InputStreamReader(obj.openStream())); 
			
			// Trim method is used to remove leading and trailing blank spaces.
		    String response = sc.readLine().trim(); 
		    
		    // Convert the data read from the url into a JSON Object.
		    JSONObject URLResponse = new JSONObject(response);
		    
			return URLResponse.getString("city");	
	}		
}
