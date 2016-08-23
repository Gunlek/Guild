package com.simpleduino.guild.Commands.Guild;

import com.simpleduino.guild.SQL.GuildSQL;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Simple-Duino on 02/07/2016.
 * Copyrights Simple-Duino, all rights reserved
 */

public class MotdCommand {

    private GuildSQL guildSQL = new GuildSQL();

    public MotdCommand(CommandSender sender, String[] args)
    {
        if(sender instanceof Player)
        {
            if(sender.hasPermission("Guild.motd"))
            {
                if(args.length >= 2) {
                    String motd = args[1];
                    Player p = (Player) sender;
                    if (guildSQL.hasGuild(p)) {
                        String guildName = guildSQL.getGuild(p);
                        if (guildSQL.getRank(p) >= 3) {
                            String finalmotd = "";
                            for(int i=1; i<args.length;i++)
                            {
                                if(i==1)
                                    finalmotd+=args[i];
                                else
                                    finalmotd+=" "+args[i];
                            }
                            guildSQL.setMotd(finalmotd, guildName);
                            p.sendMessage(ChatColor.GREEN + "Vous avez modifié le motd de votre guilde");
                        }
                        else
                        {
                            p.sendMessage(ChatColor.RED + "Vous n'avez pas la permission d'executer cette commande au sein de votre guilde");
                        }
                    }
                    else
                    {
                        p.sendMessage(ChatColor.RED + "Vous devez être membre d'une guilde pour executer cette commande");
                    }
                }
                else
                {
                    sender.sendMessage(ChatColor.RED + "Erreur: Syntaxe incorrecte, utilisez /guilde motd <new_motd>");
                }
            }
            else
            {
                sender.sendMessage(ChatColor.RED + "Vous n'avez pas la permission de modifier le motd d'une guilde");
            }
        }
        else
        {
            sender.sendMessage(ChatColor.RED + "Vous devez être joueur pour executer cette commande");
        }

        guildSQL.closeConnection();
    }
}
