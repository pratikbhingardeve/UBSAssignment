package com.positioncalculation.controller;

/*
 * 
 * @Controller class
 * PositionCalculationController.java
 * 		This class has responsibility to handle user request to calculate EODPositions.
 * 		This class will invoke service layer prepare and process input files and generate output file.
 * 
 * @Param
 * Properties config : Input file to be scanned.
 * 
 */

import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.positioncalculation.service.PositionCalculationService;

public class PositionCalculationController {
	
	private Logger logger = LoggerFactory.getLogger(PositionCalculationController.class);
	private PositionCalculationService service =  new PositionCalculationService(); 
	private String inputStartOfDayPositionFileName;
	private String inputTransactionsFileName;
	private String outputEndOfDayPositionFileName;
	
	public boolean run(Properties config) {
		inputStartOfDayPositionFileName = config.getProperty("input.init.positions.feed");
		inputTransactionsFileName=config.getProperty("input.transactions.feed");
		outputEndOfDayPositionFileName=config.getProperty("output.eod.positions.feed");
		
		try {
			/*
			 * Invoke service layer interface to read and prepare StartOfDayPositions for processing.
			 */
			if(service.prepareStartOfDatPositionDataForProcessing(inputStartOfDayPositionFileName)) {
				logger.info("Input Start Of Day Positions feed file successufully read:{}",inputStartOfDayPositionFileName);
			}
			else {
				logger.info("Falied to read Start Of Day Positions feed file:{}",inputStartOfDayPositionFileName);
				return false;
			}
			
			/*
			 * Invoke service layer interface to read and prepare transctions for processing.
			 */
			if(service.prepareTransactionDataForProcessing(inputTransactionsFileName)) { 
				logger.info("Input Daily Transactions feed file successufully read:{}",inputTransactionsFileName);
			}
			else {
				logger.info("Failed to read Input Daily Transactions feed file:{}",inputTransactionsFileName);
				return false;
			}
			
			/*
			 * Invoke service layer interface to calculate EOD positions and generate output feed.
			 */
			if(service.calculateEodPositionAndPrepareOutputFeed(outputEndOfDayPositionFileName)) {
				logger.info("EOD Instrument Account's position's Output Feed file successfully generated:{}",outputEndOfDayPositionFileName);
			}
			else {
				logger.info("Failed to calculate EOD position and generate output feed file:{}",outputEndOfDayPositionFileName);
				return false;
			}
			
			return true;
		}
		catch(Exception e) {
			logger.info("Exception: {}",e.getMessage());
			return false;
		}
	}

}
