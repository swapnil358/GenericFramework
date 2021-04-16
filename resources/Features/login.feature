Feature: Google Features 

@RFB 
Scenario: Verify google page 
	Given I launch "https://www.google.com" url 
	And I enter "testing" in "googleSearch" field in "login" page 
	
	