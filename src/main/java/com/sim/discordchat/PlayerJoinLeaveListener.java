package com.sim.discordchat;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraft.entity.player.EntityPlayerMP;

@Mod.EventBusSubscriber
public class PlayerJoinLeaveListener {

    @SubscribeEvent
    public static void onPlayerJoin(PlayerEvent.PlayerLoggedInEvent event) {
        if (event.player instanceof EntityPlayerMP) {
            String playerName = event.player.getName();
            WebHookSender.sendMessage(playerName, "<:plus:1368294153586343936> **| " + playerName + " a rejoint le serveur**");
        }
    }

    @SubscribeEvent
    public static void onPlayerLeave(PlayerEvent.PlayerLoggedOutEvent event) {
        if (event.player instanceof EntityPlayerMP) {
            String playerName = event.player.getName();
            WebHookSender.sendMessage(playerName, "<:moins:1368294154907685025> **| " + playerName + " a quitté le serveur**");
        }
    }
}
