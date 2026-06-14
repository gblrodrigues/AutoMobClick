package com.gblrod.automobclick.service;

import com.gblrod.automobclick.model.MobKill;
import org.bukkit.entity.EntityType;

import java.util.*;

public class MobStatisticsService {
    private static final long FIVE_MINUTES = 5 * 60 * 1000L;
    private final Map<UUID, List<MobKill>> kills = new HashMap<>();

    public void registerKill(
            UUID playerId,
            EntityType entityType
    ) {
        kills.computeIfAbsent(playerId, ignored -> new ArrayList<>()).add(
                new MobKill(
                        entityType,
                        System.currentTimeMillis()
                )
        );
    }

    private void cleanup(UUID playerId) {
        List<MobKill> playerKills = kills.get(playerId);

        if (playerKills == null) {
            return;
        }

        long now = System.currentTimeMillis();
        playerKills.removeIf(kill -> now - kill.timestamp() > FIVE_MINUTES);

        if (playerKills.isEmpty()) {
            kills.remove(playerId);
        }
    }

    public Map<EntityType, Long> getStatistics(UUID playerId) {
        cleanup(playerId);

        List<MobKill> playerKills = kills.get(playerId);

        if (playerKills == null) {
            return Collections.emptyMap();
        }

        Map<EntityType, Long> statistics = new HashMap<>();

        for (MobKill kill : playerKills) {
            statistics.merge(kill.type(), 1L, Long::sum);
        }

        return statistics;
    }

    public long getTotalKills(UUID playerId) {
        return getStatistics(playerId)
                .values()
                .stream()
                .mapToLong(Long::longValue)
                .sum();
    }
}