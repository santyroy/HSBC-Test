package steps;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static steps.Constants.RATE_API;
import static steps.GlobalVariables.HTTP_METHOD;

public class Utils {

    private static HttpGet getRequest;

    public static Map<String, String> getAPIResponse(String url, String httpMethod) {
        Map<String, String> responseMap = new HashMap<>();

        if (HTTP_METHOD.equals("GET")) {
            getRequest = new HttpGet(url);
        }

        try (CloseableHttpClient httpClient = HttpClients.createDefault();
             CloseableHttpResponse response = httpClient.execute(getRequest)) {

            // Get HttpResponse Status
            responseMap.put("PROTOCOL", response.getProtocolVersion().getProtocol());
            responseMap.put("STATUS", String.valueOf(response.getStatusLine().getStatusCode()));
            responseMap.put("REASON", response.getStatusLine().getReasonPhrase());
            responseMap.put("STATUS_LINE", response.getStatusLine().toString());

            HttpEntity entity = response.getEntity();
            if (entity != null) {
                // return it as a String
                String result = EntityUtils.toString(entity);
                responseMap.put("RESPONSE_BODY", result);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return responseMap;
    }

    public static String createURL(String apiName) {
        if (apiName.toLowerCase().startsWith("latest")) {
            return RATE_API + "latest";
        }
        return "invalid url";
    }

    public static String createURL(String apiName, String date) {
        if (apiName.toLowerCase().startsWith("date")) {
            return RATE_API + date;
        }
        return "invalid url";
    }
}
