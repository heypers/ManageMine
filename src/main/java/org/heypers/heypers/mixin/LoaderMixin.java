package org.heypers.heypers.mixin;

import net.minecraft.server.MinecraftServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.heypers.heypers.webhooks.DiscordWebhook;

@Mixin(MinecraftServer.class)
public class LoaderMixin {
    @Inject(at = @At("HEAD"), method = "loadWorld")
    private void init(CallbackInfo info) {
        DiscordWebhook.sendLogMessage("Мир загружен");
    }
}