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

package org.heypers.mngmine.commands;

import static net.minecraft.server.command.CommandManager.*;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

public class ServerStatusCommands {

    public static void registerCommands(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess registryAccess, RegistrationEnvironment environment) {
        dispatcher.register(literal("mngmine")
                .then(literal("server")
                        .executes(ServerStatusCommands::showServerStatus)));

        dispatcher.register(literal("server")
                .executes(ServerStatusCommands::showServerStatus));
    }

    private static int showServerStatus(CommandContext<ServerCommandSource> context) {
        ServerCommandSource source = context.getSource();

        int onlinePlayers = source.getWorld().getPlayers().size();
        String serverStatus = "Сервер работает";

        source.sendFeedback(() -> Text.literal("Статус сервера: " + serverStatus + ", Игроков онлайн: " + onlinePlayers), false);

        return 1;
    }
}