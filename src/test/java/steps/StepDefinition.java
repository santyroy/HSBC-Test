package steps;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import response.RootResponse;

import java.util.Map;

import static steps.GlobalVariables.*;

public class StepDefinition {

    @Given("a {string} request")
    public void a_request(String httpMethod) {
        HTTP_METHOD = httpMethod;
    }

    @When("{string} API is called")
    public void api_is_called(String apiName) throws JsonProcessingException {
        String url = Utils.createURL(apiName);
        Map<String, String> responseMap = Utils.getAPIResponse(url, HTTP_METHOD);
        HTTP_STATUS = Integer.parseInt(responseMap.get("STATUS"));

        ObjectMapper objectMapper = new ObjectMapper();
        rootResponse = objectMapper.readValue(responseMap.get("RESPONSE_BODY"), RootResponse.class);
    }

    @Then("a valid response is returned with base {string} date {string}")
    public void a_valid_response_is_returned_with_base_date(String base, String date) {
        System.out.println("--------------------- VALIDATE RESPONSE STARTED ------------------------");

        Assert.assertEquals("Base currency mismatch: ", base, rootResponse.getBase());
        Assert.assertEquals("Date mismatch: ", date, rootResponse.getDate());
    }

    @Then("status code is {string}")
    public void status_code_is(String statusCode) {
        Assert.assertEquals("HTTP Status code mismatch: ", Integer.parseInt(statusCode), HTTP_STATUS);

        System.out.println("--------------------- VALIDATE RESPONSE ENDED ------------------------");
    }
}
