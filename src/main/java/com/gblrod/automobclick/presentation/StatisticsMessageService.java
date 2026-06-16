package com.gblrod.automobclick.presentation;

import com.gblrod.automobclick.dto.NextExpiration;
import com.gblrod.automobclick.model.MobDisplayName;
import com.gblrod.automobclick.service.MessageService;
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
    private final MessageService messageService;
    private final long statisticsWindowMinutes;

    public StatisticsMessageService(
            MobStatisticsService statisticsService,
            MessageService messageService,
            long statisticsWindowMinutes
    ) {
        this.statisticsService = statisticsService;
        this.messageService = messageService;
        this.statisticsWindowMinutes = statisticsWindowMinutes;
    }

    public void sendStatistics(Player player) {
        UUID playerId = player.getUniqueId();
        long totalKills = statisticsService.getTotalKills(playerId);

        if (totalKills == 0) {
            player.sendMessage(messageService.get(
                    "ac_no_stats",
                    Map.of("minutes", String.valueOf(statisticsWindowMinutes))
            ));
            player.playSound(player.getLocation(), Sound.BLOCK_COMPARATOR_CLICK, 1f, 0.5f);
            return;
        }

        Map<EntityType, Long> statistics = statisticsService.getStatistics(playerId);
        Optional<NextExpiration> expiration = Optional.ofNullable(statisticsService.getNextExpiration(playerId));

        player.sendMessage("");
        player.sendMessage(messageService.get("ac_stats_header",
                Map.of(
                        "total", String.valueOf(totalKills),
                        "minutes", String.valueOf(statisticsWindowMinutes))
        ));
        player.sendMessage("");
        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BELL, 1f, 0.7f);

        for (MobDisplayName mob : MobDisplayName.values()) {
            long amount = statistics.getOrDefault(mob.getEntityType(), 0L);

            if (amount <= 0) {
                continue;
            }

            player.sendMessage(messageService.get("stats_mob_line",
                    Map.of("mob", mob.getDisplayName(), "amount", String.valueOf(amount))
            ));
        }

        expiration.ifPresent(next -> {
            String mobName = MobDisplayName.getDisplayName(next.entityType());

            player.sendMessage("");
            player.sendMessage(messageService.get("ac_next_expiration",
                    Map.of(
                            "mob", mobName,
                            "time",
                            TimeFormatter.formatMillis(next.remainingMillis()))
            ));
        });
    }
}