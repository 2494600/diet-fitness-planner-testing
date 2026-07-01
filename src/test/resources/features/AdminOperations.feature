Feature: Admin Authorization and Tab Sub-views Lifecycle

  Background:
    Given I am on the application authentication login interface

  Scenario: SM_TC_08 - Verify admin login successfully
    When I input administrative credentials with username "admin" and password "admin123"
    Then I should be rerouted cleanly onto the "/admin" dashboard panel index view

  Scenario Outline: SM_TC_09 - Verify all administration tabs toggle state active
    Given I input administrative credentials with username "admin" and password "admin123"
    Then I should be rerouted cleanly onto the "/admin" dashboard panel index view
    When I click the administrative "<var>" workspace tab selector
    Then The matching sub-view tab layout boundary must be marked active for "<var>"
    Examples:
      | var        |
      | Meal Plans |
      | Workouts   |
      | Users      |

  Scenario: SM_TC_10 - Verify administrative session dismissal
    Given I input administrative credentials with username "admin" and password "admin123"
    Then I should be rerouted cleanly onto the "/admin" dashboard panel index view
    When I close the administrative panel via logout
    Then My active session tokens must clear and redirect me back to the root landing path