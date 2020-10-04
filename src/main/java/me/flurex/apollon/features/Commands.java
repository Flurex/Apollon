package me.flurex.apollon.features;

import com.github.theholywaffle.teamspeak3.TS3Api;
import com.github.theholywaffle.teamspeak3.api.event.TS3EventAdapter;
import com.github.theholywaffle.teamspeak3.api.event.TextMessageEvent;
import com.github.theholywaffle.teamspeak3.api.wrapper.Channel;
import com.github.theholywaffle.teamspeak3.api.wrapper.Client;
import com.github.theholywaffle.teamspeak3.api.wrapper.ClientInfo;
import me.flurex.apollon.Apollon;
import me.flurex.apollon.config.ConfigKey;
import me.flurex.apollon.config.ConfigManager;
import me.flurex.apollon.methods.Methods;
import me.flurex.apollon.utils.Log;

import java.util.List;

public class Commands extends TS3EventAdapter {

    private final TS3Api api;
    private final ConfigManager config;
    private final Log logger;
    private final Methods methods;

    public Commands(Apollon plugin) {
        this.api = plugin.getApi();
        this.config = plugin.getConfig();
        this.logger = plugin.getLogger();
        this.methods = plugin.getMethods();
    }

    @Override
    public void onTextMessage(TextMessageEvent e) {
        ClientInfo clientInfo = api.getClientInfo(e.getInvokerId());
        if(e.getMessage().equalsIgnoreCase("!version")) {
            api.sendPrivateMessage(clientInfo.getId(), "TeamSpeak³ QueryBot 'Apollon' v1.1");
        } else if(e.getMessage().equalsIgnoreCase("!help")) {
            if(config.getBoolean(ConfigKey.COMMANDS_HELP_ENABLED)) {
                String[] helpArray = config.get(ConfigKey.COMMANDS_HELP_MESSAGE).split("%nm%");
                for(String message : helpArray) {
                    api.sendPrivateMessage(clientInfo.getId(), message);
                }
            }
        } else if(e.getMessage().equalsIgnoreCase("!hello")) {
            if(config.getBoolean(ConfigKey.COMMANDS_HELLO_ENABLED)) {
                api.sendPrivateMessage(clientInfo.getId(), config.get(ConfigKey.COMMANDS_HELLO_MESSAGE));
            }
        } else if(e.getMessage().equalsIgnoreCase("!kickme")) {
            if(config.getBoolean(ConfigKey.COMMANDS_KICKME_ENABLED)) {
                api.kickClientFromServer(config.get(ConfigKey.COMMANDS_KICKME_MESSAGE), clientInfo.getId());
            }
        }
    }

}
