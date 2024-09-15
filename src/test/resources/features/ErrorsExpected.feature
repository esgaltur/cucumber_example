Feature: Handle Error and Edge Cases

  Scenario: Invalid order status
    Given A new order with item "Iphone", quantity 1, price 1000.00, and invalid status "OLD"
    When I call the REST API to create the invalid order
    Then I should receive an error with http code 400 and message "Invalid format in field(s): status at line 1, column 55"

  Scenario: Invalid price format
    Given A new order with item "Laptop", quantity 2, price "INVALID", and status "NEW"
    When I call the REST API to create the invalid order
    Then I should receive an error with http code 400 and message "Invalid value for field(s): price"

  Scenario: Empty item field
    Given A new order with empty item field, quantity 1, price 500.00, and status "NEW"
    When I call the REST API to create the invalid order
    Then I should receive an error with http code 400 and message "Field 'orderModel.item': Item cannot be empty"
