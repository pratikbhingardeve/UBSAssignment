package com.positioncalculation.util;

public class Utility {
	
	public Integer calculateRelativePosition(String transactionType, String accountType, Integer transactionQty) {
		
		if("B".equalsIgnoreCase(transactionType))
		{
			if("E".equalsIgnoreCase(accountType)){
				return transactionQty;
			}
			else if("I".equalsIgnoreCase(accountType)){
				return (-1)*transactionQty;
			}
		}
		else if("S".equalsIgnoreCase(transactionType))
		{
			if("E".equalsIgnoreCase(accountType)){
				return (-1)*transactionQty;
			}
			else if("I".equalsIgnoreCase(accountType)){
				return transactionQty;
			}
		}
		
		return 0;
	}
}
