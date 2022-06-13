Feature: Context Root of Alert
  In order to use the alert API, it must be available

  Scenario: ContextRoot on app engine
    Given the alert application is alive
    When I navigate to "https://alert.datastore.trevorism.com"
    Then then a link to the help page is displayed

  Scenario: Ping on app engine
    Given the alert application is alive
    When I ping the application deployed to "https://alert.datastore.trevorism.com"
    Then pong is returned, to indicate the service is alive