/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.entity.forecast;

import com.nnpcgroup.cosm.entity.AuditInfo;
import com.nnpcgroup.cosm.entity.Auditable;
import com.nnpcgroup.cosm.entity.FiscalArrangement;
import com.nnpcgroup.cosm.entity.production.jv.ProductionDetailPK;

import javax.persistence.*;
import java.io.Serializable;
import javax.validation.constraints.NotNull;

/**
 * @author 18359
 */
@MappedSuperclass
public abstract class ForecastDetail implements Auditable, Serializable {

    private static final long serialVersionUID = 1917192116735019964L;

    //private Long id;
    protected Integer periodYear;
    protected Integer periodMonth;
    protected FiscalArrangement fiscalArrangement;
    
    private Double grossProduction;
    private Double dailyProduction;

    private AuditInfo auditInfo = new AuditInfo();

    public ForecastDetail() {
    }

//    @Id
//    @Column(name = "ID")
//    @GeneratedValue(strategy = GenerationType.TABLE)
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }

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

    @ManyToOne
    @MapsId("fiscalArrangementId")
    @JoinColumn(name = "FISCALARRANGEMENT_ID", referencedColumnName = "ID", insertable = false, updatable = false)
    public FiscalArrangement getFiscalArrangement() {
        return fiscalArrangement;
    }

    public void setFiscalArrangement(FiscalArrangement fiscalArrangement) {
        this.fiscalArrangement = fiscalArrangement;
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

    public abstract ProductionDetailPK makeProductionDetailPK();

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
