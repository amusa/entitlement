/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.comd.cosm.entitlement.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 *
 * @author 18359
 */
@Entity
@DiscriminatorValue("ACTUAL_PSC")
public class ActualPscProduction extends JvProduction {

    private static final long serialVersionUID = 1116299259496444592L;
    
        
    public ActualPscProduction() {
    }
        
  

}
