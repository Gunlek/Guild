package com.simpleduino.guild.Listeners;

import com.simpleduino.guild.SQL.GuildSQL;
import mkremins.fanciful.FancyMessage;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

/**
 * Created by Simple-Duino on 04/07/2016.
 * Copyrights Simple-Duino, all rights reserved
 */

public class PlayerListener implements Listener {

    private GuildSQL guildSQL = new GuildSQL();

    @EventHandler
    public void onInventoryClickPaper(InventoryClickEvent e)
    {
        Inventory inv = e.getClickedInventory();
        if(ChatColor.stripColor(e.getInventory().getName()).equalsIgnoreCase("guilde")) {
            if (inv.getItem(e.getSlot()).getType().equals(Material.PAPER)) {
                if (guildSQL.hasGuild((Player) e.getWhoClicked())) {
                    String guildName = guildSQL.getGuild((Player) e.getWhoClicked());
                    ItemStack clickedItem = inv.getItem(e.getSlot());
                    String itemName = ChatColor.stripColor(clickedItem.getItemMeta().getDisplayName());
                    if (itemName.toLowerCase().contains("membres")) {
                        int memberAmount = Integer.parseInt(itemName.split(" ")[0]);
                        if (clickedItem.getEnchantments().size() == 0) {
                            if (e.getSlot() == 0) {
                                if (Integer.parseInt(guildSQL.getGuildCoins(guildName)) >= 1000) {
                                    guildSQL.setGuildCoins(guildName, Integer.parseInt(guildSQL.getGuildCoins(guildName)) - 1000);
                                    guildSQL.setGuildMaxMembers(guildName, memberAmount);
                                    e.getWhoClicked().sendMessage(ChatColor.GREEN + "Vous avez augmenté le nombre maximum de membres dans votre guilde");
                                    e.getWhoClicked().closeInventory();
                                } else {
                                    e.getWhoClicked().sendMessage(ChatColor.RED + "Votre guilde n'a pas assez d'argent pour améliorer cette capacité");
                                    e.getWhoClicked().closeInventory();
                                }
                            } else if (inv.getItem(e.getSlot() - 1).getEnchantments().size() >= 1) {
                                if (Integer.parseInt(guildSQL.getGuildCoins(guildName)) >= 10000) {
                                    guildSQL.setGuildCoins(guildName, Integer.parseInt(guildSQL.getGuildCoins(guildName)) - 10000);
                                    guildSQL.setGuildMaxMembers(guildName, memberAmount);
                                    e.getWhoClicked().sendMessage(ChatColor.GREEN + "Vous avez augmenté le nombre maximum de membres dans votre guilde");
                                    e.getWhoClicked().closeInventory();
                                } else {
                                    e.getWhoClicked().sendMessage(ChatColor.RED + "Votre guilde n'a pas assez d'argent pour améliorer cette capacité");
                                    e.getWhoClicked().closeInventory();
                                }
                            } else {
                                e.getWhoClicked().closeInventory();
                                e.getWhoClicked().sendMessage(ChatColor.RED + "Vous devez acheter les améliorations précédentes avant d'acheter celle-ci");
                            }
                        } else {
                            e.getWhoClicked().closeInventory();
                            e.getWhoClicked().sendMessage(ChatColor.RED + "Vous possédez déjà cette amélioration");
                        }
                        e.setCancelled(true);
                    }
                } else {
                    e.getWhoClicked().sendMessage(ChatColor.RED + "Vous n'êtes pas membre d'une guilde");
                }
            }
        }
    }

