package api.test.SinglePromptTests;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import api.endpoints.SinglePromptEndPoints;
import api.utilities.SinglePrompts.PromptListDataProvider;
import io.restassured.response.Response;
import org.testng.Reporter;

public class PromptListTests {

    private static final Logger logger = LogManager.getLogger(PromptListTests.class);

    /**
     * Test case to validate different error scenarios for Prompt List API.
     * Reads test data from the PromptListDataProvider.
     */
    @Test(dataProvider = "errorCases", dataProviderClass = PromptListDataProvider.class)
    public void testPromptListErrors(String testCaseName, int paginate, int page, int expectedStatusCode) {
        // Log test case execution start
        logger.info("=============================================================");
        logger.info(" Executing Test Case: {}", testCaseName);
        logger.info(" Testing prompt list with paginate={} and page={}", paginate, page);

        // Call API endpoint
        Response response = SinglePromptEndPoints.promptList(paginate, page);
        int actualStatusCode = response.getStatusCode();

        // Log API response details
        logger.info(" Response Status Code: {}", actualStatusCode);
        response.then().log().all(); // Print full response in logs

        // Assertion to check if expected status matches actual status
        Assert.assertEquals(actualStatusCode, expectedStatusCode,
                " Unexpected status code for TestCase: " + testCaseName);

        // Log test result in the TestNG report
        Reporter.log(" TestCase: " + testCaseName + " | Paginate=" + paginate + " | Page=" + page +
                " | Expected: " + expectedStatusCode + " | Got: " + actualStatusCode);
        logger.info(" Test Case Completed: {}\n", testCaseName);
        logger.info("=============================================================");
    }
}
