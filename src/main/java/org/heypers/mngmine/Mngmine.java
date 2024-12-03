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

package org.heypers.mngmine;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import org.heypers.mngmine.commands.AuthCommands;
import org.heypers.mngmine.commands.ServerStatusCommands;
import org.heypers.mngmine.handlers.PlayerAuthHandler;
import org.heypers.mngmine.webhooks.DiscordWebhook;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Properties;

public class Mngmine implements ModInitializer {
    public static final String MOD_ID = "mngmine";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static String startWebhookUrl;
    public static String adminWebhookUrl;

    @Override
    public void onInitialize() {
        loadConfig();
        PlayerAuthHandler.registerHandlers();

        CommandRegistrationCallback.EVENT.register(AuthCommands::registerCommands);
        CommandRegistrationCallback.EVENT.register(ServerStatusCommands::registerCommands);

        LOGGER.info("[mngmine] мод успешно загружен!");
        DiscordWebhook.sendStartMessage(startWebhookUrl);
    }

    private void loadConfig() {
        Properties properties = new Properties();
        File configFile = new File("config/mngmine/config.properties");

        if (!configFile.exists()) {
            LOGGER.warn("Конфигурационный файл отсутствует, создается новый с настройками по умолчанию.");
            generateDefaultConfig(properties);
            return;
        }

        try (InputStream input = new FileInputStream(configFile)) {
            properties.load(input);
            startWebhookUrl = properties.getProperty("START_WEBHOOK_URL");
            adminWebhookUrl = properties.getProperty("ADMIN_WEBHOOK_URL");

            LOGGER.info("Конфигурация загружена");
        } catch (IOException ex) {
            LOGGER.error("Ошибка при загрузке конфигурации", ex);
        }
    }


    private void generateDefaultConfig(Properties properties) {
        properties.setProperty("START_WEBHOOK_URL", "http://example.com/start");
        properties.setProperty("ADMIN_WEBHOOK_URL", "http://example.com/admin");

        File configDir = new File("config/mngmine");
        if (!configDir.exists()) {
            if (configDir.mkdirs()) {
                LOGGER.info("Директория конфигурации создана: {}", configDir.getPath());
            } else {
                LOGGER.error("Не удалось создать директорию конфигурации.");
                return;
            }
        }

        File configFile = new File(configDir, "config.properties");
        try (FileWriter writer = new FileWriter(configFile)) {
            properties.store(writer, "Mngmine Configuration File");
            LOGGER.info("Файл конфигурации создан: {}", configFile.getPath());
        } catch (IOException ex) {
            LOGGER.error("Ошибка при создании файла конфигурации", ex);
        }
    }
}