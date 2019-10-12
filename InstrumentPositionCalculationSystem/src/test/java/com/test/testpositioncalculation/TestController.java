package com.test.testpositioncalculation;

import java.io.File;
import java.util.Properties;

import org.junit.BeforeClass;
import org.junit.Test;

import com.positioncalculation.controller.PositionCalculationController;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

public class TestController {
	
	final static String APP_HOME = System.getProperty("user.dir");
	static PositionCalculationController controller = null;
	static Properties config = null;
	
	@BeforeClass
	public static void init() {
		
		controller = new PositionCalculationController();
		config = new Properties();
		config.setProperty("input.init.positions.feed", APP_HOME+File.separator+"testInputFiles"+File.separator+"TestCase1_InputStartOfDayPositions.txt");
		config.setProperty("input.transactions.feed",APP_HOME+File.separator+"testInputFiles"+File.separator+"TestCase1_Transactions.txt");
		config.setProperty("output.eod.positions.feed", APP_HOME+File.separator+"testOutputFiles"+File.separator+"TestCase1_OutptuEoDOfDayPositions");
		
	}
	
	
	@Test
	public void TestControllerRunMethod() {
		assertTrue(controller.run(config));
	}
	
	/*
	 * This method could show you error messages during build if uncomment it before build, because it is
	 * tend to fail. Although build will be successfully as there is no any error in code.
	 * 
	 * Uncomment this method during testing only.
	 * 
	 * 
	@Test
	public void FailTestControllerRunMethod() {
		config.setProperty("input.transactions.feed",APP_HOME+File.separator+"testInputFiles"+File.separator+"TestCase1_InputStartOfDayPositions.txt");
		assertFalse(controller.run(config));
	}
	*/
	
	
}
