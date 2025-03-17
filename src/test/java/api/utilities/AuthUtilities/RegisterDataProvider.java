package api.utilities.AuthUtilities;

import org.testng.annotations.DataProvider;

public class RegisterDataProvider {

	// Data Provider for Positive Registration Scenarios
	@DataProvider(name = "validRegisterData")
	public static Object[][] validRegisterData() {
		return new Object[][] { { "testuser" + System.currentTimeMillis() + "@wl.com", "Test@12345", "User One", 401 },
				{ "userexample" + System.currentTimeMillis() + "@wl.com", "SecurePass@4567", "User Example", 401 } };
	}

	// Data Provider for Negative Registration Scenarios
	@DataProvider(name = "invalidRegisterData")
	public static Object[][] invalidRegisterData() {
		return new Object[][] { { "", "Test@1234", "Test User", 422 }, // Empty email
				{ "testuser1@gmail.com", "", "Test User", 409 }, // Empty password
				{ "testuser1@gmail.com", "Test@1234", "", 409 }, // Empty name   //422 status code is correct
				{ "invalidemail", "Test@1234", "Test User", 422 }, // Invalid email format
				{ "user@example.com", "short", "Test User", 409 }, // Weak password //422 status code is correct
				{ "websitelearnertest@gmail.com", "Test@1234", "Test User", 409 }, // Email already registered
				{ "", "", "", 422 } // All fields empty
		};
	}
}
