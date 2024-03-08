package dataAccessTests;

import dataAccess.*;
import model.AuthData;
import model.GameData;
import model.UserData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

public class dataAccessTests {
    AuthSQLDAO tokenAccess = new AuthSQLDAO();
    //create, verify, delete, clearAll, storageSize (could be stored without using the database?)
    GameSQLDAO gameAccess = new GameSQLDAO();
    //create, find, update, clearAll, storageSize
    UserSQLDAO userAccess = new UserSQLDAO();
    //add, verify, clearAll, storageSize
    AuthData myToken = null;
    @Test
    @DisplayName("Positive createAuth Test") //DAO code does carry over from previous tests, but not local test vars
    public void posCreateAuth() {
        //takse string
        try {
            myToken = tokenAccess.createAuth("username");
        } catch (SQLException | DataAccessException e) {
            System.out.println("Error caught");
            //ignore
        }
        Assertions.assertNotNull(myToken.getAuthToken(), "Token failed to build");
        //Assertions.assertEquals("username", myToken.getUsername(), "JoinGame failed");
    }
    @Test
    @DisplayName("Negative createAuth Test") //fixed
    public void negCreateAuth() {
        AuthData badToken = null;
        try {
            badToken = tokenAccess.createAuth("); badusername");
        } catch (SQLException | DataAccessException e) {
            System.out.println("Exception caught");
            //ignore
        }
        Assertions.assertNull(badToken, "Token didn't block a bad username");
    }
    @Test
    @DisplayName("Positive findAuth Test")
    public void posFindAuth() {
        boolean worked = false;
        try {
            myToken = tokenAccess.createAuth("username"); //yes this is allowed
            tokenAccess.verifyToken(myToken);
            worked = true;
        } catch (SQLException | DataAccessException e) {
            System.out.println("Exception caught");
            //ignore
        }
        Assertions.assertTrue(worked, "Token was not found");
    }
    @Test
    @DisplayName("Negative findAuth Test")
    public void negFindAuth() {
        boolean worked = false;
        try {
            tokenAccess.verifyToken(new AuthData("badToken"));
            worked = true;
        } catch (SQLException | DataAccessException e) {
            System.out.println("Exception caught");
            //ignore
        }
        Assertions.assertFalse(worked, "verifyToken failed to throw exception");
    }
    @Test
    @DisplayName("Positive deleteAuth Test")
    public void posDeleteAuth() {
        boolean foundafterdelete = false;
        try {
            myToken = tokenAccess.createAuth("username"); //yes this is allowed, multiple logins are fine
            tokenAccess.deleteToken(myToken);
            tokenAccess.verifyToken(myToken);
            foundafterdelete = true;
        } catch (SQLException | DataAccessException e) {
            System.out.println("Exception caught");
            //ignore
        }
        Assertions.assertFalse(foundafterdelete, "Token was found after deletion");
    }
    @Test
    @DisplayName("Negative deleteAuth Test")
    public void negDeleteAuth() {
        boolean noexception = false;
        try {
            tokenAccess.deleteToken(new AuthData("badToken"));
            noexception = true;
        } catch (SQLException | DataAccessException e) {
            System.out.println("Exception caught");
            //ignore
        }
        Assertions.assertFalse(noexception, "Deletion failed to throw not-found exception");
    }

    //GameDAO tests
    @Test
    @DisplayName("Positive createGame Test") //DAO code does carry over from previous tests, but not local test vars
    public void posCreateGame() {
        //takes Game as input (has gameID, gameName, and gamestate string to start)
        boolean worked = false;
        try {
            gameAccess.createGame(new GameData(1, "newGame"));
            worked = true;
        } catch (DataAccessException | SQLException e) {
            System.out.println("Exception caught");
        }
        Assertions.assertTrue(worked, "Game was not created");
    }
    @Test
    @DisplayName("Negative createGame Test") //fixed
    public void negCreateGame() {
        boolean worked = false;
        try {
            gameAccess.createGame(new GameData(1, "); newGame"));
            worked = true;
        } catch (DataAccessException | SQLException e) {
            System.out.println("Exception caught");
        }
        Assertions.assertFalse(worked, "CreateGame failed to throw exception");
    }

