package RequestAndResult;

import model.AuthData;

public class LoginResult {
    boolean validated = false;
    String authToken;
    String message;
    String username;

    public String getMyToken() {
        return authToken;
    }

    public LoginResult(AuthData myToken) {
        this.authToken = myToken.getAuthToken();
        this.username = myToken.getUser();
        validated = true;
    }
    public LoginResult(String exception) {
        this.message = exception;
    }

    public String getMessage() {
        return message;
    }
    public String getUsername() {
        return username;
    }

    public String toString() {
        if (authToken != null) {
            return "{ \"username\":\"" + authToken + "\" }";
        }
        else {
            return "{ \"message\":\"" + message + "\" }";
        }

    }
}
