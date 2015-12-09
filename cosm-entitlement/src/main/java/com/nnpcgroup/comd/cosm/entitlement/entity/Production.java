/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.comd.cosm.entitlement.entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 *
 * @author 18359
 */
@Entity
@Table(name = "PRODUCTION")
public abstract class Production implements Serializable {
    private static final long serialVersionUID = 1L;
    
    
    private Long id;
    private int periodYear;
    private int periodMonth;
    private FiscalPartnership partnership;
    private Double openingStock;    
    private Double closingStock;
    private Double productionVolume;    
    private Double ownShareEntitlement;
    private Double partnerShareEntitlement;
     

    public Production() {
    }
            
    public Production(int periodYear, int periodMonth, FiscalPartnership partnership, Double openingStock, Double productionVolume) {
        this.periodYear = periodYear;
        this.periodMonth = periodMonth;
        this.partnership = partnership;
        this.openingStock = openingStock;
        this.productionVolume = productionVolume;
    }

            
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    
    public void setId(Long id) {
        this.id = id;
    }

    @NotNull
    public int getPeriodYear() {
        return periodYear;
    }

    public void setPeriodYear(int periodYear) {
        this.periodYear = periodYear;
    }

    @NotNull
    public int getPeriodMonth() {
        return periodMonth;
    }

    public void setPeriodMonth(int periodMonth) {
        this.periodMonth = periodMonth;
    }

    @ManyToOne
    @NotNull
    public FiscalPartnership getPartnership() {
        return partnership;
    }

    public void setPartnership(FiscalPartnership partnership) {
        this.partnership = partnership;
    }
         
    @NotNull
    public Double getOpeningStock() {
        return openingStock;
    }

    public void setOpeningStock(Double openingStock) {
        this.openingStock = openingStock;
    }

    public Double getClosingStock() {
        return closingStock;
    }

    public void setClosingStock(Double closingStock) {
        this.closingStock = closingStock;
    }
        
    public Double getProductionVolume() {
        return productionVolume;
    }

    public void setProductionVolume(Double productionVolume) {
        this.productionVolume = productionVolume;
    }

    public Double getOwnShareEntitlement() {
        return ownShareEntitlement;
    }

    public void setOwnShareEntitlement(Double ownShareEntitlement) {
        this.ownShareEntitlement = ownShareEntitlement;
    }

    public Double getPartnerShareEntitlement() {
        return partnerShareEntitlement;
    }

    public void setPartnerShareEntitlement(Double partnerShareEntitlement) {
        this.partnerShareEntitlement = partnerShareEntitlement;
    }

       
}
