package me.flurex.apollon.config;

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
    private final Log logger = new Log();

    public ConfigManager() {
        if(!(new File(new Methods().getJarPath() + "/config.properties")).exists()) {
            logger.warning("Could not find Configuration file. Creating a new one.");
            try {
                PrintWriter writer = new PrintWriter(new Methods().getJarPath() + "/config.properties", "UTF-8");
                writer.println("# Thanks for using my TeamSpeak Bot!");
                writer.println("# In this configuration file, you are able to change every important aspect of the Bot.");
                writer.println(" ");
                writer.println("# Server Settings");
                writer.println("server.host=127.0.0.1");
                writer.println("server.port=9987");
                writer.println(" ");
                writer.println("# Query Settings");
                writer.println("query.username=Apollon");
                writer.println("query.password=password");
                writer.println("query.port=10011");
                writer.println(" ");
                writer.println("# Bot Settings");
                writer.println("bot.nickname=Apollon");
                writer.println("bot.floodrate=default");
                writer.println(" ");
                writer.println("# Advertisement");
                writer.println("advertisement.enabled=true");
                writer.println("advertisement.delay=1");
                writer.println("advertisement.message=This is a default Message. Please change it in the config.properties file.");
                writer.println(" ");
                writer.println("# AFKManager");
                writer.println("afk.enabled=true");
                writer.println("afk.time=120");
                writer.println("afk.mode=move");
                writer.println("afk.move-channel=1");
                writer.println("afk.move-notify=true");
                writer.println("afk.move-notify-type=chat");
                writer.println("afk.move-notify-message=You have been moved because you were idle for too long.");
                writer.println("afk.kick-message=You have been kicked because you were idle for too long.");
                writer.println("afk.bypass-groups=-1");
                writer.println("afk.bypass-channel=-1");
                writer.println("afk.check-time=60");
                writer.println(" ");
                writer.println("# AntiVPN");
                writer.println("antivpn.enabled=true");
                writer.println("antivpn.mode=kick");
                writer.println("antivpn.check=join, move");
                writer.println("antivpn.kick-message=The use of a VPN is prohibited on this Server.");
                writer.println("antivpn.ban-message=The use of a VPN is prohibited on this Server.");
                writer.println("antivpn.ban-duration=86400");
                writer.println("antivpn.bypass-groups=-1");
                writer.println(" ");
                writer.println("# SupportManager");
                writer.println("support.enabled=true");
                writer.println("support.channel=1");
                writer.println("support.notify-type=chat");
                writer.println("support.notify-groups=1, 2");
                writer.println("support.channel-management-groups=1, 2");
                writer.println("support.command-open=!open");
                writer.println("support.command-close=!close");
                writer.println("support.channel-name-open=Support >> Open");
                writer.println("support.channel-name-closed=Support >> Closed");
                writer.println("support.channel-maxclients=10");
                writer.println(" ");
                writer.println("# WelcomeMessage");
                writer.println("welcome.enabled=true");
                writer.println("welcome.bypass-groups=-1");
                writer.println("welcome.message=Welcome to my TeamSpeak Server!%nm%If you have any questions, you can always ask a Supporter!");
                writer.println(" ");
                writer.println("# Essential Commands");
                writer.println("commands.enabled=true");
                writer.println("commands.help-enabled=true");
                writer.println("commands.help-message=Test%nm%Help");
                writer.close();
            } catch (FileNotFoundException e) {
                logger.error("Could not copy default Configuration. Please check the permissions and try again.");
                logger.error("Exception: FileNotFoundException");
                logger.error("Here's the stack trace:");
                e.printStackTrace();
                System.exit(1);
            } catch (UnsupportedEncodingException e) {
                logger.error("Could not copy default Configuration. Please contact the Developer about this.");
                logger.error("Exception: UnsupportedEncodingException");
                logger.error("Here's the stack trace:");
                e.printStackTrace();
            }
        }
        this.properties = new Properties();
        try {
            FileInputStream fileInputStream = new FileInputStream(new Methods().getJarPath() + "/config.properties");
            properties.load(fileInputStream);
        } catch (FileNotFoundException e) {
            logger.error("Could not load the Configuration. Please check the permissions and try again.");
            logger.error("Exception: FileNotFoundException");
            logger.error("Here's the stack trace:");
            e.printStackTrace();
            System.exit(1);
        } catch (IOException e) {
            logger.error("Could not load the Configuration. Please contact the Developer about this.");
            logger.error("Exception: IOException");
            logger.error("Here's the stack trace:");
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
