package RequestAndResult;

import chess.ChessGame;
import model.GameData;

import java.util.ArrayList;
import java.util.List;

public class ListResult {
    List<GameData> games = new ArrayList<GameData>(0);

    public List<GameData> getMyGames() {
        return games;
    }

    String message;
    public ListResult(List<GameData> myGames) {
        this.games = myGames;
    }
    public ListResult(String exception) {
        this.message = exception;
    }
    public String getMessage() {
        return message;
    }
}
