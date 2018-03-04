package com.cosm.psc.entitlement.royalty.domain.model;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

@Embeddable
public class RoyaltyViewId {
	private UUID id;
	
	public RoyaltyViewId(String id) {
		setId(UUID.fromString(id));
	}
	
	@NotNull
	@Column(name="ID")
	public UUID getId() {
		return id;
	}

	private void setId(UUID id) {
		this.id = id;
	}
	
	

}
