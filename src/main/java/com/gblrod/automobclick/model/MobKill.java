package com.gblrod.automobclick.model;

import org.bukkit.entity.EntityType;

public record MobKill(
        EntityType type,
        long timestamp
) {}