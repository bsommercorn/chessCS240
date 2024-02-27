package RequestAndResult;

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

    public void setMyGame(GameData myGame) {
        this.myGame = myGame;
    }
}
