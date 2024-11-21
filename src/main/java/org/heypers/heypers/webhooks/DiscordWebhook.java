package org.heypers.heypers.webhooks;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;


public class DiscordWebhook {
    private static final String START_WEBHOOK_URL = "None"; // ч
    private static final String ADMIN_WEBHOOK_URL = "None";
    private static final Logger log = LoggerFactory.getLogger(DiscordWebhook.class);

    public static void sendStartMessage() {
        sendMessage(START_WEBHOOK_URL, "Сервер запустился ебать!");
    }


    public static void sendLogMessage(String log) {
        sendMessage(ADMIN_WEBHOOK_URL, log);
    }

    private static void sendMessage(String webhookUrl, String content) {
        try {
            var url = new URI(webhookUrl).toURL();
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "application/json");
            String jsonPayload = "{\"content\":\"" + content + "\"}";

            try (OutputStream os = connection.getOutputStream()) {
                os.write(jsonPayload.getBytes());
            }

            connection.getInputStream();
        } catch (Exception e) {
            log.error("e: ", e);
        }
    }
}
