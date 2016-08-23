package com.simpleduino.guild.Commands.Guild;

import com.simpleduino.guild.SQL.GuildSQL;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Simple-Duino on 02/07/2016.
 * Copyrights Simple-Duino, all rights reserved
 */

public class TagCommand {

    private GuildSQL guildSQL = new GuildSQL();
    public TagCommand(CommandSender sender, String[] args)
    {
        if(sender instanceof Player)
        {
            if(sender.hasPermission("Guild.tag"))
            {
                if(args.length >= 2) {
                    String tag = args[1];
                    Player p = (Player) sender;
                    if (guildSQL.hasGuild(p)) {
                        String guildName = guildSQL.getGuild(p);
                        if(Integer.parseInt(guildSQL.getGuildCoins(guildName))>=2000) {
                            if (guildSQL.getRank(p) >= 3) {
                                guildSQL.setTag(tag, guildName);
                                p.sendMessage(ChatColor.GREEN + "Vous avez modifié le tag de votre guilde");
                            } else {
                                p.sendMessage(ChatColor.RED + "Vous n'avez pas la permission d'executer cette commande au sein de votre guilde");
                            }
                        }
                        else
                        {
                            p.sendMessage(ChatColor.RED + "Votre guilde n'a pas assez d'argent pour acquérir cette capacité");
                        }
                    }
                    else
                    {
                        p.sendMessage(ChatColor.RED + "Vous devez être membre d'une guilde pour executer cette commande");
                    }
                }
                else
                {
                    sender.sendMessage(ChatColor.RED + "Erreur: Syntaxe incorrecte, utilisez /guilde tag <new_tag>");
                }
            }
            else
            {
                sender.sendMessage(ChatColor.RED + "Vous n'avez pas la permission de modifier le tag d'une guilde");
            }
        }
        else
        {
            sender.sendMessage(ChatColor.RED + "Vous devez être joueur pour executer cette commande");
        }

        guildSQL.closeConnection();
    }
}
