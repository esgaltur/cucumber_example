# Created by Root at 14/09/2024
Feature: GetOrderById

  Scenario: Order exist
  Given In database order with the id number 1 exist
  When REST API called with the id number 1
  Then The order returned, with order id "1" and order name "someOrderName1"
    # Enter steps here
