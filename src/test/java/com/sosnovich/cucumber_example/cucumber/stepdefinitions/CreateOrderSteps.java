package com.sosnovich.cucumber_example.cucumber.stepdefinitions;
import com.sosnovich.cucumber_example.entity.OrderEntity;
import com.sosnovich.cucumber_example.model.OrderStatusEnum;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;

import java.math.BigDecimal;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CreateOrderSteps {

    private static final OrderEntity order = new OrderEntity();
    private static String orderUri;
    private static Response orderCreateResponse;

    @LocalServerPort
    private int port; // Spring Boot will assign a random port in the test context

    @Before
    public void setUp() {
        RestAssured.port = port; // Set RestAssured to use the random port
        RestAssured.baseURI = "http://localhost"; // Ensure base URI points to the local server
    }

    @Given("^A new order with item \"([^\"]*)\", quantity (\\d+), price (\\d+\\.\\d+), and status \"([^\"]*)\" is created$")
    public void newOrderWithItemQuantityPriceStatus(String item, int quantity, double price, String status) {
        order.setItem(item);
        order.setQuantity(quantity);
        order.setPrice(BigDecimal.valueOf(price));
        order.setStatus(OrderStatusEnum.valueOf(status));
    }

    @When("I call the REST API to create the order")
    public void i_call_the_rest_api_to_create_the_order() {
        orderCreateResponse = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(order)
                .when()
                .post("/order");
    }

    @Then("I should receive an order with a generated Id and http code {int}")
    public void i_should_receive_an_order_with_a_generated_Id_and_http_code(Integer expectedStatusCode) {
        orderCreateResponse.then()
                .statusCode(expectedStatusCode) // Expect HTTP 201 Created
                .log().all() // Log all response details
                .header("Location", notNullValue()); // Ensure Location header is present

        // Extract the URI of the created resource from the Location header
        orderUri = orderCreateResponse.getHeader("Location");
    }

    @Given("The previously created order's URI is available with http code {int}")
    public void the_previously_created_order_s_uri_is_available(Integer expectedStatusCode) {
        orderCreateResponse.then()
                .statusCode(expectedStatusCode) // Ensure HTTP 201 Created
                .log().all();
    }

    @When("I call the REST API using the URI")
    public void i_call_the_rest_api_using_the_uri() {
        // Ensure that the URI was previously set
        if (orderUri == null) {
            throw new IllegalStateException("Order URI is not available");
        }

        // Call the REST API using the stored URI
        given()
                .when()
                .get(orderUri) // Use the previously stored URI
                .then()
                .statusCode(200) // Expect 200 OK
                .log().all(); // Log the response details for debugging
    }

    @Then("I should retrieve the order details and the http code {int}")
    public void i_should_retrieve_the_order_details_and_http_code(Integer expectedStatusCode) {
        given()
                .when()
                .get(orderUri) // Use the stored URI to fetch the order details
                .then()
                .statusCode(expectedStatusCode) // Expect 200 OK
                .log().all() // Log all response details
                .body("id", notNullValue())
                .body("item", notNullValue())
                .body("quantity", notNullValue())
                .body("price", notNullValue())
                .body("status", notNullValue());
    }
}
