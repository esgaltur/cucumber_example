package com.sosnovich.cucumber_example.cucumber.stepdefinitions;

import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ErrorsExpectedSteps {
    Map<String, Object> orderMap = new HashMap<>();
    private static Response orderCreateResponse;

    @LocalServerPort
    private int port; // Spring Boot will assign a random port in the test context

    @Before
    public void setUp() {
        RestAssured.port = port; // Set RestAssured to use the random port
        RestAssured.baseURI = "http://localhost"; // Ensure base URI points to the local server
        orderMap.clear();
    }

    @Given("^A new order with item \"([^\"]*)\", quantity (\\d+), price (\\d+\\.\\d+), and invalid status \"([^\"]*)\"$")
    public void a_new_order_with_invalid_status(String item, int quantity, double price, String status) {
        // Use a Map to represent JSON object

        orderMap.put("item", item);
        orderMap.put("quantity", quantity);
        orderMap.put("price", price);
        orderMap.put("status", status);  // Invalid status
    }

    @When("I call the REST API to create the invalid order")
    public void i_call_the_rest_api_to_create_the_invalid_order() {
        orderCreateResponse = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(orderMap)
                .post("/order");
    }


    @Given("^A new order with item \"([^\"]*)\", quantity (\\d+), price \"([^\"]+)\", and status \"([^\"]*)\"$")
    public void a_new_order_with_invalid_price(String item, int quantity, String price, String status) {
        // Using an invalid price string to trigger an error

        orderMap.put("item", item);
        orderMap.put("quantity", quantity);
        orderMap.put("price", price);
        orderMap.put("status", status);  // Invalid status
    }


    @Then("I should receive an error with http code {int} and message {string}")
    public void i_should_receive_an_error_with_http_code_and_invalid_message(int expectedStatusCode, String expectedMessage) {
        orderCreateResponse.then()
                .statusCode(expectedStatusCode)
                .body("details", equalTo(expectedMessage)).log().all();  // Check the error message
    }

    @Given("^A new order with empty item field, quantity (\\d+), price (\\d+\\.\\d+), and status \"([^\"]*)\"$")
    public void a_new_order_with_empty_item_field(int quantity, double price, String status) {
        orderMap.put("item", null);
        orderMap.put("quantity", quantity);
        orderMap.put("price", price);
        orderMap.put("status", status);  // Invalid status
    }


}
