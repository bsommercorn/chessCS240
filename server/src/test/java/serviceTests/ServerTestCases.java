package serviceTests;

import RequestAndResult.*;
import model.AuthData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import service.*;

public class ServerTestCases {
    ClearService myClear = new ClearService(); //doesn't take parameters, only requires positive test!

    CreateGameService myCreate = new CreateGameService(); //CreateGameRequest takes a gameName and an AuthToken as parameters

    JoinService myJoin = new JoinService(); //JoinGameRequest takes either a color and an ID, or just an ID

    ListService myList = new ListService(); //JoinGameRequest takes either a color and an ID, or just an ID

    LoginService myLogin = new LoginService(); //only requires an AuthToken

    LogoutService myLogout = new LogoutService(); //only requires an AuthToken

    RegisterService myRegister = new RegisterService(); //

    AuthData myToken;

    @Test
    @Order(1)
    @DisplayName("Positive Register Test") //DAO code does carry over from previous tests, but not local test vars
    public void posRegister() {
        myClear.clearAll();
        RegisterResult myResult = myRegister.newRegister(new RegisterRequest("username", "password", "email"));//register
        System.out.println("Register result was " + myResult.toString());
        Assertions.assertEquals("{ \"username\"", myResult.toString().substring(0,12), "Register did not return correctly");
        //Assert equals here
        myToken = new AuthData(myResult.getMyToken()); //this will work because global
    }
    @Test
    @Order(2)
    @DisplayName("Negative Register Test") //fixed
    public void negRegister() {
        RegisterResult myResult = myRegister.newRegister(new RegisterRequest("username", "password", "email"));//register to a username that already exists, should fail
        System.out.println("Register result was " + myResult.toString() + "\n");
        Assertions.assertEquals("{ \"message\"", myResult.toString().substring(0,11), "Register did not fail when re-registering the same email");
        //Assert equals here
    }
    @Test
    @Order(3)
    @DisplayName("Negative Logout Test") //fixed
    public void negLogout() {
        LogoutResult logoutResult = myLogout.logout(new LogoutRequest(new AuthData("badToken", "username"))); //should fail
        System.out.println("Logout result was " + logoutResult.toString());
        Assertions.assertEquals("{ \"message\"", logoutResult.toString().substring(0,11), "Logout did not fail when logging out a bad token");
    }
    @Test
    @Order(4)
    @DisplayName("Positive Logout Test") //fixed
    public void posLogout() {
        LoginResult loginResult = myLogin.newLogin(new LoginRequest("username", "password")); //should succeed
        //System.out.println("Login result was " + loginResult.toString() + "\n");
        myToken = new AuthData(loginResult.getMyToken());
        LogoutResult logoutResult = myLogout.logout(new LogoutRequest(myToken)); //should succeed
        System.out.println("Logout result was " + logoutResult.toString() + "\n");
        Assertions.assertEquals("{}", logoutResult.toString().substring(0,2), "Logout returned non-success result string");
    }
    @Test
    @Order(5)
    @DisplayName("Negative Login Test") //fixed
    public void negLogin() {
        LoginResult loginResult = myLogin.newLogin(new LoginRequest("username", "badpassword")); //should fail
        System.out.println("Login result was " + loginResult.toString());
        Assertions.assertEquals("{ \"message\"", loginResult.toString().substring(0,11), "Login did not fail when using an incorrect password");
    }
    @Test
    @Order(6)
    @DisplayName("Positive Login Test") //fixed
    public void posLogin() {
        RegisterResult myResult = myRegister.newRegister(new RegisterRequest("username", "password", "email"));//register
        myToken = new AuthData(myResult.getMyToken());
        LogoutResult logoutResult = myLogout.logout(new LogoutRequest(myToken)); //should succeed
        LoginResult loginResult = myLogin.newLogin(new LoginRequest("username", "password")); //should succeed
        System.out.println("Login result was " + loginResult.toString() + "\n");
        //myToken = loginResult.getMyToken();
        Assertions.assertEquals("{ \"username\"", loginResult.toString().substring(0,12), "Login failed using correct username and password");
    }

    @Test
    @Order(7)
    @DisplayName("Positive CreateGame Test") //fix
    public void posCreate() {
        LoginResult loginResult = myLogin.newLogin(new LoginRequest("username", "password")); //should succeed
        //System.out.println("Login result was " + loginResult.toString() + "\n");
        myToken = new AuthData(loginResult.getMyToken());
        CreateResult gameResult = myCreate.newGame(new CreateRequest("GameName", myToken)); //should succeed
        System.out.println("CreateGame result was " + gameResult.toString());
        Assertions.assertEquals("{ \"gameID\"", gameResult.toString().substring(0,10), "CreateGame failed with proper AuthToken");
    }
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
}
