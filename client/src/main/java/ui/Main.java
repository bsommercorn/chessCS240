package ui;

import model.AuthData;
import model.Request.JoinRequest;
import model.Result.CreateResult;
import model.Result.JoinResult;
import model.Result.ListResult;
import model.Result.LogoutResult;
import serverFacade.serverFacade;

import java.util.Objects;
import java.util.Scanner;
import serverFacade.*;

public class Main {
    private static final String UNICODE_ESCAPE = "\u001b";//\u001b[48;5;15m
    private static final String SET_BG_COLOR = UNICODE_ESCAPE + "[48;5;";
    public static final String SET_BG_COLOR_DARK_GREY = SET_BG_COLOR + "235m";
    public static final String SET_BG_COLOR_WHITE = SET_BG_COLOR + "15m";
    private static final String ANSI_ESCAPE = "\033";
    public static final String ANSI_GREEN = "\u001B[32m";

    private static serverFacade mySF = new serverFacade("http://localhost:8080/");
    /*
    This was in main when I first opened this project:
        var piece = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
        System.out.println("♕ 240 Chess Client: " + piece);
         */
    public static void main(String[] args) {
        System.out.println(ANSI_GREEN + "\u001b[48;5;235mHello world!");
        Scanner userInput = new Scanner(System.in);
        String myinput = "begin";
        boolean loggedin = false;
        AuthData myToken = null;
        //Repl myRepl = new Repl();
        while (!Objects.equals(myinput, "quit")) { //cool this works now
            if (!loggedin) {
                System.out.println("You are not logged in. You can access the following commands:");
                System.out.println("type 'help' for help menu");
                System.out.println("type 'quit' to quit");
                System.out.println("type 'login' to login");
                System.out.println("type 'register' to register");
                myinput = userInput.nextLine();
                if (Objects.equals(myinput, "help")) {
                    System.out.println("This is the help menu");
                }
                if (Objects.equals(myinput,"login")) {
                    myToken = doLogin();
                    if (myToken != null) {
                        loggedin = true;
                        System.out.println("Login success!");
                        System.out.println("Authtoken is " + myToken.getAuthToken());
                    }
                    else {
                        System.out.println("Login failed.");
                    }
                }
                if (Objects.equals(myinput,"register")) {
                    myToken = doRegister();
                    if (myToken != null) {
                        loggedin = true;
                        System.out.println("Register success! Logging you in.");
                        System.out.println("Authtoken is " + myToken.getAuthToken());
                    }
                    else {
                        System.out.println("Login failed after register.");
                    }
                }
            }
            if (loggedin) {
                System.out.println("You are logged in as " + "myToken.getUser()" + ". You can access the following commands:");
                System.out.println("type 'help' for help menu");
                System.out.println("type 'logout' to logout");
                System.out.println("type 'create' to create a new game");
                System.out.println("type 'list' to list all existing games");
                System.out.println("type 'join' to join a new game");
                System.out.println("type 'observe' to join an existing game as an observer");
                System.out.println("type 'quit' to quit");
                System.out.println("type 'clear' to clear the whole database and then logout");
                myinput = userInput.nextLine();
                if (Objects.equals(myinput, "help")) {
                    System.out.println("This is the help menu");
                }
                if (Objects.equals(myinput,"logout")) {
                    try {
                        mySF.doLogout(myToken);
                    } catch (ResponseException e) {
                        System.out.println("failure: " + e.getMessage());
                    }
                    loggedin = false;
                }
                if (Objects.equals(myinput, "create")) {
                    doCreate(myToken);
                }
                if (Objects.equals(myinput, "list")) {
                    doList(myToken);
                }
                if (Objects.equals(myinput, "join")) { //needs to print fancy board
                    doJoin(myToken);
                }
                if (Objects.equals(myinput, "observe")) { //needs to print fancy board
                    //doObserve(myToken);
                }
                if (Objects.equals(myinput, "clear")) {
                    System.out.println("Clearing the database.");
                    try {
                        mySF.doClear();
                    } catch (ResponseException e) {
                        System.out.println("failure: " + e.getMessage());
                    }
                    loggedin = false;
                    System.out.println("You are now logged out!");
                }
            }
            System.out.println("----------------------------------------");
        }
    }

