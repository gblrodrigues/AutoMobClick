package com.gblrod.automobclick;

import com.gblrod.automobclick.command.AutoMobClickCommand;
import com.gblrod.automobclick.listener.MobDeathListener;
import com.gblrod.automobclick.presentation.AutoclickMessageService;
import com.gblrod.automobclick.presentation.StatisticsMessageService;
import com.gblrod.automobclick.service.AutoMobClickService;
import com.gblrod.automobclick.service.MobStatisticsService;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;
import java.util.Optional;

public class AutoMobClickPlugin extends JavaPlugin {

    private AutoMobClickService autoClickService;
    private MobStatisticsService statisticsService;

    @Override
    public void onEnable() {
        AutoclickMessageService autoclickMessageService = new AutoclickMessageService();

        autoClickService = new AutoMobClickService(this, autoclickMessageService);
        statisticsService = new MobStatisticsService();

        StatisticsMessageService statisticsMessageService = new StatisticsMessageService(statisticsService);

        Objects.requireNonNull(getCommand("automobclick")).setExecutor(
                new AutoMobClickCommand(
                        autoClickService,
                        statisticsMessageService
                )
        );

        getServer().getPluginManager().registerEvents(new MobDeathListener(
                        statisticsService,
                        autoClickService
                ),
                this
        );
    }

    @Override
    public void onDisable() {
        Optional.ofNullable(autoClickService).ifPresent(AutoMobClickService::shutdown);
    }
}