package com.consolex.rainbowsix.game.tasks;

import com.consolex.rainbowsix.game.GameManager;
import com.consolex.rainbowsix.game.GameState;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Objects;

public class DiffuseTimer extends BukkitRunnable {

    private GameManager gameManager;
    public DiffuseTimer(GameManager gameManager)
    {
        this.gameManager = gameManager;
    }
    int timeLeft = 8;

    @Override
    public void run() {
        if (Objects.requireNonNull(gameManager.getBombLocation().getWorld()).getNearbyEntities(gameManager.getBombLocation(), 1.5, 1.5, 1.5).size() >= 1)
        {
            timeLeft--;
        }
        else {
            timeLeft = 8;
            Bukkit.broadcastMessage(ChatColor.RED + "Canceled Diffuse!");
            cancel();
            return;
        }


        if (timeLeft <= 0)
        {
            cancel();
            gameManager.defuseBomb();
            return;
        }

        Bukkit.getOnlinePlayers().forEach(player -> player.sendMessage(ChatColor.BOLD + "Bomb Defusing... (" + timeLeft + ")"));
    }
}
