/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 *
 * @author 18359
 */
@Entity
@DiscriminatorValue("AF")
public abstract class AlternativeFundingContract extends Contract{
    
    private static final long serialVersionUID = 8684470740659960243L;
    
    private Double sharedOilRatio;

    public Double getSharedOilRatio() {
        return sharedOilRatio;
    }

    public void setSharedOilRatio(Double sharedOilRatio) {
        this.sharedOilRatio = sharedOilRatio;
    }
    
    
    
}
