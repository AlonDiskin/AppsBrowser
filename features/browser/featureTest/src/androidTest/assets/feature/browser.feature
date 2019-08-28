Feature: Browser

  In order to get information about my installed apps
  As the user
  I want to be able to browse through all me device's applications

  Background:
    Given User has the next apps on device
      | name               | size | icon                                      | id |
      | Facebook           | 45.7 | file:///android_asset/facebookicon.png    | 1  |
      | YouTube            | 55.8 | file:///android_asset/youtubeicon.png     | 2  |
      | Google             | 21.2 | file:///android_asset/googleicon.png      | 3  |
      | WhatsApp           | 37.3 | file:///android_asset/whatsappicon.png    | 4  |
      | Gmail              | 17.8 | file:///android_asset/gmailicon.png       | 5  |
      | Facebook Messenger | 40.7 | file:///android_asset/facebookmesicon.png | 6  |
      | Google Maps        | 17.8 | file:///android_asset/gmapsicon.png       | 7  |
      | AccuWeather        | 38.4 | file:///android_asset/accuweathericon.png | 8  |
      | Google Music       | 15.2 | file:///android_asset/googlemusicicon.png | 9  |
      | Amazon             | 75.6 | file:///android_asset/amazonicon.png      | 10 |
      | Airbnb             | 65.6 | file:///android_asset/airbnbicon.png      | 11 |
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

  @apps-search
  Scenario Outline: Apps search performed
    When User performs search with "<query>" query
    Then Only apps that contains "<query>" in name are displayed sorted and ordered by default values
    When User sort apps by "<sort>" in "<order>" order
    Then Apps that contains "<query>" in name are displayed sorted by "<sort>" in "<order>" order
    When User close search
    Then All apps should be shown according to last sorting values
    Examples:
      | query | sort       | order       |
      | g     | size       | ascending   |
      | A     | size       | ascending   |
      | Face  | name       | descending  |
      | goo   | name       | ascending   |