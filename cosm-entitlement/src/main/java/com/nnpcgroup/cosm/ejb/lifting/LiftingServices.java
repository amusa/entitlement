/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.ejb.lifting;

import com.nnpcgroup.cosm.ejb.AbstractCrudServices;
import com.nnpcgroup.cosm.entity.lifting.Lifting;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 *
 * @author 18359
 * @param <T>
 */
public interface LiftingServices<T extends Lifting> extends AbstractCrudServices<T>, Serializable {

    List<T> find(Date from, Date to);
    
}
