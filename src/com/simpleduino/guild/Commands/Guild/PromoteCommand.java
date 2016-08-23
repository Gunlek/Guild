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

public class PromoteCommand {

    private GuildSQL guildSQL = new GuildSQL();

    public PromoteCommand(CommandSender sender, String[] args) {
        if(sender instanceof Player)
        {
            if(sender.hasPermission("Guild.promote"))
            {
                if(args.length >= 2) {
                    Player p = (Player) sender;
                    String promoted = args[1];
                    if (guildSQL.hasGuild(p)) {
                        try
                        {
                            if(guildSQL.hasGuild(promoted))
                            {
                                if(guildSQL.getGuild(promoted).equalsIgnoreCase(guildSQL.getGuild(p)))
                                {
                                    if(guildSQL.getRank(promoted)+1 < guildSQL.getRank(p))
                                    {
                                        try
                                        {
                                            Player promotePlayer = Bukkit.getPlayer(promoted);
                                            int currentRank = guildSQL.getRank(promotePlayer);
                                            currentRank++;
                                            guildSQL.setRank(promotePlayer, currentRank);
                                            promotePlayer.sendMessage(ChatColor.GREEN + "Vous avez été promu par "+p.getName()+" dans votre guilde");
                                            new CustomMessageSender("ALL", "GuildPromoteMember", new String[]{p.getName(), Integer.toString(guildSQL.getRank(p)), promoted, guildSQL.getGuild(p)});
                                        }
                                        catch(Exception e)
                                        {
                                            int currentRank = guildSQL.getRank(promoted);
                                            currentRank++;
                                            guildSQL.setRank(promoted, currentRank);
                                            new CustomMessageSender("ALL", "GuildPromoteMember", new String[]{p.getName(), Integer.toString(guildSQL.getRank(p)), promoted, guildSQL.getGuild(p)});
                                        }
                                    }
                                    else
                                    {
                                        p.sendMessage(ChatColor.RED + "Vous ne pouvez pas promouvoir un membre vers un rang supérieur ou égal au votre");
                                    }
                                }
                                else
                                {
                                    p.sendMessage(ChatColor.RED + "Ce joueur n'est pas de votre guilde");
                                }
                            }
                            else
                            {
                                p.sendMessage(ChatColor.RED + "Ce joueur n'a pas de guilde");
                            }
                        }
                        catch(Exception e)
                        {
                            new CustomMessageSender("ALL", "GuildPromoteMember", new String[]{p.getName(), Integer.toString(guildSQL.getRank(p)), promoted, guildSQL.getGuild(p)});
                        }

                        p.sendMessage(ChatColor.GREEN + "Vous avez promu "+promoted+" dans votre guilde");
                    }
                    else
                    {
                        p.sendMessage(ChatColor.RED + "Vous n'êtes pas membre d'une guilde");
                    }
                }
                else
                {
                    sender.sendMessage(ChatColor.RED + "Syntaxe incorrecte: /guild promote <player_name>");
                }
            }
            else
            {
                sender.sendMessage(ChatColor.RED + "Vous n'avez pas la permission de promouvoir des joueurs dans une guilde");
            }
        }
        else
        {
            sender.sendMessage(ChatColor.RED + "Vous devez être joueur pour executer cette commande");
        }
        guildSQL.closeConnection();
    }

}
