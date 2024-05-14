package po;

import java.util.Date;

public class Message {
    private String sender;
    private String receiver;
    private String content;
    private String time;

    public Message(String sender, String receiver, String content, String time) {
        this.sender = sender;
        this.receiver = receiver;
        this.content = content;
        this.time = time;
    }

    public Message() {
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
