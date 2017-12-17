/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosm.psc.infrastructure.persistence.jpa;

import com.cosm.common.infrastructure.persistence.jpa.JpaCommandRepository;
import com.cosm.psc.domain.model.cost.CostCommandRepository;
import com.cosm.psc.domain.model.cost.ProductionCost;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


/**
 *
 * @author amusa
 */
@ApplicationScoped
public class JpaCostCommandRepository extends JpaCommandRepository<ProductionCost> implements CostCommandRepository {

    @PersistenceContext(unitName = "CostPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public JpaCostCommandRepository() {
        super(ProductionCost.class);
    }



}