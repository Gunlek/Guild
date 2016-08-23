package com.simpleduino.guild.Commands.Guild;

import com.simpleduino.economy.Messaging.CustomMessageSender;
import com.simpleduino.guild.API.Guild;
import com.simpleduino.guild.SQL.GuildSQL;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Simple-Duino on 28/06/2016.
 * Copyrights Simple-Duino, all rights reserved
 */

public class DemoteCommand {

    private GuildSQL guildSQL = new GuildSQL();

    public DemoteCommand(CommandSender sender, String[] args) {
        if(sender instanceof Player)
        {
            if(sender.hasPermission("Guild.demote"))
            {
                if(args.length >= 2) {
                    Player p = (Player) sender;
                    String demoted = args[1];
                    if (guildSQL.hasGuild(p)) {
                        try
                        {
                            if(guildSQL.hasGuild(demoted))
                            {
                                if(guildSQL.getGuild(demoted).equalsIgnoreCase(guildSQL.getGuild(p)))
                                {
                                    if(guildSQL.getRank(demoted) < guildSQL.getRank(p))
                                    {
                                        if(guildSQL.getRank(demoted)>0) {
                                            try {
                                                Player demotePlayer = Bukkit.getPlayer(demoted);
                                                int currentRank = guildSQL.getRank(demotePlayer);
                                                currentRank--;
                                                guildSQL.setRank(demotePlayer, currentRank);
                                                demotePlayer.sendMessage(ChatColor.GREEN + "Vous avez été rétrogradé par " + p.getName() + " dans votre guilde");
                                                new CustomMessageSender("ALL", "GuildDemoteMember", new String[]{p.getName(), Integer.toString(guildSQL.getRank(p)), demoted, guildSQL.getGuild(p)});
                                            }
                                            catch(Exception e)
                                            {
                                                int currentRank = guildSQL.getRank(demoted);
                                                currentRank--;
                                                guildSQL.setRank(demoted, currentRank);
                                                new CustomMessageSender("ALL", "GuildDemoteMember", new String[]{p.getName(), Integer.toString(guildSQL.getRank(p)), demoted, guildSQL.getGuild(p)});
                                            }
                                        }
                                        else
                                        {
                                            p.sendMessage(ChatColor.RED + "Le membre est déjà au rang le plus bas");
                                        }
                                    }
                                    else
                                    {
                                        p.sendMessage(ChatColor.RED + "Vous n'avez pas l'autorisation de rétrograder un membre de votre guilde");
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
                            new CustomMessageSender("ALL", "GuildDemoteMember", new String[]{p.getName(), Integer.toString(guildSQL.getRank(p)), demoted, guildSQL.getGuild(p)});
                        }

                        p.sendMessage(ChatColor.GREEN + "Vous avez rétrogradé "+demoted+" dans votre guilde");
                    }
                    else
                    {
                        p.sendMessage(ChatColor.RED + "Vous n'êtes pas membre d'une guilde");
                    }
                }
                else
                {
                    sender.sendMessage(ChatColor.RED + "Syntaxe incorrecte: /guild demote <player_name>");
                }
            }
            else
            {
                sender.sendMessage(ChatColor.RED + "Vous n'avez pas la permission de rétrograder des joueurs dans une guilde");
            }
        }
        else
        {
            sender.sendMessage(ChatColor.RED + "Vous devez être joueur pour executer cette commande");
        }

        guildSQL.closeConnection();
    }

}
