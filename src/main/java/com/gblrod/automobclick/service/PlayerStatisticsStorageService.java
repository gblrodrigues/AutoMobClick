package com.gblrod.automobclick.service;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class PlayerStatisticsStorageService {
    private final JavaPlugin plugin;
    private File file;
    private FileConfiguration config;

    public PlayerStatisticsStorageService(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public void initialize() {
        file = new File(plugin.getDataFolder(), "player-stats.yml");

        if (!file.exists()) {
            try {
                Files.createFile(file.toPath());
            } catch (IOException exception) {
                plugin.getLogger().severe("Não foi possível criar o arquivo player-stats.yml");
            }
        }

        config = YamlConfiguration.loadConfiguration(file);
    }

    public void saveKills(UUID playerId, Map<EntityType, List<Long>> kills) {
        String basePath = "players." + playerId + ".kills";

        config.set(basePath, null);

        for (var entry : kills.entrySet()) {
            config.set(basePath + "." + entry.getKey().name(), entry.getValue());
        }

        save();
    }

    public Map<EntityType, List<Long>> loadKills(UUID playerId) {
        Map<EntityType, List<Long>> result = new HashMap<>();
        String path = "players." + playerId + ".kills";

        ConfigurationSection section = config.getConfigurationSection(path);

        if (section == null) {
            return result;
        }

        for (String mobName : section.getKeys(false)) {
            EntityType entityType = EntityType.valueOf(mobName);

            List<Long> timestamps = section.getLongList(mobName);
            result.put(entityType, timestamps);
        }

        return result;
    }

    private void save() {
        try {
            config.save(file);
        } catch (IOException e) {
            plugin.getLogger().severe("Falha ao salvar o arquivo player-stats.yml");
        }
    }
}