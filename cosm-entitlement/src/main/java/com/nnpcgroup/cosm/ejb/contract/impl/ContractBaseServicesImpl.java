/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.ejb.contract.impl;

import com.nnpcgroup.cosm.ejb.contract.ContractBaseServices;
import com.nnpcgroup.cosm.ejb.impl.AbstractCrudServicesImpl;
import com.nnpcgroup.cosm.entity.crude.CrudeType;
import com.nnpcgroup.cosm.entity.FiscalArrangement;
import com.nnpcgroup.cosm.entity.contract.Contract;
import com.nnpcgroup.cosm.entity.contract.ContractPK;
import com.nnpcgroup.cosm.util.COSMPersistence;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author 18359
 * @param <T>
 */
public abstract class ContractBaseServicesImpl<T extends Contract> extends AbstractCrudServicesImpl<T> implements ContractBaseServices<T> {

    private static final Logger LOG = Logger.getLogger(ContractBaseServicesImpl.class.getName());

//    @PersistenceContext(unitName = "entitlementPU")
//    private EntityManager em;

    @Inject
    @COSMPersistence
    private EntityManager em;

    public ContractBaseServicesImpl(Class<T> entityClass) {
        super(entityClass);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    @Override
    public List<T> findFiscalArrangementContracts(FiscalArrangement fa) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();

        List<T> contracts;

        CriteriaQuery cq = cb.createQuery();
        Root<T> e = cq.from(entityClass);
        try {
            cq.select(e).
                    where(
                            cb.equal(e.get("fiscalArrangement"), fa)
                    );

            Query query = getEntityManager().createQuery(cq);

            contracts = query.getResultList();
        } catch (NoResultException nre) {
            return null;
        }

        return contracts;
    }

    @Override
    public T find(Object id) {
        if(id instanceof Contract){
            Contract contract = (Contract)id;
            ContractPK cPK = contract.getContractPK();
            return super.find(cPK);
        }
        return super.find(id); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Long findContractCount(FiscalArrangement fa, CrudeType ct) {

        TypedQuery<Long> query = getEntityManager().createQuery(
                "SELECT COUNT(c) "
                        + "FROM Contract c  WHERE c.fiscalArrangement = :fiscalArrangement AND c.crudeType = :crudeType", Long.class);
        query.setParameter("fiscalArrangement", fa);
        query.setParameter("crudeType", ct);

        long count = query.getSingleResult();

        return count;
    }
}
