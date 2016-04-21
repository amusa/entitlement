/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.entity.contract;

import com.nnpcgroup.cosm.entity.CrudeType;
import com.nnpcgroup.cosm.entity.FiscalArrangement;
import java.io.Serializable;
import javax.persistence.Embedded;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author 18359
 */
@Entity
@Table(name = "CONTRACT")
public  class Contract implements Serializable {

    private static final long serialVersionUID = 4374185291370537475L;

    @EmbeddedId
    ContractPK contractPK;

    @ManyToOne
    @MapsId("crudeTypeCode")
    //@JoinColumn(name="crudeTypeCode")
    private CrudeType crudeType;

    @ManyToOne
    @MapsId("fiscalArrangementId")
    //@JoinColumn(name="fiscalArrangementId")
    private FiscalArrangement fiscalArrangement;

    public Contract() {
    }

    public CrudeType getCrudeType() {
        return crudeType;
    }

    public void setCrudeType(CrudeType crudeType) {
        this.crudeType = crudeType;
    }

    public FiscalArrangement getFiscalArrangement() {
        return fiscalArrangement;
    }

    public void setFiscalArrangement(FiscalArrangement fiscalArrangement) {
        this.fiscalArrangement = fiscalArrangement;
    }

    public ContractPK getContractPK() {
        return contractPK;
    }

    public void setContractPK(ContractPK contractPK) {
        this.contractPK = contractPK;
    }

    @Override
    public String toString() {
        return fiscalArrangement.getTitle() + "/" + crudeType.getCode();
    }

}
