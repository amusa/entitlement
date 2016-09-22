/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.entity.forecast.jv;

import com.nnpcgroup.cosm.entity.contract.ContractPK;

import javax.persistence.*;
import java.io.Serializable;
import java.util.logging.Logger;

/**
 * @author 18359
 */
@Embeddable
public class ForecastDetailPK implements Serializable {

    private static final Logger LOG = Logger.getLogger(ForecastDetailPK.class.getName());
    private static final long serialVersionUID = -5632726719147425922L;

    private ForecastPK forecast;
    private ContractPK contract;

    public ForecastDetailPK() {
    }

    public ForecastDetailPK(ForecastPK forecastPK, ContractPK contractPK) {
        this.forecast = forecastPK;
        this.contract = contractPK;
    }

//    @AttributeOverrides( {
//            @AttributeOverride(name="contract.id", column = @Column(name="CONTRACT_ID") ),
//            @AttributeOverride(name="contract.fiscalArrangementId", column = @Column(name="FISCALARRANGEMENT_ID") ),
//            @AttributeOverride(name="contract.crudeTypeCode", column = @Column(name="CRUDETYPE_CODE") )
//    } )
//    public ForecastPK getForecast() {
//        return forecast;
//    }
//
//    public void setForecast(ForecastPK forecast) {
//        this.forecast = forecast;
//    }
//    @Column(name = "PERIOD_YEAR")
//    public Integer getPeriodYear() {
//        return periodYear;
//    }
//
//    @Column(name = "PERIOD_MONTH")
//    public Integer getPeriodMonth() {
//        return periodMonth;
//    }
//
//    public void setPeriodYear(Integer periodYear) {
//        this.periodYear = periodYear;
//    }
//
//    public void setPeriodMonth(Integer periodMonth) {
//        this.periodMonth = periodMonth;
//    }
    public ContractPK getContract() {
        return contract;
    }

    public void setContract(ContractPK contract) {
        this.contract = contract;
    }

    public ForecastPK getForecast() {
        return forecast;
    }

    public void setForecast(ForecastPK forecast) {
        this.forecast = forecast;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ForecastDetailPK that = (ForecastDetailPK) o;

        if (forecast != null ? !forecast.equals(that.forecast) : that.forecast != null) {
            return false;
        }
        return contract != null ? contract.equals(that.contract) : that.contract == null;

    }

    @Override
    public int hashCode() {
        int result = forecast != null ? forecast.hashCode() : 0;
        result = 31 * result + (contract != null ? contract.hashCode() : 0);
        return result;
    }
}
