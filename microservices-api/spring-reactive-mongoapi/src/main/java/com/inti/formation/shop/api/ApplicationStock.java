package com.inti.formation.shop.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@SpringBootConfiguration
@EnableAutoConfiguration
@ComponentScan
@EnableReactiveMongoRepositories // activation de spring mongo reactive
public class ApplicationStock {
    public static void main(String[] args) {
        SpringApplication.run(ApplicationStock.class, args);
    }

}
