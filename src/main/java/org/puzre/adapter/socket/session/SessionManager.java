package org.puzre.adapter.socket.session;

import io.quarkus.websockets.next.WebSocketConnection;
import jakarta.inject.Singleton;
import org.puzre.application.port.IMessageProvider;
import org.puzre.application.port.ISessionManager;

import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Singleton
public class SessionManager implements ISessionManager {

    private final Set<WebSocketConnection> sessions = ConcurrentHashMap.newKeySet();

    private final IMessageProvider iMessageProvider;

    public SessionManager(IMessageProvider iMessageProvider) {
        this.iMessageProvider = iMessageProvider;
    }

    @Override
    public void addSession(String newUsername, WebSocketConnection session) {
        this.sessions.add(session);
        String joinMessage = iMessageProvider.getJoinMessage(newUsername);
        this.broadcastSystemMessage(newUsername, joinMessage);
    }

    @Override
    public void removeSession(String username, WebSocketConnection session) {
        String disconnectMessage = iMessageProvider.getDisconnectMessage(username);
        this.broadcastSystemMessage(username, disconnectMessage);
        this.sessions.remove(session);
    }

    @Override
    public void broadcastSystemMessage(String newUsername, String message) {
        this.sessions.forEach(session -> {
            session.sendText(message)
                    .subscribe()
                    .with(
                            success -> System.out.println("message sent to sessionId -> " + session.id()),
                            failure -> System.out.println("failed to send message to sessionId ->" + session.id())
                    );
        });
    }

    @Override
    public void broadcastUserMessage(String username, String message, WebSocketConnection senderConnection) {
        String chatMessage = iMessageProvider.getChatMessage(username, message);
        this.sessions.stream().filter(it -> !Objects.equals(it.id(), senderConnection.id()))
                .forEach(session -> {
                    session.sendText(chatMessage)
                            .subscribe()
                            .with(
                                    success -> System.out.println("message sent to sessionId -> " + session.id()),
                                    failure -> System.out.println("failed to send message to sessionId ->" + session.id())
                            );
                });
    }
}
