package dataAccess;

import model.UserData;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Objects;

public class UserDAO {
    public UserDAO() {
        try {
            configureDatabase();
        } catch (DataAccessException e) {
            //throw new RuntimeException(e);
            //do nothing
        }
    }

    private static HashMap<String,UserData> myUsers = new HashMap<String,UserData>();
    public void addUser(UserData newUser) throws DataAccessException {
        //check if the username already exists
        if (myUsers.get(newUser.getUsername()) == null) {
            myUsers.put(newUser.getUsername(), newUser);
        }
        else {
            throw new DataAccessException("Error: username already exists");
        }
        //if it doesn't, add the UserData to the HashMap

    }

    public void clearAll() {
        myUsers = new HashMap<String,UserData>();
    }

    public UserData verifyUser(String username, String password) throws DataAccessException{
        if (myUsers.get(username) != null && Objects.equals(myUsers.get(username).getPassword(), password)) {
            return myUsers.get(username);
        }
        else {
            throw new DataAccessException("Error: Username or password was incorrect");
        }
    }

    public int getSize() {
        return myUsers.size();
    }

    private final String[] createStatements = { //fix to make like real auth code
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
            //throw new DataAccessException(500, String.format("Unable to configure database: %s", ex.getMessage()));
            throw new DataAccessException("Unable to configure database: %s");
        }
    }
}
