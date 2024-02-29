package handler;

import RequestAndResult.CreateResult;
import RequestAndResult.ListRequest;
import RequestAndResult.ListResult;
import RequestAndResult.RegisterRequest;
import com.google.gson.Gson;
import model.AuthData;
import service.ListService;
import service.RegisterService;
import spark.Request;
import spark.Response;

public class ListGamesHandler {
    ListService myService = new ListService();
    public Object getList(Request req, Response res) {
        String authString= req.headers("Authorization");
        AuthData myAuth;
        if (authString != null) {
            myAuth = new AuthData(authString);
        }
        else {
            //res.status(401);
            return new Gson().toJson(new CreateResult("Error: No authToken provided"));
        }

        ListRequest newRequest = new ListRequest(myAuth);
        ListResult myResult = myService.listGames(newRequest);
        if (myResult.getMessage() != null) {
            res.status(401);
        }
        System.out.println(new Gson().toJson(myResult));
        return new Gson().toJson(myResult);
        //return myResult.toString();
    }
}
