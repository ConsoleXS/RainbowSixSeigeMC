package com.consolex.rainbowsix.game.events;

import com.consolex.rainbowsix.game.*;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.*;
import org.bukkit.util.io.BukkitObjectInputStream;

import java.util.HashMap;

public class Gameplay implements Listener {

    private GameManager gameManager;


    HashMap<Player, KitType> playerKits = new HashMap<>();
    HashMap<Player, Integer> abilityCooldown = new HashMap<>();


    public Gameplay(GameManager gameManager)
    {
        this.gameManager = gameManager;
    }

    @EventHandler
    public void playerTakeDamage(EntityDamageEvent event)
    {
        Player player = (Player) event.getEntity();
        if (event.getEntity() instanceof Player && gameManager.gameState != GameState.ROUND_ACTIVE)
        {
            event.setCancelled(true);
        }
        else if (player.getHealth() - event.getDamage() <= 0)
        {
            event.setCancelled(true);
            player.setGameMode(GameMode.SPECTATOR);
            player.getInventory().clear();
            Bukkit.broadcastMessage(ChatColor.GOLD + player.getName() + " has died!");

        }

    }


    @EventHandler
    public void dropItem(PlayerDropItemEvent event)
    {
        event.getPlayer().sendMessage(ChatColor.RED + "Sorry... No dropping items :D");
        event.setCancelled(true);

    }

    @EventHandler
    public void breakBlock(BlockBreakEvent event)
    {
        if (gameManager.gameState != GameState.ROUND_ACTIVE || event.getPlayer().getWorld() == Bukkit.getWorld("world"))
        {
            event.setCancelled(true);
        }

    }

    @EventHandler
    public void playerJoin(PlayerJoinEvent event)
    {
        Player player = event.getPlayer();
        player.getInventory().clear();
        World world = Bukkit.getWorld("world");
        player.teleport(new Location(world, 1, 100, 1));
        player.getActivePotionEffects().clear();

    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event)
    {
        Player player = event.getPlayer();
        Location location = player.getLocation();
        Block block = location.getBlock().getRelative(BlockFace.DOWN);
        World world = Bukkit.getServer().getWorld("world");
        if (block.getType().equals(Material.RED_CONCRETE) && Team.getTeam(player) != TeamType.ATTACKERS)
        {
            Team.addToTeam(TeamType.DEFENDERS, player);
            player.teleport(new Location(world, 1, 100, -1));
            Bukkit.broadcastMessage(ChatColor.RED + player.getDisplayName() + " joined red team!");
            player.setPlayerListName(ChatColor.RED + player.getDisplayName());
        }
        else if (block.getType().equals(Material.BLUE_CONCRETE) && Team.getTeam(player) != TeamType.DEFENDERS)
        {
            Team.addToTeam(TeamType.DEFENDERS, player);
            player.teleport(new Location(world, 1, 100, 1));
            Bukkit.broadcastMessage(ChatColor.BLUE + player.getDisplayName() + " joined blue team!");
            player.setPlayerListName(ChatColor.BLUE + player.getDisplayName());
        }
    }

    @EventHandler
    public void playerRightClick(PlayerInteractEvent event)
    {
        Player player = event.getPlayer();
        if (player.getInventory().getItemInMainHand().getType().equals(Material.NETHER_STAR))
        {
            if (gameManager.gameState == GameState.GAME_STARTING)
            {
                //OPEN GUI
            }
            else {
                player.sendMessage(ChatColor.RED + "You cannot select kits now!");
            }
        }
    }





}
