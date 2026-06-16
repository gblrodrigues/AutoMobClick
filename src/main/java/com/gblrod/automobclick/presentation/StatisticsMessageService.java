package com.gblrod.automobclick.presentation;

import com.gblrod.automobclick.dto.NextExpiration;
import com.gblrod.automobclick.model.MobDisplayName;
import com.gblrod.automobclick.service.MobStatisticsService;
import com.gblrod.automobclick.util.TimeFormatter;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.Optional;
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
            player.sendMessage("§b§lAUTOCLICK! §cVocê não eliminou nenhum mob nos últimos §e5m§c.");
            player.playSound(player.getLocation(), Sound.BLOCK_COMPARATOR_CLICK, 1f, 0.5f);
            return;
        }

        Map<EntityType, Long> statistics = statisticsService.getStatistics(playerId);
        Optional<NextExpiration> expiration = statisticsService.getNextExpiration(playerId);

        player.sendMessage("");
        player.sendMessage("§b§lAUTOCLICK! §eVocê eliminou §a" + totalKills + " mobs §enos últimos §a5m§a:");
        player.sendMessage("");
        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BELL, 1f, 0.7f);

        for (MobDisplayName mob : MobDisplayName.values()) {
            long amount = statistics.getOrDefault(mob.getEntityType(), 0L);

            if (amount <= 0) {
                continue;
            }

            player.sendMessage("§b ▶ §e" + mob.getDisplayName() + ": §a" + amount);
        }

        expiration.ifPresent(next -> {
            String mobName =
                    MobDisplayName.getDisplayName(
                            next.entityType()
                    );

            player.sendMessage("");
            player.sendMessage("§b 🕛 §7Próxima expiração: §c"
                    + mobName
                    + " §7em §c"
                    + TimeFormatter.formatMillis(next.remainingMillis())
            );
        });
    }
}