package com.nnpcgroup.cosm.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Transient;

/**
 * Created by maliska on 8/24/16.
 */
@Embeddable
public class AreaSize implements Serializable {

    private Double oplContractArea;
    private Double omlContractArea;
    private Double oplRentalRate;
    private Double omlRentalRate;

    @Column(name = "OPL_CONTRACT_AREA")
    public Double getOplContractArea() {
        return oplContractArea;
    }

    public void setOplContractArea(Double oplContractArea) {
        this.oplContractArea = oplContractArea;
    }

    @Column(name = "OML_CONTRACT_AREA")
    public Double getOmlContractArea() {
        return omlContractArea;
    }

    public void setOmlContractArea(Double omlContractArea) {
        this.omlContractArea = omlContractArea;
    }

    @Column(name = "OPL_RENTAL_RATE")
    public Double getOplRentalRate() {
        return oplRentalRate;
    }

    public void setOplRentalRate(Double oplRentalRate) {
        this.oplRentalRate = oplRentalRate;
    }

    @Column(name = "OML_RENTAL_RATE")
    public Double getOmlRentalRate() {
        return omlRentalRate;
    }

    public void setOmlRentalRate(Double omlRentalRate) {
        this.omlRentalRate = omlRentalRate;
    }
}
