/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosm.psc.infrastructure.persistence.jpa;

import com.cosm.common.infrastructure.persistence.jpa.JpaQueryRepository;
import com.cosm.psc.domain.model.account.ProductionSharingContract;
import com.cosm.psc.domain.model.account.PscAccountQueryRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


/**
 *
 * @author amusa
 */
@ApplicationScoped
public class JpaPscAccountQueryRepository extends JpaQueryRepository<ProductionSharingContract> implements PscAccountQueryRepository {

    @PersistenceContext(unitName = "CostPU")
    private EntityManager em;

    public JpaPscAccountQueryRepository() {
        super(ProductionSharingContract.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }



}
