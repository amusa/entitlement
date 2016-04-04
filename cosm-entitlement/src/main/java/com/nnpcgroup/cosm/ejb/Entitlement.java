/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.ejb;

import javax.enterprise.context.Dependent;

/**
 *
 * @author 18359
 * @param <T>
 */
@Dependent
public interface Entitlement<T> {
    
   public T computeEntitlement(T production);     
   //public T createInstance();
   public T enrich(T production);
   public T computeOpeningStock(T production);
   public T computeClosingStock(T production);

}
