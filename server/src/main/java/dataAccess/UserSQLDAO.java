package dataAccess;

import model.UserData;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserSQLDAO {
    public UserSQLDAO() {
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
            var statement = "SELECT COUNT(0) FROM users;";
            try (var ps = conn.prepareStatement(statement)) {
                mycount = ps.executeUpdate();
            }
        } catch (SQLException | DataAccessException e) {
            //throw new DataAccessException("Failed to create, invalid token");
        }
        return mycount;
    }

    public void addUser (UserData myUser) throws DataAccessException, SQLException {
        String username = myUser.getUsername();
        String password = myUser.getPassword();
        String email = myUser.getEmail();
        boolean found = false;
        if (myUser != null) {
            try (var conn = DatabaseManager.getConnection()) {
                var statement = "SELECT username FROM users WHERE username='" + myUser.getUsername() + "' LIMIT 1;";
                try (var ps = conn.prepareStatement(statement)) {
                    ResultSet myReturn = ps.executeQuery();
                    System.out.println(myReturn.toString());
                    if (myReturn.next()) { //this won't be null, even if we didn't find anything that matches
                        found = true;
                        //throw new DataAccessException("Error: already taken");
                        throw new DataAccessException("Error: username already exists");
                    }
                }
            }
        }

        if (username.matches("[a-zA-Z]+") && password.matches("[a-zA-Z]+")) {
            try (var conn = DatabaseManager.getConnection()) {
                var statement = "INSERT INTO users (username, password, email) VALUES(?,?,?)";
                try (var ps = conn.prepareStatement(statement)) {
                    ps.setString(1, username);
                    ps.setString(2, password);
                    ps.setString(3, email);
                    ps.executeUpdate();
                }
            }
        }
    }

    public UserData verifyUser(String username, String password) throws DataAccessException, SQLException { //this works, but do we need a find user function?
        boolean found = false;
        try (var conn = DatabaseManager.getConnection()) {
            var statement = "SELECT username, password FROM users WHERE username='" + username + "' AND password='" + password + "' LIMIT 1;";
            try (var ps = conn.prepareStatement(statement)) {
                ResultSet myReturn = ps.executeQuery();
                System.out.println(myReturn.toString());
                if (myReturn.next()) { //this won't be null, even if we didn't find anything that matches
                    found = true;
                }
            }
        }
        if (found) {
            return new UserData(username, password, "fakeemail");
        }
        else {
            throw new DataAccessException("Error: unauthorized");
        }
    }

    public void clearAll() throws DataAccessException, SQLException {
        try (var conn = DatabaseManager.getConnection()) {
            var statement = "TRUNCATE TABLE users;";
            try (var ps = conn.prepareStatement(statement)) {
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            //do nothing
        }
    }

    private final String[] createStatements = { //username is good, but authtoken needs to include numbers
            """
            CREATE TABLE IF NOT EXISTS  users (
              `id` int NOT NULL AUTO_INCREMENT,
              `username` varchar(256) NOT NULL,
              `password` varchar(256) NOT NULL,
              `email` varchar(256) NOT NULL,
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
