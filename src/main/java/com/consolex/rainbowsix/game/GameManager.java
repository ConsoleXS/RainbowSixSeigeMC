package com.consolex.rainbowsix.game;

import com.consolex.rainbowsix.RainbowSix;
import com.consolex.rainbowsix.game.MapSystem.GameMap;
import com.consolex.rainbowsix.game.tasks.BombTimer;
import com.consolex.rainbowsix.game.tasks.GameStartTimer;
import com.consolex.rainbowsix.game.tasks.LobbyStartTimer;
import jdk.tools.jlink.plugin.Plugin;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.io.BukkitObjectInputStream;

import java.util.Arrays;
import java.util.HashMap;

public class GameManager {
    public GameState gameState = GameState.LOBBY;

    private GameMap gameMap;
    private GameStartTimer gameStartTimer;
    private LobbyStartTimer lobbyStartTimer;
    private BombTimer bombTimer;
    RainbowSix plugin;

    public HashMap<Player, Integer> creditCount = new HashMap<>();

    public GameManager(GameMap gameMap, RainbowSix plugin)
    {
        this.gameMap = gameMap;
        this.plugin = plugin;
    }

    public void setGameState(GameState gameState)
    {


        switch (gameState)
        {
            case LOBBY:
                // Teleport to lobby, Clear Inventory, Clear Effects, Clear Teams, Clear Scores, Adventure mode
                this.gameState = GameState.LOBBY;
                for (Player p : Bukkit.getOnlinePlayers())
                {
                    World lobby = Bukkit.getWorld("world");
                    p.teleport(new Location(lobby, 0, 100, 0));
                    p.getInventory().clear();
                    p.getActivePotionEffects().clear();
                    Team.clearTeams();
                    Count.clearPoints();
                    p.setGameMode(GameMode.ADVENTURE);
                    p.setHealth(20);
                    creditCount.clear();
                }
                break;
            case LOBBY_STARTING:
                this.gameState = GameState.LOBBY_STARTING;
                this.lobbyStartTimer = new LobbyStartTimer(this);
                this.lobbyStartTimer.runTaskTimer(plugin, 0, 20);
                //Just count down till starting kit selection
                break;
            case GAME_STARTING:
                //Start timer for kit selection, only happens once per game!
                //Give all players starting 800 credits!
                this.gameState = GameState.GAME_STARTING;
                for (Player p : Bukkit.getOnlinePlayers())
                {

                    if (Team.isInTeam(p))
                    {
                        p.getInventory().addItem(new ItemStack(Material.NETHER_STAR, 1));
                        p.sendTitle(ChatColor.GOLD + "Kit Selection!", "Right click nether star lol", 1, 1, 1);
                        World map = gameMap.getWorld();
                        p.teleport(new Location(map, -1, 100, -1));
                        p.setHealth(20);
                        p.setGameMode(GameMode.ADVENTURE);
                        creditCount.put(p, 800);
                    }
                }
                this.gameStartTimer = new GameStartTimer(this);
                this.gameStartTimer.runTaskTimer(plugin, 0, 20);
                break;
            case ROUND_STARTING:
                this.gameState = GameState.ROUND_STARTING;
                for (Player p : Bukkit.getOnlinePlayers())
                {
                    World map = gameMap.getWorld();

                    if (Team.getTeam(p) == TeamType.ATTACKERS)
                    {
                        p.teleport(new Location(map, -1, 100, -1));
                    }
                    else
                    {
                        p.teleport(new Location(map, 1, 100, 1));
                    }
                    if (Team.isInTeam(p))
                    {
                        p.getInventory().addItem(new ItemStack(Material.NETHER_STAR, 1));
                        p.sendTitle(ChatColor.GOLD + "Buy Guns!", "Right click nether star again lmao", 1, 1, 1);
                        p.sendMessage(ChatColor.GREEN + "- You have " + creditCount.get(p) + " credits -");
                        ItemStack ability = new ItemStack(Material.GRAY_DYE);
                        ItemMeta meta = ability.getItemMeta();
                        assert meta != null;
                        meta.setDisplayName(ChatColor.RED + "KIT ABILITY (Use during round)");
                        ability.setItemMeta(meta);
                    }
                }

                //Clear items and Teleport teams to their spawns, players can buy guns with money, not select kits.
                // Give action item
                break;
            case ROUND_ACTIVE:
                this.gameState = GameState.ROUND_ACTIVE;
                //Start bomb timer
                this.bombTimer = new BombTimer(this);
                this.bombTimer.runTaskTimer(plugin, 0, 20);
                //SPAWN THE BOMB
                break;
            case ROUND_ENDED_ATTACKERS:
                this.gameState = GameState.ROUND_ENDED_ATTACKERS;
                // Attackers get a point! (And money)
                // Give players money per kill, add their kills to their summative kills, and remove their current kill count.
                // End game if 5 rounds are won.
                Count.addPoint(TeamType.ATTACKERS);
                for (Player p : Bukkit.getOnlinePlayers())
                {
                    if (Team.getTeam(p) == TeamType.ATTACKERS)
                    {
                        if (creditCount.containsKey(p))
                        {
                            creditCount.put(p, creditCount.get(p) + 3000);
                        }
                    }
                }
                if (Count.getPoints(TeamType.ATTACKERS) >= 5)
                {
                    setGameState(GameState.LOBBY);
                    Bukkit.broadcastMessage(ChatColor.GOLD + "ATTACKERS WIN!");
                }
                else {
                    setGameState(GameState.ROUND_STARTING);
                }

                break;
            case ROUND_ENDED_DEFENDERS:
                this.gameState = GameState.ROUND_ENDED_DEFENDERS;
                Count.addPoint(TeamType.DEFENDERS);
                for (Player p : Bukkit.getOnlinePlayers())
                {
                    if (Team.getTeam(p) == TeamType.DEFENDERS)
                    {
                        if (creditCount.containsKey(p))
                        {
                            creditCount.put(p, creditCount.get(p) + 3000);
                        }
                    }
                }
                if (Count.getPoints(TeamType.DEFENDERS) >= 5)
                {
                    setGameState(GameState.LOBBY);
                    Bukkit.broadcastMessage(ChatColor.GOLD + "DEFENDERS WIN!");
                }
                else {
                    setGameState(GameState.ROUND_STARTING);
                }
                break;

        }
    }

    public void defuseBomb()
    {
        bombTimer.cancel();
        Bukkit.broadcastMessage("Bomb Defused!");
        setGameState(GameState.ROUND_ENDED_DEFENDERS);
    }





}
