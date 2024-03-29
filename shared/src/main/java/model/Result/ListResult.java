package model.Result;

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

    @Override
    public String toString() { //Do NOT touch or change this one
        if (message != null) {
            return "{ \"message\":\"" + message + "\" }";
        }
        String output = "{ \"games\":[";
        if (games != null) {
            for (int i = 0; i < games.size(); i++) {
                output = output + "{\"gameID\": " + games.get(i).getGameID();
                output = output + ", \"whiteUsername\":\"" + games.get(i).getWhiteUsername() + "\"";
                output = output + ", \"blackUsername\":\"" + games.get(i).getBlackUsername() + "\"";
                output = output + ", \"gameName\":\"" + games.get(i).getGameName() + "\"} ";
                if (i > 0 && i < games.size() - 1) {
                    output = output + ", ";
                }
            }
        }
        output = output + " ]}";

        return output;
    }

    public String printString() { //Do NOT touch or change this one
        if (message != null) {
            return "{ \"message\":\"" + message + "\" }";
        }
        String output = "{ \"games\":[";
        if (games != null) {
            for (int i = 0; i < games.size(); i++) {
                output = output + "\n{\"gameID\": " + games.get(i).getGameID();
                output = output + ",\n \"whiteUsername\":\"" + games.get(i).getWhiteUsername() + "\"";
                output = output + ",\n \"blackUsername\":\"" + games.get(i).getBlackUsername() + "\"";
                output = output + ",\n \"gameName\":\"" + games.get(i).getGameName() + "\"} ";
                output = output + ",\n \"boardState\":\"" + games.get(i).getBoardState() + "\"} ";
                if (i > 0 && i < games.size() - 1) {
                    output = output + ", ";
                }
            }
        }
        output = output + " ]}";

        return output;
    }
}
