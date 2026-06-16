package com.gblrod.automobclick.service;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.Map;

public class MessageService {
    private final FileConfiguration config;
    private final MiniMessage miniMessage = MiniMessage.miniMessage();

    public MessageService(FileConfiguration config) {
        this.config = config;
    }

    public Component get(String path) {
        return miniMessage.deserialize(config.getString("messages." + path, ""));
    }

    public Component get(String path, Map<String, String> placeholders
    ) {
        String message = config.getString("messages." + path, "");

        for (var entry : placeholders.entrySet()) {
            message = message.replace("%" + entry.getKey() + "%", entry.getValue());
        }

        return miniMessage.deserialize(message);
    }
}