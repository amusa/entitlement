/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosm.psc.entitlement.taxoil.application;

import java.io.Serializable;

import com.cosm.common.event.TaxOilReady;


/**
 * @author 18359
 */
public interface TaxOilService extends Serializable {
        
    void when(TaxOilReady event);
    
}