package com.test.testpositioncalculation;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import java.io.File;
import java.util.Properties;

import org.junit.BeforeClass;
import org.junit.Test;

import com.positioncalculation.service.PositionCalculationService;

public class TestService {
	
	final static String APP_HOME = System.getProperty("user.dir");
	static Properties config = null;
	static PositionCalculationService service;
	
	@BeforeClass
	public static void init() {
		
		service = new PositionCalculationService();
		config = new Properties();
		config.setProperty("input.init.positions.feed", APP_HOME+File.separator+"testInputFiles"+File.separator+"TestCase1_InputStartOfDayPositions.txt");
		config.setProperty("input.transactions.feed",APP_HOME+File.separator+"testInputFiles"+File.separator+"TestCase1_Transactions.txt");
		config.setProperty("output.eod.positions.feed", APP_HOME+File.separator+"testOutputFiles"+File.separator+"TestCase1_OutptuEoDOfDayPositions");
		
	}
	
	@Test
	public void TestServiceProcessStartOfDayPosition() {
		assertTrue(service.prepareStartOfDatPositionDataForProcessing(config.getProperty("input.init.positions.feed")));
	}
	
	@Test
	public void TestServiceProcessTransactions() {
		assertTrue(service.prepareTransactionDataForProcessing(config.getProperty("input.transactions.feed")));
	}
	
	@Test
	public void TestServiceCalculatePositionAndGenerateOutput() {
		assertTrue(service.calculateEodPositionAndPrepareOutputFeed(config.getProperty("output.eod.positions.feed")));
	}
	
	@Test
	public void FailTestServiceProcessStartOfDayPosition() {
		assertFalse(service.prepareStartOfDatPositionDataForProcessing(config.getProperty("input.transactions.feed")));
	}
	
	
}
