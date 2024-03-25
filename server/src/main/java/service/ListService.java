package service;

import model.Request.ListRequest;
import model.Result.ListResult;
import dataAccess.*;
import model.AuthData;

import java.sql.SQLException;

public class ListService {
    private GameSQLDAO gameAccess = new GameSQLDAO();
    private AuthSQLDAO tokenAccess = new AuthSQLDAO();
    public ListResult listGames(ListRequest myRequest) {
        AuthData myToken = myRequest.getMyToken();
        try {
            tokenAccess.verifyToken(myToken);
            ListResult myResult = new ListResult(gameAccess.getMyGames()); //handler will have to convert this list to strings
            return myResult;
        } catch (DataAccessException | SQLException e) { //| SQLException e
            //e.printStackTrace();
            return new ListResult(e.getMessage());
        }
    }
}
