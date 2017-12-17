package com.cosm.jv.production.domain.repository;

import com.cosm.common.domain.repository.QueryRepository;
import com.cosm.jv.production.domain.model.ProductionDetail;
import com.cosm.jv.production.domain.model.ProductionEntitlement;

import java.util.List;


/**
 * Created by maliska on 12/16/17.
 */
public interface ProductionDetailQueryRepository extends QueryRepository<ProductionDetail> {

    ProductionDetail getPreviousMonthProduction(ProductionDetail productionDetail);
    ProductionDetail getNextMonthProduction(ProductionDetail productionDetail);

}
