package service;

import model.Request.RegisterRequest;
import model.Result.RegisterResult;
import dataAccess.*;
import model.UserData;

import java.sql.SQLException;


public class RegisterService {

    UserSQLDAO userAccess = new UserSQLDAO();

    AuthDAO tokenAccess = new AuthDAO();
    AuthSQLDAO tokenSQLAccess = new AuthSQLDAO();

    public RegisterResult newRegister(RegisterRequest newRequest) {
        String username = newRequest.getUsername();

        RegisterResult myResult = new RegisterResult();

        UserData newUser = new UserData(newRequest.getUsername(), newRequest.getPassword(), newRequest.getEmail());

        try {
            userAccess.addUser(newUser);
            myResult.setMyToken(tokenSQLAccess.createAuth(username));
            return myResult;
        } catch (DataAccessException | SQLException e) { //SQLexception
            myResult.setMessage(e.getMessage());
            return myResult;
        }
    }
}
