/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosm.jv.production.infrastructure.persistence.jpa;

import com.cosm.common.infrastructure.persistence.jpa.JpaCommandRepository;
import com.cosm.jv.production.domain.model.Production;
import com.cosm.jv.production.domain.model.ProductionDetail;
import com.cosm.jv.production.domain.repository.ProductionCommandRepository;
import com.cosm.jv.production.domain.repository.ProductionDetailCommandRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


/**
 *
 * @author amusa
 */
@ApplicationScoped
public class JpaProductionDetailCommandRepository extends JpaCommandRepository<ProductionDetail> implements ProductionDetailCommandRepository {

    @PersistenceContext(unitName = "ProductionPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public JpaProductionDetailCommandRepository() {
        super(ProductionDetail.class);
    }



}