package me.flurex.apollon;

import com.github.theholywaffle.teamspeak3.TS3Api;
import com.github.theholywaffle.teamspeak3.TS3Config;
import com.github.theholywaffle.teamspeak3.TS3Query;
import com.github.theholywaffle.teamspeak3.api.exception.TS3CommandFailedException;
import com.github.theholywaffle.teamspeak3.api.exception.TS3ConnectionFailedException;
import me.flurex.apollon.config.ConfigKey;
import me.flurex.apollon.config.ConfigManager;
import me.flurex.apollon.features.*;
import me.flurex.apollon.utils.Log;

public class Apollon {

    private final TS3Api api;
    private final ConfigManager config;
    private final Log logger = new Log();

    public static void main(String[] args) {
        new Apollon();
    }

    protected Apollon() {
        logger.info("TeamSpeak Bot 'Apollon' v1.0 is starting...");
        config = new ConfigManager();
        final TS3Config ts3Config = new TS3Config();
        ts3Config.setHost(config.get(ConfigKey.SERVER_HOST));
        logger.info("The Host was set to " + config.get(ConfigKey.SERVER_HOST));
        if(config.get(ConfigKey.BOT_FLOODRATE).equalsIgnoreCase("default")) {
            ts3Config.setFloodRate(TS3Query.FloodRate.DEFAULT);
            logger.info("The Floodrate was set to default.");
        } else if(config.get(ConfigKey.BOT_FLOODRATE).equalsIgnoreCase("unlimited")) {
            ts3Config.setFloodRate(TS3Query.FloodRate.UNLIMITED);
            logger.info("The Floodrate was set to unlimited.");
        } else {
            logger.error("Floodrate '" + config.get(ConfigKey.BOT_FLOODRATE) + " isn't an option.");
            logger.error("Please set it to either 'default' or 'unlimited'.");
            System.exit(1);
        }
        ts3Config.setQueryPort(config.getInt(ConfigKey.QUERY_PORT));
        logger.info("The Query Port was set to " + config.getInt(ConfigKey.QUERY_PORT));
        final TS3Query query = new TS3Query(ts3Config);
        query.connect();
        this.api = query.getApi();
        try {
            api.login(config.get(ConfigKey.QUERY_USERNAME), config.get(ConfigKey.QUERY_PASSWORD));
            logger.info("Query successfully logged in with Account '" + config.get(ConfigKey.QUERY_USERNAME) + "'");
        } catch(TS3CommandFailedException ex) {
            logger.error("Could not connect to " + config.get(ConfigKey.SERVER_HOST) + ".");
            logger.error("Please check your login credentials!");
            logger.error("Here's the stack trace:");
            ex.printStackTrace();
            System.exit(1);
        } catch(TS3ConnectionFailedException ex) {
            logger.error("Could not connect to " + config.get(ConfigKey.SERVER_HOST) + ".");
            logger.error("Pleae check Hostname and Port of the Server.");
            logger.error("Here's the stack trace:");
            ex.printStackTrace();
            System.exit(1);
        }
        api.selectVirtualServerByPort(config.getInt(ConfigKey.SERVER_PORT), config.get(ConfigKey.BOT_NICKNAME));
        logger.info("Nickname was set to '" + config.get(ConfigKey.BOT_NICKNAME) + "'");
        loadFeatures();
        api.registerAllEvents();
        logger.info("The Bot successfully started and is connected to " + config.get(ConfigKey.SERVER_HOST));
    }

    private void loadFeatures() {
        if(config.getBoolean(ConfigKey.ADVERTISEMENT_ENABLED)) {
            new Advertisement(this);
            logger.info("The Feature 'Advertisement' is enabled");
        } else {
            logger.info("The Feature 'Advertisement' is disabled");
        }
        if(config.getBoolean(ConfigKey.WELCOME_ENABLED)) {
            api.addTS3Listeners(new Welcome(this));
            logger.info("The Feature 'Welcome' is enabled");
        } else {
            logger.info("The Feature 'Welcome' is disabled");
        }
        if(config.getBoolean(ConfigKey.ANTIVPN_ENABLED)) {
            api.addTS3Listeners(new AntiVPN(this));
            logger.info("The Feature 'AntiVPN' is enabled");
        } else {
            logger.info("The Feature 'AntiVPN' is disabled");
        }
        if(config.getBoolean(ConfigKey.SUPPORT_ENABLED)) {
            api.addTS3Listeners(new SupportManagement(this));
            logger.info("The Feature 'SupportManagement' is enabled");
        } else {
            logger.info("The Feature 'SupportManagement' is disabled");
        }
        if(config.getBoolean(ConfigKey.AFK_ENABLED)) {
            new AFKManager(this);
            logger.info("The Feature 'AFKManager' is enabled");
        } else {
            logger.info("The Feature 'AFKManager' is disabled");
        }
        if(config.getBoolean(ConfigKey.COMMANDS_ENABLED)) {
            api.addTS3Listeners(new EssentialCommands(this));
            logger.info("The Feature 'EssentialCommands' is enabled");
        } else {
            logger.info("The Feature 'EssentialCommands' is disabled");
        }
    }

    public TS3Api getApi() {
        return api;
    }

    public ConfigManager getConfig() {
        return config;
    }
}
