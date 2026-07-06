Scenario Outline: SM_TC_04 & SM_TC_05 - Verify logged-in user dashboard data metrics
Given I am on the application authentication login interface
When I log in using excel user "<rowNumber>"
Then I should be securely redirected and synchronized to the user "/dashboard" panel
And The greeting banner text widget should contain "Hello"
And The user dashboard BMI value text display must read "22.86"
And The loaded meal planning task items collection must not be empty
And The loaded workouts routine scheduling logs must not be empty

Examples:
| rowNumber |
| 1         |
| 2         |