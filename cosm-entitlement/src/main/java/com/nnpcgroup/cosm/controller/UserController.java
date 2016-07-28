/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.controller;

import com.nnpcgroup.cosm.controller.util.JsfUtil;
import com.nnpcgroup.cosm.ejb.UserBean;
import com.nnpcgroup.cosm.entity.Company;
import com.nnpcgroup.cosm.entity.user.User;
<<<<<<< HEAD
import java.io.Serializable;
=======
>>>>>>> 93c948df2c8f8120a83697581ea8ba68047149b9
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.enterprise.context.RequestScoped;
<<<<<<< HEAD
import javax.enterprise.context.SessionScoped;
=======
>>>>>>> 93c948df2c8f8120a83697581ea8ba68047149b9
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Named;

/**
 *
 * @author 18359
 */
@Named("userController")
<<<<<<< HEAD
@SessionScoped
public class UserController implements Serializable{
=======
@RequestScoped
public class UserController {
>>>>>>> 93c948df2c8f8120a83697581ea8ba68047149b9

    @EJB
    private UserBean userBean;
    private static final Logger LOG = Logger.getLogger(UserController.class.getName());

    private User selected;
    private List<User> users;

    public UserBean getUserBean() {
        return userBean;
    }

    public void setUserBean(UserBean userBean) {
        this.userBean = userBean;
    }

    public User getSelected() {
        return selected;
    }

    public void setSelected(User selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private UserBean getFacade() {
        return userBean;
    }

    public User prepareCreate() {
<<<<<<< HEAD
        LOG.log(Level.INFO,"preparing to create...");
=======
>>>>>>> 93c948df2c8f8120a83697581ea8ba68047149b9
        selected = new User();
        initializeEmbeddableKey();
        return selected;
    }

    public void cancel() {
        users = null;
        selected = null;
    }

    public void create() {
<<<<<<< HEAD
        LOG.log(Level.INFO,"creating user...");
        persist(JsfUtil.PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("UserCreated"));
        if (!JsfUtil.isValidationFailed()) {
             LOG.log(Level.INFO,"validation failed...");
=======
        persist(JsfUtil.PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("UserCreated"));
        if (!JsfUtil.isValidationFailed()) {
>>>>>>> 93c948df2c8f8120a83697581ea8ba68047149b9
            users = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(JsfUtil.PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("UserUpdated"));
    }

    public void destroy(User user) {
        setSelected(user);
        destroy();
    }

    public void destroy() {
        persist(JsfUtil.PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("UserDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            users = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<User> getUsers() {
        if (users == null) {
            users = getFacade().findAll();
        }
        return users;
    }

    private void persist(JsfUtil.PersistAction persistAction, String successMessage) {
<<<<<<< HEAD
         LOG.log(Level.INFO,"checking selected user null condition...", selected);
        if (selected != null) {
             LOG.log(Level.INFO,"selected user not null...");
            setEmbeddableKeys();
            try {
                if (persistAction != JsfUtil.PersistAction.DELETE) {
                    LOG.log(Level.INFO,"saving...");
                    getFacade().edit(selected);
                    LOG.log(Level.INFO,"save successful...");
=======
        if (selected != null) {
            setEmbeddableKeys();
            try {
                if (persistAction != JsfUtil.PersistAction.DELETE) {
                    getFacade().edit(selected);
>>>>>>> 93c948df2c8f8120a83697581ea8ba68047149b9
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

    public User getUser(String id) {
        return getFacade().find(id);
    }

    public List<User> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<User> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = User.class)
    public static class UserControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            UserController controller = (UserController) facesContext.getApplication().getELResolver().
<<<<<<< HEAD
                    getValue(facesContext.getELContext(), null, "userController");
=======
                    getValue(facesContext.getELContext(), null, "companyController");
>>>>>>> 93c948df2c8f8120a83697581ea8ba68047149b9
            return controller.getUser(value);
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
            if (object instanceof User) {
                User o = (User) object;
                return o.getUserName();
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Company.class.getName()});
                return null;
            }
        }

    }
}
