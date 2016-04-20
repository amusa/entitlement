/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.entity;

import java.io.Serializable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 *
 * @author 18359
 */
@Entity
@Table(name = "PRICE")
public class Price implements Serializable {

    private static final long serialVersionUID = -5594726430705947415L;

    @EmbeddedId
    PricePK pricePK;
    private double realizablePrice;

    public Price() {
    }
   
    public PricePK getPricePK() {
        return pricePK;
    }

    public void setPricePK(PricePK pricePK) {
        this.pricePK = pricePK;
    }

     @NotNull
    public double getRealizablePrice() {
        return realizablePrice;
    }

    public void setRealizablePrice(double realizablePrice) {
        this.realizablePrice = realizablePrice;
    }

}
