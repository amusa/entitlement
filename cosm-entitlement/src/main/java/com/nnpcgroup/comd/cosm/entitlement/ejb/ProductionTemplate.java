/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.comd.cosm.entitlement.ejb;

import com.nnpcgroup.comd.cosm.entitlement.entity.JvProduction;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author 18359
 * @param <T>
 */
public abstract class ProductionTemplate<T> extends AbstractBean<T> {

    private static final Logger log = Logger.getLogger(ProductionTemplate.class.getName());

    @PersistenceContext(unitName = "entitlementPU")
    private EntityManager em;

    public ProductionTemplate(Class<T> entityClass) {
        super(entityClass);
    }

    @Override
    protected EntityManager getEntityManager() {
        log.info("ProductionBean::setEntityManager() called...");
        return em;
    }
    public abstract List<T> findByYearAndMonth(int year, int month) ;

    public abstract T computeEntitlement(T production);

    public abstract T createInstance();

   // public abstract T enrich(T production);

    public abstract T computeOpeningStock(T production);

    public abstract T computeClosingStock(T production);

//    public ProductionBean() {
//        super(Production.class);
//        log.info("ProductionBean::constructor  called...");
//    }
    // public abstract List<? super Production> findByYearAndMonth(int year, int month);
    //public abstract Production findByContractStreamPeriod(int year, int month, ContractStream cs);
    //@Override
    
    public T enrich(T production) {
        log.log(Level.INFO, "Enriching production {0}...", production);
        return computeEntitlement(
                computeOpeningStock(production)
        );
    }
}
