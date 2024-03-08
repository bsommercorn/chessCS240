package service;

import Request.LogoutRequest;
import Result.LogoutResult;
import dataAccess.AuthDAO;
import dataAccess.AuthSQLDAO;
import dataAccess.DataAccessException;
import model.AuthData;

import java.sql.SQLException;

public class LogoutService {
    AuthSQLDAO tokenAccess = new AuthSQLDAO();
    public LogoutResult logout(LogoutRequest myRequest) {
        AuthData myToken = myRequest.getMyToken();
        try {
            tokenAccess.deleteToken(myToken); //make sure this actually deletes
        } catch (DataAccessException | SQLException e) { // | SQLException e
            return new LogoutResult(e.getMessage());
        }
        return new LogoutResult();
    }
}
