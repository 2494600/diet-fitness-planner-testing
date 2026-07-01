Feature: Public Landing Page Assets and Carousels

  Background:
    Given The application landing page is open

  Scenario: SM_TC_01 - Verify landing page components render properly
    Then The hero section banner container should be displayed
    And The features informational grid matrix should be visible
    And The main Call-To-Action entry buttons should be present

  Scenario: SM_TC_02 - Verify quote carousel text changes automatically
    When I read the initial text value of the quotes display panel
    Then The quote text should automatically rotate to a new value within 12 seconds