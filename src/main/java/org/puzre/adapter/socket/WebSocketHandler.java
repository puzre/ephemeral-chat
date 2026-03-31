package org.puzre.adapter.socket;

import io.quarkus.websockets.next.WebSocketConnection;
import jakarta.inject.Singleton;
import org.puzre.application.port.ISessionManager;
import org.puzre.application.port.IWebSocketHandler;

@Singleton
public class WebSocketHandler implements IWebSocketHandler {

    private final ISessionManager iSessionManager;

    public WebSocketHandler(ISessionManager iSessionManager) {
        this.iSessionManager = iSessionManager;
    }

    @Override
    public void onOpen(String username, WebSocketConnection webSocketConnection) {
        this.iSessionManager.addSession(username, webSocketConnection);
    }

    @Override
    public void onClose(String username, WebSocketConnection webSocketConnection) {
        this.iSessionManager.removeSession(username, webSocketConnection);
    }

    @Override
    public void onTextMessage(String username, String message, WebSocketConnection webSocketConnection) {
        this.iSessionManager.broadcastUserMessage(username, message);
    }
}
