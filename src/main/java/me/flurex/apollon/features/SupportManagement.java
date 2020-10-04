package me.flurex.apollon.features;

import com.github.theholywaffle.teamspeak3.TS3Api;
import com.github.theholywaffle.teamspeak3.api.ChannelProperty;
import com.github.theholywaffle.teamspeak3.api.event.ClientMovedEvent;
import com.github.theholywaffle.teamspeak3.api.event.TS3EventAdapter;
import com.github.theholywaffle.teamspeak3.api.event.TextMessageEvent;
import com.github.theholywaffle.teamspeak3.api.wrapper.Client;
import com.github.theholywaffle.teamspeak3.api.wrapper.ClientInfo;
import me.flurex.apollon.Apollon;
import me.flurex.apollon.config.ConfigKey;
import me.flurex.apollon.config.ConfigManager;
import me.flurex.apollon.utils.Log;

import java.util.HashMap;
import java.util.Map;

public class SupportManagement extends TS3EventAdapter {

    private final TS3Api api;
    private final ConfigManager config;
    private final Log logger;

    public SupportManagement(Apollon plugin) {
        this.api = plugin.getApi();
        this.config = plugin.getConfig();
        this.logger = plugin.getLogger();
    }

    @Override
    public void onClientMoved(ClientMovedEvent e) {
        ClientInfo clientInfo = api.getClientInfo(e.getClientId());
        if (e.getTargetChannelId() == config.getInt(ConfigKey.SUPPORT_CHANNEL)) {
            boolean online = false;
            for (Client all : api.getClients()) {
                for(int group : config.getIntArray(ConfigKey.SUPPORT_NOTIFY_GROUPS)) {
                    if (all.isInServerGroup(group)) {
                        online = true;
                        if(config.get(ConfigKey.SUPPORT_NOTIFY_TYPE).equalsIgnoreCase("chat")) {
                            api.sendPrivateMessage(all.getId(), "[URL=client://" + clientInfo.getChannelId() + "/" + clientInfo.getUniqueIdentifier() + "~" + clientInfo.getNickname() + "]" + clientInfo.getNickname() + "[/URL] needs Support!");
                        } else if(config.get(ConfigKey.SUPPORT_NOTIFY_TYPE).equalsIgnoreCase("poke")) {
                            api.pokeClient(all.getId(), clientInfo.getNickname() + " needs Support!");
                        } else {
                            logger.error("Support Notify Type '" + config.get(ConfigKey.SUPPORT_NOTIFY_TYPE) + "' doesn't exist.");
                        }
                    }
                }
            }
            if (online) {
                api.sendPrivateMessage(clientInfo.getId(), "Welcome [B]" + clientInfo.getNickname() + "[/B]! A Supporter will be with you shortly.");
            } else {
                api.sendPrivateMessage(clientInfo.getId(), "There are currently no Supporters online. Please try again later.");
            }
        }
    }

