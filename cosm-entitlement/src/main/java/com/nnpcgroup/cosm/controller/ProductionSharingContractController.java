package com.nnpcgroup.cosm.controller;

import com.nnpcgroup.cosm.entity.ProductionSharingContract;
import com.nnpcgroup.cosm.controller.util.JsfUtil;
import com.nnpcgroup.cosm.controller.util.JsfUtil.PersistAction;
import com.nnpcgroup.cosm.ejb.ProductionSharingContractBean;
import com.nnpcgroup.cosm.entity.contract.OilField;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@Named("pscController")
@SessionScoped
public class ProductionSharingContractController implements Serializable {

    private static final long serialVersionUID = 1720728647859890689L;

    @EJB
    private ProductionSharingContractBean ejbFacade;
    private List<ProductionSharingContract> pscItems = null;
    private ProductionSharingContract selected;
    private OilField currentOilField = null;

    public ProductionSharingContractController() {
    }

    public ProductionSharingContract getSelected() {
        return selected;
    }

    public void setSelected(ProductionSharingContract selected) {
        this.selected = selected;
    }

    public OilField getCurrentOilField() {
        return currentOilField;
    }

    public void setCurrentOilField(OilField currentOilField) {
        this.currentOilField = currentOilField;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private ProductionSharingContractBean getFacade() {
        return ejbFacade;
    }

    public void prepareAddOilField() {
        currentOilField = new OilField();
    }

    public void addOilField() {
        currentOilField.setContract(selected);
        selected.addOilField(currentOilField);
    }

    public String prepareCreate() {
        selected = new ProductionSharingContract();
        initializeEmbeddableKey();
        return "psc-create";
    }

    public String create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("ProductionSharingContractCreated"));
        if (!JsfUtil.isValidationFailed()) {
            pscItems = null;    // Invalidate list of pscItems to trigger re-query.
        }

        return "psc-list";
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("ProductionSharingContractUpdated"));
    }

    public void destroy(ProductionSharingContract psc) {
        setSelected(psc);
        destroy();
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("ProductionSharingContractDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            pscItems = null;    // Invalidate list of pscItems to trigger re-query.
        }
    }

    public String cancel() {
        pscItems = null;
        return "psc-list";
    }

    public void cancelOilField() {
        currentOilField = null;
    }

    public void remove(OilField field) {
        if (selected != null) {
            selected.getOilFields().remove(field);
        }
    }

    public List<ProductionSharingContract> getPscItems() {
        if (pscItems == null) {
            pscItems = getFacade().findAll();
        }
        return pscItems;
    }

    public boolean isOffshore() {
        if (selected != null) {
            return Objects.equals(selected.getTerrain(), "OFFSHORE");
        }
        return false;
    }

    private void persist(PersistAction persistAction, String successMessage) {
        if (selected != null) {
            setEmbeddableKeys();
            try {
                if (persistAction != PersistAction.DELETE) {
                    getFacade().edit(selected);
                } else {
                    getFacade().remove(selected);
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

    public ProductionSharingContract getProductionSharingContract(long id) {
        return getFacade().find(id);
    }

    public List<ProductionSharingContract> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<ProductionSharingContract> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = ProductionSharingContract.class)
    public static class ProductionSharingContractControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            ProductionSharingContractController controller = (ProductionSharingContractController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "pscController");
            return controller.getProductionSharingContract(getKey(value));
        }

        long getKey(String value) {
            long key;
            key = Long.parseLong(value);
            return key;
        }

        String getStringKey(long value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof ProductionSharingContract) {
                ProductionSharingContract o = (ProductionSharingContract) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), ProductionSharingContract.class.getName()});
                return null;
            }
        }

    }

}
