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

package org.heypers.mngmine.webhooks;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;

public class DiscordWebhook {
    private static final Logger log = LoggerFactory.getLogger(DiscordWebhook.class);

    public static void sendStartMessage(String webhookUrl) {
        sendMessage(webhookUrl, "Сервер запустился ебать!");
    }

    public static void sendLogMessage(String webhookUrl, String logMessage) {
        sendMessage(webhookUrl, logMessage);
    }

    private static void sendMessage(String webhookUrl, String content) {
        HttpURLConnection connection = null;

        try {
            URI uri = new URI(webhookUrl);
            connection = (HttpURLConnection) uri.toURL().openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "application/json");

            String jsonPayload = "{\"content\":\"" + content + "\"}";

            try (OutputStream os = connection.getOutputStream()) {
                os.write(jsonPayload.getBytes());
                os.flush();
            }

            int responseCode = connection.getResponseCode();
            if (responseCode != HttpURLConnection.HTTP_NO_CONTENT) {
                log.error("Ошибка отправки сообщения в Discord: код ответа {}", responseCode);
            }
        } catch (Exception e) {
            log.error("Ошибка при отправке сообщения в Discord: ", e);
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }
}