package com.positioncalculation.service;

/*
 * 
 * @Service class
 * PositionCalculationService.java
 * 		This is service layer.
 * 		This class has responsibility to communicate with dao to read inputs and write output data.
 * 		This class also provides interface to invoke business logic to process transactions.
 * 
 */

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.positioncalculation.dao.FileOperationDAO;
import com.positioncalculation.dto.DTO;
import com.positioncalculation.impl.InstruemntAccountPositionDTO;
import com.positioncalculation.impl.InstrumentAccountDTO;
import com.positioncalculation.impl.PositionCalculationDAO;
import com.positioncalculation.impl.TransactionsDTO;
import com.positioncalculation.util.Utility;

public class PositionCalculationService {
	
	private Logger logger = LoggerFactory.getLogger(PositionCalculationService.class);
	
	private Map<InstrumentAccountDTO, DTO> outputEndOfDayPosition = new HashMap<>();
	private List<TransactionsDTO> inputTransctions = new ArrayList<>();
	
	private FileOperationDAO dao = new PositionCalculationDAO();
	private Utility util = new Utility();
	
	public boolean prepareStartOfDatPositionDataForProcessing(String startOfDayPositionFilePath) {
		try {
			/*
			 * Invoke DAO interface to read StartOfDayPosition input file.
			 */
			Stream<String> startOfDayPositionFileStream = dao.readFile(startOfDayPositionFilePath);
			
			/*
			 * Prepare Output DTOs using initial positions upon which transactions will be applied. 
			 */
			prepareMapOfInstrumentsInitPositions(startOfDayPositionFileStream.skip(1).collect(Collectors.toList()));
			return true;
		} catch (Exception e) {
			logger.info("Exception: {}",e.getMessage());
			return false;
		}
		
	}
	
	public boolean prepareTransactionDataForProcessing(String transactionsFilePath) {
		try {
			/*
			 * Invoke DAO interface to read Transactions input file.
			 */
			Stream<String> transactionsFileStream = dao.readJsonFile(transactionsFilePath);
			
			/*
			 * Prepare transaction DTOs.
			 * Each transaction will be applied to Positions available in each Output DTOs.
			 */
			prepareTransctions(transactionsFileStream.collect(Collectors.toList()));
			return true;
		} catch (IOException e) {
			logger.info("Exception: {}",e.getMessage());
			return false;
		}
	}
	
	private void prepareTransctions(List<String> transactions) {
			
		transactions.forEach((transaction)->{
			/*
			 * Read Transaction
			 * Prepare DTO
			 * Add it into List<TransactionDTO>
			 */
			String[] transactionFields =  transaction.substring(1,transaction.length()-1).split(",");
			
			TransactionsDTO transactionDto = new TransactionsDTO();
			transactionDto.setTransactionId(Integer.parseInt(transactionFields[3].trim()));
			transactionDto.setInstrument(transactionFields[2].trim());
			transactionDto.setTransactionType(transactionFields[0].trim());
			transactionDto.setTransactionQty(Integer.parseInt(transactionFields[1].trim()));
			
			inputTransctions.add(transactionDto);
			
		});
		logger.info("Number of transactions recieved: {}", inputTransctions.size());
	}

	private void prepareMapOfInstrumentsInitPositions(List<String> list) {

		list.forEach((instrumentObj)->{
			/*
			 * Read Input Start Of day positions.
			 * Add these positions in Output DTO as initial state of Instrument Account positions.
			 * This output DTO will be manipulated by applying transactions it.
			 * After processing of all transactions, final start of Output DTOs will be the EOD day positions of Instrument Accounts.
			 * 
			 * This initial state of every Instrument's position is maintained in map as key-value pair, where every Key is (Instrument,AccountId) 
			 * to uniquely identify each instrument account.
			 */
			String[] instrumentFields =  instrumentObj.split(",");
			
			InstrumentAccountDTO instrumentKeyDTO = new InstrumentAccountDTO(instrumentFields[0],Integer.parseInt(instrumentFields[1]));
			
			InstruemntAccountPositionDTO eodInstrumentPositionDTO = new InstruemntAccountPositionDTO();
			eodInstrumentPositionDTO.setInstrument(instrumentFields[0]);
			eodInstrumentPositionDTO.setAccount(Integer.parseInt(instrumentFields[1]));
			eodInstrumentPositionDTO.setAccountType(instrumentFields[2]);
			eodInstrumentPositionDTO.setQty(Integer.parseInt(instrumentFields[3]));
			eodInstrumentPositionDTO.setDelta(0); 
			outputEndOfDayPosition.put(instrumentKeyDTO, eodInstrumentPositionDTO);
		});
	}

	public boolean calculateEodPositionAndPrepareOutputFeed(String outputFileName) {
		
		try {	
			
			/*
			 * At this stage we are ready with Transaction DTOs contains all transactions and Output DTOs contains initial state of Instrument position.
			 * Invoke interface to apply transactions to initial Instrument's position and calculate EOD Instrument positions.
			 */
			calculateEodPositions();
			logger.info("EOD positions successfully calculated.");
			
			/*
			 * Invoke DAO interface to state of output DTO in output file.
			 */
			Boolean result = dao.writeFile(outputEndOfDayPosition.values().stream().collect(Collectors.toList()), outputFileName);
			
			if(result)
				return true;
			else
				return false;
			
		} catch (Exception e) {
			logger.info("Exception: {}",e.getMessage());
			return false;
		}
	}

	private void calculateEodPositions() {
		logger.info("Calculating EOD Positions of all Instrument Accounts ...");
		
		if(inputTransctions!=null || inputTransctions.size()>0) {
			
			inputTransctions.forEach((transaction)->{
				/*
				 * For each transaction filter Output DTO map and find out all eligible Instruments for which that transaction is applicable.
				 */
				outputEndOfDayPosition.keySet().stream()
						.filter((keyDto)->keyDto.getInstrument().equalsIgnoreCase(transaction.getInstrument()))
						.forEach((dto)->{
							/*
							 * On all applicable Instruments, apply transactions by calling business logic to find relative position and add that position to initial 
							 * position of that instrument.
							 */
							InstruemntAccountPositionDTO endOfDayPositiondto = (InstruemntAccountPositionDTO) outputEndOfDayPosition.get(dto);
							Integer relativeQty = util.calculateRelativePosition(transaction.getTransactionType(),endOfDayPositiondto.getAccountType(),
																										transaction.getTransactionQty());
							
							endOfDayPositiondto.setQty(endOfDayPositiondto.getQty()+relativeQty);
							endOfDayPositiondto.setDelta(endOfDayPositiondto.getDelta()+relativeQty);
							outputEndOfDayPosition.put(dto, endOfDayPositiondto);
							
						});
			});
		}
	}

	
}

			