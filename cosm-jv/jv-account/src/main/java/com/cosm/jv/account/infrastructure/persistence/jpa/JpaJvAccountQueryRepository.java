/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosm.jv.account.infrastructure.persistence.jpa;

import com.cosm.common.infrastructure.persistence.jpa.JpaQueryRepository;
import com.cosm.jv.account.domain.model.JointVenture;
import com.cosm.jv.account.domain.repository.JvAccountQueryRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


/**
 *
 * @author amusa
 */
@ApplicationScoped
public class JpaJvAccountQueryRepository extends JpaQueryRepository<JointVenture> implements JvAccountQueryRepository {

    @PersistenceContext(unitName = "CostPU")
    private EntityManager em;

    public JpaJvAccountQueryRepository() {
        super(JointVenture.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }



}
