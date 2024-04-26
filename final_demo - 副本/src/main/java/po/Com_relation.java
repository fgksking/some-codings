package po;

public class Com_relation {

     private String ComName;
     private String username;
     private String role;

    public Com_relation(String comName, String username, String role) {
        ComName = comName;
        this.username = username;
        this.role = role;
    }

    public String getComName() {
        return ComName;
    }

    public void setComName(String comName) {
        ComName = comName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Com_relation() {

    }

    @Override
    public String toString() {
        return "Com_relation{" +
                "ComName='" + ComName + '\'' +
                ", username='" + username + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
