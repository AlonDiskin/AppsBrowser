Feature: Browser

  In order to get information about my installed apps
  As the user
  I want to be able to browse through all me device's applications

  Background:
    Given User has the next apps on device
      | name     | size | icon                                   | id |
      | Facebook | 45.7 | file:///android_asset/facebookicon.png | 1  |
      | YouTube  | 55.8 | file:///android_asset/youtubeicon.png  | 2  |
      | Google   | 21.2 | file:///android_asset/twittericon.jpeg | 3  |
      | WhatsApp | 37.3 | file:///android_asset/whatsappicon.png | 4  |
    When User opens browser screen

  @list-apps
  Scenario: Device apps are displayed
    Then All device apps should be displayed sorted by name in ascending order

  @app-detail
  Scenario Outline: App detail displayed
    And User clicks on listed "<app>" entry
    Then User should be redirected to app detail in settings application
    Examples:
      | app      |
      | Facebook |
      | YouTube  |

  @provide-sorting
  Scenario Outline: Apps are sorted
    And Sorts apps by "<sort>" in "<order>" order
    Then Browser should display apps sorted by "<sort>" in "<order>" order
    Examples:
      | sort | order      |
      | name | ascending  |
      | name | descending |
      | size | ascending  |
      | size | descending |