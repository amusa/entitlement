/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.ejb.production.jv.impl;

import com.nnpcgroup.cosm.ejb.production.jv.JvProductionEntitlementServices;
import com.nnpcgroup.cosm.entity.EquityType;
import com.nnpcgroup.cosm.entity.FiscalArrangement;
import com.nnpcgroup.cosm.entity.FiscalPeriod;
import com.nnpcgroup.cosm.entity.JointVenture;
import com.nnpcgroup.cosm.entity.contract.ContractPK;
import com.nnpcgroup.cosm.entity.production.jv.ProductionPK;

import java.util.logging.Level;
import java.util.logging.Logger;
import com.nnpcgroup.cosm.entity.contract.JvContract;
import com.nnpcgroup.cosm.entity.production.jv.JvProductionEntitlement;
import com.nnpcgroup.cosm.entity.production.jv.JvProductionEntitlementPK;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author 18359
 * @param <T>
 * @param <E>
 */
public abstract class JvProductionEntitlementServicesImpl<T extends JvProductionEntitlement, E extends JvContract> extends ProductionEntitlementServicesImpl<T> implements JvProductionEntitlementServices<T, E> {

    private static final Logger LOG = Logger.getLogger(JvProductionEntitlementServicesImpl.class.getName());

    public JvProductionEntitlementServicesImpl(Class<T> entityClass) {
        super(entityClass);
    }

    @Override
    public T computeOpeningStock(T production) {
        T prod = getPreviousMonthProduction(production);
        if (prod != null) {
            Double openingStock = prod.getClosingStock();
            Double partnerOpeningStock = prod.getPartnerClosingStock();
            production.setOpeningStock(openingStock);
            production.setPartnerOpeningStock(partnerOpeningStock);
        } else {
            production.setOpeningStock(0.0);
            production.setPartnerOpeningStock(0.0);
        }
        return production;
    }

    @Override
    public T openingStockChanged(T entitlement) {
        LOG.log(Level.INFO, "Opening Stock changed:");
        return computeClosingStock(
                computeLifting(
                        computeAvailability(entitlement)
                )
        );
    }

    @Override
    public T computeLifting(T production) {
        Double liftableVolume, partnerLiftableVolume;
        Integer cargoes, partnerCargoes;
        Double availability, partnerAvailability;

        if (production.getOperatorDeclaredVolume() == null) {
            availability = production.getAvailability();
            partnerAvailability = production.getPartnerAvailability();
        } else {
            availability = production.getOperatorDeclaredOwnAvailability();
            partnerAvailability = production.getOperatorDeclaredPartnerAvailability();
        }

        if (production.getLifting() == null) {
            cargoes = (int) (availability / 950000.0);
            liftableVolume = cargoes * 950000.0;
            production.setCargoes(cargoes);
            production.setLifting(liftableVolume);
        }

        if (production.getPartnerLifting() == null) {
            partnerCargoes = (int) (partnerAvailability / 950000.0);
            partnerLiftableVolume = partnerCargoes * 950000.0;
            production.setPartnerCargoes(partnerCargoes);
            production.setPartnerLifting(partnerLiftableVolume);
        }

        return production;
    }

    @Override
    public T enrich(T entitlement, Double netProd) throws Exception {
        LOG.log(Level.INFO, "Enriching production:");
        return computeClosingStock(
                computeLifting(
                        computeAvailability(
                                computeEntitlement(
                                        computeOpeningStock(
                                                overLiftReset(entitlement)
                                        ),
                                        netProd
                                )
                        )
                )
        );
    }

    @Override
    public T enrich(T production) throws Exception {
        LOG.log(Level.INFO, "Enriching production:");
        return computeClosingStock(
                computeLifting(
                        computeAvailability(production)
                )
        );
    }

//    @Override
//    public T grossProductionChanged(T production) {
//        LOG.log(Level.INFO, "Gross production changed");
//        return computeClosingStock(
//                computeLifting(
//                        computeAvailability(
//                                computeEntitlement(
//                                        computeOpeningStock(
//                                                overLiftReset(production)
//                                        )
//                                )
//                        )
//                )
//        );
//    }
    @Override
    public T computeEntitlement(T production, Double netProd) {
        LOG.info("computing Entitlement...");
        FiscalArrangement fa;
        JointVenture jv;

        fa = production.getContract().getFiscalArrangement();
        //fa = fiscalBean.find(production.getFiscalArrangementId());

        assert (fa instanceof JointVenture);

        jv = (JointVenture) fa;
        EquityType et = jv.getEquityType();

        Double ownEntitlement;
        Double partnerEntitlement;

        // Double netProduction = production.getNetProduction() != null ? production.getNetProduction() : 0;
        ownEntitlement = (netProd
                * et.getOwnEquity() * 0.01);
        LOG.log(Level.INFO, "Own Entitlement=>{0} * {1} * 0.01 = {2}", new Object[]{netProd, et.getOwnEquity(), ownEntitlement});

        partnerEntitlement = (netProd
                * et.getPartnerEquity() * 0.01);
        LOG.log(Level.INFO, "Partner Entitlement=>{0} * {1} * 0.01 = {2}", new Object[]{netProd, et.getPartnerEquity(), partnerEntitlement});

        production.setOwnShareEntitlement(ownEntitlement);
        production.setPartnerShareEntitlement(partnerEntitlement);

        return production;
    }

