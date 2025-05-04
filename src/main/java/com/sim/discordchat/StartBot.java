package com.sim.discordchat;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;

import java.util.EnumSet;

public class StartBot {

    private static JDA bot;

    public static void start() {
        if (ConfigManager.token == null || ConfigManager.token.isEmpty()) {
            System.err.println("[DiscordBot] Le token est vide. Bot non lancé.");
            return;
        }

        try {
            bot = JDABuilder.createDefault(ConfigManager.token)
                    .enableIntents(EnumSet.of(
                            GatewayIntent.GUILD_MESSAGES,
                            GatewayIntent.MESSAGE_CONTENT,
                            GatewayIntent.GUILD_MEMBERS // nécessaire pour getEffectiveName()
                    ))
                    .setActivity(Activity.playing("Minecraft 1.12.2"))
                    .addEventListeners(new MessageListener())
                    .build();

            System.out.println("[DiscordBot] Bot démarré avec succès.");

        } catch (Exception e) {
            System.err.println("[DiscordBot] Échec de la connexion avec le token !");
            e.printStackTrace();
        }
    }

    public static void stop() {
        if (bot != null) {
            bot.shutdownNow();
            bot = null;
            System.out.println("[DiscordBot] Bot arrêté.");
        }
    }

    public static void reload() {
        System.out.println("[DiscordBot] Redémarrage du bot...");
        stop();
        start();
    }

    public static class MessageListener extends ListenerAdapter {
        @Override
        public void onMessageReceived(MessageReceivedEvent event) {
            if (event.getAuthor().isBot()) return;

            if (!event.getGuild().getId().equals(ConfigManager.serverID)) return;
            if (!event.getChannel().getId().equals(ConfigManager.channelID)) return;

            String content = event.getMessage().getContentDisplay();

            // Commande Discord !tps
            if (content.equalsIgnoreCase("!tps")) {
                double tps = getTPS();
                event.getChannel().sendMessage("TPS actuel du serveur Minecraft: `" + String.format("%.2f", tps) + "`").queue();
                return;
            }

            // Nom visible (pseudo dans le serveur) sinon nom d'utilisateur
            Member member = event.getMember();
            String name = member != null ? member.getEffectiveName() : event.getAuthor().getName();

            String finalMessage = "§9[DISCORD] §f" + name + ": " + content;

            MinecraftServer server = net.minecraftforge.fml.common.FMLCommonHandler.instance().getMinecraftServerInstance();
            if (server != null) {
                server.getPlayerList().getPlayers().forEach(player -> {
                    player.sendMessage(new TextComponentString(finalMessage));
                });
            }
        }

        private double getTPS() {
            MinecraftServer server = net.minecraftforge.fml.common.FMLCommonHandler.instance().getMinecraftServerInstance();
            if (server != null) {
                long[] times = server.tickTimeArray;
                if (times == null || times.length == 0) return 0;

                long total = 0;
                for (long time : times) total += time;
                double averageTickTime = total / (double) times.length / 1_000_000.0; // en ms

                return Math.min(1000.0 / averageTickTime, 20.0); // capé à 20 TPS
            }
            return 0;
        }
    }

    public static JDA getBot() {
        return bot;
    }
}
