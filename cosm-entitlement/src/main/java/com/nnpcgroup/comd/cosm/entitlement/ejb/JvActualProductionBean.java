/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.comd.cosm.entitlement.ejb;

import com.nnpcgroup.comd.cosm.entitlement.entity.ActualJvProduction;
import com.nnpcgroup.comd.cosm.entitlement.entity.ContractStream;
import com.nnpcgroup.comd.cosm.entitlement.entity.EquityType;
import com.nnpcgroup.comd.cosm.entitlement.entity.FiscalArrangement;
import com.nnpcgroup.comd.cosm.entitlement.entity.JointVenture;
import com.nnpcgroup.comd.cosm.entitlement.entity.Terminal;
import com.nnpcgroup.comd.cosm.entitlement.util.JVACTUAL;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;
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
    public ActualJvProduction computeOpeningStock(ActualJvProduction production) {
        log.info("JvActualProductionBean::computing Opening stock...");
        production.setOpeningStock(0.0);//TODO:compute Actual JV Production opening stock
        return production;
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

        log.log(Level.INFO, "Production={0} production.getProductionVolume()={1} production.getOpeningStock()={2} EquityType={3}...",
                new Object[]{production, production.getProductionVolume(), production.getOpeningStock(), et});

        ownEntitlement = (production.getProductionVolume()
                + production.getOpeningStock())
                * et.getOwnEquity() * 0.01;

        partnerEntitlement = (production.getProductionVolume()
                + production.getOpeningStock())
                * et.getPartnerEquity() * 0.01;

        production.setOwnShareEntitlement(ownEntitlement);
        production.setPartnerShareEntitlement(partnerEntitlement);

        return production;
    }

    @Override
    public ActualJvProduction computeClosingStock(ActualJvProduction production) {
        log.log(Level.INFO, "JvActualProductionBean::computing Closing stock for production {0}...", production);
        production.setClosingStock(0.0);
        return production;
    }

    //@Override
    public ActualJvProduction findByContractStreamPeriod(int year, int month, ContractStream cs) {
        log.info("JvActualProductionBean::findByContractStreamPeriod called...");
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();

// Query for a List of objects.
        ActualJvProduction production;

        CriteriaQuery cq = cb.createQuery();
        Root e = cq.from(ActualJvProduction.class);
        try {
            cq.where(
                    cb.and(cb.equal(e.get("periodYear"), year),
                            cb.equal(e.get("periodMonth"), month),
                            cb.equal(e.get("contractStream"), cs)
                    ));

            Query query = getEntityManager().createQuery(cq);

            production = (ActualJvProduction) query.getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }

        return production;
    }

    @Override
    public ActualJvProduction enrich(ActualJvProduction production) {
        log.log(Level.INFO, "Enriching production {0}...", production);
        return computeClosingStock(
                computeAdjustedEntitlement(
                        computeTerminalLosses(
                                super.enrich(production)
                        )
                ));
    }

    // @Override
    public ActualJvProduction computeAdjustedEntitlement(ActualJvProduction production) {
        log.log(Level.INFO, "Computing adjusted Entitlement for {0}...", production);
        //assert (production instanceof ActualJvProduction);
        //ActualJvProduction jvProd = (ActualJvProduction) production;
        Double terminalLoss = production.getDeadStockContribution() + production.getLineFillContribution();
        Double availability = production.getOpeningStock() + production.getProductionVolume();
        Double adjAvailability = availability - terminalLoss;

        FiscalArrangement fa;
        JointVenture jv;

        fa = production.getContractStream().getFiscalArrangement();

        assert (fa instanceof JointVenture);

        jv = (JointVenture) fa;
        EquityType et = jv.getEquityType();

        Double adjOwnEntitlement;
        Double adjPartnerEntitlement;

        adjOwnEntitlement = adjAvailability * et.getOwnEquity() * 0.01;

        adjPartnerEntitlement = adjAvailability * et.getPartnerEquity() * 0.01;

        production.setAdjustedOwnEntitlement(adjOwnEntitlement);
        production.setAdjustedPartnerEntitlement(adjPartnerEntitlement);
        log.log(Level.INFO, "Adjusted Entitlement: Own={0} Partner={1}...",
                new Object[]{production.getAdjustedOwnEntitlement(), production.getAdjustedPartnerEntitlement()});
        return production;
    }

    // @Override
    public ActualJvProduction computeTerminalLosses(ActualJvProduction production) {
        log.log(Level.INFO, "Computing Terminal Loss for production {0}...", production);
        //assert (production instanceof ActualJvProduction);
        //ActualJvProduction jvProd = (ActualJvProduction) production;

        Terminal terminal = production.getContractStream().getCrudeType().getTerminal();
        production.setDeadStockContribution(terminal.getDeadStockVolume());
        production.setLineFillContribution(terminal.getLineFillVolume());
        log.log(Level.INFO, "Terminal Losses: Deadstock={0} Linefill={1}...", new Object[]{production.getDeadStockContribution(), production.getLineFillContribution()});

        return production;
    }

    @Override
    public List<ActualJvProduction> findByYearAndMonth(int year, int month) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
// Query for a List of objects.
        CriteriaQuery cq = cb.createQuery();
        Root e = cq.from(ActualJvProduction.class);
        cq.where(
                cb.and(cb.equal(e.get("periodYear"), year),
                        cb.equal(e.get("periodMonth"), month)
                ));

        Query query = getEntityManager().createQuery(cq);
        List<ActualJvProduction> productions = query.getResultList();

        return productions;
    }

}
