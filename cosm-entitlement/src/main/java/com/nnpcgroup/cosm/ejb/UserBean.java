/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.ejb;

import com.nnpcgroup.cosm.ejb.impl.AbstractCrudServicesImpl;
import com.nnpcgroup.cosm.entity.user.User;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Root;

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

    public boolean authenticate(String userName, String password) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();

        CriteriaQuery<User> cq = cb.createQuery(User.class);
        Root<User> e = cq.from(entityClass);
        try {
            cq.select(e).where(
                    cb.and(cb.equal(e.get("userName"), userName),
                            cb.equal(e.get("passwd"), password)
                    ));
            Query query = getEntityManager().createQuery(cq);
            User user = (User)query.getSingleResult();
            return query.getMaxResults() > 0;
        } catch (NoResultException nre) {
            return false;
        }
      
    }
}
