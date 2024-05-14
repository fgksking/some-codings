package po;

import java.util.Date;

public class userIP {
    private String IP;
    private String username;
    private long time;

    public userIP(String IP, String username, long time) {
        this.IP = IP;
        this.username = username;
        this.time = time;
    }

    public String getIP() {
        return IP;
    }

    public void setIP(String IP) {
        this.IP = IP;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
