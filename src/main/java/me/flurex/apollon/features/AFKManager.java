package me.flurex.apollon.features;

import com.github.theholywaffle.teamspeak3.TS3Api;
import com.github.theholywaffle.teamspeak3.api.wrapper.Client;
import me.flurex.apollon.Apollon;
import me.flurex.apollon.config.ConfigKey;
import me.flurex.apollon.config.ConfigManager;
import me.flurex.apollon.utils.Log;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class AFKManager implements Runnable {

    private final TS3Api api;
    private final ConfigManager config;
    private final Log logger;
    private final ScheduledExecutorService executor;

    public AFKManager(Apollon plugin) {
        this.api = plugin.getApi();
        this.config = plugin.getConfig();
        this.logger = new Log();
        this.executor = new ScheduledThreadPoolExecutor(1);

        executor.schedule(this, config.getInt(ConfigKey.AFK_CHECK_TIME), TimeUnit.SECONDS);
    }

    @Override
    public void run() {
        for(Client all : api.getClients()) {
            if(all.getIdleTime() >= (config.getLong(ConfigKey.AFK_TIME) * 1000)) {
                for(int bypassGroup : config.getIntArray(ConfigKey.AFK_BYPASS_GROUPS)) {
                    if(!all.isInServerGroup(bypassGroup)) {
                        for(int bypassChannel : config.getIntArray(ConfigKey.AFK_BYPASS_CHANNEL)) {
                            if(all.getChannelId() != bypassChannel) {
                                if(config.get(ConfigKey.AFK_MODE).equalsIgnoreCase("move")) {
                                    if(all.getChannelId() != config.getInt(ConfigKey.AFK_MOVE_CHANNEL)) {
                                        if(!all.isServerQueryClient()) {
                                            api.moveClient(all.getId(), config.getInt(ConfigKey.AFK_MOVE_CHANNEL));
                                            logger.info("Client '" + all.getNickname() + "' with ID '" + all.getId() + "' was moved to the AFK Channel for idling too long.");
                                            if (config.getBoolean(ConfigKey.AFK_MOVE_NOTIFY)) {
                                                if (config.get(ConfigKey.AFK_MOVE_NOTIFY_TYPE).equalsIgnoreCase("chat")) {
                                                    api.sendPrivateMessage(all.getId(), config.get(ConfigKey.AFK_MOVE_NOTIFY_MESSAGE));
                                                } else if (config.get(ConfigKey.AFK_MOVE_NOTIFY_TYPE).equalsIgnoreCase("poke")) {
                                                    api.pokeClient(all.getId(), config.get(ConfigKey.AFK_MOVE_NOTIFY_MESSAGE));
                                                } else {
                                                    logger.error("AFK Notify Type '" + config.get(ConfigKey.AFK_MOVE_NOTIFY_TYPE) + "' doesn't exist.");
                                                }
                                            }
                                        }
                                    }
                                } else if(config.get(ConfigKey.AFK_MODE).equalsIgnoreCase("kick")) {
                                    if(!all.isServerQueryClient()) {
                                        api.kickClientFromServer(config.get(ConfigKey.AFK_KICK_MESSAGE), all.getId());
                                        logger.info("Client '" + all.getNickname() + "' with ID '" + all.getId() + "' was kicked from the Server for idling too long.");
                                    }
                                } else {
                                    logger.error("AFK Mode '" + config.get(ConfigKey.AFK_MODE) + "' doesn't exist.");
                                }
                            }
                        }
                    }
                }
            }
        }
        executor.schedule(this, config.getInt(ConfigKey.ADVERTISEMENT_DELAY), TimeUnit.SECONDS);
    }

}
