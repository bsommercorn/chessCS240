package service;

import Request.LogoutRequest;
import Result.LogoutResult;
import dataAccess.AuthDAO;
import dataAccess.DataAccessException;
import model.AuthData;

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
