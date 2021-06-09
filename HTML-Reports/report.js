$(document).ready(function() {var formatter = new CucumberHTML.DOMFormatter($('.cucumber-report'));formatter.uri("resources/Features/Googlecheck.feature");
formatter.feature({
  "name": "Facebook Authentication",
  "description": "",
  "keyword": "Feature"
});
formatter.scenario({
  "name": "Facebook Launch and authentication",
  "description": "",
  "keyword": "Scenario"
});
formatter.step({
  "name": "I want to launch the facebook in firefox browser",
  "keyword": "Given "
});
formatter.match({
  "location": "stepDefination1.i_want_to_launch_the_facebook_in_firefox_browser()"
});
formatter.result({
  "status": "passed"
});
formatter.step({
  "name": "I want to authenticate with valid credentails",
  "keyword": "When "
});
formatter.match({
  "location": "stepDefination1.i_want_to_authenticate_with_valid_credentails()"
});
formatter.result({
  "status": "passed"
});
formatter.step({
  "name": "I want to verify whether user navigated to home page or not",
  "keyword": "Then "
});
formatter.match({
  "location": "stepDefination1.i_want_to_verify_whether_user_navigated_to_home_page_or_not()"
});
formatter.result({
  "error_message": "java.lang.AssertionError: expected [My account - My Store] but found [Login - My Store]\r\n\tat org.testng.Assert.fail(Assert.java:99)\r\n\tat org.testng.Assert.failNotEquals(Assert.java:1037)\r\n\tat org.testng.Assert.assertEqualsImpl(Assert.java:140)\r\n\tat org.testng.Assert.assertEquals(Assert.java:122)\r\n\tat org.testng.Assert.assertEquals(Assert.java:629)\r\n\tat org.testng.Assert.assertEquals(Assert.java:639)\r\n\tat com.ms.stepDefination.stepDefination1.i_want_to_verify_whether_user_navigated_to_home_page_or_not(stepDefination1.java:40)\r\n\tat ✽.I want to verify whether user navigated to home page or not(resources/Features/Googlecheck.feature:6)\r\n",
  "status": "failed"
});
});