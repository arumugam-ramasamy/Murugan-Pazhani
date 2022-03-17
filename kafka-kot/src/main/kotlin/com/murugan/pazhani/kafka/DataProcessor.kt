package com.murugan.pazhani.kafka

import org.apache.logging.log4j.kotlin.Logging
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Bean
import java.util.*


@SpringBootApplication
class DataProcessor(val appConfig: AppConfig):Logging {
    @Bean
    fun commandLineRunner(ctx: ApplicationContext): CommandLineRunner? {
        return CommandLineRunner { args: Array<String?>? ->
            logger.info("Let's inspect the beans provided by Spring Boot:")
            val beanNames = ctx.beanDefinitionNames
            Arrays.sort(beanNames)
            for (beanName in beanNames) {
                logger.info(beanName)
            }

            appConfig.getAsyncExecutor()
            while (true) {
                println("DataProcessor heartbeat...")
                Thread.sleep(30000)
            }
        }
    }
}

fun main(args: Array<String>) {
    runApplication<DataProcessor>(*args)
}