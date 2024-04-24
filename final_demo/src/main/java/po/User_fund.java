package po;

import java.math.BigDecimal;

public class User_fund {
    private String username;

    private String bank_user_pwd;
    private BigDecimal user_amount;

    public User_fund(String username, String bank_user_pwd, BigDecimal user_amount) {
        this.username = username;
        this.bank_user_pwd = bank_user_pwd;
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

    public String getBank_user_pwd() {
        return bank_user_pwd;
    }

    public void setBank_user_pwd(String bank_user_pwd) {
        this.bank_user_pwd = bank_user_pwd;
    }

    public BigDecimal getUser_amount() {
        return user_amount;
    }

    public void setUser_amount(BigDecimal user_amount) {
        this.user_amount = user_amount;
    }
}
