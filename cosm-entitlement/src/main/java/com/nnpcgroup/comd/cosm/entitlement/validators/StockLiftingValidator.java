/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.comd.cosm.entitlement.validators;

import com.nnpcgroup.comd.cosm.entitlement.controller.JvActualProductionController;
import com.nnpcgroup.comd.cosm.entitlement.entity.JvActualProduction;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 *
 * @author 18359
 */
@FacesValidator(value = "liftingValidator")
public class StockLiftingValidator implements Validator {

    @Override
    public void validate(FacesContext facesContext, UIComponent component, Object value) throws ValidatorException {

        JvActualProductionController jvActualController = (JvActualProductionController) facesContext.getApplication().getELResolver().
                getValue(facesContext.getELContext(), null, "jvActualController");

        JvActualProduction currentProduction = jvActualController.getCurrentProduction();

        Double openingStock = currentProduction.getOpeningStock();
        Double entitlement = currentProduction.getOwnShareEntitlement();
        Double lifting = currentProduction.getLifting();

        Double partnerOpeningStock = currentProduction.getPartnerOpeningStock();
        Double partnerEntitlement = currentProduction.getPartnerShareEntitlement();
        Double partnerLifting = currentProduction.getPartnerLifting();

        Double bucket = openingStock + entitlement + partnerOpeningStock + partnerEntitlement;

        if (bucket - lifting - partnerLifting < 0) {
            FacesMessage msg
                    = new FacesMessage("Stock Lifting validation failed!",
                            "Please check your availability and lifting volume");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);

            throw new ValidatorException(msg);
        }
    }

}
