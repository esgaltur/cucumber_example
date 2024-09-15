package com.sosnovich.cucumber_example.cucumber.stepdefinitions;

import com.sosnovich.cucumber_example.entity.OrderEntity;
import com.sosnovich.cucumber_example.model.OrderStatusEnum;
import com.sosnovich.cucumber_example.repository.OrderRepository;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.math.BigDecimal;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@SpringBootTest
public class GetOrderByIdFromAPISteps {
    @Autowired
    private OrderRepository orderRepository;

    @LocalServerPort
    private int port;  // Spring Boot will assign a random port in the test context

    @Before
    public void setUp() {
        RestAssured.port = port; // Set RestAssured to use the random port
        RestAssured.basePath = "/";  // Optional, if your API has a base path
        RestAssured.baseURI = "http://localhost";
    }

    @Given("an order with the ID {int} exists in the database")
    public void an_order_with_the_id_exists_in_the_database(Integer int1) {
        OrderEntity order = new OrderEntity();
        order.setId(3L);
        order.setQuantity(1);
        order.setStatus(OrderStatusEnum.DELIVERED);
        order.setItem("Some Item");
        order.setPrice(BigDecimal.valueOf(4.45));
        // Save the order to the test database
        orderRepository.save(order); // Use your repository or service to interact with the DB
        Assertions.assertTrue(orderRepository.findById(3).isPresent());
    }

    @When("I call the REST API with the ID {int}")
    public void iCallTheRESTAPIWithTheID(int arg0) {
        // Perform the REST API call using RestAssured
        given()
                .when()
                .get("/order/" + arg0)  // Assuming this is your endpoint
                .then()
                .statusCode(200)  // Expect HTTP 200 OK response
                .log()
                .all();  // Log all response details
    }

    @Then("I should get an order with ID {int} and item {string} and price {double}")
    public void iShouldGetAnOrderWithIDAndName(int arg0, String arg1, double arg2) {
        given()
                .when()
                .get("/order/" + arg0)
                .then()
                .statusCode(200)
                .log().all()  // Log all response details
                .body("id", equalTo(arg0))  // Check that the ID matches
                .body("item", equalTo(arg1))  // Check that the name matches
                .body("price", equalTo(new BigDecimal(arg2).floatValue()));
    }
}
