package model;

public class AuthData {
    String authToken;
    String user;

    public AuthData(String authToken, String user) {
        this.authToken = authToken;
        this.user = user;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
