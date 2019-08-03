Feature: Browser feature e2e acceptance tests

  Scenario: Browser workflow
    Given User is in device home screen
    When User open application
    Then Then all device apps should be listed in home screen