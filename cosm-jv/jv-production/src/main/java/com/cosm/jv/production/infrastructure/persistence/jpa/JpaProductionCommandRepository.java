/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosm.jv.production.infrastructure.persistence.jpa;

import com.cosm.common.infrastructure.persistence.jpa.JpaCommandRepository;
import com.cosm.jv.production.domain.model.Production;
import com.cosm.jv.production.domain.repository.ProductionCommandRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


/**
 *
 * @author amusa
 */
@ApplicationScoped
public class JpaProductionCommandRepository extends JpaCommandRepository<Production> implements ProductionCommandRepository {

    @PersistenceContext(unitName = "ProductionPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public JpaProductionCommandRepository() {
        super(Production.class);
    }



}