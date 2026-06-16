package com.gblrod.automobclick;

import com.gblrod.automobclick.command.AutoMobClickCommand;
import com.gblrod.automobclick.config.MobConfigurationService;
import com.gblrod.automobclick.listener.MobDeathListener;
import com.gblrod.automobclick.listener.PlayerConnectionListener;
import com.gblrod.automobclick.presentation.AutoclickMessageService;
import com.gblrod.automobclick.presentation.StatisticsMessageService;
import com.gblrod.automobclick.service.AutoMobClickService;
import com.gblrod.automobclick.service.MessageService;
import com.gblrod.automobclick.service.MobStatisticsService;
import com.gblrod.automobclick.service.PlayerStatisticsStorageService;
import org.bukkit.entity.EntityType;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;
import java.util.Optional;
import java.util.Set;

public class AutoMobClickPlugin extends JavaPlugin {
    private AutoMobClickService autoClickService;
    private MobStatisticsService statisticsService;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        MessageService messageService = new MessageService(getConfig());

        long statisticsWindowMinutes = getConfig().getLong("settings.statistics_window_minutes", 5);
        long statisticsWindowMillis = statisticsWindowMinutes * 60_000L;

        MobConfigurationService mobConfigurationService = new MobConfigurationService(getConfig(), getLogger());
        AutoclickMessageService autoclickMessageService = new AutoclickMessageService(messageService);
        Set<EntityType> allowedMobs = mobConfigurationService.getAllowedMobs();

        PlayerStatisticsStorageService storageService = new PlayerStatisticsStorageService(this);
        storageService.initialize();

        statisticsService = new MobStatisticsService(
                statisticsWindowMillis,
                storageService
        );

        getServer().getPluginManager().registerEvents(new PlayerConnectionListener(statisticsService), this);

        autoClickService = new AutoMobClickService(this, autoclickMessageService, allowedMobs);
        StatisticsMessageService statisticsMessageService = new StatisticsMessageService(
                statisticsService,
                messageService,
                statisticsWindowMinutes
        );

        Objects.requireNonNull(getCommand("automobclick")).setExecutor(
                new AutoMobClickCommand(
                        autoClickService,
                        statisticsMessageService
                )
        );

        getServer().getPluginManager().registerEvents(new MobDeathListener(
                        statisticsService,
                        autoClickService,
                        allowedMobs
                ),
                this
        );
    }

    @Override
    public void onDisable() {
        if (statisticsService != null) {
            statisticsService.saveAll();
        }

        Optional.ofNullable(autoClickService).ifPresent(AutoMobClickService::shutdown);
    }
}