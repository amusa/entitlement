/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.entity.forecast.jv;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 *
 * @author 18359
 */
@Entity
@DiscriminatorValue("AF")
public abstract class AlternativeFundingForecast extends Forecast {

    private static final long serialVersionUID = 2917192116735019964L;

    private Double tangibleCost;
    private Double intangibleCost;
    protected Double carryOil;
    protected Double sharedOil;

    public AlternativeFundingForecast() {
    }

    public Double getTangibleCost() {
        return tangibleCost;
    }

    public void setTangibleCost(Double tangibleCost) {
        this.tangibleCost = tangibleCost;
    }

    public Double getIntangibleCost() {
        return intangibleCost;
    }

    public void setIntangibleCost(Double intangibleCost) {
        this.intangibleCost = intangibleCost;
    }

    public Double getCarryOil() {
        return carryOil;
    }

    public void setCarryOil(Double carryOil) {
        this.carryOil = carryOil;
    }

    public Double getSharedOil() {
        return sharedOil;
    }

    public void setSharedOil(Double sharedOil) {
        this.sharedOil = sharedOil;
    }

}
