/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.report.schb1;

/**
 *
 * @author Ayemi
 */
public class TaxOilAllocation extends Allocation {

    private Double royalty;

    @Override
    public Double getReceived() {
        if (getRecoverable() <= 0) {
            return 0.0;
        } else if (getRecoverable() <= (getLiftingProceed() - getRoyalty())) {
            return getRecoverable();
        } else {
            return getLiftingProceed() - getRoyalty();
        }
    }

    public Double getRoyalty() {
        return royalty;
    }

    public void setRoyalty(Double royalty) {
        this.royalty = royalty;
    }

}
