package com.cosm.common.event;

public class CrudeProduced extends CosmEvent {
	
	private double grossProduction;

	public CrudeProduced(EventPeriod period, String pscId, double grossProduction) {
		super(period, pscId);
		this.grossProduction = grossProduction;
	}
	
	public double getGrossProduction() {
		return grossProduction;
	}
	

}
