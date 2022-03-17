package com.murugan.pazhani.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@EnableConfigurationProperties
@EnableScheduling
public class AppConfig {
    private static final Logger logger = LoggerFactory.getLogger(AppConfig.class);

    @Value("${taskExecutor.corePoolSize}")
    private int corePoolSize;
    @Value("${taskExecutor.maxPoolSize}")
    private int maxPoolSize;
    @Value("${taskExecutor.queCapacity}")
    private int queueCapacity;


    public TaskExecutor getAsyncExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(corePoolSize);
        taskExecutor.setMaxPoolSize(maxPoolSize);
        taskExecutor.setQueueCapacity(queueCapacity);

        logger.info("Created taskPoolExecutor: {}", taskExecutor.getMaxPoolSize());
        return taskExecutor;
    }

}
