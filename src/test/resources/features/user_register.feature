Feature: Users registers
  As a user of the bank
  I would like to be able to register
  So I can to be able to make payments and execute transactions on my products.

  Scenario: Successful user registration

    Given Roxanne is a client and needs manage her products
    When She sends the required information to register
    Then She must obtains a virtual account in order to log in as required