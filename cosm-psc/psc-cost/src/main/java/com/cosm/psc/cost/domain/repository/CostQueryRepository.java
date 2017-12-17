package com.cosm.psc.cost.domain.repository;

import com.cosm.common.domain.repository.QueryRepository;
import com.cosm.common.domain.service.internal.FiscalPeriod;
import com.cosm.psc.cost.domain.model.CostItem;
import com.cosm.psc.cost.domain.model.ProductionCost;

import java.util.List;
import java.util.Map;


/**
 * Created by maliska on 12/16/17.
 */
public interface CostQueryRepository extends QueryRepository<ProductionCost> {


    List<ProductionCost> find(Long pscId, Long oilFieldId, Integer year, Integer month);

    List<ProductionCost> findOpex(Long pscId, Long oilFieldId, Integer year, Integer month);

    List<ProductionCost> findCapex(Long pscId, Long oilFieldId, Integer year, Integer month);

    double getCostToDate(Long pscId, Long oilFieldId, Integer year, Integer month);

    double getOpex(Long pscId, Long oilFieldId, Integer year, Integer month);

    double getCurrentYearOpex(Long pscId, Long oilFieldId, Integer year, Integer month);

    double getCapex(Long pscId, Long oilFieldId, Integer year, Integer month);

    double getCurrentYearCapex(Long pscId, Long oilFieldId, Integer year, Integer month);

    double getCapitalAllowanceRecovery(Long pscId, Long oilFieldId, Integer year, Integer month);

    Double getEducationTax(Long pscId, Long oilFieldId, Integer year, Integer month);

    boolean fiscalPeriodExists(Long pscId, Long oilFieldId, Integer year, Integer month);

    boolean fiscalPeriodExists(Long pscId, Long oilFieldId, FiscalPeriod fp);

    public Map<CostItem, Double> getProdCostItemCosts(Long pscId, Long oilFieldId, Integer year, Integer month);
}
