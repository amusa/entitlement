/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.comd.cosm.entitlement.ejb;

import com.nnpcgroup.comd.cosm.entitlement.entity.Production;
import java.util.List;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

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

    public List<Production> findByYearAndMonth(int year, int month) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();

// Query for a List of objects.
        CriteriaQuery cq = cb.createQuery();
        Root e = cq.from(Production.class);
        cq.where(
                cb.and(cb.equal(e.get("periodYear"), year),
                        cb.equal(e.get("periodMonth"), month)
                ));

        Query query = getEntityManager().createQuery(cq);
        List<Production> productions = query.getResultList();

        return productions;
    }

    @Override
    public Production enrich(Production production) {
        return computeEntitlement(
                computeOpeningStock(production)
        );
    }
}
