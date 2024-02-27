package handler;

import RequestAndResult.JoinRequest;
import RequestAndResult.JoinResult;
import RequestAndResult.ListRequest;
import RequestAndResult.ListResult;
import com.google.gson.Gson;
import service.JoinService;
import service.ListService;
import spark.Request;
import spark.Response;

public class JoinGameHandler {
    JoinService myService = new JoinService();
    public Object joinGame(Request req, Response res) {
        String myJSON = req.body();
        JoinRequest newRequest = new Gson().fromJson(myJSON, JoinRequest.class);

        JoinResult myResult = myService.joinGame(newRequest);
        return new Gson().toJson(myResult);
    }
}
