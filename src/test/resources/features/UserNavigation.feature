Feature: User Dashboard Navigation Menu Routing

  Scenario: SM_TC_06 - Verify workspace navbar router link sanity
    Given I am on the application authentication login interface
    When I log in using the newly registered account credentials with password "Pass@123"
    Then I should be securely redirected and synchronized to the user "/dashboard" panel
    When I navigate across "/history", "/profile", and back to "/dashboard" navigation links
    Then Each target route application path parameter must load safely