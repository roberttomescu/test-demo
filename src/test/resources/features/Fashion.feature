Feature: Fashion

  Background:
    Given I setup driver

  Scenario Outline: AutomationPractice.com - Buy dress
    And I go to "http://automationpractice.com"
    When I search for "<item>"
    Then I check that search results have "<item>"
    And I quit the driver
    Examples:
      | item  |
      | shirt |
      | dress |

