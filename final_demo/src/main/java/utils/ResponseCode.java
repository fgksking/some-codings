package utils;

public enum ResponseCode {

    SUCCESS(200,"SUCCESS"),
    PROBLEM(100,"AGAIN"),  //重复入账
    ERROR(101,"ERROR"),
    ILLEGAL_ARGUMENT(102, "ILLEGAL_ARGUMENT");
    private final int code;
    private final  String desc;
    ResponseCode(int code,String desc){
        this.code=code;
        this.desc=desc;
    }

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
