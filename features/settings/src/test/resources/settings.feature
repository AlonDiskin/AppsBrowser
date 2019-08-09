Feature: Settings

  In order to customize application behavior
  As the user
  I want to be able to configure application settings

  Scenario: App theme setting is displayed
    Given App has the next theme preference:
      | theme |
      | dark  |
      | light |
    When User open settings screen
    And Open theme setting menu
    Then Existing preference are displayed