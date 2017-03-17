/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.entity.cost;

import java.io.Serializable;

/**
 *
 * @author Ayemi
 */
public class CostOilDetail implements Serializable {

    private Double armotizedCapex;
    private Double opex;
    private Double educationTax;
    private Double costOilBfw;

    public Double getArmotizedCapex() {
        return armotizedCapex;
    }

    public void setArmotizedCapex(Double armotizedCapex) {
        this.armotizedCapex = armotizedCapex;
    }

    public Double getOpex() {
        return opex;
    }

    public void setOpex(Double opex) {
        this.opex = opex;
    }

    public Double getEducationTax() {
        return educationTax;
    }

    public void setEducationTax(Double educationTax) {
        this.educationTax = educationTax;
    }

    public Double getCostOilBfw() {
        return costOilBfw;
    }

    public void setCostOilBfw(Double costOilBfw) {
        this.costOilBfw = costOilBfw;
    }

    public Double getCostOil() {
        return armotizedCapex + opex + educationTax;
    }

    public Double getCostOilCum() {
        return getCostOil() + costOilBfw;
    }
}
