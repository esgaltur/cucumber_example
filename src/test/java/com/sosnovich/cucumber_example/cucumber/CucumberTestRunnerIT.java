package com.sosnovich.cucumber_example.cucumber;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features",
        glue = {"com.sosnovich.cucumber_example.cucumber.stepdefinitions",
                "com.sosnovich.cucumber_example.cucumber.config",
                "com.sosnovich.cucumber_example.cucumber.hooks",

        },
        plugin = {"pretty", "html:target/cucumber-reports"}) // Output format)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CucumberTestRunnerIT {
}
