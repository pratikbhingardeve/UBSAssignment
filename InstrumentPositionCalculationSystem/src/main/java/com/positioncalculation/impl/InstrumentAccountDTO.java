package com.positioncalculation.impl;

/*
 * @Dto
 * InstrumentAccountDTO.java
 * 		This is the DTO implementation of Key for each Instrument Account to identify it uniquely.
 * 
 */

import com.positioncalculation.dto.DTO;

public final class InstrumentAccountDTO implements DTO {
	
	private final String instrument;
	private final Integer account;
	
	public String getInstrument() {
		return instrument;
	}

	public Integer getAccount() {
		return account;
	}

	public InstrumentAccountDTO(String instrument, Integer account){
		this.instrument = instrument;
		this.account = account;
	}

}
