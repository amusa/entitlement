/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosm.user.infrastructure.persistence.jpa;

import com.cosm.common.infrastructure.persistence.jpa.JpaCommandRepository;
import com.cosm.user.domain.repository.UserCommandRepository;
import com.cosm.user.domain.model.User;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Root;


/**
 *
 * @author amusa
 */
@ApplicationScoped
public class JpaUserCommandRepository extends JpaCommandRepository<User> implements UserCommandRepository {

    @PersistenceContext(unitName = "userPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public JpaUserCommandRepository() {
        super(User.class);
    }

    @Override
    public void updatePassword(String userName, String newPassword) {
        CriteriaBuilder cb = this.em.getCriteriaBuilder();

        // create update
        CriteriaUpdate<User> update = cb.
                createCriteriaUpdate(User.class);

        // set the root class
        Root e = update.from(User.class);

        // set update and where clause
        update.set("passwd", newPassword);
        update.where(cb.equal(e.get("userName"),
                userName));

        // perform update
        this.em.createQuery(update).executeUpdate();
    }

}