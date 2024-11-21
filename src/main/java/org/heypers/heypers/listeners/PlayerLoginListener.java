package org.heypers.heypers.listeners;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import org.heypers.heypers.data.PlayerAuthManager;

public class PlayerLoginListener {

    public static void registerListeners() {
        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
            ServerPlayerEntity player = handler.getPlayer();
            if (!PlayerAuthManager.isPlayerLoggedIn(player.getUuid())) {
                player.sendMessage(Text.literal("Вы должны войти в систему с помощью /login!"), false);
                // что-то сделать тут надо емае
            }
        });

        ServerLifecycleEvents.SERVER_STOPPED.register(server -> {
            // не ебу
        });
    }
}
