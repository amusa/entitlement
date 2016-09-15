/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.entity.forecast.jv;

import com.nnpcgroup.cosm.entity.contract.Contract;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author 18359
 */
@Entity
@Table(name = "FORECAST_DETAIL")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "FTYPE")
public abstract class ForecastDetail implements Serializable {

    private static final long serialVersionUID = 2917192116735019964L;

    private ForecastDetailPK forecastDetailPK;
    private Integer periodYear;
    private Integer periodMonth;
    private Contract contract;



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

}
