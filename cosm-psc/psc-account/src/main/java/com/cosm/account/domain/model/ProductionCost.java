/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosm.account.domain.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import com.cosm.common.domain.model.FiscalPeriod;
import com.cosm.common.domain.model.OilFieldId;
import com.cosm.common.domain.model.ProductionSharingContractId;

import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author Ayemi
 */
@Entity
@Table(name = "PRODUCTION_COST")
public class ProductionCost implements Serializable {

    private ProductionCostId productionCostId;
    private FiscalPeriod fiscalPeriod;
    private ProductionSharingContractId pscId;
    private OilFieldId oilFieldId;
    private CostItem costItem;
    private Double amount;
    private Double amountCum;

    public ProductionCost() {
    }

    public ProductionCost(ProductionCostId costId) {
        this.productionCostId = costId;
    }

    @EmbeddedId
    public ProductionCostId getProductionCostId() {
        return productionCostId;
    }

    public void setProductionCostId(ProductionCostId productionCostId) {
        this.productionCostId = productionCostId;
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

    @Embedded
    @AttributeOverride(name = "id", column = @Column(name = "OIL_FIELD_ID"))
    public OilFieldId getOilFieldId() {
        return oilFieldId;
    }

    public void setOilFieldId(OilFieldId oilFieldId) {
        this.oilFieldId = oilFieldId;
    }

    @ManyToOne
    //@MapsId("costCode")
    @JoinColumn(name = "COST_CODE", referencedColumnName = "CODE")
    public CostItem getCostItem() {
        return costItem;
    }

    public void setCostItem(CostItem costItem) {
        this.costItem = costItem;
    }

    @NotNull
    @Column(name = "AMOUNT")
    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    @NotNull
    @Column(name = "AMOUNT_CUM")
    public Double getAmountCum() {
        return amountCum;
    }

    public void setAmountCum(Double amountCum) {
        this.amountCum = amountCum;
    }

    public void totalCummulativeAmount(Double prevAmountCum) {
        if (prevAmountCum != null) {
            amountCum = prevAmountCum + (amount != null ? amount : 0);
        }
    }

   
}
