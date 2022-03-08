package com.murugan.pazhani.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CommandRunner implements ApplicationRunner {
    @Autowired
    private AppConfig appConfig;

    public static void main(String[] args) {
        SpringApplication.run(CommandRunner.class, args);
    }

    public void run(ApplicationArguments args) throws Exception {
        appConfig.getAsyncExecutor();
        while (true) {
            System.out.println("Runner heartbeat...");
            Thread.sleep(30000);
        }
    }
}
