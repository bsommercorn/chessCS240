package server.websocket;

import chess.ChessGame;
import org.eclipse.jetty.websocket.api.Session;

import java.io.IOException;

public class Connection {
    public ChessGame gameUpdate;
    public Session session;

    public Connection(ChessGame gameUpdate, Session session) {
        this.gameUpdate = gameUpdate;
        this.session = session;
    }

    public void send(String msg) throws IOException {
        session.getRemote().sendString(msg);
    }
}