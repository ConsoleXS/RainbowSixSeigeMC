package com.consolex.rainbowsix.game.events;

import com.consolex.rainbowsix.RainbowSix;
import com.consolex.rainbowsix.game.*;
import com.consolex.rainbowsix.game.tasks.BombTimer;
import com.consolex.rainbowsix.game.tasks.DiffuseTimer;
import com.consolex.rainbowsix.game.tasks.GameStartTimer;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.*;

import java.util.HashMap;

public class Gameplay implements Listener {


    // TODO: Diamond sword speed boost, Wearing armor auto enchants it, KIT ABILITIES?



    private GameManager gameManager;


    HashMap<Player, KitType> playerKits = new HashMap<>();
    HashMap<Player, Integer> abilityCooldown = new HashMap<>();

    private DiffuseTimer diffuseTimer;
    private RainbowSix plugin;




    public Gameplay(GameManager gameManager, RainbowSix plugin)
    {
        this.gameManager = gameManager;
        this.plugin = plugin;
    }



    @EventHandler
    public void playerTakeDamage(EntityDamageEvent event)
    {
        if (event.getEntity() instanceof  Player)
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
            Team.addToTeam(TeamType.ATTACKERS, player);
            player.teleport(new Location(world, 1, 100, -1));
            Bukkit.broadcastMessage(ChatColor.RED + player.getDisplayName() + " joined attackers team!");
            player.setPlayerListName(ChatColor.RED + player.getDisplayName());
        }
        else if (block.getType().equals(Material.BLUE_CONCRETE) && Team.getTeam(player) != TeamType.DEFENDERS)
        {
            Team.addToTeam(TeamType.DEFENDERS, player);
            player.teleport(new Location(world, 1, 100, 1));
            Bukkit.broadcastMessage(ChatColor.BLUE + player.getDisplayName() + " joined defenders team!");
            player.setPlayerListName(ChatColor.BLUE + player.getDisplayName());
        }
    }

    @EventHandler
    public void defuseBomb(PlayerInteractAtEntityEvent event)
    {
        if (event.getRightClicked() instanceof ArmorStand)
        {
            this.diffuseTimer = new DiffuseTimer(gameManager);
            this.diffuseTimer.runTaskTimer(plugin, 0, 20);
        }
    }




}
