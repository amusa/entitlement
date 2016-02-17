/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.comd.cosm.entitlement.entity;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.MappedSuperclass;

/**
 *
 * @author 18359
 */
//@Entity
//@DiscriminatorValue("FORECAST")
//@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
//@DiscriminatorColumn(name = "FA_TYPE")
@MappedSuperclass
public abstract class ForecastProduction extends Production {

    private static final long serialVersionUID = 3407190935163393865L;
    
    public ForecastProduction() {
    }

}
