/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.ejb;

import com.nnpcgroup.cosm.ejb.impl.AbstractCrudServicesImpl;
import com.nnpcgroup.cosm.entity.user.User;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author 18359
 */
@Stateless
public class UserBean extends AbstractCrudServicesImpl<User> {

    @PersistenceContext(unitName = "userPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UserBean() {
        super(User.class);
    }    

}
