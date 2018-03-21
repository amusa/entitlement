/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosm.shipping.crude.infrastructure.persistence.jpa;

import com.cosm.common.infrastructure.persistence.jpa.JpaCommandRepository;
import com.cosm.shipping.crude.domain.model.CrudeCommandRepository;
import com.cosm.shipping.crude.domain.model.CrudeType;


import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;



/**
 *
 * @author amusa
 */
@ApplicationScoped
public class JpaCrudeCommandRepository extends JpaCommandRepository<CrudeType> implements CrudeCommandRepository {

    @PersistenceContext(unitName = "userPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public JpaCrudeCommandRepository() {
        super(CrudeType.class);
    }



}