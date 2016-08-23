package com.simpleduino.guild.Commands.Guild;

import com.simpleduino.economy.Messaging.CustomMessageSender;
import com.simpleduino.guild.SQL.GuildSQL;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Simple-Duino on 28/06/2016.
 * Copyrights Simple-Duino, all rights reserved
 */

public class LeaveCommand {

    private GuildSQL guildSQL = new GuildSQL();

    public LeaveCommand(CommandSender sender, String[] args) {
        if(sender instanceof Player)
        {
            if(sender.hasPermission("Guild.leave"))
            {
                Player p = (Player)sender;
                if(guildSQL.hasGuild(p))
                {
                    String guildName = guildSQL.getGuild(p);
                    for(String pName : guildSQL.getGuildMembers(guildName))
                    {
                        try
                        {
                            Bukkit.getPlayer(pName).sendMessage(ChatColor.GREEN + p.getName()+" a quitté votre guilde");
                        }
                        catch(Exception e)
                        {

                        }
                    }
                    new CustomMessageSender("ALL", "PlayerLeaveGuild", new String[]{p.getName(), guildName});
                    guildSQL.leaveGuild(p);
                    p.sendMessage(ChatColor.GREEN + "Vous avez quitté la guilde "+ ChatColor.GRAY + "["+guildName+"]");
                }
                else
                {
                    p.sendMessage(ChatColor.RED + "Vous n'êtes pas membre d'une guilde");
                }
            }
            else
            {
                sender.sendMessage(ChatColor.RED + "Vous n'avez pas la permission de quitter une guilde");
            }
        }
        else
        {
            sender.sendMessage(ChatColor.RED + "Vous devez être joueur pour executer cette commande");
        }

        guildSQL.closeConnection();
    }

}
