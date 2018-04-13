/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosm.common.event.util;

import com.cosm.common.event.CosmEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;
import org.apache.kafka.common.serialization.Deserializer;

/**
 *
 * @author maliska
 */
public class CosmEventDeserializer implements Deserializer<CosmEvent> {

    @Override
    public void configure(Map map, boolean bln) {

    }

    @Override
    public void close() {

    }

    @Override
    public CosmEvent deserialize(String topic, byte[] bytes) {
        ObjectMapper mapper = new ObjectMapper();
        CosmEvent event = null;
        try {
            event = mapper.readValue(bytes, CosmEvent.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return event;
    }

}
