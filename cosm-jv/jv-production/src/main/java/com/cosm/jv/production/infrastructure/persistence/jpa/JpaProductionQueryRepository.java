/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosm.jv.production.infrastructure.persistence.jpa;

import com.cosm.common.infrastructure.persistence.jpa.JpaQueryRepository;
import com.cosm.jv.production.domain.model.Production;
import com.cosm.jv.production.domain.repository.ProductionQueryRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author amusa
 */
@ApplicationScoped
public class JpaProductionQueryRepository extends JpaQueryRepository<Production> implements ProductionQueryRepository {
    private static final Logger LOG = Logger.getLogger(JpaProductionQueryRepository.class.getName());

    @PersistenceContext(unitName = "ProductionPU")
    private EntityManager em;

    public JpaProductionQueryRepository() {
        super(Production.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    @Override
    public List<Production> findByYearAndMonth(int year, int month) {
        LOG.log(Level.INFO, "Parameters: year={0}, month={1}", new Object[]{year, month});

        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();

        List<Production> productions;

        CriteriaQuery cq = cb.createQuery();
        Root e = cq.from(Production.class);
        try {
            cq.select(e).where(
                    cb.and(cb.equal(e.get("periodYear"), year),
                            cb.equal(e.get("periodMonth"), month)
                    ));

            Query query = getEntityManager().createQuery(cq);

            productions = query.getResultList();
        } catch (NoResultException nre) {
            return null;
        }

        return productions;
    }


}
