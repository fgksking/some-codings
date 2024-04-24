package po;

import java.math.BigDecimal;

public class permission {

    private String ComName;
    private String username;
    private String approval_name;

    private BigDecimal per_amount;

    public permission(String comName, String username, String approval_name, BigDecimal per_amount) {
        ComName = comName;
        this.username = username;
        this.approval_name = approval_name;
        this.per_amount = per_amount;
    }

    public permission() {
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

    public String getApproval_name() {
        return approval_name;
    }

    public void setApproval_name(String approval_name) {
        this.approval_name = approval_name;
    }

    public BigDecimal getPer_amount() {
        return per_amount;
    }

    public void setPer_amount(Integer per_amount) {
        this.per_amount = new BigDecimal(per_amount);
    }

    @Override
    public String toString() {
        return "permission{" +
                "ComName='" + ComName + '\'' +
                ", username='" + username + '\'' +
                ", approval_name='" + approval_name + '\'' +
                ", per_amount=" + per_amount +
                '}';
    }
}
