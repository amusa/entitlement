/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.ejb;

import com.nnpcgroup.cosm.ejb.impl.AbstractCrudServicesImpl;
import com.nnpcgroup.cosm.entity.FiscalArrangement;
import com.nnpcgroup.cosm.entity.JointVenture;
import com.nnpcgroup.cosm.entity.ProductionSharingContract;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author 18359
 */
@Stateless
public class FiscalArrangementBean extends AbstractCrudServicesImpl<FiscalArrangement> {

    @PersistenceContext(unitName = "entitlementPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public FiscalArrangementBean() {
        super(FiscalArrangement.class);
    }

    public List<ProductionSharingContract> findPscFiscalArrangements() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<ProductionSharingContract> cq = cb.createQuery(ProductionSharingContract.class);
        Root<ProductionSharingContract> pscRoot = cq.from(ProductionSharingContract.class);
        cq.select(pscRoot);
        TypedQuery<ProductionSharingContract> q = em.createQuery(cq);
        List<ProductionSharingContract> pscs = q.getResultList();
        return pscs;
    }

    public List<JointVenture> findJvFiscalArrangements() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<JointVenture> cq = cb.createQuery(JointVenture.class);
        Root<JointVenture> jvRoot = cq.from(JointVenture.class);
        cq.select(jvRoot);
        TypedQuery<JointVenture> q = em.createQuery(cq);
        List<JointVenture> jvs = q.getResultList();
        return jvs;
    }
}
