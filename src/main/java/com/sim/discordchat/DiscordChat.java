package com.sim.discordchat;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.CommandEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.*;

import java.io.File;

@Mod(modid = DiscordChat.MODID, name = "Chat Discord", version = DiscordChat.VERSION)
public class DiscordChat {

    public static final String MODID = "chatdiscord";
    public static final String VERSION = "1.12.2a";

    @Mod.Instance(MODID)
    public static DiscordChat instance;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        // Chargement de la configuration depuis le fichier
        ConfigManager.init(new File(event.getModConfigurationDirectory(), "discordchat.cfg"));

        // Inscription des événements Forge
        MinecraftForge.EVENT_BUS.register(this);

        // Démarrage du bot Discord
        StartBot.start();
    }

    @EventHandler
    public void onServerStarting(FMLServerStartingEvent event) {
        // Enregistrement des commandes serveurs
        event.registerServerCommand(new CommandChatDiscord());

        // Notifie le début du démarrage du serveur
        ServerStatusNotifier.handleServerStarting();
    }

    @EventHandler
    public void onServerStarted(FMLServerStartedEvent event) {
        // Serveur complètement lancé
        ServerStatusNotifier.handleServerStarted();
    }

    @EventHandler
    public void onServerStopping(FMLServerStoppingEvent event) {
        // Serveur en train de s’arrêter
        ServerStatusNotifier.handleServerStopping();
    }

    @EventHandler
    public void onServerStopped(FMLServerStoppedEvent event) {
        // Serveur complètement arrêté
        ServerStatusNotifier.handleServerStopped();
    }

    // Événement de commande personnalisé si besoin
    @net.minecraftforge.fml.common.eventhandler.SubscribeEvent
    public void onCommand(CommandEvent event) {
        ServerStatusNotifier.handleReloadCommand(event);
    }

    // Utilitaire pour préfixer avec le modid
    public static String prependModID(String name) {
        return MODID + ":" + name;
    }
}
