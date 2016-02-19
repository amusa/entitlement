/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.comd.cosm.entitlement.ejb;

import com.nnpcgroup.comd.cosm.entitlement.entity.ActualJvProduction;
import com.nnpcgroup.comd.cosm.entitlement.entity.EquityType;
import com.nnpcgroup.comd.cosm.entitlement.entity.FiscalArrangement;
import com.nnpcgroup.comd.cosm.entitlement.entity.JointVenture;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
//@JVACTUAL
public class JvActualProductionBean extends ProductionTemplate<ActualJvProduction> {//implements ActualEntitlement {

    private static final Logger log = Logger.getLogger(JvActualProductionBean.class.getName());

    public JvActualProductionBean() {
        super(ActualJvProduction.class);
        log.info("JvActualProductionBean::constructor activated...");

    }

    @Override
    public ActualJvProduction createInstance() {
        log.info("JvActualProductionBean::Creating new ActualJvProduction Instance...");
        return new ActualJvProduction();
    }

    @Override
    public ActualJvProduction computeEntitlement(ActualJvProduction production) {
        log.log(Level.INFO, "JvActualProductionBean::computing Entitlement for production {0}...", production);
        FiscalArrangement fa;
        JointVenture jv;

        fa = production.getContractStream().getFiscalArrangement();

        assert (fa instanceof JointVenture);

        jv = (JointVenture) fa;
        EquityType et = jv.getEquityType();

        Double ownEntitlement;
        Double partnerEntitlement;
        Double grossProd = production.getGrossProduction();
        Double stockAdjustment = production.getStockAdjustment() == null ? 0 : production.getStockAdjustment();

        log.log(Level.INFO, "Production={0} production.getProductionVolume()={1} production.getOpeningStock()={2} EquityType={3}...",
                new Object[]{production, production.getProductionVolume(), production.getOpeningStock(), et});

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

//    @Override
//    public ActualJvProduction computeClosingStock(ActualJvProduction production) {
//        log.log(Level.INFO, "JvActualProductionBean::computing Closing stock for production {0}...", production);
//        
//        Double lifting = production.getLifting() == null ? 0 : production.getLifting();
//        Double stockAdjustment = production.getStockAdjustment() == null ? 0 : production.getStockAdjustment();
//       
//        log.log(Level.INFO, "JvActualProductionBean::computeClosingStock parameters "
//                + "stockAdj={1} lifting={2}", new Object[]{stockAdjustment, lifting});
//        
//        Double closingStock = stockAdjustment - lifting;
//
//        production.setClosingStock(closingStock);
//
//        return production;
//    }

//    @Override
//    public ActualJvProduction findByContractStreamPeriod(int year, int month, ContractStream cs) {
//        log.info("JvActualProductionBean::findByContractStreamPeriod called...");
//        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
//
//// Query for a List of objects.
//        ActualJvProduction production;
//
//        CriteriaQuery cq = cb.createQuery();
//        Root e = cq.from(ActualJvProduction.class);
//        try {
//            cq.where(
//                    cb.and(cb.equal(e.get("periodYear"), year),
//                            cb.equal(e.get("periodMonth"), month),
//                            cb.equal(e.get("contractStream"), cs)
//                    ));
//
//            Query query = getEntityManager().createQuery(cq);
//
//            production = (ActualJvProduction) query.getSingleResult();
//        } catch (NoResultException nre) {
//            return null;
//        }
//
//        return production;
//    }
//    @Override
//    public ActualJvProduction enrich(ActualJvProduction production) {
//        log.log(Level.INFO, "Enriching production {0}...", production);
//        return computeClosingStock(
//                super.enrich(production)
//        );
//    }
    // @Override
//    public ActualJvProduction computeAdjustedEntitlement(ActualJvProduction production) {
//        log.log(Level.INFO, "Computing adjusted Entitlement for {0}...", production);
//        //assert (production instanceof ActualJvProduction);
//        //ActualJvProduction jvProd = (ActualJvProduction) production;
//        //Double terminalLoss = 
//        Double availability = production.getOpeningStock() + production.getProductionVolume();
//        Double adjAvailability = availability - terminalLoss;
//
//        FiscalArrangement fa;
//        JointVenture jv;
//
//        fa = production.getContractStream().getFiscalArrangement();
//
//        assert (fa instanceof JointVenture);
//
//        jv = (JointVenture) fa;
//        EquityType et = jv.getEquityType();
//
//        Double adjOwnEntitlement;
//        Double adjPartnerEntitlement;
//
//        adjOwnEntitlement = adjAvailability * et.getOwnEquity() * 0.01;
//
//        adjPartnerEntitlement = adjAvailability * et.getPartnerEquity() * 0.01;
//
//        production.setAdjustedOwnEntitlement(adjOwnEntitlement);
//        production.setAdjustedPartnerEntitlement(adjPartnerEntitlement);
//        log.log(Level.INFO, "Adjusted Entitlement: Own={0} Partner={1}...",
//                new Object[]{production.getAdjustedOwnEntitlement(), production.getAdjustedPartnerEntitlement()});
//        return production;
//    }
    // @Override
//    public ActualJvProduction computeTerminalLosses(ActualJvProduction production) {
//        log.log(Level.INFO, "Computing Terminal Loss for production {0}...", production);
//        //assert (production instanceof ActualJvProduction);
//        //ActualJvProduction jvProd = (ActualJvProduction) production;
//
//        Terminal terminal = production.getContractStream().getCrudeType().getTerminal();
//        production.setDeadStockContribution(terminal.getDeadStockVolume());
//        production.setLineFillContribution(terminal.getLineFillVolume());
//        log.log(Level.INFO, "Terminal Losses: Deadstock={0} Linefill={1}...", new Object[]{production.getDeadStockContribution(), production.getLineFillContribution()});
//
//        return production;
//    }
    @Override
    public List<ActualJvProduction> findByYearAndMonth(int year, int month) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
// Query for a List of objects.
        CriteriaQuery cq = cb.createQuery();
        Root e = cq.from(ActualJvProduction.class
        );
        cq.where(
                cb.and(cb.equal(e.get("periodYear"), year),
                        cb.equal(e.get("periodMonth"), month)
                ));

        Query query = getEntityManager().createQuery(cq);
        List<ActualJvProduction> productions = query.getResultList();

        return productions;
    }

//    @Override
//    public ActualJvProduction computeGrossProduction(ActualJvProduction production) {
//        Double prodVolume = production.getProductionVolume();
//        Double grossProd = prodVolume * 30; //TODO:Calculate days for each month
//        production.setGrossProduction(grossProd);
//        return production;
//    }
}
