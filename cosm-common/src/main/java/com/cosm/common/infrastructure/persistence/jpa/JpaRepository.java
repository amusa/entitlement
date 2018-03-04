/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosm.common.infrastructure.persistence.jpa;

import com.cosm.common.domain.repository.Repository;

import java.util.List;

import javax.persistence.EntityManager;
/**
 *
 * @author amusa
 * @param <T>
 */
public abstract class JpaRepository<T> implements Repository<T> {

    protected final Class<T> entityClass;

    public JpaRepository(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    protected abstract EntityManager entityManager();

	@Override
	public void add(T entity) {
		 entityManager().persist(entity);		
	}

	@Override
	public void save(T entity) {
		entityManager().merge(entity);		
	}

	@Override
	public void remove(T entity) {
		entityManager().remove(entity);		
	}
	
	@Override
    public T find(Object id) {
        return entityManager().find(entityClass, id);
    }

    @Override
    public List<T> findAll() {
        javax.persistence.criteria.CriteriaQuery cq = entityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        return entityManager().createQuery(cq).getResultList();
    }

    
    

}
