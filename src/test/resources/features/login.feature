Feature: Login Functionality

  Scenario Outline: Verify user can login using excel data

    Given User launches the application
    When User enters username "<username>"
    And User enters password "<password>"
    And User clicks login button
    Then User should be redirected successfully

    Examples:
      | username | password |
      | admin    | admin123 |
      | user1    | pass123  |