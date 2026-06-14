package com.gblrod.automobclick.presentation;

import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class AutoclickMessageService {
    public void sendEnabledMessage(Player player) {
        player.sendMessage("§a§lAUTOCLICK! §eVocê §aativou §eo AutoClick.");
        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BELL, 1f, 0.7f);
    }

    public void sendDisabledMessage(Player player) {
        player.sendMessage("§a§lAUTOCLICK! §eVocê §cdesativou §eo AutoClick.");
        player.playSound(player.getLocation(), Sound.BLOCK_COMPARATOR_CLICK, 1f, 0.5f);
    }
}
