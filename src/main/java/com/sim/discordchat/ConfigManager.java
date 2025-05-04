package com.sim.discordchat;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

import java.io.File;

public class ConfigManager {

    public static String webhook;
    public static String token;
    public static String channelID;
    public static String serverID;

    private static Configuration config;

    public static void init(File configFile) {
        config = new Configuration(configFile);

        try {
            config.load();

            webhook   = config.get("Discord", "Webhook", "").getString();
            token     = config.get("Discord", "Token", "").getString();
            channelID = config.get("Discord", "ChannelID", "").getString();
            serverID  = config.get("Discord", "ServerID", "").getString();

        } catch (Exception e) {
            System.err.println("Erreur lors du chargement de la configuration DiscordChat :");
            e.printStackTrace();
        } finally {
            if (config.hasChanged()) {
                config.save();
            }
        }
    }

    public static void setProperty(String category, String key, String value) {
        if (config == null) {
            System.err.println("Configuration non initialisée.");
            return;
        }

        Property prop = config.get(category, key, "");
        prop.set(value);
        config.save();
    }
}
