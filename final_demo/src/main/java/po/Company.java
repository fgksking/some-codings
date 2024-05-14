package po;

public class Company {
    private String username;
    private String ComName;
    private String ComSize;
    private String ComDirection;
    private int ComMember;
    private Boolean ComIspublic;
    private Boolean IsApply;
    private boolean is_frozen;

    public Company(String username, String comName, String comSize, String comDirection, int comMember, Boolean comIspublic, Boolean isApply, boolean is_frozen) {
        this.username = username;
        ComName = comName;
        ComSize = comSize;
        ComDirection = comDirection;
        ComMember = comMember;
        ComIspublic = comIspublic;
        IsApply = isApply;
        this.is_frozen = is_frozen;
    }

    public Company() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public int getComMember() {
        return ComMember;
    }

    public void setComMember(int comMember) {
        ComMember = comMember;
    }

    public Boolean getComIspublic() {
        return ComIspublic;
    }

    public void setComIspublic(Boolean comIspublic) {
        ComIspublic = comIspublic;
    }

    public Boolean isApply() {
        return IsApply;
    }

    public void setApply(Boolean apply) {
        IsApply = apply;
    }

    public boolean isIs_frozen() {
        return is_frozen;
    }

    public void setIs_frozen(boolean is_frozen) {
        this.is_frozen = is_frozen;
    }
}
