/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.comd.cosm.entitlement.ejb;

import com.nnpcgroup.comd.cosm.entitlement.entity.ProductionPlanBAK;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author 18359
 */
@Stateless
public class StockForecastBean extends AbstractBean<ProductionPlanBAK> {

    @PersistenceContext(unitName = "entitlementPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public StockForecastBean() {
        super(ProductionPlanBAK.class);
    }

    public List<ProductionPlanBAK> getStockForecasts() {
        List<ProductionPlanBAK> stockForecasts = new ArrayList<>();

        ProductionPlanBAK st = new ProductionPlanBAK(new Long(1), 2015, 1, null, 134100.00, 1438107.00);
        stockForecasts.add(st);
        st = new ProductionPlanBAK(new Long(1), 2015, 1, null, 134100.00, 1438107.00);
        stockForecasts.add(st);
        st = new ProductionPlanBAK(new Long(2), 2015, 2, null, 205177.20, 205177.20);
        stockForecasts.add(st);
        st = new ProductionPlanBAK(new Long(3), 2015, 1, null, 9493.00, 110403.00);
        stockForecasts.add(st);
        st = new ProductionPlanBAK(new Long(4), 2015, 2, null, 609.60, 1070.80);
        stockForecasts.add(st);
        
        return stockForecasts;
    }

}
