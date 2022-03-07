package com.murugan.pazhani.consumer;

import com.murugan.pazhani.Event;
import com.murugan.pazhani.EventConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Component("eventConsumer")
public class EventConsumerImpl implements EventConsumer {
    private static Logger logger = LoggerFactory.getLogger(EventConsumerImpl.class);
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    private Random random = new Random(123);
    private Map<Long, Boolean> waiting = new HashMap<>();

    @Autowired
    TaskExecutor taskExecutor;

    @Override
    public void consume(Event event) {
        logger.info("active count: {}, current pool size: {}, still running: {}",
                ((ThreadPoolTaskExecutor)taskExecutor).getActiveCount(),
                ((ThreadPoolTaskExecutor)taskExecutor).getPoolSize(), waiting.size());
        taskExecutor.execute(() -> {
            logger.info("{} -> start processing", event.id());
            waiting.put(event.id(), true);
            try {
                Thread.sleep(random.nextLong(2000, 10000));
            } catch (InterruptedException e) {
            }
            waiting.remove(event.id());
            logger.info("{} -> end processing", event.id());
        });
    }
}

