package com.simpleduino.guild.Commands.Guild;

import com.simpleduino.guild.SQL.GuildSQL;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by Simple-Duino on 28/06/2016.
 * Copyrights Simple-Duino, all rights reserved
 */

public class MembersCommand {

    private GuildSQL guildSQL = new GuildSQL();
    File cfgFile = new File("plugins/Guild/config.yml");
    YamlConfiguration cfg = YamlConfiguration.loadConfiguration(cfgFile);

    public MembersCommand(CommandSender sender, String[] args) {
        if (sender instanceof Player) {
            if (sender.hasPermission("Guild.members")) {
                Player p = (Player)sender;
                if(guildSQL.hasGuild(p)) {
                    String guildName = guildSQL.getGuild(p);
                    ArrayList<String> guildMembers = guildSQL.getGuildMembers(guildName);
                    String inlineMembers = "";
                    sender.sendMessage(ChatColor.YELLOW + "Voici la liste des membres de la guilde " + guildName);
                    for (int i = 0; i < guildMembers.size(); i++) {
                        /*if (i < guildMembers.size() - 1)
                            inlineMembers += guildMembers.get(i) + ", ";
                        else
                            inlineMembers += guildMembers.get(i);*/
                        String rankName = cfg.get("ranks."+Integer.toString(guildSQL.getRank(guildMembers.get(i)))).toString();
                        rankName = rankName.replace("&", "§");
                        sender.sendMessage(ChatColor.YELLOW + guildMembers.get(i) +" - "+rankName);
                    }
                    sender.sendMessage(ChatColor.YELLOW + inlineMembers);
                }
                else
                {
                    p.sendMessage(ChatColor.RED + "Vous n'êtes pas membre d'une guilde");
                }
            } else {
                sender.sendMessage(ChatColor.RED + "Vous n'avez pas la permission de lister les membres d'une guilde");
            }
        } else {
            sender.sendMessage(ChatColor.RED + "Vous devez être un joueur pour executer cette commande");
        }

        guildSQL.closeConnection();
    }

}
