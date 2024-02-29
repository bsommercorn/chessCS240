package model;

import java.util.Objects;

public class AuthData {
    String authToken;
    String user;

    public AuthData(String authToken, String user) {
        this.authToken = authToken;
        this.user = user;
    }
    public AuthData(String authToken) {
        this.authToken = authToken;
        String[] extract = authToken.split("/", 2);
        this.user = extract[0];
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuthData authData = (AuthData) o;
        return Objects.equals(authToken, authData.authToken) && Objects.equals(user, authData.user);
    }
}
