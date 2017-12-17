/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosm.psc.lifting.infrastructure.persistence.jpa;

import com.cosm.common.infrastructure.persistence.jpa.JpaCommandRepository;
import com.cosm.psc.lifting.domain.model.Lifting;
import com.cosm.psc.lifting.domain.repository.LiftingCommandRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


/**
 *
 * @author amusa
 */
@ApplicationScoped
public class JpaLiftingCommandRepository extends JpaCommandRepository<Lifting> implements LiftingCommandRepository {

    @PersistenceContext(unitName = "LiftingPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public JpaLiftingCommandRepository() {
        super(Lifting.class);
    }



}