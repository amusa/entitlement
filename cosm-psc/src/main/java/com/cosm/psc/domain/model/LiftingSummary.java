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
public class LiftingSummary {

    private String liftingDate;
    private Double liftingVolume;
    private Double osPrice;
    private Double proceed;
    private Double corporationProceed;
    private Double contractorProceed;

    public String getLiftingDate() {
        return liftingDate;
    }

    public void setLiftingDate(String liftingDate) {
        this.liftingDate = liftingDate;
    }

    public Double getLiftingVolume() {
        return liftingVolume;
    }

    public void setLiftingVolume(Double liftingVolume) {
        this.liftingVolume = liftingVolume;
    }

    public Double getOsPrice() {
        return osPrice;
    }

    public void setOsPrice(Double osPrice) {
        this.osPrice = osPrice;
    }

    public Double getProceed() {
        return proceed;
    }

    public void setProceed(Double proceed) {
        this.proceed = proceed;
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

}
