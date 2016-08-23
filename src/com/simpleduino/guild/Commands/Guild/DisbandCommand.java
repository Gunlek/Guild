package com.simpleduino.guild.Commands.Guild;

import com.simpleduino.economy.Messaging.CustomMessageSender;
import com.simpleduino.guild.SQL.GuildSQL;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

/**
 * Created by Simple-Duino on 28/06/2016.
 * Copyrights Simple-Duino, all rights reserved
 */

public class DisbandCommand {

    private GuildSQL guildSQL = new GuildSQL();

    public DisbandCommand(CommandSender sender, String[] args) {
        if(sender instanceof Player)
        {
            if(sender.hasPermission("Guild.disband"))
            {
                Player p = (Player)sender;
                String guildName = guildSQL.getGuild(p);
                if(guildSQL.hasGuild(p))
                {
                    if(guildSQL.getRank(p)>=3)
                    {
                        ArrayList<String> members = guildSQL.getGuildMembers(guildName);
                        String str = "";
                        int index = 0;
                        for(String m : members)
                        {
                            str+=m;
                            if(index<members.size()-1)
                            {
                                str+=", ";
                            }
                            index++;
                        }
                        for(String m : members)
                        {
                            try
                            {
                                Player member = Bukkit.getPlayer(m);
                                if(!member.getName().equalsIgnoreCase(p.getName()))
                                    member.sendMessage(ChatColor.YELLOW + p.getName() + " a dissous votre guilde");
                                else
                                    member.sendMessage(ChatColor.YELLOW + "Vous avez dissous votre guilde, "+ChatColor.GRAY+"["+guildName+"]");
                            }
                            catch(Exception e)
                            {
                                //Player isn't on this server
                            }
                        }
                        new CustomMessageSender("ALL", "PlayerDisbandGuild", new String[]{p.getName(), str});
                        guildSQL.disbandGuild(guildName);
                    }
                    else
                    {
                        p.sendMessage(ChatColor.RED + "Vous devez être leader de la guilde que vous souhaitez dissoudre");
                    }
                }
                else
                {
                    p.sendMessage(ChatColor.RED + "Vous n'êtes pas membre d'une guilde");
                }
            }
            else
            {
                sender.sendMessage(ChatColor.RED + "Vous n'avez pas la permission de dissoudre une guilde");
            }
        }
        else
        {
            sender.sendMessage(ChatColor.RED + "Vous devez être joueur pour executer cette commande");
        }

        guildSQL.closeConnection();
    }

}
