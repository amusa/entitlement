/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.entity.forecast.jv;

import com.nnpcgroup.cosm.entity.contract.ContractPK;

import java.io.Serializable;
import java.util.logging.Logger;

/**
 *
 * @author 18359
 */
public class ForecastPK implements Serializable {
    private static final Logger LOG = Logger.getLogger(ForecastPK.class.getName());
    private static final long serialVersionUID = -5632726719147425922L;
    private Integer periodYear;
    private Integer periodMonth;
//    private ContractPK contract;
    private Long fiscalArrangementId;
    private String crudeTypeCode;

    
    public ForecastPK() {
    }

    public ForecastPK(Integer periodYear, Integer periodMonth, Long fiscalArrangementId, String crudeTypeCode) {
        this.periodYear = periodYear;
        this.periodMonth = periodMonth;
        this.fiscalArrangementId = fiscalArrangementId;
        this.crudeTypeCode = crudeTypeCode;
    }

    public Integer getPeriodYear() {
        return periodYear;
    }

    public Integer getPeriodMonth() {
        return periodMonth;
    }

    public void setPeriodYear(Integer periodYear) {
        this.periodYear = periodYear;
    }

    public void setPeriodMonth(Integer periodMonth) {
        this.periodMonth = periodMonth;
    }

    public Long getFiscalArrangementId() {
        return fiscalArrangementId;
    }

    public void setFiscalArrangementId(Long fiscalArrangementId) {
        this.fiscalArrangementId = fiscalArrangementId;
    }

    public String getCrudeTypeCode() {
        return crudeTypeCode;
    }

    public void setCrudeTypeCode(String crudeTypeCode) {
        this.crudeTypeCode = crudeTypeCode;
    }

//    public ContractPK getContract() {
//        return contract;
//    }
//
//    public void setContract(ContractPK contract) {
//        this.contract = contract;
//    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ForecastPK that = (ForecastPK) o;

        if (!periodYear.equals(that.periodYear)) return false;
        if (!periodMonth.equals(that.periodMonth)) return false;
        if (!fiscalArrangementId.equals(that.fiscalArrangementId)) return false;
        return crudeTypeCode.equals(that.crudeTypeCode);

    }

    @Override
    public int hashCode() {
        int result = periodYear.hashCode();
        result = 31 * result + periodMonth.hashCode();
        result = 31 * result + fiscalArrangementId.hashCode();
        result = 31 * result + crudeTypeCode.hashCode();
        return result;
    }
}
