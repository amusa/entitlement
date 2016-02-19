/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.comd.cosm.entitlement.ejb;

import com.nnpcgroup.comd.cosm.entitlement.entity.EquityType;
import com.nnpcgroup.comd.cosm.entitlement.entity.FiscalArrangement;
import com.nnpcgroup.comd.cosm.entitlement.entity.ForecastJvProduction;
import com.nnpcgroup.comd.cosm.entitlement.entity.JointVenture;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;

/**
 *
 * @author 18359
 */
@Stateless
//@JV
public class JvProductionBean extends ProductionTemplate<ForecastJvProduction> {

    private static final Logger log = Logger.getLogger(JvProductionBean.class.getName());

    public JvProductionBean() {
        super(ForecastJvProduction.class);
        log.info("JvProductionBean::constructor activated...");

    }

    @Override
    public ForecastJvProduction createInstance() {
        log.info("JvProductionBean::createProductionEntity() called...");
        return new ForecastJvProduction();
    }

//    @Override
//    public ForecastJvProduction computeOpeningStock(ForecastJvProduction production) {
//        log.info("JvProductionBean::computing Opening stock...");
//        production.setOpeningStock(0.0);//TODO:compute JV Production opening stock
//        return production;
//    }
    @Override
    public List<ForecastJvProduction> findByYearAndMonth(int year, int month) {
        log.log(Level.INFO, "Parameters: year={0}, month={1}", new Object[]{year, month});
//        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
//// Query for a List of objects.
//        CriteriaQuery cq = cb.createQuery();
//        Root e = cq.from(JvProduction.class);
//        cq.where(
//                cb.and(cb.equal(e.get("periodYear"), year),
//                        cb.equal(e.get("periodMonth"), month)
//                ));
//
//        Query query = getEntityManager().createQuery(cq);
//        List<JvProduction> productions = query.getResultList();

        List<ForecastJvProduction> productions = getEntityManager().createQuery(
                "SELECT p FROM Production p WHERE p.periodYear = :year and p.periodMonth = :month and TYPE(p) = ForecastJvProduction")
                .setParameter("year", year)
                .setParameter("month", month)
                .getResultList();

        return productions;
    }

    @Override
    public ForecastJvProduction computeEntitlement(ForecastJvProduction production) {
        log.info("JvProductionBean::computing Entitlement...");
        FiscalArrangement fa;
        JointVenture jv;

        fa = production.getContractStream().getFiscalArrangement();

        assert (fa instanceof JointVenture);

        jv = (JointVenture) fa;
        EquityType et = jv.getEquityType();

        Double ownEntitlement;
        Double partnerEntitlement;
        Double grossProd = production.getGrossProduction();
       
        grossProd = grossProd == null ? 0 : grossProd;
        
        ownEntitlement = (grossProd
                * et.getOwnEquity() * 0.01);

        partnerEntitlement = (grossProd
                * et.getPartnerEquity() * 0.01);

        production.setOwnShareEntitlement(ownEntitlement);
        production.setPartnerShareEntitlement(partnerEntitlement);

        return production;
    }

//    @Override
//    public ForecastJvProduction findByContractStreamPeriod(int year, int month, ContractStream cs) {
//       log.info("ForecastProductionBean::findByContractStreamPeriod called...");
//        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
//
//// Query for a List of objects.
//        ForecastJvProduction production;
//
//        CriteriaQuery cq = cb.createQuery();
//        Root e = cq.from(ForecastJvProduction.class);
//        try {
//            cq.where(
//                    cb.and(cb.equal(e.get("periodYear"), year),
//                            cb.equal(e.get("periodMonth"), month),
//                            cb.equal(e.get("contractStream"), cs)
//                    ));
//
//            Query query = getEntityManager().createQuery(cq);
//
//            production = (ForecastJvProduction) query.getSingleResult();
//        } catch (NoResultException nre) {
//            return null;
//        }
//
//        return production;
//    }
}
