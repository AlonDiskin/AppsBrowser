Feature: Browser

  In order to get information about my installed apps
  As the user
  I want to be able to browse through all me device's applications

  @list-apps
  Scenario: Device apps are displayed
  In order to see what applications are currently existing on my device
  As the user
  I want to get a listing of all installed applications

    Given User has the next apps on device
      | name     | size | icon         |
      | Facebook | 45.7 | "fac_ic.jpg" |
      | YouTube  | 55.8 | "yt_ic.jpg"  |
      | Google   | 21.2 | "gog_ic.jpg" |
      | WhatsApp | 37.3 | "wha_ic.jpg" |
    When User opens browser screen
    Then All device apps should be displayed