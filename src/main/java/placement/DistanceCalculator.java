package placement;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

class DistanceCalculator
{
	String key;
	String mode;
	public DistanceCalculator(String key, String mode) {
		this.key = key;
		this.mode = mode;
	}
	public long getDistance(String fromPostcode, String toPostcode)
	{
		//inline will store the JSON data streamed in string format
		String inline = "";
		long distance = 0;
	
		try
		{
			// mode drive, transit
			URL url = new URL("https://maps.googleapis.com/maps/api/distancematrix/json?origins=?origins=" + fromPostcode + "&destinations=" + toPostcode + "&mode=" + mode + "&key=" + key);
			//Parse URL into HttpURLConnection in order to open the connection in order to get the JSON data
			HttpURLConnection conn = (HttpURLConnection)url.openConnection();
			//Set the request to GET or POST as per the requirements
			conn.setRequestMethod("GET");
			//Use the connect method to create the connection bridge
			conn.connect();
			//Get the response status of the Rest API
			int responsecode = conn.getResponseCode();
			System.out.println("Response code is: " +responsecode);
			
			//Iterating condition to if response code is not 200 then throw a runtime exception
			//else continue the actual process of getting the JSON data
			if(responsecode != 200) {
				return 0;
			}
			else
			{
				//Scanner functionality will read the JSON data from the stream
				Scanner sc = new Scanner(url.openStream());
				while(sc.hasNext())
				{
					inline+=sc.nextLine();
				}
				System.out.println("\nJSON Response in String format"); 
				System.out.println(inline);
				//Close the stream when reading the data has been finished
				sc.close();
			}
			
			//JSONParser reads the data from string object and break each data into key value pairs
			JSONParser parse = new JSONParser();
			//Type caste the parsed json data in json object
			JSONObject jobj = (JSONObject)parse.parse(inline);
			JSONObject rowObj = (JSONObject) ((JSONArray) jobj.get("rows")).get(0);
			JSONObject elementObj = (JSONObject) ((JSONArray) rowObj.get("elements")).get(0);
			JSONObject distanceObj = (JSONObject) elementObj.get("distance");
			 distance = ((Long)distanceObj.get("value")).longValue();
			
			System.out.println("distance ["+distance+" m]");
			
			//Disconnect the HttpURLConnection stream
			conn.disconnect();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return distance;
	}
}