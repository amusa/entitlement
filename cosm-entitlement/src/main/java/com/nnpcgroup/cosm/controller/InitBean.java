/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.controller;

import com.nnpcgroup.cosm.ejb.CompanyBean;
import com.nnpcgroup.cosm.entity.Company;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Named;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author 18359
 */
@Startup
@Singleton
public class InitBean {

    private static final Logger LOG = Logger.getLogger(InitBean.class.getName());

    @EJB
    CompanyBean companyBean;

    private Company company;

    /**
     * Creates a new instance of ApplicationController
     */
    @PostConstruct
    public void init() {
        String defaultCompany;
        FacesContext ctx = FacesContext.getCurrentInstance();
        defaultCompany
                = ctx.getExternalContext().getInitParameter("DEFAULT_COMPANY");

        LOG.log(Level.INFO, "Default company is {0}...", defaultCompany);

        company = companyBean.findByCompanyName(defaultCompany);

        if (company == null) {
            company = new Company();
            company.setName(defaultCompany);
            companyBean.create(company);
            LOG.log(Level.INFO, "Default company entity not created. Entity created successfully...");
        }
    }

    public Company getDefaultCompany() {
        return company;
    }

}
