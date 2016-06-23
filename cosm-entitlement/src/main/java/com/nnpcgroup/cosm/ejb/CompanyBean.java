/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.ejb;

import com.nnpcgroup.cosm.ejb.impl.AbstractCrudServicesImpl;
import com.nnpcgroup.cosm.entity.Company;
import javax.ejb.Stateless;
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
@Stateless
public class CompanyBean extends AbstractCrudServicesImpl<Company> {

    @PersistenceContext(unitName = "entitlementPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CompanyBean() {
        super(Company.class);
    }

    public Company findByCompanyName(String compName) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();

        Company company;

        CriteriaQuery cq = cb.createQuery();
        Root e = cq.from(entityClass);
        try {
            cq.where(
                    cb.equal(e.get("name"), compName)
            );

            Query query = getEntityManager().createQuery(cq);

            company = (Company) query.getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }

        return company;
    }

}
