package com.cosm.common.domain.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class CrudeTypeId {
	private String code;
	
    @Column(name = "CODE")
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
