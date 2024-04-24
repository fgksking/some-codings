package po;

import java.util.Date;

public class trade {
     private Date trade_time;
     private String trade_type;
     private String username;
     private String target;
     private String trade_amount;
     private String bank;

    public trade(Date trade_time, String trade_type, String username, String target, String trade_amount, String bank) {
        this.trade_time = trade_time;
        this.trade_type = trade_type;
        this.username = username;
        this.target = target;
        this.trade_amount = trade_amount;
        this.bank = bank;
    }

    public trade() {
    }

    public Date getTrade_time() {
        return trade_time;
    }

    public void setTrade_time(Date trade_time) {
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

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getTrade_amount() {
        return trade_amount;
    }

    public void setTrade_amount(String trade_amount) {
        this.trade_amount = trade_amount;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }
}
