package handler;

import RequestAndResult.LoginRequest;
import RequestAndResult.LoginResult;
import RequestAndResult.RegisterRequest;
import RequestAndResult.RegisterResult;
import com.google.gson.Gson;
import service.LoginService;
import spark.Request;
import spark.Response;

public class LoginHandler {
    LoginService myService = new LoginService();
    public Object login(Request req, Response res) {
        String myJSON = req.body();
        LoginRequest newRequest = new Gson().fromJson(myJSON, LoginRequest.class);

        LoginResult myResult = myService.newLogin(newRequest);
        return new Gson().toJson(myResult);
    }
}
