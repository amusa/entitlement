/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.ejb.forecast.psc;

import com.nnpcgroup.cosm.ejb.CommonServices;
import com.nnpcgroup.cosm.entity.ProductionSharingContract;
import com.nnpcgroup.cosm.entity.cost.ProductionCost;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author 18359
 */
public interface ProductionCostServices extends CommonServices<ProductionCost>, Serializable {

    List<ProductionCost> find(ProductionSharingContract psc, Integer year, Integer month);

}
