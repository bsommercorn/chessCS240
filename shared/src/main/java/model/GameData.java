package model;

import chess.ChessGame;

public class GameData {
    int gameID;
    String whiteUsername;
    String blackUsername;
    String gameName;
    //ChessGame game; //either convert this early, or only store strings in the first place
    String boardState = "Br,Bn,Bb,Bq,Bk,Bb,Bn,Br,Bp,Bp,Bp,Bp,Bp,Bp,Bp,Bp, , , , , , , , , , , , , , , , , , , , , , , , , , , , , , , , ,Wp,Wp,Wp,Wp,Wp,Wp,Wp,Wp,Wr,Wn,Wb,Wq,Wk,Wb,Wn,Wr,";

    public GameData(int gameID, String whiteUsername, String blackUsername, String gameName, ChessGame game) {
        this.gameID = gameID;
        this.whiteUsername = whiteUsername;
        this.blackUsername = blackUsername;
        this.gameName = gameName;
        //this.game = game;
    }

    public GameData(int gameID, String gameName) {
        this.gameID = gameID;
        this.gameName = gameName;
    }

    public GameData() {
    }

    public int getGameID() {
        return gameID;
    }

    public String getWhiteUsername() {
        return whiteUsername;
    }

    public void setWhiteUsername(String whiteUsername) {
        this.whiteUsername = whiteUsername;
    }

    public String getBlackUsername() {
        return blackUsername;
    }

    public void setBlackUsername(String blackUsername) {
        this.blackUsername = blackUsername;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameID(int gameID) {
        this.gameID = gameID;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public String getBoardState() {
        return boardState;
    }
    public void setBoardState(String boardState) {
        this.boardState = boardState;
    }
}
