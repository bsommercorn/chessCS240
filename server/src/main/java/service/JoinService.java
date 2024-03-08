package service;

import Request.JoinRequest;
import Result.JoinResult;
import chess.ChessGame;
import dataAccess.AuthDAO;
import dataAccess.AuthSQLDAO;
import dataAccess.DataAccessException;
import dataAccess.GameDAO;
import model.AuthData;
import model.GameData;

import java.sql.SQLException;

public class JoinService {
    private GameDAO gameAccess = new GameDAO();
    private AuthSQLDAO tokenAccess = new AuthSQLDAO();
    public JoinResult joinGame(JoinRequest myRequest) {
        AuthData myToken = myRequest.getMyToken();
        try {
            tokenAccess.verifyToken(myToken);
            if (myRequest.getPlayercolor() == null) {//for viewers
                GameData myGame = gameAccess.findGame(myRequest.getGameID());
                if (myRequest.isBadColor()) {
                    return new JoinResult("Error: bad color");
                }
                JoinResult myResult = new JoinResult(myGame);
                return myResult;
            }
            else {
                GameData myGame = gameAccess.findGame(myRequest.getGameID());
                //get the player color, and check if this game has a blank spot in that color
                if (myRequest.getPlayercolor() == ChessGame.TeamColor.BLACK) {
                    if (myGame.getBlackUsername() != null) {
                        JoinResult myResult = new JoinResult("Error: already taken");
                        return myResult;
                    }
                    else {
                        myGame.setBlackUsername(myRequest.getMyToken().getUser());
                        //gameAccess.update(myGame.getGameID(), myGame); //update this game in database
                    }
                } else if (myRequest.getPlayercolor() == ChessGame.TeamColor.WHITE) {
                    if (myGame.getWhiteUsername() != null) {
                        JoinResult myResult = new JoinResult("Error: already taken");
                        return myResult;
                    }
                    else {
                        myGame.setWhiteUsername(myRequest.getMyToken().getUser());
                        //gameAccess.update(myGame.getGameID(), myGame);
                    }
                }
                JoinResult myResult = new JoinResult(myGame);
                return myResult;
            }
        } catch (DataAccessException | SQLException e) { // | SQLException e
            //e.printStackTrace();
            return new JoinResult(e.getMessage());
        }
    }
}
