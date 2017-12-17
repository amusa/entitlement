/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosm.jv.account.domain.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 *
 * @author 18359
 */
@Entity
@DiscriminatorValue("MCA")
public class ModifiedCarryContract extends AlternativeFundingContract {

    private static final long serialVersionUID = -308376402305136004L;

    private Double taxRate;
    private Double royaltyRate;

    public ModifiedCarryContract() {
    }

    public ModifiedCarryContract(JointVenture jointVenture, CrudeType crudeType) {
        super(jointVenture, crudeType);
    }

    public Double getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(Double taxRate) {
        this.taxRate = taxRate;
    }

    public Double getRoyaltyRate() {
        return royaltyRate;
    }

    public void setRoyaltyRate(Double royaltyRate) {
        this.royaltyRate = royaltyRate;
    }

    @Override
    public String discriminatorValue() {
        DiscriminatorValue discriminatorValue = ModifiedCarryContract.class.getAnnotation(DiscriminatorValue.class);
        return discriminatorValue.value();
    }
}
