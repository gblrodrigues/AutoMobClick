package com.gblrod.automobclick.listener;

import com.gblrod.automobclick.model.MobDisplayName;
import com.gblrod.automobclick.service.AutoMobClickService;
import com.gblrod.automobclick.service.MobStatisticsService;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

public class MobDeathListener implements Listener {
    private final MobStatisticsService statisticsService;
    private final AutoMobClickService autoClickService;

    public MobDeathListener(
            MobStatisticsService statisticsService,
            AutoMobClickService autoClickService
    ) {
        this.statisticsService = statisticsService;
        this.autoClickService = autoClickService;
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        Player killer = event.getEntity().getKiller();

        if (killer == null) {
            return;
        }

        if (!autoClickService.isEnabled(killer)) {
            return;
        }

        if (!MobDisplayName.supportedTypes().contains(event.getEntity().getType())) {
            return;
        }

        statisticsService.registerKill(
                killer.getUniqueId(),
                event.getEntity().getType()
        );
    }
}