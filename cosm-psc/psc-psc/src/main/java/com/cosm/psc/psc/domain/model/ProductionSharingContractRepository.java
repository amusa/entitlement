package com.cosm.psc.psc.domain.model;

import java.util.List;

import com.cosm.common.domain.model.ProductionSharingContractId;
import com.cosm.common.domain.repository.Repository;

public interface ProductionSharingContractRepository {	
	
	void add(ProductionSharingContract psc);

    void save(ProductionSharingContract psc);

    void remove(ProductionSharingContract psc);    
        
    List<ProductionSharingContract> allProductionSharingContracts();
    
    ProductionSharingContract productionSharingContractOfId(ProductionSharingContractId pscId); 
    
    List<ProductionSharingContract> productionSharingContracts ();
    
    ProductionSharingContractId nextContractId();

}
