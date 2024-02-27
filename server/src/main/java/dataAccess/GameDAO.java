package dataAccess;

import model.GameData;

import java.util.ArrayList;

public class GameDAO {
    private static ArrayList<GameData> myGames = new ArrayList<GameData>();
    public void clearAll() {
        myGames = new ArrayList<GameData>();
    }

    public int numGamesMade() {
        return myGames.size();
    }

    public void createGame(GameData myGame) {
        myGames.add(myGame);
    }

    public ArrayList<GameData> getMyGames() {
        return myGames;
    }

    public GameData findGame(int gameID) {
        return myGames.get(gameID - 1);
    }
    //create
    //read
    //update
    //delete
}