    @Override
    public T computeAvailability(T production) {
        Double availability, partnerAvailability;
        Double ownEntitlement = production.getOwnShareEntitlement();
        Double partnerEntitlement = production.getPartnerShareEntitlement();
        Double openingStock = production.getOpeningStock();
        Double partnerOpeningStock = production.getPartnerOpeningStock();

        availability = ownEntitlement + openingStock;//+ overlift;
        partnerAvailability = partnerEntitlement + partnerOpeningStock;// + partnerOverlift;

        production.setAvailability(availability);
        production.setPartnerAvailability(partnerAvailability);

        return production;
    }

    @Override
    public T computeClosingStock(T production) {
        Double closingStock, partnerClosingStock;
        Double availability, partnerAvailability;

        if (production.getOperatorDeclaredVolume() == null) {
            availability = production.getAvailability();
            partnerAvailability = production.getPartnerAvailability();
        } else {
            availability = production.getOperatorDeclaredOwnAvailability();
            partnerAvailability = production.getOperatorDeclaredPartnerAvailability();
        }

        Double lifting = production.getLifting();
        Double partnerLifting = production.getPartnerLifting();

        closingStock = availability - lifting;
        partnerClosingStock = partnerAvailability - partnerLifting;
        production.setClosingStock(closingStock);
        production.setPartnerClosingStock(partnerClosingStock);

        return production;
    }

    @Override
    public T getPreviousMonthProduction(T production) {
        int month = production.getPeriodMonth();
        int year = production.getPeriodYear();

        FiscalPeriod prevFp = getPreviousFiscalPeriod(year, month);
        ContractPK cPK = production.getContract().getContractPK();
        ProductionPK pPK = new ProductionPK(prevFp.getYear(), prevFp.getMonth(), production.getProduction().getFiscalArrangement().getId());

        T prod = find(new JvProductionEntitlementPK(pPK, cPK));

        return prod;

    }

    @Override
    public T getNextMonthProduction(T production) {
        int month = production.getPeriodMonth();
        int year = production.getPeriodYear();
        FiscalPeriod nextFp = getNextFiscalPeriod(year, month);
        ContractPK cPK = production.getContract().getContractPK();
        ProductionPK pPK = new ProductionPK(nextFp.getYear(), nextFp.getMonth(), production.getProduction().getFiscalArrangement().getId());

        T prod = find(new JvProductionEntitlementPK(pPK, cPK));

        return prod;
    }

    @Override
    public T find(int year, int month, E contract) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();

        T entitlement;

        CriteriaQuery cq = cb.createQuery();
        Root e = cq.from(entityClass);
        try {
            cq.select(e).where(
                    cb.and(cb.equal(e.get("periodYear"), year),
                            cb.equal(e.get("periodMonth"), month),
                            cb.equal(e.get("contract"), contract)
                    ));

            Query query = getEntityManager().createQuery(cq);

            entitlement = (T) query.getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }

        return entitlement;
    }

    @Override
    public void delete(int year, int month, E contract) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();

        // create delete
        CriteriaDelete<T> delete = cb.
                createCriteriaDelete(entityClass);

        // set the root class
        Root e = delete.from(entityClass);

        // set where clause
        delete.where(
                cb.and(cb.equal(e.get("periodYear"), year),
                        cb.equal(e.get("periodMonth"), month),
                        cb.equal(e.get("contract"), contract)
                ));

        // perform update
        getEntityManager().createQuery(delete).executeUpdate();
    }

    @Override
    public T computeOverlift(T production) {
        Double closingStock = production.getClosingStock();

        if (closingStock < 0) {
            production.setClosingStock(0.0);
            production.setOverlift(-1 * closingStock);
            production.setPartnerOverlift(closingStock);
        }

        Double partnerClosingStock = production.getPartnerClosingStock();

        if (partnerClosingStock < 0) {
            production.setPartnerClosingStock(0.0);
            production.setPartnerOverlift(-1 * partnerClosingStock);
            production.setOverlift(partnerClosingStock);
        }

        return production;
    }

    @Override
    public T computeOperatorDeclaredEquity(T production, Double operatorDeclaredVol) {
        production.setOperatorDeclaredVolume(operatorDeclaredVol);
        return computeOperatorDeclaredEquity(production);
    }

    @Override
    public T computeOperatorDeclaredEquity(T production) {
        if (production.getOperatorDeclaredVolume() == null) {
            production.setOperatorDeclaredOwnAvailability(null);
            production.setOperatorDeclaredPartnerAvailability(null);

        } else {
            Double operatorVol = production.getOperatorDeclaredVolume();
            Double ownAvail2 = operatorVol * production.getOwnEquityRatio();
            Double partnerAvail2 = operatorVol * production.getPartnerEquityRatio();

            production.setOperatorDeclaredOwnAvailability(ownAvail2);
            production.setOperatorDeclaredPartnerAvailability(partnerAvail2);
        }

        return computeClosingStock(
                computeLifting(production)
        );
    }

    @Override
    public T operatorDeclaredVolumeChanged(T production, Double operatorDeclaredVol) {
        production.setOperatorDeclaredVolume(operatorDeclaredVol);
        return production;
    }

    public T overLiftReset(T production) {
        production.setOverlift(null);
        production.setPartnerOverlift(null);
        return production;
    }

}
