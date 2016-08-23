package com.simpleduino.guild.Commands.Guild;

import com.simpleduino.economy.Messaging.CustomMessageSender;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Simple-Duino on 28/06/2016.
 * Copyrights Simple-Duino, all rights reserved
 */

public class DenyCommand {

    public DenyCommand(CommandSender sender, String[] args) {
        if(sender instanceof Player)
        {
            if(sender.hasPermission("Guild.deny"))
            {
                Player p = (Player)sender;
                if(InviteCommand.GuildRequest.containsKey(p.getName()))
                {
                    String inviter = "";
                    for(String key : InviteCommand.pendingRequest.keySet())
                    {
                        if(InviteCommand.pendingRequest.get(key).equalsIgnoreCase(p.getName()))
                        {
                            inviter = key;
                            break;
                        }
                    }

                    p.sendMessage(ChatColor.GREEN + "Vous avez décliné l'invitation de "+inviter+" à rejoindre sa guilde, "+ChatColor.GRAY+"["+InviteCommand.GuildRequest.get(p.getName())+"]");
                    try
                    {
                        Bukkit.getPlayer(inviter).sendMessage(ChatColor.GREEN + p.getName() + " a decliné l'invitation à rejoindre votre guilde");
                    }
                    catch(Exception e)
                    {
                        new CustomMessageSender("ALL", "GuildDenyInvitation", new String[]{inviter, p.getName()});
                    }

                    InviteCommand.pendingRequest.remove(inviter);
                    InviteCommand.GuildRequest.remove(p.getName());
                }
            }
        }
    }

}
