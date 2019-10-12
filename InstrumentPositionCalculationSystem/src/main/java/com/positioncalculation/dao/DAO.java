package com.positioncalculation.dao;

/*
 * @Interface
 * DAO.java
 * 		This Interface takes care of DAO operations on data maintained by DTO  		
 */

import java.io.IOException;
import java.util.List;
import java.util.stream.Stream;

import com.positioncalculation.dto.DTO;

public interface DAO {
	
	public void init(Object obj) throws IOException;
	
	public void end() throws IOException;
	
	public boolean writeFile(List<DTO> dto, String outputFileName) throws IOException;
	 
	public Stream<String> readFile(String filePath) throws IOException;

}
