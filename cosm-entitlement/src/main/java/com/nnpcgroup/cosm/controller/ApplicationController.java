/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.controller;

import com.nnpcgroup.cosm.ejb.CompanyBean;
import com.nnpcgroup.cosm.entity.Company;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author 18359
 */
@Named(value = "applicationController")
@ApplicationScoped
public class ApplicationController {

    @EJB
    CompanyBean companyBean;

    private Company company;

    /**
     * Creates a new instance of ApplicationController
     */
    public ApplicationController() {
        String defaultCompany;
        FacesContext ctx = FacesContext.getCurrentInstance();
        defaultCompany
                = ctx.getExternalContext().getInitParameter("DEFAULT_COMPANY");
        company = companyBean.findByCompanyName(defaultCompany);

        if (company == null) {
            company = new Company();
            company.setName(defaultCompany);
            companyBean.create(company);
        }
    }

    public Company getDefaultCompany() {
        return company;
    }

}
