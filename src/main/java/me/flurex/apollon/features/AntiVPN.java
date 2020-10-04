package me.flurex.apollon.features;

import com.github.theholywaffle.teamspeak3.TS3Api;
import com.github.theholywaffle.teamspeak3.api.event.ClientJoinEvent;
import com.github.theholywaffle.teamspeak3.api.event.ClientMovedEvent;
import com.github.theholywaffle.teamspeak3.api.event.TS3EventAdapter;
import com.github.theholywaffle.teamspeak3.api.wrapper.ClientInfo;
import me.flurex.apollon.Apollon;
import me.flurex.apollon.config.ConfigKey;
import me.flurex.apollon.config.ConfigManager;
import me.flurex.apollon.methods.Methods;
import me.flurex.apollon.utils.Log;

public class AntiVPN extends TS3EventAdapter {

    private final TS3Api api;
    private final ConfigManager config;
    private final Log logger;
    private final Methods methods;

    public AntiVPN(Apollon plugin) {
        this.api = plugin.getApi();
        this.config = plugin.getConfig();
        this.logger = plugin.getLogger();
        this.methods = plugin.getMethods();
    }

    @Override
    public void onClientJoin(ClientJoinEvent e) {
        final String[] checks = config.getStringArray(ConfigKey.ANTIVPN_CHECKS);
        for(String check : checks) {
            if(check.equalsIgnoreCase("join")) {
                checkVPN(api.getClientInfo(e.getClientId()));
            }
        }
    }

    @Override
    public void onClientMoved(ClientMovedEvent e) {
        final String[] checks = config.getStringArray(ConfigKey.ANTIVPN_CHECKS);
        for(String check : checks) {
            if(check.equalsIgnoreCase("move")) {
                checkVPN(api.getClientInfo(e.getClientId()));
            }
        }
    }

    private void checkVPN(ClientInfo clientInfo) {
        final int[] antiVPNBypassGroups = config.getIntArray(ConfigKey.ANTIVPN_BYPASS_GROUPS);
        if(antiVPNBypassGroups[0] != -1) {
            for(int antiVPNBypassGroup : antiVPNBypassGroups) {
                if(!clientInfo.isInServerGroup(antiVPNBypassGroup)) {
                    return;
                }
            }
        }
        if(methods.hasVPN(clientInfo)) {
            if(config.get(ConfigKey.ANTIVPN_MODE).equalsIgnoreCase("kick")) {
                api.kickClientFromServer(config.get(ConfigKey.ANTIVPN_KICK_MESSAGE), clientInfo.getId());
                logger.info("The Client '" + clientInfo.getNickname() + "' got kicked for using a VPN.");
            } else if(config.get(ConfigKey.ANTIVPN_MODE).equalsIgnoreCase("ban")) {
                api.banClient(clientInfo.getId(), config.getLong(ConfigKey.ANTIVPN_BAN_DURATION), config.get(ConfigKey.ANTIVPN_BAN_MESSAGE));
                logger.info("The Client '" + clientInfo.getNickname() + "' got banned for " + config.get(ConfigKey.ANTIVPN_BAN_DURATION) + " seconds because he was using a VPN.");
            } else {
                logger.error("The AntiVPN Mode '" + config.get(ConfigKey.ANTIVPN_MODE) + "' isn't an option.");
                logger.error("Please change it to either 'kick' or 'ban'.");
                logger.error("The Client '" + clientInfo.getNickname() + "' couldn't be punished.");
            }
        }
    }

}
