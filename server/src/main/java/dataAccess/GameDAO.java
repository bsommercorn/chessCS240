package dataAccess;

import model.GameData;

import java.util.ArrayList;
import java.util.List;

public class GameDAO {
    private static List<GameData> myGames = new ArrayList<GameData>();
    public void clearAll() {
        myGames = new ArrayList<GameData>();
    }

    public int numGamesMade() {
        return (myGames.size() + 100);
    }

    public void createGame(GameData myGame) {
        myGames.add(myGame);
    }

    public List<GameData> getMyGames() {
        if (myGames == null) {
            myGames = new ArrayList<GameData>();
        }
        return myGames;
    }

    public GameData findGame(int gameIDtry) throws DataAccessException {
        int gameID = gameIDtry - 100;
        if (gameID >= 0 && gameID < myGames.size()) {
            if (myGames.get(gameID) != null) {
                return myGames.get(gameID);
            }
            else {
                throw new DataAccessException("Error: bad request");
            }
        }
        else {
            throw new DataAccessException("Error: bad request");
        }
    }
    //create
    //read
    //update
    //delete
}
