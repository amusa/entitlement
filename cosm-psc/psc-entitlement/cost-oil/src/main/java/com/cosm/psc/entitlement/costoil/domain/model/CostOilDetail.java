/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosm.psc.entitlement.costoil.domain.model;

import java.io.Serializable;

/**
 * @author Ayemi
 */
public class CostOilDetail implements Serializable {

    private double armotizedCapex;
    private double opex;
    private double educationTax;
    private double costOilBfw;

    public double getArmotizedCapex() {
        return armotizedCapex;
    }

    public void setArmotizedCapex(double armotizedCapex) {
        this.armotizedCapex = armotizedCapex;
    }

    public double getOpex() {
        return opex;
    }

    public void setOpex(Double opex) {
        this.opex = opex;
    }

    public double getEducationTax() {
        return educationTax;
    }

    public void setEducationTax(double educationTax) {
        this.educationTax = educationTax;
    }

    public double getCostOilBfw() {
        return costOilBfw;
    }

    public void setCostOilBfw(double costOilBfw) {
        this.costOilBfw = costOilBfw;
    }

    public double getCostOil() {
        return armotizedCapex + opex + Math.max(0, educationTax);
    }

    public double getCostOilCum() {
        return getCostOil() + costOilBfw;
    }
}
