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