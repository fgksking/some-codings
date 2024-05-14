package po;

import java.math.BigDecimal;
import java.util.Date;

public class trade {
     private String trade_time;
     private String trade_type;
     private String username;
     private String traget;
     private BigDecimal trade_amount;
     private String bank;//账户主体  User/Com
    //可以为空 没改
     private String ComName;

    public trade(String trade_time, String trade_type, String username, String traget, BigDecimal trade_amount, String bank, String comName) {
        this.trade_time = trade_time;
        this.trade_type = trade_type;
        this.username = username;
        this.traget = traget;
        this.trade_amount = trade_amount;
        this.bank = bank;
        ComName = comName;
    }

    public trade() {
    }

    public String getTrade_time() {
        return trade_time;
    }

    public void setTrade_time(String trade_time) {
        this.trade_time = trade_time;
    }

    public String getTrade_type() {
        return trade_type;
    }

    public void setTrade_type(String trade_type) {
        this.trade_type = trade_type;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTraget() {
        return traget;
    }

    public void setTraget(String traget) {
        this.traget = traget;
    }

    public BigDecimal getTrade_amount() {
        return trade_amount;
    }

    public void setTrade_amount(BigDecimal trade_amount) {
        this.trade_amount = trade_amount;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getComName() {
        return ComName;
    }

    public void setComName(String comName) {
        ComName = comName;
    }

    @Override
    public String toString() {
        return "trade{" +
                "trade_time='" + trade_time + '\'' +
                ", trade_type='" + trade_type + '\'' +
                ", username='" + username + '\'' +
                ", traget='" + traget + '\'' +
                ", trade_amount=" + trade_amount +
                ", bank='" + bank + '\'' +
                ", ComName='" + ComName + '\'' +
                '}';
    }
}
