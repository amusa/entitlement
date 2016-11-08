/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.entity.production.jv;

import com.nnpcgroup.cosm.entity.forecast.jv.AlternativeFundingForecastDetail;
import com.nnpcgroup.cosm.entity.forecast.ForecastDetail;
import javax.persistence.Entity;

/**
 *
 * @author 18359
 */
@Entity
public abstract class AlternativeFundingProductionEntitlement extends JvProductionEntitlement {

    private static final long serialVersionUID = 4881837273578907336L;

    private Double tangibleCost;
    private Double intangibleCost;
    private Double carryOil;
    private Double carryOilReceived;
    private Double sharedOil;
    private Double carryTaxExpenditure;
    private Double carryTaxRelief;
    private Double residualCarryExpenditure;
    private Double guaranteedNotionalMargin;
    private Double capitalCarryCostAmortized;
    private Double carryOilCum;
    private Double sharedOilCum;
    private Double carryTaxExpenditureCum;
    private Double carryTaxReliefCum;
    private Double residualCarryExpenditureCum;
    private Double capitalCarryCostAmortizedCum;
    private Double carryOilReceivedCum;

    public AlternativeFundingProductionEntitlement() {
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

    public Double getCarryOilCum() {
        return carryOilCum;
    }

    public void setCarryOilCum(Double carryOilCum) {
        this.carryOilCum = carryOilCum;
    }

    public Double getSharedOilCum() {
        return sharedOilCum;
    }

    public void setSharedOilCum(Double sharedOilCum) {
        this.sharedOilCum = sharedOilCum;
    }

    public Double getCarryTaxExpenditureCum() {
        return carryTaxExpenditureCum;
    }

    public void setCarryTaxExpenditureCum(Double carryTaxExpenditureCum) {
        this.carryTaxExpenditureCum = carryTaxExpenditureCum;
    }

    public Double getCarryTaxReliefCum() {
        return carryTaxReliefCum;
    }

    public void setCarryTaxReliefCum(Double carryTaxReliefCum) {
        this.carryTaxReliefCum = carryTaxReliefCum;
    }

    public Double getResidualCarryExpenditureCum() {
        return residualCarryExpenditureCum;
    }

    public void setResidualCarryExpenditureCum(Double residualCarryExpenditureCum) {
        this.residualCarryExpenditureCum = residualCarryExpenditureCum;
    }

    public Double getCapitalCarryCostAmortizedCum() {
        return capitalCarryCostAmortizedCum;
    }

    public void setCapitalCarryCostAmortizedCum(Double capitalCarryCostAmortizedCum) {
        this.capitalCarryCostAmortizedCum = capitalCarryCostAmortizedCum;
    }

    public Double getCarryOilReceived() {
        return carryOilReceived;
    }

    public void setCarryOilReceived(Double carryOilReceived) {
        this.carryOilReceived = carryOilReceived;
    }

    public Double getCarryOilReceivedCum() {
        return carryOilReceivedCum;
    }

    public void setCarryOilReceivedCum(Double carryOilReceivedCum) {
        this.carryOilReceivedCum = carryOilReceivedCum;
    }

    @Override
    public void duplicate(ForecastDetail forecastDetail) {
        super.duplicate(forecastDetail);
        AlternativeFundingForecastDetail afDetail = (AlternativeFundingForecastDetail) forecastDetail;

//        this.tangibleCost = afDetail.getTangibleCost();
//        this.intangibleCost = afDetail.getIntangibleCost();
    }
}
