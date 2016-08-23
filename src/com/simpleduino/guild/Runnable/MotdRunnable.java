package com.simpleduino.guild.Runnable;

import com.simpleduino.guild.SQL.GuildSQL;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * Created by Simple-Duino on 04/07/2016.
 * Copyrights Simple-Duino, all rights reserved
 */

public class MotdRunnable extends BukkitRunnable {

    private String playerName;
    private GuildSQL guildSQL = new GuildSQL();

    public MotdRunnable(String pName)
    {
        this.playerName = pName;
    }

    @Override
    public void run() {
        try
        {
            Player p = Bukkit.getPlayer(this.playerName);
            if (guildSQL.hasGuild(p)) {
                String guildName = guildSQL.getGuild(p);
                if (guildSQL.getNotif(p))
                {
                    p.sendMessage("------------------------------------------------");
                    p.sendMessage(guildSQL.getMotd(guildName).replace("&", "§"));
                    p.sendMessage("------------------------------------------------");
                }
            }
            guildSQL.closeConnection();
        }
        catch(Exception e)
        {

        }
    }
}
