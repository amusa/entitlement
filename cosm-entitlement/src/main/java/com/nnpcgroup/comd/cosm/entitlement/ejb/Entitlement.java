/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.comd.cosm.entitlement.ejb;

import com.nnpcgroup.comd.cosm.entitlement.entity.Production;

/**
 *
 * @author 18359
 */
interface Entitlement {
    
   public Double calculateEntitlement(); 
   public Production createProductionEntity();
   public Production createActualProductionEntity();

}
