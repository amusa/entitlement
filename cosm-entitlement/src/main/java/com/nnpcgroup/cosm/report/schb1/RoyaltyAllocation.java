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
public class RoyaltyAllocation extends Allocation {

    @Override
    public Double getReceived() {
        if (getRecoverable() <= 0) {
            return 0.0;
        } else if (getRecoverable() <= getLiftingProceed()) {
            return getRecoverable();
        } else {
            return getLiftingProceed();
        }
    }

}