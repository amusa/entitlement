/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosm.psc.domain.model;

import java.io.Serializable;

/**
 * @author Ayemi
 * @date 04.12.2016
 */
public abstract class Allocation implements Serializable {

    private Double chargeBfw;
    private Double monthlyCharge;
    private Double liftingProceed;
    private double prevCumMonthlyCharge;

    public Allocation() {
        this.chargeBfw = 0.0;
        this.monthlyCharge = 0.0;
        this.liftingProceed = 0.0;
        this.prevCumMonthlyCharge = 0.0;
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

    public double getPrevCumMonthlyCharge() {
        return prevCumMonthlyCharge;
    }

    public void setPrevCumMonthlyCharge(double prevCumMonthlyCharge) {
        this.prevCumMonthlyCharge = prevCumMonthlyCharge;
    }

    public double getCumMonthlyCharge() {
        return getPrevCumMonthlyCharge() + getMonthlyCharge();
    }

    public Double getRecoverable() {
        return chargeBfw + monthlyCharge;
    }

    public Double getReceived() {
        if (getRecoverable() <= 0) {
            return 0.0;
        } else if (getRecoverable() <= getLiftingProceed()) {
            return getRecoverable();
        } else {
            return getLiftingProceed();
        }
    }

    public Double getChargeCfw() {
        return getRecoverable() - getReceived();
    }

}
