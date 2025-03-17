package api.test.AuthTests;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import api.endpoints.AuthEndPoint;
import api.utilities.AuthUtilities.TokenManager;
import io.restassured.response.Response;
import org.testng.Reporter;

public class GetUserTests {

	private static final Logger logger = LogManager.getLogger(GetUserTests.class);

	@Test
	public void testGetUserWithValidToken() {
		logger.info("Starting test: testGetUserWithValidToken");

		// Retrieve token from TokenManager
		String bearerToken = TokenManager.getToken();
		logger.info("Retrieved Bearer Token: {}", bearerToken);

		// Ensure the token is available before making the request
		Assert.assertNotNull(bearerToken, "Token is null! Login might have failed.");
		Assert.assertFalse(bearerToken.isEmpty(), "Token is empty! Cannot fetch user details.");

		// Make the API request
		Response response = AuthEndPoint.getUser(bearerToken);
		logger.info("API request sent for fetching user details");

		// Validate response
		int statusCode = response.getStatusCode();
		logger.info("Response Status Code: {}", statusCode);

		Assert.assertEquals(statusCode, 200, "Failed to fetch user details! Unexpected status code.");

		response.then().log().all();

		// Log test details for Extent Reports
		Reporter.log("Get User API called successfully with token: " + bearerToken);
	}
}
