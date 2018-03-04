/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosm.psc.entitlement.royalty.domain.model;

import java.io.Serializable;

import com.cosm.common.event.RoyaltyReady;

/**
 * @author 18359
 */
public interface RoyaltyService extends Serializable {
				
	public void when(RoyaltyReady event);
	
}
