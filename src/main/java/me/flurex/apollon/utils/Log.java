package me.flurex.apollon.utils;

import me.flurex.apollon.Apollon;
import me.flurex.apollon.methods.Methods;

public class Log {

    private final Apollon plugin;

    public Log(Apollon plugin) {
        this.plugin = plugin;
    }

    public void info(String message) {
        System.out.println(plugin.getMethods().getDate() + " [INFORMATION] " + message);
    }

    public void warning(String message) {
        System.out.println(plugin.getMethods().getDate() + " [WARNING] " + message);
    }

    public void error(String message) {
        System.out.println(plugin.getMethods().getDate() + " [ERROR] " + message);
    }

}
