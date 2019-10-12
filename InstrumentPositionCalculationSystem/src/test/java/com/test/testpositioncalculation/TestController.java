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
	
	@Test
	public void FailTestControllerRunMethod() {
		config.setProperty("input.transactions.feed",APP_HOME+File.separator+"testInputFiles"+File.separator+"TestCase1_InputStartOfDayPositions.txt");
		assertFalse(controller.run(config));
	}
	
	
}
