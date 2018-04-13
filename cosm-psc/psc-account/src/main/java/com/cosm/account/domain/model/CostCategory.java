/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosm.account.domain.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Ayemi
 */
@Entity
@Table(name = "COST_CATEGORY")
public class CostCategory implements Serializable {

    private String code;
    private String description;
    private List<CostItem> costItems;

    public CostCategory() {
    }

    public CostCategory(String code, String description) {
        this.code = code;
        this.description = description;
    }

    @Id
    @Column(name = "CODE")
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @NotNull
    @Column(name = "DESCRIPTION")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @OneToMany(mappedBy = "costCategory")
    public List<CostItem> getCostItems() {
        return costItems;
    }

    public void setCostItems(List<CostItem> costItems) {
        this.costItems = costItems;
    }

}