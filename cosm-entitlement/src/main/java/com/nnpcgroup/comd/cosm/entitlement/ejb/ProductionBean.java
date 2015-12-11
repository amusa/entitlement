/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.comd.cosm.entitlement.ejb;

import com.nnpcgroup.comd.cosm.entitlement.entity.Production;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author 18359
 */
public abstract class ProductionBean extends AbstractBean<Production> implements Entitlement {

    private static final Logger log = Logger.getLogger(ProductionBean.class.getName());

    @PersistenceContext(unitName = "entitlementPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        log.info("ProductionBean::setEntityManager() called...");
        return em;
    }

    public ProductionBean() {
        super(Production.class);
        log.info("ProductionBean::constructor  called...");
    }

//    public List<Production> getProductions() {
//        List<Production> productions = new ArrayList<>();
//
//        Production prod = new PlanProduction(2015, 1, null, 134100.00, 1438107.00);
//        productions.add(prod);
//        prod = new PlanProduction(2015, 1, null, 134100.00, 1438107.00);
//        productions.add(prod);
//        prod = new PlanProduction(2015, 2, null, 205177.20, 205177.20);
//        productions.add(prod);
//        prod = new PlanProduction(2015, 1, null, 9493.00, 110403.00);
//        productions.add(prod);
//        prod = new PlanProduction(2015, 2, null, 609.60, 1070.80);
//        productions.add(prod);
//        
//        return productions;
//    }
}
