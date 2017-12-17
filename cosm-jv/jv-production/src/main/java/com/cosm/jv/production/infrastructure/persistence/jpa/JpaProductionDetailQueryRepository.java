/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosm.jv.production.infrastructure.persistence.jpa;

import com.cosm.common.domain.service.FiscalPeriodService;
import com.cosm.common.domain.service.internal.FiscalPeriod;
import com.cosm.common.infrastructure.persistence.jpa.JpaQueryRepository;
import com.cosm.jv.production.domain.model.ProductionDetail;
import com.cosm.jv.production.domain.model.ProductionDetailPK;
import com.cosm.jv.production.domain.repository.ProductionDetailQueryRepository;

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
public class JpaProductionDetailQueryRepository extends JpaQueryRepository<ProductionDetail> implements ProductionDetailQueryRepository {
    private static final Logger LOG = Logger.getLogger(JpaProductionDetailQueryRepository.class.getName());

    @Inject
    FiscalPeriodService fiscalService;

    @PersistenceContext(unitName = "ProductionPU")
    private EntityManager em;

    public JpaProductionDetailQueryRepository() {
        super(ProductionDetail.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }


    @Override
    public ProductionDetail getPreviousMonthProduction(ProductionDetail productionDetail) {
        int month = productionDetail.getPeriodMonth();
        int year = productionDetail.getPeriodYear();
        FiscalPeriod prevFp = fiscalService.getPreviousFiscalPeriod(year, month);

        ProductionDetailPK pPk = new ProductionDetailPK(prevFp.getYear(), prevFp.getMonth(), productionDetail.getJvId(), productionDetail.getContractId(), productionDetail.getCrudeTypeCode());
        ProductionDetail prevProductionDetail = find(pPk);

        return prevProductionDetail;
    }

    @Override
    public ProductionDetail getNextMonthProduction(ProductionDetail productionDetail) {
        int month = productionDetail.getPeriodMonth();
        int year = productionDetail.getPeriodYear();
        FiscalPeriod nextFp = fiscalService.getNextFiscalPeriod(year, month);

        ProductionDetailPK pPk = new ProductionDetailPK(nextFp.getYear(), nextFp.getMonth(), productionDetail.getJvId(), productionDetail.getContractId(), productionDetail.getCrudeTypeCode());

        ProductionDetail nextProductionDetail = find(pPk);

        return nextProductionDetail;
    }

}
