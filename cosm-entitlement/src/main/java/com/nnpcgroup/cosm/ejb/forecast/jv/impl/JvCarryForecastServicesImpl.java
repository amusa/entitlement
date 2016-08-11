/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.ejb.forecast.jv.impl;

import com.nnpcgroup.cosm.ejb.forecast.jv.JvCarryForecastServices;
import com.nnpcgroup.cosm.entity.Price;
import com.nnpcgroup.cosm.entity.PricePK;
import com.nnpcgroup.cosm.entity.forecast.jv.CarryForecast;
import com.nnpcgroup.cosm.exceptions.NoRealizablePriceException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.Dependent;

/**
 *
 * @author 18359
 */
@Dependent
public abstract class JvCarryForecastServicesImpl extends JvAlternativeFundingForecastServicesImpl<CarryForecast> implements JvCarryForecastServices {

    private static final Logger LOG = Logger.getLogger(JvCarryForecastServicesImpl.class.getName());

    public JvCarryForecastServicesImpl(Class<CarryForecast> entityClass) {
        super(entityClass);
    }

    @Override
    public CarryForecast computeNotionalMargin(CarryForecast forecast) throws NoRealizablePriceException {
        PricePK pricePK = new PricePK();
        LOG.log(Level.INFO, "*********NPE Check********* forecast={0}", new Object[]{forecast});
        pricePK.setPeriodMonth(forecast.getPeriodMonth());
        pricePK.setPeriodYear(forecast.getPeriodYear());

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

        forecast.setMargin(GNM);
        LOG.log(Level.INFO, "Guaranteed National Margin (GNM)=>{0}", GNM);

        return forecast;
    }

}
