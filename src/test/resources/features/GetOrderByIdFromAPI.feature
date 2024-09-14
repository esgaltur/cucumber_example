# Created by Root at 14/09/2024
Feature: GetOrderById

  Scenario: Order exists
    Given an order with the ID 3 exists in the database
    When I call the REST API with the ID 3
    Then I should get an order with ID 3 and item "Some Item" and price 4.45

