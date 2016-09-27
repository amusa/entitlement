/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.entity.forecast.jv;

import com.nnpcgroup.cosm.entity.AuditInfo;
import com.nnpcgroup.cosm.entity.AuditListener;
import com.nnpcgroup.cosm.entity.Auditable;
import com.nnpcgroup.cosm.entity.contract.Contract;
import com.nnpcgroup.cosm.entity.production.jv.ProductionDetailPK;
import com.nnpcgroup.cosm.entity.production.jv.ProductionPK;
import org.eclipse.persistence.annotations.Customizer;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * @param <E>
 * @author 18359
 */
@Customizer(ForecastDetailCustomizer.class)
@EntityListeners(AuditListener.class)
@Entity
@Table(name = "FORECAST_DETAIL")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "FTYPE")
public abstract class ForecastDetail<E extends Forecast> implements Auditable, Serializable {

    private static final long serialVersionUID = 1917192116735019964L;

    private ForecastDetailPK forecastDetailPK;
    private Integer periodYear;
    private Integer periodMonth;
    private Contract contract;
    private E forecast;
    private AuditInfo auditInfo = new AuditInfo();

    public ForecastDetail() {
    }

    @EmbeddedId
    public ForecastDetailPK getForecastDetailPK() {
        return forecastDetailPK;
    }

    public void setForecastDetailPK(ForecastDetailPK forecastDetailPK) {
        this.forecastDetailPK = forecastDetailPK;
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
    //@MapsId("contract")
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

    @ManyToOne(targetEntity = Forecast.class)
//    @MapsId("forecast")
    @JoinColumns({
        @JoinColumn(name = "PERIOD_YEAR", referencedColumnName = "PERIOD_YEAR", updatable = false, insertable = false),
        @JoinColumn(name = "PERIOD_MONTH", referencedColumnName = "PERIOD_MONTH", updatable = false, insertable = false),
        @JoinColumn(name = "FISCALARRANGEMENT_ID", referencedColumnName = "FISCALARRANGEMENT_ID", insertable = false, updatable = false)
    })
    public E getForecast() {
        return forecast;
    }

    public void setForecast(E forecast) {
        this.forecast = forecast;
    }

    public ProductionDetailPK makeProductionDetailPK() {
        ProductionDetailPK pPK = new ProductionDetailPK(
                new ProductionPK(this.getPeriodYear(), this.getPeriodMonth(), this.getForecast().getFiscalArrangement().getId()),
                this.getContract().getContractPK()
        );

        return pPK;
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
        hash = 67 * hash + Objects.hashCode(this.forecastDetailPK);
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
        final ForecastDetail<E> other = (ForecastDetail<E>) obj;
        if (!Objects.equals(this.forecastDetailPK, other.forecastDetailPK)) {
            return false;
        }
        return true;
    }

}
