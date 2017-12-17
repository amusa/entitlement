/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosm.psc.domain.model.production;

import com.cosm.common.domain.util.AuditInfo;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.logging.Logger;

/**
 * @author 18359
 */

@Entity
@Table(name = "PRODUCTION")
public class Production implements Serializable{

    private static final long serialVersionUID = -295843614383355072L;

    private static final Logger LOG = Logger.getLogger(Production.class.getName());

    private ProductionPK productionPK;

    private Integer periodYear;
    private Integer periodMonth;
    private Long pscId;
    private Long oilFieldId;

    private Double grossProduction;
    private Double dailyProduction;

    private AuditInfo auditInfo = new AuditInfo();


    public Production() {
    }


    @EmbeddedId
    public ProductionPK getForecastPK() {
        return productionPK;
    }

    public void setForecastPK(ProductionPK forecastPK) {
        this.productionPK = forecastPK;
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

    @Column(name = "PSC_ID", updatable = false, insertable = false)
    public Long getPscId() {
        return pscId;
    }

    public void setPscId(Long pscId) {
        this.pscId = pscId;
    }

    @Column(name = "OIL_FIELD_ID", updatable = false, insertable = false)
    public Long getOilFieldId() {
        return oilFieldId;
    }

    public void setOilFieldId(Long oilFieldId) {
        this.oilFieldId = oilFieldId;
    }

    @NotNull
    @Column(name = "DAILY_PRODUCTION")
    public Double getDailyProduction() {
        return dailyProduction;
    }

    public void setDailyProduction(Double dailyProduction) {
        this.dailyProduction = dailyProduction;
    }

    @Column(name = "GROSS_PRODUCTION")
    public Double getGrossProduction() {
        return grossProduction;
    }

    public void setGrossProduction(Double grossProduction) {
        this.grossProduction = grossProduction;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Production that = (Production) o;

        return productionPK.equals(that.productionPK);
    }

    @Override
    public int hashCode() {
        return productionPK.hashCode();
    }
}
