package com.consolex.rainbowsix.game.tasks;

import com.consolex.rainbowsix.game.GameManager;
import com.consolex.rainbowsix.game.GameState;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

public class BombTimer extends BukkitRunnable {
    private final GameManager gameManager;
    private int timeLeft = 28;


    public BombTimer(GameManager gameManager)
    {
        this.gameManager = gameManager;
    }

    @Override
    public void run() {
        timeLeft--;
        if (timeLeft <= 0)
        {
            cancel();
            gameManager.setGameState(GameState.ROUND_ENDED_DEFENDERS);
            return;
        }

        Bukkit.getOnlinePlayers().forEach(player -> player.sendMessage("Bomb explodes in (TEST MODE IF IT IS LIKE THIS IN A REAL GAME WHOOPS) " + timeLeft));
    }
}
