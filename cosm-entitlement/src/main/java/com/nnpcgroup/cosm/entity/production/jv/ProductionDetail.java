/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.entity.production.jv;

import com.nnpcgroup.cosm.entity.contract.Contract;
import com.nnpcgroup.cosm.entity.forecast.jv.ForecastDetail;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

/**
 * @author 18359
 * @param <E>
 */
@Entity
@Table(name = "PRODUCTION_DETAIL")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "PTYPE")
public abstract class ProductionDetail<E extends Production> implements Serializable {

    private static final long serialVersionUID = -115843614381155072L;

    private ProductionDetailPK productionDetailPK;
    private Integer periodYear;
    private Integer periodMonth;
    private Contract contract;
    private E production;

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

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + Objects.hashCode(this.productionDetailPK);
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
        final ProductionDetail other = (ProductionDetail) obj;
        if (!Objects.equals(this.productionDetailPK, other.productionDetailPK)) {
            return false;
        }
        return true;
    }

}
