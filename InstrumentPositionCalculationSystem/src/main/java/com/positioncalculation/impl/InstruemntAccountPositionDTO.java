package com.positioncalculation.impl;

/*
 * @Dto
 * InstruemntAccountPositionDTO.java
 * 		This is the DTO implementation.
 * 		It holds the current position of Instrument Account.
 * 		It gets initialized using input feed file StartOfDayPositions.txt and then transactions will be applied on this.
 * 		Transaction can change this DTOs state if applicable and then it hold the current position.
 * 		After processing of all transaction this DTO will hold EOD position of Instrument Account.
 */


import com.positioncalculation.dto.DTO;

public class InstruemntAccountPositionDTO implements DTO {
	
	private String instrument;
	private Integer account;
	private String accountType;
	private Integer qty;
	private Integer delta;
	
	public String getInstrument() {
		return instrument;
	}
	public void setInstrument(String instrument) {
		this.instrument = instrument;
	}
	public Integer getAccount() {
		return account;
	}
	public void setAccount(Integer account) {
		this.account = account;
	}
	public String getAccountType() {
		return accountType;
	}
	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}
	public Integer getQty() {
		return qty;
	}
	public void setQty(Integer qty) {
		this.qty = qty;
	}
	public Integer getDelta() {
		return delta;
	}
	public void setDelta(Integer delta) {
		this.delta = delta;
	}
	
	public String toString() {
		return  instrument+","+account+","+accountType+","+qty+","+delta;
	}
	
}
