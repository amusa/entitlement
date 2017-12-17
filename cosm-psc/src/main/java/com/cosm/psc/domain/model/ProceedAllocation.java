/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosm.psc.domain.model;

/**
 *
 * @author Ayemi
 */
public class ProceedAllocation {

    private String categoryTitle;
    private Double monthlyChargeBfw;
    private Double monthlyCharge;
    private Double recoverable;
    private Double corporationProceed;
    private Double contractorProceed;
    private Double monthlyChargeCfw;

    public String getCategoryTitle() {
        return categoryTitle;
    }

    public void setCategoryTitle(String categoryTitle) {
        this.categoryTitle = categoryTitle;
    }

    public Double getMonthlyChargeBfw() {
        return monthlyChargeBfw;
    }

    public void setMonthlyChargeBfw(Double monthlyChargeBfw) {
        this.monthlyChargeBfw = monthlyChargeBfw;
    }

    public Double getMonthlyCharge() {
        return monthlyCharge;
    }

    public void setMonthlyCharge(Double monthlyCharge) {
        this.monthlyCharge = monthlyCharge;
    }

    public Double getRecoverable() {
        return recoverable;
    }

    public void setRecoverable(Double recoverable) {
        this.recoverable = recoverable;
    }

    public Double getCorporationProceed() {
        return corporationProceed;
    }

    public void setCorporationProceed(Double corporationProceed) {
        this.corporationProceed = corporationProceed;
    }

    public Double getContractorProceed() {
        return contractorProceed;
    }

    public void setContractorProceed(Double contractorProceed) {
        this.contractorProceed = contractorProceed;
    }

    public Double getMonthlyChargeCfw() {
        return monthlyChargeCfw;
    }

    public void setMonthlyChargeCfw(Double monthlyChargeCfw) {
        this.monthlyChargeCfw = monthlyChargeCfw;
    }

}
