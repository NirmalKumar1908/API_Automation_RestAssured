package api.endpoints;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import static io.restassured.RestAssured.given;

public class StreamAPITest {

    // Fixed server URL and access token
    private static final String SERVER_URL = "https://api.gravitywrite.com/api";
    private static final String ACCESS_TOKEN = "DIBYiYTvAGmcW1cz7TtpQSkEVMo2Qopr1yYDUxrJ5513f3a3";

    @Test
    public void testStreamAPI() {
        // Fixed API endpoint with query parameters
        String apiUrl = SERVER_URL + "/singlePrompt/contentsnew"
                + "?access_token=" + ACCESS_TOKEN
                + "&prompt_id=46"
                + "&tone_id=10"
                + "&question_70=Healthy%20recipes"
                + "&question_243=13"
                + "&language_id=21"
                + "&is_chat=0";

        // Create request specification
        RequestSpecification requestSpec = given()
                .header("Accept", "text/event-stream");

        // Print the request headers
        System.out.println("==== Request Headers ====");
        requestSpec.get(apiUrl).getHeaders().forEach(header ->
            System.out.println(header.getName() + ": " + header.getValue())
        );

        // Send GET request to stream API
        Response response = requestSpec.when()
                .get(apiUrl)
                .then()
                .extract()
                .response();

        // Print the response headers
        System.out.println("\n==== Response Headers ====");
        response.getHeaders().forEach(header ->
            System.out.println(header.getName() + ": " + header.getValue())
        );

        // Validate response status
        Assert.assertEquals(response.statusCode(), 200, "Unexpected status code!");

        // Process streaming response
        try (InputStream inputStream = response.asInputStream();
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {

            String line;
            boolean hasData = false;

            while ((line = reader.readLine()) != null) {
                System.out.println("Stream Data: " + line);
                hasData = true;  // Mark that we received data
            }

            // Assert that stream returned some data
            Assert.assertTrue(hasData, "No data received from the stream API!");

        } catch (Exception e) {
            Assert.fail("Error while reading stream: " + e.getMessage());
        }
    }
}
