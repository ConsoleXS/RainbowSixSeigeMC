package com.consolex.rainbowsix.game.tasks;

import com.consolex.rainbowsix.game.GameManager;
import com.consolex.rainbowsix.game.GameState;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

public class LobbyStartTimer extends BukkitRunnable {

    private final GameManager gameManager;
    private int timeLeft = 10;


    public LobbyStartTimer(GameManager gameManager)
    {
        this.gameManager = gameManager;
    }

    @Override
    public void run() {
        timeLeft--;
        if (timeLeft <= 0)
        {
            cancel();
            gameManager.setGameState(GameState.GAME_STARTING);
            return;
        }

        Bukkit.getOnlinePlayers().forEach(player -> player.sendTitle("Starting in " + timeLeft, "", 10, 20, 10));
    }




}
