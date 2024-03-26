package clientTests;

import model.AuthData;
import model.Request.*;
import model.Result.*;
import org.junit.jupiter.api.*;
import server.Server;
import serverFacade.*;

public class ServerFacadeTests {

    private static Server server = new Server();
    private static serverFacade mySF = null;
    private static AuthData myToken = null;

    @BeforeAll
    public static void init() {
        server = new Server();
        var port = server.run(0);
        mySF = new serverFacade("http://localhost:" + port);
        System.out.println("Started test HTTP server on " + port);
    }

    @AfterAll
    static void stopServer() {
        server.stop();
    }

    @Test
    @Order(1)
    @DisplayName("Positive Register Test") //DAO code does carry over from previous tests, but not local test vars
    public void posRegister() {
        System.out.println("Clearing the database");
        try {
            mySF.doClear();
        } catch (ResponseException e) {
            System.out.println("failure: " + e.getMessage());
        }

        try {
            myToken = mySF.doRegister("myusername", "mypassword", "myemail");
            System.out.println("Successful register");
        } catch (ResponseException e) {
            System.out.println("failure: " + e.getMessage());
        }
        Assertions.assertNotNull(myToken, "Expected AuthToken from successful register");
    }
    @Test
    @Order(2)
    @DisplayName("Negative Register Test") //fixed
    public void negRegister() {
        System.out.println("Logging out");
        try {
            mySF.doLogout(myToken);
            myToken = null;
        } catch (ResponseException e) {
            System.out.println("failure: " + e.getMessage());
        }

        try {
            myToken = mySF.doRegister("myusername", "mypassword", "myemail");
        } catch (ResponseException e) {
            System.out.println("failure: " + e.getMessage());
            System.out.println("Task failed successfully");
        }
        Assertions.assertNull(myToken, "Unexpected AuthToken from registering duplicate user");
    }
    @Test
    @Order(3)
    @DisplayName("Negative Logout Test") //fixed
    public void negLogout() {
        String failMessage = null;
        System.out.println("Logging out");
        try {
            mySF.doLogout(myToken);
        } catch (ResponseException e) {
            System.out.println("failure: " + e.getMessage());
            failMessage = e.getMessage();
        }
        Assertions.assertNotNull(failMessage, "Should not be able to logout an already logged out user");
    }
    @Test
    @Order(4)
    @DisplayName("Positive Logout Test") //fixed
    public void posLogout() {
        String failMessage = null;
        try {
            myToken = mySF.doRegister("newusername", "newpassword", "newemail");
        } catch (ResponseException e) {
            System.out.println("failure: " + e.getMessage());
            failMessage = e.getMessage();
        }
        try {
            mySF.doLogout(myToken);
        } catch (ResponseException e) {
            System.out.println("failure: " + e.getMessage());
            failMessage = e.getMessage();
        }
        Assertions.assertNull(failMessage, "Unexpected error given correct login & logout information");
    }
    @Test
    @Order(5)
    @DisplayName("Negative Login Test") //fixed
    public void negLogin() {
        String failMessage = null;
        try {
            myToken = mySF.doLogin("myusername", "badpassword");
        } catch (ResponseException e) {
            System.out.println("failure: " + e.getMessage());
            failMessage = e.getMessage();
        }
        Assertions.assertNotNull(failMessage, "Expected login to fail when using an incorrect password");
    }
    @Test
    @Order(6)
    @DisplayName("Positive Login Test") //fixed
    public void posLogin() {
        try {
            myToken = mySF.doLogin("myusername", "mypassword");
        } catch (ResponseException e) {
            System.out.println("failure: " + e.getMessage());
        }
        Assertions.assertNotNull(myToken, "Expected AuthToken from successful login");
    }
    @Test
    @Order(7)
    @DisplayName("Positive CreateGame Test") //fix
    public void posCreate() {
        String failMessage = null;
        CreateResult gameResult = null;
        try {
            gameResult = mySF.doCreate(myToken, "NewGameName");
        } catch (ResponseException e) {
            System.out.println("failure: " + e.getMessage());
            failMessage = e.getMessage();
        }
        Assertions.assertNull(failMessage, "CreateGame failed with proper AuthToken");
    }
    @Test
    @Order(8)
    @DisplayName("Negative CreateGame Test") //fix
    public void negCreate() {
        String failMessage = null;
        try {
            mySF.doLogout(myToken);
        } catch (ResponseException e) {
            System.out.println("failure: " + e.getMessage());
        }
        CreateResult gameResult = null;
        try {
            gameResult = mySF.doCreate(myToken, "NewGameName");
        } catch (ResponseException e) {
            System.out.println("failure: " + e.getMessage());
            failMessage = e.getMessage();
        }
        Assertions.assertNotNull(failMessage, "CreateGame should not be possible while logged in");
    }
    @Test
    @Order(9)
    @DisplayName("Positive ListGames Test") //fix
    public void posList() {
        try {
            myToken = mySF.doLogin("myusername", "mypassword");
        } catch (ResponseException e) {
            System.out.println("failure: " + e.getMessage());
        }
        ListResult myResult = null;
        try {
            myResult = mySF.doList(myToken);
        } catch (ResponseException e) {
            System.out.println("failure: " + e.getMessage());
        }
        Assertions.assertNull(myResult.getMessage(), "ListGames failed with proper AuthToken");
    }
    @Test
    @Order(10)
    @DisplayName("Negative ListGames Test") //fix
    public void negList() {
        try {
            mySF.doLogout(myToken);
        } catch (ResponseException e) {
            System.out.println("failure: " + e.getMessage());
        }
        ListResult myResult = null;
        try {
            myResult = mySF.doList(myToken);
        } catch (ResponseException e) {
            System.out.println("failure: " + e.getMessage());
        }
        Assertions.assertNull(myResult, "ListGames should not succeed with expired token");
    }
    @Test
    @Order(11)
    @DisplayName("Negative JoinGame Test") //fix
    public void negJoin() {
        JoinResult myResult = null;
        try {
            myResult = mySF.doJoin(myToken, "BLACK", 100);
        } catch (ResponseException e) {
            System.out.println("failure: " + e.getMessage());
        }
        Assertions.assertNull(myResult, "JoinGame did not fail when given no token");
    }
    @Test
    @Order(12)
    @DisplayName("Positive JoinGame Test") //fix
    public void posJoin() {
        System.out.println("Clearing the database");
        try {
            mySF.doClear();
            myToken = mySF.doRegister("myusername", "mypassword", "myemail");
            mySF.doCreate(myToken, "NewGameName");
        } catch (ResponseException e) {
            System.out.println("failure: " + e.getMessage());
            System.out.println("Clear/register failed");
        }

        JoinResult myResult = null;
        try {
            myResult = mySF.doJoin(myToken, "WHITE", 100);
        } catch (ResponseException e) {
            System.out.println("failure: " + e.getMessage());
            System.out.println("Join failed");
        }
        Assertions.assertNotNull(myResult.getMyGame(), "JoinGame failed with proper AuthToken");
    }
    @Test
    @Order(13)
    @DisplayName("Positive ClearAll Test") //fix
    public void posClear() {
        String failmessage = null;
        try {
            mySF.doClear();
            System.out.println("Database cleared");
        } catch (ResponseException e) {
            System.out.println("failure: " + e.getMessage());
            failmessage = e.getMessage();
        }
        Assertions.assertNull(failmessage, "Clear database has failed");
    }

    /*
    @Test
    @Order(13)
    @DisplayName("Positive ClearAll Test") //fix
    public void posClear() {
        myClear.clearAll();
        Assertions.assertEquals(true,myClear.isEmpty(),"Database still contains elements after clearing");
    }

     */
}
