package com.cosm.shipping.lifting.domain.model;

import java.util.UUID;

import javax.persistence.Column;

public class LiftingId {
	
	private UUID id;
	
	public LiftingId(String id) {
		this.id = UUID.fromString(id);
	}
	
	@Column(name = "ID")    
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }


}
