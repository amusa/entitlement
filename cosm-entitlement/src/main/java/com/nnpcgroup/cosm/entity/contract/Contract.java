/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.entity.contract;

import com.nnpcgroup.cosm.entity.CrudeType;
import com.nnpcgroup.cosm.entity.FiscalArrangement;
import com.nnpcgroup.cosm.entity.forecast.jv.Forecast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.*;

/**
 * @author 18359
 */
@Entity
@IdClass(ContractPK.class)
@Table(name = "CONTRACT")
public abstract class Contract implements Serializable {

    private static final long serialVersionUID = 4374185291370537475L;

    private Long fiscalArrangementId;
//    private FiscalArrangement fiscalArrangement;
    private String crudeTypeCode;
//    private CrudeType crudeType;
    private String title;

    public Contract() {
    }

    public Contract(Long fiscalArrangementId, String crudeTypeCode) {
        this.fiscalArrangementId = fiscalArrangementId;
        this.crudeTypeCode = crudeTypeCode;
    }

    @Id
    public Long getFiscalArrangementId() {
        return fiscalArrangementId;
    }

    public void setFiscalArrangementId(Long fiscalArrangementId) {
        this.fiscalArrangementId = fiscalArrangementId;
    }

    @Id
    public String getCrudeTypeCode() {
        return crudeTypeCode;
    }

    public void setCrudeTypeCode(String crudeTypeCode) {
        this.crudeTypeCode = crudeTypeCode;
    }
//
//        @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "FISCALARRANGEMENTID", insertable = false, updatable = false)
//    @MapsId("fiscalArrangementId")
//    public FiscalArrangement getFiscalArrangement() {
//        return fiscalArrangement;
//    }
//
//    public void setFiscalArrangement(FiscalArrangement fiscalArrangement) {
//        this.fiscalArrangement = fiscalArrangement;
//    }


//    @ManyToOne
//    @MapsId("crudeTypeCode")
//    @JoinColumn(name = "CRUDETYPECODE", insertable = false, updatable = false)
//      public CrudeType getCrudeType() {
//        return crudeType;
//    }
//
//    public void setCrudeType(CrudeType crudeType) {
//        this.crudeType = crudeType;
//    }

    @Column(name = "TITLE")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        //return fiscalArrangement.getTitle() + "/" + crudeType.getCode();
        return fiscalArrangementId + "/" + crudeTypeCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Contract contract = (Contract) o;

        if (!fiscalArrangementId.equals(contract.fiscalArrangementId)) return false;
        return crudeTypeCode.equals(contract.crudeTypeCode);

    }

    @Override
    public int hashCode() {
        int result = fiscalArrangementId.hashCode();
        result = 31 * result + crudeTypeCode.hashCode();
        return result;
    }
}
