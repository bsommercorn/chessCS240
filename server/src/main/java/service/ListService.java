package service;

import Request.ListRequest;
import Result.ListResult;
import dataAccess.AuthDAO;
import dataAccess.AuthSQLDAO;
import dataAccess.DataAccessException;
import dataAccess.GameDAO;
import model.AuthData;

import java.sql.SQLException;

public class ListService {
    private GameDAO gameAccess = new GameDAO();
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
