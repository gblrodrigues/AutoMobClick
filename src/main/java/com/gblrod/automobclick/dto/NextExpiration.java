package com.gblrod.automobclick.dto;

import org.bukkit.entity.EntityType;

public record NextExpiration(
        EntityType entityType,
        long remainingMillis
) {
}