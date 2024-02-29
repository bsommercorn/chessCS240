package Request;

import model.AuthData;

public class LogoutRequest {
    AuthData myToken = null;
    public LogoutRequest(AuthData myToken) {
        this.myToken = myToken;
    }

    public LogoutRequest() {
    }

    public AuthData getMyToken() {
        return myToken;
    }
    public void setMyToken(AuthData myToken) {
        this.myToken = myToken;
    }
}
