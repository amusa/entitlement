/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.controller;

import com.nnpcgroup.cosm.ejb.production.jv.JvProductionServices;
import com.nnpcgroup.cosm.entity.contract.Contract;
import com.nnpcgroup.cosm.entity.production.jv.Production;
import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Any;
import javax.enterprise.inject.Instance;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.Annotated;
import javax.enterprise.inject.spi.InjectionPoint;

/**
 *
 * @author 18359
 */
@Dependent
public class ProductionServiceFactory {
    
    @Produces
    @ProductionServiceProducer
    public JvProductionServices createProductionService(@Any Instance<JvProductionServices<? extends Production, ? extends Contract>> instance, InjectionPoint injectionPoint) {
        Annotated annotated = injectionPoint.getAnnotated();
        ProductionServiceType contractTypeAnnotation = annotated.getAnnotation(ProductionServiceType.class);
        Class<? extends JvProductionServices<? extends Production, ? extends Contract>> contractType = contractTypeAnnotation.value().getProductionServiceType();
        return instance.select(contractType).get();
    }
}
