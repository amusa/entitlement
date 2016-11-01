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
public class JvForecastEntitlementPK extends ForecastEntitlementPK implements Serializable {

    private static final Logger LOG = Logger.getLogger(JvForecastEntitlementPK.class.getName());
    private static final long serialVersionUID = -5632726719147425922L;

    private Long contractId;
    private Long contractFiscalArrangementId;
    private String crudeTypeCode;

    public JvForecastEntitlementPK() {
    }

    public JvForecastEntitlementPK(ForecastPK forecastPK, ContractPK contractPK) {
        super(forecastPK);
        this.contractId = contractPK.getId();
        this.contractFiscalArrangementId = contractPK.getFiscalArrangementId();
        this.crudeTypeCode = contractPK.getCrudeTypeCode();

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

    @Override
    public int hashCode() {
        int hash = super.hashCode();
        hash = 89 * hash + Objects.hashCode(this.contractId);
        hash = 89 * hash + Objects.hashCode(this.contractFiscalArrangementId);
        hash = 89 * hash + Objects.hashCode(this.crudeTypeCode);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        super.equals(obj);

        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final JvForecastEntitlementPK other = (JvForecastEntitlementPK) obj;
        if (!Objects.equals(this.crudeTypeCode, other.crudeTypeCode)) {
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
