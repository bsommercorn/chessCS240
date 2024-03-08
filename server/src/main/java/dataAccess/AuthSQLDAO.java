package dataAccess;

import model.AuthData;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class AuthSQLDAO {
    DatabaseManager chessDB = new DatabaseManager();
    public AuthSQLDAO() {
        try {
            configureDatabase();
        } catch (DataAccessException e) {
            e.printStackTrace();
            //throw new RuntimeException(e);
            //do nothing
        }
    }
    public int storageSize() {
        int mycount = 0;

        try (var conn = DatabaseManager.getConnection()) {
            var statement = "SELECT COUNT(0) FROM tokens;";
            try (var ps = conn.prepareStatement(statement)) {
                mycount = ps.executeUpdate();
            }
        } catch (SQLException | DataAccessException e) {
            //throw new DataAccessException("Failed to create, invalid token");
        }
        return mycount;
    }
    public AuthData createAuth (String username) throws SQLException, DataAccessException {
        String newToken = username + "/" + LocalDateTime.now();

        try (var conn = DatabaseManager.getConnection()) {
            var statement = "INSERT INTO tokens (authtoken, username) VALUES(?,?)";
            try (var ps = conn.prepareStatement(statement)) {
                ps.setString(1, newToken);
                ps.setString(2, username);
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            throw new DataAccessException("Failed to create, invalid token");
        }
        return new AuthData(newToken, username);
    }

    public void verifyToken(AuthData myToken) throws DataAccessException, SQLException { //use throwing?
        boolean found = false;
        if (myToken != null) {
            try (var conn = DatabaseManager.getConnection()) {
                var statement = "SELECT authtoken, username FROM tokens WHERE authToken='" + myToken.getAuthToken() + "' LIMIT 1;";
                try (var ps = conn.prepareStatement(statement)) {
                    ResultSet myReturn = ps.executeQuery();
                    System.out.println(myReturn.toString());
                    if (myReturn.next()) { //this won't be null, even if we didn't find anything that matches
                        found = true;
                    }
                }
            } catch (SQLException e) {
                throw new DataAccessException("Failed to create, invalid token");
            }
        }
        if (!found) {
            throw new DataAccessException("Error: unauthorized");
        }
    }

    public void deleteToken(AuthData myToken) throws DataAccessException, SQLException { //considered unauthorized if the AuthToken was invalid (wasn't in our database)
        boolean found = false;
        if (myToken != null) {
            try (var conn = DatabaseManager.getConnection()) {
                var statement = "DELETE FROM tokens WHERE authToken='" + myToken.getAuthToken() + "';";
                try (var ps = conn.prepareStatement(statement)) {
                    int i = ps.executeUpdate();
                    if (i != 0) {
                        found = true;
                    }
                }
            } catch (SQLException e) {
                throw new DataAccessException("Failed to create, invalid token");
            }
        }
        if (!found) {
            throw new DataAccessException("Error: unauthorized");
        }
    }

    public void clearAll() throws DataAccessException, SQLException {
        try (var conn = DatabaseManager.getConnection()) {
            var statement = "TRUNCATE TABLE tokens;";
            try (var ps = conn.prepareStatement(statement)) {
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            //do nothing
        }
    }

    private final String[] createStatements = { //username is good, but authtoken needs to include numbers
            """
            CREATE TABLE IF NOT EXISTS  tokens (
              `id` int NOT NULL AUTO_INCREMENT,
              `authtoken` varchar(256) NOT NULL,
              `username` varchar(256) NOT NULL,
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
