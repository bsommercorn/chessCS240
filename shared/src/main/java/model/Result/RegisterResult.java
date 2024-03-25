package model.Result;
import model.AuthData;
public class RegisterResult {
    String authToken;
    String message;
    String username;
    public String getMyToken() {
        return authToken;
    }
    public void setMyToken(AuthData myToken) {
        authToken = myToken.getAuthToken();
        this.username = myToken.getUser();
    }
    public String getUsername() {
        return username;
    }

    public String getAuthToken() {
        return authToken;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public RegisterResult() {
    }
    public RegisterResult(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        if (authToken != null) {
            return "{ \"username\":\"" + authToken + "\" }"; //fix?
        }
        else {
            return "{ \"message\":\"" + message + "\" }";
        }

    }
}
