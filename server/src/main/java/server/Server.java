package server;

import service.*;
import spark.*;

public class Server {

    public int run(int desiredPort) {
        Spark.port(8080);

        //Spark.staticFiles.location("web");
        Spark.externalStaticFileLocation("C:\\Users\\benja\\IdeaProjects\\chess\\web");

        Spark.post("/user", (req, res) -> (new RegisterService()).register(req, res));
        Spark.post("/session", (req, res) -> (new LoginService()).login(req, res));
        Spark.delete("/session", (req, res) -> (new LogoutService()).logout(req, res));

        Spark.post("/game", (req, res) -> (new CreateGameService()).newGame(req, res));
        Spark.get("/game", (req, res) -> (new ListService()).getList(req, res));
        //Spark.post("/games", (req, res) -> (new ListGamesHandler()).getList(req, res));
        Spark.put("/game", (req, res) -> (new JoinService()).joinGame(req, res));

        Spark.delete("/db", (req, res) -> (new ClearService()).clearAll(req, res));
        // Register your endpoints and handle exceptions here.

        Spark.awaitInitialization();
        return Spark.port();
    }

    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }
}

/*
package server;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import server.Handlers.*;
import spark.Spark;

import java.sql.DriverManager;
import java.sql.SQLException;

import static org.glassfish.grizzly.http.util.Header.Connection;

public class Server {
    public static void main(String[] args) {
        Spark.port(8080);
        Spark.externalStaticFileLocation("C:\\Users\\benja\\IdeaProjects\\chess\\web");

        Spark.post("/user", (req, res) -> (new RegisterHandler()).register(req, res));
        Spark.post("/session", (req, res) -> (new LoginHandler()).login(req, res));
        Spark.delete("/session", (req, res) -> (new LogoutHandler()).logout(req, res));

        Spark.post("/game", (req, res) -> (new CreateGameHandler()).newGame(req, res));
        Spark.get("/game", (req, res) -> (new ListGamesHandler()).getList(req, res));
        Spark.post("/games", (req, res) -> (new ListGamesHandler()).getList(req, res));
        Spark.put("/game", (req, res) -> (new JoinGameHandler()).joinGame(req, res));

        Spark.delete("/db", (req, res) -> (new ClearApplicationHandler()).clearAll(req, res));
        //create database variable here
    }
}
 */