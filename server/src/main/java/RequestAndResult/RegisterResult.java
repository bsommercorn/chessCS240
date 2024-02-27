package RequestAndResult;
import model.AuthData;
public class RegisterResult {
    AuthData myToken;
    String message;

    public AuthData getMyToken() {
        return myToken;
    }
    public void setMyToken(AuthData myToken) {
        this.myToken = myToken;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public RegisterResult() {
    }
}
