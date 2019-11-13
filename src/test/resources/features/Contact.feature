Feature: Contact form

  Background:
    Given I setup driver
    And I go to "http://automationpractice.com"
    When I click on 'Contact us'

  Scenario Outline: Contact customer service
    And I select Subject Heading "<subject heading>"
    And I enter email address "<email address>"
    And I enter message "<message text>"
    And I click 'Send' expecting success
    And I check the testbackpack for message to be equal to "<message text>"
    Then Success message "<success message>" appears

    Examples:
      | subject heading  | email address         | message text | success message                                      |
      | Webmaster        | test@devschooling.com | text         | Your message has been successfully sent to our team. |
      | Customer service | test@devschooling.com | text         | Your message has been successfully sent to our team. |

  Scenario Outline: Invalid customer form request
    And I select Subject Heading "<subject heading>"
    And I enter email address "<email address>"
    And I enter message "<message text>"
    And I click 'Send' expecting error
    Then Error message appears "<error message>"

    Examples:
      | subject heading  | email address         | message text | error message                                   |
      | -- Choose --     | test@devschooling.com | text         | Please select a subject from the list provided. |
      | Customer service |                       | text         | Invalid email address.                          |
      | Customer service | test@devschooling.com |              | The message cannot be blank.                    |
      | -- Choose --     |                       |              | Please select a subject from the list provided. |