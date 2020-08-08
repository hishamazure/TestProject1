package com.me.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 
 * @author Hisham Marie
 *
 */
public class ConfigReader {
	static Properties  p=new Properties();
	
	static {
		try {
			
	        InputStream input = ConfigReader.class.getClassLoader().getResourceAsStream("com/me/util/resources/Transportation_methods.properties");
	        if (input == null) {
	        	
                System.out.println("Sorry, unable to find Transportation_methods.properties, pleae add the file 'Transportation_methods.properties' to com/me/util/resources/ ");
                
            } else {
            	p.load(input);
            }
		
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static void main(String []aa) {
		System.out.println(getProperty("medium-diesel-car"));
	}
	
	public static String getProperty(String name) {
		if (p.get(name) != null){
			return (String)p.get(name);
		} else {
			
			return "";
		}
	}
	
	public static String getCO2ByTrasponrtName(String name) {		
		return getProperty(name);
	}
}
