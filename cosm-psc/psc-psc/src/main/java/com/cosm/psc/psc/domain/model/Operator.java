package com.cosm.psc.psc.domain.model;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Collection;
import java.util.Objects;

/**
 *
 * @author 18359
 */
@Embeddable
public class Operator implements Serializable {

    private static final long serialVersionUID = 2643548471978688966L;    
   
    private String name;

   

    @NotNull
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Override
    public String toString() {
        return name;
    }
        
    
}
