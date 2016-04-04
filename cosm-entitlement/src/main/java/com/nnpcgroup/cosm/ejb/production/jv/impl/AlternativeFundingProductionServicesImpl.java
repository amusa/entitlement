/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.ejb.production.jv.impl;

import com.nnpcgroup.cosm.ejb.production.jv.AlternativeFundingProductionServices;
import java.util.logging.Logger;

/**
 *
 * @author 18359
 * @param <T>
 */
public abstract class AlternativeFundingProductionServicesImpl<T> extends JvProductionServicesImpl<T> implements AlternativeFundingProductionServices<T> {

    private static final Logger LOG = Logger.getLogger(AlternativeFundingProductionServicesImpl.class.getName());

    public AlternativeFundingProductionServicesImpl(Class<T> entityClass) {
        super(entityClass);
    }
    
    @Override
    public T createInstance() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public T computeEntitlement(T production) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public T computeClosingStock(T production) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public T computeAvailability(T production) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public T liftingChanged(T production) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public T grossProductionChanged(T production) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public T computeStockAdjustment(T production) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
}
