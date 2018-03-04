package com.cosm.common.event;

public class CrudeLifted extends CosmEvent {
	
	private double corporationProceed;	
	private double cashPayment;
	private double weightedAveragePrice;
	private double contractorProceed;
	private double grossIncome;
	private double monthlyIncome;


	public CrudeLifted(EventPeriod period, String pscId, double grossIncome, double monthlyIncome, double corpProceed, double contProceed, double weightedAvePrice, double cashPayment) {
		super(period, pscId);
		this.grossIncome = grossIncome;
		this.monthlyIncome = monthlyIncome;
		this.corporationProceed = corpProceed;
		this.contractorProceed = contProceed;
		this.weightedAveragePrice = weightedAvePrice;
		this.cashPayment = cashPayment;
	}


	public double getCorporationProceed() {
		return corporationProceed;
	}


	public double getCashPayment() {
		return cashPayment;
	}


	public double getWeightedAveragePrice() {
		return weightedAveragePrice;
	}


	public double getContractorProceed() {
		return contractorProceed;
	}


	public double getGrossIncome() {
		return grossIncome;
	}


	public double getMonthlyIncome() {
		return monthlyIncome;
	}
	
	
	
	

}