    @Override
    public void onTextMessage(TextMessageEvent e) {
        Map<ChannelProperty, String> property = new HashMap<>();
        ClientInfo clientInfo = api.getClientInfo(e.getInvokerId());
        if(e.getMessage().equalsIgnoreCase(config.get(ConfigKey.SUPPORT_COMMAND_OPEN))) {
            for(int group : config.getIntArray(ConfigKey.SUPPORT_CHANNEL_MANAGEMENT_GROUPS)) {
                if(clientInfo.isInServerGroup(group)) {
                    if(api.getChannelInfo(config.getInt(ConfigKey.SUPPORT_CHANNEL)).getName().equals(config.get(ConfigKey.SUPPORT_CHANNEL_NAME_CLOSED))) {
                        property.put(ChannelProperty.CHANNEL_NAME, config.get(ConfigKey.SUPPORT_CHANNEL_NAME_OPEN));
                        property.put(ChannelProperty.CHANNEL_MAXCLIENTS, config.get(ConfigKey.SUPPORT_CHANNEL_MAXCLIENTS));
                        property.put(ChannelProperty.CHANNEL_FLAG_MAXCLIENTS_UNLIMITED, "1");
                        api.editChannel(config.getInt(ConfigKey.SUPPORT_CHANNEL), property);
                        property.clear();
                        logger.info("Support Channel with ID '" + config.get(ConfigKey.SUPPORT_CHANNEL) + "' was opened.");
                        api.sendPrivateMessage(clientInfo.getId(), "The Support Channel was successfully opened.");
                    } else if(api.getChannelInfo(config.getInt(ConfigKey.SUPPORT_CHANNEL)).getName().equals(config.get(ConfigKey.SUPPORT_CHANNEL_NAME_OPEN))) {
                        api.sendPrivateMessage(clientInfo.getId(), "The Support Channel is alredy opened.");
                    } else {
                        property.put(ChannelProperty.CHANNEL_NAME, config.get(ConfigKey.SUPPORT_CHANNEL_NAME_CLOSED));
                        property.put(ChannelProperty.CHANNEL_MAXCLIENTS, "0");
                        property.put(ChannelProperty.CHANNEL_FLAG_MAXCLIENTS_UNLIMITED, "0");
                        api.editChannel(config.getInt(ConfigKey.SUPPORT_CHANNEL), property);
                        property.clear();
                        logger.error("Invalid Support Channel Name!");
                        logger.error("The Channel was automatically closed. To open it again, send a message to the Bot with the content '!open'");
                        api.sendPrivateMessage(clientInfo.getId(), "Invalid Channel Name. The Support Channel was automatically closed.");
                    }
                }
            }
        } else if(e.getMessage().equalsIgnoreCase(config.get(ConfigKey.SUPPORT_COMMAND_CLOSE))) {
            for(int group : config.getIntArray(ConfigKey.SUPPORT_CHANNEL_MANAGEMENT_GROUPS)) {
                if(clientInfo.isInServerGroup(group)) {
                    if(api.getChannelInfo(config.getInt(ConfigKey.SUPPORT_CHANNEL)).getName().equals(config.get(ConfigKey.SUPPORT_CHANNEL_NAME_OPEN))) {
                        property.put(ChannelProperty.CHANNEL_NAME, config.get(ConfigKey.SUPPORT_CHANNEL_NAME_CLOSED));
                        property.put(ChannelProperty.CHANNEL_MAXCLIENTS, "0");
                        property.put(ChannelProperty.CHANNEL_FLAG_MAXCLIENTS_UNLIMITED, "0");
                        api.editChannel(config.getInt(ConfigKey.SUPPORT_CHANNEL), property);
                        property.clear();
                        logger.info("Support Channel with ID '" + config.get(ConfigKey.SUPPORT_CHANNEL) + "' was closed.");
                        api.sendPrivateMessage(clientInfo.getId(), "The Support Channel was successfully closed.");
                    } else if(api.getChannelInfo(config.getInt(ConfigKey.SUPPORT_CHANNEL)).getName().equals(config.get(ConfigKey.SUPPORT_CHANNEL_NAME_CLOSED))) {
                        api.sendPrivateMessage(clientInfo.getId(), "The Support Channel is alredy closed.");
                    } else {
                        property.put(ChannelProperty.CHANNEL_NAME, config.get(ConfigKey.SUPPORT_CHANNEL_NAME_CLOSED));
                        property.put(ChannelProperty.CHANNEL_MAXCLIENTS, "0");
                        property.put(ChannelProperty.CHANNEL_FLAG_MAXCLIENTS_UNLIMITED, "0");
                        api.editChannel(config.getInt(ConfigKey.SUPPORT_CHANNEL), property);
                        property.clear();
                        logger.error("Invalid Support Channel Name!");
                        logger.error("The Channel was automatically closed. To open it again, send a message to the Bot with the content '!open'");
                        api.sendPrivateMessage(clientInfo.getId(), "Invalid Channel Name. The Support Channel was automatically closed.");
                    }
                }
            }
        }
    }
}
