package server.websocket;

import chess.ChessGame;
import com.google.gson.Gson;
import dataAccess.*;
import exception.ResponseException;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import webSocketMessages.Action;
import webSocketMessages.Notification;

import java.io.IOException;
import java.util.Timer;


@WebSocket
public class WebSocketHandler {

    private final ConnectionManager connections = new ConnectionManager();

    @OnWebSocketMessage
    public void onMessage(Session session, String message) throws IOException { //this class belongs to the server
        Action action = new Gson().fromJson(message, Action.class);
        switch (action.type()) { //change this
            case MOVE -> update(action.gameUpdate(), action.gameID(), session); //
            //case EXIT -> exit(action.visitorName());
        }
    }

    private void update(ChessGame gameUpdate, Integer gameID, Session session) throws IOException {
        connections.update(gameID, gameUpdate, session);
        var message = String.format("%s is in the shop", gameUpdate);
        var notification = new Notification(Notification.Type.ARRIVAL, message);
        connections.broadcast(gameUpdate, notification);
    }

    private void exit(String visitorName) throws IOException {
        connections.remove(visitorName);
        var message = String.format("%s left the shop", visitorName);
        var notification = new Notification(Notification.Type.DEPARTURE, message);
        connections.broadcast(visitorName, notification);
    }

    public void makeNoise(String petName, String sound) throws ResponseException {
        try {
            var message = String.format("%s says %s", petName, sound);
            var notification = new Notification(Notification.Type.NOISE, message);
            connections.broadcast("", notification);
        } catch (Exception ex) {
            throw new ResponseException(500, ex.getMessage());
        }
    }
}