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
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

/**
 *
 * @author 18359
 */
@Entity
@IdClass(ContractPK.class)
@Table(name = "CONTRACT")
public class Contract implements Serializable {

    private static final long serialVersionUID = 4374185291370537475L;

    private FiscalArrangement fiscalArrangement;
    private CrudeType crudeType;

    public Contract() {
    }

    public Contract(FiscalArrangement fiscalArrangement, CrudeType crudeType) {
        this.fiscalArrangement = fiscalArrangement;
        this.crudeType = crudeType;
    }
    
    
    @Id
    @ManyToOne
    //@MapsId("crudeTypeCode")
    public CrudeType getCrudeType() {
        return crudeType;
    }

    public void setCrudeType(CrudeType crudeType) {
        this.crudeType = crudeType;
    }

    @Id
    @ManyToOne
    //@MapsId("fiscalArrangementId")
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
        hash = 71 * hash + Objects.hashCode(this.crudeType);
        hash = 71 * hash + Objects.hashCode(this.fiscalArrangement);
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
        if (!Objects.equals(this.crudeType, other.crudeType)) {
            return false;
        }
        if (!Objects.equals(this.fiscalArrangement, other.fiscalArrangement)) {
            return false;
        }
        return true;
    }

}
