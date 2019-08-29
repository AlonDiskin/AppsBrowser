Feature: Settings

  In order to customize application behavior
  As the user
  I want to be able to configure application settings

  @customize-theme
  Scenario Outline: App theme is customized
    Given App theme is set as"<existing theme>"
    And User open settings screen
    And Open theme setting menu
    When User selects a "<selected theme>" theme
    Then App visual theme should change to "<selected theme>"
    Examples:
      | existing theme | selected theme |
      | light          | dark           |
      | dark           | light          |
