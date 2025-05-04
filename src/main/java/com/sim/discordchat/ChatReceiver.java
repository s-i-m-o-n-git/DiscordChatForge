package com.sim.discordchat;

import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class ChatReceiver {

    @SubscribeEvent
    public static void onServerChat(ServerChatEvent event) {
        String username = event.getPlayer().getName();
        String message = event.getMessage();
        System.out.println("[ChatReceiver] " + username + " a dit : " + message);
        WebHookSender.sendMessage(username, message); // on utilise le nom du joueur ici
    }
}
