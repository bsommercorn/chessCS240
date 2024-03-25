package model.Request;

import model.AuthData;

public class ListRequest {
    AuthData myToken = null;
    public ListRequest(AuthData myToken) {
        this.myToken = myToken;
    }

    public ListRequest() {
    }

    public AuthData getMyToken() {
        return myToken;
    }

    public void setMyToken(AuthData myToken) {
        this.myToken = myToken;
    }
}
