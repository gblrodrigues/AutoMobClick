package com.gblrod.automobclick;

import com.gblrod.automobclick.command.AutoMobClickCommand;
import com.gblrod.automobclick.config.MobConfigurationService;
import com.gblrod.automobclick.listener.MobDeathListener;
import com.gblrod.automobclick.presentation.AutoclickMessageService;
import com.gblrod.automobclick.presentation.StatisticsMessageService;
import com.gblrod.automobclick.service.AutoMobClickService;
import com.gblrod.automobclick.service.MobStatisticsService;
import org.bukkit.entity.EntityType;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;
import java.util.Optional;
import java.util.Set;

public class AutoMobClickPlugin extends JavaPlugin {
    private AutoMobClickService autoClickService;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        MobConfigurationService mobConfigurationService = new MobConfigurationService(getConfig(), getLogger());
        AutoclickMessageService autoclickMessageService = new AutoclickMessageService();
        Set<EntityType> allowedMobs = mobConfigurationService.getAllowedMobs();

        autoClickService = new AutoMobClickService(
                this,
                autoclickMessageService,
                allowedMobs
        );
        MobStatisticsService statisticsService = new MobStatisticsService();

        StatisticsMessageService statisticsMessageService = new StatisticsMessageService(statisticsService);

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
        Optional.ofNullable(autoClickService).ifPresent(AutoMobClickService::shutdown);
    }
}