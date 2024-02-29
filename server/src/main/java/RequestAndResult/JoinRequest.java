package RequestAndResult;

import chess.ChessGame;
import model.AuthData;

import java.util.Objects;

public class JoinRequest {
    ChessGame.TeamColor playerColor = null;
    AuthData myToken = null;
    boolean badColor = false;

    public JoinRequest(String playerColor, int gameID) {
        if (Objects.equals(playerColor, "BLACK")) {
            this.playerColor = ChessGame.TeamColor.BLACK;
        } else if (Objects.equals(playerColor, "WHITE")) {
            this.playerColor = ChessGame.TeamColor.WHITE;
        }
        else if (playerColor != null) {
            badColor = true;
        }

        this.gameID = gameID;
    }

    public boolean isBadColor() {
        return badColor;
    }
    int gameID;
    public JoinRequest(AuthData myToken, String playerColor, int gameID) {//fix???
        this.myToken = myToken;
        //include player authtoken????
        if (Objects.equals(playerColor, "BLACK")) {
            this.playerColor = ChessGame.TeamColor.BLACK;
        } else if (Objects.equals(playerColor, "WHITE")) {
            this.playerColor = ChessGame.TeamColor.WHITE;
        }
        else if (playerColor != null) {
            badColor = true;
        }

        this.gameID = gameID;
    }
    public JoinRequest() {}
    public AuthData getMyToken() {
        return myToken;
    }
    public ChessGame.TeamColor getPlayercolor() {
        return playerColor;
    }
    public int getGameID() {
        return gameID;
    }

    public void setMyToken(AuthData myToken) {
        this.myToken = myToken;
    }

}
