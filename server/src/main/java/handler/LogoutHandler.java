package handler;

import RequestAndResult.CreateResult;
import RequestAndResult.LogoutRequest;
import RequestAndResult.LogoutResult;
import com.google.gson.Gson;
import model.AuthData;
import service.LoginService;
import service.LogoutService;
import spark.Request;
import spark.Response;

public class LogoutHandler {
    LogoutService myService = new LogoutService();
    public Object logout(Request req, Response res) {
        String authString = req.headers("Authorization"); //maybe fix in all classes to match
        AuthData myAuth;
        if (authString != null) {
            myAuth = new AuthData(authString);
        }
        else {
            res.status(401);
            return new Gson().toJson(new CreateResult("Error: No authToken provided"));
        }

        LogoutRequest newRequest = new LogoutRequest(myAuth);

        LogoutResult myResult = myService.logout(newRequest);
        if (myResult.getMessage() != null) {
            res.status(401);
        }
        return new Gson().toJson(myResult);
    }
}
