package com.test.testpositioncalculation;

import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

import com.positioncalculation.util.Utility;

public class TestPositionCalculation {
	
	
	static Utility util = null;
	static Integer expectedResult, actualResult;
	static Integer qty;
	
	@BeforeClass
	public static void init() {
		util = new Utility();
	}
	
	@Test
	public void PositiveTestPositionCalculation() {
		
		qty = 20500; 
		actualResult = qty + util.calculateRelativePosition("B", "E", 35000);
		expectedResult = 55500;
		
		assertEquals(expectedResult,actualResult);
		
		qty = 20500; 
		actualResult = qty + util.calculateRelativePosition("B", "I", 5000);
		expectedResult = 15500;
		
		assertEquals(expectedResult,actualResult);
		
		qty = 18000; 
		actualResult = qty + util.calculateRelativePosition("S", "E", 26500);
		expectedResult = -8500;
		
		assertEquals(expectedResult,actualResult);
		
		qty = 21520; 
		actualResult = qty + util.calculateRelativePosition("S", "I", 5000);
		expectedResult = 26520;
		
		assertEquals(expectedResult,actualResult);
		
	}
	
	@Test
	public void NegativePositionCalculation(){
		
		qty = 21520; 
		actualResult = qty + util.calculateRelativePosition("Z", "I", 5000);
		expectedResult = 21520;
		
		assertEquals(expectedResult,actualResult);
		
		actualResult = qty + util.calculateRelativePosition("S", "X", 5000);
		expectedResult = 21520;
		
		assertEquals(expectedResult,actualResult);
	}

	@Test(expected = NullPointerException.class)
	public void NullTransactionQtyTest() {
		actualResult = qty + util.calculateRelativePosition("B", "E", null);
		assertEquals(expectedResult,actualResult);
	}
	
}
