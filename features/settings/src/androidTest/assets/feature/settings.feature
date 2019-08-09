Feature: Settings

  In order to customize application behavior
  As the user
  I want to be able to configure application settings

  @customize-theme
  Scenario Outline: App theme setting is displayed
    Given User open settings screen
    And Open theme setting menu
    Then App has the next theme setting selections:
      | theme |
      | dark  |
      | light |
    When User selects a "<theme>" theme
    Then App visual theme should change to "<theme>"

    Examples:
      | theme  |
      | dark   |
      | light  |
