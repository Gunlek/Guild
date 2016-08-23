package com.simpleduino.guild.Messaging;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import com.simpleduino.economy.Messaging.CustomMessageSender;
import com.simpleduino.guild.Commands.Guild.InviteCommand;
import com.simpleduino.guild.GuildPlugin;
import com.simpleduino.guild.Runnable.InvitationExpire;
import com.simpleduino.guild.Runnable.MotdRunnable;
import com.simpleduino.guild.SQL.GuildSQL;
import mkremins.fanciful.FancyMessage;
import org.apache.logging.log4j.core.pattern.UUIDPatternConverter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by Simple-Duino on 10/06/2016.
 * Copyrights Simple-Duino, all rights reserved
 */

public class MessageListener implements PluginMessageListener {

    private GuildSQL guildSQL = new GuildSQL();

    @Override
    public void onPluginMessageReceived(String channel, Player player, byte[] message) {
        if (!channel.equals("BungeeCord")) {
            return;
        }

        ByteArrayDataInput in = ByteStreams.newDataInput(message);
        String subchannel = in.readUTF();
        if(subchannel.equalsIgnoreCase("playerjoinproxy"))
        {
            String name = in.readUTF();
            String uuid = in.readUTF();
            Bukkit.getServer().getScheduler().runTaskLater(GuildPlugin.getPlugin(GuildPlugin.class), new MotdRunnable(name), 20L*1);
            try
            {
                Player p = Bukkit.getPlayer(name);
                String guildName = guildSQL.getGuild(name);
                if(guildSQL.hasGuild(name))
                {
                    for(String member : guildSQL.getGuildMembers(guildName))
                    {
                        try {
                            Player m = Bukkit.getPlayer(member);
                            if (!m.getName().equalsIgnoreCase(name)) {
                                if (guildSQL.getNotif(m))
                                    m.sendMessage(ChatColor.YELLOW + "[" + ChatColor.GOLD + "Guilde" + ChatColor.YELLOW + "] " + ChatColor.DARK_GRAY.toString() + ChatColor.ITALIC + name + ChatColor.RESET.toString() + ChatColor.DARK_GRAY + " a rejoint le serveur");
                            }
                        }
                        catch(Exception e) {

                        }
                    }
                    new CustomMessageSender("ALL", "GuildPlayerJoinProxy", new String[]{p.getName(), guildSQL.getGuild(p)});
                }
            }
            catch(Exception e)
            {

            }
        }
        else if(subchannel.equalsIgnoreCase("playerleftproxy"))
        {
            String name = in.readUTF();
            String uuid = in.readUTF();
            try
            {
                Player p = Bukkit.getPlayer(name);
                String guildName = guildSQL.getGuild(name);
                if(guildSQL.hasGuild(name))
                {
                    for(String member : guildSQL.getGuildMembers(guildName))
                    {
                        try {
                            Player m = Bukkit.getPlayer(member);
                            if (!m.getName().equalsIgnoreCase(name)) {
                                if (guildSQL.getNotif(m))
                                    m.sendMessage(ChatColor.YELLOW + "[" + ChatColor.GOLD + "Guilde" + ChatColor.YELLOW + "] " + ChatColor.DARK_GRAY.toString() + ChatColor.ITALIC + name + ChatColor.RESET.toString() + ChatColor.DARK_GRAY + " a quitté le serveur");
                            }
                        }
                        catch(Exception e) {

                        }
                    }
                    new CustomMessageSender("ALL", "GuildPlayerLeftProxy", new String[]{p.getName(), guildSQL.getGuild(p)});
                }
            }
            catch(Exception e)
            {

            }
        }
        else if (subchannel.equals("GuildInvitation")) {
            short len = in.readShort();
            byte[] msgBytes = new byte[len];
            in.readFully(msgBytes);

            DataInputStream msgin = new DataInputStream(new ByteArrayInputStream(msgBytes));
            String inviter = null, invited = null, guildName = null;
            try {
                inviter = msgin.readUTF();
                invited = msgin.readUTF();
                guildName = msgin.readUTF();
            } catch (IOException e) {
                e.printStackTrace();
            }

            InviteCommand.pendingRequest.put(inviter, invited);
            InviteCommand.GuildRequest.put(invited, guildName);
            Bukkit.getServer().getScheduler().runTaskLater(GuildPlugin.getPlugin(GuildPlugin.class), new InvitationExpire(inviter), 20L*15);

            try
            {
                Player invitedPlayer = Bukkit.getPlayer(invited);
                new FancyMessage(inviter+" vous invite à rejoindre sa guilde ("+guildName+")")
                        .color(ChatColor.BLUE)
                        .send(invitedPlayer);
                new FancyMessage("Acceptez-vous ?")
                        .color(ChatColor.BLUE)
                        .send(invitedPlayer);
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
                        .send(invitedPlayer);
                invitedPlayer.sendMessage(ChatColor.YELLOW + "L'invitation expire dans 15 secondes");
            }
            catch(Exception e)
            {
                //Invited player isn't on this server
            }
        }

        else if (subchannel.equals("GuildAcceptInvitation")) {
            short len = in.readShort();
            byte[] msgBytes = new byte[len];
            in.readFully(msgBytes);

            DataInputStream msgin = new DataInputStream(new ByteArrayInputStream(msgBytes));
            String inviter = null, invited = null, guildName = null;
            try {
                inviter = msgin.readUTF();
                invited = msgin.readUTF();
                guildName = msgin.readUTF();
            } catch (IOException e) {
                e.printStackTrace();
            }

            InviteCommand.GuildRequest.remove(invited);
            InviteCommand.pendingRequest.remove(inviter);

            try
            {
                Player invitedPlayer = Bukkit.getPlayer(invited);
                invitedPlayer.sendMessage(ChatColor.GREEN + invited + " a accepté votre invitation à rejoindre votre guilde");
            }
            catch(Exception e)
            {
                //Invited player isn't on this server
            }
        }

        else if (subchannel.equals("GuildDenyInvitation")) {
            short len = in.readShort();
            byte[] msgBytes = new byte[len];
            in.readFully(msgBytes);

            DataInputStream msgin = new DataInputStream(new ByteArrayInputStream(msgBytes));
            String inviter = null, invited = null;
            try {
                inviter = msgin.readUTF();
                invited = msgin.readUTF();
            } catch (IOException e) {
                e.printStackTrace();
            }

            InviteCommand.GuildRequest.remove(invited);
            InviteCommand.pendingRequest.remove(inviter);

            try
            {
                Player inviterPlayer = Bukkit.getPlayer(inviter);
                inviterPlayer.sendMessage(ChatColor.GREEN + invited + " a decliné l'invitation à rejoindre votre guilde");
            }
            catch(Exception e)
            {
                //Inviter player isn't on this server
            }
        }

        else if (subchannel.equals("PlayerLeaveGuild")) {
            short len = in.readShort();
            byte[] msgBytes = new byte[len];
            in.readFully(msgBytes);

            DataInputStream msgin = new DataInputStream(new ByteArrayInputStream(msgBytes));
            String lefter = null, guildName = null;
            try {
                lefter = msgin.readUTF();
                guildName = msgin.readUTF();
            } catch (IOException e) {
                e.printStackTrace();
            }

            for(String pName : guildSQL.getGuildMembers(guildName))
            {
                try
                {
                    Bukkit.getPlayer(pName).sendMessage(ChatColor.GREEN + lefter+" a quitté votre guilde");
                }
                catch(Exception e)
                {

                }
            }
        }

        else if (subchannel.equals("GuildPromoteMember")) {
            short len = in.readShort();
            byte[] msgBytes = new byte[len];
            in.readFully(msgBytes);

            DataInputStream msgin = new DataInputStream(new ByteArrayInputStream(msgBytes));
            String promoter = null, promoted = null, guildName = null;
            int rank = -1;
            try {
                promoter = msgin.readUTF();
                rank = Integer.parseInt(msgin.readUTF());
                promoted = msgin.readUTF();
                guildName = msgin.readUTF();
            } catch (IOException e) {
                e.printStackTrace();
            }

            try
            {
                Player promotePlayer = Bukkit.getPlayer(promoted);
                promotePlayer.sendMessage(ChatColor.GREEN + "Vous avez été promu par "+promoter+" dans votre guilde");

            }
            catch(Exception e)
            {

            }
        }



        else if (subchannel.equals("GuildDemoteMember")) {
            short len = in.readShort();
            byte[] msgBytes = new byte[len];
            in.readFully(msgBytes);

            DataInputStream msgin = new DataInputStream(new ByteArrayInputStream(msgBytes));
            String demoter = null, demoted = null, guildName = null;
            int rank = -1;
            try {
                demoter = msgin.readUTF();
                rank = Integer.parseInt(msgin.readUTF());
                demoted = msgin.readUTF();
                guildName = msgin.readUTF();
            } catch (IOException e) {
                e.printStackTrace();
            }

            try
            {
                Player demotedPlayer = Bukkit.getPlayer(demoted);
                demotedPlayer.sendMessage(ChatColor.GREEN + "Vous avez été promu par "+demoter+" dans votre guilde");
            }
            catch(Exception e)
            {

            }
        }

        else if (subchannel.equals("GuildMemberAlreadyAtLowestRank")) {
            short len = in.readShort();
            byte[] msgBytes = new byte[len];
            in.readFully(msgBytes);

            DataInputStream msgin = new DataInputStream(new ByteArrayInputStream(msgBytes));
            String demoter = null, demoted = null;
            try {
                demoter = msgin.readUTF();
                demoted = msgin.readUTF();
            } catch (IOException e) {
                e.printStackTrace();
            }

            try
            {
                Player p = Bukkit.getPlayer(demoter);
                p.sendMessage(ChatColor.RED + "Le membre est déjà au rang le plus bas");
            }
            catch(Exception e)
            {
                //Player is not on this server
            }
        }

        else if (subchannel.equals("NotAllowedToDemoteGuildMember")) {
            short len = in.readShort();
            byte[] msgBytes = new byte[len];
            in.readFully(msgBytes);

            DataInputStream msgin = new DataInputStream(new ByteArrayInputStream(msgBytes));
            String demoter = null, demoted = null;
            try {
                demoter = msgin.readUTF();
                demoted = msgin.readUTF();
            } catch (IOException e) {
                e.printStackTrace();
            }

            try
            {
                Player p = Bukkit.getPlayer(demoter);
                p.sendMessage(ChatColor.RED + "Vous n'avez pas l'autorisation de rétrograder un membre de votre guilde");
            }
            catch(Exception e)
            {
                //Player is not on this server
            }
        }

        else if (subchannel.equals("GuildMemberNotInGuild")) {
            short len = in.readShort();
            byte[] msgBytes = new byte[len];
            in.readFully(msgBytes);

            DataInputStream msgin = new DataInputStream(new ByteArrayInputStream(msgBytes));
            String demoter = null, demoted = null;
            try {
                demoter = msgin.readUTF();
                demoted = msgin.readUTF();
            } catch (IOException e) {
                e.printStackTrace();
            }

            try
            {
                Player p = Bukkit.getPlayer(demoter);
                p.sendMessage(ChatColor.RED + "Ce joueur n'est pas de votre guilde");
            }
            catch(Exception e)
            {
                //Player is not on this server
            }
        }

        else if (subchannel.equals("GuildMemberHasNoGuild")) {
            short len = in.readShort();
            byte[] msgBytes = new byte[len];
            in.readFully(msgBytes);

            DataInputStream msgin = new DataInputStream(new ByteArrayInputStream(msgBytes));
            String demoter = null, demoted = null;
            try {
                demoter = msgin.readUTF();
                demoted = msgin.readUTF();
            } catch (IOException e) {
                e.printStackTrace();
            }

            try
            {
                Player p = Bukkit.getPlayer(demoter);
                p.sendMessage(ChatColor.RED + "Ce joueur n'a pas de guilde");
            }
            catch(Exception e)
            {
                //Player is not on this server
            }
        }

        else if (subchannel.equals("GuildMemberRankWillBecomeEgualOrSupThanPromoter")) {
            short len = in.readShort();
            byte[] msgBytes = new byte[len];
            in.readFully(msgBytes);

            DataInputStream msgin = new DataInputStream(new ByteArrayInputStream(msgBytes));
            String demoter = null, demoted = null;
            try {
                demoter = msgin.readUTF();
                demoted = msgin.readUTF();
            } catch (IOException e) {
                e.printStackTrace();
            }

            try
            {
                Player p = Bukkit.getPlayer(demoter);
                p.sendMessage(ChatColor.RED + "Vous ne pouvez pas promouvoir un membre vers un rang supérieur ou égal au votre");
            }
            catch(Exception e)
            {
                //Player is not on this server
            }
        }

        else if (subchannel.equals("PlayerDisbandGuild")) {
            short len = in.readShort();
            byte[] msgBytes = new byte[len];
            in.readFully(msgBytes);

            DataInputStream msgin = new DataInputStream(new ByteArrayInputStream(msgBytes));
            String disbander = null, memberList = null;
            try {
                disbander = msgin.readUTF();
                memberList = msgin.readUTF();
            } catch (IOException e) {
                e.printStackTrace();
            }

            for(String member : memberList.split(", "))
            {
                try
                {
                    Player pMember = Bukkit.getPlayer(member);
                    pMember.sendMessage(ChatColor.YELLOW + disbander + " a dissous votre guilde");
                }
                catch(Exception e)
                {
                    //Player isn't on this server
                }
            }
        }

        else if (subchannel.equals("GuildMemberKicked")) {
            short len = in.readShort();
            byte[] msgBytes = new byte[len];
            in.readFully(msgBytes);

            DataInputStream msgin = new DataInputStream(new ByteArrayInputStream(msgBytes));
            String kicker = null, kicked = null;
            try {
                kicker = msgin.readUTF();
                kicked = msgin.readUTF();
            } catch (IOException e) {
                e.printStackTrace();
            }

            try
            {
                Player kickedPlayer = Bukkit.getPlayer(kicked);
                kickedPlayer.sendMessage(ChatColor.YELLOW + "Vous avez été éjecté de votre guilde par "+kicker);
            }
            catch(Exception e)
            {
                //Player isn't on this server
            }
        }

        else if (subchannel.equals("GuildPlayerJoinProxy")) {
            short len = in.readShort();
            byte[] msgBytes = new byte[len];
            in.readFully(msgBytes);

            DataInputStream msgin = new DataInputStream(new ByteArrayInputStream(msgBytes));
            String joiner = null, guildName = null;
            try {
                joiner = msgin.readUTF();
                guildName = msgin.readUTF();
            } catch (IOException e) {
                e.printStackTrace();
            }

            for(String member : guildSQL.getGuildMembers(guildName))
            {
                try {
                    Player pMember = Bukkit.getPlayer(member);
                    if (pMember.getName().toLowerCase() != joiner) {
                        if (guildSQL.getNotif(pMember))
                            pMember.sendMessage(ChatColor.RED + joiner + ChatColor.YELLOW + " a rejoint le serveur");
                    }
                }
                catch(Exception e)
                {
                    //Memnber isn't on this server
                }
            }
        }

        else if (subchannel.equals("GuildPlayerLeftProxy")) {
            short len = in.readShort();
            byte[] msgBytes = new byte[len];
            in.readFully(msgBytes);

            DataInputStream msgin = new DataInputStream(new ByteArrayInputStream(msgBytes));
            String lefter = null, guildName = null;
            try {
                lefter = msgin.readUTF();
                guildName = msgin.readUTF();
            } catch (IOException e) {
                e.printStackTrace();
            }

            for(String member : guildSQL.getGuildMembers(guildName))
            {
                try {
                    Player pMember = Bukkit.getPlayer(member);
                    if (pMember.getName().toLowerCase() != lefter) {
                        if (guildSQL.getNotif(pMember))
                            pMember.sendMessage(ChatColor.RED + lefter + ChatColor.YELLOW + " a quitté le serveur");
                    }
                }
                catch(Exception e)
                {
                    //Memnber isn't on this server
                }
            }
        }
    }

}
