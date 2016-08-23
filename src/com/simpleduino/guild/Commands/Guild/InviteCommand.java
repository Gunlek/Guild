package com.simpleduino.guild.Commands.Guild;

import com.simpleduino.economy.Messaging.CustomMessageSender;
import com.simpleduino.guild.GuildPlugin;
import com.simpleduino.guild.Runnable.InvitationExpire;
import com.simpleduino.guild.SQL.GuildSQL;
import mkremins.fanciful.FancyMessage;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;

/**
 * Created by Simple-Duino on 28/06/2016.
 * Copyrights Simple-Duino, all rights reserved
 */

public class InviteCommand {

    public static HashMap<String, String> GuildRequest = new HashMap<>();
    public static HashMap<String, String> pendingRequest = new HashMap<>();
    private GuildSQL guildSQL = new GuildSQL();

    public InviteCommand(CommandSender sender, String[] args)
    {
        if(sender.hasPermission("Guild.invite"))
        {
            if(args.length >= 2)
            {
                String newMember = args[1];
                if(sender instanceof Player)
                {
                    Player p = (Player)sender;
                    if(!InviteCommand.pendingRequest.containsKey(p.getName())) {
                        if (guildSQL.hasGuild(p)) {
                            if (guildSQL.getRank(p)==3) {
                                if(guildSQL.getGuildMembers(guildSQL.getGuild(p)).size() < Integer.parseInt(guildSQL.getGuildMaxMembers(guildSQL.getGuild(p)))) {
                                    InviteCommand.pendingRequest.put(p.getName(), newMember);
                                    InviteCommand.GuildRequest.put(newMember, guildSQL.getGuild(p));
                                    if(guildSQL.hasGuild(newMember))
                                    {
                                        if(guildSQL.getGuild(newMember).equalsIgnoreCase(guildSQL.getGuild(p)))
                                        {
                                            p.sendMessage(ChatColor.RED + newMember + " est déjà membre de votre guilde");
                                        }
                                        else
                                        {
                                            try {
                                                Player invited = Bukkit.getPlayer(newMember);
                                                new FancyMessage(p.getName() + " vous invite à rejoindre sa guilde (" + guildSQL.getGuild(p) + ")")
                                                        .color(ChatColor.BLUE)
                                                        .send(invited);
                                                new FancyMessage("Acceptez-vous ?")
                                                        .color(ChatColor.BLUE)
                                                        .send(invited);
                                                new FancyMessage("[OUI] ")
                                                        .color(ChatColor.GREEN)
                                                        .style(ChatColor.BOLD)
                                                        .command("/guild accept")
                                                        .tooltip(ChatColor.GREEN + "Accepter l'invitation")
                                                        .then("[NON]")
                                                        .color(ChatColor.DARK_RED)
                                                        .style(ChatColor.BOLD)
                                                        .command("/guild deny")
                                                        .tooltip(ChatColor.DARK_RED + "Décliner l'invitation")
                                                        .send(invited);
                                                invited.sendMessage(ChatColor.YELLOW + "L'invitation expire dans 15 secondes");
                                            } catch (Exception e) {
                                                new CustomMessageSender("ALL", "GuildInvitation", new String[]{p.getName(), newMember, guildSQL.getGuild(p)});
                                            }
                                            p.sendMessage(ChatColor.GREEN + "Vous avez invité " + newMember + " à rejoindre votre guilde");
                                            p.sendMessage(ChatColor.YELLOW + "L'invitation expire dans 15 secondes");
                                            Bukkit.getServer().getScheduler().runTaskLater(GuildPlugin.getPlugin(GuildPlugin.class), new InvitationExpire(p.getName()), 20L * 15);
                                        }
                                    }
                                    else
                                    {
                                        try {
                                            Player invited = Bukkit.getPlayer(newMember);
                                            new FancyMessage(p.getName() + " vous invite à rejoindre sa guilde (" + guildSQL.getGuild(p) + ")")
                                                    .color(ChatColor.BLUE)
                                                    .send(invited);
                                            new FancyMessage("Acceptez-vous ?")
                                                    .color(ChatColor.BLUE)
                                                    .send(invited);
                                            new FancyMessage("[OUI] ")
                                                    .color(ChatColor.GREEN)
                                                    .style(ChatColor.BOLD)
                                                    .command("/guild accept")
                                                    .tooltip(ChatColor.GREEN + "Accepter l'invitation")
                                                    .then("[NON]")
                                                    .color(ChatColor.DARK_RED)
                                                    .style(ChatColor.BOLD)
                                                    .command("/guild deny")
                                                    .tooltip(ChatColor.DARK_RED + "Décliner l'invitation")
                                                    .send(invited);
                                            invited.sendMessage(ChatColor.YELLOW + "L'invitation expire dans 15 secondes");
                                        } catch (Exception e) {
                                            new CustomMessageSender("ALL", "GuildInvitation", new String[]{p.getName(), newMember, guildSQL.getGuild(p)});
                                        }
                                        p.sendMessage(ChatColor.GREEN + "Vous avez invité " + newMember + " à rejoindre votre guilde");
                                        p.sendMessage(ChatColor.YELLOW + "L'invitation expire dans 15 secondes");
                                        Bukkit.getServer().getScheduler().runTaskLater(GuildPlugin.getPlugin(GuildPlugin.class), new InvitationExpire(p.getName()), 20L * 15);
                                    }
                                }
                                else
                                {
                                    p.sendMessage(ChatColor.RED + "Vous avez atteint le nombre maximum de membres dans votre guilde");
                                }
                            } else {
                                p.sendMessage(ChatColor.RED + "Vous devez être leader de la guilde pour inviter des joueurs");
                            }
                        } else {
                            p.sendMessage(ChatColor.RED + "Vous n'êtes pas membre d'une guilde");
                        }
                    }
                    else
                    {
                        p.sendMessage(ChatColor.RED + "Vous avez déjà une demande d'invitation en cours...");
                    }
                }
                else
                {
                    sender.sendMessage(ChatColor.RED + "Vous devez être joueur pour executer cette commande");
                }
            }
            else
            {
                sender.sendMessage(ChatColor.RED + "Syntaxe incorrecte: /guild invite <player_name>");
            }
        }
        else
        {
            sender.sendMessage(ChatColor.RED + "Vous n'avez pas la permission d'inviter des joueurs dans une guilde");
        }

        guildSQL.closeConnection();
    }

}
