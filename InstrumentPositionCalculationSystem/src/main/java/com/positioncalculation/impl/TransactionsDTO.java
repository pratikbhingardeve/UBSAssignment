package com.positioncalculation.impl;

/*
 * @Dto
 * TransactionsDTO.java
 * 		This is the DTO implementation.
 * 		This DTO hold the state of transaction.
 * 
 */

import com.positioncalculation.dto.DTO;

public class TransactionsDTO implements DTO {

	private Integer transactionId;
	private String instrument;
	private String transactionType;
	private Integer transactionQty;
	
	public Integer getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(Integer transactionId) {
		this.transactionId = transactionId;
	}
	public String getInstrument() {
		return instrument;
	}
	public void setInstrument(String instrument) {
		this.instrument = instrument;
	}
	public String getTransactionType() {
		return transactionType;
	}
	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}
	public Integer getTransactionQty() {
		return transactionQty;
	}
	public void setTransactionQty(Integer transactionQty) {
		this.transactionQty = transactionQty;
	}
}
