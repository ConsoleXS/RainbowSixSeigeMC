package com.consolex.rainbowsix.game.tasks;

import com.consolex.rainbowsix.game.GameManager;
import com.consolex.rainbowsix.game.GameState;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

public class GameStartTimer extends BukkitRunnable {

    private final GameManager gameManager;
    private int timeLeft = 20;


    public GameStartTimer(GameManager gameManager)
    {
        this.gameManager = gameManager;
    }

    @Override
    public void run() {
        timeLeft--;
        if (timeLeft <= 0)
        {
            cancel();
            gameManager.setGameState(GameState.ROUND_STARTING);
            return;
        }

        Bukkit.getOnlinePlayers().forEach(player -> player.sendMessage("Starting in " + timeLeft));
    }
}
