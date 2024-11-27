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
            if (responseCode != HttpURLConnection.HTTP_OK) {
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