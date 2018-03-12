package com.cosm.psc.entitlement.profitoil.domain.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

@Entity(name="PROFIT_OIL_ALLOCATION")
public abstract class Allocation {
	private AllocationId allocationId;
	
	private double monthlyCharge;
	private double monthlyChargeToDate;
	private double profitOilBroughtForward;
	private double profitOilReceived;
	private double profitOilCarriedForward;
	
	private ProfitOilProjection profitOilProjection;
	
	
	public Allocation(AllocationId allocationId, double monthlyCharge, double monthlyChargeToDate,
			double profitOilBroughtForward, double profitOilReceived, double profitOilCarriedForward,
			ProfitOilProjection profitOilProjection) {
		super();
		this.allocationId = allocationId;
		this.monthlyCharge = monthlyCharge;
		this.monthlyChargeToDate = monthlyChargeToDate;
		this.profitOilBroughtForward = profitOilBroughtForward;
		this.profitOilReceived = profitOilReceived;
		this.profitOilCarriedForward = profitOilCarriedForward;
		this.profitOilProjection = profitOilProjection;
	}


	public AllocationId getAllocationId() {
		return allocationId;
	}


	public double getMonthlyCharge() {
		return monthlyCharge;
	}


	public double getMonthlyChargeToDate() {
		return monthlyChargeToDate;
	}


	public double getProfitOilBroughtForward() {
		return profitOilBroughtForward;
	}


	public double getProfitOilReceived() {
		return profitOilReceived;
	}


	public double getProfitOilCarriedForward() {
		return profitOilCarriedForward;
	}


	public ProfitOilProjection getProfitOilProjection() {
		return profitOilProjection;
	}
	
	
	
	
	



}
