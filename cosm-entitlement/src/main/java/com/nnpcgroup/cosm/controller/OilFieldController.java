package com.nnpcgroup.cosm.controller;

import com.nnpcgroup.cosm.controller.util.JsfUtil;
import com.nnpcgroup.cosm.ejb.forecast.psc.OilFieldServices;
import com.nnpcgroup.cosm.entity.FiscalArrangement;
import com.nnpcgroup.cosm.entity.contract.OilField;
import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@Named("oilFieldController")
@SessionScoped
public class OilFieldController implements Serializable {

    private static final long serialVersionUID = 2912957338932205000L;
    private static final Logger LOG = Logger.getLogger(OilFieldController.class.getName());

    private OilField currentOilField;
    private List<OilField> oilFields;

    @EJB
    private OilFieldServices oilFieldBean;

    public OilFieldController() {

    }

    public OilField getCurrentOilField() {
        return currentOilField;
    }

    public void setCurrentOilField(OilField currentOilField) {
        this.currentOilField = currentOilField;
    }

    public List<OilField> getOilFields() {
        return oilFields;
    }

    public void setOilFields(List<OilField> oilFields) {
        this.oilFields = oilFields;
    }

    private void destroy() {
        try {
            oilFieldBean.remove(currentOilField);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("OilFieldDeleted"));
            LOG.log(Level.INFO, "Oil field deleted successfully {0}...", currentOilField);
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            LOG.log(Level.INFO, "Failed to delete Oil field {0}...", currentOilField);
        }
    }

    public OilField findOilField(Long id) {
        return oilFieldBean.find(id);
    }

    @FacesConverter(forClass = OilField.class)
    public static class OilFieldConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            OilFieldController controller = (OilFieldController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "oilFieldController");
            return controller.findOilField(getKey(value));
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
            if (object instanceof OilField) {
                OilField o = (OilField) object;
                return getStringKey(o.getId());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + FiscalArrangement.class.getName());
            }
        }

    }

}
