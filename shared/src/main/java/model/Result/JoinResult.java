package model.Result;

import model.GameData;

public class JoinResult {
    GameData myGame;
    String message;

    public JoinResult(GameData newGame) {
        this.myGame = newGame;
    }
    public JoinResult(String exception) {
        this.message = exception;
    }

    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public GameData getMyGame() {
        return myGame;
    }

    public String toString() {
        if (message != null) {
            return "{ \"message\":\"" + message + "\" }";
        }
        else {
            return "{}";
        }

    }
}
