package com.nnpcgroup.cosm.controller;

import com.nnpcgroup.cosm.entity.EquityType;
import com.nnpcgroup.cosm.controller.util.JsfUtil;
import com.nnpcgroup.cosm.controller.util.JsfUtil.PersistAction;
import com.nnpcgroup.cosm.ejb.EquityTypeBean;

import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIInput;
import javax.faces.validator.ValidatorException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@Named("equityTypeController")
@SessionScoped
public class EquityTypeController implements Serializable {

    private static final Logger LOG = Logger.getLogger(EquityTypeController.class.getName());

    @EJB
    private com.nnpcgroup.cosm.ejb.EquityTypeBean ejbFacade;
    private List<EquityType> items = null;
    private EquityType selected;

    public EquityTypeController() {
    }

    public EquityType getSelected() {
        return selected;
    }

    public void setSelected(EquityType selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private EquityTypeBean getFacade() {
        return ejbFacade;
    }

    public EquityType prepareCreate() {
        selected = new EquityType();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("EquityTypeCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("EquityTypeUpdated"));
    }

    public void destroy(EquityType equityType) {
        setSelected(equityType);
        destroy();
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("EquityTypeDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void cancel() {
        items = null;
        selected = null;
    }

    public List<EquityType> getItems() {
        if (items == null) {
            items = getFacade().findAll();
        }
        return items;
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

    public EquityType getEquityType(java.lang.Long id) {
        return getFacade().find(id);
    }

    public List<EquityType> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<EquityType> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    public void equityDescriptionListener() {
        String equities[] = selected.getDescription().split("/");
        if (equities.length == 1 && !equities[0].isEmpty()) {
            double ownEquity = Double.parseDouble(equities[0]);
            selected.setOwnEquity(ownEquity);
        }

        if (equities.length == 2 && !equities[1].isEmpty()) {
            double partnerEquity = Double.parseDouble(equities[1]);
            selected.setPartnerEquity(partnerEquity);
        }
    }

    public void equityRatioValidator(FacesContext facesContext, UIComponent uiComponent, Object object) throws ValidatorException {
        double ownEquity = selected.getOwnEquity();
        double partnerEquity = selected.getPartnerEquity();

        double equity = ownEquity + partnerEquity;
        if (equity != 100.0) {
            cancel();
            FacesMessage msg = new FacesMessage(ResourceBundle.getBundle("/Bundle").getString("EquityRationValidationError"));
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(msg);
        }

    }


    @FacesConverter(forClass = EquityType.class)
    public static class EquityTypeControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            EquityTypeController controller = (EquityTypeController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "equityTypeController");
            return controller.getEquityType(getKey(value));
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
            if (object instanceof EquityType) {
                EquityType o = (EquityType) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), EquityType.class.getName()});
                return null;
            }
        }

    }

}
