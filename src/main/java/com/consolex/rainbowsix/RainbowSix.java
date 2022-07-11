package com.consolex.rainbowsix;

import com.consolex.rainbowsix.game.GameManager;
import com.consolex.rainbowsix.game.MapSystem.GameMap;
import com.consolex.rainbowsix.game.MapSystem.LocalGameMap;
import com.consolex.rainbowsix.game.Team;
import com.consolex.rainbowsix.game.commands.QuitCommand;
import com.consolex.rainbowsix.game.commands.StartCommand;
import com.consolex.rainbowsix.game.events.ShopGUI;
import com.consolex.rainbowsix.game.events.Gameplay;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class RainbowSix extends JavaPlugin {

    private GameManager gameManager;
    private Team team;
    private GameMap map;


    @Override
    public void onEnable() {
        // Plugin startup logic
        System.out.println("RainbowSix Initialized!");

        // --- Creating Map ---- //
        getDataFolder().mkdirs();

        File gameMapsFolder = new File(getDataFolder(), "gameMaps");
        if (!gameMapsFolder.exists()) {
            gameMapsFolder.mkdirs();
        }

        map = new LocalGameMap(gameMapsFolder, "coastline", true);


        this.gameManager = new GameManager(map, this);
        this.team = new Team();
        getServer().getPluginManager().registerEvents(new Gameplay(gameManager), this);
        getServer().getPluginManager().registerEvents(new ShopGUI(gameManager), this);

        getCommand("start").setExecutor(new StartCommand(gameManager));
        getCommand("quit").setExecutor(new QuitCommand(gameManager));
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        System.out.println("RainbowSix has Shutdown...");
    }
}
