package com.nnpcgroup.cosm.controller;

import com.nnpcgroup.cosm.controller.util.JsfUtil;
import com.nnpcgroup.cosm.controller.util.JsfUtil.PersistAction;
import com.nnpcgroup.cosm.ejb.cost.CostItemServices;
import com.nnpcgroup.cosm.ejb.cost.ProductionCostServices;
import com.nnpcgroup.cosm.entity.FiscalPeriod;
import com.nnpcgroup.cosm.entity.ProductionSharingContract;
import com.nnpcgroup.cosm.entity.cost.CostItem;
import com.nnpcgroup.cosm.entity.cost.ProductionCost;
import com.nnpcgroup.cosm.entity.cost.ProductionCostPK;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

@Named("prodCostController")
@SessionScoped
public class ProductionCostController implements Serializable {

    private static final Logger LOG = Logger.getLogger(ProductionCostController.class.getName());

    private static final long serialVersionUID = 3411266588734031876L;

    @EJB
    private ProductionCostServices prodCostBean;

    @EJB
    private CostItemServices costBean;

    @Inject
    Principal principal;

    private ProductionCost currentProdCost;
    private List<ProductionCost> productionCosts;
    private List<ProductionCost> prevProductionCosts;
    private Integer currentYear;
    private Integer currentMonth;
    private ProductionSharingContract currentPsc;

    public ProductionCostController() {
    }

    public void loadProductionCosts() {
        if (currentYear != null && currentMonth != null && currentPsc != null) {
            productionCosts = getProdCostBean().find(currentPsc, currentYear, currentMonth);
        }
    }

    public void loadPreviousProductionCosts() {
        if (currentYear != null && currentMonth != null && currentPsc != null) {
            FiscalPeriod fp = getProdCostBean().getPreviousFiscalPeriod(currentYear, currentMonth);
            prevProductionCosts = getProdCostBean().find(currentPsc, fp.getYear(), fp.getMonth());
        }

    }

    public boolean isEnableControlButton() {
        return currentYear != null && currentMonth != null && currentPsc != null;
    }

    public ProductionCost getCurrentProdCost() {
        return currentProdCost;
    }

    public void setCurrentProdCost(ProductionCost currentProdCost) {
        this.currentProdCost = currentProdCost;
    }

    public List<ProductionCost> getProductionCosts() {
        return productionCosts;
    }

    public void setProductionCosts(List<ProductionCost> productionCosts) {
        this.productionCosts = productionCosts;
    }

    public List<ProductionCost> getCapitalCosts() {
        if (productionCosts == null) {
            return null;
        }
        List<ProductionCost> capitalCosts = new ArrayList<>();
        productionCosts.stream().filter((pc) -> (pc.getCostItem().getCostCategory().getCode().equals("CAPEX"))).forEach((pc) -> {
            capitalCosts.add(pc);
        });
        return capitalCosts;
    }

    public List<ProductionCost> getNonCapitalCosts() {
        if (productionCosts == null) {
            return null;
        }
        List<ProductionCost> nonCapitalCosts = new ArrayList<>();
        productionCosts.stream().filter((pc) -> (pc.getCostItem().getCostCategory().getCode().equals("OPEX"))).forEach((pc) -> {
            nonCapitalCosts.add(pc);
        });
        return nonCapitalCosts;
    }

    public Double getTotalCapitalCost() {
        if (productionCosts == null) {
            return null;
        }
        double totalCapitalCost = 0;
        for (ProductionCost pc : productionCosts) {
            if (pc.getCostItem().getCostCategory().getCode().equals("CAPEX")) {
                totalCapitalCost += pc.getAmount();
            }
        }
        return totalCapitalCost;
    }

    public Double getTotalNonCapitalCost() {
        double totalNonCapitalCost = 0;
        if (productionCosts == null) {
            return null;
        }
        for (ProductionCost pc : productionCosts) {
            if (pc.getCostItem().getCostCategory().getCode().equals("OPEX")) {
                totalNonCapitalCost += pc.getAmount();
            }
        }
        return totalNonCapitalCost;
    }

