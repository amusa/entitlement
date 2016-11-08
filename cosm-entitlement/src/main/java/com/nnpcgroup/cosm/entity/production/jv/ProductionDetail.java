/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.entity.production.jv;

import com.nnpcgroup.cosm.entity.AuditInfo;
import com.nnpcgroup.cosm.entity.Auditable;
import com.nnpcgroup.cosm.entity.forecast.ForecastDetail;

import java.io.Serializable;
import javax.persistence.*;

/**
 * @author 18359
 */
@MappedSuperclass
public abstract class ProductionDetail implements Auditable, Serializable {

    private static final long serialVersionUID = -115843614381155072L;

    protected Integer periodYear;
    protected Integer periodMonth;
    protected AuditInfo auditInfo = new AuditInfo();

    public ProductionDetail() {
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

    public void duplicate(ForecastDetail forecastDetail) {
        // this.setContract(forecastDetail.getContract());
        this.setPeriodYear(forecastDetail.getPeriodYear());
        this.setPeriodMonth(forecastDetail.getPeriodMonth());
        //this.setProductionDetailPK(forecastDetail.makeProductionDetailPK());
    }

    public void setCurrentUser(String user) {
//        auditInfo.setCurrentUser(user);
        getAuditInfo().setLastModifiedBy(user);
    }

    @Embedded
    public AuditInfo getAuditInfo() {
        if (auditInfo == null) {
            auditInfo = new AuditInfo();
        }
        return auditInfo;
    }

    public void setAuditInfo(AuditInfo auditInfo) {
        this.auditInfo = auditInfo;
    }

}
