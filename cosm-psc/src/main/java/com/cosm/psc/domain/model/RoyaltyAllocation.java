/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosm.psc.domain.model;

/**
 * @author Ayemi
 */
public class RoyaltyAllocation extends Allocation {
    private Double cashPayment;

    public Double getCashPayment() {
        return cashPayment != null ? cashPayment : 0;
    }

    public void setCashPayment(Double cashPayment) {
        this.cashPayment = cashPayment;
    }

    @Override
    public Double getReceived() {
        if (getCashPayment() > 0) {
            return getCashPayment();
        }

        return super.getReceived();
    }

    public Double getRoyaltyReceived() {
        return super.getReceived();
    }
}
