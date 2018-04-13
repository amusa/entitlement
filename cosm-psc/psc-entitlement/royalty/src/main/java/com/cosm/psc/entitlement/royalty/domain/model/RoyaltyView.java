/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosm.psc.entitlement.royalty.domain.model;

import com.cosm.common.domain.model.FiscalPeriod;
import com.cosm.common.domain.model.ProductionSharingContractId;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;


/**
 * @author 18359
 * @since 23/02/2018
 */
@Entity(name="ROYALTY_VIEW")
public class RoyaltyView implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private RoyaltyViewId royaltyViewId;
	private FiscalPeriod fiscalPeriod;
	private ProductionSharingContractId pscId;
	private double royalty;
	private double royaltyToDate;
	private Allocation allocation;
	private double proceed;
	private double cashPayment;

        public RoyaltyView(){
            
        }

	private RoyaltyView(RoyaltyViewId royaltyViewId, FiscalPeriod fiscalPeriod, ProductionSharingContractId pscId,
			double royalty, double royaltyToDate, Allocation allocation) {
		super();
		this.royaltyViewId = royaltyViewId;
		this.fiscalPeriod = fiscalPeriod;
		this.pscId = pscId;
		this.royalty = royalty;
		this.royaltyToDate = royaltyToDate;
		this.allocation = allocation;
	}

	public RoyaltyView(RoyaltyViewId royaltyViewId, RoyaltyAllocation royAlloc) {
		this.royaltyViewId = royaltyViewId;
		this.fiscalPeriod = royAlloc.getFiscalPeriod();
		this.pscId = royAlloc.getPscId();
		this.royalty = royAlloc.getMonthlyCharge();
		this.royaltyToDate = royAlloc.getCumMonthlyCharge();
		this.cashPayment = royAlloc.getCashPayment();
		this.proceed = royAlloc.getLiftingProceed();
		this.allocation = new Allocation( 
				royAlloc.getChargeBfw(),  
				royAlloc.getReceived(),  
				royAlloc.getChargeCfw());
		
	}

	@EmbeddedId
	public RoyaltyViewId getRoyaltyViewId() {
		return royaltyViewId;
	}

	public void setRoyaltyViewId(RoyaltyViewId royaltyViewId) {
		this.royaltyViewId = royaltyViewId;
	}

	@Embedded
	public FiscalPeriod getFiscalPeriod() {
		return fiscalPeriod;
	}

	public void setFiscalPeriod(FiscalPeriod fiscalPeriod) {
		this.fiscalPeriod = fiscalPeriod;
	}

	@Embedded
        @AttributeOverride(name="id", column=@Column(name="PSC_ID"))
	public ProductionSharingContractId getPscId() {
		return pscId;
	}

	public void setPscId(ProductionSharingContractId pscId) {
		this.pscId = pscId;
	}

	@NotNull
	@Column(name = "ROYALTY")
	public double getRoyalty() {
		return royalty;
	}

	public void setRoyalty(double royalty) {
		this.royalty = royalty;
	}

	@NotNull
	@Column(name = "ROYALTY_TO_DATE")
	public double getRoyaltyToDate() {
		return royaltyToDate;
	}

	public void setRoyaltyToDate(double royaltyToDate) {
		this.royaltyToDate = royaltyToDate;
	}

	@Embedded
	public Allocation getAllocation() {
		return allocation;
	}

	public void setAllocation(Allocation allocation) {
		this.allocation = allocation;
	}
	
	
	@NotNull
	@Column(name = "PROCEED")
	public double getProceed() {
		return proceed;
	}

	public void setProceed(double proceed) {
		this.proceed = proceed;
	}

	@NotNull
	@Column(name = "CASH_PAYMENT")
	public double getCashPayment() {
		return cashPayment;
	}

	public void setCashPayment(double cashPayment) {
		this.cashPayment = cashPayment;
	}



	public static class RoyaltyBuilder {
		private RoyaltyViewId royaltyViewId;
		private FiscalPeriod fiscalPeriod;
		private ProductionSharingContractId pscId;
		private double royalty;
		private double royaltyToDate;
		private double royaltyBroughtForward;
		private double royaltyReceived;
		private double royaltyCarriedForward;

		RoyaltyBuilder withId(RoyaltyViewId id) {
			this.royaltyViewId = id;
			return this;
		}

		RoyaltyBuilder withFiscalPeriod(FiscalPeriod fp) {
			this.fiscalPeriod = fp;
			return this;
		}

		RoyaltyBuilder withProductionSharingContractId(ProductionSharingContractId pscId) {
			this.pscId = pscId;
			return this;
		}

		RoyaltyBuilder withRoyalty(double royalty) {
			this.royalty = royalty;
			return this;
		}

		RoyaltyBuilder withRoyaltyToDate(double royaltyToDate) {
			this.royaltyToDate = royaltyToDate;
			return this;
		}

		RoyaltyBuilder withRoyaltyBfw(double royaltyBroughtForward) {
			this.royaltyBroughtForward = royaltyBroughtForward;
			return this;
		}

		RoyaltyBuilder withRoyaltyReceived(double royReceived) {
			this.royaltyReceived = royReceived;
			return this;
		}

		RoyaltyView build() {
			return new RoyaltyView(
					royaltyViewId, fiscalPeriod, pscId,
					royalty, royaltyToDate, 
					new Allocation(royaltyBroughtForward, royaltyReceived, royaltyCarriedForward)
					);

		}
	}

}
