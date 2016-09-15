/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.ejb.production.jv;

import com.nnpcgroup.cosm.ejb.*;
import com.nnpcgroup.cosm.entity.contract.Contract;
import com.nnpcgroup.cosm.entity.production.jv.ProductionDetail;
import java.io.Serializable;

/**
 *
 * @author 18359
 * @param <T>
 * @param <E>
 */
public interface ProductionDetailServices<T extends ProductionDetail, E extends Contract> extends CommonServices<T>, Serializable {

    public T grossProductionChanged(T production);

    public T liftingChanged(T production);
       
    public T computeOverlift(T production);
    
    public T computeOperatorDeclaredEquity(T production);
}
