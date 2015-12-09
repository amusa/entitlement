/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.comd.cosm.entitlement.entity;

import javax.persistence.Entity;

/**
 *
 * @author 18359
 */
@Entity
public class PlanProduction extends Production {

    private static final long serialVersionUID = 5176195132775152290L;

    public PlanProduction() {
    }

    public PlanProduction(int periodYear, int periodMonth, ContractStream partnership, Double openingStock, Double productionVolume) {
        super(periodYear, periodMonth, partnership, openingStock, productionVolume);
    }
    
    
    
    
    
}
