package api.test.AuthTests;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import api.endpoints.AuthEndPoint;
import api.payload.AuthPayloads.Login;
import io.restassured.response.Response;
import api.utilities.AuthUtilities.LoginDataProvider;
import api.utilities.AuthUtilities.TokenManager;
import org.testng.Reporter;

public class LoginTests {

	private static final Logger logger = LogManager.getLogger(LoginTests.class);

	@Test(dataProvider = "loginDataFromExcel", dataProviderClass = LoginDataProvider.class)
	public void testUserLogin(String testCaseName, String email, String password, String expectedStatusCode) {
		logger.info("Executing Test Case: {}", testCaseName);
		logger.info("Starting testUserLogin - Email: [{}], Expected Status Code: [{}]", email, expectedStatusCode);

		Login login = new Login(email, password);
		logger.info("Login Request Payload: {}", login);

		Response response = AuthEndPoint.loginUser(login);
		int actualStatusCode = response.getStatusCode();

		logger.info("Response Status Code: {}", actualStatusCode);
		logger.info("Response Body: {}", response.getBody().asPrettyString());

		Reporter.log("Test Data: TestCase=" + testCaseName + ", Email=" + email + ", ExpectedStatusCode="
				+ expectedStatusCode);

		Assert.assertEquals(actualStatusCode, Integer.parseInt(expectedStatusCode), "Status code mismatch for login");

		if (actualStatusCode == 200) {
			String token = response.jsonPath().getString("data.access_token");
			if (token == null || token.isEmpty()) {
				logger.error("Login successful, but token is missing!");
				throw new RuntimeException("Login successful, but token is missing!");
			}
			TokenManager.setToken(token);
			logger.info("Token stored successfully in TokenManager!");
		} else {
			logger.error("Login failed! Unable to fetch token.");
		}
	}
}
