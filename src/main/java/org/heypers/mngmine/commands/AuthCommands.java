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
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import org.heypers.mngmine.data.PlayerAuthManager;
import org.heypers.mngmine.handlers.PlayerAuthHandler;

import java.util.UUID;

public class AuthCommands {

    public static void registerCommands(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess registryAccess, RegistrationEnvironment environment) {
        dispatcher.register(literal("reg")
                .then(argument("password", StringArgumentType.string())
                        .executes(context -> registerPlayer(context, StringArgumentType.getString(context, "password")))));

        dispatcher.register(literal("login")
                .then(argument("password", StringArgumentType.string())
                        .executes(context -> loginPlayer(context, StringArgumentType.getString(context, "password")))));
    }

    private static int registerPlayer(CommandContext<ServerCommandSource> context, String password) {
        ServerPlayerEntity player = context.getSource().getPlayer();

        if (player == null) {
            context.getSource().sendFeedback(() -> Text.literal("Команда недоступна для консоли."), false);
            return 0;
        }

        boolean registered = PlayerAuthManager.registerPlayer(player.getUuid(), password);
        if (registered) {
            context.getSource().sendFeedback(() -> Text.literal("Вы успешно зарегистрированы!"), false);
        } else {
            context.getSource().sendFeedback(() -> Text.literal("Вы уже зарегистрированы."), false);
        }

        return 1;
    }

    private static int loginPlayer(CommandContext<ServerCommandSource> context, String password) {
        ServerPlayerEntity player = context.getSource().getPlayer();
        UUID uuid = player.getUuid();

        if (PlayerAuthManager.loginPlayer(uuid, password) || PlayerAuthManager.isSessionActive(uuid)) {
            PlayerAuthHandler.authorizePlayer(uuid);
            PlayerAuthManager.startSession(uuid);
            context.getSource().sendFeedback(() -> Text.literal("Вы успешно вошли в систему!"), false);
            return 1;
        }

        context.getSource().sendFeedback(() -> Text.literal("Неправильный пароль или вы не зарегистрированы."), false);
        return 0;
    }

}

