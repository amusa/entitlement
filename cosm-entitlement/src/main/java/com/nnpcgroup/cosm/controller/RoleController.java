/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.controller;

import com.nnpcgroup.cosm.controller.util.JsfUtil;
import com.nnpcgroup.cosm.ejb.RoleBean;
import com.nnpcgroup.cosm.entity.Company;
import com.nnpcgroup.cosm.entity.user.Role;
import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.model.SelectItem;
import javax.inject.Named;

/**
 *
 * @author 18359
 */
@Named("roleController")
@SessionScoped
public class RoleController implements Serializable {

    @EJB
    private RoleBean roleBean;
    private static final Logger LOG = Logger.getLogger(RoleController.class.getName());

    private Role selected;
    private List<Role> roles;

    public RoleBean getRoleBean() {
        return roleBean;
    }

    public void setUserBean(RoleBean roleBean) {
        this.roleBean = roleBean;
    }

    public Role getSelected() {
        return selected;
    }

    public void setSelected(Role selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private RoleBean getFacade() {
        return roleBean;
    }

    public Role prepareCreate() {
        LOG.log(Level.INFO, "preparing to create...");

        selected = new Role();
        initializeEmbeddableKey();
        return selected;
    }

    public void cancel() {
        roles = null;
        selected = null;
    }

    public void create() {
        LOG.log(Level.INFO, "creating user...");
        persist(JsfUtil.PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("UserCreated"));
        if (!JsfUtil.isValidationFailed()) {
            LOG.log(Level.INFO, "validation failed...");
            roles = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(JsfUtil.PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("UserUpdated"));
    }

    public void destroy(Role role) {
        setSelected(role);
        destroy();
    }

    public void destroy() {
        persist(JsfUtil.PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("UserDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            roles = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Role> getUsers() {
        if (roles == null) {
            roles = getFacade().findAll();
        }
        return roles;
    }

    private void persist(JsfUtil.PersistAction persistAction, String successMessage) {
        LOG.log(Level.INFO, "checking selected user null condition...", selected);
        if (selected != null) {
            LOG.log(Level.INFO, "selected user not null...");
            setEmbeddableKeys();
            try {
                if (persistAction != JsfUtil.PersistAction.DELETE) {
                    LOG.log(Level.INFO, "saving...");
                    getFacade().edit(selected);
                    LOG.log(Level.INFO, "save successful...");
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

    public Role getRole(String id) {
        return getFacade().find(id);
    }

    public List<Role> getItemsAvailableSelectMany() {
        return getFacade().findAll();

    }

    public SelectItem[] getItemsAvailableSelectOne() {
        return  JsfUtil.getSelectItems(getFacade().findAll(),false);
    }

    @FacesConverter(forClass = com.nnpcgroup.cosm.entity.user.Role.class)
    public static class RoleControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            RoleController controller = (RoleController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "roleController");

            return controller.getRole(value);
        }

        int getKey(String value) {
            int key;
            key = Integer.parseInt(value);
            return key;
        }

        String getStringKey(int value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Role) {
                Role o = (Role) object;
                return o.getRole();
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Company.class.getName()});
                return null;
            }
        }

    }
}
