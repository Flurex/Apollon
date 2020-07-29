package me.flurex.apollon.features;

import com.github.theholywaffle.teamspeak3.TS3Api;
import com.github.theholywaffle.teamspeak3.api.event.ClientJoinEvent;
import com.github.theholywaffle.teamspeak3.api.event.TS3EventAdapter;
import com.github.theholywaffle.teamspeak3.api.wrapper.ClientInfo;
import me.flurex.apollon.Apollon;
import me.flurex.apollon.config.ConfigKey;
import me.flurex.apollon.config.ConfigManager;

public class Welcome extends TS3EventAdapter {

    private final TS3Api api;
    private final ConfigManager config;

    public Welcome(Apollon plugin) {
        this.api = plugin.getApi();
        this.config = plugin.getConfig();
    }

    @Override
    public void onClientJoin(ClientJoinEvent e) {
        final int clientId = e.getClientId();
        final ClientInfo clientInfo = api.getClientInfo(clientId);
        final int[] welcomeBypassGroups = config.getIntArray(ConfigKey.WELCOME_BYPASS_GROUPS);
        if(welcomeBypassGroups[0] != -1) {
            for(int welcomeBypassGroup : welcomeBypassGroups) {
                if(clientInfo.isInServerGroup(welcomeBypassGroup)) {
                    return;
                }
            }
        }
        String[] welcomeArray = config.get(ConfigKey.WELCOME_MESSAGE).split("%nm%");
        for(String message : welcomeArray) {
            api.sendPrivateMessage(clientInfo.getId(), message);
        }
    }
}
