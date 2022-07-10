package com.consolex.rainbowsix.game.MapSystem;

import org.bukkit.World;

public interface GameMap {

    boolean load();
    void unload();
    boolean restoreFromSource();

    boolean isLoaded();
    World getWorld();
}
