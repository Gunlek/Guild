package com.simpleduino.guild.Listeners;

import com.simpleduino.guild.SQL.GuildSQL;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.PluginEnableEvent;

import java.util.ArrayList;

/**
 * Created by Simple-Duino on 05/07/2016.
 * Copyrights Simple-Duino, all rights reserved
 */

public class ServerListener implements Listener {

    @EventHandler
    public void onPluginLoad(PluginEnableEvent e)
    {
        ArrayList<String> guildList = new GuildSQL().getGuilds();
        for(String guildName : guildList)
        {
            Bukkit.broadcastMessage(guildName);
            new GuildSQL().setDailyCoins(guildName, 0);
        }
    }
}
