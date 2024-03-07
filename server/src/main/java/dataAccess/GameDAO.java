package dataAccess;

import model.GameData;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GameDAO {
    private static List<GameData> myGames = new ArrayList<GameData>();

    public GameDAO() {
        try {
            configureDatabase();
        } catch (DataAccessException e) {
            //throw new RuntimeException(e);
            //do nothing
        }
    }

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

    public int getSize() {
        return myGames.size();
    }

    private final String[] createStatements = { //fix to match gameDAO code & corresponding table
            """
            CREATE TABLE IF NOT EXISTS  pet (
              `id` int NOT NULL AUTO_INCREMENT,
              `name` varchar(256) NOT NULL,
              `type` ENUM('CAT', 'DOG', 'FISH', 'FROG', 'ROCK') DEFAULT 'CAT',
              `json` TEXT DEFAULT NULL,
              PRIMARY KEY (`id`),
              INDEX(type),
              INDEX(name)
            ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
            """
    };

    private void configureDatabase() throws DataAccessException { //have the constructor call this
        DatabaseManager.createDatabase();
        try (var conn = DatabaseManager.getConnection()) {
            for (var statement : createStatements) {
                try (var preparedStatement = conn.prepareStatement(statement)) {
                    preparedStatement.executeUpdate();
                }
            }
        } catch (SQLException ex) {
            //throw new DataAccessException(500, String.format("Unable to configure database: %s", ex.getMessage()));
            throw new DataAccessException("Unable to configure database: %s");
        }
    }
}
