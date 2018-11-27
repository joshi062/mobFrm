Feature: EriBank Login

 
  @search
  Scenario: login with valid credentials into EriBank Application
    Given User is on the login page
    And User enters Credentials to LogIn
      | company | company |
    And I press "Login"
    Then "Make Payment" button is Visible
