package org.puzre.application.port;

import io.quarkus.websockets.next.WebSocketConnection;

public interface ISessionManager {
    void addSession(String newUsername, WebSocketConnection webSocketConnection);
    void removeSession(String username, WebSocketConnection webSocketConnection);
    void broadcastSystemMessage(String newUsername, String message);
    void broadcastUserMessage(String username, String message, WebSocketConnection senderConnection);
}
