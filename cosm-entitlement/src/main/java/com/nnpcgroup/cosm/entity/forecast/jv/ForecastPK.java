/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.entity.forecast.jv;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.logging.Logger;

/**
 *
 * @author 18359
 */
@Embeddable
public class ForecastPK implements Serializable {

    private static final Logger LOG = Logger.getLogger(ForecastPK.class.getName());
    private static final long serialVersionUID = -5632726719147425922L;
    private Integer periodYear;
    private Integer periodMonth;
    private Long fiscalArrangementId;

    public ForecastPK() {
    }

    public ForecastPK(Integer periodYear, Integer periodMonth, Long fiscalArrangementId) {
        this.periodYear = periodYear;
        this.periodMonth = periodMonth;
        this.fiscalArrangementId = fiscalArrangementId;
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

    @Column(name = "FISCALARRANGEMENT_ID")
    public Long getFiscalArrangementId() {
        return fiscalArrangementId;
    }

    public void setFiscalArrangementId(Long fiscalArrangementId) {
        this.fiscalArrangementId = fiscalArrangementId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ForecastPK that = (ForecastPK) o;

        if (periodYear != null ? !periodYear.equals(that.periodYear) : that.periodYear != null) {
            return false;
        }
        if (periodMonth != null ? !periodMonth.equals(that.periodMonth) : that.periodMonth != null) {
            return false;
        }
        return fiscalArrangementId != null ? fiscalArrangementId.equals(that.fiscalArrangementId) : that.fiscalArrangementId == null;

    }

    @Override
    public int hashCode() {
        int result = periodYear != null ? periodYear.hashCode() : 0;
        result = 31 * result + (periodMonth != null ? periodMonth.hashCode() : 0);
        result = 31 * result + (fiscalArrangementId != null ? fiscalArrangementId.hashCode() : 0);
        return result;
    }
}
