Feature: Api Testing Feature

  Scenario: Register User with email
    Given I get all the users from "https://reqres.in/api/users"
    And I save an email from a random user
    When I register another user using the saved email at "https://reqres.in/api/register"
    And I login with the newly created user at "https://reqres.in/api/login"
    Then The register user token and login token should be the same

    @backpack
    Scenario: Test that backpack maintains state
      Given I check testBackPack