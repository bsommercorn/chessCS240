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

    public List<GameData> getMyGames() throws SQLException, DataAccessException { //this NEEDS to be fixed
        List<GameTag> myGames = new ArrayList<GameTag>();

        var myConnection = chessDB.getConnection();
        GameTag returnGame = new GameTag();
        var statement = "SELECT * FROM games";
        try (var preparedStatement = myConnection.prepareStatement(statement)) {
            //preparedStatement.setString(1, myToken.getUsername());
            ResultSet myReturn = preparedStatement.executeQuery();
            //System.out.println(myReturn.toString());
            //found = true;
            while (myReturn.next()) {
                returnGame.setGameID(myReturn.getInt("gameID"));
                returnGame.setGameName(myReturn.getString("gameName"));
                returnGame.setWhiteUsername(myReturn.getString("whiteUsername"));
                returnGame.setBlackUsername(myReturn.getString("blackUsername"));
                //returnGame.setGame(new chess.Game());//FIX THIS LATER
                String myState = myReturn.getString("gamestate"); //FIX THIS LATER, convert string to game
                //System.out.println("gamestate retrieved from database was: " + myState);
                returnGame.setServerBoard(myState); //instead of this, convert it to a real board

                myGames.add(returnGame);
                returnGame = new GameTag();
            }
        }
        finally {
            chessDB.closeConnection(myConnection);
        }
        return myGames;
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
