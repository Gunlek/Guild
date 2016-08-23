package com.simpleduino.guild.Commands.Guild;

import com.simpleduino.guild.Inventories.GuildShopInventory;
import com.simpleduino.guild.SQL.GuildSQL;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Simple-Duino on 02/07/2016.
 * Copyrights Simple-Duino, all rights reserved
 */

public class ShopCommand {

    private GuildSQL guildSQL = new GuildSQL();

    public ShopCommand(CommandSender sender, String[] args)
    {
        if(sender instanceof Player)
        {
            if(sender.hasPermission("Guild.shop"))
            {
                Player p = (Player)sender;
                if(guildSQL.hasGuild(p))
                {
                    int rank = guildSQL.getRank(p);
                    if(rank >= 2)
                        p.openInventory(new GuildShopInventory(p).getInventory());
                }
            }
        }
        guildSQL.closeConnection();
    }
}
