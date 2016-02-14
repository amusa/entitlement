/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.comd.cosm.entitlement.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 *
 * @author 18359
 */
@Entity
@DiscriminatorValue("JV_ACTUAL")
public class ActualJvProduction extends JvProduction {

    private static final long serialVersionUID = 2917192116735019964L;

    private Double lineFillContribution;
    private Double deadStockContribution;
    private Double adjustedOwnEntitlement;
    private Double adjustedPartnerEntitlement;

    
    public ActualJvProduction() {
    }
        
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

    public Double getAdjustedOwnEntitlement() {
        return adjustedOwnEntitlement;
    }

    public void setAdjustedOwnEntitlement(Double adjustedOwnEntitlement) {
        this.adjustedOwnEntitlement = adjustedOwnEntitlement;
    }

    public Double getAdjustedPartnerEntitlement() {
        return adjustedPartnerEntitlement;
    }

    public void setAdjustedPartnerEntitlement(Double adjustedPartnerEntitlement) {
        this.adjustedPartnerEntitlement = adjustedPartnerEntitlement;
    }

}
