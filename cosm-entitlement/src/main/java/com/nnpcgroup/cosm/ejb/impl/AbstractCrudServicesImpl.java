/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.ejb.impl;

import com.nnpcgroup.cosm.ejb.AbstractCrudServices;
import java.util.List;
import javax.persistence.EntityManager;

/**
 *
 * @author 18359
 * @param <T>
 */
public abstract class AbstractCrudServicesImpl<T> implements AbstractCrudServices<T> {

    protected final Class<T> entityClass;

    public AbstractCrudServicesImpl(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    protected abstract EntityManager getEntityManager();

    @Override
    public void create(T entity) {
        getEntityManager().persist(entity);
    }

    @Override
    public void edit(T entity) {
        getEntityManager().merge(entity);
    }

    @Override
    public void remove(T entity) {
        getEntityManager().remove(getEntityManager().merge(entity));
    }

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

    @Override
    public List<T> findRange(int[] range) {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        q.setMaxResults(range[1] - range[0] + 1);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }

    @Override
    public int count() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        javax.persistence.criteria.Root<T> rt = cq.from(entityClass);
        cq.select(getEntityManager().getCriteriaBuilder().count(rt));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }

    @Override
    public void create(List<T> entityList) {
        entityList.stream().forEach((e) -> {
            getEntityManager().persist(e);
        });

    }

    @Override
    public void edit(List<T> entityList) {
        entityList.stream().forEach((e) -> {
            getEntityManager().merge(e);
        });

    }

    @Override
    public void flush() {
        getEntityManager().flush();
    }
    
    @Override
    public T merge (T t){
        return getEntityManager().merge(t);
    }

    @Override
    public void refresh(T t) {
        getEntityManager().refresh(t);
    }

    @Override
    public boolean isPersist(T t){
        return getEntityManager().contains(t);
    }
    
    

}
