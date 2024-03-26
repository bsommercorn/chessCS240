package clientTests;

import model.AuthData;
import model.Request.*;
import model.Result.*;
import org.junit.jupiter.api.*;
import server.Server;
import serverFacade.*;

public class ServerFacadeTests {

    private static Server server = new Server();
    private serverFacade mySF = new serverFacade("http://localhost:8080/");
    private static AuthData myToken;

    @BeforeAll
    public static void init() {
        server = new Server();
        var port = server.run(8080);
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
            System.out.println("Successful Register");
        } catch (ResponseException e) {
            System.out.println("failure: " + e.getMessage());
        }

        try {
            myToken = mySF.doRegister("myusername", "mypassword", "myemail");
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
            myToken = mySF.doLogin("myusername", "mypassword");
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

    /*

    @Test
    @Order(8)
    @DisplayName("Negative CreateGame Test") //fix
    public void negCreate() {
        CreateResult gameResult = myCreate.newGame(new CreateRequest("GameName", new AuthData("badToken"))); //should fail
        System.out.println("CreateGame result was " + gameResult.toString() + "\n");
        Assertions.assertEquals("{ \"message\"", gameResult.toString().substring(0,11), "CreateGame did not fail when supplied with a bad token");
    }

    @Test
    @Order(9)
    @DisplayName("Positive ListGames Test") //fix
    public void posList() {
        LoginResult loginResult = myLogin.newLogin(new LoginRequest("username", "password")); //should succeed
        //System.out.println("Login result was " + loginResult.toString() + "\n");
        myToken = new AuthData(loginResult.getMyToken());
        CreateResult gameResult = myCreate.newGame(new CreateRequest("GameName", myToken)); //should succeed
        ListResult listGameResult = myList.listGames(new ListRequest(myToken)); //should succeed
        System.out.println("ListGame result was " + listGameResult.toString());
        Assertions.assertEquals("{ \"games\"", listGameResult.toString().substring(0,9), "ListGames failed with proper AuthToken");
    }
    @Test
    @Order(10)
    @DisplayName("Negative ListGames Test") //fix
    public void negList() {
        LogoutResult logoutResult = myLogout.logout(new LogoutRequest(myToken));
        ListResult listGameResult = myList.listGames(new ListRequest(myToken)); //should fail
        System.out.println("ListGame result was " + listGameResult.toString() + "\n");
        Assertions.assertEquals("{ \"message\"", listGameResult.toString().substring(0,11), "ListGames did not fail when using an expired token");
    }
    @Test
    @Order(11)
    @DisplayName("Negative JoinGame Test") //fix
    public void negJoin() {
        JoinResult joinGameResult = myJoin.joinGame(new JoinRequest("BLACK", 100)); //should fail, no auth
        System.out.println("JoinGame result was " + joinGameResult.toString());
        Assertions.assertEquals("{ \"message\"", joinGameResult.toString().substring(0,11), "JoinGame did not fail when given no token");
    }
    @Test
    @Order(12)
    @DisplayName("Positive JoinGame Test") //fix
    public void posJoin() {
        LoginResult loginResult = myLogin.newLogin(new LoginRequest("username", "password")); //logging back in
        myToken = new AuthData(loginResult.getMyToken());
        CreateResult gameResult = myCreate.newGame(new CreateRequest("GameName", myToken)); //should succeed
        JoinRequest gameRequest = new JoinRequest("BLACK", 100);
        gameRequest.setMyToken(myToken);
        JoinResult joinGameResult = myJoin.joinGame(gameRequest); //should succeed
        System.out.println("JoinGame result was " + joinGameResult.toString() + "\n");
        Assertions.assertEquals("{}", joinGameResult.toString().substring(0,2), "JoinGame failed with proper AuthToken");
    }

    @Test
    @Order(13)
    @DisplayName("Positive ClearAll Test") //fix
    public void posClear() {
        myClear.clearAll();
        Assertions.assertEquals(true,myClear.isEmpty(),"Database still contains elements after clearing");
    }

     */
}
