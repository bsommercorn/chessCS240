package service;

import Request.LoginRequest;
import Result.LoginResult;
import dataAccess.AuthDAO;
import dataAccess.DataAccessException;
import dataAccess.UserDAO;
import model.UserData;

public class LoginService {
    UserDAO userAccess = new UserDAO();
    AuthDAO tokenAccess = new AuthDAO();

    public LoginResult newLogin(LoginRequest myRequest) {
        String username = myRequest.getUsername();
        String password = myRequest.getPassword();
        UserData myUser = null;
        try {
            myUser = userAccess.verifyUser(username, password);
        } catch (DataAccessException e) { // | SQLException e
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
