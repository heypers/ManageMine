package org.heypers.mngmine.mixin;

import net.minecraft.server.MinecraftServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.heypers.mngmine.webhooks.DiscordWebhook;

import static org.heypers.mngmine.Mngmine.adminWebhookUrl;

@Mixin(MinecraftServer.class)
public class LoaderMixin {
    @Inject(at = @At("HEAD"), method = "loadWorld")
    private void init(CallbackInfo info) {
        DiscordWebhook.sendLogMessage(adminWebhookUrl, "Мир загружен");
    }
}