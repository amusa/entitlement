package com.nnpcgroup.comd.cosm.entitlement.controller;

import com.nnpcgroup.comd.cosm.entitlement.controller.util.JsfUtil;
import com.nnpcgroup.comd.cosm.entitlement.ejb.ContractStreamBean;
import com.nnpcgroup.comd.cosm.entitlement.entity.ContractStream;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.model.DataModel;
import javax.faces.model.SelectItem;

@Named("contractStreamController")
@SessionScoped
public class ContractStreamController implements Serializable {

    private static final long serialVersionUID = 3964009055767422659L;

    private ContractStream current;
    private DataModel items = null;
    @EJB
    private ContractStreamBean contractStreamBean;
    
    private int selectedItemIndex;

    public ContractStreamController() {
    }

    public ContractStream getSelected() {
        if (current == null) {
            current = new ContractStream();
            selectedItemIndex = -1;
        }
        return current;
    }

    private ContractStreamBean getContractStreamBean() {
        return contractStreamBean;
    }
    
    
    
    public SelectItem[] getItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(contractStreamBean.findAll(), false);
    }

    public SelectItem[] getItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(contractStreamBean.findAll(), true);
    }

    public ContractStream getContractStream(java.lang.Integer id) {
        return contractStreamBean.find(id);
    }

    @FacesConverter(forClass = ContractStream.class)
    public static class ContractStreamControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            ContractStreamController controller = (ContractStreamController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "contractStreamController");
            return controller.getContractStream(getKey(value));
        }

        java.lang.Integer getKey(String value) {
            java.lang.Integer key;
            key = Integer.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Integer value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof ContractStream) {
                ContractStream o = (ContractStream) object;
                return getStringKey(o.getId());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + ContractStream.class.getName());
            }
        }

    }

}
