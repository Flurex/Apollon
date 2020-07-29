package me.flurex.apollon.utils;

import me.flurex.apollon.methods.Methods;

public class Log {

    public void info(String message) {
        System.out.println(new Methods().getDate() + " [INFORMATION] " + message);
    }

    public void warning(String message) {
        System.out.println(new Methods().getDate() + " [WARNING] " + message);
    }

    public void error(String message) {
        System.out.println(new Methods().getDate() + " [ERROR] " + message);
    }

}
