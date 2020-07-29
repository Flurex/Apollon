package me.flurex.apollon.features;

import com.github.theholywaffle.teamspeak3.TS3Api;
import com.github.theholywaffle.teamspeak3.api.event.TS3EventAdapter;
import com.github.theholywaffle.teamspeak3.api.event.TextMessageEvent;
import com.github.theholywaffle.teamspeak3.api.wrapper.ClientInfo;
import me.flurex.apollon.Apollon;
import me.flurex.apollon.config.ConfigKey;
import me.flurex.apollon.config.ConfigManager;
import me.flurex.apollon.utils.Log;

public class EssentialCommands extends TS3EventAdapter {

    private final TS3Api api;
    private final ConfigManager config;
    private final Log logger;

    public EssentialCommands(Apollon plugin) {
        this.api = plugin.getApi();
        this.config = plugin.getConfig();
        this.logger = new Log();
    }

    @Override
    public void onTextMessage(TextMessageEvent e) {
        ClientInfo clientInfo = api.getClientInfo(e.getInvokerId());
        if(config.getBoolean(ConfigKey.COMMANDS_HELP_ENABLED)) {
            if (e.getMessage().equalsIgnoreCase("!help")) {
                String[] helpArray = config.get(ConfigKey.COMMANDS_HELP_MESSAGE).split("%nm%");
                for(String message : helpArray) {
                    api.sendPrivateMessage(clientInfo.getId(), message);
                }
            }
        }
    }
}
