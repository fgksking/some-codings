package po;

public class Com_fund {
    private String ComName;
    boolean is_frozen;
    private Integer com_amount;

    public Com_fund(String comName, boolean is_frozen, Integer com_amount) {
        ComName = comName;
        this.is_frozen = is_frozen;
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

    public boolean isIs_frozen() {
        return is_frozen;
    }

    public void setIs_frozen(boolean is_frozen) {
        this.is_frozen = is_frozen;
    }

    public Integer getCom_amount() {
        return com_amount;
    }

    public void setCom_amount(Integer com_amount) {
        this.com_amount = com_amount;
    }
}
