Feature: List device apps

  In order to see what applications are currently existing on my device
  As the user
  I want to get a listing of all installed applications

  Scenario: Device apps are displayed
    Given User has the next apps on device
      | name     | size | icon         |
      | Facebook | 45.7 | "fac_ic.jpg" |
      | YouTube  | 55.8 | "yt_ic.jpg"  |
      | Google   | 21.2 | "gog_ic.jpg" |
      | WhatsApp | 37.3 | "wha_ic.jpg" |
    When User opens browser screen
    Then All device apps should be displayed