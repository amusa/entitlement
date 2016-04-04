/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.ejb.production.jv.impl;

import com.nnpcgroup.cosm.ejb.production.jv.RegularProductionServices;
import com.nnpcgroup.cosm.entity.production.jv.RegularProduction;
import com.nnpcgroup.cosm.entity.EquityType;
import com.nnpcgroup.cosm.entity.FiscalArrangement;
import com.nnpcgroup.cosm.entity.JointVenture;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author 18359
 */
@Stateless
@Local(RegularProductionServices.class)
public class RegularProductionBean extends JvProductionServicesImpl<RegularProduction> implements RegularProductionServices {

    private static final Logger log = Logger.getLogger(RegularProductionBean.class.getName());

    public RegularProductionBean() {
        super(RegularProduction.class);
        log.info("ProductionBean::constructor activated...");

    }

    @Override
    public RegularProduction createInstance() {
        log.info("JvActualProductionBean::Creating new JvActualProduction Instance...");
        return new RegularProduction();
    }

    @Override
    public RegularProduction computeEntitlement(RegularProduction production) {
        log.log(Level.INFO, "JvActualProductionBean::computing Entitlement for production {0}...", production);
        FiscalArrangement fa;
        JointVenture jv;

        fa = production.getContract().getFiscalArrangement();

        assert (fa instanceof JointVenture);

        jv = (JointVenture) fa;
        EquityType et = jv.getEquityType();

        Double ownEntitlement;
        Double partnerEntitlement;
        Double grossProd = production.getGrossProduction();
        Double stockAdjustment = production.getStockAdjustment() == null ? 0 : production.getStockAdjustment();

        log.log(Level.INFO, "Production={0} production.getOpeningStock()={1} EquityType={3}...",
                new Object[]{production, production.getOpeningStock(), et});

        if (grossProd == null) {
            log.log(Level.INFO, "Gross production is NULL!");
            return production;
        }
        ownEntitlement = (grossProd
                + stockAdjustment)
                * et.getOwnEquity() * 0.01;

        partnerEntitlement = (grossProd
                + stockAdjustment)
                * et.getPartnerEquity() * 0.01;

        production.setOwnShareEntitlement(ownEntitlement);
        production.setPartnerShareEntitlement(partnerEntitlement);

        return production;
    }

    @Override
    public List<RegularProduction> findByYearAndMonth(int year, int month) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery cq = cb.createQuery();
        Root e = cq.from(RegularProduction.class
        );
        cq.where(
                cb.and(cb.equal(e.get("periodYear"), year),
                        cb.equal(e.get("periodMonth"), month)
                ));

        Query query = getEntityManager().createQuery(cq);
        List<RegularProduction> productions = query.getResultList();

        return productions;
    }

    @Override
    public RegularProduction openingStockChanged(RegularProduction production) {
        log.log(Level.INFO, "Opening Stock changed {0}...", production);
        return computeClosingStock(
                computeAvailability(production)
        );
    }

    @Override
    public RegularProduction liftingChanged(RegularProduction production) {
        log.log(Level.INFO, "Lifting changed {0}...", production);
        return computeClosingStock(
                computeAvailability(
                        computeStockAdjustment(
                                computeClosingStock(
                                        computeAvailability(
                                                stockAdjustmentReset(production)
                                        )
                                )
                        )
                )
        );
    }

    public RegularProduction stockAdjustmentReset(RegularProduction production) {
        production.setStockAdjustment(null);
        production.setPartnerStockAdjustment(null);
        return production;
    }

    @Override
    public RegularProduction grossProductionChanged(RegularProduction production) {
        log.log(Level.INFO, "Gross production changed");
        return computeClosingStock(
                computeLifting(
                        computeAvailability(
                                computeEntitlement(
                                        computeOpeningStock(
                                                stockAdjustmentReset(production)
                                        )
                                )
                        )
                )
        );
    }

    @Override
    public RegularProduction computeAvailability(RegularProduction production) {
        Double availability, partnerAvailability;
        Double ownEntitlement = production.getOwnShareEntitlement();
        Double partnerEntitlement = production.getPartnerShareEntitlement();
        Double openingStock = production.getOpeningStock();
        Double partnerOpeningStock = production.getPartnerOpeningStock();
        Double stockAdjustment = production.getStockAdjustment() != null ? production.getStockAdjustment() : 0.0;
        Double partnerStockAdjustment = production.getStockAdjustment() != null ? production.getPartnerStockAdjustment() : 0.0;

        availability = ownEntitlement + openingStock + stockAdjustment;
        partnerAvailability = partnerEntitlement + partnerOpeningStock + partnerStockAdjustment;

        production.setAvailability(availability);
        production.setPartnerAvailability(partnerAvailability);

        return production;
    }

    @Override
    public RegularProduction computeClosingStock(RegularProduction production) {
        Double closingStock, partnerClosingStock;
        Double availability = production.getAvailability();
        Double partnerAvailability = production.getPartnerAvailability();
        Double lifting = production.getLifting();
        Double partnerLifting = production.getPartnerLifting();

        closingStock = availability - lifting;
        partnerClosingStock = partnerAvailability - partnerLifting;

        production.setClosingStock(closingStock);
        production.setPartnerClosingStock(partnerClosingStock);

        return production;
    }

    @Override
    public RegularProduction computeStockAdjustment(RegularProduction production) {
        Double closingStock = production.getClosingStock();

        if (closingStock < 0) {
            production.setClosingStock(0.0);
            production.setStockAdjustment(-1 * closingStock);
            production.setPartnerStockAdjustment(closingStock);
        }

        Double partnerClosingStock = production.getPartnerClosingStock();

        if (partnerClosingStock < 0) {
            production.setPartnerClosingStock(0.0);
            production.setPartnerStockAdjustment(-1 * partnerClosingStock);
            production.setStockAdjustment(partnerClosingStock);
        }

        return production;

    }
   
    
}
