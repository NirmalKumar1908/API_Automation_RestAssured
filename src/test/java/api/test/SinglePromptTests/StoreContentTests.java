//package api.test.SinglePromptTests;
//
//import org.testng.Assert;
//import org.testng.annotations.Test;
//import org.apache.logging.log4j.Logger;
//import org.apache.logging.log4j.LogManager;
//import api.payload.SinglePromptPayloads.StoreContentPayload;
//import io.restassured.response.Response;
//import api.utilities.SinglePrompts.StoreContentDataProvider;
//import org.testng.Reporter;
//
//public class StoreContentTests {
//
//	private static final Logger logger = LogManager.getLogger(StoreContentTests.class);
//
//	@Test(dataProvider = "storeContentDataFromExcel", dataProviderClass = StoreContentDataProvider.class)
//	public void testStoreContent(String testCaseName, String promptId, String content, String toneId,
//			String question70, String question243, String languageId, String chatAnswer, String isExisting,
//			String expectedStatusCode) {
//
//		logger.info("Executing Test Case: {}", testCaseName);
//		logger.info("Starting testStoreContent - Prompt ID: [{}], Expected Status Code: [{}]", promptId,
//				expectedStatusCode);
//
//		// Creating payload
//		StoreContentPayload payload = new StoreContentPayload(promptId, content, toneId, question70, question243,
//				languageId, chatAnswer, Boolean.parseBoolean(isExisting));
//
//		logger.info("Request Payload: {}", payload);
//
//		// Sending request
//		Response response = StoreContentEndpoints.storeContent(payload);
//		int actualStatusCode = response.getStatusCode();
//
//		logger.info("Response Status Code: {}", actualStatusCode);
//		logger.info("Response Body: {}", response.getBody().asPrettyString());
//
//		// Logging test case details in TestNG report
//		Reporter.log("Test Data: TestCase=" + testCaseName + ", PromptID=" + promptId + ", ExpectedStatusCode="
//				+ expectedStatusCode);
//
//		// Assertion
//		Assert.assertEquals(actualStatusCode, Integer.parseInt(expectedStatusCode), "Status code mismatch for StoreContent");
//	}
//}
