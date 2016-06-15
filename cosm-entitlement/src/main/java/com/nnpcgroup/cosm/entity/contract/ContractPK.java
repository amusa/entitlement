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

/**
 *
 * @author 18359
 */
public class ContractPK implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long fiscalArrangementId;
    private String crudeTypeCode;

    public ContractPK() {
    }

    public ContractPK(Long fiscalArrangementId, String crudeTypeCode) {
        this.fiscalArrangementId = fiscalArrangementId;
        this.crudeTypeCode = crudeTypeCode;
    }

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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ContractPK that = (ContractPK) o;

        if (!fiscalArrangementId.equals(that.fiscalArrangementId)) return false;
        return crudeTypeCode.equals(that.crudeTypeCode);

    }

    @Override
    public int hashCode() {
        int result = fiscalArrangementId.hashCode();
        result = 31 * result + crudeTypeCode.hashCode();
        return result;
    }
}
