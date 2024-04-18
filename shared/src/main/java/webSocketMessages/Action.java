package webSocketMessages;

import chess.ChessGame;
import com.google.gson.Gson;

public record Action(Type type, ChessGame gameUpdate, Integer gameID) {
    public enum Type {
        MOVE,
        EXIT
    }

    public String toString() {
        return new Gson().toJson(this);
    }
}