    public static AuthData doLogin() {
        Scanner userInput = new Scanner(System.in);

        System.out.println("enter username:");
        String myusername = userInput.nextLine();
        System.out.println("enter password:");
        String mypassword = userInput.nextLine();

        try {
            return mySF.doLogin(myusername, mypassword);
        } catch (ResponseException e) {
            System.out.println("failure: " + e.getMessage());
        }
        return null;
    }
    public static AuthData doRegister() {
        Scanner userInput = new Scanner(System.in);

        System.out.println("enter username:");
        String myusername = userInput.nextLine();
        System.out.println("enter password:");
        String mypassword = userInput.nextLine();
        System.out.println("enter email:");
        String myemail = userInput.nextLine();

        try {
            return mySF.doRegister(myusername, mypassword, myemail);
        } catch (ResponseException e) {
            System.out.println("failure: " + e.getMessage());
        }
        return null;
    }
    public static void doCreate(AuthData myToken) { //maybe have these return strings instead?
        Scanner userInput = new Scanner(System.in);

        System.out.println("Time to create a new game");
        System.out.println("enter gamename:");
        String gamename = userInput.nextLine();
        System.out.println("Game will be created. Authtoken for this user is " + myToken.getAuthToken());

        try {
            CreateResult myResult = mySF.doCreate(myToken, gamename);
            System.out.println("New game created! ID for the new game is " + myResult.getGameID());
        } catch (ResponseException e) {
            System.out.println("failure: " + e.getMessage());
            System.out.println("Could not create game.");
        }
    }

    public static void doList(AuthData myToken) {
        System.out.println("Games will be listed. Authtoken for this user is " + myToken.getAuthToken());
        try {
            ListResult myResult = mySF.doList(myToken);
            System.out.println("Here is all the games on the server!");
            System.out.println(myResult.toString());
        } catch (ResponseException e) {
            System.out.println("failure: " + e.getMessage());
            System.out.println("Unable to list games.");
        }
    }

    public static void doJoin(AuthData myToken) {
        Scanner userInput = new Scanner(System.in);
        System.out.println("Please enter the id of the game you want to join as a player");
        int gameID = userInput.nextInt();
        userInput.nextLine();
        System.out.println("Please enter the player color you would like to join as, type 'BLACK' or 'WHITE'");
        String mycolor = userInput.nextLine();
        //check the authToken here???
        System.out.println("Game " + gameID + " will be joined. Authtoken for this user is " + myToken.getAuthToken());

        try {
            JoinResult myResult = mySF.doJoin(myToken, mycolor, gameID);
            System.out.println("Joined the game!");
        } catch (ResponseException e) {
            System.out.println("failure: " + e.getMessage());
            System.out.println("Could not join game.");
        }

        /*
        Game joinedGame = new Game(myResult.getMyGame().getGameID(), myResult.getMyGame().getGameName());
            joinedGame.setServerBoard(myResult.getMyGame().getServerBoard());
            System.out.println(joinedGame.gamePrint());
         */
    }

    /*
    public static void doJoin(AuthToken myToken) {
        Scanner userInput = new Scanner(System.in);
        Repl myRepl = new Repl();

        System.out.println("Let the games begin.");
        System.out.println("Please enter the id of the game you want to join as a player");
        int gameID = userInput.nextInt();
        userInput.nextLine();
        System.out.println("Please enter the player color you would like to join as, type 'BLACK' or 'WHITE'");
        String mycolor = userInput.nextLine();
        //check the authToken here???
        System.out.println("Game will be joined. Authtoken for this user is " + myToken.getAuthToken());
        JoinGameRequest myReq = new JoinGameRequest(mycolor, gameID);
        myReq.setMyToken(myToken);//this is how we authorize????
        //JoinGameResult myResult = new JoinGameService().claimSpot(myReq);
        JoinGameResult myResult = (JoinGameResult) myRepl.httpHandle("game", "PUT", myReq, JoinGameResult.class);
        if (myResult == null || myResult.getMessage() != null) {
            System.out.println("Could not join game.");
            System.out.println(myResult.toString());
            //output the message?
        }
        else {
            System.out.println("Joined the game!");
            //System.out.println(myResult.toString());
            Game joinedGame = new Game(myResult.getMyGame().getGameID(), myResult.getMyGame().getGameName());
            joinedGame.setServerBoard(myResult.getMyGame().getServerBoard());
            System.out.println(joinedGame.gamePrint());
            //do something else with the game here, like printing the current game status
        }
    }

    public static void doObserve(AuthToken myToken) {
        Scanner userInput = new Scanner(System.in);
        Repl myRepl = new Repl();

        //System.out.println("Look at this chess game right here. But don't touch.");
        System.out.println("Please enter the id of the game you want to observe");
        int gameID = userInput.nextInt();
        userInput.nextLine();
        JoinGameRequest myReq = new JoinGameRequest(null, gameID);
        myReq.setMyToken(myToken);
        //JoinGameResult myResult = new JoinGameService().claimSpot(myReq);
        JoinGameResult myResult = (JoinGameResult) myRepl.httpHandle("game", "PUT", myReq, JoinGameResult.class);
        if (myResult == null || myResult.getMessage() != null) {
            System.out.println("Could not observe game.");
            //output the message?
        }
        else {
            System.out.println("Joined the game! (as observer)");
            //System.out.println(myResult.toString());
            Game joinedGame = new Game(myResult.getMyGame().getGameID(), myResult.getMyGame().getGameName());
            joinedGame.setServerBoard(myResult.getMyGame().getServerBoard());
            System.out.println(joinedGame.gamePrint());
            //do something else with the game here, like printing the current game status
        }
    }
}

     */
}