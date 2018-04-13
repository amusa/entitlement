package com.cosm.psc.psc.domain.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

@Embeddable
public class ContractTitle implements Serializable {

    private String title;

    public ContractTitle() {
    }

    
    public ContractTitle(String title) {
        this.title = title;
    }

    @NotNull
    @Column(name = "TITLE")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
