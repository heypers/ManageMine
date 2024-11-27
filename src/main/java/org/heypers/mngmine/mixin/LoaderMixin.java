/*
 * Copyright (c) 2024. Heypers Project, mr_fortuna, Heypers Team.
 *
 * Данный файл является частью интеллектуальной собственности проекта Heypers Project.
 *
 * Использование, модификация и распространение разрешены только в соответствии с официальной лицензией проекта.
 *
 * Любое копирование, изменение или распространение без явного письменного согласия правообладателей запрещено.
 * Обязательно указывать ссылку на официальные ресурсы Heypers Project при любом использовании или распространении информации.
 *
 * Для получения дополнительной информации о лицензии и условиях использования, посетите официальный сайт Проекта или свяжитесь с правообладателями.
 *
 * Официальный ресурс: https://heypers.pythonanywhere.com/license
 */

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