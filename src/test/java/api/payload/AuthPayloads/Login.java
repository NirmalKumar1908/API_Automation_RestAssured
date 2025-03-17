package api.payload.AuthPayloads;

public class Login {
	private String email;
	private String password;

	// No-argument constructor
	public Login() {
	}

	// Parameterized constructor
	public Login(String email, String password) {
		this.email = email;
		this.password = password;
	}

	// Getters and setters
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	// Override toString for logging purposes
	@Override
	public String toString() {
		return "Login{" + "email='" + email + '\'' + ", password='" + password + '\'' + '}';
	}
}
