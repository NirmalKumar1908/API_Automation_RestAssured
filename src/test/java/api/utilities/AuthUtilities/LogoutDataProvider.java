package api.utilities.AuthUtilities;

import org.testng.annotations.DataProvider;

public class LogoutDataProvider {

    @DataProvider(name = "validLogoutData")
    public static Object[][] validLogoutData() {
        return new Object[][] {
            {TokenManager.getToken(), 200} // Fetching the token dynamically with expected status code
        };
    }

    @DataProvider(name = "invalidLogoutData")
    public static Object[][] invalidLogoutData() {
        return new Object[][] {
//            {"", 401},  // Empty token
//            {null, 401}, // Null token
            {"invalidBearerToken456", 401} // Invalid token
        };
    }
}
