package org.puzre.adapter.socket.hadler;

import io.quarkus.websockets.next.WebSocketConnection;
import jakarta.inject.Singleton;
import org.puzre.application.port.ISessionManager;
import org.puzre.application.port.IWebSocketManager;

@Singleton
public class WebSocketManager implements IWebSocketManager {

    private final ISessionManager iSessionManager;

    public WebSocketManager(ISessionManager iSessionManager) {
        this.iSessionManager = iSessionManager;
    }

    @Override
    public void onOpen(String username, WebSocketConnection userConnection) {
        this.iSessionManager.addSession(username, userConnection);
    }

    @Override
    public void onClose(String username, WebSocketConnection userConnection) {
        this.iSessionManager.removeSession(username, userConnection);
    }

    @Override
    public void onTextMessage(String username, String message, WebSocketConnection senderConnection) {
        this.iSessionManager.broadcastUserMessage(username, message, senderConnection);
    }
}
