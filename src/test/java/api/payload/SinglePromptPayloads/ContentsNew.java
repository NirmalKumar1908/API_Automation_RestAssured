package api.payload.SinglePromptPayloads;

public class ContentsNew {
    private String promptId;
    private String toneId;
    private String question70;
    private String question243;
    private String languageId;
    private String isChat;

    // No-argument constructor
    public ContentsNew() {
    }

    // Parameterized constructor
    public ContentsNew(String promptId, String toneId, String question70, String question243, String languageId, String isChat) {
        this.promptId = promptId;
        this.toneId = toneId;
        this.question70 = question70;
        this.question243 = question243;
        this.languageId = languageId;
        this.isChat = isChat;
    }

    // Getters and setters
    public String getPromptId() {
        return promptId;
    }

    public void setPromptId(String promptId) {
        this.promptId = promptId;
    }

    public String getToneId() {
        return toneId;
    }

    public void setToneId(String toneId) {
        this.toneId = toneId;
    }

    public String getQuestion70() {
        return question70;
    }

    public void setQuestion70(String question70) {
        this.question70 = question70;
    }

    public String getQuestion243() {
        return question243;
    }

    public void setQuestion243(String question243) {
        this.question243 = question243;
    }

    public String getLanguageId() {
        return languageId;
    }

    public void setLanguageId(String languageId) {
        this.languageId = languageId;
    }

    public String getIsChat() {
        return isChat;
    }

    public void setIsChat(String isChat) {
        this.isChat = isChat;
    }

    // Override toString for logging purposes
    @Override
    public String toString() {
        return "ContentsNew{" +
                "promptId='" + promptId + '\'' +
                ", toneId='" + toneId + '\'' +
                ", question70='" + question70 + '\'' +
                ", question243='" + question243 + '\'' +
                ", languageId='" + languageId + '\'' +
                ", isChat='" + isChat + '\'' +
                '}';
    }
}
