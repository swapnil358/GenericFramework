@RFB
Feature: Facebook Authentication

  Scenario: Facebook Launch and authentication
    Given I launch "https://www.google.com" url
    When I enterr "google" in "googleSearch" field in "login" page
    Then I click on "search" in "login" page
