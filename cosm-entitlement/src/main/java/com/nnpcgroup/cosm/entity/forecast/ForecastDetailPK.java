/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.entity.forecast;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * @author 18359
 */
@MappedSuperclass
public class ForecastDetailPK implements Serializable {

    private static final long serialVersionUID = -5632726719147425922L;

    protected Integer periodYear;
    protected Integer periodMonth;
    protected Long fiscalArrangementId;

    public ForecastDetailPK() {
    }

    public ForecastDetailPK(ForecastPK forecastPK) {
        this.periodYear = forecastPK.getPeriodYear();
        this.periodMonth = forecastPK.getPeriodMonth();
        this.fiscalArrangementId = forecastPK.getFiscalArrangementId();
    }

    @Transient
    public ForecastPK getForecastPK() {
        return new ForecastPK(periodYear, periodMonth, fiscalArrangementId);
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
        int hash = 7;
        hash = 59 * hash + Objects.hashCode(this.periodYear);
        hash = 59 * hash + Objects.hashCode(this.periodMonth);
        hash = 59 * hash + Objects.hashCode(this.fiscalArrangementId);
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
        final ForecastDetailPK other = (ForecastDetailPK) obj;

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
