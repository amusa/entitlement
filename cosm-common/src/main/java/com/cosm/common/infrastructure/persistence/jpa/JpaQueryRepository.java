/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosm.common.infrastructure.persistence.jpa;

import com.cosm.common.domain.repository.QueryRepository;

import javax.persistence.EntityManager;
import java.util.List;

/**
 *
 * @author amusa
 * @param <T>
 */
public abstract class JpaQueryRepository<T> implements QueryRepository<T> {

    protected final Class<T> entityClass;

    public JpaQueryRepository(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    protected abstract EntityManager getEntityManager();

    @Override
    public T find(Object id) {
        return getEntityManager().find(entityClass, id);
    }

    @Override
    public List<T> findAll() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        return getEntityManager().createQuery(cq).getResultList();
    }

}
