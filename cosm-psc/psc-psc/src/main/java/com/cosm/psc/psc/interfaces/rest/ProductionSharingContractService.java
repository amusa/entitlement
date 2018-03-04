package com.cosm.psc.psc.interfaces.rest;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.cosm.common.domain.model.ProductionSharingContractId;
import com.cosm.common.util.DateUtil;
import com.cosm.psc.psc.domain.model.ProductionSharingContract;
import com.cosm.psc.psc.domain.model.ProductionSharingContractRepository;


@Stateless
@Path("/psc")
public class ProductionSharingContractService{
	
	@Inject
	private ProductionSharingContractRepository pscRepository;
     
	@Inject
	private ProductionService productionService;
	
	@GET
	@Path("/concession")
    @Produces({MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN})
	public double concessionRental(@QueryParam("id") String id,
			@QueryParam("year") Integer year, 
			@QueryParam("month") Integer month) {
		
		ProductionSharingContractId pscId = new ProductionSharingContractId(id);
		ProductionSharingContract psc = pscRepository.productionSharingContractOfId(pscId);
    	double concessionRental = 0;

        if (productionService.isFirstProductionOfYear(pscId, year, month)) {
            concessionRental = psc.getOmlAnnualConcessionRental();
            if (DateUtil.getYearOfDate(psc.getFirstOilDate()) == year) {
                concessionRental += psc.getOplTotalConcessionRental();
            }
        }
        
        return concessionRental;
    }	
	
}
