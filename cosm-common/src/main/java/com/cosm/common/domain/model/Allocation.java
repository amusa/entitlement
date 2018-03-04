/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosm.common.domain.model;

import java.io.Serializable;

/**
 * @author Ayemi
 * @date 04.12.2016
 */
public abstract class Allocation implements Serializable {

	private FiscalPeriod fiscalPeriod;
	private ProductionSharingContractId pscId;
    private Double chargeBfw;
    private Double monthlyCharge;
    private Double liftingProceed;
    private double prevCumMonthlyCharge;
        
    protected Allocation(FiscalPeriod fiscalPeriod, ProductionSharingContractId pscId, double chargeBfw, double monthlyCharge, double liftingProceed, double prevCumMonthlyCharge) {
		this.chargeBfw = chargeBfw;
		this.monthlyCharge = monthlyCharge;
		this.liftingProceed = liftingProceed;
		this.prevCumMonthlyCharge = prevCumMonthlyCharge;
	}
    
    public FiscalPeriod getFiscalPeriod() {
		return fiscalPeriod;
	}

    public ProductionSharingContractId getPscId() {
		return pscId;
	}

    public double getChargeBfw() {
        return chargeBfw;
    }
  	
    public double getMonthlyCharge() {
        return monthlyCharge;
    }
    
    public double getLiftingProceed() {
        return liftingProceed;
    }
    
    public double getPrevCumMonthlyCharge() {
        return prevCumMonthlyCharge;
    }

    
    public double getCumMonthlyCharge() {
        return getPrevCumMonthlyCharge() + getMonthlyCharge();
    }

    public double getRecoverable() {
        return chargeBfw + monthlyCharge;
    }

    public double getReceived() {
        if (getRecoverable() <= 0) {
            return 0.0;
        } else if (getRecoverable() <= getLiftingProceed()) {
            return getRecoverable();
        } else {
            return getLiftingProceed();
        }
    }

    public double getChargeCfw() {
        return getRecoverable() - getReceived();
    }
    
   
}
