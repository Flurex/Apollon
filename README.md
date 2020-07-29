# Apollon
Apollon is a TeamSpeak Query Bot with many features. It's written completely in Java.

## Getting started
###### Download
Download the <a href="https://github.com/Flurex/Apollon/releases/latest">latest release</a> and upload the jar file to your Server.

###### Start the Bot
Please ensure that you have Java installed. You can do this by using "java -version".
After checking if Java is installed, you can run the Bot by using "java -jar Apollon-1.0.jar"

###### Configuration
After you started the Bot for the first time, a file named "config.properties" should be created in the directory. Open this file and adjust the values that the Bot can successfully connect to the Server.
You can find an example on how to do this below.

## Features
1. **AntiVPN**: Checks if a Client uses a VPN and kicks them if they do.
2. **AFK-Manager**: Moves or Kicks a Client when idling for more than x minutes.
3. **Advertisement**: Sends a message to the whole Server every x minutes.
4. **Support-Manager**: Sends a message to specific groups when a Client enters a certain Channel and opens/closes a Support Channel with a command.
5. **Welcome**: Sends a message when a Client connects to the Server.
6. **EssentialCommands**: Adds a few essential Commands to the Bot.

All features can be enabled/disabled in the config.properties file.

## Configuration
The Apollon TeamSpeak³ Query Bot is nearly completely customizable by editing the config.properties.
The following example shows how to enter the right values for the Bot to connect to your server.
###### Example
```# Server Settings
# The host the bot should connect to
server.host=127.0.0.1
# The Port of the TeamSpeak Server
server.port=9987
 
# Query Settings
# The login credentials for the server query.
query.username=Apollon
query.password=password
# The Query Port of the Server.
query.port=10011
 
# Bot Settings
# The Nickname the Bot should have.
bot.nickname=Apollon
# This should be set to 'default' unless the IP Adress the bot is running on, is whitelisted in the query_ip_whitelist.txt of your TeamSpeak Server.
# If Apollon is running on the same machine as the TeamSpeak Server, use 127.0.0.1 as host. This IP is whitelisted by default.
bot.floodrate=default
```

## Getting Help
If you have any questions or need help setting up the Bot, you can always contact me through <a href="https://github.com/Flurex/Apollon/issues">Github Issues</a>.
