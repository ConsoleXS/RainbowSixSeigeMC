package com.consolex.rainbowsix.game.events;

import com.consolex.rainbowsix.game.GameManager;
import com.consolex.rainbowsix.game.GameState;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import javax.swing.*;
import java.util.Arrays;
import java.util.HashMap;

public class ShopGUI implements Listener {
    private final Inventory inv;
    private GameManager gameManager;
    HashMap<Material, Integer> itemCosts = new HashMap<>();

    public ShopGUI(GameManager gameManager) {
        this.gameManager = gameManager;
        inv = Bukkit.createInventory(null, 9, "Example");

        // Put the items into the inventory
        initializeItems();
    }

    // You can call this whenever you want to put the items in
    public void initializeItems() {
        inv.addItem(createGuiItem(Material.DIAMOND_SWORD, 800,"Knife", "§aRecommended in short range combat.", "§bIncreases player speed while holding it!"));
        inv.addItem(createGuiItem(Material.IRON_HELMET, 34,"§bIron Helmet", "§aSeriously you need this, protects from headshots!", "§bProtection V + 20% headshot reduction"));
        inv.addItem(createGuiItem(Material.DIAMOND_HELMET, 34,"§bDiamond Helmet", "§aSeriously you need this, protects from headshots!", "§bProtection X + 40% headshot reduction"));
    }

    // Nice little method to create a gui item with a custom name, and description
    protected ItemStack createGuiItem(final Material material, int creditCost, final String name, final String... lore) {
        final ItemStack item = new ItemStack(material, 1);
        final ItemMeta meta = item.getItemMeta();

        itemCosts.put(material, creditCost);
        // Set the name of the item
        assert meta != null;
        meta.setDisplayName(name);

        // Set the lore of the item
        meta.setLore(Arrays.asList(lore));

        item.setItemMeta(meta);

        return item;
    }

    // You can open the inventory with this

    // Check for clicks on items
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!event.getInventory().equals(inv)) return;

        event.setCancelled(true);

        final ItemStack clickedItem = event.getCurrentItem();

        // verify current item is not null
        if (clickedItem == null || clickedItem.getType().isAir()) return;

        final Player player = (Player) event.getWhoClicked();

        int itemCost = itemCosts.get(clickedItem.getType());
        int playerCredit = gameManager.creditCount.get(player);

        if (itemCost <= playerCredit)
        {
            gameManager.creditCount.put(player, playerCredit - itemCost);
            player.sendMessage(ChatColor.DARK_AQUA + "You have " + gameManager.creditCount.get(player) + " credits left!");
            player.getInventory().addItem(clickedItem);
        }


    }


    @EventHandler
    public void onInventoryClick(final InventoryDragEvent event) {
        if (event.getInventory().equals(inv)) {
            event.setCancelled(true);
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
                //OPEN KIT GUI
            }
            else if (gameManager.gameState == GameState.ROUND_STARTING)
            {
                player.openInventory(inv);
            }
            else {
                player.sendMessage(ChatColor.RED + "You cannot select kits now!");
            }
        }
    }



}
