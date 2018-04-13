/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosm.common.event.util;

import com.cosm.common.event.CosmEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;
import org.apache.kafka.common.serialization.Serializer;

/**
 *
 * @author maliska
 */
public class CosmEventSerializer implements Serializer<CosmEvent> {

    @Override
    public void configure(Map map, boolean bln) {

    }

    @Override
    public byte[] serialize(String string, CosmEvent event) {
        byte[] retVal = null;
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            retVal = objectMapper.writeValueAsString(event).getBytes();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return retVal;
    }

    @Override
    public void close() {

    }

}
