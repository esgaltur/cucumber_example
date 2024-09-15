# Created by Root at 14/09/2024
Feature: Create and Retrieve Order

  Background: Create a new order when it does not exist
    Given A new order with item "Iphone", quantity 4, price 4.20, and status "NEW" is created
    When I call the REST API to create the order
    Then I should receive an order with a generated Id and http code 201

  Scenario: Retrieve the newly created order by Id
    Given The previously created order's URI is available with http code 201
    When I call the REST API using the URI
    Then I should retrieve the order details and the http code 200




