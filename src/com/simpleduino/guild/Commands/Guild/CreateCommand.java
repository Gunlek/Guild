package com.simpleduino.guild.Commands.Guild;

import com.simpleduino.guild.SQL.GuildSQL;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Simple-Duino on 28/06/2016.
 * Copyrights Simple-Duino, all rights reserved
 */

public class CreateCommand {

    private GuildSQL guildSQL = new GuildSQL();

    public CreateCommand(CommandSender sender, String[] args) {
        if(sender.hasPermission("Guild.create"))
        {
            if(sender instanceof Player)
            {
                Player p = (Player)sender;
                if(args.length >= 2)
                {
                    String guildName = args[1];
                    if(guildSQL.hasGuild(p)) {
                        if (guildName.length() > 4) {
                            if (guildName.length() < 20) {
                                if (guildSQL.createGuild(guildName, p))
                                    p.sendMessage(ChatColor.GREEN + "Vous avez créé la guilde " + guildName + " avec succès !");
                                else
                                    p.sendMessage(ChatColor.RED + "La guilde que vous souhaitez créer existe déjà");
                            } else {
                                p.sendMessage(ChatColor.RED + "Le nom de la guilde doit être inférieur à 20 caractères");
                            }
                        } else {
                            p.sendMessage(ChatColor.RED + "Le nom de la guilde doit être supérieur à 4 caractères");
                        }
                    }
                    else
                    {
                        p.sendMessage(ChatColor.RED + "Vous êtes déjà membre d'une guilde, quittez la avant d'en créer une autre");
                    }
                }
                else
                {
                    p.sendMessage(ChatColor.RED + "Syntaxe incorrecte: /guild create <guild_name>");
                }
            }
            else
            {
                sender.sendMessage(ChatColor.RED + "Vous devez être joueur pour executer cette commande");
            }
        }
        else
        {
            sender.sendMessage(ChatColor.RED + "Vous n'avez pas la permission de créer une guilde");
        }

        guildSQL.closeConnection();
    }
}
