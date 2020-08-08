package test;

import java.io.IOException;

import com.me.Tester1;

/**
 * Tester class using Junit
 * @author Hisham Marie
 *
 */
public class Junit {
	
	public String TestCO2() {
		
		Tester1 tester1 = new Tester1();
		try {
			return tester1.getCO2Emission("Hamburg", "Berlin", "medium-diesel-car");
			
			
			
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "NOTHING";
	}
	
	public String TestCO2Again() {
		
		return "B";
	}

}
