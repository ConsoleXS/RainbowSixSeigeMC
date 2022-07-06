package com.consolex.rainbowsix;

import com.consolex.rainbowsix.game.GameManager;
import com.consolex.rainbowsix.game.Team;
import org.bukkit.plugin.java.JavaPlugin;

public final class RainbowSix extends JavaPlugin {

    private GameManager gameManager;
    private Team team;

    @Override
    public void onEnable() {
        // Plugin startup logic
        System.out.println("RainbowSix Initialized!");

        this.gameManager = new GameManager();
        this.team = new Team();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        System.out.println("RainbowSix Shutdown...");
    }
}
