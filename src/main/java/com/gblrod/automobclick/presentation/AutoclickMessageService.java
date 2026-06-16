package com.gblrod.automobclick.presentation;

import com.gblrod.automobclick.service.MessageService;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class AutoclickMessageService {
    private final MessageService messageService;

    public AutoclickMessageService(MessageService messageService) {
        this.messageService = messageService;
    }

    public void sendEnabledMessage(Player player) {
        player.sendMessage(messageService.get("ac_enabled"));
        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BELL, 1f, 0.7f);
    }

    public void sendDisabledMessage(Player player) {
        player.sendMessage(messageService.get("ac_disabled"));
        player.playSound(player.getLocation(), Sound.BLOCK_COMPARATOR_CLICK, 1f, 0.5f);
    }
}