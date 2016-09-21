/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.entity.production.jv;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;

/**
 *
 * @author 18359
 */
@Embeddable
public class ProductionPK implements Serializable {

    private static final long serialVersionUID = 2983325339937581443L;

    private Integer periodYear;
    private Integer periodMonth;
    private Long fiscalArrangementId;

    public ProductionPK() {
    }

    public ProductionPK(Integer periodYear, Integer periodMonth, Long fiscalArrangementId) {
        this.periodYear = periodYear;
        this.periodMonth = periodMonth;
        this.fiscalArrangementId = fiscalArrangementId;
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

    @Column(name = "FISCALARRANGEMENT_ID")
    public Long getFiscalArrangementId() {
        return fiscalArrangementId;
    }

    public void setFiscalArrangementId(Long fiscalArrangementId) {
        this.fiscalArrangementId = fiscalArrangementId;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 83 * hash + Objects.hashCode(this.periodYear);
        hash = 83 * hash + Objects.hashCode(this.periodMonth);
        hash = 83 * hash + Objects.hashCode(this.fiscalArrangementId);
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
        final ProductionPK other = (ProductionPK) obj;
        if (!Objects.equals(this.periodYear, other.periodYear)) {
            return false;
        }
        if (!Objects.equals(this.periodMonth, other.periodMonth)) {
            return false;
        }
        if (!Objects.equals(this.fiscalArrangementId, other.fiscalArrangementId)) {
            return false;
        }
        return true;
    }

}
