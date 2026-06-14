package com.gblrod.automobclick.config;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EntityType;

import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

public class MobConfigurationService {
    private final FileConfiguration config;
    private final Logger logger;

    public MobConfigurationService(FileConfiguration config, Logger logger) {
        this.config = config;
        this.logger = logger;
    }

    public Set<EntityType> getAllowedMobs() {
        Set<EntityType> allowedMobs = new HashSet<>();

        for (String mobName : config.getStringList("allowed-mobs")) {
            try {
                allowedMobs.add(EntityType.valueOf(mobName.toUpperCase()));
            } catch (IllegalArgumentException ignored) {
                logger.warning("Mob inválido em config.yml: " + mobName);
            }
        }

        return allowedMobs;
    }
}