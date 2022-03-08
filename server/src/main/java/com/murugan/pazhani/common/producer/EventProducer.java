package com.murugan.pazhani.common.producer;

import com.murugan.pazhani.common.Event;
import com.murugan.pazhani.common.consumer.EventConsumerImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.util.Random;

@Component
public class EventProducer {
    private static final Logger logger = LoggerFactory.getLogger(EventProducer.class);

    private String[] eventTypes = {"sell", "buy", "market"};
    private String[] ticker = {"comm","hpe","msft","goog","fb"};

    @Autowired
    private EventConsumerImpl comsumer;

    private long counter;
    private Random tickerGen = new Random(23);
    private Random eventTypeGen = new Random(19);

    @Scheduled(fixedRate = 1000)
    public void fire() {
        long now = System.currentTimeMillis();

        comsumer.consume(new Event(now,
                eventTypes[eventTypeGen.nextInt(3)],
                ticker[tickerGen.nextInt(ticker.length)],
                ++counter, "do something"));
    }
}
