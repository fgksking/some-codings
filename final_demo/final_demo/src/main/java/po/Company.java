package po;

public class Company {
    private Integer Com_id;
    private String ComName;
    private String ComSize;
    private String ComDirection;
    private Boolean ComIspublic;

    public Integer getCom_id() {
        return Com_id;
    }

    public void setCom_id(Integer com_id) {
        Com_id = com_id;
    }

    public String getComName() {
        return ComName;
    }

    public void setComName(String comName) {
        ComName = comName;
    }


    public String getComSize() {
        return ComSize;
    }

    public void setComSize(String comSize) {
        ComSize = comSize;
    }

    public String getComDirection() {
        return ComDirection;
    }

    public void setComDirection(String comDirection) {
        ComDirection = comDirection;
    }

    public Boolean getComIspublic() {
        return ComIspublic;
    }

    public void setComIspublic(Boolean comIspublic) {
        ComIspublic = comIspublic;
    }

    public Company(Integer com_id, String comName,  String comSize, String comDirection, Boolean comIspublic) {
        Com_id = com_id;
        ComName = comName;
        ComSize = comSize;
        ComDirection = comDirection;
        ComIspublic = comIspublic;
    }

    public Company() {
    }

    @Override
    public String toString() {
        return "Company{" +
                "Com_id=" + Com_id +
                ", ComName='" + ComName + '\'' +
                ", ComSize='" + ComSize + '\'' +
                ", ComDirection='" + ComDirection + '\'' +
                ", ComIspublic=" + ComIspublic +
                '}';
    }
}
