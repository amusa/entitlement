package com.cosm.common.event;

public class ProductionPosted extends CosmEvent {
	
	private double grossProduction;

	public ProductionPosted(EventPeriod period, String pscId, double grossProduction) {
		super(period, pscId);
		this.grossProduction = grossProduction;
	}
	
	public double getGrossProduction() {
		return grossProduction;
	}
	

}
