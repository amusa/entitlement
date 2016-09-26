package com.nnpcgroup.cosm.entity.forecast.jv;

import org.eclipse.persistence.config.DescriptorCustomizer;
import org.eclipse.persistence.descriptors.ClassDescriptor;
import org.eclipse.persistence.history.HistoryPolicy;

/**
 * Created by maliska on 9/25/16.
 */
public class ForecastCustomizer implements DescriptorCustomizer {
    @Override
    public void customize(ClassDescriptor classDescriptor) throws Exception {
        HistoryPolicy policy = new HistoryPolicy();
        policy.addStartFieldName("START_DATE");
        policy.addEndFieldName("END_DATE");
        policy.addHistoryTableName("FORECAST_HISTORY");
        classDescriptor.setHistoryPolicy(policy);
    }
}
