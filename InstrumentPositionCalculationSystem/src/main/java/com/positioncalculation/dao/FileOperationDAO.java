package com.positioncalculation.dao;

/*
 * @Dao
 * FileOperationDAO.java
 * 		This is the DAO implementation for File related DAO operations.		
 * 
 */

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.positioncalculation.dto.DTO;

public abstract class FileOperationDAO implements DAO{
	
	private Logger logger = LoggerFactory.getLogger(FileOperationDAO.class);
	protected BufferedWriter writer = null;
	
	@Override
	public void init(Object obj) throws IOException {
		writer = new BufferedWriter(new FileWriter((File)obj));
	}
	
	@Override
	public void end() throws IOException {
		writer.flush();
		writer.close();
	}
	
	protected abstract void addHeader() throws IOException;
	
	public abstract boolean writeFile(List<DTO> dto, String outputFileName) throws IOException;
	
	protected abstract void writeAndAppend(DTO dto) throws IOException;
	
	public abstract Stream<String> readFile(String filePath) throws IOException;
	
	public abstract Stream<String> readJsonFile(String filePath) throws IOException;
}
