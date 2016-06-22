/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.entity.forecast.jv;

import com.nnpcgroup.cosm.entity.contract.ContractPK;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.logging.Logger;

/**
 *
 * @author 18359
 */
@Embeddable
public class ForecastPK implements Serializable {
    private static final Logger LOG = Logger.getLogger(ForecastPK.class.getName());
    private static final long serialVersionUID = -5632726719147425922L;
    private Integer periodYear;
    private Integer periodMonth;
    private ContractPK contract;
//    private Long fiscalArrangementId;
//    private String crudeTypeCode;

    
    public ForecastPK() {
    }

    public ForecastPK(Integer periodYear, Integer periodMonth, ContractPK contract) {
        this.periodYear = periodYear;
        this.periodMonth = periodMonth;
        this.contract = contract;
    }

    @Column(name = "PERIOD_YEAR")
    public Integer getPeriodYear() {
        return periodYear;
    }

    @Column(name = "PERIOD_MONTH")
    public Integer getPeriodMonth() {
        return periodMonth;
    }

    public void setPeriodYear(Integer periodYear) {
        this.periodYear = periodYear;
    }

    public void setPeriodMonth(Integer periodMonth) {
        this.periodMonth = periodMonth;
    }


    public ContractPK getContract() {
        return contract;
    }

    public void setContract(ContractPK contract) {
        this.contract = contract;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ForecastPK that = (ForecastPK) o;

        if (!periodYear.equals(that.periodYear)) return false;
        if (!periodMonth.equals(that.periodMonth)) return false;
        return contract.equals(that.contract);

    }

    @Override
    public int hashCode() {
        int result = periodYear.hashCode();
        result = 31 * result + periodMonth.hashCode();
        result = 31 * result + contract.hashCode();
        return result;
    }
}
