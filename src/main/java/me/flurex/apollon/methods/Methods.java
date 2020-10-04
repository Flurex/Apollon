package me.flurex.apollon.methods;

import com.github.theholywaffle.teamspeak3.TS3Api;
import com.github.theholywaffle.teamspeak3.api.wrapper.Channel;
import com.github.theholywaffle.teamspeak3.api.wrapper.ClientInfo;
import me.flurex.apollon.Apollon;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Methods {

    private final TS3Api api;

    public Methods(Apollon plugin) {
        this.api = plugin.getApi();
    }

    public String getDate() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
        return simpleDateFormat.format(new Date());
    }

    public String getJarPath() {
        try {
            return (new File(Apollon.class.getProtectionDomain().getCodeSource().getLocation().toURI())).getParentFile().getPath();
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return "ERROR";
        }
    }

    public boolean channelExists(String name) {
        for(Channel channel : api.getChannels()) {
            if(channel.getName().equalsIgnoreCase(name)) {
                return true;
            }
        }
        return false;
    }

    public boolean hasVPN(ClientInfo ci) {
        String address = ci.getIp().replace("/", "").split(":")[0];
        String url = "https://api.iptoasn.com/v1/as/ip/" + address;
        try {
            HttpURLConnection connection = (HttpURLConnection)(new URL(url)).openConnection();
            connection.setRequestProperty("User-Agent",
                    "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line = bufferedReader.readLine();
            while (line != null) {
                try {
                    line = bufferedReader.readLine();
                    if (line != null && (
                            line.contains("M247") || line.contains("NFORCE") || line.contains("WORLDSTREAM") ||
                                    line.contains("AS58065") || line.contains("CDN77") ||
                                    line.contains("CLOUVIDER London") || line.contains("GCORE") ||
                                    line.contains("AS208673") || line.contains("17639") ||
                                    line.contains("VIVIDHOSTING") || line.contains("INETLTD") ||
                                    line.contains("Data Miners") || line.contains("AS58065")))
                        return true;
                } catch (NullPointerException e) {
                    return false;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

}
