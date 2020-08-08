package com.me.api.http.openroutes;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.me.api.dto.Location;

/**
 * Singelton Class for Network-Aware REST Services to call openrouteservice operations
 * 
 * 
 * @author Hisham Marie
 *
 */
public class ServiceRequest {
	
	
    private static final ServiceRequest instance = new ServiceRequest();
    
    //private constructor to avoid client applications to use constructor
    private ServiceRequest(){}

    public static ServiceRequest getInstance(){
        return instance;
    }
    
    private static String encodeValue(String value) {
        try {
            return URLEncoder.encode(value, StandardCharsets.UTF_8.toString());
        } catch (UnsupportedEncodingException ex) {
            throw new RuntimeException(ex.getCause());
        }
    }

    /**
     * Get city by name
     * This will return the most first city returned by openrouteservice
     * @param name : City Name
     * @return JSON Object for the city returned by openrouteservice
     */
    public JSONObject callGetCityByName(String name){
    	
    	String key = "5b3ce3597851110001cf624806334f9f20a644969d2bbc120f02dc2d";
    	//String key = System.getenv("ORS_TOKEN");
    	//System.out.println("["+name+"]");
    	name = encodeValue(name.trim());
    	//System.out.println("["+name+"]");
    	//System.out.println("CityName="+name);
    	Client client = ClientBuilder.newClient();
    	 
    	//https://api.openrouteservice.org/geocode/search?api_key=5b3ce3597851110001cf624806334f9f20a644969d2bbc120f02dc2d&text=Hamburg 
    
    	Response response = client.target("https://api.openrouteservice.org/geocode/search?api_key="+key+"&text="+name)
    	  .request(MediaType.TEXT_PLAIN_TYPE)
    	  .header("Accept", "application/json, application/geo+json, application/gpx+xml, img/png; charset=utf-8")
    	  .get();
    	
    	
    	
		int status =  (int)response.getStatus();
		if (status != 200) {
			System.out.println("HTTP1 Status is not 200, the status is:" + status); 
			return null;
		}
		
		
		String jsonResponse = response.readEntity(String.class);
		//System.out.println("body:" + jsonResponse);
		
		
		return convertStringtoJSON(jsonResponse);
    	
    	
    }
    
    
    /**
     * Get the distance between two location
     * @param l1 : Location 1
     * @param l2 : Location 2
     * @return JSON Object for openrouteservice distance 
     */
    public JSONObject callGetDistinationBetweenTwoPoints(Location l1, Location l2){
    	
    	
		String myJSON2 = "{\"locations\":[["+l1.getLongitute()+","+l1.getLatitude()+"],["+l2.getLongitute()+","+l2.getLatitude()+"]],\"metrics\":[\"distance\"],\"units\":\"km\"}";
		
    	String key = "5b3ce3597851110001cf624806334f9f20a644969d2bbc120f02dc2d";
    	//String key = System.getenv("ORS_TOKEN");

		Client client = ClientBuilder.newClient();
		Entity<String> payload = Entity.json(myJSON2);
		Response response = client.target("https://api.openrouteservice.org/v2/matrix/driving-car")
		  .request()
		  .header("Authorization", key)
		  .header("Accept", "application/json, application/geo+json, application/gpx+xml, img/png; charset=utf-8")
		  .header("Content-Type", "application/json; charset=utf-8")
		  .post(payload);

		
		int status =  (int)response.getStatus();
		if (status != 200) {
			System.out.println("HTTP Status is not 200, the status is:" + status);
			return null;
		}
		//System.out.println("status: " + response.getStatus());
		//System.out.println("headers: " + response.getHeaders());
		String jsonResponse = response.readEntity(String.class);
		//System.out.println("body:" + jsonResponse);
		
		return convertStringtoJSON(jsonResponse);
    	
    }
    
    private JSONObject convertStringtoJSON(String str) {
    	  
    	  JSONParser parser = new JSONParser();
    	  JSONObject jsonObject = null;
    	  try {
    		  jsonObject = (JSONObject) parser.parse(str);
    	  } catch (ParseException e) {
    		  // TODO Auto-generated catch block
    		  e.printStackTrace();
    	  }
    	  
    	  
    	  return jsonObject;
    	  
      }

}
