package com.gblrod.automobclick.service;

import com.gblrod.automobclick.presentation.AutoclickMessageService;
import org.bukkit.GameMode;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.RayTraceResult;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class AutoMobClickService {
    private final JavaPlugin plugin;
    private final Map<UUID, BukkitTask> activeTasks = new HashMap<>();
    private static final long AC_TICKS = 2L;
    private final AutoclickMessageService messageService;
    private final Set<EntityType> allowedMobs;

    private boolean isInvalidPlayer(Player player) {
        return !player.isOnline() || player.isDead() || player.getGameMode() == GameMode.SPECTATOR;
    }

    public boolean isEnabled(Player player) {
        return activeTasks.containsKey(player.getUniqueId());
    }

    public AutoMobClickService(
            JavaPlugin plugin,
            AutoclickMessageService messageService,
            Set<EntityType> allowedMobs
    ) {
        this.plugin = plugin;
        this.messageService = messageService;
        this.allowedMobs = allowedMobs;
    }

    public void toggle(Player player) {
        UUID playerId = player.getUniqueId();

        if (activeTasks.containsKey(playerId)) {
            stop(player);

            messageService.sendDisabledMessage(player);
            return;
        }

        start(player);

        messageService.sendEnabledMessage(player);
    }

    private void start(Player player) {
        UUID playerId = player.getUniqueId();
        BukkitTask task = new BukkitRunnable() {
            @Override
            public void run() {
                if (isInvalidPlayer(player)) {
                    stop(player);
                    return;
                }

                RayTraceResult rayResult = player.getWorld().rayTraceEntities(
                        player.getEyeLocation(),
                        player.getEyeLocation().getDirection(),
                        3.0,
                        0,
                        entity -> entity instanceof LivingEntity && !entity.equals(player)
                );

                if (rayResult == null) {
                    return;
                }

                if (!(rayResult.getHitEntity() instanceof LivingEntity target)) {
                    return;
                }

                if (target.isDead()) {
                    return;
                }

                if (!allowedMobs.contains(target.getType())) {
                    return;
                }

                player.swingMainHand();
                player.attack(target);
            }
        }.runTaskTimer(plugin, 0L, AC_TICKS);

        activeTasks.put(playerId, task);
    }

    private void stop(Player player) {
        UUID playerId = player.getUniqueId();
        BukkitTask task = activeTasks.remove(playerId);

        if (task != null) {
            task.cancel();
        }
    }

    public void shutdown() {
        activeTasks.values().forEach(BukkitTask::cancel);
        activeTasks.clear();
    }
}