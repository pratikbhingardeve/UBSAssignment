package com.positioncalculation.impl;

/*
 * @Dao
 * FileOperationDAO.java
 * 		This is the DAO implementation.
 * 		This class takes care of reading StartOfDayPosition, Transaction file and write output in EODPoition file.		
 * 
 */

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.positioncalculation.dao.FileOperationDAO;
import com.positioncalculation.dto.DTO;

public class PositionCalculationDAO extends FileOperationDAO {

	private Logger logger = LoggerFactory.getLogger(PositionCalculationDAO.class);
	private StringBuilder strBuf = new StringBuilder();
	private File outputFile = null;
	
	@Override
	public boolean writeFile(List<DTO> dtos, String eodPositionOutputFileName) throws IOException {
		try {
				logger.info("Writing Eod Positions into output files ...");
				
				/*
				 * Check if given output file is present. If not generate it.
				 */
				outputFile = new File(eodPositionOutputFileName);
				if (!outputFile.exists()) {
					outputFile.createNewFile();
				}
				
				/*
				 * Initialize parent DAO class state.
				 * 	Initialize BufferedWritter to write DTO state in given output file. 
				 */
				init(outputFile);
				
				/*
				 * Write header at start of file.
				 */
				addHeader();
				
				dtos.forEach((t)->{
					try {
						writeAndAppend(t);
					} catch (IOException e) {
						e.printStackTrace();
					}
				});
				
				end();
				return true;
		}
		catch(Exception e) {
			logger.info("Exception: {}",e.getMessage());
			return false;
		}
	}

	@Override
	public Stream<String> readFile(String startOfDayPositionFilePath) throws IOException {
		/*
		 * Using Files class generate stream<String> of data in input file: StartOfDayPosition
		 * Each row in file will be treated as seperate String in stream object.
		 */
		return Files.lines(Paths.get(startOfDayPositionFilePath));
	}
	
	@SuppressWarnings("unchecked")
	public Stream<String> readJsonFile(String transactionFilePath) throws IOException {

		JSONParser parser = new JSONParser();
		List<String> allTransactions = new ArrayList<>();
		
        try {
 
            Object obj = parser.parse(new FileReader(transactionFilePath));
            /*
             * Read input file: Transactions.
             * Parse its Json content and read all transaction as JsonObject  
             */
            JSONArray jsonArray = (JSONArray) obj;
            
            jsonArray.forEach((transaction)->{
            	Object object = (Object)transaction;
            	JSONObject jObj = (JSONObject) object;
            	/*
            	 * From Json object read the values, prepare string and add into List
            	 */
            	allTransactions.add(jObj.values().toString());
            }
            );
        } 
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        
        parser.reset();
        
        /*
         * Return stream of List<String>.
         * Each string in stream is transaction itself
         * 
         * Java has provided strong library to process stream.
         * To process each transaction against all instruments account, stream provides better way of filtering and manipulation.
         * Thats in spite of returning List<String>, stream<String> has returned.
         * 
         */
        return allTransactions.stream();
	}
	
	@Override
	protected void addHeader() throws IOException {
		strBuf.setLength(0);
		strBuf.append("Instrument").append("\t");
		strBuf.append("Account").append("\t");
		strBuf.append("AccountType").append("\t");
		strBuf.append("Quantity").append("\t");
		strBuf.append("Delta");
		strBuf.append("\r\n");
		
		writer.write(strBuf.toString());		
	}
	
	@Override
	protected void writeAndAppend(DTO dto) throws IOException {
		
		InstruemntAccountPositionDTO endOfDayPositiondto = (InstruemntAccountPositionDTO)dto;
		
		strBuf.setLength(0);
		strBuf.append(endOfDayPositiondto.getInstrument()).append("\t");
		strBuf.append(endOfDayPositiondto.getAccount()).append("\t");
		strBuf.append(endOfDayPositiondto.getAccountType()).append("\t");
		strBuf.append(endOfDayPositiondto.getQty()).append("\t");
		strBuf.append(endOfDayPositiondto.getDelta());
		strBuf.append("\r\n");
		
		writer.write(strBuf.toString());		
	}
}
