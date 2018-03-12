/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosm.psc.entitlement.royalty.domain.model;

import com.cosm.common.domain.model.Allocation;
import com.cosm.common.domain.model.FiscalPeriod;
import com.cosm.common.domain.model.ProductionSharingContractId;

/**
 * @author Ayemi
 */
public class RoyaltyAllocation extends Allocation {
    
	public RoyaltyAllocation(FiscalPeriod fiscalPeriod, ProductionSharingContractId pscId, double chargeBfw,
			double monthlyCharge, double liftingProceed, double prevCumMonthlyCharge, double cashPayment) {

		super(fiscalPeriod, pscId, chargeBfw, monthlyCharge, liftingProceed, prevCumMonthlyCharge);

		setCashPayment(cashPayment);

	}

	private Double cashPayment;

    public Double getCashPayment() {
        return cashPayment != null ? cashPayment : 0;
    }

    public void setCashPayment(Double cashPayment) {
        this.cashPayment = cashPayment;
    }

    @Override
    public double getReceived() {
        if (getCashPayment() > 0) {
            return getCashPayment();
        }

        return super.getReceived();
    }

    public double getRoyaltyReceived() {
        return super.getReceived();
    }
}
