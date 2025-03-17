package api.test.AuthTests;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import api.endpoints.AuthEndPoint;
import api.payload.AuthPayloads.ForgotPassword;
import io.restassured.response.Response;
import api.utilities.AuthUtilities.ForgotPasswordDataProvider;
import org.testng.Reporter;

public class ForgotPasswordTests {

	private static final Logger logger = LogManager.getLogger(ForgotPasswordTests.class);

	@Test(dataProvider = "forgotPasswordDataFromExcel", dataProviderClass = ForgotPasswordDataProvider.class)
	public void testForgotPassword(String testCaseName, String email, String expectedStatusCode) {
		logger.info("Executing Test Case: {}", testCaseName);
		logger.info("Starting testForgotPassword - Email: [{}], Expected Status Code: [{}]", email, expectedStatusCode);

		ForgotPassword forgotPasswordRequest = new ForgotPassword(email);
		logger.info("Forgot Password Request Payload: {}", forgotPasswordRequest);

		Response response = AuthEndPoint.forgotPassword(forgotPasswordRequest);
		int actualStatusCode = response.getStatusCode();

		logger.info("Response Status Code: {}", actualStatusCode);
		logger.info("Response Body: {}", response.getBody().asPrettyString());

		Reporter.log("Test Data: TestCase=" + testCaseName + ", Email=" + email + ", ExpectedStatusCode="
				+ expectedStatusCode);

		Assert.assertEquals(actualStatusCode, Integer.parseInt(expectedStatusCode),
				"Status code mismatch for forgot password request");

		if (actualStatusCode == 200) {
			String message = response.jsonPath().getString("message");
			if (message == null || message.isEmpty()) {
				logger.error("Forgot password successful, but no response message received!");
				throw new RuntimeException("Forgot password successful, but no response message received!");
			}
			logger.info("Forgot password request successful. Message: {}", message);
		} else {
			logger.error("Forgot password request failed.");
		}
	}
}
