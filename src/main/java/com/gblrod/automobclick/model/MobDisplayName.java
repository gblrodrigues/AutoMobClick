package com.gblrod.automobclick.model;

import org.bukkit.entity.EntityType;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public enum MobDisplayName {
    ZOMBIE(EntityType.ZOMBIE, "Zumbi"),
    IRON_GOLEM(EntityType.IRON_GOLEM, "Golem"),
    SLIME(EntityType.SLIME, "Slime"),
    PIG(EntityType.PIG, "Porco");

    private final EntityType entityType;
    private final String displayName;

    MobDisplayName(EntityType entityType, String displayName) {
        this.entityType = entityType;
        this.displayName = displayName;
    }

    public EntityType getEntityType() {
        return entityType;
    }

    public String getDisplayName() {
        return displayName;
    }

    public static Set<EntityType> supportedTypes() {
        return Arrays.stream(values())
                .map(MobDisplayName::getEntityType)
                .collect(Collectors.toSet());
    }
}
