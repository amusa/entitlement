/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.controller;

import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author 18359
 */
@Named
@RequestScoped
public class LoginBean {
    @Size(min=4, max=10)
    @NotNull
    private String username;
     
    @Size(min=4, max=10)
    @NotNull
    private String password;
     
 
    public void setUsername(String name) {
        this.username = name;
    }
     
    public String getUsername() {
        return username;
    }
 
 
    public String getPassword() {
        return password;
    }
 
 
    public void setPassword(String password) {
        this.password = password;
    }
     
    public void login() {
        LOG.info("Logging in...");
        
        if ("BootsFaces".equalsIgnoreCase(username) && "rocks!".equalsIgnoreCase(password)) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Congratulations! You've successfully logged in.");
            FacesContext.getCurrentInstance().addMessage("loginForm:password", msg);
             
        } else {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "", "That's the wrong password. Hint: BootsFaces rocks!");
            FacesContext.getCurrentInstance().addMessage("loginForm:password", msg);
        }
    }
    private static final Logger LOG = Logger.getLogger(LoginBean.class.getName());
     
    public void forgotPassword() {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Default user name: BootsFaces");
        FacesContext.getCurrentInstance().addMessage("loginForm:username", msg);
        msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Default password: rocks!");
        FacesContext.getCurrentInstance().addMessage("loginForm:password", msg);
    }
}