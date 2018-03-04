package com.cosm.psc.production.domain.model;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class ProductionId {
	
	private UUID id;
	
	public ProductionId(String id) {
		this.id = UUID.fromString(id);
	}

	@Column(name = "ID")
	public UUID getId() {
		return id;
	}
	
	

}
