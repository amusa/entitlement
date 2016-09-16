/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.ejb.production.jv;

import com.nnpcgroup.cosm.entity.contract.JvContract;
import com.nnpcgroup.cosm.entity.production.jv.JvProductionDetail;

/**
 *
 * @author 18359
 * @param <T>
 * @param <E>
 *
 */
public interface JvProductionDetailServices<T extends JvProductionDetail, E extends JvContract> extends ProductionDetailServices<T, E> {

}
