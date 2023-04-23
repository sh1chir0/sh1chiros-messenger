package ua.sh1chiro.messenger.models;

/**
 * @author sh1chiro 20.04.2023
 */
public class ChatMessage {
    private Long chatId;
    private MessageType type;
    private String content;
    private String sender;
    public enum MessageType {
        CHAT,
        JOIN,
        LEAVE
    }
    public MessageType getType() {
        return type;
    }
    public void setType(MessageType type) {
        this.type = type;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public String getSender() {
        return sender;
    }
    public void setSender(String sender) {
        this.sender = sender;
    }

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }
    public ChatMessage(){}

}