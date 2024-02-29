package service;

import RequestAndResult.ListRequest;
import RequestAndResult.ListResult;
import dataAccess.AuthDAO;
import dataAccess.DataAccessException;
import dataAccess.GameDAO;
import model.AuthData;
import spark.Request;
import spark.Response;

import java.sql.SQLException;

public class ListService {
    private GameDAO gameAccess = new GameDAO();
    private AuthDAO tokenAccess = new AuthDAO();
    public ListResult listGames(ListRequest myRequest) {
        AuthData myToken = myRequest.getMyToken();
        try {
            tokenAccess.verifyToken(myToken);
            ListResult myResult = new ListResult(gameAccess.getMyGames()); //handler will have to convert this list to strings
            return myResult;
        } catch (DataAccessException e) { //| SQLException e
            //e.printStackTrace();
            return new ListResult(e.getMessage());
        }
    }
}
