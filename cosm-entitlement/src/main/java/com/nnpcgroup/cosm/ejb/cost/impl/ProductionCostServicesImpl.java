/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.ejb.cost.impl;

import com.nnpcgroup.cosm.ejb.cost.ProductionCostServices;
import com.nnpcgroup.cosm.ejb.impl.CommonServicesImpl;
import com.nnpcgroup.cosm.entity.ProductionSharingContract;
import com.nnpcgroup.cosm.entity.cost.ProductionCost;
import java.io.Serializable;
import java.util.List;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 * @author 18359
 */
@Stateless
@Local(ProductionCostServices.class)
public class ProductionCostServicesImpl extends CommonServicesImpl<ProductionCost> implements ProductionCostServices, Serializable {

    public ProductionCostServicesImpl() {
        super(ProductionCost.class);
    }

    @Override
    public List<ProductionCost> find(ProductionSharingContract psc, Integer year, Integer month) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();

        List<ProductionCost> prodCosts;

        CriteriaQuery cq = cb.createQuery();
        Root e = cq.from(ProductionCost.class);
        try {
            cq.select(e).where(
                    cb.and(cb.equal(e.get("periodYear"), year),
                            cb.equal(e.get("periodMonth"), month),
                            cb.equal(e.get("psc"), psc)
                    ));

            Query query = getEntityManager().createQuery(cq);

            prodCosts = query.getResultList();
        } catch (NoResultException nre) {
            return null;
        }

        return prodCosts;
    }
    
    //TODO:refactor CommonServices interface

    @Override
    public ProductionCost getPreviousMonthProduction(ProductionCost production) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ProductionCost getNextMonthProduction(ProductionCost production) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ProductionCost enrich(ProductionCost production) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
