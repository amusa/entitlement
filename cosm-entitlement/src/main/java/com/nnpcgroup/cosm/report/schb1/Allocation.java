/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.report.schb1;

/**
 *
 * @author Ayemi
 * @date 04.12.2016
 */
public abstract class Allocation {

    private Double chargeBfw;
    private Double monthlyCharge;
    private Double liftingProceed;

    public Allocation() {
        this.chargeBfw = 0.0;
        this.monthlyCharge = 0.0;
        this.liftingProceed = 0.0;
    }

    public Double getChargeBfw() {
        return chargeBfw;
    }

    public void setChargeBfw(Double chargeBfw) {
        this.chargeBfw = chargeBfw;
    }

    public Double getMonthlyCharge() {
        return monthlyCharge;
    }

    public void setMonthlyCharge(Double monthlyCharge) {
        this.monthlyCharge = monthlyCharge;
    }

    public Double getLiftingProceed() {
        return liftingProceed;
    }

    public void setLiftingProceed(Double liftingProceed) {
        this.liftingProceed = liftingProceed;
    }

    public Double getRecoverable() {
        return chargeBfw + monthlyCharge;
    }

    public abstract Double getReceived();

    public Double getChargeCfw() {
        return getRecoverable() - getReceived();
    }

}