    @Test
    @DisplayName("Positive findGame Test") //DAO code does carry over from previous tests, but not local test vars
    public void posFindGame() {
        //takes int for ID
        boolean worked = false;
        try {
            gameAccess.createGame(new GameData(1, "newGame"));
            gameAccess.findGame(1);
            worked = true;
        } catch (DataAccessException | SQLException e) {
            System.out.println("Exception caught");
        }
        Assertions.assertTrue(worked, "Game was not found");
    }
    @Test
    @DisplayName("Negative findGame Test") //fixed
    public void negFindGame() {
        //takes int for ID
        boolean worked = false;
        try {
            gameAccess.createGame(new GameData(1, "newGame"));
            gameAccess.findGame(2);
            worked = true;
        } catch (DataAccessException | SQLException e) {
            System.out.println("Exception caught");
        }
        Assertions.assertFalse(worked, "FindGame failed to throw exception");
    }
    @Test
    @DisplayName("Positive updateGame Test") //DAO code does carry over from previous tests, but not local test vars
    public void posUpdateGame() {
        //takes int for ID, plus Game for replacement values
        boolean worked = false;
        try {
            gameAccess.createGame(new GameData(1, "newGame"));
            gameAccess.update(1, new GameData(1, "updatedGame"));
            worked = true;
        } catch (DataAccessException | SQLException e) {
            System.out.println("Exception caught");
        }
        Assertions.assertTrue(worked, "FindGame failed to throw exception");
    }
    @Test
    @DisplayName("Negative updateGame Test") //fixed
    public void negUpdateGame() {
        //takes int for ID, plus Game for replacement vals
        boolean worked = false;
        try {
            gameAccess.createGame(new GameData(1, "newGame"));
            gameAccess.update(1, new GameData(1, "); invalidGame"));
            worked = true;
        } catch (DataAccessException | SQLException e) {
            System.out.println("Exception caught");
        }
        Assertions.assertFalse(worked, "FindGame failed to throw exception");
    }

    @Test
    @DisplayName("Positive createUser Test") //DAO code does carry over from previous tests, but not local test vars
    public void posCreateUser() {
        //takes Game as input (has gameID, gameName, and gamestate string to start)
        boolean worked = false;
        try {
            userAccess.clearAll();
            userAccess.addUser(new UserData("username","password","email"));
            worked = true;
        } catch (DataAccessException | SQLException e) {
            System.out.println("Exception caught");
        }
        Assertions.assertTrue(worked, "User was not created");
    }
    @Test
    @DisplayName("Negative createUser Test") //fixed
    public void negCreateUser() {
        boolean worked = false;
        try {
            userAccess.addUser(new UserData("username","password","email"));
            userAccess.addUser(new UserData("username","password","email"));
            worked = true;
        } catch (DataAccessException | SQLException e) {
            System.out.println("Exception caught");
        }
        Assertions.assertFalse(worked, "Duplicate user was created");
    }
    @Test
    @DisplayName("Positive verifyUser Test") //DAO code does carry over from previous tests, but not local test vars
    public void posVerifyUser() {
        //takes username and password
        boolean worked = false;
        try {
            userAccess.clearAll();
            userAccess.addUser(new UserData("username","password","email"));
            userAccess.verifyUser("username", "password");
            worked = true;
        } catch (DataAccessException | SQLException e) {
            System.out.println("Exception caught");
        }
        Assertions.assertTrue(worked, "User was not created");
    }
    @Test
    @DisplayName("Negative verifyUser Test") //fixed
    public void negVerifyUser() {
        boolean worked = false;
        try {
            userAccess.clearAll();
            userAccess.addUser(new UserData("username","password","email"));
            userAccess.verifyUser("username", "badpassword");
            worked = true;
        } catch (DataAccessException | SQLException e) {
            System.out.println("Exception caught");
        }
        Assertions.assertFalse(worked, "Duplicate user was created");
    }

    @Test
    @DisplayName("Positive clearAll Test")
    public void posClearAll() {
        boolean foundafterdelete = false;
        try {
            myToken = tokenAccess.createAuth("username"); //yes this is allowed, multiple logins are fine
            userAccess.addUser(new UserData("newusername", "password","email"));
            gameAccess.createGame(new GameData(1,"gameName"));

            tokenAccess.clearAll();
            userAccess.clearAll();
            gameAccess.clearAll();

            Assertions.assertEquals(0, userAccess.storageSize(), "User was found after clearing the database");
            Assertions.assertEquals(0, gameAccess.storageSize(), "User was found after clearing the database");
            Assertions.assertEquals(0, tokenAccess.storageSize(), "User was found after clearing the database");
        } catch (SQLException | DataAccessException e) {
            System.out.println("Exception caught");
            //ignore
        }
    }
}
