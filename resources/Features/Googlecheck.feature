@fb
Feature: Facebook Authentication

  Scenario: Google Launch and authentication
    Given I want to launch the facebook in firefox browser
    When I want to authenticate with valid credentails
    Then I want to verify whether user navigated to home page or not
