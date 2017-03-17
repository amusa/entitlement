package com.nnpcgroup.cosm.entity.production.jv;

import org.eclipse.persistence.config.DescriptorCustomizer;
import org.eclipse.persistence.descriptors.ClassDescriptor;
import org.eclipse.persistence.history.HistoryPolicy;

/**
 * Created by maliska on 9/25/16.
 */
public class ProductionDetailCustomizer implements DescriptorCustomizer {
    @Override
    public void customize(ClassDescriptor classDescriptor) throws Exception {
        HistoryPolicy policy = new HistoryPolicy();
        policy.addStartFieldName("START_DATE");
        policy.addEndFieldName("END_DATE");
        policy.addHistoryTableName("PRODUCTION_DETAIL_HISTORY");
        classDescriptor.setHistoryPolicy(policy);
    }
}
