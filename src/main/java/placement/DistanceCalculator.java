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
	
	public static void main(String[] args) {
		DistanceCalculator dc = new DistanceCalculator("AIzaSyAt08icyRLfS-8bGNNaqqJmg1r4UPuPkjs","walking");
		dc.getDistance("HA53YJ", "NW32UB");
		dc.getDistance("W1D4LR", "NW32UB");
		dc.getDistance("NW32UB", "W1D4LR");
		dc.getDistance("HA53YJ", "SE91JE");
		dc.getDistance("W1D 4LR", "SE9 1JE");
		dc.getDistance("SE91JE", "W1D4LR");
	}
	public DistanceCalculator(String key, String mode) {
		this.key = key;
		this.mode = mode;
	}
		
	public long getDistance(String postcode1, String postcode2)
	{
		//inline will store the JSON data streamed in string format
		String inline = "";
		long distance = 0;
		postcode1 = postcode1.toUpperCase().replaceAll("\\s+","");
		postcode2 = postcode2.toUpperCase().replaceAll("\\s+","");
		int comparison = postcode1.compareTo(postcode2);
		String fromPostcode;
		String toPostcode;
		System.out.println("Comparison =" + comparison);
		if (comparison == 0) {
			return 1; // don't return 0 as that is "not found"
		}
		else if (comparison > 0) {
			fromPostcode = postcode2;
			toPostcode = postcode1;
		}
		else {
			fromPostcode = postcode1;
			toPostcode = postcode2;
		}
		System.out.println(fromPostcode + "->" + toPostcode);
	
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
//			System.out.println("Response code is: " +responsecode);
			
			//Iterating condition to if response code is not 200 then throw a runtime exception
			//else continue the actual process of getting the JSON data
			if(responsecode != 200) {
				System.out.println("distance [NOT FOUND]");

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
//				System.out.println("\nJSON Response in String format"); 
//				System.out.println(inline);
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
//			e.printStackTrace();
			System.out.println("distance [ERROR]");

		}
		return distance;
	}
}