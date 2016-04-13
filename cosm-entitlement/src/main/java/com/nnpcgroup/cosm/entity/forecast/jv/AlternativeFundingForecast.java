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
    private Double carryOil;
    private Double sharedOil;
    private Double carryTaxExpenditure;
    private Double carryTaxRelief;
    private Double residualCarryExpenditure;
    private Double guaranteedNotionalMargin;
    private Double capitalCarryCostAmortized;

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

    public Double getCarryTaxExpenditure() {
        return carryTaxExpenditure;
    }

    public void setCarryTaxExpenditure(Double carryTaxExpenditure) {
        this.carryTaxExpenditure = carryTaxExpenditure;
    }

    public Double getCarryTaxRelief() {
        return carryTaxRelief;
    }

    public void setCarryTaxRelief(Double carryTaxRelief) {
        this.carryTaxRelief = carryTaxRelief;
    }

    public Double getResidualCarryExpenditure() {
        return residualCarryExpenditure;
    }

    public void setResidualCarryExpenditure(Double residualCarryExpenditure) {
        this.residualCarryExpenditure = residualCarryExpenditure;
    }

    public Double getGuaranteedNotionalMargin() {
        return guaranteedNotionalMargin;
    }

    public void setGuaranteedNotionalMargin(Double guaranteedNotionalMargin) {
        this.guaranteedNotionalMargin = guaranteedNotionalMargin;
    }

    public Double getCapitalCarryCostAmortized() {
        return capitalCarryCostAmortized;
    }

    public void setCapitalCarryCostAmortized(Double capitalCarryCostAmortized) {
        this.capitalCarryCostAmortized = capitalCarryCostAmortized;
    }

    
}
