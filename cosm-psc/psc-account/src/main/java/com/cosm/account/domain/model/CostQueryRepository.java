package com.cosm.account.domain.model;

import com.cosm.common.domain.model.FiscalPeriod;
import com.cosm.common.domain.model.ProductionSharingContractId;
import com.cosm.common.domain.repository.QueryRepository;


import java.util.List;
import java.util.Map;


/**
 * Created by maliska on 12/16/17.
 */
public interface CostQueryRepository extends QueryRepository<ProductionCost> {
       
    List<ProductionCost> find(ProductionSharingContractId pscId, Integer year, Integer month);

    List<ProductionCost> findOpex(ProductionSharingContractId pscId, Integer year, Integer month);

    List<ProductionCost> findCapex(ProductionSharingContractId pscId, Integer year, Integer month);

    double getCostToDate(ProductionSharingContractId pscId, Integer year, Integer month);

    double getOpex(ProductionSharingContractId pscId, Integer year, Integer month);

    double getCurrentYearOpex(ProductionSharingContractId pscId, Integer year, Integer month);

    double getCapex(ProductionSharingContractId pscId, Integer year, Integer month);

    double getCurrentYearCapex(ProductionSharingContractId pscId, Integer year, Integer month);

    double getCapitalAllowanceRecovery(ProductionSharingContractId pscId, Integer year, Integer month);

    Double getEducationTax(ProductionSharingContractId pscId, Integer year, Integer month);

    boolean fiscalPeriodExists(ProductionSharingContractId pscId, Integer year, Integer month);

    boolean fiscalPeriodExists(ProductionSharingContractId pscId, FiscalPeriod fp);

    public Map<CostItem, Double> getProdCostItemCosts(ProductionSharingContractId pscId, Integer year, Integer month);
}
