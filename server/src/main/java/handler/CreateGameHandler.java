package handler;

import RequestAndResult.CreateRequest;
import RequestAndResult.CreateResult;
import RequestAndResult.RegisterRequest;
import com.google.gson.Gson;
import service.CreateGameService;
import service.RegisterService;
import spark.Request;
import spark.Response;

public class CreateGameHandler {
    CreateGameService myService = new CreateGameService();
    public Object newGame(Request req, Response res) {
        String myJSON = req.body();
        CreateRequest newRequest = new Gson().fromJson(myJSON, CreateRequest.class);

        CreateResult myResult = myService.newGame(newRequest);
        return new Gson().toJson(myResult);
    }
}
