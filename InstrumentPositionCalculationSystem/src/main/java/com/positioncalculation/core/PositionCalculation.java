package com.positioncalculation.core;

/*
 * 
 * @Main class
 * PositionCalculation.java
 * 		This is the class where execution of this project starts.
 * 		This class invokes controller with required configurations. 		
 * 
 * @Param
 * System Parameters:
 * String StartOfDayPositionsFilename : Input file to be scanned.
 * String TransactionsFilename : Input file to be scanned.
 * String EndOfDayPositionsfilename : Input file to be scanned.
 * 
 */

import java.time.LocalDate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.positioncalculation.controller.PositionCalculationController;

public class PositionCalculation extends PositionCalculationInit {
	
	private static Logger logger = LoggerFactory.getLogger(PositionCalculation.class);
	protected static PositionCalculationController controller;
	
	public static void main(String[] args) {
		try {
			/*
			 * Initialize project state by calling init() of super class PositionCalculationInit
			 */
			init();
			
			logger.info("Business Date: {}",LocalDate.now());
			
			controller = new PositionCalculationController();
			

			/*
			 * Invoke controller with configuration provided
			 */
			if(controller.run(config)) {
				logger.info("======================================================================================================================");
				logger.info("Process Completed !!!");
				logger.info("======================================================================================================================");
			}
			else {
				logger.info("-----------------------------------------------------------------------------------------------------------------------");
				logger.info("Process Failed.");
				logger.info("-----------------------------------------------------------------------------------------------------------------------");
			}
				
		}
		catch(Exception e)
		{
			logger.info("Exception:{}",e.getMessage());
		}
	}

}
