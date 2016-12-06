/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.entity.contract;

import com.nnpcgroup.cosm.entity.crude.CrudeType;
import com.nnpcgroup.cosm.entity.FiscalArrangement;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

/**
 *
 * @author 18359
 */
@Entity
@DiscriminatorValue("AF")
public abstract class AlternativeFundingContract extends JvContract {

    private static final long serialVersionUID = 8684470740659960243L;

    private JvContract jvContract;
    private Double sharedOilRatio;
    private Double terminalPeriod;
    private Double terminalSharedOil;

    public AlternativeFundingContract() {
    }

    public AlternativeFundingContract(FiscalArrangement fiscalArrangement, CrudeType crudeType) {
        super(fiscalArrangement, crudeType);
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
    public JvContract getJvContract() {
        return jvContract;
    }

    public void setJvContract(JvContract jvContract) {
        this.jvContract = jvContract;
    }

}
