package com.cosm.psc.domain.model.cost;

import com.cosm.common.domain.repository.QueryRepository;
import com.cosm.common.domain.service.internal.FiscalPeriod;
import com.cosm.psc.domain.model.account.ProductionSharingContract;

import java.util.List;
import java.util.Map;


/**
 * Created by maliska on 12/16/17.
 */
public interface CostQueryRepository extends QueryRepository<ProductionCost> {


    List<ProductionCost> find(ProductionSharingContract psc, Integer year, Integer month);

    List<ProductionCost> findOpex(ProductionSharingContract psc, Integer year, Integer month);

    List<ProductionCost> findCapex(ProductionSharingContract psc, Integer year, Integer month);

    double getCostToDate(ProductionSharingContract psc, Integer year, Integer month);

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
