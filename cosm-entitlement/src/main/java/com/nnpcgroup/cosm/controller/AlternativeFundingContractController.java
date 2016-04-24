package com.nnpcgroup.cosm.controller;

import com.nnpcgroup.cosm.entity.contract.Contract;
import com.nnpcgroup.cosm.controller.util.JsfUtil;
import com.nnpcgroup.cosm.controller.util.JsfUtil.PersistAction;
import com.nnpcgroup.cosm.ejb.contract.ContractServices;
import com.nnpcgroup.cosm.ejb.contract.impl.ContractBean;
import com.nnpcgroup.cosm.entity.contract.CarryContract;
import com.nnpcgroup.cosm.entity.FiscalArrangement;
import com.nnpcgroup.cosm.entity.contract.ContractPK;
import com.nnpcgroup.cosm.entity.contract.ModifiedCarryContract;
import com.nnpcgroup.cosm.entity.contract.RegularContract;

import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.model.SelectItem;

@Named("afContractController")
@SessionScoped
public class AlternativeFundingContractController implements Serializable {

    private static final Logger LOG = Logger.getLogger(AlternativeFundingContractController.class.getName());

    private static final long serialVersionUID = 3411266588734031876L;

    @EJB
    private ContractServices ejbFacade;
    private List<Contract> items = null;
    private Contract selected;
    private String contractType;
    private FiscalArrangement fiscalArrangement;

    public AlternativeFundingContractController() {
    }

    public String getContractType() {
        return contractType;
    }

    public void setContractType(String contractType) {
        this.contractType = contractType;
    }

    public FiscalArrangement getFiscalArrangement() {
        return fiscalArrangement;
    }

    public void setFiscalArrangement(FiscalArrangement fiscalArrangement) {
        this.fiscalArrangement = fiscalArrangement;
    }

    public Contract getSelected() {
        return selected;
    }

    public void setSelected(Contract selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private ContractServices getFacade() {
        return ejbFacade;
    }

    public Contract prepareCreate() {
        selected = new RegularContract();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("ContractStreamCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("ContractStreamUpdated"));
    }

    public void cancel() {
        reset();
    }

    public void reset() {
        selected = null;
        items = null;
    }

    public void destroy(Contract cs) {
        setSelected(cs);
        destroy();
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("ContractStreamDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Contract> getItems() {
        items = getFacade().findAll();
        return items;
    }

    private void persist(PersistAction persistAction, String successMessage) {
        if (selected != null) {
            setEmbeddableKeys();
            try {
                if (persistAction != PersistAction.DELETE) {
                    getFacade().edit(selected);
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

    public Contract getContract(ContractPK pk) {
        return getFacade().find(pk);
    }

    public List<Contract> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public SelectItem[] getItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(getFacade().findAll(), true);
    }

    public void contractTypeSelected(AjaxBehaviorEvent event) {
        LOG.log(Level.INFO, "Contract Type Selected...{0}", contractType);
        if (null != contractType) {
            switch (contractType) {
                case "RG":
                    selected = new RegularContract();
                    break;
                case "MCA":
                    selected = new ModifiedCarryContract();
                    break;
                case "CA":
                    selected = new CarryContract();
                    break;
                default:
                    break;
            }
            ContractPK contractPK = new ContractPK();
            // contractPK.setCrudeType();
            contractPK.setFiscalArrangementId(fiscalArrangement.getId());
            selected.setContractPK(contractPK);
        }
    }

    public void addContract(FiscalArrangement fa) {
        setFiscalArrangement(fa);
    }

    @FacesConverter(forClass = Contract.class)
    public static class ContractStreamControllerConverter implements Converter {

        private static final String SEPARATOR = "#";
        private static final String SEPARATOR_ESCAPED = "\\#";

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            AlternativeFundingContractController controller = (AlternativeFundingContractController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "afContractController");
            return controller.getContract(getKey(value));
        }

        ContractPK getKey(String value) {
            ContractPK key;
            String values[] = value.split(SEPARATOR_ESCAPED);
            key = new ContractPK();
            key.setFiscalArrangementId(Long.valueOf(values[0]));
            key.setCrudeTypeCode(values[1]);
            return key;
        }

        String getStringKey(ContractPK value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value.getFiscalArrangementId());
            sb.append(SEPARATOR);
            sb.append(value.getCrudeTypeCode());
            return sb.toString();
        }

//        java.lang.Integer getKey(String value) {
//            java.lang.Integer key;
//            key = Integer.valueOf(value);
//            return key;
//        }
//
//        String getStringKey(java.lang.Integer value) {
//            StringBuilder sb = new StringBuilder();
//            sb.append(value);
//            return sb.toString();
//        }
        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Contract) {
                Contract o = (Contract) object;
                return getStringKey(o.getContractPK());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Contract.class.getName()});
                return null;
            }
        }

    }

}
