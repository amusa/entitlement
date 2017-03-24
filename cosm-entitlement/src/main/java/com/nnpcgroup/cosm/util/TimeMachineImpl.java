package com.nnpcgroup.cosm.util;

import javax.enterprise.context.Dependent;
import java.io.Serializable;
import java.time.Instant;

/**
 * Created by Ayemi on 21/03/2017.
 */
@Dependent
public class TimeMachineImpl implements TimeMachine, Serializable {
    private Instant startTime, finishTime;


    @Override
    public void start() {
        startTime = Instant.now();
        finishTime = null;
    }

    @Override
    public void finish() {
        finishTime = Instant.now();
    }

    @Override
    public long duration() {
        return DateUtil.secondsDiff(startTime, finishTime);
    }
}
