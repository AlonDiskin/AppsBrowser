Feature: Application features e2e acceptance tests

  Background: User is in device home screen
    When User launch application

  @home
  Scenario: Home workflow
    Given User is in device home screen
    When User launch application
    Then Home screen should be displayed
    And Browser feature ui should be shown inside home screen
    When User navigates to settings screen
    Then Settings ui should be shown as a composition in home screen

  @browser
  Scenario: Browser workflow
    Given User is in device home screen
    When User open application
    Then All device apps should be listed in home screen sorted by name
    When User clicks on app entry in middle of displayed list
    Then User should be redirected to app detail screen of device settings app

  @settings
  Scenario: Settings feature workflow
    And Navigates to settings screen
    And Set theme to dark
    Then App visual theme should be changed to dark
    When User exist app and returns
    Then App theme should be dark