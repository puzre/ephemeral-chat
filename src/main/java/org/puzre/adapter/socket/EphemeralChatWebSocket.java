package org.puzre.adapter.socket;

import io.quarkus.websockets.next.*;
import jakarta.enterprise.context.ApplicationScoped;
import org.puzre.application.port.IWebSocketManager;

@ApplicationScoped
@WebSocket(path = "/{username}/ephemeral-chat")
public class EphemeralChatWebSocket {

    private final IWebSocketManager iwebSocketManager;

    public EphemeralChatWebSocket(IWebSocketManager iwebSocketManager) {
        this.iwebSocketManager = iwebSocketManager;
    }

    @OnOpen
    public void onOpen(@PathParam("username") String username, WebSocketConnection webSocketConnection) {
        this.iwebSocketManager.onOpen(username, webSocketConnection);
    }

    @OnClose
    public void onClose(@PathParam("username") String username, WebSocketConnection webSocketConnection) {
        this.iwebSocketManager.onClose(username, webSocketConnection);
    }

    @OnTextMessage
    public void onTextMessage(@PathParam("username") String username, String message, WebSocketConnection sender) {
        this.iwebSocketManager.onTextMessage(username, message, sender);
    }
}
