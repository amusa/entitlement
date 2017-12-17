/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosm.user.infrastructure.persistence.jpa;

import com.cosm.common.infrastructure.persistence.jpa.JpaQueryRepository;
import com.cosm.user.domain.repository.UserQueryRepository;
import com.cosm.user.domain.model.User;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;


/**
 *
 * @author amusa
 */
@ApplicationScoped
public class JpaUserQueryRepository extends JpaQueryRepository<User> implements UserQueryRepository {

    @PersistenceContext(unitName = "userPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public JpaUserQueryRepository() {
        super(User.class);
    }

    @Override
    public boolean authenticate(String userName, String password) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();

        CriteriaQuery<User> cq = cb.createQuery(User.class);
        Root<User> e = cq.from(entityClass);
        try {
            cq.select(e).where(
                    cb.and(cb.equal(e.get("userName"), userName),
                            cb.equal(e.get("passwd"), password)
                    ));
            Query query = getEntityManager().createQuery(cq);

            return query.getMaxResults() > 0;
        } catch (NoResultException nre) {
            return false;
        }
    }

}
