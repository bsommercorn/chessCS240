package server;

import handler.*;
import spark.*;

public class Server {

    public static void main(String[] args) {
        new Server().run(8080);
    }

    public int run(int desiredPort) {
        Spark.port(desiredPort);

        Spark.staticFiles.location("web");

        Spark.post("/user", (req, res) -> (new RegisterHandler()).register(req, res));
        Spark.post("/session", (req, res) -> (new LoginHandler()).login(req, res));
        Spark.delete("/session", (req, res) -> (new LogoutHandler()).logout(req, res));

        Spark.post("/game", (req, res) -> (new CreateGameHandler()).newGame(req, res));
        Spark.get("/game", (req, res) -> (new ListGamesHandler()).getList(req, res));
        Spark.put("/game", (req, res) -> (new JoinGameHandler()).joinGame(req, res));

        Spark.delete("/db", (req, res) -> (new ClearHandler()).clearAll(req, res));

        Spark.post("/games", (req, res) -> (new ListGamesHandler()).getList(req, res)); //shhhhhh, don't question it

        Spark.awaitInitialization();
        return Spark.port();
    }

    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }
}