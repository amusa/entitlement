package com.cosm.jv.production.domain.repository;

import com.cosm.common.domain.repository.QueryRepository;
import com.cosm.jv.production.domain.model.ProductionEntitlement;


/**
 * Created by maliska on 12/16/17.
 */
public interface EntitlementQueryRepository extends QueryRepository<ProductionEntitlement> {

    ProductionEntitlement getPreviousMonthProduction(ProductionEntitlement entitlement);

    ProductionEntitlement getNextMonthProduction(ProductionEntitlement entitlement);

}
