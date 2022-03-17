package com.murugan.pazhani.kafka

import org.apache.logging.log4j.kotlin.Logging
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Configuration
import org.springframework.core.task.TaskExecutor
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor

@Configuration
@EnableConfigurationProperties
@EnableScheduling
class AppConfig(
    @Value("\${processor.taskExecutor.corePoolSize}") private val corePoolSize: Int,
    @Value("\${processor.taskExecutor.maxPoolSize}") private val maxPoolSize: Int,
    @Value("\${processor.taskExecutor.queueCapacity}") private val queueCapacity: Int):
    Logging {
        fun getAsyncExecutor():TaskExecutor {
            val taskExecutor = ThreadPoolTaskExecutor()
            taskExecutor.corePoolSize = corePoolSize
            taskExecutor.maxPoolSize = maxPoolSize
            taskExecutor.setQueueCapacity(queueCapacity)

            logger.info {"Created taskPoolExecutor: ${taskExecutor.maxPoolSize}"}
            return taskExecutor
        }
}