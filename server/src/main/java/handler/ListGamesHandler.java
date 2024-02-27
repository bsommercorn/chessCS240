package handler;

import RequestAndResult.ListRequest;
import RequestAndResult.ListResult;
import RequestAndResult.RegisterRequest;
import com.google.gson.Gson;
import service.ListService;
import service.RegisterService;
import spark.Request;
import spark.Response;

public class ListGamesHandler {
    ListService myService = new ListService();
    public Object getList(Request req, Response res) {
        String myJSON = req.body();
        ListRequest newRequest = new Gson().fromJson(myJSON, ListRequest.class);

        ListResult myResult = myService.listGames(newRequest);
        return new Gson().toJson(myResult);
    }
}
