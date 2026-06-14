package com.gblrod.automobclick.listener;

import com.gblrod.automobclick.service.MobStatisticsService;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerConnectionListener implements Listener {
    private final MobStatisticsService statisticsService;

    public PlayerConnectionListener(MobStatisticsService statisticsService) {
        this.statisticsService = statisticsService;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        statisticsService.loadPlayer(event.getPlayer().getUniqueId());
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        statisticsService.unloadPlayer(event.getPlayer().getUniqueId());
    }
}