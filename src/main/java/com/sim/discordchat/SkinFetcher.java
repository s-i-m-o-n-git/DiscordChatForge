package com.sim.discordchat;
// NOT USE POUR LINSTANT
public class SkinFetcher {
    public static String getAvatarUrl(String username) {
        if (username == null || username.isEmpty()) return "";
        return "https://minotar.net/avatar/" + username + "/256";
    }
}
