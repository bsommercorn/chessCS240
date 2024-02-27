package RequestAndResult;

import chess.ChessGame;
import model.GameData;

import java.util.ArrayList;

public class ListResult {
    ArrayList<GameData> myGames;
    String message;
    public ListResult(ArrayList<GameData> myGames) {
        this.myGames = myGames;
    }
    public ListResult(String exception) {
        this.message = exception;
    }
    public ListResult(){}
}
