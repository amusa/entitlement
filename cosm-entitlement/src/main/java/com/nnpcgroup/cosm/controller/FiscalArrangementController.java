package com.nnpcgroup.cosm.controller;

import com.nnpcgroup.cosm.controller.util.JsfUtil;
import com.nnpcgroup.cosm.ejb.contract.ContractBaseServices;
import com.nnpcgroup.cosm.ejb.FiscalArrangementBean;
import com.nnpcgroup.cosm.ejb.contract.ContractServices;
import com.nnpcgroup.cosm.entity.contract.Contract;
import com.nnpcgroup.cosm.entity.FiscalArrangement;
import com.nnpcgroup.cosm.entity.JointVenture;
import com.nnpcgroup.cosm.entity.ProductionSharingContract;
import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.model.SelectItem;

@Named("fiscalController")
@SessionScoped
public class FiscalArrangementController implements Serializable {

    private static final long serialVersionUID = 2912957338932205000L;
    private static final Logger LOG = Logger.getLogger(FiscalArrangementController.class.getName());

    private FiscalArrangement currentFiscal;
    private List<FiscalArrangement> fiscalArrangements;
    private List<ProductionSharingContract> pscFiscalArrangements;
    private List<JointVenture> jvFiscalArrangements;

    @EJB
    private FiscalArrangementBean fiscalBean;

    @EJB
    private ContractServices contractBean;

    public FiscalArrangementController() {
        this.currentFiscal = null;
    }

    public FiscalArrangement getCurrentFiscal() {
        return currentFiscal;
    }

    public void setCurrentFiscal(FiscalArrangement currentFiscal) {
        this.currentFiscal = currentFiscal;
    }

    private FiscalArrangementBean getFiscalBean() {
        return fiscalBean;
    }

    private void performDestroy() {
        try {
            getFiscalBean().remove(currentFiscal);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("FiscalArrangementDeleted"));
            LOG.log(Level.INFO, "Fiscal Arrangement deleted successfully {0}...", currentFiscal);
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            LOG.log(Level.INFO, "Failed to delete Fiscal Arrangement {0}...", currentFiscal);
        }
    }

    public List<FiscalArrangement> getFiscalArrangements() {
        if (fiscalArrangements == null) {
            fiscalArrangements = getFiscalBean().findAll();
        }
        return fiscalArrangements;
    }

    public List<ProductionSharingContract> getPscFiscalArrangements() {
        if (pscFiscalArrangements == null) {
            pscFiscalArrangements = getFiscalBean().findPscFiscalArrangements();
        }
        return pscFiscalArrangements;
    }

    public List<JointVenture> getJvFiscalArrangements() {
        if (jvFiscalArrangements == null) {
            jvFiscalArrangements = getFiscalBean().findJvFiscalArrangements();
        }
        return jvFiscalArrangements;
    }

    public void setFiscalArrangements(List<FiscalArrangement> fiscalArrangements) {

        this.fiscalArrangements = fiscalArrangements;
    }

//    public SelectItem[] getFiscalSelectOptions() {
//        return JsfUtil.getSelectItems(fiscalBean.findAll(), false);
//    }
    public SelectItem[] getFiscalSelectOptions() {
        return JsfUtil.getSelectItems(fiscalBean.findAll(), false);
    }

    public SelectItem[] getJvFiscalSelectOptions() {
        return JsfUtil.getSelectItems(fiscalBean.findJvFiscalArrangements(), false);
    }

    public SelectItem[] getPscFiscalSelectOptions() {
        return JsfUtil.getSelectItems(fiscalBean.findPscFiscalArrangements(), false);
    }

    public List<FiscalArrangement> getFiscalList() {
        return fiscalBean.findAll();
    }

    public List<Contract> getContracts() {
        List<Contract> contracts = null;

        if (null != currentFiscal) {
            contracts = contractBean.findFiscalArrangementContracts(currentFiscal);
        } else {
            LOG.log(Level.FINE, "Fiscal Arrangement is null {0}...", currentFiscal);
        }

        return contracts;
    }

    public SelectItem[] getContractSelectOptions() {
        LOG.log(Level.FINE, "getting currentFiscal = {0}...", currentFiscal);
        if (null != currentFiscal) {
            return JsfUtil.getSelectItems(contractBean.findFiscalArrangementContracts(currentFiscal), true);
        }
        return null;
    }

    public void fiscalListChanged(AjaxBehaviorEvent event) {

    }

    public FiscalArrangement getFiscalArrangement(java.lang.Long id) {
        return fiscalBean.find(id);
    }

    @FacesConverter(forClass = FiscalArrangement.class)
    public static class FiscalArrangementControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            FiscalArrangementController controller = (FiscalArrangementController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "fiscalController");
            return controller.getFiscalArrangement(getKey(value));
        }

        java.lang.Long getKey(String value) {
            java.lang.Long key;
            key = Long.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Long value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof FiscalArrangement) {
                FiscalArrangement o = (FiscalArrangement) object;
                return getStringKey(o.getId());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + FiscalArrangement.class.getName());
            }
        }

    }

}
