Feature: Browser feature e2e acceptance tests

  Scenario: Browser workflow
    Given User is in device home screen
    When User open application
    Then All device apps should be listed in home screen sorted by name
    When User clicks on app entry in middle of displayed list
    Then User should be redirected to app detail screen of device settings app