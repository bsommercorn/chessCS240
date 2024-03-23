package ui;

import chess.*;
import chess.Pieces.ChessPiece;
import model.AuthData;

import java.util.Objects;
import java.util.Scanner;

public class Main {
    private static final String UNICODE_ESCAPE = "\u001b";//\u001b[48;5;15m
    private static final String SET_BG_COLOR = UNICODE_ESCAPE + "[48;5;";
    public static final String SET_BG_COLOR_DARK_GREY = SET_BG_COLOR + "235m";
    public static final String SET_BG_COLOR_WHITE = SET_BG_COLOR + "15m";
    private static final String ANSI_ESCAPE = "\033";
    public static final String ANSI_GREEN = "\u001B[32m";
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
                    //myToken = doLogin();
                    //if (myToken != null) {
                        loggedin = true;
                        System.out.println("Login success!");
                        //System.out.println("Authtoken is " + myToken.getAuthToken());
                    //}
                    //else {
                        //System.out.println("Login failed.");
                    //}
                }
                if (Objects.equals(myinput,"register")) {
                    //myToken = doRegister();
                    //if (myToken != null) {
                        loggedin = true;
                        System.out.println("Register success! Logging you in.");
                        //System.out.println("Authtoken is " + myToken.getAuthToken());
                    //}
                    //else {
                        //System.out.println("Login failed after register.");
                    //}
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
                    //doLogout(myToken);
                    loggedin = false;
                }
                if (Objects.equals(myinput, "create")) {
                    //doCreate(myToken);
                }
                if (Objects.equals(myinput, "list")) {
                    //doList(myToken);
                }
                if (Objects.equals(myinput, "join")) { //needs to print fancy board
                    //doJoin(myToken);
                }
                if (Objects.equals(myinput, "observe")) { //needs to print fancy board
                    //doObserve(myToken);
                }
                if (Objects.equals(myinput, "clear")) {
                    //doClear();
                    loggedin = false;
                }
            }
            System.out.println("----------------------------------------");
        }
    }
    /*
    public static AuthToken doLogin() {
        Scanner userInput = new Scanner(System.in);
        Repl myRepl = new Repl();

        System.out.println("enter username:");
        String myusername = userInput.nextLine();
        System.out.println("enter password:");
        String mypassword = userInput.nextLine();

        //LoginResult myResult = new LoginService().loginAttempt(new LoginRequest(myusername, mypassword)); //call TA tomorrow and fix with them
        LoginResult myResult = (LoginResult) myRepl.httpHandle("session", "POST", new LoginRequest(myusername, mypassword), LoginResult.class);
        if (myResult == null || myResult.getMessage() != null) {
            return null;
        }
        else {
            AuthToken myToken = new AuthToken(myResult.getMyToken().getAuthToken(), myusername);
            return myToken;
        }
    }

    public static AuthToken doRegister() {
        Scanner userInput = new Scanner(System.in);
        Repl myRepl = new Repl();

        System.out.println("enter username:");
        String myusername = userInput.nextLine();
        System.out.println("enter password:");
        String mypassword = userInput.nextLine();
        System.out.println("enter email:");
        String myemail = userInput.nextLine();
        //handle bad input here?
        //RegisterResult myResult = new RegisterService().newRegister(new RegisterRequest(myusername, mypassword, myemail));
        RegisterResult myResult = (RegisterResult) myRepl.httpHandle("user", "POST", new RegisterRequest(myusername, mypassword, myemail), RegisterResult.class);
        if (myResult == null || myResult.getMessage() != null) {
            return null;
        }
        else {
            AuthToken myToken = new AuthToken(myResult.getMyToken().getAuthToken(), myusername);
            return myToken;
        }
    }

    public static void doLogout(AuthToken myToken) {
        Repl myRepl = new Repl();
        System.out.println("You will now be logged out");
        Result myResult = (Result) myRepl.httpHandle("session", "DELETE", new LogoutRequest(myToken), Result.class);
    }

    public static void doCreate(AuthToken myToken) { //maybe have these return strings instead?
        Scanner userInput = new Scanner(System.in);
        Repl myRepl = new Repl();

        System.out.println("Time to create a new game");
        System.out.println("enter gamename:");
        String gamename = userInput.nextLine();
        System.out.println("Game will be created. Authtoken for this user is " + myToken.getAuthToken());

        CreateGameResult myResult = (CreateGameResult) myRepl.httpHandle("game", "POST", new CreateGameRequest(gamename, myToken), CreateGameResult.class);
        if (myResult == null || myResult.getMessage() != null) {
            System.out.println("Could not create game.");
        }
        else {
            System.out.println("New game created! ID for the new game is " + myResult.getGameID());
        }
    }

    public static void doList(AuthToken myToken) {
        Repl myRepl = new Repl();
        //ListGameResult myResult = new ListGamesService().findAll(new Request(myToken));
        System.out.println("Games will be listed. Authtoken for this user is " + myToken.getAuthToken());
        ListGameResult myResult = (ListGameResult) myRepl.httpHandle("games", "POST", new Request(myToken), ListGameResult.class);
        if (myResult == null || myResult.getMessage() != null) {
            System.out.println("Unable to list games.");
        }
        else {
            System.out.println("Here is all the games on the server!");
            System.out.println(myResult.toString());
        }
    }

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

    public static void doClear() {
        Repl myRepl = new Repl();
        System.out.println("Clearing the database.");
        //Result myReponse = (Result) myRepl.httpHandle("db", "DELETE", new Request(myToken), Result.class);
        Result myReponse = (Result) myRepl.httpHandle("db", "DELETE", new Request(), Result.class);
        System.out.println("You are now logged out!");
    }
}

     */
}