package dataAccess;

import model.GameData;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GameSQLDAO {
    public GameSQLDAO() {
        try {
            configureDatabase();
        } catch (DataAccessException e) {
            e.printStackTrace();
            //do nothing
        }
    }

    public int storageSize() {
        int mycount = 0;
        try (var conn = DatabaseManager.getConnection()) {
            var statement = "SELECT COUNT(*) FROM games;";
            try (var ps = conn.prepareStatement(statement)) {
                ResultSet myResult = ps.executeQuery();
                System.out.println(myResult.toString());
                while(myResult.next()) {
                    mycount = myResult.getInt("COUNT(*)");
                }
                return mycount;
            }
        }
        catch (DataAccessException | SQLException e) {
            //ignore
        }
        return -1;
    }

    public int getGamesMade() throws DataAccessException {
        return (this.storageSize() + 100);
    }

    public List<GameData> getMyGames() throws SQLException, DataAccessException { //this NEEDS to be fixed
        List<GameData> myGames = new ArrayList<GameData>();
        GameData returnGame = new GameData();

        try (var conn = DatabaseManager.getConnection()) {
            var statement = "SELECT * FROM games";
            try (var ps = conn.prepareStatement(statement)) {
                ResultSet myReturn = ps.executeQuery();
                while (myReturn.next()) {
                    returnGame.setGameID(myReturn.getInt("gameID"));
                    returnGame.setGameName(myReturn.getString("gameName"));
                    returnGame.setWhiteUsername(myReturn.getString("whiteUsername"));
                    returnGame.setBlackUsername(myReturn.getString("blackUsername"));
                    myGames.add(returnGame);
                    returnGame = new GameData();
                }
            }
        }
        return myGames;
    }

    public void createGame (GameData myGame) throws DataAccessException, SQLException {
        String gameName = myGame.getGameName();
        int gameID = myGame.getGameID();

        String saveName = gameName;
        gameName = gameName.replaceAll("[;\\)\\(]+", "");
        if (saveName != gameName) {
            throw new DataAccessException("Error: bad request");
        }
        try (var conn = DatabaseManager.getConnection()) {
            var statement = "INSERT INTO games (gameID, gameName, gamestate) VALUES(?,?,?)";
            try (var ps = conn.prepareStatement(statement)) {
                ps.setInt(1, gameID);
                ps.setString(2, gameName);
                ps.setString(3, myGame.getBoardState());
                ps.executeUpdate();
            }
        }
    }

    public void update(int myID, GameData replacement) throws DataAccessException, SQLException {
        int gameID = replacement.getGameID();
        String gameName = replacement.getGameName();
        String usernameW = replacement.getWhiteUsername();
        String usernameB = replacement.getBlackUsername();
        String gameJSON = replacement.getBoardState();

        String saveName = gameName;
        gameName = gameName.replaceAll("[;\\)\\(]+", "");
        if (saveName != gameName) {
            throw new DataAccessException("Error: bad request");
        }

        try (var conn = DatabaseManager.getConnection()) {
            var statement = "UPDATE games SET whiteUsername=?, blackUsername=?, gameName=?, gamestate=? WHERE gameID='" + myID + "'";
            try (var ps = conn.prepareStatement(statement)) {
                ps.setString(1, usernameW);
                ps.setString(2, usernameB);
                ps.setString(3, gameName);
                ps.setString(4, gameJSON);
                ps.executeUpdate();
            }
        }
    }

    public GameData findGame(int getGame) throws DataAccessException, SQLException {
        String usernameW;
        String usernameB;
        String myName = "fakename";
        GameData returnGame = new GameData();
        returnGame.setGameID(getGame);
        boolean found = false;
        try (var conn = DatabaseManager.getConnection()) {
            var statement = "SELECT gameID, whiteUsername, blackUsername, gameName, gamestate FROM games WHERE gameID='" + getGame + "' LIMIT 1;";
            try (var ps = conn.prepareStatement(statement)) {
                ResultSet myReturn = ps.executeQuery();
                while (myReturn.next()) {
                    returnGame.setGameName(myReturn.getString("gameName"));
                    returnGame.setWhiteUsername(myReturn.getString("whiteUsername"));
                    returnGame.setBlackUsername(myReturn.getString("blackUsername"));
                    returnGame.setBoardState(myReturn.getString("gamestate"));
                    found = true;
                }
            }
        }
        if (found) {
            return returnGame;
        }
        else {
            throw new DataAccessException("Error: bad request");
        }
    }

    public void clearAll() throws DataAccessException, SQLException {
        try (var conn = DatabaseManager.getConnection()) {
            var statement = "TRUNCATE TABLE games;";
            try (var ps = conn.prepareStatement(statement)) {
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            //do nothing
        }
    }

    private final String[] createStatements = { //fix to match gameDAO code & corresponding table
            """
            CREATE TABLE IF NOT EXISTS  games (
              `id` int NOT NULL AUTO_INCREMENT,
              `gameID` int NOT NULL,
              `whiteUsername` varchar(256),
              `blackUsername` varchar(256),
              `gameName` varchar(256),
              `gameState` varchar(256),
              PRIMARY KEY (`id`)
            ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
            """
    };

    private void configureDatabase() throws DataAccessException {
        DatabaseManager.createDatabase();
        try (var conn = DatabaseManager.getConnection()) {
            for (var statement : createStatements) {
                try (var preparedStatement = conn.prepareStatement(statement)) {
                    preparedStatement.executeUpdate();
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            //throw new DataAccessException(500, String.format("Unable to configure database: %s", ex.getMessage()));
            throw new DataAccessException("Unable to configure database: %s");
        }
    }
}
