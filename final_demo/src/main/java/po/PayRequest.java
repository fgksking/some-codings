package po;

import java.math.BigDecimal;

public class PayRequest {
    private String sender;
    private String receiver;
    private BigDecimal amount;
    private String sign;

    public PayRequest(String sender, String receiver, BigDecimal amount, String sign) {
        this.sender = sender;
        this.receiver = receiver;
        this.amount = amount;
        this.sign = sign;
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

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public PayRequest() {
    }



}
