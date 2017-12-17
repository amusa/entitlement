/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosm.jv.account.domain.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

/**
 *
 * @author 18359
 */
@Entity
@DiscriminatorValue("AF")
public abstract class AlternativeFundingContract extends Contract {

    private static final long serialVersionUID = 8684470740659960243L;

    private RegularContract regularContract;
    private Double sharedOilRatio;
    private Double terminalPeriod;
    private Double terminalSharedOil;

    public AlternativeFundingContract() {
    }

    public AlternativeFundingContract(JointVenture jointVenture, CrudeType crudeType) {
        super(jointVenture, crudeType);
    }

    public Double getSharedOilRatio() {
        return sharedOilRatio;
    }

    public void setSharedOilRatio(Double sharedOilRatio) {
        this.sharedOilRatio = sharedOilRatio;
    }

    public Double getTerminalPeriod() {
        return terminalPeriod;
    }

    public void setTerminalPeriod(Double terminalPeriod) {
        this.terminalPeriod = terminalPeriod;
    }

    public Double getTerminalSharedOil() {
        return terminalSharedOil;
    }

    public void setTerminalSharedOil(Double terminalSharedOil) {
        this.terminalSharedOil = terminalSharedOil;
    }

    @ManyToOne
    public RegularContract getRegularContract() {
        return regularContract;
    }

    public void setRegularContract(RegularContract regularContract) {
        this.regularContract = regularContract;
    }

}
