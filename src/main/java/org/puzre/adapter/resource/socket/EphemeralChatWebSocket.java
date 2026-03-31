package org.puzre.adapter.resource.socket;

import io.quarkus.websockets.next.*;
import jakarta.enterprise.context.ApplicationScoped;
import org.puzre.application.port.IWebSocketHandler;

@ApplicationScoped
@WebSocket(path = "/{username}/ephemeral-chat")
public class EphemeralChatWebSocket {

    private final IWebSocketHandler iwebSocketHandler;

    public EphemeralChatWebSocket(IWebSocketHandler iwebSocketHandler) {
        this.iwebSocketHandler = iwebSocketHandler;
    }

    @OnOpen
    public void onOpen(@PathParam("username") String username, WebSocketConnection webSocketConnection) {
        this.iwebSocketHandler.onOpen(username, webSocketConnection);
    }

    @OnClose
    public void onClose(@PathParam("username") String username, WebSocketConnection webSocketConnection) {
        this.iwebSocketHandler.onClose(username, webSocketConnection);
    }

    @OnTextMessage
    public void onTextMessage(@PathParam("username") String username, String message, WebSocketConnection sender) {
        this.iwebSocketHandler.onTextMessage(username, message, sender);
    }
}
