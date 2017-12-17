/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosm.jv.production.infrastructure.persistence.jpa;

import com.cosm.common.domain.service.FiscalPeriodService;
import com.cosm.common.domain.service.internal.FiscalPeriod;
import com.cosm.common.infrastructure.persistence.jpa.JpaQueryRepository;
import com.cosm.jv.production.domain.model.ProductionEntitlement;
import com.cosm.jv.production.domain.model.ProductionEntitlementPK;
import com.cosm.jv.production.domain.repository.EntitlementQueryRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.logging.Logger;


/**
 *
 * @author amusa
 */
@ApplicationScoped
public class JpaEntitlementQueryRepository extends JpaQueryRepository<ProductionEntitlement> implements EntitlementQueryRepository {
    private static final Logger LOG = Logger.getLogger(JpaEntitlementQueryRepository.class.getName());

    @Inject
    FiscalPeriodService fiscalService;

    @PersistenceContext(unitName = "ProductionPU")
    private EntityManager em;

    public JpaEntitlementQueryRepository() {
        super(ProductionEntitlement.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    @Override
    public ProductionEntitlement getPreviousMonthProduction(ProductionEntitlement entitlement) {
        int month = entitlement.getPeriodMonth();
        int year = entitlement.getPeriodYear();
        FiscalPeriod prevFp = fiscalService.getPreviousFiscalPeriod(year, month);

        ProductionEntitlementPK pPk = new ProductionEntitlementPK(prevFp.getYear(), prevFp.getMonth(), entitlement.getJvId(), entitlement.getContractId(), entitlement.getCrudeTypeCode());
        ProductionEntitlement prevEntitlement = find(pPk);

        return prevEntitlement;
    }

    @Override
    public ProductionEntitlement getNextMonthProduction(ProductionEntitlement entitlement) {
        int month = entitlement.getPeriodMonth();
        int year = entitlement.getPeriodYear();
        FiscalPeriod nextFp = fiscalService.getNextFiscalPeriod(year, month);

        ProductionEntitlementPK pPk = new ProductionEntitlementPK(nextFp.getYear(), nextFp.getMonth(), entitlement.getJvId(), entitlement.getContractId(), entitlement.getCrudeTypeCode());

        ProductionEntitlement nextEntitlement = find(pPk);

        return nextEntitlement;
    }


}
