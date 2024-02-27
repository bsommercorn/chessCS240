package service;

import RequestAndResult.RegisterRequest;
import RequestAndResult.RegisterResult;
import dataAccess.AuthDAO;
import dataAccess.DataAccessException;
import dataAccess.UserDAO;
import model.UserData;
import spark.Request;
import spark.Response;

import java.sql.SQLException;


public class RegisterService {

    UserDAO userAccess = new UserDAO();

    AuthDAO tokenAccess = new AuthDAO();

    public RegisterResult newRegister(RegisterRequest newRequest) {
        String username = newRequest.getUsername();

        RegisterResult myResult = new RegisterResult();

        UserData newUser = new UserData(newRequest.getUsername(), newRequest.getPassword(), newRequest.getEmail());

        try {
            userAccess.addUser(newUser);
            myResult.setMyToken(tokenAccess.createAuth(username));
            return myResult;
        } catch (DataAccessException e) { //| SQLException e
            myResult.setMessage(e.getMessage());
            return myResult;
        }
    }
}
