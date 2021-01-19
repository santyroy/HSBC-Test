Feature: Exchange Rate API

  @test
  Scenario Outline: To test exchange rate API
    Given a "<HTTP_METHOD>" request
    When "<API_NAME>" API is called
    Then a valid response is returned with base "<BASE>" date "<DATE>"
    And status code is "<STATUS_CODE>"

    Examples:
      | HTTP_METHOD | API_NAME    | STATUS_CODE | BASE | DATE       |
      | GET         | Latest Rate | 200         | EUR  | 2021-01-18 |