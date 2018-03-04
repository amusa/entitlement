package com.cosm.common.domain.model;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class ProductionSharingContractId implements Serializable {	
	private static final long serialVersionUID = 1L;
	private UUID id;
	
	public ProductionSharingContractId(String id) {
		this.id = UUID.fromString(id);
	}
		
	@Column(name = "ID")
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProductionSharingContractId other = (ProductionSharingContractId) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

    

}
