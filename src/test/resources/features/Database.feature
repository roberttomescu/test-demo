Feature: Database feature

  Scenario: Database interaction in Java
    When I check test bean
    Given I select something from the database using Java JDBC
    And I select from database using Spring JDBC Template
    When I select a user with email "janet@email.com" using Spring JDBC
    And I select a user via Spring Data JPA
    And I add a new user with
      | name   | email            | status | id |
      | Marius | mdima@gmail.com  | 1      | 2  |
      | Tom    | tom@gmail.com    | 1      | 3  |
      | Scott  | stiger@gmail.com | 1      | 4  |
    Then I check that there are 4 customers in the database

