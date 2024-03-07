package dataAccess;

import model.AuthData;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Random;

public class AuthDAO {
    //private static ArrayList<Game> myGames = new ArrayList<Game>();
    private static ArrayList<AuthData> myTokens = new ArrayList<AuthData>();

    public AuthDAO() {
        try {
            configureDatabase();
        } catch (DataAccessException e) {
            //throw new RuntimeException(e);
            //do nothing
        }
    }

    public AuthData createAuth(String username) throws DataAccessException{
        Random rand = new Random();
        String newToken = username + "/" + LocalDateTime.now() + rand.nextInt(500);
        AuthData myToken = new AuthData(newToken, username);
        if (!myTokens.contains(myToken)) {
            myTokens.add(myToken);
        } else {
            throw new DataAccessException("Error: AuthToken already exists");
        }
        return myToken;
    }

    public void clearAll() {
        myTokens = new ArrayList<AuthData>();
    }

    public void verifyToken(AuthData myToken) throws DataAccessException {
        if (!myTokens.contains(myToken)) {
            throw new DataAccessException("Error: unauthorized");
        }
    }

    public void deleteToken(AuthData myToken) throws DataAccessException{
        if (myTokens.contains(myToken)) {
            myTokens.remove(myToken);
        }
        else {
            throw new DataAccessException("Error: AuthToken not found");
        }
    }

    public int getSize() {
        return myTokens.size();
    }

    private final String[] createStatements = { //fix to make like real auth code
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

    private void configureDatabase() throws DataAccessException {
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
