/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosm.psc.cost.domain.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author Ayemi
 */
@Entity
@Table(name = "PRODUCTION_COST")
public class ProductionCost implements Serializable {

    private ProductionCostPK productionCostPK;
    private Integer periodYear;
    private Integer periodMonth;
    private Long pscId;
    private Long oilFieldId;
    private CostItem costItem;
    private Double amount;
    private Double amountCum;

    public ProductionCost() {
    }

    @EmbeddedId
    public ProductionCostPK getProductionCostPK() {
        return productionCostPK;
    }

    public void setProductionCostPK(ProductionCostPK productionCostPK) {
        this.productionCostPK = productionCostPK;
    }

    @Column(name = "PERIOD_YEAR", updatable = false, insertable = false)
    public Integer getPeriodYear() {
        return periodYear;
    }

    public void setPeriodYear(Integer periodYear) {
        this.periodYear = periodYear;
    }

    @Column(name = "PERIOD_MONTH", updatable = false, insertable = false)
    public Integer getPeriodMonth() {
        return periodMonth;
    }

    public void setPeriodMonth(Integer periodMonth) {
        this.periodMonth = periodMonth;
    }

    @NotNull
    @Column(name = "PSC_ID", updatable = false, insertable = false)
    public Long getPscId() {
        return pscId;
    }

    public void setPscId(Long pscId) {
        this.pscId = pscId;
    }

    @NotNull
    @Column(name = "OIL_FIELD_ID", updatable = false, insertable = false)
    public Long getOilFieldId() {
        return oilFieldId;
    }

    public void setOilFieldId(Long oilFieldId) {
        this.oilFieldId = oilFieldId;
    }

    @ManyToOne
    @MapsId("costCode")
    @JoinColumn(name = "COST_CODE", referencedColumnName = "CODE", updatable = false, insertable = false)
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

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 11 * hash + Objects.hashCode(this.productionCostPK);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ProductionCost other = (ProductionCost) obj;
        if (!Objects.equals(this.productionCostPK, other.productionCostPK)) {
            return false;
        }
        return true;
    }

}
