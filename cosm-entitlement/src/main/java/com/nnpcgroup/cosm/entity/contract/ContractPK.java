/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.entity.contract;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Embeddable;

/**
 *
 * @author 18359
 */
@Embeddable
public class ContractPK implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long fiscalArrangementId;
    private String crudeTypeCode;

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

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 83 * hash + Objects.hashCode(this.fiscalArrangementId);
        hash = 83 * hash + Objects.hashCode(this.crudeTypeCode);
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
        final ContractPK other = (ContractPK) obj;
        if (!Objects.equals(this.crudeTypeCode, other.crudeTypeCode)) {
            return false;
        }
        if (!Objects.equals(this.fiscalArrangementId, other.fiscalArrangementId)) {
            return false;
        }
        return true;
    }

}
