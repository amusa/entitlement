package com.cosm.psc.production.domain.model;

import java.util.List;

import com.cosm.common.domain.model.FiscalPeriod;
import com.cosm.common.domain.model.FiscalYear;
import com.cosm.common.domain.model.OilFieldId;
import com.cosm.common.domain.model.ProductionSharingContractId;

public interface ProductionRepository {	
	
	Production productionOfId(ProductionId productionId);
	
    Production productionOf(FiscalPeriod fiscalPeriod, ProductionSharingContractId pscId);

    List<Production> productionsOf(FiscalYear fiscalYear, ProductionSharingContractId pscId);
    
    public List<Production> productionsOf(FiscalYear fiscalYear, OilFieldId oilField);

    Production productionOf(FiscalPeriod fiscalPeriod, OilFieldId oilFieldId);
    
    double grossProductionOfPeriod(FiscalPeriod fiscalPeriod, ProductionSharingContractId pscId);

    double grossProductionOfFiscalYear(FiscalYear fiscalYear, ProductionSharingContractId pscId);

    double grossProductionToDate(FiscalPeriod fiscalPeriodr, ProductionSharingContractId pscId);
    
    boolean isFirstProductionOfYear(FiscalPeriod fiscalPeriod, ProductionSharingContractId pscId);

    boolean isFirstOmlProduction(FiscalYear fiscalYear, ProductionSharingContractId pscId);
    
    void store (Production production);
    
    void remove(Production production);
    
    void save(Production production);
    
    ProductionId nextProductionId();

}
