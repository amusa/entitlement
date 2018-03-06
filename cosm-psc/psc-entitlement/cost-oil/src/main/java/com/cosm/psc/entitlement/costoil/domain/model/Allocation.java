package com.cosm.psc.entitlement.costoil.domain.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

@Embeddable
public class Allocation {
	private double costOilBroughtForward;
	private double costOilReceived;
	private double costOilCarriedForward;
	
	
	
	public Allocation(double costOilBroughtForward, double costOilReceived, double costOilCarriedForward) {
		super();
		setCostOilBroughtForward(costOilBroughtForward);
		setCostOilReceived(costOilReceived);
		setCostOilCarriedForward (costOilCarriedForward);
	}

	
	

	@NotNull   
    @Column(name = "COST_OIL_BFW")
	public double getCostOilBroughtForward() {
		return costOilBroughtForward;
	}


	@NotNull   
    @Column(name = "COST_OIL_RECEIVED")
	public double getCostOilReceived() {
		return costOilReceived;
	}


	@NotNull   
    @Column(name = "COST_OIL_CFW")
	public double getCostOilCarriedForward() {
		return costOilCarriedForward;
	}




	private void setCostOilBroughtForward(double costOilBroughtForward) {
		this.costOilBroughtForward = costOilBroughtForward;
	}




	private void setCostOilReceived(double costOilReceived) {
		this.costOilReceived = costOilReceived;
	}




	private void setCostOilCarriedForward(double costOilCarriedForward) {
		this.costOilCarriedForward = costOilCarriedForward;
	}






}
