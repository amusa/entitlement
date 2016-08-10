/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.controller;

import com.nnpcgroup.cosm.controller.util.JsfUtil;
import com.nnpcgroup.cosm.entity.user.User;
import java.io.IOException;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.inject.Inject;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author 18359
 */
@Named(value = "userAuth")
@SessionScoped
public class UserAuth implements Serializable {

    private static final long serialVersionUID = 4727908159539105845L;
    private static final Logger LOG = Logger.getLogger(UserAuth.class.getName());

    @Inject
    UserController userController;

    private String username;
    private String password;
    private String originalURL;
    private User loggedUser;
    private String newPassword;

    /**
     * Creates a new instance of UserAuth
     */
    public UserAuth() {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        originalURL = (String) externalContext.getRequestMap().get(RequestDispatcher.FORWARD_REQUEST_URI);

        if (originalURL == null) {
            originalURL = externalContext.getRequestContextPath() + "/faces/index.xhtml";
        } else {
            String originalQuery = (String) externalContext.getRequestMap().get(RequestDispatcher.FORWARD_QUERY_STRING);

            if (originalQuery != null) {
                originalURL += "?" + originalQuery;
            }
        }
    }

//    @PostConstruct
//    public void init() {
//        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
//        HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();
//        originalURL = (String) request.getAttribute("original.url");// (String) externalContext.getRequestMap().get(RequestDispatcher.FORWARD_REQUEST_URI);
//
//        if (originalURL == null) {
//            originalURL = externalContext.getRequestContextPath() + "/faces/index.xhtml";
//        } else {
//            String originalQuery = (String) externalContext.getRequestMap().get(RequestDispatcher.FORWARD_QUERY_STRING);
//
//            if (originalQuery != null) {
//                originalURL += "?" + originalQuery;
//            }
//        }
//    }
    public void login() throws IOException {
        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext externalContext = context.getExternalContext();

        HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();

//        originalURL = (String) request.getAttribute("original.url");//(String) externalContext.getRequestMap().get(RequestDispatcher.FORWARD_REQUEST_URI);
//
//        if (originalURL == null) {
//            originalURL = externalContext.getRequestContextPath() + "/faces/index.xhtml";
//        } else {
//            String originalQuery = (String) externalContext.getRequestMap().get(RequestDispatcher.FORWARD_QUERY_STRING);
//
//            if (originalQuery != null) {
//                originalURL += "?" + originalQuery;
//            }
//        }
        try {
            request.login(username, password);
            LOG.log(Level.INFO, "Login successful {0}", username);
            LOG.log(Level.INFO, "Redirecting to original url... {0}", originalURL);
            loggedUser = userController.getUser(username);
            username = null;
            password = null;
            externalContext.redirect(originalURL);
        } catch (ServletException e) {
            // Handle unknown username/password in request.login().
            context.addMessage(null, new FacesMessage("Unknown login"));
            LOG.log(Level.INFO, "Unknown login {0}", username);
        }
    }

    public void logout() throws IOException {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        externalContext.invalidateSession();
        loggedUser = null;
        externalContext.redirect(externalContext.getRequestContextPath() + "/faces/login.xhtml");
        LOG.info("Logged out!");
    }

    public void changePassword() {
        if (loggedUser != null) {
            try {
                userController.changePassword(loggedUser.getUserName(), password, newPassword);
                JsfUtil.addErrorMessage(ResourceBundle.getBundle("/Bundle").getString("PasswordChangeSuccess"));
            } catch (Exception ex) {
                Logger.getLogger(UserAuth.class.getName()).log(Level.SEVERE, null, ex);
                 JsfUtil.addErrorMessage(ResourceBundle.getBundle("/Bundle").getString("PasswordChangeError"));
            }
        }

        username = null;
        password = null;
        newPassword = null;
    }

    public void validatePassword(ComponentSystemEvent event) {

        FacesContext fc = FacesContext.getCurrentInstance();

        UIComponent components = event.getComponent();

        // get password
        UIInput uiInputPassword = (UIInput) components.findComponent("newPassword");
        String password = uiInputPassword.getLocalValue() == null ? ""
                : uiInputPassword.getLocalValue().toString();
        String passwordId = uiInputPassword.getClientId();

        // get confirm password
        UIInput uiInputConfirmPassword = (UIInput) components.findComponent("newPasswordConfirm");
        String confirmPassword = uiInputConfirmPassword.getLocalValue() == null ? ""
                : uiInputConfirmPassword.getLocalValue().toString();

        // Let required="true" do its job.
        if (password.isEmpty() || confirmPassword.isEmpty()) {
            return;
        }

        if (!password.equals(confirmPassword)) {

            FacesMessage msg = new FacesMessage("Password must match confirm password");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            fc.addMessage(passwordId, msg);
            fc.renderResponse();

        }

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public User getLoggedUser() {
        return loggedUser;
    }

    public void setLoggedUser(User loggedUser) {
        this.loggedUser = loggedUser;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getOriginalURL() {
        return originalURL;
    }

    public void setOriginalURL(String originalURL) {
        this.originalURL = originalURL;
    }

}
