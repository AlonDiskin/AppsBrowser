Feature: Home feature end to end test scenarios

  Scenario: Home workflow
    Given User is in device home screen
    When User launch application
    Then Home screen should be displayed
    And Browser feature ui should be shown inside home screen
