package com.nnpcgroup.cosm.controller;

import com.nnpcgroup.cosm.entity.Contract;
import com.nnpcgroup.cosm.controller.util.JsfUtil;
import com.nnpcgroup.cosm.controller.util.JsfUtil.PersistAction;
import com.nnpcgroup.cosm.ejb.contract.ContractServices;
import com.nnpcgroup.cosm.entity.CarryContract;
import com.nnpcgroup.cosm.entity.FiscalArrangement;
import com.nnpcgroup.cosm.entity.ModifiedCarryContract;
import com.nnpcgroup.cosm.entity.RegularContract;

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

@Named("contractController")
@SessionScoped
public class ContractController implements Serializable {

    private static final Logger LOG = Logger.getLogger(ContractController.class.getName());

    private static final long serialVersionUID = 3411266588734031876L;

    @EJB
    private ContractServices ejbFacade;

    private List<Contract> items = null;
    private Contract selected;
    private String contractType;
    private FiscalArrangement fiscalArrangement;

    public ContractController() {
    }

    public String getContractType() {
        return contractType;
    }

    public void setContractType(String contractType) {
        this.contractType = contractType;
    }

    public FiscalArrangement getFiscalArrangement() {
        LOG.log(Level.INFO, "Returning fiscal arrangement {0}...", fiscalArrangement);
        return fiscalArrangement;
    }

    public void setFiscalArrangement(FiscalArrangement fiscalArrangement) {
         LOG.log(Level.INFO, "Setting fiscal arrangement {0}...", fiscalArrangement);
        this.fiscalArrangement = fiscalArrangement;
    }

    public Contract getSelected() {
        return selected;
    }

    public void setSelected(Contract selected) {
        this.selected = selected;
        if (selected instanceof RegularContract) {
            contractType = "REG";
        } else if (selected instanceof CarryContract) {
            contractType = "CA";
        } else if (selected instanceof ModifiedCarryContract) {
            contractType = "MCA";
        }
    }

    public CarryContract getCarrySelected() {
        if (selected instanceof CarryContract) {
            return (CarryContract) selected;
        }
        return null;
    }

    public void setCarrySelected(CarryContract carrySelected) {
        if (carrySelected != null) {
            this.selected = carrySelected;
        }
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private ContractServices getFacade() {
        return ejbFacade;
    }

    public Contract prepareCreate() {
        selected = new RegularContract(); //TODO:evaluate type of contract first
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

    public Contract getContract(java.lang.Integer id) {
        return getFacade().find(id);
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
                case "REG":
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
            selected.setFiscalArrangement(fiscalArrangement);
        }
    }

    public void addContractFiscalArrangement(FiscalArrangement fa) {
        LOG.log(Level.INFO, "Adding Contract for fiscal arrangement {0}...", fa);
        setSelected(new RegularContract()); //Default contract
        setFiscalArrangement(fa);
    }

    @FacesConverter(forClass = Contract.class)
    public static class ContractControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            ContractController controller = (ContractController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "contractController");
            return controller.getContract(getKey(value));
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
            if (object instanceof Contract) {
                Contract o = (Contract) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Contract.class.getName()});
                return null;
            }
        }

    }

}
