/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosm.psc.entitlement.costoil.domain.model;

import java.io.Serializable;
import java.util.Optional;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

import com.cosm.common.domain.model.FiscalPeriod;
import com.cosm.common.domain.model.ProductionSharingContractId;

/**
 * @author Ayemi
 */
@Entity(name = "COST_OIL_PROJECTION")
public class CostOilProjection implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private CostOilProjectionId costOilProjectionId;
	private FiscalPeriod fiscalPeriod;
	private ProductionSharingContractId pscId;
	private double amortizedCapex;
	private double opex;
	private double educationTax;
	private double monthlyIncome;
	private double contractorProceed;
	private double costRecoveryLimitRate;
	private Allocation allocation;
	private Optional<CostOilProjection> priorCostOilProjection;

	private CostOilProjection(CostOilProjectionId aProjectionId, FiscalPeriod aFiscalPeriod,
			ProductionSharingContractId aPscId, double amortizedCapex, double opex, double eduTax, double monthlyIncome,
			double contProceed, double costRecovLimitRate, Optional<CostOilProjection> priorCostOilProj) {

		this.costOilProjectionId = aProjectionId;
		this.fiscalPeriod = aFiscalPeriod;
		this.pscId = aPscId;
		this.amortizedCapex = amortizedCapex;
		this.opex = opex;
		this.educationTax = eduTax;
		this.monthlyIncome = monthlyIncome;
		this.contractorProceed = contProceed;
		this.costRecoveryLimitRate = costRecovLimitRate;
		this.priorCostOilProjection = priorCostOilProj;

		populateAllocation();
	}

	@EmbeddedId
	public CostOilProjectionId getCostOilProjectionId() {
		return costOilProjectionId;
	}

	public FiscalPeriod getFiscalPeriod() {
		return fiscalPeriod;
	}

	public ProductionSharingContractId getPscId() {
		return pscId;
	}

	public double getArmotizedCapex() {
		return amortizedCapex;
	}

	public void setArmotizedCapex(double armotizedCapex) {
		this.amortizedCapex = armotizedCapex;
	}

	public double getOpex() {
		return opex;
	}

	public void setOpex(Double opex) {
		this.opex = opex;
	}

	public double getEducationTax() {
		return educationTax;
	}

	public void setEducationTax(double educationTax) {
		this.educationTax = educationTax;
	}

	public double getCostOilBfw() {
		if (priorCostOilProjection.isPresent()) {
			return Math.max(0, getPriorCostOilProjection().get().getCostOilToDate()
					- getPriorCostOilProjection().get().getAllocation().getCostOilReceived());
		}

		return 0;
	}

	public double getCostOil() {
		return amortizedCapex + opex + Math.max(0, educationTax);
	}

	public double getCostOilToDate() {
		return getCostOil() + getCostOilBfw();
	}

	public double getEducationTaxVariance() {

		if (fiscalPeriod.getMonth() == 1) {
			return getEducationTax();
		}

		return getEducationTax() - getPriorEducationTaxVariance();
	}

	public double getPriorEducationTaxVariance() {
		if (priorCostOilProjection.isPresent()) {
			return getPriorCostOilProjection().get().getEducationTaxVariance();
		}
		return 0;
	}

	public double getMonthlyCurrentCharge() {
		double costOilCharge = Math.max(0,
				Math.min(getMonthlyIncome() * getCostRecoveryLimitRate(), getCostOilToDate()));

		return costOilCharge;
	}

	public double getMonthlyChargeToDate() {
		return getMonthlyCurrentCharge() + getPriorMonthlyChargeToDate();
	}

	public double getPriorMonthlyChargeToDate() {
		if (priorCostOilProjection.isPresent()) {
			return getPriorCostOilProjection().get().getMonthlyChargeToDate();
		}
		return 0;
	}

	public double getMonthlyIncome() {
		return monthlyIncome;
	}

	public double getContractorProceed() {
		return contractorProceed;
	}

	public double getCostRecoveryLimitRate() {
		return costRecoveryLimitRate;
	}

	public Optional<CostOilProjection> getPriorCostOilProjection() {
		return priorCostOilProjection;
	}

	public Allocation getAllocation() {
		return allocation;
	}

	private void populateAllocation() {
		double costOilCfw = priorCostOilProjection.isPresent()
				? getPriorCostOilProjection().get().getAllocation().getCostOilCarriedForward()
				: 0;

		CostOilAllocation coAlloc = new CostOilAllocation(getFiscalPeriod(), getPscId(), costOilCfw,
				getMonthlyCurrentCharge(), getContractorProceed(), getPriorMonthlyChargeToDate());

		this.allocation = new Allocation(coAlloc.getChargeBfw(), coAlloc.getReceived(), coAlloc.getChargeCfw());
	}
	
	public static class Builder {
		
		private CostOilProjectionId newProjectionId;
		private FiscalPeriod newFiscalPeriod;
		private ProductionSharingContractId newPscId;
		private double newAmortizedCapex;
		private double newOpex;
		private double newEducationTax;
		private double newMonthlyIncome;
		private double newContractorProceed;
		private double newCostRecoveryLimitRate;
		private Optional<CostOilProjection> newPriorCostOilProjection;
		
		
		public Builder withId(CostOilProjectionId aId) {
			this.newProjectionId = aId;
			return this;
		}
		
		public Builder withFiscalPeriod(FiscalPeriod aFiscalPeriod) {
			this.newFiscalPeriod = aFiscalPeriod;
			return this;
		}

		public Builder withContractId(ProductionSharingContractId aPscId) {
			this.newPscId = aPscId;
			return this;
		}
		
		public Builder withAmortizedCapex(double amortizedCapex) {
			this.newAmortizedCapex = amortizedCapex;
			return this;
		}
		
		public Builder withOpex(double opex) {
			this.newOpex = opex;
			return this;
		}
		
		public Builder withEducationTax(double eduTax) {
			this.newEducationTax = eduTax;
			return this;
		}
		
		public Builder withMonthlyIncome(double monthlyIncome) {
			this.newMonthlyIncome = monthlyIncome;
			return this;
		}
		
		public Builder withContractorProceed(double contProceed) {
			this.newContractorProceed = contProceed;
			return this;
		}
		
		public Builder withCostRecoveryLimit(double costRecoveryLimit) {
			this.newCostRecoveryLimitRate = costRecoveryLimit;
			return this;
		}
		
		
		public Builder withPriorCostOilProjection(Optional<CostOilProjection>  coProjection) {
			this.newPriorCostOilProjection = coProjection;
			return this;
		}
		
		
		public CostOilProjection build() {
			return new CostOilProjection(newProjectionId, newFiscalPeriod,
					newPscId, newAmortizedCapex, newOpex, newEducationTax, newMonthlyIncome,
					 newContractorProceed,  newCostRecoveryLimitRate,  newPriorCostOilProjection);
		}
	}


}
