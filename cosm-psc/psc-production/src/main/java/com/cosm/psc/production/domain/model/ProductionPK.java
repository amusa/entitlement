/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosm.psc.production.domain.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.logging.Logger;

/**
 *
 * @author 18359
 */
@Embeddable
public class ProductionPK implements Serializable {

    private static final Logger LOG = Logger.getLogger(ProductionPK.class.getName());
    private static final long serialVersionUID = -5632726719147425922L;

    private Integer periodYear;
    private Integer periodMonth;
    private Long pscId;
    private Long oilFieldId;

    public ProductionPK() {
    }

    public ProductionPK(Integer periodYear, Integer periodMonth, Long pscId, Long oilFieldId) {
        this.periodYear = periodYear;
        this.periodMonth = periodMonth;
        this.pscId = pscId;
        this.oilFieldId = oilFieldId;
    }

    @Column(name = "PERIOD_YEAR")
    public Integer getPeriodYear() {
        return periodYear;
    }

    @Column(name = "PERIOD_MONTH")
    public Integer getPeriodMonth() {
        return periodMonth;
    }

    public void setPeriodYear(Integer periodYear) {
        this.periodYear = periodYear;
    }

    public void setPeriodMonth(Integer periodMonth) {
        this.periodMonth = periodMonth;
    }

    @Column(name = "PSC_ID")
    public Long getPscId() {
        return pscId;
    }

    public void setPscId(Long pscId) {
        this.pscId = pscId;
    }

    @Column(name = "OIL_FIELD_ID")
    public Long getOilFieldId() {
        return oilFieldId;
    }

    public void setOilFieldId(Long oilFieldId) {
        this.oilFieldId = oilFieldId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ProductionPK that = (ProductionPK) o;

        if (periodYear != null ? !periodYear.equals(that.periodYear) : that.periodYear != null) {
            return false;
        }
        if (periodMonth != null ? !periodMonth.equals(that.periodMonth) : that.periodMonth != null) {
            return false;
        }
        return pscId != null ? pscId.equals(that.pscId) : that.pscId == null;

    }

    @Override
    public int hashCode() {
        int result = periodYear != null ? periodYear.hashCode() : 0;
        result = 31 * result + (periodMonth != null ? periodMonth.hashCode() : 0);
        result = 31 * result + (pscId != null ? pscId.hashCode() : 0);
        return result;
    }
}
