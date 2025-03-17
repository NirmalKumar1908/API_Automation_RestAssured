package api.utilities.AuthUtilities;

import org.testng.annotations.DataProvider;
import api.endpoints.AuthEndPoint;
import api.payload.AuthPayloads.Login;
import io.restassured.response.Response;

public class GetUserDataProvider {

	@DataProvider(name = "validLoginData")
	public static Object[][] getUserData(String email, String password) {
		// Create login payload
		Login loginPayload = new Login();
		loginPayload.setEmail(email);
		loginPayload.setPassword(password);

		// Call the login API
		Response loginResponse = AuthEndPoint.loginUser(loginPayload);

		// Ensure login is successful before extracting the token
		if (loginResponse.getStatusCode() == 200) {
			String accessToken = loginResponse.jsonPath().getString("data.access_token");

			// Store token in TokenManager
			TokenManager.setToken(accessToken);

			return new Object[][] { { accessToken, 200 } };
		} else {
			return new Object[][] { { null, loginResponse.getStatusCode() } };
		}
	}

	@DataProvider(name = "invalidLoginData")
	public static Object[][] invalidUserData() {
		return new Object[][] { { "invalid@example.com", "Password123", 401 }, // Invalid email
				{ "valid@example.com", "WrongPassword", 401 }, // Invalid password
				{ "", "Password123", 400 }, // Missing email
				{ "valid@example.com", "", 400 }, // Missing password
				{ "", "", 400 } // Missing both fields
		};
	}
}
