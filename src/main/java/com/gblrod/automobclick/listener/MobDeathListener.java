package com.gblrod.automobclick.listener;

import com.gblrod.automobclick.service.AutoMobClickService;
import com.gblrod.automobclick.service.MobStatisticsService;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

import java.util.Set;

public class MobDeathListener implements Listener {
    private final MobStatisticsService statisticsService;
    private final AutoMobClickService autoClickService;
    private final Set<EntityType> allowedMobs;

    public MobDeathListener(
            MobStatisticsService statisticsService,
            AutoMobClickService autoClickService,
            Set<EntityType> allowedMobs
    ) {
        this.statisticsService = statisticsService;
        this.autoClickService = autoClickService;
        this.allowedMobs = allowedMobs;
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

        if (!allowedMobs.contains(event.getEntity().getType())) {
            return;
        }

        statisticsService.registerKill(
                killer.getUniqueId(),
                event.getEntity().getType()
        );
    }
}