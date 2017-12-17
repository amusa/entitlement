package com.cosm.jv.production.domain.repository;

import com.cosm.common.domain.repository.QueryRepository;
import com.cosm.jv.production.domain.model.Production;

import java.util.List;


/**
 * Created by maliska on 12/16/17.
 */
public interface ProductionQueryRepository extends QueryRepository<Production> {

    public List<Production> findByYearAndMonth(int year, int month);


}
