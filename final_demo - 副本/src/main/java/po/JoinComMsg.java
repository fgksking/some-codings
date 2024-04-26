package po;

public class JoinComMsg {

    private String username;
    private String ComName;
    private String reason;
    private String join_time;

    public JoinComMsg(String username, String comName, String reason, String join_time) {
        this.username = username;
        ComName = comName;
        this.reason = reason;
        this.join_time = join_time;
    }

    public JoinComMsg() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getComName() {
        return ComName;
    }

    public void setComName(String comName) {
        ComName = comName;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getJoin_time() {
        return join_time;
    }

    public void setJoin_time(String join_time) {
        this.join_time = join_time;
    }

    @Override
    public String toString() {
        return "JoinComMsg{" +
                "username='" + username + '\'' +
                ", ComName='" + ComName + '\'' +
                ", reason='" + reason + '\'' +
                ", join_time='" + join_time + '\'' +
                '}';
    }
}
