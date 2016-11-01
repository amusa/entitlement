/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.entity.forecast.jv;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author 18359
 */
@MappedSuperclass
public class ForecastEntitlementPK implements Serializable {

    private static final long serialVersionUID = -5632726719147425922L;

    protected Integer periodYear;
    protected Integer periodMonth;
    protected Long fiscalArrangementId;

    public ForecastEntitlementPK() {
    }

    public ForecastEntitlementPK(ForecastPK forecastPK) {
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
    
}
