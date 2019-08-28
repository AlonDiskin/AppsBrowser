Feature: Application features e2e tests

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
    When User searches for apps that begins with letter 'a'
    Then All apps that begin with letter 'a' displayed sorted by size in descending order

  @settings
  Scenario: Settings workflow
    And Navigates to settings screen
    And Set theme to dark
    Then App visual theme should be changed to dark
    When User exist app and returns
    Then App theme should be dark

 @app-workflow
 Scenario: User uses apps
   And User sort apps by size in ascending order
   Then Apps should be displayed sorted by size in ascending order
   When User performs a search with query 'go'
   And Changes app theme to dark
   When User return to browser screen
   Then All apps containing 'go' in name should be displayed
   When user rotates the app
   And System kill and recreates app
   Then All apps containing 'go' in name should be displayed
   And app theme should be dark