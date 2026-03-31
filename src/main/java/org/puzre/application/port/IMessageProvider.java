package org.puzre.application.port;

public interface IMessageProvider {
    String getJoinMessage(String username);
    String getDisconnectMessage(String username);
    String getChatMessage(String message, String username);
}
