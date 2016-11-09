/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.entity.forecast;

import com.nnpcgroup.cosm.entity.AuditInfo;
import com.nnpcgroup.cosm.entity.Auditable;
import com.nnpcgroup.cosm.entity.FiscalArrangement;
import com.nnpcgroup.cosm.entity.contract.OilField;

import java.io.Serializable;
import java.util.Objects;
import java.util.logging.Logger;
import javax.persistence.*;

/**
 * @author 18359
 */
@MappedSuperclass
public abstract class ForecastBase implements Auditable, Serializable {

    private static final long serialVersionUID = -295843614383355072L;

    private static final Logger LOG = Logger.getLogger(ForecastBase.class.getName());

    private ForecastBasePK forecastPK;
    private Integer periodYear;
    private FiscalArrangement fiscalArrangement;
    private OilField oilField;
    private String remark;
    private AuditInfo auditInfo = new AuditInfo();

    public ForecastBase() {
    }

    @EmbeddedId
    public ForecastBasePK getForecastPK() {
        return forecastPK;
    }

    public void setForecastPK(ForecastBasePK forecastPK) {
        this.forecastPK = forecastPK;        
    }

    @Column(name = "PERIOD_YEAR", updatable = false, insertable = false)
    public Integer getPeriodYear() {
        return periodYear;
    }

    public void setPeriodYear(Integer periodYear) {
        this.periodYear = periodYear;
    }
    
    @ManyToOne
    @JoinColumn(name = "FISCALARRANGEMENT_ID", referencedColumnName = "ID")
    @MapsId("fiscalArrangementId")
    public FiscalArrangement getFiscalArrangement() {
        return fiscalArrangement;
    }

    public void setFiscalArrangement(FiscalArrangement fiscalArrangement) {
        this.fiscalArrangement = fiscalArrangement;
    }
        
    @Column(name = "REMARK")
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + Objects.hashCode(this.forecastPK);
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
        final ForecastBase other = (ForecastBase) obj;
        if (!Objects.equals(this.forecastPK, other.forecastPK)) {
            return false;
        }
        return true;
    }

}
