package com.cosm.common.event;

public class CashCallPlaced extends CosmEvent {
	
	private double currentYearCapex;	
	private double armotizedCapex;
	private double currentYearOpex;
	private double currentMonthOpex;
	private double costToDate;
	private double educationTax;


	public CashCallPlaced(EventPeriod period, String pscId, double currentYearCapex, 
			double armotizedCapex, double currentYearOpex, double currentMonthOpex, double costToDate, double educationTax) {
		super(period, pscId);
		this.currentYearCapex = currentYearCapex; 
		this.armotizedCapex = armotizedCapex;
		this.currentYearOpex = currentYearOpex;
		this.currentMonthOpex = currentMonthOpex;
		this.costToDate = costToDate;
		this.educationTax = educationTax;
	}


	public double getCurrentYearCapex() {
		return currentYearCapex;
	}


	public double getArmotizedCapex() {
		return armotizedCapex;
	}


	public double getCurrentYearOpex() {
		return currentYearOpex;
	}


	public double getCurrentMonthOpex() {
		return currentMonthOpex;
	}


	public double getCostToDate() {
		return costToDate;
	}


	public double getEducationTax() {
		return educationTax;
	}
	
	

}
