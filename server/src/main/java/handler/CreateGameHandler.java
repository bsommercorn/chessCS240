package handler;

import RequestAndResult.CreateRequest;
import RequestAndResult.CreateResult;
import RequestAndResult.RegisterRequest;
import com.google.gson.Gson;
import model.AuthData;
import service.CreateGameService;
import service.RegisterService;
import spark.Request;
import spark.Response;

public class CreateGameHandler {
    CreateGameService myService = new CreateGameService();
    public Object newGame(Request req, Response res) {
        String authString= req.headers("Authorization");
        AuthData myAuth;
        if (authString != null) {
            myAuth = new AuthData(authString);
        }
        else {
            //res.status(401);
            return new Gson().toJson(new CreateResult("Error: No authToken provided"));
        }

        String myJSON = req.body();
        CreateRequest newRequest = new Gson().fromJson(myJSON, CreateRequest.class);
        newRequest.setMyToken(myAuth);

        CreateResult myResult = myService.newGame(newRequest);
        if (myResult.getMessage() != null) {
            res.status(401);
        }
        return new Gson().toJson(myResult);
    }
}
