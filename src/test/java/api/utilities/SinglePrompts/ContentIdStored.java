package api.utilities.SinglePrompts;

public class ContentIdStored {
	private static String contentId;

	public static void setContentId(String id) {
		contentId = id;
	}

	public static String getContentId() {
		return contentId;
	}
}
