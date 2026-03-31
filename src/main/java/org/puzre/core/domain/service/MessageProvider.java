package org.puzre.core.domain.service;

import jakarta.inject.Singleton;
import org.puzre.application.port.IMessageProvider;

@Singleton
public class MessageProvider implements IMessageProvider {

    @Override
    public String getJoinMessage(String username) {
        return String.format("%s has joined", username);
    }

    @Override
    public String getDisconnectMessage(String username) {
        return String.format("%s has disconnected", username);
    }

    @Override
    public String getChatMessage(String message, String username) {
        return String.format("<%s> %s", message, username);
    }
}
