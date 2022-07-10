package com.consolex.rainbowsix.game.commands;

import com.consolex.rainbowsix.game.GameManager;
import com.consolex.rainbowsix.game.GameState;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class StartCommand implements CommandExecutor {

    private GameManager gameManager;

    public StartCommand(GameManager gameManager)
    {
        this.gameManager = gameManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (gameManager.gameState == GameState.LOBBY)
        {
            gameManager.setGameState(GameState.LOBBY_STARTING);
        }
        return true;

    }
}
