/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.entity.production.jv;

import com.nnpcgroup.cosm.entity.contract.ContractPK;
import javax.persistence.Embeddable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Transient;

/**
 *
 * @author 18359
 */
@Embeddable
public class JvProductionDetailPK extends ProductionDetailPK {

    private static final long serialVersionUID = 2983325339937581443L;

    private Long contractId;
    private Long contractFiscalArrangementId;
    private String crudeTypeCode;

    public JvProductionDetailPK() {
    }

    public JvProductionDetailPK(ProductionPK productionPK, ContractPK contractPK) {
        super(productionPK);

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
        hash = 53 * hash + Objects.hashCode(this.contractId);
        hash = 53 * hash + Objects.hashCode(this.contractFiscalArrangementId);
        hash = 53 * hash + Objects.hashCode(this.crudeTypeCode);
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
        final JvProductionDetailPK other = (JvProductionDetailPK) obj;
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
