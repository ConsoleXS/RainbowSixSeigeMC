package com.consolex.rainbowsix.game.commands;

import com.consolex.rainbowsix.game.GameManager;
import com.consolex.rainbowsix.game.GameState;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.util.io.BukkitObjectInputStream;

public class QuitCommand implements CommandExecutor {
    private GameManager gameManager;

    public QuitCommand(GameManager gameManager)
    {
        this.gameManager = gameManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (gameManager.gameState != GameState.LOBBY)
        {
            gameManager.setGameState(GameState.LOBBY);
            Bukkit.broadcastMessage(ChatColor.RED + "Game force terminated by admin!");
        }
        return true;

    }
}
