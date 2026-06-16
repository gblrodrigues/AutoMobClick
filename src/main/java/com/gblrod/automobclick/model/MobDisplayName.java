package com.gblrod.automobclick.model;

import org.bukkit.entity.EntityType;

public enum MobDisplayName {
    ZOMBIE(EntityType.ZOMBIE, "Zumbi"),
    IRON_GOLEM(EntityType.IRON_GOLEM, "Golem"),
    PIG(EntityType.PIG, "Porco"),
    BREEZE(EntityType.BREEZE, "Vórtice"),
    BLAZE(EntityType.BLAZE, "Blaze");

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

    public static String getDisplayName(EntityType entityType) {
        for (MobDisplayName mob : values()) {
            if (mob.entityType == entityType) {
                return mob.displayName;
            }
        }

        return entityType.name();
    }
}