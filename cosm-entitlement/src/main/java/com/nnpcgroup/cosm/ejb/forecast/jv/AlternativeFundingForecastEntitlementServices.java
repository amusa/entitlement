/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.ejb.forecast.jv;

import com.nnpcgroup.cosm.entity.contract.AlternativeFundingContract;
import com.nnpcgroup.cosm.entity.contract.Contract;
import com.nnpcgroup.cosm.entity.forecast.jv.AlternativeFundingForecastEntitlement;
import com.nnpcgroup.cosm.exceptions.NoRealizablePriceException;

/**
 *
 * @author 18359
 * @param <T>
 */
public interface AlternativeFundingForecastEntitlementServices<T extends AlternativeFundingForecastEntitlement> extends JvForecastEntitlementServices<T> {

    public T computeCarryOil(T forecast);

    public T computeSharedOil(T forecast);
    
    public T computeResidualCarryExpenditure(T forecast);
    
    public T computeNotionalMargin(T forecast) throws NoRealizablePriceException;

    public T computeCarryTaxRelief(T forecast);

    public T computeCarryTaxExpenditure(T forecast);

    public T computeCapitalCarryCostAmortized(T forecast);

    public T computeAlternativeFunding(T production) throws NoRealizablePriceException;

    public Double computeCarryOilCum(Contract cs);

    public Double computeSharedOilCum(Contract cs);

    public Double computeResidualCarryExpenditureCum(Contract cs);

    public Double computeCarryTaxReliefCum(Contract cs);

    public Double computeCarryTaxExpenditureCum(Contract cs);

    public Double computeCapitalCarryCostAmortizedCum(Contract cs);
    
    public Long getSharedOilPeriod(AlternativeFundingContract contract);

}
