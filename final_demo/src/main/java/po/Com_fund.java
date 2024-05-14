package po;

import java.math.BigDecimal;

public class Com_fund {
    private String ComName;

    private BigDecimal com_amount;

    public Com_fund(String comName, BigDecimal com_amount) {
        ComName = comName;
        this.com_amount = com_amount;
    }

    public Com_fund() {
    }

    public String getComName() {
        return ComName;
    }

    public void setComName(String comName) {
        ComName = comName;
    }

    public BigDecimal getCom_amount() {
        return com_amount;
    }

    public void setCom_amount(BigDecimal com_amount) {
        this.com_amount = com_amount;
    }
}
