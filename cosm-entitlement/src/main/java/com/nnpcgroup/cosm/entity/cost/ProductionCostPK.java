/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.entity.cost;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Ayemi
 */
@Embeddable
public class ProductionCostPK implements Serializable {

    private Integer periodYear;
    private Integer periodMonth;
    private Long pscId;
    private String costCode;

    public ProductionCostPK() {
    }

    public ProductionCostPK(Integer periodYear, Integer periodMonth, Long pscId, String costCode) {
        this.periodYear = periodYear;
        this.periodMonth = periodMonth;
        this.pscId = pscId;
        this.costCode = costCode;
    }

    @Column(name = "PERIOD_YEAR")
    public Integer getPeriodYear() {
        return periodYear;
    }

    public void setPeriodYear(Integer periodYear) {
        this.periodYear = periodYear;
    }

    @Column(name = "PERIOD_MONTH")
    public Integer getPeriodMonth() {
        return periodMonth;
    }

    public void setPeriodMonth(Integer periodMonth) {
        this.periodMonth = periodMonth;
    }

    @NotNull
    @Column(name = "PSC_ID")
    public Long getPscId() {
        return pscId;
    }

    public void setPscId(Long pscId) {
        this.pscId = pscId;
    }

    @NotNull
    @Column(name = "COST_CODE")
    public String getCostCode() {
        return costCode;
    }

    public void setCostCode(String costCode) {
        this.costCode = costCode;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + Objects.hashCode(this.periodYear);
        hash = 67 * hash + Objects.hashCode(this.periodMonth);
        hash = 67 * hash + Objects.hashCode(this.pscId);
        hash = 67 * hash + Objects.hashCode(this.costCode);
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
        final ProductionCostPK other = (ProductionCostPK) obj;
        if (!Objects.equals(this.costCode, other.costCode)) {
            return false;
        }
        if (!Objects.equals(this.periodYear, other.periodYear)) {
            return false;
        }
        if (!Objects.equals(this.periodMonth, other.periodMonth)) {
            return false;
        }
        if (!Objects.equals(this.pscId, other.pscId)) {
            return false;
        }
        return true;
    }

}
