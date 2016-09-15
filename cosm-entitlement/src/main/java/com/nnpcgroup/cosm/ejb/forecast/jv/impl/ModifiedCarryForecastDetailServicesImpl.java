/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.ejb.forecast.jv.impl;

import com.nnpcgroup.cosm.ejb.forecast.jv.ModifiedCarryForecastDetailServices;
import com.nnpcgroup.cosm.entity.Price;
import com.nnpcgroup.cosm.entity.PricePK;
import com.nnpcgroup.cosm.entity.contract.Contract;
import com.nnpcgroup.cosm.entity.contract.ModifiedCarryContract;
import com.nnpcgroup.cosm.entity.forecast.jv.ModifiedCarryForecastDetail;
import com.nnpcgroup.cosm.exceptions.NoRealizablePriceException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.Dependent;

/**
 *
 * @author 18359
 */
@Dependent
public class ModifiedCarryForecastDetailServicesImpl extends AlternativeFundingForecastDetailServicesImpl<ModifiedCarryForecastDetail> implements ModifiedCarryForecastDetailServices {

    private static final Logger LOG = Logger.getLogger(ModifiedCarryForecastDetailServicesImpl.class.getName());

    public ModifiedCarryForecastDetailServicesImpl(Class<ModifiedCarryForecastDetail> entityClass) {
        super(entityClass);
    }

    //@Override
    public ModifiedCarryForecastDetail createInstance() {
        return new ModifiedCarryForecastDetail();
    }

    @Override
    public ModifiedCarryForecastDetail computeNotionalMargin(ModifiedCarryForecastDetail forecast) throws NoRealizablePriceException {
        PricePK pricePK = new PricePK();
        LOG.log(Level.INFO, "*********NPE Check********* forecast={0}", new Object[]{forecast});
        pricePK.setPeriodMonth(forecast.getPeriodMonth());
        pricePK.setPeriodYear(forecast.getPeriodYear());

        Double INM = null;

        Price price = priceBean.find(pricePK);

        Contract contract = forecast.getContract();
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

        forecast.setMargin(INM);
        LOG.log(Level.INFO, "Increamental National Margin (GNM)=>{0}", INM);

        return forecast;
    }

}
