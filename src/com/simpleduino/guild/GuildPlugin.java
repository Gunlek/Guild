package com.simpleduino.guild;

import com.simpleduino.guild.Commands.GuildCommands;
import com.simpleduino.guild.Listeners.PlayerListener;
import com.simpleduino.guild.Listeners.ServerListener;
import com.simpleduino.guild.Messaging.MessageListener;
import com.simpleduino.guild.SQL.GuildSQL;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Simple-Duino on 28/06/2016.
 * Copyrights Simple-Duino, all rights reserved
 */

public class GuildPlugin extends JavaPlugin {

    private File cfgFile = new File("plugins/Guild/config.yml");

    public void onEnable() {
        this.getServer().getPluginCommand("guild").setExecutor(new GuildCommands());
        if(!cfgFile.exists())
        {
            cfgFile.getParentFile().mkdirs();
            try {
                cfgFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }

            YamlConfiguration cfg = YamlConfiguration.loadConfiguration(cfgFile);
            cfg.set("sql.hostname", "localhost");
            cfg.set("sql.database", "guild");
            cfg.set("sql.username", "user");
            cfg.set("sql.password", "password");
            cfg.set("guild.default.motd", "Ceci est le motd par défaut de votre guilde, utilisez /guild motd <new_motd> pour le modifier");
            cfg.set("guild.default.coins", "0");
            try {
                cfg.save(cfgFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        this.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        this.getServer().getMessenger().registerIncomingPluginChannel(this, "BungeeCord", new MessageListener());

        this.getServer().getPluginManager().registerEvents(new PlayerListener(), this);
        this.getServer().getPluginManager().registerEvents(new ServerListener(), this);
    }

    public void onDisable() {

    }

}
