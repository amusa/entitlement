/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.ejb.contract.impl;

import com.nnpcgroup.cosm.ejb.contract.ContractServices;
import com.nnpcgroup.cosm.ejb.impl.AbstractCrudServicesImpl;
import com.nnpcgroup.cosm.entity.Contract;
import com.nnpcgroup.cosm.entity.FiscalArrangement;
import java.util.List;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author 18359
 */

public class ContractServicesImpl extends AbstractCrudServicesImpl<Contract>  implements ContractServices {

    private static final Logger log = Logger.getLogger(ContractServicesImpl.class.getName());

    @PersistenceContext(unitName = "entitlementPU")
    private EntityManager em;

    public ContractServicesImpl(Class<Contract> entityClass) {
        super(entityClass);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    

    @Override
    public List<Contract> findFiscalArrangementContracts(FiscalArrangement fa) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();

        List<Contract> contracts;

        CriteriaQuery cq = cb.createQuery();
        Root e = cq.from(entityClass);
        try {
            cq.where(
                    cb.equal(e.get("fiscalArrangement"), fa)
            );

            Query query = getEntityManager().createQuery(cq);

            contracts = query.getResultList();
        } catch (NoResultException nre) {
            return null;
        }

        return contracts;
    }

    
}
