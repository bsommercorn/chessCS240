package service;

import RequestAndResult.CreateRequest;
import RequestAndResult.CreateResult;
import dataAccess.AuthDAO;
import dataAccess.DataAccessException;
import dataAccess.GameDAO;
import model.AuthData;
import model.GameData;
import spark.Request;
import spark.Response;

import java.sql.SQLException;

public class CreateGameService {

    private GameDAO gameAccess = new GameDAO();
    private AuthDAO tokenAccess = new AuthDAO();

    public CreateResult newGame(CreateRequest myRequest) {
        AuthData myToken = myRequest.getToken();
        try {
            if (myRequest.getGameName() == null) {
                return new CreateResult("Error: gameName was null");
            }
            tokenAccess.verifyToken(myToken);
            GameData myGame = new GameData(gameAccess.numGamesMade(), myRequest.getGameName());
            gameAccess.createGame(myGame);
            CreateResult myResult = new CreateResult(myGame.getGameID());
            return myResult;
        } catch (DataAccessException e) { //| SQLException e
            e.printStackTrace();
            return new CreateResult(e.getMessage());
        }
    }
}
