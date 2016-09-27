/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.entity.forecast.jv;

import com.nnpcgroup.cosm.entity.contract.ContractPK;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.logging.Logger;

/**
 * @author 18359
 */
@Embeddable
public class ForecastDetailPK implements Serializable {

    private static final Logger LOG = Logger.getLogger(ForecastDetailPK.class.getName());
    private static final long serialVersionUID = -5632726719147425922L;

//    private ForecastPK forecast;
//    private ContractPK contract;
    private Integer periodYear;
    private Integer periodMonth;
    private Long fiscalArrangementId;

    private Long contractId;
    private Long contractFiscalArrangementId;
    private String crudeTypeCode;

    public ForecastDetailPK() {
    }

    public ForecastDetailPK(ForecastPK forecastPK, ContractPK contractPK) {
        this.periodYear = forecastPK.getPeriodYear();
        this.periodMonth = forecastPK.getPeriodMonth();
        this.fiscalArrangementId = forecastPK.getFiscalArrangementId();

        this.contractId = contractPK.getId();
        this.contractFiscalArrangementId = contractPK.getFiscalArrangementId();
        this.crudeTypeCode = contractPK.getCrudeTypeCode();
    }

    @Transient
    public ContractPK getContractPK() {
        return new ContractPK(contractId, contractFiscalArrangementId, crudeTypeCode);
    }

    @Transient
    public ForecastPK getForecastPK() {
        return new ForecastPK(periodYear, periodMonth, fiscalArrangementId);
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
//    public ContractPK getContract() {
//        return contract;
//    }
//
//    public void setContract(ContractPK contract) {
//        this.contract = contract;
//    }
//
//    public ForecastPK getForecast() {
//        return forecast;
//    }
//
//    public void setForecast(ForecastPK forecast) {
//        this.forecast = forecast;
//    }
    @Column(name = "PERIOD_YEAR")
    public Integer getPeriodYear() {
        return periodYear;
    }

    public void setPeriodYear(Integer periodYear) {
        this.periodYear = periodYear;
    }

    @Column(name = "PERIOD_MONTH")
    public Integer getPeriodMonth() {
        return periodMonth;
    }

    public void setPeriodMonth(Integer periodMonth) {
        this.periodMonth = periodMonth;
    }

    @Column(name = "FISCALARRANGEMENT_ID")
    public Long getFiscalArrangementId() {
        return fiscalArrangementId;
    }

    public void setFiscalArrangementId(Long fiscalArrangementId) {
        this.fiscalArrangementId = fiscalArrangementId;
    }

    @Column(name = "CONTRACT_ID")
    public Long getContractId() {
        return contractId;
    }

    public void setContractId(Long contractId) {
        this.contractId = contractId;
    }

    @Column(name = "CONTRACT_FISCAL_ID")
    public Long getContractFiscalArrangementId() {
        return contractFiscalArrangementId;
    }

    public void setContractFiscalArrangementId(Long contractFiscalArrangementId) {
        this.contractFiscalArrangementId = contractFiscalArrangementId;
    }

    @Column(name = "CRUDETYPE_CODE")
    public String getCrudeTypeCode() {
        return crudeTypeCode;
    }

    public void setCrudeTypeCode(String crudeTypeCode) {
        this.crudeTypeCode = crudeTypeCode;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + Objects.hashCode(this.periodYear);
        hash = 59 * hash + Objects.hashCode(this.periodMonth);
        hash = 59 * hash + Objects.hashCode(this.fiscalArrangementId);
        hash = 59 * hash + Objects.hashCode(this.contractId);
        hash = 59 * hash + Objects.hashCode(this.contractFiscalArrangementId);
        hash = 59 * hash + Objects.hashCode(this.crudeTypeCode);
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
        final ForecastDetailPK other = (ForecastDetailPK) obj;
        if (!Objects.equals(this.crudeTypeCode, other.crudeTypeCode)) {
            return false;
        }
        if (!Objects.equals(this.periodYear, other.periodYear)) {
            return false;
        }
        if (!Objects.equals(this.periodMonth, other.periodMonth)) {
            return false;
        }
        if (!Objects.equals(this.fiscalArrangementId, other.fiscalArrangementId)) {
            return false;
        }
        if (!Objects.equals(this.contractId, other.contractId)) {
            return false;
        }
        if (!Objects.equals(this.contractFiscalArrangementId, other.contractFiscalArrangementId)) {
            return false;
        }
        return true;
    }

}
