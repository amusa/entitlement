/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.ejb.cost;

import com.nnpcgroup.cosm.ejb.CommonServices;
import com.nnpcgroup.cosm.entity.FiscalPeriod;
import com.nnpcgroup.cosm.entity.ProductionSharingContract;
import com.nnpcgroup.cosm.entity.cost.CostItem;
import com.nnpcgroup.cosm.entity.cost.ProductionCost;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author 18359
 */
public interface ProductionCostServices extends CommonServices<ProductionCost>, Serializable {

    List<ProductionCost> find(ProductionSharingContract psc, Integer year, Integer month);

    List<ProductionCost> findOpex(ProductionSharingContract psc, Integer year, Integer month);

    List<ProductionCost> findCapex(ProductionSharingContract psc, Integer year, Integer month);

    double getOpex(ProductionSharingContract psc, Integer year, Integer month);

    double getCurrentYearOpex(ProductionSharingContract psc, Integer year, Integer month);

    double getCapex(ProductionSharingContract psc, Integer year, Integer month);

    double getCurrentYearCapex(ProductionSharingContract psc, Integer year, Integer month);

    double getCapitalAllowanceRecovery(ProductionSharingContract psc, Integer year, Integer month);

    Double getEducationTax(ProductionSharingContract psc, Integer year, Integer month);

    boolean fiscalPeriodExists(ProductionSharingContract psc, Integer year, Integer month);

    boolean fiscalPeriodExists(ProductionSharingContract psc, FiscalPeriod fp);

    public Map<CostItem, Double> getProdCostItemCosts(ProductionSharingContract psc, Integer year, Integer month);
}
