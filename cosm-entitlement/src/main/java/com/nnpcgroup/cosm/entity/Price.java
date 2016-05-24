/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 *
 * @author 18359
 */
@Entity
@IdClass(PricePK.class)
@Table(name = "PRICE")
public class Price implements Serializable {

    private static final long serialVersionUID = -5594726430705947415L;

    private double realizablePrice;
    private int periodYear;
    private int periodMonth;

    public Price() {
    }

    @NotNull
    public double getRealizablePrice() {
        return realizablePrice;
    }

    public void setRealizablePrice(double realizablePrice) {
        this.realizablePrice = realizablePrice;
    }

    @Id
    public int getPeriodYear() {
        return periodYear;
    }

    public void setPeriodYear(int periodYear) {
        this.periodYear = periodYear;
    }

    @Id
    public int getPeriodMonth() {
        return periodMonth;
    }

    public void setPeriodMonth(int periodMonth) {
        this.periodMonth = periodMonth;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + this.periodYear;
        hash = 17 * hash + this.periodMonth;
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
        final Price other = (Price) obj;
        if (this.periodYear != other.periodYear) {
            return false;
        }
        if (this.periodMonth != other.periodMonth) {
            return false;
        }
        return true;
    }

}
