package com.cosm.psc.entitlement.taxoil.event.kafka;

import javax.json.bind.serializer.JsonbSerializer;
import javax.json.bind.serializer.SerializationContext;
import javax.json.stream.JsonGenerator;

import com.cosm.common.event.CosmEvent;

public class EventJsonbSerializer implements JsonbSerializer<CosmEvent> {

    @Override
    public void serialize(final CosmEvent event, final JsonGenerator generator, final SerializationContext ctx) {
        generator.write("class", event.getClass().getCanonicalName());
        generator.writeStartObject("data");
        ctx.serialize("data", event, generator);
        generator.writeEnd();
    }

}
