package api.payload.AuthPayloads;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ResetPassword {
    private String email;
    private String password;

    @JsonProperty("confirm_password") // Ensures correct JSON format
    private String confirmPassword;

    // Constructor
    public ResetPassword(String email, String password, String confirmPassword) {
        this.email = email;
        this.password = password;
        this.confirmPassword = confirmPassword;
    }

    // Getters and Setters
    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }
}
