package api.test.AuthTests;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import api.endpoints.AuthEndPoint;
import api.payload.AuthPayloads.ResetPassword;
import api.utilities.AuthUtilities.ResetPasswordDataProvider;
import io.restassured.response.Response;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.testng.Reporter;

public class ResetPasswordTests {

	private static final Logger logger = LogManager.getLogger(ResetPasswordTests.class);

	/**
	 * Test case to validate different error scenarios for Reset Password API. Reads
	 * test data from the ResetPasswordDataProvider.
	 */
	@Test(dataProvider = "resetPasswordDataFromExcel", dataProviderClass = ResetPasswordDataProvider.class)
	public void testResetPassword(String testCaseName, String email, String password, String confirmPassword, String expectedStatusCode) {
	    logger.info("=============================================================");
	    logger.info("Executing Test Case: {}", testCaseName);
	    logger.info("Email: {} | Password: {} | Confirm Password: {}", email, password, confirmPassword);

	    // Create ResetPassword object
	    ResetPassword resetPassword = new ResetPassword(email, password, confirmPassword);

	    try {
	        // Log JSON payload
	        String jsonPayload = new ObjectMapper().writeValueAsString(resetPassword);
	        logger.info("Request Payload: {}", jsonPayload);
	    } catch (Exception e) {
	        logger.error("Failed to convert ResetPassword object to JSON", e);
	    }

	    // Call API endpoint
	    Response response = AuthEndPoint.resetPassword(resetPassword);
	    int actualStatusCode = response.getStatusCode();
	    int expectedCode = Integer.parseInt(expectedStatusCode);

	    // Log and assert response
	    logger.info("Response Status Code: {}", actualStatusCode);
	    response.then().log().all();
	    Assert.assertEquals(actualStatusCode, expectedCode, "Unexpected status code");

	    // Log test result
	    Reporter.log("TestCase: " + testCaseName + " | Expected: " + expectedStatusCode + " | Got: " + actualStatusCode);
	    logger.info("Test Case Completed: {}\n", testCaseName);
	    logger.info("=============================================================");
	}

}
