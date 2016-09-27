/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.entity.production.jv;

import com.nnpcgroup.cosm.entity.contract.ContractPK;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Transient;

/**
 *
 * @author 18359
 */
@Embeddable
public class ProductionDetailPK implements Serializable {

    private static final long serialVersionUID = 2983325339937581443L;

    private Integer periodYear;
    private Integer periodMonth;
    private Long fiscalArrangementId;

    private Long contractId;
    private Long contractFiscalArrangementId;
    private String crudeTypeCode;

    public ProductionDetailPK() {
    }

    public ProductionDetailPK(ProductionPK productionPK, ContractPK contractPK) {
        this.periodYear = productionPK.getPeriodYear();
        this.periodMonth = productionPK.getPeriodMonth();
        this.fiscalArrangementId = productionPK.getFiscalArrangementId();

        this.contractId = contractPK.getId();
        this.contractFiscalArrangementId = contractPK.getFiscalArrangementId();
        this.crudeTypeCode = contractPK.getCrudeTypeCode();
    }

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

    @Transient
    public ContractPK getContractPK() {
        return new ContractPK(contractId, contractFiscalArrangementId, crudeTypeCode);
    }

    @Transient
    public ProductionPK getProductionPK() {
        return new ProductionPK(periodYear, periodMonth, fiscalArrangementId);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 73 * hash + Objects.hashCode(this.periodYear);
        hash = 73 * hash + Objects.hashCode(this.periodMonth);
        hash = 73 * hash + Objects.hashCode(this.fiscalArrangementId);
        hash = 73 * hash + Objects.hashCode(this.contractId);
        hash = 73 * hash + Objects.hashCode(this.contractFiscalArrangementId);
        hash = 73 * hash + Objects.hashCode(this.crudeTypeCode);
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
        final ProductionDetailPK other = (ProductionDetailPK) obj;
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
