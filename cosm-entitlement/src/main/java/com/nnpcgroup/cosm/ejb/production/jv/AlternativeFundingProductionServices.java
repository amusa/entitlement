/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.ejb.production.jv;

import com.nnpcgroup.cosm.entity.contract.AlternativeFundingContract;
import com.nnpcgroup.cosm.entity.production.jv.AlternativeFundingProduction;

/**
 *
 * @author 18359
 * @param <T>
 * @param <E>
 */
public interface AlternativeFundingProductionServices<T extends AlternativeFundingProduction, E extends AlternativeFundingContract> extends JvProductionServices<T, E> {

    public T computeCarryOil(T production);

    public T computeSharedOil(T production);

    public T computeGuaranteedNotionalMargin(T production);

    public T computeResidualCarryExpenditure(T production);

    public T computeCarryTaxRelief(T production);

    public T computeCarryTaxExpenditure(T production);

    public T computeCapitalCarryCostAmortized(T production);

    public T computeAlternativeFunding(T production);

    public Double computeCarryOilCum(E cs);

    public Double computeSharedOilCum(E cs);

    public Double computeResidualCarryExpenditureCum(E cs);

    public Double computeCarryTaxReliefCum(E cs);

    public Double computeCarryTaxExpenditureCum(E cs);

    public Double computeCapitalCarryCostAmortizedCum(E cs);

}
