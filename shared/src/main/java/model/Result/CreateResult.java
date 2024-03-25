package model.Result;

public class CreateResult {
    Integer gameID; //maybe change this to int gameID = -1 or something???
    String message;
    public int getGameID() {
        return gameID;
    }
    public String getMessage() {
        return message;
    }

    public CreateResult(int gameID) { //success constructor
        this.gameID = gameID;
    }
    public CreateResult(String exception) { //fail, error constructor
        this.message = exception;
    }

    public String toString() {
        if (gameID != null) {
            return "{ \"gameID\": " + gameID + " }";
        }
        else {
            return "{ \"message\":\"" + message + "\" }";
        }

    }
}
