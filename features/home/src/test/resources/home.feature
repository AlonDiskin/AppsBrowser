Feature: Home feature

  In order to facilitate app navigation
  As the user
  I want to have a main app home screen

  Scenario: Home shows browser
    Given User opened home screen
    Then Browser feature ui should be displayed in home screen

  Scenario: Home composite settings
    Given User is in home screen
    When User navigates to settings screen
    Then Settings screen should be displayed inside home screen
