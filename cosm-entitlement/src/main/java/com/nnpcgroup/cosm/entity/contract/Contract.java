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
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

/**
 *
 * @author 18359
 */
@Entity
@Table(name = "CONTRACT")
public class Contract implements Serializable {

    private static final long serialVersionUID = 4374185291370537475L;

    private ContractPK contractPK;
    private CrudeType crudeType;
    private FiscalArrangement fiscalArrangement;

    public Contract() {
    }

    @EmbeddedId
    public ContractPK getContractPK() {
        return contractPK;
    }

    public void setContractPK(ContractPK contractPK) {
        this.contractPK = contractPK;
    }

    @ManyToOne
    @MapsId("crudeTypeCode")
    public CrudeType getCrudeType() {
        return crudeType;
    }

    public void setCrudeType(CrudeType crudeType) {
        this.crudeType = crudeType;
    }

    @ManyToOne
    @MapsId("fiscalArrangementId")
    public FiscalArrangement getFiscalArrangement() {
        return fiscalArrangement;
    }

    public void setFiscalArrangement(FiscalArrangement fiscalArrangement) {
        this.fiscalArrangement = fiscalArrangement;
    }

    @Override
    public String toString() {
        return fiscalArrangement.getTitle() + "/" + crudeType.getCode();
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 67 * hash + Objects.hashCode(this.contractPK);
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
        final Contract other = (Contract) obj;
        if (!Objects.equals(this.contractPK, other.contractPK)) {
            return false;
        }
        return true;
    }
    
    

}
