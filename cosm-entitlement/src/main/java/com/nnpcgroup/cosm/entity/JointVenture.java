/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

/**
 *
 * @author 18359
 */
@Entity
@DiscriminatorValue("JV")
public class JointVenture extends FiscalArrangement {

    private static final long serialVersionUID = 7758288081166549749L;
    
    protected EquityType equityType;

    @OneToOne
    public EquityType getEquityType() {
        return equityType;
    }

    public void setEquityType(EquityType equityType) {
        this.equityType = equityType;
    }
    
     
    
}
