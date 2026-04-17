package org.puzre.application.port;

import io.quarkus.websockets.next.WebSocketConnection;

public interface IWebSocketHandler {
    void onOpen(String username, WebSocketConnection userConnection);
    void onClose(String username, WebSocketConnection userConnection);
    void onTextMessage(String username, String message, WebSocketConnection senderConnection);
}
