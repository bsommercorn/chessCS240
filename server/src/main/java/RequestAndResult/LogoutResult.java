package RequestAndResult;

public class LogoutResult {
    String message = null;

    public LogoutResult(String message) {
        this.message = message;
    }

    public LogoutResult() {
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
