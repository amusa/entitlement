package com.cosm.psc.entitlement.taxoil.domain.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

@Embeddable
public class Allocation {
	private double taxOilBroughtForward;
	private double taxOilReceived;
	private double taxOilCarriedForward;
	
	
	
	public Allocation(double taxOilBroughtForward, double taxOilReceived, double taxOilCarriedForward) {
		super();
		setTaxOilBroughtForward(taxOilBroughtForward);
		setTaxOilReceived(taxOilReceived);
		setTaxOilCarriedForward (taxOilCarriedForward);
	}

	
	
	@NotNull   
    @Column(name = "TAX_OIL_BFW")
	public double getTaxOilBroughtForward() {
		return taxOilBroughtForward;
	}



	@NotNull   
    @Column(name = "TAX_OIL_RECEIVED")
	public double getTaxOilReceived() {
		return taxOilReceived;
	}



	@NotNull   
    @Column(name = "TAX_OIL_CFW")
	public double getTaxOilCarriedForward() {
		return taxOilCarriedForward;
	}




	private void setTaxOilBroughtForward(double taxOilBroughtForward) {
		this.taxOilBroughtForward = taxOilBroughtForward;
	}




	private void setTaxOilReceived(double taxOilReceived) {
		this.taxOilReceived = taxOilReceived;
	}




	private void setTaxOilCarriedForward(double taxOilCarriedForward) {
		this.taxOilCarriedForward = taxOilCarriedForward;
	}



}
