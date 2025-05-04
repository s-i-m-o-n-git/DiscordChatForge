package com.sim.discordchat;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;

import java.io.File;

public class CommandChatDiscord extends CommandBase {

    private static boolean maintenanceMode = false;

    @Override
    public String getName() {
        return "chatdiscord";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "/chatdiscord <reloadconfig|showconfig|maintenancemode|setwebhook|settoken|setchannelid|setserverid>";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) {
        if (!sender.canUseCommand(getRequiredPermissionLevel(), getName())) {
            sender.sendMessage(new TextComponentString("§c[ChatDiscord] Vous devez être OP pour utiliser cette commande."));
            return;
        }

        if (args.length == 0) {
            sender.sendMessage(new TextComponentString("§e[ChatDiscord] Commandes disponibles :"));
            sender.sendMessage(new TextComponentString("§6/chatdiscord reloadconfig §7- Recharge la configuration depuis le fichier."));
            sender.sendMessage(new TextComponentString("§6/chatdiscord showconfig §7- Affiche la configuration actuelle."));
            sender.sendMessage(new TextComponentString("§6/chatdiscord maintenancemode §7- Active ou désactive le mode maintenance."));
            sender.sendMessage(new TextComponentString("§6/chatdiscord setwebhook <url> §7- Définit le webhook Discord."));
            sender.sendMessage(new TextComponentString("§6/chatdiscord settoken <token> §7- Définit le token du bot Discord."));
            sender.sendMessage(new TextComponentString("§6/chatdiscord setchannelid <id> §7- Définit l'ID du canal Discord."));
            sender.sendMessage(new TextComponentString("§6/chatdiscord setserverid <id> §7- Définit l'ID du serveur Discord."));
            return;
        }

        switch (args[0].toLowerCase()) {
            case "reloadconfig":
                ConfigManager.init(new File("config/discordchat.cfg"));
                StartBot.reload();
                sender.sendMessage(new TextComponentString("§a[ChatDiscord] Configuration et bot Discord rechargés."));
                break;
            case "showconfig":
                sender.sendMessage(new TextComponentString("§bWebhook: §f" + ConfigManager.webhook));
                sender.sendMessage(new TextComponentString("§bToken: §f" + ConfigManager.token));
                sender.sendMessage(new TextComponentString("§bChannelID: §f" + ConfigManager.channelID));
                sender.sendMessage(new TextComponentString("§bServerID: §f" + ConfigManager.serverID));
                break;

            case "maintenancemode":
                maintenanceMode = !maintenanceMode;
                sender.sendMessage(new TextComponentString("§e[ChatDiscord] Mode maintenance: §6" + (maintenanceMode ? "ACTIVÉ" : "DÉSACTIVÉ")));
                break;

            case "setwebhook":
                if (args.length >= 2) {
                    ConfigManager.setProperty("Discord", "Webhook", args[1]);
                    ConfigManager.webhook = args[1];
                    sender.sendMessage(new TextComponentString("§aWebhook mis à jour."));
                } else {
                    sender.sendMessage(new TextComponentString("§cUtilisation: /chatdiscord setwebhook <url>"));
                }
                break;

            case "settoken":
                if (args.length >= 2) {
                    ConfigManager.setProperty("Discord", "Token", args[1]);
                    ConfigManager.token = args[1];
                    sender.sendMessage(new TextComponentString("§aToken mis à jour."));
                } else {
                    sender.sendMessage(new TextComponentString("§cUtilisation: /chatdiscord settoken <token>"));
                }
                break;

            case "setchannelid":
                if (args.length >= 2) {
                    ConfigManager.setProperty("Discord", "ChannelID", args[1]);
                    ConfigManager.channelID = args[1];
                    sender.sendMessage(new TextComponentString("§aChannelID mis à jour."));
                } else {
                    sender.sendMessage(new TextComponentString("§cUtilisation: /chatdiscord setchannelid <id>"));
                }
                break;

            case "setserverid":
                if (args.length >= 2) {
                    ConfigManager.setProperty("Discord", "ServerID", args[1]);
                    ConfigManager.serverID = args[1];
                    sender.sendMessage(new TextComponentString("§aServerID mis à jour."));
                } else {
                    sender.sendMessage(new TextComponentString("§cUtilisation: /chatdiscord setserverid <id>"));
                }
                break;

            default:
                sender.sendMessage(new TextComponentString("§cCommande inconnue. " + getUsage(sender)));
        }
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 4; // OP uniquement
    }
}
