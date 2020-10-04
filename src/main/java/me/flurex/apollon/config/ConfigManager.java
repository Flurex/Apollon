package me.flurex.apollon.config;

import me.flurex.apollon.Apollon;
import me.flurex.apollon.utils.Log;
import me.flurex.apollon.methods.Methods;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ConfigManager {

    private final Properties properties;
    private final Apollon plugin;

    public ConfigManager(Apollon plugin) {
        this.plugin = plugin;
        if(!(new File(plugin.getMethods().getJarPath() + "/config.properties")).exists()) {
            plugin.getLogger().warning("Could not find Configuration file. Creating a new one.");
            try {
                PrintWriter writer = new PrintWriter(plugin.getMethods().getJarPath() + "/config.properties", "UTF-8");
                writer.println("# Thanks for using my TeamSpeak Bot!");
                writer.println("# In this configuration file, you are able to change every important aspect of the Bot.");
                writer.println(" ");
                writer.println("# Server Settings");
                writer.println("# The host the bot should connect to");
                writer.println("server.host=127.0.0.1");
                writer.println("# The Port of the TeamSpeak Server");
                writer.println("server.port=9987");
                writer.println(" ");
                writer.println("# Query Settings");
                writer.println("# The login credentials for the server query.");
                writer.println("query.username=Apollon");
                writer.println("query.password=password");
                writer.println("# The Query Port of the Server.");
                writer.println("query.port=10011");
                writer.println(" ");
                writer.println("# Bot Settings");
                writer.println("# The Nickname the Bot should have.");
                writer.println("bot.nickname=Apollon");
                writer.println("# This should be set to 'default' unless the IP Adress the bot is running on, is whitelisted in the query_ip_whitelist.txt of your TeamSpeak Server.");
                writer.println("# If Apollon is running on the same machine as the TeamSpeak Server, use 127.0.0.1 as host. This IP is whitelisted by default.");
                writer.println("bot.floodrate=default");
                writer.println(" ");
                writer.println("# Advertisement");
                writer.println("# Enable/Disable the Feature");
                writer.println("advertisement.enabled=true");
                writer.println("# The delay between the advertisement messages (in minutes)");
                writer.println("advertisement.delay=1");
                writer.println("# The message that will be broadcastet.");
                writer.println("advertisement.message=This is a default Message. Please change it in the config.properties file.");
                writer.println(" ");
                writer.println("# AFKManager");
                writer.println("# Enable/Disable the Feature");
                writer.println("afk.enabled=true");
                writer.println("# The time a Client needs to be AFK for the Bot to move it (in seconds)");
                writer.println("afk.time=120");
                writer.println("# Should the Client be moved or kicked?");
                writer.println("afk.mode=move");
                writer.println("# The ID of the Channel the Client should be moved to");
                writer.println("afk.move-channel=1");
                writer.println("Should the Client be notified when being moved?");
                writer.println("afk.move-notify=true");
                writer.println("Should the Bot send a text message to the Client or poke it?");
                writer.println("afk.move-notify-type=chat");
                writer.println("# The Message the Client will receive when being moved");
                writer.println("afk.move-notify-message=You have been moved because you were idle for too long.");
                writer.println("# The Message the Client will receive when being kicked");
                writer.println("afk.kick-message=You have been kicked because you were idle for too long.");
                writer.println("# The list of groups that should not be affected by the AFK-Manager");
                writer.println("# -1: every group should be affected");
                writer.println("# 1, 2, 3...: IDs of the server groups that should not be affected seperated by a comma.");
                writer.println("afk.bypass-groups=-1");
                writer.println("# The list of channels that should not be affected by the AFK-Manager");
                writer.println("# -1: every channel should be affected");
                writer.println("# 1, 2, 3...: IDs of the channels that should not be affected seperated by a comma.");
                writer.println("afk.bypass-channel=-1");
                writer.println("# The delay between checks if a Client is AFK (in seconds)");
                writer.println("afk.check-time=60");
                writer.println(" ");
                writer.println("# AntiVPN");
                writer.println("# Enable/Disable the Feature");
                writer.println("antivpn.enabled=true");
                writer.println("# Should a Client be kicked or banned for using a VPN?");
                writer.println("antivpn.mode=kick");
                writer.println("#When should we check if a Client uses a VPN?");
                writer.println("# At the moment only join and move are available.");
                writer.println("antivpn.check=join, move");
                writer.println("# The message a Client should receive when being kicked");
                writer.println("antivpn.kick-message=The use of a VPN is prohibited on this Server.");
                writer.println("# The message a Client should receive when being banned");
                writer.println("antivpn.ban-message=The use of a VPN is prohibited on this Server.");
                writer.println("# The duration of a ban for using a VPN (in seconds)");
                writer.println("antivpn.ban-duration=86400");
                writer.println("# The list of groups that should not be affected by the AntiVPN");
                writer.println("# -1: every group should be affected");
                writer.println("# 1, 2, 3...: IDs of the server groups that should not be affected seperated by a comma.");
                writer.println("antivpn.bypass-groups=-1");
                writer.println(" ");
                writer.println("# SupportManager");
                writer.println("# Enable/Disable the Feature");
                writer.println("support.enabled=true");
                writer.println("# The ID of the Support Channel");
                writer.println("support.channel=1");
                writer.println("# Should we send a text message or poke a staff member when a Client enters the Support Channel?");
                writer.println("support.notify-type=chat");
                writer.println("# The list of group IDs that should be sent a message when a Client enters the Support Channel seperated by a comma.");
                writer.println("support.notify-groups=1, 2");
                writer.println("# The list of group IDs that should be allowed to open/close the Support Channel");
                writer.println("support.channel-management-groups=1, 2");
                writer.println("# The Command that can be used to open the Support Channel");
                writer.println("support.command-open=!open");
                writer.println("# The Command that can be used to close the Support Channel");
                writer.println("support.command-close=!close");
                writer.println("# The name of the Support Channel when opened");
                writer.println("support.channel-name-open=Support >> Open");
                writer.println("# The name of the Support Channel when closed");
                writer.println("support.channel-name-closed=Support >> Closed");
                writer.println("# The max amount of Clients that can be in the Support Channel at once when opened");
                writer.println("support.channel-maxclients=10");
                writer.println(" ");
                writer.println("# WelcomeMessage");
                writer.println("# Enable/Disable the Feature");
                writer.println("welcome.enabled=true");
                writer.println("# The list of groups that should not be sent a Welcome message");
                writer.println("# -1: every group should be sent a message");
                writer.println("# 1, 2, 3...: IDs of the server groups that should not be sent a message seperated by a comma.");
                writer.println("welcome.bypass-groups=-1");
                writer.println("# The message that should be sent to a Client on join");
                writer.println("# You can seperate the Text into two or more messages with using %nm%.");
                writer.println("welcome.message=Welcome to my TeamSpeak Server!%nm%If you have any questions, you can always ask a Supporter!");
                writer.println(" ");
                writer.println("# Commands");
                writer.println("# Enable/Disable the Feature");
                writer.println("commands.enabled=true");
                writer.println("# Enable/Disable the Help Command");
                writer.println("commands.help-enabled=true");
                writer.println("# Enable/Disable the Hello Command");
                writer.println("commands.hello-enabled=true");
                writer.println("# Enable/Disable the KickMe Command");
                writer.println("commands.kickme-enabled=true");
                writer.println("# The Message a user should be sent when entering the Help Command");
                writer.println("# You can seperate the Text into two or more messages with using %nm%.");
                writer.println("commands.help-message=This is the default Help Message.%nm%You can change it in the config.properties.");
                writer.println("# The Message a user will be sent when sending !hello to the bot");
                writer.println("commands.hello-message=Hey! My Name is Apollon. I'm the Query-Bot on this TeamSpeak.");
                writer.println("# The Kick Reason a user will get when entering the !kickme Command");
                writer.println("commands.kickme-message=Whatever bro..");
                writer.close();
            } catch (FileNotFoundException e) {
                plugin.getLogger().error("Could not copy default Configuration. Please check the permissions and try again.");
                plugin.getLogger().error("Exception: FileNotFoundException");
                plugin.getLogger().error("Here's the stack trace:");
                e.printStackTrace();
                System.exit(1);
            } catch (UnsupportedEncodingException e) {
                plugin.getLogger().error("Could not copy default Configuration. Please contact the Developer about this.");
                plugin.getLogger().error("Exception: UnsupportedEncodingException");
                plugin.getLogger().error("Here's the stack trace:");
                e.printStackTrace();
            }
        }
        this.properties = new Properties();
        try {
            FileInputStream fileInputStream = new FileInputStream(plugin.getMethods().getJarPath() + "/config.properties");
            properties.load(fileInputStream);
        } catch (FileNotFoundException e) {
            plugin.getLogger().error("Could not load the Configuration. Please check the permissions and try again.");
            plugin.getLogger().error("Exception: FileNotFoundException");
            plugin.getLogger().error("Here's the stack trace:");
            e.printStackTrace();
            System.exit(1);
        } catch (IOException e) {
            plugin.getLogger().error("Could not load the Configuration. Please contact the Developer about this.");
            plugin.getLogger().error("Exception: IOException");
            plugin.getLogger().error("Here's the stack trace:");
            e.printStackTrace();
        }
    }

    public String get(ConfigKey key) {
        return properties.getProperty(key.getKey());
    }

    public int getInt(ConfigKey key) {
        return Integer.parseInt(properties.getProperty(key.getKey()));
    }

    public boolean getBoolean(ConfigKey key) {
        return Boolean.parseBoolean(properties.getProperty(key.getKey()));
    }

    public long getLong(ConfigKey key) {
        return Long.parseLong(properties.getProperty(key.getKey()));
    }

    public String[] getStringArray(ConfigKey key) {
        return properties.getProperty(key.getKey()).replaceAll("\\s+", "").split(",");
    }

    public int[] getIntArray(ConfigKey key) {
        String[] tempArr = getStringArray(key);
        int[] tempIntArr = new int[tempArr.length];
        for (int i = 0; i < tempArr.length; i++) {
            tempIntArr[i] = Integer.parseInt(tempArr[i]);
        }
        return tempIntArr;
    }

    public List<String> getStringList(ConfigKey key) {
        return Arrays.asList(getStringArray(key));
    }

    public List<Integer> getIntegerList(ConfigKey key) {
        return IntStream.of(getIntArray(key)).boxed().collect(Collectors.toList());
    }

}
