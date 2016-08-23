package com.simpleduino.guild.Inventories;

import com.simpleduino.guild.SQL.GuildSQL;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Simple-Duino on 04/07/2016.
 * Copyrights Simple-Duino, all rights reserved
 */

public class GuildShopInventory {

    private File cfgFile = new File("plugins/Guild/config.yml");
    private YamlConfiguration cfg = YamlConfiguration.loadConfiguration(cfgFile);
    private Inventory shop = Bukkit.createInventory(null, 54, ChatColor.GOLD.toString() + ChatColor.BOLD + "Guilde");
    private GuildSQL guildSQL = new GuildSQL();

    public GuildShopInventory(Player p)
    {
        int index = Integer.parseInt(cfg.get("guild.default.max-members").toString())+5;
        for(int i=0;i<18;i++)
        {
            ItemStack paper_is = new ItemStack(Material.PAPER, 1);
            ItemMeta im = paper_is.getItemMeta();
            im.setDisplayName(ChatColor.RESET.toString() + ChatColor.GREEN + Integer.toString(index)+" membres");
            ArrayList<String> lore = new ArrayList<>();
            lore.add(ChatColor.YELLOW + "Augmenter le nombre maximal");
            lore.add(ChatColor.YELLOW + "de membres dans votre guilde");
            if(i==0)
                lore.add(ChatColor.LIGHT_PURPLE + "Prix: 1000 coins");
            else
                lore.add(ChatColor.LIGHT_PURPLE + "Prix: 10 000 coins");
            im.setLore(lore);
            paper_is.setItemMeta(im);
            if(Integer.parseInt(guildSQL.getGuildMaxMembers(guildSQL.getGuild(p)))>=index)
                paper_is.addUnsafeEnchantment(Enchantment.DURABILITY, 1);
            this.shop.setItem(i, paper_is);
            index+=5;
        }
        index = Integer.parseInt(cfg.get("guild.default.coins").toString())+1000;
        for(int i=27;i<36;i++)
        {
            ItemStack gold_is = new ItemStack(Material.GOLD_INGOT, 1);
            ItemMeta im = gold_is.getItemMeta();
            im.setDisplayName(ChatColor.RESET.toString() + ChatColor.GREEN + Integer.toString(index)+" coins");
            ArrayList<String> lore = new ArrayList<>();
            lore.add(ChatColor.YELLOW + "Augmenter le nombre maximal");
            lore.add(ChatColor.YELLOW + "de coins par jour pour la guilde");
            if(i==27)
                lore.add(ChatColor.LIGHT_PURPLE + "Prix: 1000 coins");
            else
                lore.add(ChatColor.LIGHT_PURPLE + "Prix: 10 000 coins");
            im.setLore(lore);
            gold_is.setItemMeta(im);
            if(Integer.parseInt(guildSQL.getGuildCoinsPerDay(guildSQL.getGuild(p)))>=index)
                gold_is.addUnsafeEnchantment(Enchantment.DURABILITY, 1);
            this.shop.setItem(i, gold_is);
            if(i==34)
                index+=3000;
            else
                index+=1000;
        }

        ItemStack sign_is = new ItemStack(Material.SIGN);
        ItemMeta sign_im = sign_is.getItemMeta();
        sign_im.setDisplayName(ChatColor.RESET + "Modifier le MOTD");
        sign_is.setItemMeta(sign_im);
        this.shop.setItem(38, sign_is);

        if(guildSQL.hasGuild(p) && guildSQL.getHasTag(guildSQL.getGuild(p))) {
            ItemStack panel_is = new ItemStack(Material.PAINTING);
            ItemMeta panel_im = panel_is.getItemMeta();
            panel_im.setDisplayName(ChatColor.RESET + "Modifier le Tag");
            panel_is.setItemMeta(panel_im);
            this.shop.setItem(40, panel_is);
        }
        else if(guildSQL.hasGuild(p) && !guildSQL.getHasTag(guildSQL.getGuild(p)))
        {
            ItemStack panel_is = new ItemStack(Material.PAINTING);
            ItemMeta panel_im = panel_is.getItemMeta();
            panel_im.setDisplayName(ChatColor.RESET + "Débloquer le Tag");
            ArrayList<String> lore = new ArrayList<>();
            lore.add(ChatColor.LIGHT_PURPLE + "Prix: 20 000 coins");
            panel_im.setLore(lore);
            panel_is.setItemMeta(panel_im);
            this.shop.setItem(40, panel_is);
        }

        ItemStack sword_is = new ItemStack(Material.DIAMOND_SWORD);
        ItemMeta sword_im = sword_is.getItemMeta();
        sword_im.setDisplayName(ChatColor.MAGIC + "Coming soon ??");
        sword_is.setItemMeta(sword_im);
        this.shop.setItem(42, sword_is);

        guildSQL.closeConnection();
    }

    public Inventory getInventory()
    {
        return this.shop;
    }
}
