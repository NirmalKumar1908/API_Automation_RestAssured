package api.payload.SinglePromptPayloads;

import java.util.HashMap;
import java.util.Map;

public class StoreContentPayload {
	private int prompt_id;
	private String content;
	private int tone_id;
	private String question_70;
	private String question_243;
	private int language_id;
	private int content_id;
	private String chat_answer;
	private boolean is_existing;

	// Constructor
	public StoreContentPayload(Map<String, Object> data) {
		this.prompt_id = getIntValue(data.get("prompt_id"));
		this.content = (String) data.getOrDefault("content", "");
		this.tone_id = getIntValue(data.get("tone_id"));
		this.question_70 = (String) data.getOrDefault("question_70", "");
		this.question_243 = (String) data.getOrDefault("question_243", "");
		this.language_id = getIntValue(data.get("language_id"));
		this.content_id = getIntValue(data.get("content_id"));
		this.chat_answer = (String) data.getOrDefault("chat_answer", "");
		this.is_existing = (boolean) data.getOrDefault("is_existing", false);
	}

	// Constructor to allow dynamic content_id
	public StoreContentPayload(Map<String, Object> data, int contentId) {
		this(data);
		this.content_id = contentId;
	}

	// Convert object to a mutable map
	public Map<String, Object> toMap() {
		Map<String, Object> map = new HashMap<>();
		map.put("prompt_id", prompt_id);
		map.put("content", content);
		map.put("tone_id", tone_id);
		map.put("question_70", question_70);
		map.put("question_243", question_243);
		map.put("language_id", language_id);
		map.put("content_id", content_id);
		map.put("chat_answer", chat_answer);
		map.put("is_existing", is_existing);
		return map;
	}

	// Convert Object to int safely
	private int getIntValue(Object value) {
		if (value instanceof Number) {
			return ((Number) value).intValue();
		}
		return -1; // Use -1 instead of 0 to indicate invalid values
	}
}
