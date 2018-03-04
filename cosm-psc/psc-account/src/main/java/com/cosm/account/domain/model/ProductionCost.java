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

    

    @Embedded
    public ProductionSharingContractId getPscId() {
		return pscId;
	}

	public void setPscId(ProductionSharingContractId pscId) {
		this.pscId = pscId;
	}

	@Embedded
	public OilFieldId getOilFieldId() {
		return oilFieldId;
	}

	public void setOilFieldId(OilFieldId oilFieldId) {
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
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fiscalPeriod == null) ? 0 : fiscalPeriod.hashCode());
		result = prime * result + ((oilFieldId == null) ? 0 : oilFieldId.hashCode());
		result = prime * result + ((pscId == null) ? 0 : pscId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProductionCost other = (ProductionCost) obj;
		if (fiscalPeriod == null) {
			if (other.fiscalPeriod != null)
				return false;
		} else if (!fiscalPeriod.equals(other.fiscalPeriod))
			return false;
		if (oilFieldId == null) {
			if (other.oilFieldId != null)
				return false;
		} else if (!oilFieldId.equals(other.oilFieldId))
			return false;
		if (pscId == null) {
			if (other.pscId != null)
				return false;
		} else if (!pscId.equals(other.pscId))
			return false;
		return true;
	}

    

}
