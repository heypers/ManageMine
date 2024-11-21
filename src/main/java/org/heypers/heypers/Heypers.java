package org.heypers.heypers;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import org.heypers.heypers.commands.AuthCommands;
import org.heypers.heypers.webhooks.DiscordWebhook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Heypers implements ModInitializer {
    public static final String MOD_ID = "heypers";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        CommandRegistrationCallback.EVENT.register(AuthCommands::registerCommands);

        LOGGER.info("Heypers мод успешно загружен!");
        DiscordWebhook.sendStartMessage();
    }
}
