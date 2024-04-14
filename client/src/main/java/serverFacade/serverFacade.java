package serverFacade;
import com.google.gson.Gson;
import exception.ResponseException;
import model.AuthData;
import model.Request.*;
import model.Result.*;

import java.io.*;
import java.net.*;

public class serverFacade {
    //what is this for?
    //The ServerFacade class both sends and recieves HTTP requests from the Server, over the internet
    //Basically, this will have a lot of the Repl functionality
    //Client code uses methods here to access API stuff on the server side of things
    private final String serverUrl;

    public serverFacade(String url) {
        serverUrl = url;
    }

    //specific functions here
    public AuthData doLogin(String username, String password) throws ResponseException {
        var path = "/session";
        LoginResult myResult = this.makeRequest("POST", path, new LoginRequest(username, password), LoginResult.class, null);
        //error code handling?
        return new AuthData(myResult.getMyToken(), myResult.getUsername());
    }

    public AuthData doRegister(String username, String password, String email) throws ResponseException {
        var path = "/user";
        RegisterResult myResult = this.makeRequest("POST", path, new RegisterRequest(username, password, email), RegisterResult.class, null);
        //error code handling?
        return new AuthData(myResult.getMyToken(), myResult.getUsername());
    }

    public void doLogout(AuthData myToken) throws ResponseException {
        var path = "/session";
        LogoutResult myResult = this.makeRequest("DELETE", path, new LogoutRequest(myToken), LogoutResult.class, myToken);
    }

    public void doClear() throws ResponseException {
        var path = "/db";
        ClearResult myResult = this.makeRequest("DELETE", path, new ClearRequest(), ClearResult.class, null);
    }

    public CreateResult doCreate(AuthData myToken, String myName) throws ResponseException {
        var path = "/game";
        CreateResult myResult = this.makeRequest("POST", path, new CreateRequest(myName, myToken), CreateResult.class, myToken);
        return myResult;
    }

    public ListResult doList(AuthData myToken) throws ResponseException {
        var path = "/games";
        ListResult myResult = this.makeRequest("GET", path, new ListRequest(myToken), ListResult.class, myToken);
        return myResult;
    }

    public JoinResult doJoin(AuthData myToken, String color, int myID) throws ResponseException {
        var path = "/game";
        JoinResult myResult = this.makeRequest("PUT", path, new JoinRequest(color, myID), JoinResult.class, myToken);
        return myResult;
    }

    public JoinResult doObserve(AuthData myToken, int myID) throws ResponseException {
        var path = "/game";
        JoinResult myResult = this.makeRequest("PUT", path, new JoinRequest(null, myID), JoinResult.class, myToken);
        return myResult;
    }

    private <T> T makeRequest(String method, String path, Object request, Class<T> responseClass, AuthData myAuth) throws ResponseException {
        try {
            URL url = (new URI(serverUrl + path)).toURL();
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod(method);
            http.setDoOutput(true);

            if (myAuth != null) {
                http.addRequestProperty("Authorization", myAuth.getAuthToken());
            }

            writeBody(request, http);
            http.connect();
            throwIfNotSuccessful(http);
            return readBody(http, responseClass);
        } catch (Exception ex) {
            throw new ResponseException(500, ex.getMessage());
        }
    }


    private static void writeBody(Object request, HttpURLConnection http) throws IOException {
        if (request != null) {
            http.addRequestProperty("Content-Type", "application/json");
            String reqData = new Gson().toJson(request);
            try (OutputStream reqBody = http.getOutputStream()) {
                reqBody.write(reqData.getBytes());
            }
        }
    }

    private void throwIfNotSuccessful(HttpURLConnection http) throws IOException, ResponseException {
        var status = http.getResponseCode();
        if (!isSuccessful(status)) {
            throw new ResponseException(status, "failure: " + status);
        }
    }

    private static <T> T readBody(HttpURLConnection http, Class<T> responseClass) throws IOException {
        T response = null;
        if (http.getContentLength() < 0) {
            try (InputStream respBody = http.getInputStream()) {
                InputStreamReader reader = new InputStreamReader(respBody);
                if (responseClass != null) {
                    response = new Gson().fromJson(reader, responseClass);
                }
            }
        }
        return response;
    }
    private boolean isSuccessful(int status) {
        return status / 100 == 2;
    }
}
