package service;

import model.Request.LoginRequest;
import model.Result.LoginResult;
import dataAccess.*;
import model.UserData;

import java.sql.SQLException;

public class LoginService {
    UserSQLDAO userAccess = new UserSQLDAO();
    AuthSQLDAO tokenAccess = new AuthSQLDAO();

    public LoginResult newLogin(LoginRequest myRequest) {
        String username = myRequest.getUsername();
        String password = myRequest.getPassword();
        UserData myUser = null;
        try {
            myUser = userAccess.verifyUser(username, password);
        } catch (DataAccessException | SQLException e) { // | SQLException e
            LoginResult myResult = new LoginResult(e.getMessage());
            return myResult;
        }

        if (myUser != null) {
            LoginResult myResult = null;
            try {
                myResult = new LoginResult(tokenAccess.createAuth(username));
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException(); //FIX LATER
            }
            return myResult;
        }
        else { //password was not a match
            LoginResult myResult = new LoginResult("Error: unauthorized");
            return myResult;
        }
    }
}
