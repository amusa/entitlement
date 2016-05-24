/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.entity.contract;

import com.nnpcgroup.cosm.entity.CrudeType;
import com.nnpcgroup.cosm.entity.FiscalArrangement;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Embeddable;

/**
 *
 * @author 18359
 */
//@Embeddable
public class ContractPK implements Serializable {

    private static final long serialVersionUID = 1L;

    private FiscalArrangement fiscalArrangement;
    private CrudeType crudeType;

    public ContractPK() {
    }

    public ContractPK(FiscalArrangement fiscalArrangement, CrudeType crudeType) {
        this.fiscalArrangement = fiscalArrangement;
        this.crudeType = crudeType;
    }

    public FiscalArrangement getFiscalArrangement() {
        return fiscalArrangement;
    }

    public void setFiscalArrangement(FiscalArrangement fiscalArrangement) {
        this.fiscalArrangement = fiscalArrangement;
    }

    public CrudeType getCrudeType() {
        return crudeType;
    }

    public void setCrudeType(CrudeType crudeType) {
        this.crudeType = crudeType;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 83 * hash + Objects.hashCode(this.fiscalArrangement);
        hash = 83 * hash + Objects.hashCode(this.crudeType);
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
        if (!Objects.equals(this.fiscalArrangement, other.fiscalArrangement)) {
            return false;
        }
        if (!Objects.equals(this.crudeType, other.crudeType)) {
            return false;
        }
        return true;
    }

}
