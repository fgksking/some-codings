package po;

import java.math.BigDecimal;

public class User_fund {
    private String username;

    private BigDecimal user_amount;

    public User_fund(String username, BigDecimal user_amount) {
        this.username = username;
        this.user_amount = user_amount;
    }

    public User_fund() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public BigDecimal getUser_amount() {
        return user_amount;
    }

    public void setUser_amount(BigDecimal user_amount) {
        this.user_amount = user_amount;
    }
}
