/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosm.psc.entitlement.taxoil.domain.model;

import com.cosm.common.domain.model.Allocation;
import com.cosm.common.domain.model.FiscalPeriod;
import com.cosm.common.domain.model.ProductionSharingContractId;

/**
 *
 * @author Ayemi
 */
public class TaxOilAllocation extends Allocation {

    private Double royalty;
    
    protected TaxOilAllocation(FiscalPeriod fiscalPeriod, ProductionSharingContractId pscId, double chargeBfw, double monthlyCharge, double liftingProceed, double prevCumMonthlyCharge) {
		super(fiscalPeriod, pscId, chargeBfw, monthlyCharge, liftingProceed, prevCumMonthlyCharge);
	}

    @Override
    public double getReceived() {
        if (getRecoverable() <= 0) {
            return 0.0;
        } else if (getRecoverable() <= (getLiftingProceed() - getRoyalty())) {
            return getRecoverable();
        } else {
            return getLiftingProceed() - getRoyalty();
        }
    }

    public double getRoyalty() {
        return royalty;
    }

    public void setRoyalty(double royalty) {
        this.royalty = royalty;
    }

}
