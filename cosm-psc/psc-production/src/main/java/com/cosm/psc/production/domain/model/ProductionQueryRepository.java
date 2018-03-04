package com.cosm.psc.production.domain.model;

import java.util.List;

import com.cosm.common.domain.model.OilFieldId;
import com.cosm.common.domain.model.ProductionSharingContractId;
import com.cosm.common.domain.repository.QueryRepository;


/**
 * Created by maliska on 12/16/17.
 */
public interface ProductionQueryRepository extends QueryRepository<Production> {

     Production findSingle(int year, int month, ProductionSharingContractId psc);

     List<Production> find(int year, ProductionSharingContractId psc);

     List<Production> find(int year, OilFieldId oilField);

     double getGrossProduction(ProductionSharingContractId psc, int year, int month);

     double getGrossProductionToDate(ProductionSharingContractId psc, int year, int month);

     boolean productionExits(ProductionSharingContractId psc, int year, int month);

     boolean isFirstProductionOfYear(ProductionSharingContractId psc, int year, int month);

     boolean isFirstOmlProduction(ProductionSharingContractId psc, int year, int month);

}
