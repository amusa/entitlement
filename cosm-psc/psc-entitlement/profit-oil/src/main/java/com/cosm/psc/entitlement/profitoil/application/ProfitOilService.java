/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosm.psc.entitlement.profitoil.application;

import com.cosm.common.event.ProfitOilReady;

import java.io.Serializable;

/**
 * @author 18359
 */
public interface ProfitOilService extends Serializable {
        
    void when(ProfitOilReady event);

}
