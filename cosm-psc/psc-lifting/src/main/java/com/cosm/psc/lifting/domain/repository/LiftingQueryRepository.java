package com.cosm.psc.lifting.domain.repository;

import com.cosm.common.domain.repository.QueryRepository;
import com.cosm.psc.lifting.domain.model.Lifting;

import java.util.Date;
import java.util.List;

/**
 * Created by maliska on 12/16/17.
 */
public interface LiftingQueryRepository extends QueryRepository<Lifting> {

    List<Lifting> find(Date from, Date to);

    List<Lifting> find(Long pscId, Long oilFieldId, Date from, Date to);

    List<Lifting> find(Long pscId, Long oilFieldId, int year, int month);

    double computeWeightedAvePrice(Long pscId, Long oilFieldId, int year, int month);

    double getCorporationProceed(Long pscId, Long oilFieldId, int year, int month);

    double getContractorProceed(Long pscId, Long oilFieldId, int year, int month);

    double getMonthlyIncome(Long pscId, Long oilFieldId, int year, int month);

    double getProceedToDate(Long pscId, Long oilFieldId, int year, int month);

    double getGrossIncome(Long pscId, Long oilFieldId, int year, int month);

    double getCashPayment(Long pscId, Long oilFieldId, int year, int month);
}
