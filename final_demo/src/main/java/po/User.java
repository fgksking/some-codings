package po;

public class User {

    Integer user_id;
    String username;
    String email;
    String role;
    String photo_url;
    boolean is_frozen;

    public User(Integer user_id, String username, String email, String role, String photo_url, boolean is_frozen) {
        this.user_id = user_id;
        this.username = username;
        this.email = email;
        this.role = role;
        this.photo_url = photo_url;
        this.is_frozen = is_frozen;
    }

    public User() {
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPhoto_url() {
        return photo_url;
    }

    public void setPhoto_url(String photo_url) {
        this.photo_url = photo_url;
    }

    public boolean isIs_frozen() {
        return is_frozen;
    }

    public void setIs_frozen(boolean is_frozen) {
        this.is_frozen = is_frozen;
    }
}
