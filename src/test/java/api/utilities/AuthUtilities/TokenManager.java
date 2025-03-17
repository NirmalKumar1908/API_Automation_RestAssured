package api.utilities.AuthUtilities;

public class TokenManager {
	private static String bearerToken;

	public static void setToken(String token) {
		bearerToken = token;
	}

	public static String getToken() {
		if (bearerToken == null || bearerToken.isEmpty()) {
			throw new IllegalStateException("Bearer token is not set. Ensure login is successful before using it.");
		}
		return bearerToken;
	}
}
