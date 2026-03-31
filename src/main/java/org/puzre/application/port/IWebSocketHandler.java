package org.puzre.application.port;

import io.quarkus.websockets.next.WebSocketConnection;

public interface IWebSocketHandler {
    void onOpen(String username, WebSocketConnection webSocketConnection);
    void onClose(String username, WebSocketConnection webSocketConnection);
    void onTextMessage(String username, String message, WebSocketConnection webSocketConnection);
}
