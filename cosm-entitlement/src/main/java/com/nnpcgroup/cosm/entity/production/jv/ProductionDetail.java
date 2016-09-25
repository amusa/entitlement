/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.entity.production.jv;

import com.nnpcgroup.cosm.entity.AuditInfo;
import com.nnpcgroup.cosm.entity.AuditListener;
import com.nnpcgroup.cosm.entity.Auditable;
import com.nnpcgroup.cosm.entity.contract.Contract;
import com.nnpcgroup.cosm.entity.forecast.jv.ForecastDetail;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.*;

/**
 * @author 18359
 * @param <E>
 */
@EntityListeners(AuditListener.class)
@Entity
@Table(name = "PRODUCTION_DETAIL")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "PTYPE")
public abstract class ProductionDetail<E extends Production>  implements Auditable, Serializable {

    private static final long serialVersionUID = -115843614381155072L;

    private ProductionDetailPK productionDetailPK;
    private Integer periodYear;
    private Integer periodMonth;
    private Contract contract;
    private E production;
    private AuditInfo auditInfo = new AuditInfo();

    public ProductionDetail() {
    }

    @EmbeddedId
    public ProductionDetailPK getProductionDetailPK() {
        return productionDetailPK;
    }

    public void setProductionDetailPK(ProductionDetailPK productionDetailPK) {
        this.productionDetailPK = productionDetailPK;
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

    @ManyToOne
    @MapsId("contract")
    @JoinColumns({
        @JoinColumn(name = "CONTRACT_ID", referencedColumnName = "ID", insertable = false, updatable = false),
        @JoinColumn(name = "CONTRACT_FISCAL_ID", referencedColumnName = "FISCALARRANGEMENTID", insertable = false, updatable = false),
        @JoinColumn(name = "CRUDETYPE_CODE", referencedColumnName = "CRUDETYPECODE", insertable = false, updatable = false)
    })
    public Contract getContract() {
        return contract;
    }

    public void setContract(Contract contract) {
        this.contract = contract;
    }

    @ManyToOne(targetEntity = Production.class)
    @MapsId("production")
    @JoinColumns({
        @JoinColumn(name = "PERIOD_YEAR", referencedColumnName = "PERIOD_YEAR", updatable = false, insertable = false),
        @JoinColumn(name = "PERIOD_MONTH", referencedColumnName = "PERIOD_MONTH", updatable = false, insertable = false),
        @JoinColumn(name = "FISCALARRANGEMENT_ID", referencedColumnName = "FISCALARRANGEMENT_ID", insertable = false, updatable = false)
    })
    public E getProduction() {
        return production;
    }

    public void duplicate(ForecastDetail forecastDetail) {
        this.setContract(forecastDetail.getContract());
        this.setPeriodYear(forecastDetail.getPeriodYear());
        this.setPeriodMonth(forecastDetail.getPeriodMonth());
        this.setProductionDetailPK(forecastDetail.makeProductionDetailPK());
    }

    public void setProduction(E production) {
        this.production = production;
    }

    public void setCurrentUser(String user) {
//        auditInfo.setCurrentUser(user);
        auditInfo.setLastModifiedBy(user);
    }

    @Embedded
    public AuditInfo getAuditInfo() { return auditInfo; }

    public void setAuditInfo(AuditInfo auditInfo) {
        this.auditInfo = auditInfo;
    }

}
