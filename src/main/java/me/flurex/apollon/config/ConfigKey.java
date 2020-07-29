package me.flurex.apollon.config;

public enum ConfigKey {

    SERVER_HOST("server.host"),
    SERVER_PORT("server.port"),
    QUERY_USERNAME("query.username"),
    QUERY_PASSWORD("query.password"),
    QUERY_PORT("query.port"),
    BOT_NICKNAME("bot.nickname"),
    BOT_FLOODRATE("bot.floodrate"),
    ADVERTISEMENT_ENABLED("advertisement.enabled"),
    ADVERTISEMENT_DELAY("advertisement.delay"),
    ADVERTISEMENT_MESSAGE("advertisement.message"),
    AFK_ENABLED("afk.enabled"),
    AFK_TIME("afk.time"),
    AFK_MODE("afk.mode"),
    AFK_MOVE_CHANNEL("afk.move-channel"),
    AFK_MOVE_NOTIFY("afk.move-notify"),
    AFK_MOVE_NOTIFY_TYPE("afk.move-notify-type"),
    AFK_MOVE_NOTIFY_MESSAGE("afk.move-notify-message"),
    AFK_KICK_MESSAGE("afk.kick-message"),
    AFK_BYPASS_GROUPS("afk.bypass-groups"),
    AFK_BYPASS_CHANNEL("afk.bypass-channel"),
    AFK_CHECK_TIME("afk.check-time"),
    ANTIVPN_ENABLED("antivpn.enabled"),
    ANTIVPN_MODE("antivpn.mode"),
    ANTIVPN_CHECKS("antivpn.check"),
    ANTIVPN_KICK_MESSAGE("antivpn.kick-message"),
    ANTIVPN_BAN_MESSAGE("antivpn.ban-message"),
    ANTIVPN_BAN_DURATION("antivpn.ban-duration"),
    ANTIVPN_BYPASS_GROUPS("antivpn.bypass-groups"),
    WELCOME_ENABLED("welcome.enabled"),
    WELCOME_BYPASS_GROUPS("welcome.bypass-groups"),
    WELCOME_MESSAGE("welcome.message"),
    SUPPORT_ENABLED("support.enabled"),
    SUPPORT_CHANNEL("support.channel"),
    SUPPORT_NOTIFY_TYPE("support.notify-type"),
    SUPPORT_NOTIFY_GROUPS("support.notify-groups"),
    SUPPORT_CHANNEL_MANAGEMENT_GROUPS("support.channel-management-groups"),
    SUPPORT_COMMAND_OPEN("support.command-open"),
    SUPPORT_COMMAND_CLOSE("support.command-close"),
    SUPPORT_CHANNEL_NAME_OPEN("support.channel-name-open"),
    SUPPORT_CHANNEL_NAME_CLOSED("support.channel-name-closed"),
    SUPPORT_CHANNEL_MAXCLIENTS("support.channel-maxclients"),
    COMMANDS_ENABLED("commands.enabled"),
    COMMANDS_HELP_ENABLED("commands.help-enabled"),
    COMMANDS_HELP_MESSAGE("commands.help-message");

    private String key;

    ConfigKey(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
