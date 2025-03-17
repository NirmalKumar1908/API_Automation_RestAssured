package api.test.AuthTests;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import api.endpoints.AuthEndPoint;
import api.payload.AuthPayloads.Register;
import api.utilities.AuthUtilities.RegisterDataProvider;
import io.restassured.response.Response;
import org.testng.Reporter;

public class RegisterTests {

	private static final Logger logger = LogManager.getLogger(RegisterTests.class);

	// Test for Valid User Registration using DataProvider
	@Test(dataProvider = "validRegisterData", dataProviderClass = RegisterDataProvider.class)
	public void testValidUserRegister(String email, String password, String name, int expectedStatusCode) {
		logger.info("Starting testValidUserRegister for Email: {}, Name: {}", email, name);

		Register register = new Register();
		register.setName(name);
		register.setEmail(email);
		register.setPassword(password);

		Response response = AuthEndPoint.registerUser(register);
		int actualStatusCode = response.getStatusCode();

		logger.info("Register API Response Status Code: {}", actualStatusCode);
		Assert.assertEquals(actualStatusCode, expectedStatusCode, "Status code mismatch for valid registration");

		// Log test details for Extent Reports
		Reporter.log("Valid Register API tested with email: " + email);
		response.prettyPrint();
	}

	// Test for Invalid User Registration using DataProvider
	@Test(dataProvider = "invalidRegisterData", dataProviderClass = RegisterDataProvider.class)
	public void testInvalidUserRegister(String email, String password, String name, int expectedStatusCode) {
		logger.info("Starting testInvalidUserRegister for Email: {}, Name: {}", email, name);

		Register register = new Register();
		register.setName(name);
		register.setEmail(email);
		register.setPassword(password);

		Response response = AuthEndPoint.registerUser(register);
		int actualStatusCode = response.getStatusCode();

		logger.info("Register API Response Status Code: {}", actualStatusCode);
		Assert.assertEquals(actualStatusCode, expectedStatusCode, "Status code mismatch for invalid registration");

		// Log test details for Extent Reports
		Reporter.log("Invalid Register API tested with email: " + email);
		response.prettyPrint();
	}
}
