package me.flurex.apollon.features;

import com.github.theholywaffle.teamspeak3.TS3Api;
import me.flurex.apollon.Apollon;
import me.flurex.apollon.config.ConfigKey;
import me.flurex.apollon.config.ConfigManager;
import me.flurex.apollon.utils.Log;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Advertisement implements Runnable {

    private final TS3Api api;
    private final ConfigManager config;
    private final Log logger;
    private final ScheduledExecutorService executor;

    public Advertisement(Apollon plugin) {
        this.api = plugin.getApi();
        this.config = plugin.getConfig();
        this.logger = new Log();
        this.executor = new ScheduledThreadPoolExecutor(1);

        executor.schedule(this, config.getLong(ConfigKey.ADVERTISEMENT_DELAY), TimeUnit.MINUTES);
    }

    @Override
    public void run() {
        api.sendServerMessage(config.get(ConfigKey.ADVERTISEMENT_MESSAGE));
        executor.schedule(this, config.getLong(ConfigKey.ADVERTISEMENT_DELAY), TimeUnit.MINUTES);
    }
}
