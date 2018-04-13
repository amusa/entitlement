/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosm.psc.entitlement.royalty.domain.model;

import com.cosm.common.domain.model.FiscalPeriod;
import com.cosm.common.domain.model.ProductionSharingContractId;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author 18359
 * @since 23/02/2018
 */
@Entity(name = "ROYALTY_PROJECTION")
public class RoyaltyProjection implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private RoyaltyProjectionId royaltyProjectionId;
    private FiscalPeriod fiscalPeriod;
    private ProductionSharingContractId pscId;
    private double royalty;
    private double royaltyToDate;
    private Allocation allocation;
    private double proceed;
    private double cashPayment;
   
  

    public RoyaltyProjection() {

    }

    public RoyaltyProjection(RoyaltyProjectionId royaltyProjectionId, FiscalPeriod fiscalPeriod, ProductionSharingContractId pscId,
            double royalty, double royaltyToDate, Allocation allocation) {
        super();
        this.royaltyProjectionId = royaltyProjectionId;
        this.fiscalPeriod = fiscalPeriod;
        this.pscId = pscId;
        this.royalty = royalty;
        this.royaltyToDate = royaltyToDate;
        this.allocation = allocation;
    }

    public RoyaltyProjection(RoyaltyProjectionId royaltyProjectionId, RoyaltyCalculator royaltyCalculator) {
        this.royaltyProjectionId = royaltyProjectionId;
        this.fiscalPeriod = royaltyCalculator.getFiscalPeriod();
        this.pscId = royaltyCalculator.getPscId();
        this.royalty = royaltyCalculator.royalty();
        this.royaltyToDate = royaltyCalculator.getRoyaltyAllocation().getCumMonthlyCharge();
        this.cashPayment = royaltyCalculator.getCashPayment();
        this.proceed = royaltyCalculator.getProceed();
        this.allocation = new Allocation(
                royaltyCalculator.getRoyaltyAllocation().getChargeBfw(),
                royaltyCalculator.getRoyaltyAllocation().getReceived(),
                royaltyCalculator.getRoyaltyAllocation().getChargeCfw());

    }

    @EmbeddedId
    public RoyaltyProjectionId getRoyaltyProjectionId() {
        return royaltyProjectionId;
    }

    public void setRoyaltyProjectionId(RoyaltyProjectionId royaltyProjectionId) {
        this.royaltyProjectionId = royaltyProjectionId;
    }

    @Embedded
    public FiscalPeriod getFiscalPeriod() {
        return fiscalPeriod;
    }

    public void setFiscalPeriod(FiscalPeriod fiscalPeriod) {
        this.fiscalPeriod = fiscalPeriod;
    }

    @Embedded
    @AttributeOverride(name = "id", column = @Column(name = "PSC_ID"))
    public ProductionSharingContractId getPscId() {
        return pscId;
    }

    public void setPscId(ProductionSharingContractId pscId) {
        this.pscId = pscId;
    }

    @NotNull
    @Column(name = "ROYALTY")
    public double getRoyalty() {
        return royalty;
    }

    public void setRoyalty(double royalty) {
        this.royalty = royalty;
    }

    @NotNull
    @Column(name = "ROYALTY_TO_DATE")
    public double getRoyaltyToDate() {
        return royaltyToDate;
    }

    public void setRoyaltyToDate(double royaltyToDate) {
        this.royaltyToDate = royaltyToDate;
    }

    @Embedded
    public Allocation getAllocation() {
        return allocation;
    }

    public void setAllocation(Allocation allocation) {
        this.allocation = allocation;
    }

    @NotNull
    @Column(name = "PROCEED")
    public double getProceed() {
        return proceed;
    }

    public void setProceed(double proceed) {
        this.proceed = proceed;
    }

    @NotNull
    @Column(name = "CASH_PAYMENT")
    public double getCashPayment() {
        return cashPayment;
    }

    public void setCashPayment(double cashPayment) {
        this.cashPayment = cashPayment;
    }


}
