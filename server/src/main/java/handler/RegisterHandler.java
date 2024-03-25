package handler;

import model.Request.RegisterRequest;
import model.Result.RegisterResult;
import com.google.gson.Gson;
import service.RegisterService;
import spark.Request;
import spark.Response;

public class RegisterHandler {
    RegisterService myService = new RegisterService();
    public Object register(Request req, Response res) {
        String myJSON = req.body();
        RegisterRequest newRequest = new Gson().fromJson(myJSON, RegisterRequest.class);
        if (newRequest.getUsername() == null || newRequest.getPassword() == null || newRequest.getEmail() == null) {
            res.status(400);
            System.out.println("Bad request from null strings passed in to Register");
            return new Gson().toJson(new RegisterResult("Error: null input strings"));
        }

        RegisterResult myResult = myService.newRegister(newRequest);
        if (myResult.getMessage() != null) {
            res.status(403);
        }
        return new Gson().toJson(myResult);
    }
}
