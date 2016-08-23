package com.simpleduino.guild.Runnable;

import com.simpleduino.guild.Commands.Guild.InviteCommand;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * Created by Simple-Duino on 29/06/2016.
 * Copyrights Simple-Duino, all rights reserved
 */

public class InvitationExpire extends BukkitRunnable {

    private String playerName;

    public InvitationExpire(String pn)
    {
        this.playerName = pn;
    }

    @Override
    public void run() {
        if(InviteCommand.pendingRequest.containsKey(this.playerName)) {
            String invited = InviteCommand.pendingRequest.get(this.playerName);
            InviteCommand.GuildRequest.remove(invited);
            InviteCommand.pendingRequest.remove(this.playerName);
            try {
                Bukkit.getPlayer(invited).sendMessage(ChatColor.YELLOW + "L'invitation de guilde a expiré");
            } catch (Exception e) {

            }
            try {
                Bukkit.getPlayer(this.playerName).sendMessage(ChatColor.YELLOW + "L'invitation de guilde a expiré");
            } catch (Exception e) {

            }
        }
    }
}
