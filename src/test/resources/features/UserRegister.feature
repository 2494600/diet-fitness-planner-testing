Feature: New User Account Registration

  Scenario: SM_TC_03 - Verify registration form handles valid submissions
    Given I navigate to the user registration route portal
    When I submit valid personal details with a unique username, password "Pass@123", age "25", height "175", weight "70", level "BEGINNER", and goal "OVERALL_HEALTH"
    Then I should be securely redirected and synchronized to the user "/dashboard" panel