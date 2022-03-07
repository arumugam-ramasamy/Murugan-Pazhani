package com.murugan.pazhani.producer;

import com.murugan.pazhani.Event;
import com.murugan.pazhani.consumer.EventConsumerImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.util.Random;

import java.text.SimpleDateFormat;
import java.util.Date;

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
                eventTypes[eventTypeGen.nextInt(0,3)],
                ticker[tickerGen.nextInt(0, ticker.length)],
                ++counter, "do something"));
    }
}
