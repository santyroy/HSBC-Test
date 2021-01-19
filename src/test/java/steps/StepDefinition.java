package steps;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import response.ErrorResponse;
import response.RootResponse;

import java.util.Map;

import static steps.GlobalVariables.*;

public class StepDefinition {

    private static Map<String, String> responseMap;

    @Given("a {string} request")
    public void a_request(String httpMethod) {
        HTTP_METHOD = httpMethod;
    }

    @When("{string} API is called")
    public void api_is_called(String apiName) throws JsonProcessingException {
        String url = Utils.createURL(apiName);
        responseMap = Utils.getAPIResponse(url, HTTP_METHOD);
        HTTP_STATUS = Integer.parseInt(responseMap.get("STATUS"));
    }

    @When("{string} API is called with date {string}")
    public void api_is_called_with_date(String apiName, String date) {
        String url = Utils.createURL(apiName, date);
        responseMap = Utils.getAPIResponse(url, HTTP_METHOD);
        HTTP_STATUS = Integer.parseInt(responseMap.get("STATUS"));
    }

    @Then("a valid response is returned with base {string} date {string}")
    public void a_valid_response_is_returned_with_base_date(String base, String date) throws JsonProcessingException {
        System.out.println("--------------------- VALIDATE RESPONSE STARTED ------------------------");
        ObjectMapper objectMapper = new ObjectMapper();
        rootResponse = objectMapper.readValue(responseMap.get("RESPONSE_BODY"), RootResponse.class);

        Assert.assertEquals("Base currency mismatch: ", base, rootResponse.getBase());
        Assert.assertEquals("Date mismatch: ", date, rootResponse.getDate());
    }

    @Then("an invalid response is returned with {string}")
    public void an_invalid_response_is_returned_with(String error) throws JsonProcessingException {
        System.out.println("--------------------- VALIDATE RESPONSE STARTED ------------------------");
        ObjectMapper objectMapper = new ObjectMapper();
        errorResponse = objectMapper.readValue(responseMap.get("RESPONSE_BODY"), ErrorResponse.class);

        Assert.assertEquals("Error message mismatch: ", error, errorResponse.getError());
    }

    @Then("status code is {string}")
    public void status_code_is(String statusCode) {
        Assert.assertEquals("HTTP Status code mismatch: ", Integer.parseInt(statusCode), HTTP_STATUS);

        System.out.println("--------------------- VALIDATE RESPONSE ENDED ------------------------");
    }
}
