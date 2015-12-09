/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.comd.cosm.entitlement.entity;

import javax.persistence.Entity;

/**
 *
 * @author 18359
 */
@Entity
public class ActualProduction extends Production {

    private static final long serialVersionUID = -5206065666784730417L;

    private Double lineFillContribution;
    private Double deadStockContribution;
    private Double ajustedOwnEntitlement;
    private Double adjustedPartnerEntitlement;

    public Double getLineFillContribution() {
        return lineFillContribution;
    }

    public void setLineFillContribution(Double lineFillContribution) {
        this.lineFillContribution = lineFillContribution;
    }

    public Double getDeadStockContribution() {
        return deadStockContribution;
    }

    public void setDeadStockContribution(Double deadStockContribution) {
        this.deadStockContribution = deadStockContribution;
    }

    public Double getAjustedOwnEntitlement() {
        return ajustedOwnEntitlement;
    }

    public void setAjustedOwnEntitlement(Double ajustedOwnEntitlement) {
        this.ajustedOwnEntitlement = ajustedOwnEntitlement;
    }

    public Double getAdjustedPartnerEntitlement() {
        return adjustedPartnerEntitlement;
    }

    public void setAdjustedPartnerEntitlement(Double adjustedPartnerEntitlement) {
        this.adjustedPartnerEntitlement = adjustedPartnerEntitlement;
    }
    
    
    
    
    
}
