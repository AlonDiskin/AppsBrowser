Feature: Home feature end to end test scenarios

  Scenario: Browser ui is displayed
    Given User is in device home screen
    When User launch application
    Then Home screen should be displayed
    And Browser feature ui should be shown inside home screen
    When User navigates to settings feature
    Then Settings ui should be displayed inside home screen
