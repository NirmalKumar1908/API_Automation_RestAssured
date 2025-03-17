package api.test.SinglePromptTests;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import api.endpoints.SinglePromptEndPoints;
import api.utilities.AuthUtilities.TokenManager;
import api.utilities.SinglePrompts.ContentsNewDataProvider;
import api.utilities.SinglePrompts.ContentIdStored; // Import ContentIdStored class
import io.restassured.response.Response;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class ContentsNewTests {

    private static final Logger logger = LogManager.getLogger(ContentsNewTests.class);

    @Test(dataProvider = "contentsDataFromExcel", dataProviderClass = ContentsNewDataProvider.class)
    public void testStreamAPI(String testCaseName, String promptId, String toneId, String question70,
                              String question243, String languageId, String isChat, String expectedStatus, String enabled) {

        logger.info("Executing Test Case: {}", testCaseName);
        logger.info(
                "Request Data - PromptID: [{}], ToneID: [{}], Question70: [{}], Question243: [{}], LanguageID: [{}], IsChat: [{}]",
                promptId, toneId, question70, question243, languageId, isChat);

        // Fetch the Bearer token
        String bearerToken = TokenManager.getToken();
        Assert.assertNotNull(bearerToken, "Token is null! Login might have failed.");
        Assert.assertFalse(bearerToken.isEmpty(), "Token is empty! Cannot fetch user details.");

        // Split the token using pipe (|) and get the second part
        String[] tokenParts = bearerToken.split("\\|");
        Assert.assertTrue(tokenParts.length > 1, "Token format is incorrect. Cannot proceed with the API request.");
        String tokenToSend = tokenParts[1];

        // Call getContentsNew to get the streaming response
        Response response = SinglePromptEndPoints.getContentsNew(tokenToSend, promptId, toneId, question70, question243,
                languageId, isChat);

        // Validate response status with expected status
        int expectedStatusCode = Integer.parseInt(expectedStatus); // Convert Expected Status to integer
        Assert.assertEquals(response.statusCode(), expectedStatusCode, "Unexpected status code! Expected: " + expectedStatusCode);

        // Print the full response headers
        logger.info("Response Headers: {}", response.getHeaders());

        // Fetch and print the 'content_id' from the response headers
        String contentId = response.getHeader("content_id"); // Get content_id from response header
        logger.info("Content-ID: {}", contentId);

        // Store the Content-ID using the ContentIdStored class
        ContentIdStored.setContentId(contentId);

        // Process streaming response
        try (InputStream inputStream = response.asInputStream();
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {

            String line;
            boolean hasData = false;

            while ((line = reader.readLine()) != null) {
                String cleanedLine = new String(line.getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8);
                logger.info("Stream Data: {}", cleanedLine);
                System.out.println(cleanedLine); // Directly print to console
                hasData = true;
            }

            // Assert that stream returned some data
            Assert.assertTrue(hasData, "No data received from the stream API!");

        } catch (Exception e) {
            Assert.fail("Error while reading stream: " + e.getMessage());
        }
    }
}
