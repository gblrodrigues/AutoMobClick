package com.gblrod.automobclick.service;

import com.gblrod.automobclick.dto.NextExpiration;
import org.bukkit.entity.EntityType;

import java.util.*;

public class MobStatisticsService {
    private final Map<UUID, Map<EntityType, List<Long>>> kills = new HashMap<>();
    private final long statisticsWindowMillis;
    private final PlayerStatisticsStorageService storageService;

    public void registerKill(UUID playerId, EntityType entityType) {
        kills
                .computeIfAbsent(playerId, k -> new HashMap<>())
                .computeIfAbsent(entityType, k -> new ArrayList<>())
                .add(System.currentTimeMillis());

        cleanup(playerId);
    }

    private void cleanup(UUID playerId) {
        Map<EntityType, List<Long>> playerKills = kills.get(playerId);

        if (playerKills == null) return;

        long now = System.currentTimeMillis();

        playerKills.values().forEach(list ->
                list.removeIf(timestamp -> now - timestamp > statisticsWindowMillis)
        );

        playerKills.entrySet().removeIf(entry -> entry.getValue().isEmpty());

        if (playerKills.isEmpty()) {
            kills.remove(playerId);
        }
    }

    public Map<EntityType, Long> getStatistics(UUID playerId) {
        cleanup(playerId);

        Map<EntityType, List<Long>> playerKills = kills.get(playerId);

        if (playerKills == null) {
            return Collections.emptyMap();
        }

        Map<EntityType, Long> result = new HashMap<>();

        for (var entry : playerKills.entrySet()) {
            result.put(entry.getKey(), (long) entry.getValue().size());
        }

        return result;
    }

    public long getTotalKills(UUID playerId) {
        return getStatistics(playerId)
                .values()
                .stream()
                .mapToLong(Long::longValue)
                .sum();
    }

    public MobStatisticsService(long statisticsWindowMillis, PlayerStatisticsStorageService storageService) {
        this.statisticsWindowMillis = statisticsWindowMillis;
        this.storageService = storageService;
    }

    public void savePlayer(UUID playerId) {
        Map<EntityType, List<Long>> playerKills = kills.get(playerId);

        if (playerKills == null) {
            return;
        }

        storageService.saveKills(playerId, playerKills);
    }

    public void unloadPlayer(UUID playerId) {
        savePlayer(playerId);
        kills.remove(playerId);
    }

    public void saveAll() {
        for (UUID playerId : new HashSet<>(kills.keySet())) {
            savePlayer(playerId);
        }
    }

    public void loadPlayer(UUID playerId) {
        Map<EntityType, List<Long>> loadedKills = storageService.loadKills(playerId);

        if (!loadedKills.isEmpty()) {
            kills.put(playerId, loadedKills);
        }

        cleanup(playerId);
    }

    public NextExpiration getNextExpiration(UUID playerId) {
        cleanup(playerId);

        Map<EntityType, List<Long>> playerKills = kills.get(playerId);

        if (playerKills == null) {
            return null;
        }

        EntityType nextEntityType = null;
        long oldestTimestamp = Long.MAX_VALUE;

        for (var entry : playerKills.entrySet()) {
            for (Long timestamp : entry.getValue()) {
                if (timestamp < oldestTimestamp) {
                    oldestTimestamp = timestamp;
                    nextEntityType = entry.getKey();
                }
            }
        }

        if (nextEntityType == null) {
            return null;
        }

        long remaining = (oldestTimestamp + statisticsWindowMillis) - System.currentTimeMillis();

        return new NextExpiration(
                nextEntityType,
                Math.max(remaining, 0L)
        );
    }
}