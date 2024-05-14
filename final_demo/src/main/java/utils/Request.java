package utils;

import java.math.BigDecimal;

public class Request {
    private String sender;

    private String receiver;

    private String bank_type;
    private BigDecimal amount;

    public Request(String sender, String receiver, String bank_type, BigDecimal amount) {
        this.sender = sender;
        this.receiver = receiver;
        this.bank_type = bank_type;
        this.amount = amount;
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

    public String getBank_type() {
        return bank_type;
    }

    public void setBank_type(String bank_type) {
        this.bank_type = bank_type;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
