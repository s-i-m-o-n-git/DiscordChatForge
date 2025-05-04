package com.sim.discordchat;

import net.minecraftforge.event.CommandEvent;
import net.minecraft.command.ICommandSender;

public class ServerStatusNotifier {

    public static void handleServerStarting() {
        WebHookSender.sendMessage("Serveur", "<:plus:1368294153586343936> **| Le serveur est en train de démarrer...**");
    }

    public static void handleServerStarted() {
        WebHookSender.sendMessage("Serveur", "<:plus:1368294153586343936> **| Le serveur est maintenant en ligne.**");
    }

    public static void handleServerStopping() {
        WebHookSender.sendMessage("Serveur", "<:moins:1368294154907685025> **| Le serveur est en train de s’éteindre...**");
    }

    public static void handleServerStopped() {
        WebHookSender.sendMessage("Serveur", "<:moins:1368294154907685025> **| Le serveur est éteint.**");
    }

    public static void handleReloadCommand(CommandEvent event) {
        if (event.getCommand().getName().equalsIgnoreCase("reload")) {
            ICommandSender sender = event.getSender();
            WebHookSender.sendMessage("Serveur", ":arrows_counterclockwise: **| Le serveur a été rechargé par " + sender.getName() + "**");
        }
    }
}
