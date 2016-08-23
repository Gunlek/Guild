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

public class AcceptCommand {

    private GuildSQL guildSQL = new GuildSQL();

    public AcceptCommand(CommandSender sender, String[] args) {
        if(sender.hasPermission("Guild.accept"))
        {
            if(sender instanceof Player)
            {
                Player p = (Player)sender;
                if(InviteCommand.GuildRequest.containsKey(p.getName()))
                {
                    if(!guildSQL.hasGuild(p)) {
                        String inviter = "";
                        for (String key : InviteCommand.pendingRequest.keySet()) {
                            if (InviteCommand.pendingRequest.get(key).equalsIgnoreCase(p.getName())) {
                                inviter = key;
                                break;
                            }
                        }

                        try {
                            Player inviterPlayer = Bukkit.getPlayer(inviter);
                            inviterPlayer.sendMessage(ChatColor.GREEN + p.getName() + " a accepté votre invitation à rejoindre votre guilde");
                        } catch (Exception e) {
                            new CustomMessageSender("ALL", "GuildAcceptInvitation", new String[]{inviter, p.getName(), InviteCommand.GuildRequest.get(p.getName())});
                        }
                        p.sendMessage(ChatColor.GREEN + "Vous faites maintenant parti de la guilde " + ChatColor.DARK_GRAY + "[" + InviteCommand.GuildRequest.get(p.getName()) + "]");
                        if (!guildSQL.joinGuild(p, InviteCommand.GuildRequest.get(p.getName()))) {
                            guildSQL.registerMember(p);
                            guildSQL.joinGuild(p, InviteCommand.GuildRequest.get(p.getName()));
                        }
                        InviteCommand.GuildRequest.remove(p.getName());
                        InviteCommand.pendingRequest.remove(inviter);
                    }
                    else
                    {
                        p.sendMessage(ChatColor.RED + "Vous êtes déjà membre d'une guilde, quittez la avant d'en rejoindre une autre");
                    }
                }
                else
                {
                    p.sendMessage(ChatColor.RED + "Vous n'avez pas d'invitation de guilde en cours");
                }
            }
            else
            {
                sender.sendMessage(ChatColor.RED + "Cette commande doit être executée par un joueur");
            }
        }
        else
        {
            sender.sendMessage(ChatColor.RED + "Vous n'avez pas la permission d'accepter une invitation à une guilde");
        }

        guildSQL.closeConnection();
    }

}
