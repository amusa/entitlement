/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosm.jv.account.domain.model;

import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 *
 * @author Ayemi
 */
public class CrudePricePK implements Serializable {

    private Date priceDate;
    private String crudeTypeCode;

    @Column(name = "PRICE_DATE")
    @Temporal(TemporalType.DATE)
    public Date getPriceDate() {
        return priceDate;
    }

    public void setPriceDate(Date priceDate) {
        this.priceDate = priceDate;
    }

    @Column(name = "CRUDE_TYPE_CODE")
    public String getCrudeTypeCode() {
        return crudeTypeCode;
    }

    public void setCrudeTypeCode(String crudeTypeCode) {
        this.crudeTypeCode = crudeTypeCode;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 37 * hash + Objects.hashCode(this.priceDate);
        hash = 37 * hash + Objects.hashCode(this.crudeTypeCode);
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
        final com.nnpcgroup.cosm.entity.crude.CrudePricePK other = (com.nnpcgroup.cosm.entity.crude.CrudePricePK) obj;
        if (!Objects.equals(this.crudeTypeCode, other.crudeTypeCode)) {
            return false;
        }
        if (!Objects.equals(this.priceDate, other.priceDate)) {
            return false;
        }
        return true;
    }

}
