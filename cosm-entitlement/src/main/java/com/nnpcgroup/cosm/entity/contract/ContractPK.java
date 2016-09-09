/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.entity.contract;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 *
 * @author 18359
 */
@Embeddable
public class ContractPK implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private Long fiscalArrangementId;
    private String crudeTypeCode;

    public ContractPK() {
    }

    public ContractPK(Long id, Long fiscalArrangementId, String crudeTypeCode) {
        this.id = id;
        this.fiscalArrangementId = fiscalArrangementId;
        this.crudeTypeCode = crudeTypeCode;
    }

    @Column(name = "ID")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "FISCALARRANGEMENTID")
    public Long getFiscalArrangementId() {
        return fiscalArrangementId;
    }

    public void setFiscalArrangementId(Long fiscalArrangementId) {
        this.fiscalArrangementId = fiscalArrangementId;
    }

    @Column(name = "CRUDETYPECODE")
    public String getCrudeTypeCode() {
        return crudeTypeCode;
    }

    public void setCrudeTypeCode(String crudeTypeCode) {
        this.crudeTypeCode = crudeTypeCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ContractPK that = (ContractPK) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (fiscalArrangementId != null ? !fiscalArrangementId.equals(that.fiscalArrangementId) : that.fiscalArrangementId != null)
            return false;
        return crudeTypeCode != null ? crudeTypeCode.equals(that.crudeTypeCode) : that.crudeTypeCode == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (fiscalArrangementId != null ? fiscalArrangementId.hashCode() : 0);
        result = 31 * result + (crudeTypeCode != null ? crudeTypeCode.hashCode() : 0);
        return result;
    }
}
