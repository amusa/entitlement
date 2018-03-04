package com.cosm.common.event;

import com.cosm.common.event.kafka.EventProducer;

public interface Publisher<T> {

	void publish(T object, EventProducer producer);
	
}
