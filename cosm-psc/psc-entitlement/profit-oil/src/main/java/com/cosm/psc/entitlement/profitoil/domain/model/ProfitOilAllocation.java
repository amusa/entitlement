package com.cosm.psc.entitlement.profitoil.domain.model;

import com.cosm.common.domain.model.Allocation;

/**
 * Created by Ayemi on 28/04/2017.
 */
public class ProfitOilAllocation extends Allocation {

    private double deductibles;

    public double getDeductibles() {
        return deductibles;
    }

    public void setDeductibles(double deductibles) {
        this.deductibles = deductibles;
    }

    @Override
    public Double getReceived() {
        return getLiftingProceed() - getDeductibles();
    }
}
