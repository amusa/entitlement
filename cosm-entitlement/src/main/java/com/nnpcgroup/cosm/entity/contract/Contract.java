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
import javax.persistence.*;

/**
 *
 * @author 18359
 */
@Entity
@IdClass(ContractPK.class)
@Table(name = "CONTRACT")
public abstract class Contract implements Serializable {

    private static final long serialVersionUID = 4374185291370537475L;

    private Long fiscalArrangementId;
    private String crudeTypeCode;

    private FiscalArrangement fiscalArrangement;
    private CrudeType crudeType;

    public Contract() {
    }

    public Contract(Long fiscalArrangementId, String crudeTypeCode) {
        this.fiscalArrangementId = fiscalArrangementId;
        this.crudeTypeCode = crudeTypeCode;
    }

    @Id
    public String getCrudeTypeCode() {
        return crudeTypeCode;
    }

    public void setCrudeTypeCode(String crudeTypeCode) {
        this.crudeTypeCode = crudeTypeCode;
    }

    @Id
    public Long getFiscalArrangementId() {
        return fiscalArrangementId;
    }

    public void setFiscalArrangementId(Long fiscalArrangementId) {
        this.fiscalArrangementId = fiscalArrangementId;
    }

    @ManyToOne
    @MapsId("fiscalArrangementId")
    @JoinColumn(name = "FISCALARRANGEMENTID", referencedColumnName = "ID", updatable = false, insertable = false)
    public FiscalArrangement getFiscalArrangement() {
        return fiscalArrangement;
    }

    public void setFiscalArrangement(FiscalArrangement fiscalArrangement) {
        this.fiscalArrangement = fiscalArrangement;
    }

    @ManyToOne
    @JoinColumn(name = "CRUDETYPECODE", referencedColumnName = "CODE", updatable = false, insertable = false)
    @MapsId("crudeTypeCode")
    public CrudeType getCrudeType() {
        return crudeType;
    }

    public void setCrudeType(CrudeType crudeType) {
        this.crudeType = crudeType;
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
