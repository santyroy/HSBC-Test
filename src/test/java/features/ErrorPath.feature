@feature
Feature: Exchange Rate API Error Path

  @error1
  Scenario Outline: To test error for exchange date rate API
    Given a "<HTTP_METHOD>" request
    When "<API_NAME>" API is called with date "<DATE>"
    Then an invalid response is returned with "<ERROR>"
    And status code is "<STATUS_CODE>"

    Examples:
      | HTTP_METHOD | API_NAME  | STATUS_CODE | ERROR                                                   | DATE       |
      | GET         | Date Rate | 400         | time data 'abc' does not match format '%Y-%m-%d'        | abc        |
      | GET         | Date Rate | 400         | time data '123' does not match format '%Y-%m-%d'        | 123        |
      | GET         | Date Rate | 400         | time data '!@$' does not match format '%Y-%m-%d'        | !@$        |
      | GET         | Date Rate | 400         | time data '18-01-2021' does not match format '%Y-%m-%d' | 18-01-2021 |
      | GET         | Date Rate | 400         | time data '01-18-2021' does not match format '%Y-%m-%d' | 01-18-2021 |