/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosm.account.infrastructure.persistence.jpa;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.cosm.account.domain.model.CostItem;
import com.cosm.account.domain.model.CostItemRepository;
import com.cosm.common.infrastructure.persistence.jpa.JpaRepository;

/**
 * @author 18359
 */
@ApplicationScoped
public class JpaCostItemRepository extends JpaRepository<CostItem> implements CostItemRepository {

    @PersistenceContext   
    private EntityManager em;

    public JpaCostItemRepository() {
        super(CostItem.class);
    }

    public JpaCostItemRepository(Class<CostItem> entityClass) {
        super(entityClass);
    }

    @Override
    protected EntityManager entityManager() {
        return em;
    }

}
