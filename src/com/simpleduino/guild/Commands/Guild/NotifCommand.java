package com.simpleduino.guild.Commands.Guild;

import com.simpleduino.guild.SQL.GuildSQL;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Simple-Duino on 28/06/2016.
 * Copyrights Simple-Duino, all rights reserved
 */

public class NotifCommand {

    private GuildSQL guildSQL = new GuildSQL();

    public NotifCommand(CommandSender sender, String[] args) {
        if(sender instanceof Player)
        {
            if(sender.hasPermission("Guild.notif"))
            {
                Player p = (Player)sender;
                if(guildSQL.hasGuild(p))
                {
                    if(guildSQL.getNotif(p)) {
                        guildSQL.setNotif(p, false);
                        p.sendMessage(ChatColor.YELLOW + "Vous avez désactivé les notifications de guilde");
                    }
                    else
                    {
                        guildSQL.setNotif(p, true);
                        p.sendMessage(ChatColor.YELLOW + "Vous avez activé les notifications de guilde");
                    }
                }
                else
                {
                    p.sendMessage(ChatColor.RED + "Vous n'êtes pas membre d'une guilde");
                }
            }
            else
            {
                sender.sendMessage(ChatColor.RED + "Vous n'avez pas la permission d'activer ou désactiver les notifications de guilde");
            }
        }
        else
        {
            sender.sendMessage(ChatColor.RED + "Vous devez être joueur pour executer cette commande");
        }
        guildSQL.closeConnection();
    }

}
