package com.me;

import java.io.IOException;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.json.simple.JSONObject;

import com.me.api.dto.City;
import com.me.api.dto.Distance;
import com.me.api.http.openroutes.ServiceRequest;
import com.me.json.JSONDTOConvertor;
import com.me.util.ConfigReader;
import com.me.util.NumberHelper;


/**
 * The main class that has the business of calculation the total CO2 for transportation mean between two cities
 * 
 * 
 * 
 * @author Hisham Marie
 *
 */
public class Tester1 {


  public static void main(String[] args) throws IOException {
	  
	  
	  String token = System.getenv("ORS_TOKEN");
	  if (token == null || token.trim().length() == 0) {
          //System.out.println("Please set the API token in ORS_TOKEN environment variable");
          //System.exit(1);
		  
	  }
	  
	  
      Options options = new Options();

      Option s = new Option("s", "start", true, "The name of the city to start from");
      s.setRequired(true);
      options.addOption(s);

      Option e = new Option("e", "end", true, "The name of the destination City");
      e.setRequired(true);
      options.addOption(e);

      Option method = new Option("method", "transportation-method", true, "The Transportation Method");
      method.setRequired(true);
      options.addOption(method);

      
      CommandLineParser parser = new DefaultParser();
      HelpFormatter formatter = new HelpFormatter();
      CommandLine cmd = null;

      try {
          cmd = parser.parse(options, args);
      } catch (Exception ex) {
          System.out.println(ex.getMessage());
          formatter.printHelp("CO2 Calculator - Hisham Marie", options);

          System.exit(1);
          
      }

      String city1 = cmd.getOptionValue("start");
      String city2 = cmd.getOptionValue("end");
      String transMethod = cmd.getOptionValue("transportation-method");

      
      String out = getCO2Emission(city1.trim(), city2.trim(), transMethod.trim());
      System.out.println(out);
      
	  
  }
  

  /**
   * Get the CO2 emission
   * @param city1Var : Start City
   * @param city2Var : End City
   * @param transMethod : Transportation Method
   * @return
   * @throws IOException
   */
  public static String getCO2Emission(String city1Var, String city2Var, String transMethod) throws IOException {
 	  String trasMethod = transMethod;  
	  String cityName1 = city1Var;
	  String cityName2 = city2Var;
	  
	  String co2InGram = ConfigReader.getCO2ByTrasponrtName(trasMethod);
	  if (co2InGram == null || co2InGram.trim().length() == 0) {
		  System.out.println("Please enter a valid transportation method");
		  System.exit(-1);
	  }
	  
	  
	  /**
	   * Get the first city location from openrouteservice API
	   */
	  JSONObject jsonCity1 = ServiceRequest.getInstance().callGetCityByName(cityName1);
	
	  City city1 = null;
	  if (jsonCity1 == null) {
		  System.out.println("Problem while getting the location of city 1. Please try again");
		  System.exit(-1);
	  } else {
		  city1 =  JSONDTOConvertor.getInstance().getCityFromJSON(jsonCity1);
	  }
	  
	  if (city1 == null) {
		  System.out.println("We could not find start city ("+cityName1+"), please try different name");
		  System.exit(-1);
	  }
	  
	  
	  /**
	   * Get the second city location from openrouteservice API
	   */
	  JSONObject jsonCity2 = ServiceRequest.getInstance().callGetCityByName(cityName2);
	  
	  City city2 = null;
	  if (jsonCity2 == null) {
		  System.out.println("Problem while getting the location of city 2. Please try again");
		  System.exit(-1);
	  } else {
		  city2 =  JSONDTOConvertor.getInstance().getCityFromJSON(jsonCity2);
	  }
	  
	  if (city2 == null) {
		  System.out.println("We could not find end city ("+cityName2+"), please try different name");
		  System.exit(-1);
	  }
	  
	
	  
	  
	  /**
	   * Get the distance between the two cities by calling openrouteservice API
	   */
	  JSONObject distanceResponse = ServiceRequest.getInstance().callGetDistinationBetweenTwoPoints(city1.getLocation(), city2.getLocation());	
	  if (distanceResponse == null) {
		  System.out.println("There was a problem while getting the distance between the two cities, please try again!");
		  System.exit(-1);
	  }
	  Distance distanceDto = JSONDTOConvertor.getInstance().getDistanceFromJSON(distanceResponse);
	  if (distanceDto == null) {
		  System.out.println("There was a problem while getting the distance between the two cities, please try again!!");
		  System.exit(-1);
	  }	  
	  //System.out.println("Distance in KM " + distanceDto.getDistinationInKM());
	  
	  
	  
	  int co2perkm  = 0;
	  try {
		  co2perkm = Integer.parseInt(co2InGram);
	  } catch (NumberFormatException e){
		  System.out.println("Please make sure that the Transportation methods number in the property file is Integer");
		  System.exit(-1);
	  }
	  
	  double distinationInKM = 0.0;
	  try {
		  distinationInKM = Double.parseDouble(distanceDto.getDistinationInKM());
	  } catch (NumberFormatException e){
		  System.out.println("The number returned from the service API is not valid!");
		  System.exit(-1);
	  }
	  
	  double amountOfCO2 = (distinationInKM * co2perkm)/1000;
	  double amountOfCO2Rounded = NumberHelper.round(amountOfCO2, 1);
	  return "Your trip caused " + amountOfCO2Rounded + "kg of CO2-equivalent";
	  
  }
  
  
}