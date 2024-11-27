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

        if (player == null) {
            context.getSource().sendFeedback(() -> Text.literal("Команда недоступна для консоли."), false);
            return 0;
        }

        boolean loggedIn = PlayerAuthManager.loginPlayer(player.getUuid(), password);
        if (loggedIn) {
            context.getSource().sendFeedback(() -> Text.literal("Вы успешно вошли в систему!"), false);
        } else {
            context.getSource().sendFeedback(() -> Text.literal("Неправильный пароль или вы не зарегистрированы."), false);
        }

        return 1;
    }

}

