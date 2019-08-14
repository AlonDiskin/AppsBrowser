Feature: Application features e2e acceptance tests

  Background:
    Given User is in device home screen
    When User launch application

  @browser
  Scenario: Browser workflow
    Then All device apps should be listed in home screen sorted by name
    When User clicks on app entry in middle of displayed list
    Then User should be redirected to app detail screen of device settings app
    When User return to browser screen
    And Sort apps by size in descending order
    Then Apps should be displayed sorted by size in descending order

  @settings
  Scenario: Settings feature workflow
    And Navigates to settings screen
    And Set theme to dark
    Then App visual theme should be changed to dark
    When User exist app and returns
    Then App theme should be dark