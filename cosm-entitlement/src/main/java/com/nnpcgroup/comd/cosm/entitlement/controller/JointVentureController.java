package com.nnpcgroup.comd.cosm.entitlement.controller;

import com.nnpcgroup.comd.cosm.entitlement.entity.JointVenture;
import com.nnpcgroup.comd.cosm.entitlement.controller.util.JsfUtil;
import com.nnpcgroup.comd.cosm.entitlement.controller.util.JsfUtil.PersistAction;
import com.nnpcgroup.comd.cosm.entitlement.ejb.JointVentureBean;

import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.enterprise.context.SessionScoped;

import javax.inject.Named;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@Named("jvController")
@SessionScoped
public class JointVentureController implements Serializable {

    private static final long serialVersionUID = 2953220859300701424L;
    private static final Logger LOG = Logger.getLogger(JointVentureController.class.getName());

    @EJB
    private JointVentureBean jvBean;
    private List<JointVenture> jvItems = null;
    private JointVenture selected;

    public JointVentureController() {
    }

    public JointVenture getSelected() {
        return selected;
    }

    public void setSelected(JointVenture selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private JointVentureBean getJvBean() {
        return jvBean;
    }

    public JointVenture prepareCreate() {
        selected = new JointVenture();
        initializeEmbeddableKey();
         LOG.log(Level.INFO,"Creating new JV Object...{0}",selected);
        return selected;
    }

    public void cancel() {
        LOG.log(Level.INFO,"Cancelling operation...{0}",selected);
        jvItems = null;
    }

    public void create() {
        LOG.log(Level.INFO,"Creating JV Contract...{0}",selected);
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("JointVentureCreated"));
        if (!JsfUtil.isValidationFailed()) {
            jvItems = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        LOG.log(Level.INFO,"Updating JV Contract...{0}",selected);
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("JointVentureUpdated"));
    }
    
    public void destroy(JointVenture jv) {
        setSelected(jv);
        destroy();
    }
    

    public void destroy() {
        LOG.log(Level.INFO,"Deleting JV Contract...{0}",selected);
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("JointVentureDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            jvItems = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<JointVenture> getJvItems() {
        if (jvItems == null) {
            jvItems = getJvBean().findAll();
        }
        return jvItems;
    }

    private void persist(PersistAction persistAction, String successMessage) {
        LOG.log(Level.INFO,"persist called...{0}",selected);
        if (selected != null) {
            LOG.log(Level.INFO,"JV is not null. Persisting...{0}",selected);
            setEmbeddableKeys();
            try {
                if (persistAction != PersistAction.DELETE) {
                    getJvBean().edit(selected);
                } else {
                    getJvBean().remove(selected);
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

    public JointVenture getJointVenture(long id) {
        return getJvBean().find(id);
    }

    public List<JointVenture> getItemsAvailableSelectMany() {
        return getJvBean().findAll();
    }

    public List<JointVenture> getItemsAvailableSelectOne() {
        return getJvBean().findAll();
    }

    @FacesConverter(forClass = JointVenture.class)
    public static class JointVentureControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            JointVentureController controller = (JointVentureController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "jointVentureController");
            return controller.getJointVenture(getKey(value));
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
            if (object instanceof JointVenture) {
                JointVenture o = (JointVenture) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), JointVenture.class.getName()});
                return null;
            }
        }

    }

}
