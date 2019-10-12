package com.positioncalculation.core;

/*
 * 
 * @Init class
 * PositionCalculationInit.java
 * 		This is the super class of main class.
 * 		This class has responsibility to read the System parameters, prepare the configuration file and initialize the state of project.
 * 
 * @Param
 * System Parameters:
 * String StartOfDayPositionsFilename : Input file to be scanned.
 * String TransactionsFilename : Input file to be scanned.
 * String EndOfDayPositionsfilename : Input file to be scanned.
 * 
 */

import java.io.File;
import java.util.Properties;

import org.apache.log4j.PropertyConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PositionCalculationInit {
	
	protected final static Logger logger = LoggerFactory.getLogger(PositionCalculation.class);
	protected static final String APP_HOME = System.getProperty("app.home", System.getProperty("user.dir"));
	protected static String inputStartOfDayPositionFileName = null;
	protected static String inputTransactionFileName = null;
	protected static String outputEodPositionFileName = null;
	
	protected static String inputStartOfDayPositionFilePath = null;
	protected static String inputTransactionFilePath = null;
	protected static String outputEodPositionsFilePath = null;
	
	protected static Properties config;
		
	protected static void init() {
		try {
			/*
			 * Read Log4j properties and configure it to use logger across the project.
			 */
			String log4jConfPath = APP_HOME+File.separator+"logConfig"+File.separator+"log4j.properties";
			PropertyConfigurator.configure(log4jConfPath);
			
			/*
			 * Read input system parameters and set them in configuration file.
			 * If any required input parameter is null then throw exception.
			 */
			
			inputStartOfDayPositionFileName = System.getProperty("input.init.positions");
			if(inputStartOfDayPositionFileName==null || "".equalsIgnoreCase(inputStartOfDayPositionFileName))
			{
				throw new IllegalArgumentException("Input file name of StartOfDayPositions data cannot be null or blank.");
			}
			inputStartOfDayPositionFilePath = APP_HOME+File.separator+"input"+File.separator+inputStartOfDayPositionFileName;
			logger.info("Start Of Day Position feed: {}",inputStartOfDayPositionFilePath);
			
			inputTransactionFileName = System.getProperty("input.transactions");
			if(inputTransactionFileName==null || "".equalsIgnoreCase(inputTransactionFileName))
			{
				throw new IllegalArgumentException("Input file name of daily transactions data cannot be null or blank.");
			}
			inputTransactionFilePath = APP_HOME+File.separator+"input"+File.separator+inputTransactionFileName;
			logger.info("Daily Transactions feed: {}",inputTransactionFilePath);
			
			outputEodPositionFileName = System.getProperty("output.eod.positions");
			if(outputEodPositionFileName==null || "".equalsIgnoreCase(outputEodPositionFileName))
			{
				throw new IllegalArgumentException("Output file name cannot be null or blank.");
			}
			outputEodPositionsFilePath = APP_HOME+File.separator+"output"+File.separator+outputEodPositionFileName;
			logger.info("End Of Day Positions feed: {}",outputEodPositionsFilePath);
			
			config = new Properties();
			
			config.setProperty("input.init.positions.feed", inputStartOfDayPositionFilePath);
			config.setProperty("input.transactions.feed", inputTransactionFilePath);
			config.setProperty("output.eod.positions.feed", outputEodPositionsFilePath);
			
		}
		catch(IllegalArgumentException e) {
			logger.info("Exception:{}",e.getMessage());
		}
	}
}
