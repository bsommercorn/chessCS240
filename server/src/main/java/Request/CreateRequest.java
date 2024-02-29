package Request;

import model.AuthData;

public class CreateRequest {
    String gameName;
    AuthData token;
    public CreateRequest(String gamename, AuthData myToken) {
        this.gameName = gamename;
        token = myToken;
    }

    public String getGameName() {
        return gameName;
    }

    public AuthData getToken() {
        return token;
    }

    public void setMyToken(AuthData myAuth) {
        this.token = myAuth;
    }
}
