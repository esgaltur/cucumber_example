package com.sosnovich.cucumber_example.cucumber.hooks;

import io.cucumber.java.Before;
import io.cucumber.java.Scenario;

public class ExampleSystemHooks {


    @Before
    public void beforeScenario(Scenario scenario) {
        System.out.println("Before Scenario" + scenario.getName());
    }

    @Before
    public void afterScenario(Scenario scenario) {
        System.out.println("After Scenario: " + scenario.getName());
    }
}
