package com.cosm.psc.production.domain.repository;

import com.cosm.common.domain.repository.QueryRepository;
import com.cosm.psc.production.domain.model.Production;

import java.util.List;


/**
 * Created by maliska on 12/16/17.
 */
public interface ProductionQueryRepository extends QueryRepository<Production> {

     Production findSingle(int year, int month, int pscId, int oilFieldId);

     List<Production> find(int year, int pscId);

     List<Production> find(int year, int pscId, int oilFieldId);

     double getGrossProduction(int pscId, int oilFieldId, int year, int month);

     double getGrossProductionToDate(int pscId, int oilFieldId, int year, int month);

     boolean productionExits(int pscId, int oilFieldId, int year, int month);

     boolean isFirstProductionOfYear(int pscId, int oilFieldId, int year, int month);

     boolean isFirstOmlProduction(int pscId, int oilFieldId, int year, int month);

}
