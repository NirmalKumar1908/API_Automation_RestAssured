package api.endpoints;

import static io.restassured.RestAssured.given;

import java.util.Map;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;


public class SinglePromptEndPoints {

    // Fetch prompt list with pagination
    public static Response promptList(int paginate, int page) {
        return given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .header("app", "MTIzfFdsd2Vi") // API key or token
                .queryParam("paginate", paginate)
                .queryParam("page", page)
                .when()
                .get(Routes.SinglePromptRoutes.SINGLE_PROMPT_LIST);
    }

    // Fetch category list with pagination
    public static Response categoryList(int paginate, int page) {
        return given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .header("app", "MTIzfFdsd2Vi") // API key or token
                .queryParam("paginate", paginate)
                .queryParam("page", page)
                .when()
                .get(Routes.SinglePromptRoutes.CATEGORY_LIST);
    }

    // Store content with authentication
    public static Response storeContent(String token, Map<String, Object> requestBody) {
        return given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + token)
                .header("app", "MTIzfFdsd2Vi") // API Key
                .body(requestBody)
                .when()
                .post(Routes.SinglePromptRoutes.STORE_CONTENT);
    }

    public static Response getContentsNew(String accessToken, String promptId, String toneId, String question70, String question243, String languageId, String isChat) {
        String apiUrl = Routes.SinglePromptRoutes.GET_CONTENTS_NEW
                + "?access_token=" + accessToken
                + "&prompt_id=" + promptId
                + "&tone_id=" + toneId
                + "&question_70=" + question70
                + "&question_243=" + question243
                + "&language_id=" + languageId
                + "&is_chat=" + isChat;

        RequestSpecification requestSpec = given()
                .header("Accept", "text/event-stream")
                .header("Authorization", "Bearer " + accessToken);

        return requestSpec.get(apiUrl);
    }
}
