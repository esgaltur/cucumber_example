package com.sosnovich.cucumber_example.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableAutoConfiguration
@ComponentScan("com.sosnovich.cucumber_example.mappers")
@EntityScan(basePackages = {"com.sosnovich.cucumber_example.entity"})
@EnableJpaRepositories(basePackages = "com.sosnovich.cucumber_example.repository")
@EnableTransactionManagement
public class CucumberExampleConfiguration {
    }
