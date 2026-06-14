package com.gblrod.automobclick.presentation;

import com.gblrod.automobclick.model.MobDisplayName;
import com.gblrod.automobclick.service.MobStatisticsService;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.UUID;

public class StatisticsMessageService {
    private final MobStatisticsService statisticsService;

    public StatisticsMessageService(MobStatisticsService statisticsService) {
        this.statisticsService = statisticsService;
    }

    public void sendStatistics(Player player) {
        UUID playerId = player.getUniqueId();
        long totalKills = statisticsService.getTotalKills(playerId);

        if (totalKills == 0) {
            player.sendMessage("§b§lAUTOCLICK! §cVocê não eliminou nenhum mob §bnos últimos §e5m§c.");
            return;
        }

        Map<EntityType, Long> statistics = statisticsService.getStatistics(playerId);

        player.sendMessage("");
        player.sendMessage("§b§lAUTOCLICK! §aVocê eliminou §e" + totalKills + " mobs §anos últimos §b5m§a:");
        player.sendMessage("");
        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BELL, 1f, 0.7f);

        for (MobDisplayName mob : MobDisplayName.values()) {
            long amount = statistics.getOrDefault(mob.getEntityType(), 0L);

            if (amount <= 0) {
                continue;
            }

            player.sendMessage("§b ▶ §e" + mob.getDisplayName() + ": §a" + amount);
        }
    }
}