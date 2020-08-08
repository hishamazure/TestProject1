package com.me.json;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.me.api.dto.City;
import com.me.api.dto.Distance;
import com.me.api.dto.Location;

/**
 * A singleton class with eager initialization .
 * 
 * This class will have the logic of extracting data from JSON object, returned from api.openrouteservice.org/geocode, to CityDTO or other DTOs
 * 
 * @author Hisham Marie
 *
 */
public class JSONDTOConvertor {
	
    private static final JSONDTOConvertor instance = new JSONDTOConvertor();
    
    //private constructor to avoid client applications to use constructor
    private JSONDTOConvertor(){}

    public static JSONDTOConvertor getInstance(){
        return instance;
    }
    
    
    public Distance getDistanceFromJSON(JSONObject jsonObject) {
    	
    	
    	if (jsonObject == null) {
    		return null;
    	}
    	
    	Distance distance = new Distance();
    	
    	


        
       
        
        JSONArray distancesArr = (JSONArray)jsonObject.get("distances");
        //System.out.println(distancesArr.get(0));
        JSONArray odistancesArr2 = (JSONArray)distancesArr.get(0);
        //System.out.println(odistancesArr2.get(0));
        
        //System.out.println(odistancesArr2.get(1));
        distance.setDistinationInKM(String.valueOf(odistancesArr2.get(1)));
	        
    	
    	return distance;
    }
    
    public City getCityFromJSON(JSONObject obj) {
    	
    	if (obj == null) {
    		return null;
    	}
    	
    	
   

		JSONArray features = (JSONArray)(obj.get("features"));
		//System.out.println(features.size());
		
		if (features == null || features.size() ==0) {
			return null;
		}
		
		JSONObject a = (JSONObject)features.get(0);
		//System.out.println("a:" + a.toString());
		JSONObject b = (JSONObject)a.get("geometry");
		//System.out.println("b:" + b.toString());
		JSONArray c = (JSONArray)b.get("coordinates");
		
		
		//System.out.println("longitude:" + c.get(0));
		//System.out.println("latitude:" +c.get(1));
    	    
    	
    	City cityDto = new City();
    	cityDto.setLocation(new Location(String.valueOf(c.get(1)), String.valueOf(c.get(0))));

    	
    	return cityDto;
    }

    

    
    

}
