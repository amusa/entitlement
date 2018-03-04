package com.cosm.account.domain.model;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class ProductionCostId {
	private UUID id;
	
	public ProductionCostId(String id) {
		this.id = UUID.fromString(id);
	}
	
	@Column(name="ID")
	public UUID getId() {
		return id;
	}

}
