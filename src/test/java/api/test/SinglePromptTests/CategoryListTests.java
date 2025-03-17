package api.test.SinglePromptTests;

import org.testng.Assert;
import org.testng.annotations.Test;

import api.endpoints.SinglePromptEndPoints;
import api.utilities.SinglePrompts.CategoryListDataProvider;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import io.restassured.response.Response;
import org.testng.Reporter;

public class CategoryListTests {

    private static final Logger logger = LogManager.getLogger(CategoryListTests.class);

    @Test(dataProvider = "categoryErrorCases", dataProviderClass = CategoryListDataProvider.class)
    public void testCategoryListErrors(int paginate, int page, int expectedStatusCode) {
        logger.info("Executing test with paginate={} and page={}", paginate, page);

        Response response = SinglePromptEndPoints.categoryList(paginate, page);
        int actualStatusCode = response.getStatusCode();

        logger.info("Received Response - Status Code: {}", actualStatusCode);
        logger.debug("Response Body: {}", response.getBody().asPrettyString());

        Assert.assertEquals(actualStatusCode, expectedStatusCode,
            "Mismatch in status code for paginate=" + paginate + " & page=" + page);

        Reporter.log(String.format("Tested API with paginate=%d, page=%d. Expected: %d, Got: %d",
                paginate, page, expectedStatusCode, actualStatusCode));
    }
}