    public Double getTotalCapitalCostCum() {
        if (productionCosts == null) {
            return null;
        }
        double totalCapitalCostCum = 0;
        for (ProductionCost pc : productionCosts) {
            if (pc.getCostItem().getCostCategory().getCode().equals("CAPEX")) {
                totalCapitalCostCum += pc.getAmountCum();
            }
        }
        return totalCapitalCostCum;
    }

    public Double getTotalNonCapitalCostCum() {
        if (productionCosts == null) {
            return null;
        }
        double totalNonCapitalCostCum = 0;
        for (ProductionCost pc : productionCosts) {
            if (pc.getCostItem().getCostCategory().getCode().equals("OPEX")) {
                totalNonCapitalCostCum += pc.getAmountCum();
            }
        }
        return totalNonCapitalCostCum;
    }

    protected void setEmbeddableKeys() {

    }

    protected void initializeEmbeddableKey() {
        //currentProdCost.setCurrentUser(principal.getName());
    }

    public void productionCostAmountChangeListener(ProductionCost prodCost) {
        //update cummulative cost
        boolean found = false;
        Iterator<ProductionCost> it = prevProductionCosts.iterator();
        ProductionCost prevCost = null;
        while (it.hasNext() && !found) {
            prevCost = it.next();
            if (prevCost.getCostItem().equals(prodCost.getCostItem())) {
                prodCost.totalCummulativeAmount(prevCost.getAmountCum());
                found = true;
            }
        }
        if (!found) {
            prodCost.totalCummulativeAmount(new Double(0));
        }
    }

    private void prepareProdCostItems(CostItem costItem) {
        ProductionCost prodCost = new ProductionCost();

        //set embedded key
        ProductionCostPK pPk = new ProductionCostPK();
        pPk.setPeriodYear(currentYear);
        pPk.setPeriodMonth(currentMonth);
        pPk.setPscId(currentPsc.getId());
        pPk.setCostCode(costItem.getCode());

        prodCost.setProductionCostPK(pPk);

        prodCost.setPsc(currentPsc);
        prodCost.setPeriodYear(currentYear);
        prodCost.setPeriodMonth(currentMonth);
        prodCost.setCostItem(costItem);

        productionCosts.add(prodCost);

    }

    private ProductionCostServices getProdCostBean() {
        return prodCostBean;
    }

    public void prepareCreate() {
        List<CostItem> costItems = costBean.findAll();
        productionCosts = new ArrayList<>();

        loadPreviousProductionCosts();

        for (CostItem costItem : costItems) {
            prepareProdCostItems(costItem);
        }

    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("ProdCostCreated"));
        if (!JsfUtil.isValidationFailed()) {
            reset();
            loadProductionCosts();
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("ProdCostUpdated"));
    }

    public void cancel() {
        reset();
    }

    public void reset() {
        currentProdCost = null;
        productionCosts = null;

    }

    public void destroy(ProductionCost pc) {
        setCurrentProdCost(pc);
        destroy();
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("ProdCostDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            reset();
        }
    }

     public void remove(ProductionCost pc) {
        if (productionCosts != null) {
            productionCosts.remove(pc);
        }
    }
     
    private void persist(PersistAction persistAction, String successMessage) {
        if (productionCosts != null) {
            try {
                if (persistAction != PersistAction.DELETE) {
                    getProdCostBean().edit(productionCosts);
                } else {
                    getProdCostBean().remove(productionCosts);
                }
                JsfUtil.addSuccessMessage(successMessage);
            } catch (EJBException ex) {
                String msg = "";
                Throwable cause = ex.getCause();
                if (cause != null) {
                    msg = cause.getLocalizedMessage();
                }
                if (msg.length() > 0) {
                    JsfUtil.addErrorMessage(msg);
                } else {
                    JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            }
        }
    }

    public Integer getCurrentMonth() {
        return currentMonth;
    }

    public void setCurrentMonth(Integer currentMonth) {
        this.currentMonth = currentMonth;
    }

    public ProductionSharingContract getCurrentPsc() {
        return currentPsc;
    }

    public void setCurrentPsc(ProductionSharingContract currentPsc) {
        this.currentPsc = currentPsc;
    }

    public Integer getCurrentYear() {
        return currentYear;
    }

    public void setCurrentYear(Integer currentYear) {
        this.currentYear = currentYear;
    }

}
