package api.endpoints;

public class Routes {
	public static final String BASE_URL = "https://api.gravitywrite.com/api";

	public static class AuthRoutes {
		public static final String LOGIN_ENDPOINT = BASE_URL + "/auth/login";
		public static final String GET_USER_ENDPOINT = BASE_URL + "/auth/getUser";
		public static final String FORGET_PASSWORD_ENDPOINT = BASE_URL + "/auth/password/email";
		public static final String LOGOUT_ENDPOINT = BASE_URL + "/logout";
		public static final String REGISTER_ENDPOINT = BASE_URL + "/auth/register";
		public static final String RESET_PASSWORD = BASE_URL + "/auth/forgotPassword";
	}

	public static class SinglePromptRoutes {
		public static final String SINGLE_PROMPT_LIST = BASE_URL + "/singlePrompt/promptsList";
		public static final String CATEGORY_LIST = BASE_URL + "/singlePrompt/categoryList";
		public static final String GET_CONTENTS_NEW = BASE_URL + "/singlePrompt/contentsnew";
		public static final String STORE_CONTENT = BASE_URL + "/singlePrompt/storeContent";

	}
}
