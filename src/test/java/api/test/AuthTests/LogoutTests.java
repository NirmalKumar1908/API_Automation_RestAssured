package api.test.AuthTests;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import api.endpoints.AuthEndPoint;
import api.utilities.AuthUtilities.LogoutDataProvider;
import io.restassured.response.Response;
import org.testng.Reporter;

public class LogoutTests {

    private static final Logger logger = LogManager.getLogger(LogoutTests.class);

    @Test(dependsOnMethods = "api.test.AuthTests.LoginTests.testValidUserLogin", 
          dataProvider = "validLogoutData", 
          dataProviderClass = LogoutDataProvider.class)
    public void testValidLogout(String bearerToken, int expectedStatusCode) {
        logger.info("Starting testValidLogout for Token: {}", bearerToken);

        Response response = AuthEndPoint.logout(bearerToken);

        int actualStatusCode = response.getStatusCode();
        logger.info("Logout API Response Status Code: {}", actualStatusCode);

        // Validate status code
        Assert.assertEquals(actualStatusCode, expectedStatusCode, "Status code mismatch for valid logout");

        // Log response
        response.prettyPrint();

        // Verify successful logout response
        if (actualStatusCode == 200) {
            logger.info("Logout successful for token: {}", bearerToken);
        } else {
            logger.error("Logout failed! Unexpected response.");
            throw new RuntimeException("Logout failed! Unexpected response.");
        }

        // Log test details for Extent Reports
        Reporter.log("Valid Logout API tested with token: " + bearerToken);
    }

    @Test(dataProvider = "invalidLogoutData", dataProviderClass = LogoutDataProvider.class)
    public void testInvalidLogout(String bearerToken, int expectedStatusCode) {
        logger.info("Starting testInvalidLogout for Token: {}", bearerToken);

        Response response = AuthEndPoint.logout(bearerToken);

        int actualStatusCode = response.getStatusCode();
        logger.info("Logout API Response Status Code: {}", actualStatusCode);

        // Validate status code
        Assert.assertEquals(actualStatusCode, expectedStatusCode, "Status code mismatch for invalid logout");

        // Log response
        response.prettyPrint();

        // Log test details for Extent Reports
        Reporter.log("Invalid Logout API tested with token: " + bearerToken);
    }
}
