Feature: User Authentication Session Termination

  Scenario: SM_TC_07 - Verify user logout action clears state and exits cleanly
    Given I am on the application authentication login interface
    When I log in using the newly registered account credentials with password "Pass@123"
    Then I should be securely redirected and synchronized to the user "/dashboard" panel
    When I click the navigation header session logout option
    Then My active session tokens must clear and redirect me back to the root landing path