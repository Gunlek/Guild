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

public class KickCommand {

    private GuildSQL guildSQL = new GuildSQL();

    public KickCommand(CommandSender sender, String[] args) {
        if(sender instanceof Player)
        {
            if(sender.hasPermission("Guild.kick"))
            {
                if(args.length >= 2) {
                    String kickedPlayer = args[1];
                    Player p = (Player) sender;
                    if (guildSQL.hasGuild(p)) {
                        String guildName = guildSQL.getGuild(p);
                        if(guildSQL.hasGuild(kickedPlayer))
                        {
                            if(guildSQL.getGuild(kickedPlayer).equalsIgnoreCase(guildName))
                            {
                                if (guildSQL.getRank(p) > guildSQL.getRank(kickedPlayer)) {
                                    p.sendMessage(ChatColor.YELLOW + "Vous avez éjecté "+kickedPlayer+" de votre guilde");
                                    try {
                                        Player kicked = Bukkit.getPlayer(kickedPlayer);
                                        guildSQL.kickMember(kicked, guildName);
                                        kicked.sendMessage(ChatColor.YELLOW + "Vous avez été éjecté de votre guilde par " + p.getName());
                                    } catch (Exception e) {
                                        guildSQL.kickMember(kickedPlayer, guildName);
                                        new CustomMessageSender("ALL", "GuildMemberKicked", new String[]{p.getName(), kickedPlayer});
                                    }
                                }
                                else
                                {
                                    p.sendMessage(ChatColor.RED + "Vous ne pouvez pas exclure un membre d'un grade supérieur ou égal au votre");
                                }
                            }
                            else
                            {
                                p.sendMessage(ChatColor.RED + "Ce joueur n'est pas membre de votre guilde");
                            }
                        }
                        else
                        {
                            p.sendMessage(ChatColor.RED + "Ce joueur n'a pas de guilde");
                        }
                    }
                    else
                    {
                        p.sendMessage(ChatColor.RED + "Vous n'êtes pas membre d'une guilde");
                    }
                }
                else
                {
                    sender.sendMessage(ChatColor.RED + "Erreur: Syntaxe incorrecte, utilisez /guilde kick <player_name>");
                }
            }
            else
            {
                sender.sendMessage(ChatColor.RED + "Vous n'avez pas la permission d'exclure des membres de guilde");
            }
        }
        else
        {
            sender.sendMessage(ChatColor.RED + "Vous devez être joueur pour executer cette commande");
        }

        guildSQL.closeConnection();
    }
}
