package handler;

import RequestAndResult.*;
import com.google.gson.Gson;
import model.AuthData;
import service.JoinService;
import service.ListService;
import spark.Request;
import spark.Response;

public class JoinGameHandler {
    JoinService myService = new JoinService();
    public Object joinGame(Request req, Response res) {
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
        JoinRequest newRequest = new Gson().fromJson(myJSON, JoinRequest.class);
        newRequest.setMyToken(myAuth);

        JoinResult myResult = myService.joinGame(newRequest);
        if (myResult.getMessage() != null) {
            System.out.println("Error code was: " + myResult.getMessage());
            if (myResult.getMessage() == "Error: bad request") {
                res.status(400);
            }
            if (myResult.getMessage() == "Error: unauthorized") {
                res.status(401);
            }
            if (myResult.getMessage() == "Error: already taken") {
                res.status(403);
            }
            if (myResult.getMessage() == "Error: bad color") {
                res.status(403);
            }
        }
        return new Gson().toJson(myResult);
    }
}
