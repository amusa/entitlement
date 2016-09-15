/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.ejb.production.jv;

import com.nnpcgroup.cosm.ejb.*;
import com.nnpcgroup.cosm.entity.production.jv.Production;
import java.io.Serializable;

/**
 *
 * @author 18359
 * @param <T>
 */
public interface ProductionServices<T extends Production> extends AbstractCrudServices<T>, Serializable {

    
}
