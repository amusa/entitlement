package com.cosm.psc.domain.model.production;

import com.cosm.common.domain.repository.QueryRepository;
import com.cosm.psc.domain.model.account.OilField;
import com.cosm.psc.domain.model.account.ProductionSharingContract;

import java.util.List;


/**
 * Created by maliska on 12/16/17.
 */
public interface ProductionQueryRepository extends QueryRepository<Production> {

     Production findSingle(int year, int month, ProductionSharingContract psc);

     List<Production> find(int year, ProductionSharingContract psc);

     List<Production> find(int year, OilField oilField);

     double getGrossProduction(ProductionSharingContract psc, int year, int month);

     double getGrossProductionToDate(ProductionSharingContract psc, int year, int month);

     boolean productionExits(ProductionSharingContract psc, int year, int month);

     boolean isFirstProductionOfYear(ProductionSharingContract psc, int year, int month);

     boolean isFirstOmlProduction(ProductionSharingContract psc, int year, int month);

}
