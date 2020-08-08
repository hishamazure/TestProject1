package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class TestCase1 {

	@Test
	public void test() {
		Junit test = new Junit();
		
		String result = test.TestCO2();
		assertEquals("Your trip caused 49.2kg of CO2-equivalent", result);
		
		
	}

}
