/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.comd.cosm.entitlement.ejb;

import com.nnpcgroup.comd.cosm.entitlement.entity.JvActualProduction;

/**
 *
 * @author 18359

 */
public interface JvActualProductionServices extends JvProductionServices<JvActualProduction> {
    public JvActualProduction liftingChanged(JvActualProduction production);
    public JvActualProduction grossProductionChanged(JvActualProduction production);
    
}
