package po;

import java.math.BigDecimal;

public class Com_group {
    private Integer Com_id;
    private Integer Com_group_id;
    private String Com_group_name;
    private Integer Com_group_amount;

    public Com_group(Integer com_id, Integer com_group_id, String com_group_name, Integer com_group_amount) {
        Com_id = com_id;
        Com_group_id = com_group_id;
        Com_group_name = com_group_name;
        Com_group_amount = com_group_amount;
    }

    public Com_group() {
    }

    public Integer getCom_id() {
        return Com_id;
    }

    public void setCom_id(Integer com_id) {
        Com_id = com_id;
    }

    public Integer getCom_group_id() {
        return Com_group_id;
    }

    public void setCom_group_id(Integer com_group_id) {
        Com_group_id = com_group_id;
    }

    public String getCom_group_name() {
        return Com_group_name;
    }

    public void setCom_group_name(String com_group_name) {
        Com_group_name = com_group_name;
    }

    public Integer getCom_group_amount() {
        return Com_group_amount;
    }

    public void setCom_group_amount(Integer com_group_amount) {
        Com_group_amount = com_group_amount;
    }
}
