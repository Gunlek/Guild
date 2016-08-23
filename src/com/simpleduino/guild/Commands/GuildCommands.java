package com.simpleduino.guild.Commands;

import com.simpleduino.guild.Commands.Guild.*;
import com.simpleduino.guild.SQL.GuildSQL;
import mkremins.fanciful.FancyMessage;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by Simple-Duino on 28/06/2016.
 * Copyrights Simple-Duino, all rights reserved
 */

public class GuildCommands implements CommandExecutor {

    File cfgFile = new File("plugins/Guild/config.yml");
    YamlConfiguration cfg = YamlConfiguration.loadConfiguration(cfgFile);
    private GuildSQL guildSQL = new GuildSQL();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if(sender instanceof Player) {
            if (!guildSQL.isRegistered((Player) sender))
                guildSQL.registerMember((Player) sender);
        }
        if (args.length >= 1) {
            switch (args[0].toLowerCase()) {
                case "create":
                    new CreateCommand(sender, args);
                    break;
                case "accept":
                    new AcceptCommand(sender, args);
                    break;
                case "demote":
                    new DemoteCommand(sender, args);
                    break;
                case "deny":
                    new DenyCommand(sender, args);
                    break;
                case "disband":
                    new DisbandCommand(sender, args);
                    break;
                case "invite":
                    new InviteCommand(sender, args);
                    break;
                case "kick":
                    new KickCommand(sender, args);
                    break;
                case "leave":
                    new LeaveCommand(sender, args);
                    break;
                case "members":
                    new MembersCommand(sender, args);
                    break;
                case "notif":
                    new NotifCommand(sender, args);
                    break;
                case "promote":
                    new PromoteCommand(sender, args);
                    break;
                case "shop":
                    new ShopCommand(sender, args);
                    break;
                case "tag":
                    new TagCommand(sender, args);
                    break;
                case "motd":
                    new MotdCommand(sender, args);
                    break;
                default:
                    if(sender instanceof Player)
                    {
                        Player p = (Player)sender;
                        if(guildSQL.hasGuild(p))
                        {
                            String guildName = guildSQL.getGuild(p);
                            ArrayList<String> members = guildSQL.getGuildMembers(guildName);
                            int rank = guildSQL.getRank(p);
                            String rankName = cfg.get("ranks."+Integer.toString(rank)).toString();
                            rankName = rankName.replace("&", "§");
                            p.sendMessage(ChatColor.YELLOW + "Vous êtes "+rankName+ChatColor.YELLOW+" de la guilde "+ChatColor.DARK_GRAY+"["+guildName+"]");
                            p.sendMessage(ChatColor.YELLOW + "Votre guilde compte "+ChatColor.RED+Integer.toString(members.size())+ChatColor.YELLOW+" membre(s)");
                            p.sendMessage(ChatColor.YELLOW + "Voici quelques commandes qui pourraient être utiles");
                            if(guildSQL.getNotif(p))
                            {
                                if(guildSQL.getRank(p)==3)
                                {
                                    new FancyMessage("- [Invite]")
                                            .color(ChatColor.BLUE)
                                            .tooltip(ChatColor.BLUE + "Inviter un joueur dans la guilde")
                                            .suggest("/guild invite ")
                                            .send(p);
                                    new FancyMessage("- [Kick]")
                                            .tooltip(ChatColor.RED + "Exclure un membre")
                                            .color(ChatColor.RED)
                                            .suggest("/guild kick ")
                                            .send(p);
                                    new FancyMessage("- [Promote]")
                                            .color(ChatColor.DARK_GREEN)
                                            .tooltip(ChatColor.DARK_GREEN + "Promouvoir un membre")
                                            .suggest("/guild promote ")
                                            .send(p);
                                    new FancyMessage("- [Demote]")
                                            .color(ChatColor.DARK_BLUE)
                                            .tooltip(ChatColor.DARK_BLUE + "Rétrograder un membre")
                                            .suggest("/guild demote ")
                                            .send(p);
                                    new FancyMessage("- [Members]")
                                            .color(ChatColor.GOLD)
                                            .tooltip(ChatColor.GOLD + "Voir la liste des membres")
                                            .command("/guild members")
                                            .send(p);
                                    new FancyMessage("- [Notifications]")
                                            .color(ChatColor.GREEN)
                                            .tooltip(ChatColor.DARK_RED + "Désactiver les notifications de guilde")
                                            .command("/guild notif")
                                            .send(p);
                                    new FancyMessage("- [MOTD]")
                                            .color(ChatColor.YELLOW)
                                            .tooltip(ChatColor.YELLOW + "Modifier le MOTD de votre guilde")
                                            .suggest("/guild motd ")
                                            .send(p);
                                    new FancyMessage("- [Tag]")
                                            .color(ChatColor.DARK_GRAY)
                                            .tooltip(ChatColor.DARK_GRAY + "Modifier le Tag de votre guilde")
                                            .suggest("/guild tag ")
                                            .send(p);
                                    new FancyMessage("- [Shop]")
                                            .color(ChatColor.DARK_PURPLE)
                                            .tooltip(ChatColor.DARK_PURPLE + "Accéder au shop de la guilde")
                                            .command("/guild shop")
                                            .send(p);
                                    new FancyMessage("- [Disband]")
                                            .color(ChatColor.AQUA)
                                            .tooltip(ChatColor.AQUA + "Dissoudre votre guilde")
                                            .command("/guild disband")
                                            .send(p);
                                }
                                else if(guildSQL.getRank(p)==2)
                                {
                                    new FancyMessage("- [Invite]")
                                            .color(ChatColor.BLUE)
                                            .tooltip(ChatColor.BLUE + "Inviter un joueur dans la guilde")
                                            .suggest("/guild invite ")
                                            .send(p);
                                    new FancyMessage("- [Kick]")
                                            .tooltip(ChatColor.RED + "Exclure un membre")
                                            .color(ChatColor.RED)
                                            .suggest("/guild kick ")
                                            .send(p);
                                    new FancyMessage("- [Promote]")
                                            .color(ChatColor.DARK_GREEN)
                                            .tooltip(ChatColor.DARK_GREEN + "Promouvoir un membre")
                                            .suggest("/guild promote ")
                                            .send(p);
                                    new FancyMessage("- [Demote]")
                                            .color(ChatColor.DARK_BLUE)
                                            .tooltip(ChatColor.DARK_BLUE + "Rétrograder un membre")
                                            .suggest("/guild demote ")
                                            .send(p);
                                    new FancyMessage("- [Members]")
                                            .color(ChatColor.GOLD)
                                            .tooltip(ChatColor.GOLD + "Voir la liste des membres")
                                            .command("/guild members")
                                            .send(p);
                                    new FancyMessage("- [Notifications]")
                                            .color(ChatColor.GREEN)
                                            .tooltip(ChatColor.DARK_RED + "Désactiver les notifications de guilde")
                                            .command("/guild notif")
                                            .send(p);
                                    new FancyMessage("- [MOTD]")
                                            .color(ChatColor.YELLOW)
                                            .tooltip(ChatColor.YELLOW + "Modifier le MOTD de votre guilde")
                                            .suggest("/guild motd ")
                                            .send(p);
                                    new FancyMessage("- [Tag]")
                                            .color(ChatColor.DARK_GRAY)
                                            .tooltip(ChatColor.DARK_GRAY + "Modifier le Tag de votre guilde")
                                            .suggest("/guild tag ")
                                            .send(p);
                                    new FancyMessage("- [Shop]")
                                            .color(ChatColor.DARK_PURPLE)
                                            .tooltip(ChatColor.DARK_PURPLE + "Accéder au shop de la guilde")
                                            .command("/guild shop")
                                            .send(p);
                                }
                                else if(guildSQL.getRank(p)==1)
                                {
                                    new FancyMessage("- [Invite]")
                                            .color(ChatColor.BLUE)
                                            .tooltip(ChatColor.BLUE + "Inviter un joueur dans la guilde")
                                            .suggest("/guild invite ")
                                            .send(p);
                                    new FancyMessage("- [Kick]")
                                            .tooltip(ChatColor.RED + "Exclure un membre")
                                            .color(ChatColor.RED)
                                            .suggest("/guild kick ")
                                            .send(p);
                                    new FancyMessage("- [Members]")
                                            .color(ChatColor.GOLD)
                                            .tooltip(ChatColor.GOLD + "Voir la liste des membres")
                                            .command("/guild members")
                                            .send(p);
                                    new FancyMessage("- [Notifications]")
                                            .color(ChatColor.GREEN)
                                            .tooltip(ChatColor.DARK_RED + "Désactiver les notifications de guilde")
                                            .command("/guild notif")
                                            .send(p);
                                }
                                else {
                                    new FancyMessage("[Members]")
                                            .color(ChatColor.YELLOW)
                                            .tooltip(ChatColor.GREEN + "Voir la liste des membres")
                                            .command("/guild members")
                                            .then(" [Notifications]")
                                            .color(ChatColor.GREEN)
                                            .tooltip(ChatColor.RED + "Désactiver les notifications de guilde")
                                            .command("/guild notif")
                                            .then(" [Quitter]")
                                            .color(ChatColor.RED)
                                            .tooltip(ChatColor.RED + "Quitter la guilde")
                                            .command("/guild leave")
                                            .send(p);
                                }
                            }
                            else {
                                if(guildSQL.getRank(p)==3)
                                {
                                    new FancyMessage("- [Invite]")
                                            .color(ChatColor.BLUE)
                                            .tooltip(ChatColor.BLUE + "Inviter un joueur dans la guilde")
                                            .suggest("/guild invite ")
                                            .send(p);
                                    new FancyMessage("- [Kick]")
                                            .tooltip(ChatColor.RED + "Exclure un membre")
                                            .color(ChatColor.RED)
                                            .suggest("/guild kick ")
                                            .send(p);
                                    new FancyMessage("- [Promote]")
                                            .color(ChatColor.DARK_GREEN)
                                            .tooltip(ChatColor.DARK_GREEN + "Promouvoir un membre")
                                            .suggest("/guild promote ")
                                            .send(p);
                                    new FancyMessage("- [Demote]")
                                            .color(ChatColor.DARK_BLUE)
                                            .tooltip(ChatColor.DARK_BLUE + "Rétrograder un membre")
                                            .suggest("/guild demote ")
                                            .send(p);
                                    new FancyMessage("- [Members]")
                                            .color(ChatColor.GOLD)
                                            .tooltip(ChatColor.GOLD + "Voir la liste des membres")
                                            .command("/guild members")
                                            .send(p);
                                    new FancyMessage("- [Notifications]")
                                            .color(ChatColor.DARK_RED)
                                            .tooltip(ChatColor.GREEN + "Activer les notifications de guilde")
                                            .command("/guild notif")
                                            .send(p);
                                    new FancyMessage("- [MOTD]")
                                            .color(ChatColor.YELLOW)
                                            .tooltip(ChatColor.YELLOW + "Modifier le MOTD de votre guilde")
                                            .suggest("/guild motd ")
                                            .send(p);
                                    new FancyMessage("- [Tag]")
                                            .color(ChatColor.DARK_GRAY)
                                            .tooltip(ChatColor.DARK_GRAY + "Modifier le Tag de votre guilde")
                                            .suggest("/guild tag ")
                                            .send(p);
                                    new FancyMessage("- [Shop]")
                                            .color(ChatColor.DARK_PURPLE)
                                            .tooltip(ChatColor.DARK_PURPLE + "Accéder au shop de la guilde")
                                            .command("/guild shop")
                                            .send(p);
                                    new FancyMessage("- [Disband]")
                                            .color(ChatColor.AQUA)
                                            .tooltip(ChatColor.AQUA + "Dissoudre votre guilde")
                                            .command("/guild disband")
                                            .send(p);
                                }
                                else if(guildSQL.getRank(p)==2)
                                {
                                    new FancyMessage("- [Invite]")
                                            .color(ChatColor.BLUE)
                                            .tooltip(ChatColor.BLUE + "Inviter un joueur dans la guilde")
                                            .suggest("/guild invite ")
                                            .send(p);
                                    new FancyMessage("- [Kick]")
                                            .tooltip(ChatColor.RED + "Exclure un membre")
                                            .color(ChatColor.RED)
                                            .suggest("/guild kick ")
                                            .send(p);
                                    new FancyMessage("- [Promote]")
                                            .color(ChatColor.DARK_GREEN)
                                            .tooltip(ChatColor.DARK_GREEN + "Promouvoir un membre")
                                            .suggest("/guild promote ")
                                            .send(p);
                                    new FancyMessage("- [Demote]")
                                            .color(ChatColor.DARK_BLUE)
                                            .tooltip(ChatColor.DARK_BLUE + "Rétrograder un membre")
                                            .suggest("/guild demote ")
                                            .send(p);
                                    new FancyMessage("- [Members]")
                                            .color(ChatColor.GOLD)
                                            .tooltip(ChatColor.GOLD + "Voir la liste des membres")
                                            .command("/guild members")
                                            .send(p);
                                    new FancyMessage("- [Notifications]")
                                            .color(ChatColor.DARK_RED)
                                            .tooltip(ChatColor.GREEN + "Activer les notifications de guilde")
                                            .command("/guild notif")
                                            .send(p);
                                    new FancyMessage("- [MOTD]")
                                            .color(ChatColor.YELLOW)
                                            .tooltip(ChatColor.YELLOW + "Modifier le MOTD de votre guilde")
                                            .suggest("/guild motd ")
                                            .send(p);
                                    new FancyMessage("- [Tag]")
                                            .color(ChatColor.DARK_GRAY)
                                            .tooltip(ChatColor.DARK_GRAY + "Modifier le Tag de votre guilde")
                                            .suggest("/guild tag ")
                                            .send(p);
                                    new FancyMessage("- [Shop]")
                                            .color(ChatColor.DARK_PURPLE)
                                            .tooltip(ChatColor.DARK_PURPLE + "Accéder au shop de la guilde")
                                            .command("/guild shop")
                                            .send(p);
                                }
                                else if(guildSQL.getRank(p)==1)
                                {
                                    new FancyMessage("- [Invite]")
                                            .color(ChatColor.BLUE)
                                            .tooltip(ChatColor.BLUE + "Inviter un joueur dans la guilde")
                                            .suggest("/guild invite ")
                                            .send(p);
                                    new FancyMessage("- [Kick]")
                                            .tooltip(ChatColor.RED + "Exclure un membre")
                                            .color(ChatColor.RED)
                                            .suggest("/guild kick ")
                                            .send(p);
                                    new FancyMessage("- [Members]")
                                            .color(ChatColor.GOLD)
                                            .tooltip(ChatColor.GOLD + "Voir la liste des membres")
                                            .command("/guild members")
                                            .send(p);
                                    new FancyMessage("- [Notifications]")
                                            .color(ChatColor.DARK_RED)
                                            .tooltip(ChatColor.GREEN + "Activer les notifications de guilde")
                                            .command("/guild notif")
                                            .send(p);
                                }
                                else {
                                    new FancyMessage("[Members]")
                                            .color(ChatColor.YELLOW)
                                            .tooltip(ChatColor.GREEN + "Voir la liste des membres")
                                            .command("/guild members")
                                            .then(" [Notifications]")
                                            .color(ChatColor.DARK_RED)
                                            .tooltip(ChatColor.GREEN + "Activer les notifications de guilde")
                                            .command("/guild notif")
                                            .then(" [Quitter]")
                                            .color(ChatColor.RED)
                                            .tooltip(ChatColor.RED + "Quitter la guilde")
                                            .command("/guild leave")
                                            .send(p);
                                }
                            }
                        }
                        else
                        {
                            p.sendMessage(ChatColor.RED + "Vous n'êtes membre d'aucune guilde.");
                            new FancyMessage("Vous pouvez créer votre propre guilde en cliquant")
                                    .color(ChatColor.YELLOW)
                                    .then(" [ICI]")
                                    .color(ChatColor.GREEN)
                                    .style(ChatColor.BOLD)
                                    .suggest("/guild create ")
                                    .tooltip(ChatColor.GREEN + "Créer ma guilde")
                                    .send(p);
                            p.sendMessage(ChatColor.YELLOW + "Ou alors, simplement vous faire inviter dans la guilde d'un joueur");
                        }
                    }
                    break;
            }
        } else {
            if(sender instanceof Player)
            {
                Player p = (Player)sender;
                if(guildSQL.hasGuild(p))
                {
                    String guildName = guildSQL.getGuild(p);
                    ArrayList<String> members = guildSQL.getGuildMembers(guildName);
                    int rank = guildSQL.getRank(p);
                    String rankName = cfg.get("ranks."+Integer.toString(rank)).toString();
                    rankName = rankName.replace("&", "§");
                    p.sendMessage(ChatColor.YELLOW + "Vous êtes "+rankName+ChatColor.YELLOW+" de la guilde "+ChatColor.DARK_GRAY+"["+guildName+"]");
                    p.sendMessage(ChatColor.YELLOW + "Votre guilde compte "+ChatColor.RED+Integer.toString(members.size())+ChatColor.YELLOW+" membre(s)");
                    p.sendMessage(ChatColor.YELLOW + "Voici quelques commandes qui pourraient être utiles");
                    if(guildSQL.getNotif(p))
                    {
                        if(guildSQL.getRank(p)==3)
                        {
                            new FancyMessage("- [Invite]")
                                    .color(ChatColor.BLUE)
                                    .tooltip(ChatColor.BLUE + "Inviter un joueur dans la guilde")
                                    .suggest("/guild invite ")
                                    .send(p);
                            new FancyMessage("- [Kick]")
                                    .tooltip(ChatColor.RED + "Exclure un membre")
                                    .color(ChatColor.RED)
                                    .suggest("/guild kick ")
                                    .send(p);
                            new FancyMessage("- [Promote]")
                                    .color(ChatColor.DARK_GREEN)
                                    .tooltip(ChatColor.DARK_GREEN + "Promouvoir un membre")
                                    .suggest("/guild promote ")
                                    .send(p);
                            new FancyMessage("- [Demote]")
                                    .color(ChatColor.DARK_BLUE)
                                    .tooltip(ChatColor.DARK_BLUE + "Rétrograder un membre")
                                    .suggest("/guild demote ")
                                    .send(p);
                            new FancyMessage("- [Members]")
                                    .color(ChatColor.GOLD)
                                    .tooltip(ChatColor.GOLD + "Voir la liste des membres")
                                    .command("/guild members")
                                    .send(p);
                            new FancyMessage("- [Notifications]")
                                    .color(ChatColor.GREEN)
                                    .tooltip(ChatColor.DARK_RED + "Désactiver les notifications de guilde")
                                    .command("/guild notif")
                                    .send(p);
                            new FancyMessage("- [MOTD]")
                                    .color(ChatColor.YELLOW)
                                    .tooltip(ChatColor.YELLOW + "Modifier le MOTD de votre guilde")
                                    .suggest("/guild motd ")
                                    .send(p);
                            new FancyMessage("- [Tag]")
                                    .color(ChatColor.DARK_GRAY)
                                    .tooltip(ChatColor.DARK_GRAY + "Modifier le Tag de votre guilde")
                                    .suggest("/guild tag ")
                                    .send(p);
                            new FancyMessage("- [Shop]")
                                    .color(ChatColor.DARK_PURPLE)
                                    .tooltip(ChatColor.DARK_PURPLE + "Accéder au shop de la guilde")
                                    .command("/guild shop")
                                    .send(p);
                            new FancyMessage("- [Disband]")
                                    .color(ChatColor.AQUA)
                                    .tooltip(ChatColor.AQUA + "Dissoudre votre guilde")
                                    .command("/guild disband")
                                    .send(p);
                        }
                        else if(guildSQL.getRank(p)==2)
                        {
                            new FancyMessage("- [Invite]")
                                    .color(ChatColor.BLUE)
                                    .tooltip(ChatColor.BLUE + "Inviter un joueur dans la guilde")
                                    .suggest("/guild invite ")
                                    .send(p);
                            new FancyMessage("- [Kick]")
                                    .tooltip(ChatColor.RED + "Exclure un membre")
                                    .color(ChatColor.RED)
                                    .suggest("/guild kick ")
                                    .send(p);
                            new FancyMessage("- [Promote]")
                                    .color(ChatColor.DARK_GREEN)
                                    .tooltip(ChatColor.DARK_GREEN + "Promouvoir un membre")
                                    .suggest("/guild promote ")
                                    .send(p);
                            new FancyMessage("- [Demote]")
                                    .color(ChatColor.DARK_BLUE)
                                    .tooltip(ChatColor.DARK_BLUE + "Rétrograder un membre")
                                    .suggest("/guild demote ")
                                    .send(p);
                            new FancyMessage("- [Members]")
                                    .color(ChatColor.GOLD)
                                    .tooltip(ChatColor.GOLD + "Voir la liste des membres")
                                    .command("/guild members")
                                    .send(p);
                            new FancyMessage("- [Notifications]")
                                    .color(ChatColor.GREEN)
                                    .tooltip(ChatColor.DARK_RED + "Désactiver les notifications de guilde")
                                    .command("/guild notif")
                                    .send(p);
                            new FancyMessage("- [MOTD]")
                                    .color(ChatColor.YELLOW)
                                    .tooltip(ChatColor.YELLOW + "Modifier le MOTD de votre guilde")
                                    .suggest("/guild motd ")
                                    .send(p);
                            new FancyMessage("- [Tag]")
                                    .color(ChatColor.DARK_GRAY)
                                    .tooltip(ChatColor.DARK_GRAY + "Modifier le Tag de votre guilde")
                                    .suggest("/guild tag ")
                                    .send(p);
                            new FancyMessage("- [Shop]")
                                    .color(ChatColor.DARK_PURPLE)
                                    .tooltip(ChatColor.DARK_PURPLE + "Accéder au shop de la guilde")
                                    .command("/guild shop")
                                    .send(p);
                        }
                        else if(guildSQL.getRank(p)==1)
                        {
                            new FancyMessage("- [Invite]")
                                    .color(ChatColor.BLUE)
                                    .tooltip(ChatColor.BLUE + "Inviter un joueur dans la guilde")
                                    .suggest("/guild invite ")
                                    .send(p);
                            new FancyMessage("- [Kick]")
                                    .tooltip(ChatColor.RED + "Exclure un membre")
                                    .color(ChatColor.RED)
                                    .suggest("/guild kick ")
                                    .send(p);
                            new FancyMessage("- [Members]")
                                    .color(ChatColor.GOLD)
                                    .tooltip(ChatColor.GOLD + "Voir la liste des membres")
                                    .command("/guild members")
                                    .send(p);
                            new FancyMessage("- [Notifications]")
                                    .color(ChatColor.GREEN)
                                    .tooltip(ChatColor.DARK_RED + "Désactiver les notifications de guilde")
                                    .command("/guild notif")
                                    .send(p);
                        }
                        else {
                            new FancyMessage("[Members]")
                                    .color(ChatColor.YELLOW)
                                    .tooltip(ChatColor.GREEN + "Voir la liste des membres")
                                    .command("/guild members")
                                    .then(" [Notifications]")
                                    .color(ChatColor.GREEN)
                                    .tooltip(ChatColor.RED + "Désactiver les notifications de guilde")
                                    .command("/guild notif")
                                    .then(" [Quitter]")
                                    .color(ChatColor.RED)
                                    .tooltip(ChatColor.RED + "Quitter la guilde")
                                    .command("/guild leave")
                                    .send(p);
                        }
                    }
                    else {
                        if(guildSQL.getRank(p)==3)
                        {
                            new FancyMessage("- [Invite]")
                                    .color(ChatColor.BLUE)
                                    .tooltip(ChatColor.BLUE + "Inviter un joueur dans la guilde")
                                    .suggest("/guild invite ")
                                    .send(p);
                            new FancyMessage("- [Kick]")
                                    .tooltip(ChatColor.RED + "Exclure un membre")
                                    .color(ChatColor.RED)
                                    .suggest("/guild kick ")
                                    .send(p);
                            new FancyMessage("- [Promote]")
                                    .color(ChatColor.DARK_GREEN)
                                    .tooltip(ChatColor.DARK_GREEN + "Promouvoir un membre")
                                    .suggest("/guild promote ")
                                    .send(p);
                            new FancyMessage("- [Demote]")
                                    .color(ChatColor.DARK_BLUE)
                                    .tooltip(ChatColor.DARK_BLUE + "Rétrograder un membre")
                                    .suggest("/guild demote ")
                                    .send(p);
                            new FancyMessage("- [Members]")
                                    .color(ChatColor.GOLD)
                                    .tooltip(ChatColor.GOLD + "Voir la liste des membres")
                                    .command("/guild members")
                                    .send(p);
                            new FancyMessage("- [Notifications]")
                                    .color(ChatColor.DARK_RED)
                                    .tooltip(ChatColor.GREEN + "Activer les notifications de guilde")
                                    .command("/guild notif")
                                    .send(p);
                            new FancyMessage("- [MOTD]")
                                    .color(ChatColor.YELLOW)
                                    .tooltip(ChatColor.YELLOW + "Modifier le MOTD de votre guilde")
                                    .suggest("/guild motd ")
                                    .send(p);
                            new FancyMessage("- [Tag]")
                                    .color(ChatColor.DARK_GRAY)
                                    .tooltip(ChatColor.DARK_GRAY + "Modifier le Tag de votre guilde")
                                    .suggest("/guild tag ")
                                    .send(p);
                            new FancyMessage("- [Shop]")
                                    .color(ChatColor.DARK_PURPLE)
                                    .tooltip(ChatColor.DARK_PURPLE + "Accéder au shop de la guilde")
                                    .command("/guild shop")
                                    .send(p);
                            new FancyMessage("- [Disband]")
                                    .color(ChatColor.AQUA)
                                    .tooltip(ChatColor.AQUA + "Dissoudre votre guilde")
                                    .command("/guild disband")
                                    .send(p);
                        }
                        else if(guildSQL.getRank(p)==2)
                        {
                            new FancyMessage("- [Invite]")
                                    .color(ChatColor.BLUE)
                                    .tooltip(ChatColor.BLUE + "Inviter un joueur dans la guilde")
                                    .suggest("/guild invite ")
                                    .send(p);
                            new FancyMessage("- [Kick]")
                                    .tooltip(ChatColor.RED + "Exclure un membre")
                                    .color(ChatColor.RED)
                                    .suggest("/guild kick ")
                                    .send(p);
                            new FancyMessage("- [Promote]")
                                    .color(ChatColor.DARK_GREEN)
                                    .tooltip(ChatColor.DARK_GREEN + "Promouvoir un membre")
                                    .suggest("/guild promote ")
                                    .send(p);
                            new FancyMessage("- [Demote]")
                                    .color(ChatColor.DARK_BLUE)
                                    .tooltip(ChatColor.DARK_BLUE + "Rétrograder un membre")
                                    .suggest("/guild demote ")
                                    .send(p);
                            new FancyMessage("- [Members]")
                                    .color(ChatColor.GOLD)
                                    .tooltip(ChatColor.GOLD + "Voir la liste des membres")
                                    .command("/guild members")
                                    .send(p);
                            new FancyMessage("- [Notifications]")
                                    .color(ChatColor.DARK_RED)
                                    .tooltip(ChatColor.GREEN + "Activer les notifications de guilde")
                                    .command("/guild notif")
                                    .send(p);
                            new FancyMessage("- [MOTD]")
                                    .color(ChatColor.YELLOW)
                                    .tooltip(ChatColor.YELLOW + "Modifier le MOTD de votre guilde")
                                    .suggest("/guild motd ")
                                    .send(p);
                            new FancyMessage("- [Tag]")
                                    .color(ChatColor.DARK_GRAY)
                                    .tooltip(ChatColor.DARK_GRAY + "Modifier le Tag de votre guilde")
                                    .suggest("/guild tag ")
                                    .send(p);
                            new FancyMessage("- [Shop]")
                                    .color(ChatColor.DARK_PURPLE)
                                    .tooltip(ChatColor.DARK_PURPLE + "Accéder au shop de la guilde")
                                    .command("/guild shop")
                                    .send(p);
                        }
                        else if(guildSQL.getRank(p)==1)
                        {
                            new FancyMessage("- [Invite]")
                                    .color(ChatColor.BLUE)
                                    .tooltip(ChatColor.BLUE + "Inviter un joueur dans la guilde")
                                    .suggest("/guild invite ")
                                    .send(p);
                            new FancyMessage("- [Kick]")
                                    .tooltip(ChatColor.RED + "Exclure un membre")
                                    .color(ChatColor.RED)
                                    .suggest("/guild kick ")
                                    .send(p);
                            new FancyMessage("- [Members]")
                                    .color(ChatColor.GOLD)
                                    .tooltip(ChatColor.GOLD + "Voir la liste des membres")
                                    .command("/guild members")
                                    .send(p);
                            new FancyMessage("- [Notifications]")
                                    .color(ChatColor.DARK_RED)
                                    .tooltip(ChatColor.GREEN + "Activer les notifications de guilde")
                                    .command("/guild notif")
                                    .send(p);
                        }
                        else {
                            new FancyMessage("[Members]")
                                    .color(ChatColor.YELLOW)
                                    .tooltip(ChatColor.GREEN + "Voir la liste des membres")
                                    .command("/guild members")
                                    .then(" [Notifications]")
                                    .color(ChatColor.DARK_RED)
                                    .tooltip(ChatColor.GREEN + "Activer les notifications de guilde")
                                    .command("/guild notif")
                                    .then(" [Quitter]")
                                    .color(ChatColor.RED)
                                    .tooltip(ChatColor.RED + "Quitter la guilde")
                                    .command("/guild leave")
                                    .send(p);
                        }
                    }
                }
                else
                {
                    p.sendMessage(ChatColor.RED + "Vous n'êtes membre d'aucune guilde.");
                    new FancyMessage("Vous pouvez créer votre propre guilde en cliquant")
                            .color(ChatColor.YELLOW)
                            .then(" [ICI]")
                            .color(ChatColor.GREEN)
                            .style(ChatColor.BOLD)
                            .suggest("/guild create ")
                            .tooltip(ChatColor.GREEN + "Créer ma guilde")
                            .send(p);
                    p.sendMessage(ChatColor.YELLOW + "Ou alors, simplement vous faire inviter dans la guilde d'un joueur");
                }
            }
        }

        return false;
    }
}
