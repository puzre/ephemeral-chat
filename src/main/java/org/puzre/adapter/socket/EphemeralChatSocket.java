package org.puzre.adapter.socket;

import io.quarkus.websockets.next.*;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@ApplicationScoped
@WebSocket(path = "/ephemeral-chat")
public class EphemeralChatSocket {
    private Set<WebSocketConnection> connections = ConcurrentHashMap.newKeySet();

    @OnOpen
    public void onOpen(WebSocketConnection connection) {
        connections.add(connection);
        IO.println("info -> connection created - id-" + connection.id());
    }

    @OnClose
    public void onClose(WebSocketConnection connection) {
        connections.remove(connection);
        IO.println("info -> connection removed - id-" + connection.id());
    }

    @OnTextMessage
    public void onTextMessage(String message, WebSocketConnection sender) {
        IO.println("info -> messaged sent - msg-" + message);
        for (WebSocketConnection connection : connections) {
            connection.sendText(message + "sender -> " + sender.id());
        }
    }
}
