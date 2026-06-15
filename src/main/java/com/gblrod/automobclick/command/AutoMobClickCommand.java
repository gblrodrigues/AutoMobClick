package com.gblrod.automobclick.command;

import com.gblrod.automobclick.presentation.StatisticsMessageService;
import com.gblrod.automobclick.service.AutoMobClickService;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;

public class AutoMobClickCommand implements CommandExecutor {
    private final AutoMobClickService autoClickService;
    private final StatisticsMessageService statisticsMessageService;

    public AutoMobClickCommand(
            AutoMobClickService autoClickService,
            StatisticsMessageService statisticsMessageService
    ) {
        this.autoClickService = autoClickService;
        this.statisticsMessageService = statisticsMessageService;
    }

    @Override
    public boolean onCommand(
            @NotNull CommandSender sender,
            @NotNull Command command,
            @NotNull String label,
            String @NonNull [] args
    ) {
        if (!(sender instanceof Player player)) {
            return true;
        }

        if (args.length == 1 && args[0].equalsIgnoreCase("stats")) {
            statisticsMessageService.sendStatistics(player);
            return true;
        }

        autoClickService.toggle(player);
        return true;
    }
}