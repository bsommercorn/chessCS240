package handler;

import model.Result.ClearResult;
import com.google.gson.Gson;
import service.ClearService;
import spark.Request;
import spark.Response;

public class ClearHandler {
    ClearService myService = new ClearService();
    public Object clearAll(Request req, Response res) {
        myService.clearAll();
        return new Gson().toJson(new ClearResult());
    }
}
