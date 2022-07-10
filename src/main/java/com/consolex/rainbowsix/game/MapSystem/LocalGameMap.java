package com.consolex.rainbowsix.game.MapSystem;

import org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;

import java.io.File;
import java.io.IOException;

public class LocalGameMap implements GameMap {

    private final File sourceWorldFolder;
    private File activeWorldFolder;

    private World world;



    public LocalGameMap(File worldFolder, String worldName, boolean loadOnInit)
    {
        this.sourceWorldFolder = new File (
                worldFolder, worldName
        );

        if (loadOnInit) load();
    }



    @Override
    public boolean load() {
        if (isLoaded()) return true;

        this.activeWorldFolder = new File(
                Bukkit.getWorldContainer().getParentFile(),
                sourceWorldFolder.getName() + "_active_" + System.currentTimeMillis()
        );

        try
        {
            FileUtils.copyDirectory(sourceWorldFolder, activeWorldFolder);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }



        this.world = Bukkit.createWorld(
                new WorldCreator(activeWorldFolder.getName())
        );

        if (world != null) this.world.setAutoSave(false);


        return isLoaded();
    }

    @Override
    public void unload() {
        if (world != null) Bukkit.unloadWorld(world, false);
        if (activeWorldFolder != null)
        {
            try
            {
                FileUtils.forceDelete(activeWorldFolder);
            } catch (IOException e)
            {
                e.printStackTrace();
            }

            world = null;
            activeWorldFolder = null;
        }
    }

    @Override
    public boolean restoreFromSource() {

        unload();
        return load();

    }

    @Override
    public boolean isLoaded() {
        return getWorld() != null;
    }

    @Override
    public World getWorld() {
        return world;
    }
}
