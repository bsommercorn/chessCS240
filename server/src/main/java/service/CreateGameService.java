package service;

import model.Request.CreateRequest;
import model.Result.CreateResult;
import dataAccess.*;
import model.AuthData;
import model.GameData;

import java.sql.SQLException;

public class CreateGameService {

    private GameSQLDAO gameAccess = new GameSQLDAO();
    private AuthSQLDAO tokenAccess = new AuthSQLDAO();

    public CreateResult newGame(CreateRequest myRequest) {
        AuthData myToken = myRequest.getToken();
        try {
            if (myRequest.getGameName() == null) {
                return new CreateResult("Error: gameName was null");
            }
            tokenAccess.verifyToken(myToken);
            GameData myGame = new GameData(gameAccess.getGamesMade(), myRequest.getGameName());
            gameAccess.createGame(myGame);
            CreateResult myResult = new CreateResult(myGame.getGameID());
            return myResult;
        } catch (DataAccessException | SQLException e) {
            //e.printStackTrace();
            return new CreateResult(e.getMessage());
        }
    }
}
