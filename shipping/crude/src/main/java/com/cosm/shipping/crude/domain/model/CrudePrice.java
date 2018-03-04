/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosm.shipping.crude.domain.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Ayemi
 */
@Entity
@Table(name = "CRUDE_PRICE")
@IdClass(CrudePricePK.class)
public class CrudePrice implements Serializable {

    private Date priceDate;
    private String crudeTypeCode;
    private Double osPrice;

    @Id
    @Temporal(javax.persistence.TemporalType.DATE)
    @Column(name = "PRICE_DATE")
    public Date getPriceDate() {
        return priceDate;
    }

    public void setPriceDate(Date priceDate) {
        this.priceDate = priceDate;
    }

    @Id
    @Column(name = "CRUDE_TYPE_CODE")
    public String getCrudeTypeCode() {
        return crudeTypeCode;
    }

    public void setCrudeTypeCode(String crudeTypeCode) {
        this.crudeTypeCode = crudeTypeCode;
    }

    @NotNull
    @Column(name = "OS_PRICE")
    public Double getOsPrice() {
        return osPrice;
    }

    public void setOsPrice(Double osPrice) {
        this.osPrice = osPrice;
    }

}
