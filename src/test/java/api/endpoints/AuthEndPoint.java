package api.endpoints;

import static io.restassured.RestAssured.given;

import api.payload.AuthPayloads.ForgotPassword;
import api.payload.AuthPayloads.Login;
import api.payload.AuthPayloads.Register;
import api.payload.AuthPayloads.ResetPassword;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import com.fasterxml.jackson.databind.ObjectMapper;

public class AuthEndPoint {

    // Login API - returns response with token
    public static Response loginUser(Login payload) {
        if (payload == null) {
            throw new IllegalArgumentException("Login payload cannot be null!");
        }

        return given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .header("app", "MTIzfFdsd2Vi") // API key or token
                .body(payload)  
                .when()
                .post(Routes.AuthRoutes.LOGIN_ENDPOINT);
    }

    // Get User API - Requires Bearer Token
    public static Response getUser(String bearerToken) {
        if (bearerToken == null || bearerToken.isEmpty()) {
            throw new IllegalArgumentException("Bearer token is null or empty!");
        }

        return given()
                .header("Authorization", "Bearer " + bearerToken)
                .when()
                .get(Routes.AuthRoutes.GET_USER_ENDPOINT);
    }

    // Register User API
    public static Response registerUser(Register payload) {
        if (payload == null) {
            throw new IllegalArgumentException("Register payload cannot be null!");
        }

        return given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .header("app", "MTIzfFdsd2Vi")
                .body(payload)
                .when()
                .post(Routes.AuthRoutes.REGISTER_ENDPOINT);
    }
    
    // Forgot Password API 
    public static Response forgotPassword(ForgotPassword payload) {
        if (payload == null) {
            throw new IllegalArgumentException("Forgot Password payload cannot be null!");
        }

        return given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .header("app", "MTIzfFdsd2Vi")
                .body(payload)  
                .when()
                .post(Routes.AuthRoutes.FORGET_PASSWORD_ENDPOINT);
    }
    
    // Logout API 
    public static Response logout(String bearerToken) {
        if (bearerToken == null || bearerToken.isEmpty()) {
            throw new IllegalArgumentException("Bearer token is null or empty!");
        }

        return given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .header("app", "MTIzfFdsd2Vi")
                .header("Authorization", "Bearer " + bearerToken)
                .when()
                .post(Routes.AuthRoutes.LOGOUT_ENDPOINT);
    }
    
    // Reset Password API (Updated with JSON Serialization Debugging)
    public static Response resetPassword(ResetPassword payload) {
        if (payload == null) {
            throw new IllegalArgumentException("Reset Password payload cannot be null!");
        }

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonPayload;
        
        try {
            jsonPayload = objectMapper.writeValueAsString(payload);
        } catch (Exception e) {
            throw new RuntimeException("Failed to serialize ResetPassword payload", e);
        }

        System.out.println("Sending JSON Payload: " + jsonPayload); // Debugging

        return given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .header("app", "MTIzfFdsd2Vi")
                .body(jsonPayload)  // Ensure correct JSON format
                .when()
                .post(Routes.AuthRoutes.RESET_PASSWORD);
    }
}
