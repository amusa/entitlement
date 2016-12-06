/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.ejb.forecast.jv.impl;

import com.nnpcgroup.cosm.ejb.forecast.jv.CarryForecastEntitlementServices;
import com.nnpcgroup.cosm.entity.Price;
import com.nnpcgroup.cosm.entity.PricePK;
import com.nnpcgroup.cosm.entity.contract.AlternativeFundingContract;
import com.nnpcgroup.cosm.entity.contract.Contract;
import com.nnpcgroup.cosm.entity.forecast.jv.CarryForecastEntitlement;
import com.nnpcgroup.cosm.exceptions.NoRealizablePriceException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.TypedQuery;

/**
 *
 * @author 18359
 */
public abstract class CarryForecastEntitlementServicesImpl extends AlternativeFundingForecastEntitlementServicesImpl<CarryForecastEntitlement> implements CarryForecastEntitlementServices {

    private static final Logger LOG = Logger.getLogger(CarryForecastEntitlementServicesImpl.class.getName());

    public CarryForecastEntitlementServicesImpl(Class<CarryForecastEntitlement> entityClass) {
        super(entityClass);
    }

    @Override
    public CarryForecastEntitlement computeNotionalMargin(CarryForecastEntitlement entitlement) throws NoRealizablePriceException {
        PricePK pricePK = new PricePK();
        pricePK.setPeriodMonth(entitlement.getPeriodMonth());
        pricePK.setPeriodYear(entitlement.getPeriodYear());

        Double GNM = null;// = 4.1465; //TODO:temporary placeholder

        Price price = priceBean.find(pricePK);
        if (price != null) {
            LOG.log(Level.INFO, "Realizable Price found {0}", price.getRealizablePrice());
            GNM = price.getRealizablePrice() * 0.12225;
        } else {
            LOG.log(Level.INFO, "Realizable Price NOT found {0}");

            throw new NoRealizablePriceException(String.format("Realizable Price for the year %s and month %s not found!",
                    new Object[]{pricePK.getPeriodYear(), pricePK.getPeriodMonth()}
            ));
        }

        entitlement.setMargin(GNM);
        LOG.log(Level.INFO, "Increamental Notional Margin (GNM)=>{0}", GNM);

        return entitlement;
    }

}
