package service;

import RequestAndResult.LogoutRequest;
import RequestAndResult.LogoutResult;
import dataAccess.AuthDAO;
import dataAccess.DataAccessException;
import model.AuthData;
import spark.Request;
import spark.Response;

import java.sql.SQLException;

public class LogoutService {
    AuthDAO tokenAccess = new AuthDAO();
    public LogoutResult logout(LogoutRequest myRequest) {
        AuthData myToken = myRequest.getMyToken();
        try {
            tokenAccess.deleteToken(myToken); //make sure this actually deletes
        } catch (DataAccessException e) { // | SQLException e
            return new LogoutResult(e.getMessage());
        }
        return new LogoutResult();
    }
}
