package utils;

public class PayResponse {

    private boolean Success;
    private String Message;

    public PayResponse(boolean success, String message) {
        Success = success;
        Message = message;
    }

    public PayResponse() {
    }

    public boolean isSuccess() {
        return Success;
    }

    public void setSuccess(boolean success) {
        Success = success;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }
}
