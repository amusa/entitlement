package com.nnpcgroup.cosm.controller;

import com.nnpcgroup.cosm.controller.util.JsfUtil;
import com.nnpcgroup.cosm.controller.util.JsfUtil.PersistAction;
import com.nnpcgroup.cosm.ejb.lifting.LiftingServices;
import com.nnpcgroup.cosm.ejb.lifting.PscLiftingServices;
import com.nnpcgroup.cosm.entity.ProductionSharingContract;
import com.nnpcgroup.cosm.entity.lifting.Lifting;
import com.nnpcgroup.cosm.entity.lifting.PscLifting;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.security.Principal;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Named("liftingController")
@SessionScoped
public class LiftingController implements Serializable {

    private static final Logger LOG = Logger.getLogger(LiftingController.class.getName());

    private static final long serialVersionUID = 3411266588734031876L;

    @EJB
    private PscLiftingServices liftingBean;

    @Inject
    Principal principal;

    private PscLifting currentPscLifting;
    private List<PscLifting> pscLiftings;
    private ProductionSharingContract currentPsc;
    private Date liftingFromDate;
    private Date liftingToDate;

    public LiftingController() {
    }

    public PscLifting getCurrentPscLifting() {
        return currentPscLifting;
    }

    public void setCurrentPscLifting(PscLifting currentPscLifting) {
        this.currentPscLifting = currentPscLifting;
    }

    public List<PscLifting> getpscLiftings() {
        return pscLiftings;
    }

    public void setPscLiftings(List<PscLifting> pscLiftings) {
        this.pscLiftings = pscLiftings;
    }

   @Temporal(TemporalType.DATE)
    public Date getLiftingFromDate() {
        return liftingFromDate;
    }

    public void setLiftingFromDate(Date liftingFromDate) {
        this.liftingFromDate = liftingFromDate;
    }

    @Temporal(TemporalType.DATE)
    public Date getLiftingToDate() {
        return liftingToDate;
    }

    public void setLiftingToDate(Date liftingToDate) {
        this.liftingToDate = liftingToDate;
    }

    public ProductionSharingContract getCurrentPsc() {
        return currentPsc;
    }

    public void setCurrentPsc(ProductionSharingContract currentPsc) {
        this.currentPsc = currentPsc;
    }

    protected void setEmbeddableKeys() {

    }

    protected void initializeEmbeddableKey() {
        //currentProdCost.setCurrentUser(principal.getName());
    }

    private LiftingServices getFacade() {
        return liftingBean;
    }

    public void prepareCreate() {
        currentPscLifting = new PscLifting();//TODO:
    }

    public void prepareUpdate(PscLifting lifting) {
        this.currentPscLifting = lifting;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("LiftingCreated"));
        if (!JsfUtil.isValidationFailed()) {
            reset();
            loadLiftings();
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("LiftingUpdated"));
    }

    public void cancel() {
        reset();
    }

    public void reset() {
        currentPscLifting = null;
        pscLiftings = null;
        loadLiftings();
    }

    public void loadLiftings() {
        if (liftingFromDate != null && liftingToDate != null) {
            if (currentPsc == null) {
                pscLiftings = liftingBean.find(liftingFromDate, liftingToDate);
            } else {
                pscLiftings = liftingBean.find(currentPsc, liftingFromDate, liftingToDate);
            }
        }
    }

    public void destroy(PscLifting lifting) {
        setCurrentPscLifting(lifting);
        destroy();
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("LiftingDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            reset();
            loadLiftings();
        }
    }

    private void persist(PersistAction persistAction, String successMessage) {
        if (currentPscLifting != null) {

            try {
                if (persistAction != PersistAction.DELETE) {
                    getFacade().edit(currentPscLifting);
                } else {
                    getFacade().remove(currentPscLifting);
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

}
