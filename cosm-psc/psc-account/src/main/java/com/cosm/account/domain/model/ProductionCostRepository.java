package com.cosm.account.domain.model;

import java.util.List;
import java.util.Map;

import com.cosm.common.domain.model.FiscalPeriod;
import com.cosm.common.domain.model.ProductionSharingContractId;
import com.cosm.common.domain.repository.Repository;


/**
 * Created by maliska on 21/02/18.
 */
public interface ProductionCostRepository extends Repository<ProductionCost> {


    List<ProductionCost> costItemsOfPeriod(FiscalPeriod fiscalPeriod, ProductionSharingContractId pscId);

    List<ProductionCost> opexCostItemsOfPeriod(FiscalPeriod fiscalPeriod, ProductionSharingContractId pscId);

    List<ProductionCost> capexCostItemsOfPeriod(FiscalPeriod fiscalPeriod, ProductionSharingContractId pscId);

    double costToDate(FiscalPeriod fiscalPeriod, ProductionSharingContractId pscId);

    double opexOfPeriod(FiscalPeriod fiscalPeriod, ProductionSharingContractId pscId) ;

    double opexOfYearToMonth(FiscalPeriod fiscalPeriod, ProductionSharingContractId pscId);

    double capexOfPeriod(FiscalPeriod fiscalPeriod, ProductionSharingContractId pscId);

    double capexOfYearToMonth(FiscalPeriod fiscalPeriod, ProductionSharingContractId pscId);

    double capitalAllowanceRecovery(FiscalPeriod fiscalPeriod, ProductionSharingContractId pscId);

    Double educationTaxOfCost(FiscalPeriod fiscalPeriod, ProductionSharingContractId pscId);

    Map<CostItem, Double> currentYearCostItems(FiscalPeriod fiscalPeriod, ProductionSharingContractId pscId);
    
    ProductionCostId nextProductionCostId();
}