    @EventHandler
    public void onInventoryClickGold(InventoryClickEvent e)
    {
        Inventory inv = e.getClickedInventory();
        if(ChatColor.stripColor(e.getInventory().getName()).equalsIgnoreCase("guilde")) {
            if (inv.getItem(e.getSlot()).getType().equals(Material.GOLD_INGOT)) {
                if (guildSQL.hasGuild((Player) e.getWhoClicked())) {
                    String guildName = guildSQL.getGuild((Player) e.getWhoClicked());
                    ItemStack clickedItem = inv.getItem(e.getSlot());
                    String itemName = ChatColor.stripColor(clickedItem.getItemMeta().getDisplayName());
                    if (itemName.toLowerCase().contains("coins")) {
                        int coinAmount = Integer.parseInt(itemName.split(" ")[0]);
                        if (clickedItem.getEnchantments().size() == 0) {
                            if (e.getSlot() == 0) {
                                if (Integer.parseInt(guildSQL.getGuildCoins(guildName)) >= 1000) {
                                    guildSQL.setGuildCoins(guildName, Integer.parseInt(guildSQL.getGuildCoins(guildName)) - 1000);
                                    guildSQL.setGuildCoinsPerDay(guildName, coinAmount);
                                    e.getWhoClicked().sendMessage(ChatColor.GREEN + "Vous avez augmenté le nombre maximum de coins par jour que peut gagner votre");
                                    e.getWhoClicked().closeInventory();
                                } else {
                                    e.getWhoClicked().sendMessage(ChatColor.RED + "Votre guilde n'a pas assez d'argent pour améliorer cette capacité");
                                    e.getWhoClicked().closeInventory();
                                }
                            } else if (inv.getItem(e.getSlot() - 1).getEnchantments().size() >= 1) {
                                if (Integer.parseInt(guildSQL.getGuildCoins(guildName)) >= 10000) {
                                    guildSQL.setGuildCoins(guildName, Integer.parseInt(guildSQL.getGuildCoins(guildName)) - 10000);
                                    guildSQL.setGuildCoinsPerDay(guildName, coinAmount);
                                    e.getWhoClicked().sendMessage(ChatColor.GREEN + "Vous avez augmenté le nombre maximum de coins par jour que peut gagner votre");
                                    e.getWhoClicked().closeInventory();
                                } else {
                                    e.getWhoClicked().sendMessage(ChatColor.RED + "Votre guilde n'a pas assez d'argent pour améliorer cette capacité");
                                    e.getWhoClicked().closeInventory();
                                }
                            } else {
                                e.getWhoClicked().closeInventory();
                                e.getWhoClicked().sendMessage(ChatColor.RED + "Vous devez acheter les améliorations précédentes avant d'acheter celle-ci");
                            }
                        } else {
                            e.getWhoClicked().closeInventory();
                            e.getWhoClicked().sendMessage(ChatColor.RED + "Vous possédez déjà cette amélioration");
                        }
                        e.setCancelled(true);
                    }
                } else {
                    e.getWhoClicked().sendMessage(ChatColor.RED + "Vous n'êtes pas membre d'une guilde");
                }
            }
        }
    }

    @EventHandler
    public void onInventoryClickPainting(InventoryClickEvent e)
    {
        Inventory inv = e.getClickedInventory();
        if(ChatColor.stripColor(e.getInventory().getName()).equalsIgnoreCase("guilde")) {
            if (inv.getItem(e.getSlot()).getType().equals(Material.PAINTING)) {
                if (guildSQL.hasGuild((Player) e.getWhoClicked())) {
                    String guildName = guildSQL.getGuild((Player) e.getWhoClicked());
                    ItemStack clickedItem = inv.getItem(e.getSlot());
                    String itemName = ChatColor.stripColor(clickedItem.getItemMeta().getDisplayName());
                    if (itemName.toLowerCase().contains("tag")) {
                        if (itemName.toLowerCase().contains("débloquer")) {
                            if (Integer.parseInt(guildSQL.getGuildCoins(guildName)) >= 20000) {
                                guildSQL.setGuildCoins(guildName, Integer.parseInt(guildSQL.getGuildCoins(guildName)) - 20000);
                                guildSQL.setHasTag(true, guildName);
                                e.getWhoClicked().closeInventory();
                                e.getWhoClicked().sendMessage(ChatColor.GREEN + "Vous avez acheté la possibilité d'avoir un grade pour votre guilde");
                            } else {
                                e.getWhoClicked().sendMessage(ChatColor.RED + "Votre guilde n'a pas assez d'argent pour acquérir cette capacité");
                                e.getWhoClicked().closeInventory();
                            }
                        } else {
                            new FancyMessage("Cliquez ici pour modifier le tag de votre guilde")
                                    .color(ChatColor.BLUE)
                                    .style(ChatColor.BOLD)
                                    .suggest("/guild tag ")
                                    .send((Player) e.getWhoClicked());
                            e.getWhoClicked().closeInventory();
                        }
                        e.setCancelled(true);
                    }
                }
            }
        }
    }

    @EventHandler
    public void onInventoryClickSign(InventoryClickEvent e)
    {
        Inventory inv = e.getClickedInventory();
        if(ChatColor.stripColor(e.getInventory().getName()).equalsIgnoreCase("guilde")) {
            if (inv.getItem(e.getSlot()).getType().equals(Material.SIGN)) {
                if (guildSQL.hasGuild((Player) e.getWhoClicked())) {
                    String guildName = guildSQL.getGuild((Player) e.getWhoClicked());
                    ItemStack clickedItem = inv.getItem(e.getSlot());
                    String itemName = ChatColor.stripColor(clickedItem.getItemMeta().getDisplayName());
                    if (itemName.toLowerCase().contains("motd")) {
                        e.getWhoClicked().closeInventory();
                        new FancyMessage("Cliquez ici pour modifier le MOTD de votre guilde")
                                .color(ChatColor.BLUE)
                                .style(ChatColor.BOLD)
                                .suggest("/guild motd ")
                                .send((Player) e.getWhoClicked());
                        e.setCancelled(true);
                    }
                }
            }
        }
    }

}
