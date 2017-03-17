/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.ejb.forecast.jv.impl;

import com.nnpcgroup.cosm.ejb.forecast.jv.ModifiedCarryForecastEntitlementServices;
import com.nnpcgroup.cosm.entity.Price;
import com.nnpcgroup.cosm.entity.PricePK;
import com.nnpcgroup.cosm.entity.contract.AlternativeFundingContract;
import com.nnpcgroup.cosm.entity.contract.CarryContract;
import com.nnpcgroup.cosm.entity.contract.Contract;
import com.nnpcgroup.cosm.entity.contract.ModifiedCarryContract;
import com.nnpcgroup.cosm.entity.forecast.jv.ModifiedCarryForecastEntitlement;
import com.nnpcgroup.cosm.exceptions.NoRealizablePriceException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.TypedQuery;

/**
 *
 * @author 18359
 */
public class ModifiedCarryForecastEntitlementServicesImpl extends AlternativeFundingForecastEntitlementServicesImpl<ModifiedCarryForecastEntitlement> implements ModifiedCarryForecastEntitlementServices {

    private static final Logger LOG = Logger.getLogger(ModifiedCarryForecastEntitlementServicesImpl.class.getName());

    public ModifiedCarryForecastEntitlementServicesImpl(Class<ModifiedCarryForecastEntitlement> entityClass) {
        super(entityClass);
    }

    @Override
    public ModifiedCarryForecastEntitlement computeNotionalMargin(ModifiedCarryForecastEntitlement entitlement) throws NoRealizablePriceException {
        PricePK pricePK = new PricePK();
        LOG.log(Level.INFO, "*********NPE Check********* forecast={0}", new Object[]{entitlement});
        pricePK.setPeriodMonth(entitlement.getPeriodMonth());
        pricePK.setPeriodYear(entitlement.getPeriodYear());

        Double INM = null;

        Price price = priceBean.find(pricePK);

        Contract contract = entitlement.getContract();
        assert (contract instanceof ModifiedCarryContract);
        ModifiedCarryContract mcContract = (ModifiedCarryContract) contract;

        Double taxRate = mcContract.getTaxRate();
        Double royaltyRate = mcContract.getRoyaltyRate();

        if (price != null) {
            LOG.log(Level.INFO, "Realizable Price found {0}", price.getRealizablePrice());
            INM = ((1 - royaltyRate) * (1 - taxRate)) / price.getRealizablePrice();
        } else {
            LOG.log(Level.INFO, "Realizable Price NOT found {0}");

            throw new NoRealizablePriceException(String.format("Realizable Price for the year %s and month %s not found!",
                    new Object[]{pricePK.getPeriodYear(), pricePK.getPeriodMonth()}
            ));
        }

        entitlement.setMargin(INM);
        LOG.log(Level.INFO, "Increamental National Margin (GNM)=>{0}", INM);

        return entitlement;
    }

}
