/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosm.common.infrastructure.persistence.jpa;

import com.cosm.common.domain.repository.CommandRepository;

import javax.persistence.EntityManager;
import java.util.List;

/**
 *
 * @author amusa
 * @param <T>
 */
public abstract class JpaCommandRepository<T> implements CommandRepository<T> {

    protected final Class<T> entityClass;

    public JpaCommandRepository(Class<T> entityClass) {
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
    public void remove(List<T> entityList) {
        entityList.stream().forEach((e) -> {
            getEntityManager().remove(e);
        });

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

}
