package handler;

import RequestAndResult.RegisterRequest;
import RequestAndResult.RegisterResult;
import com.google.gson.Gson;
import service.RegisterService;
import spark.Request;
import spark.Response;

public class RegisterHandler {
    RegisterService myService = new RegisterService();
    public Object register(Request req, Response res) {
        String myJSON = req.body();
        RegisterRequest newRequest = new Gson().fromJson(myJSON, RegisterRequest.class);

        RegisterResult myResult = myService.newRegister(newRequest);
        return new Gson().toJson(myResult);
    }
}
