package com.sim.discordchat;



import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class WebHookSender {

    // Cette méthode récupère le webhook de la config


    public static void sendMessage(String playerName, String message) {
        try {
            // Récupère le webhook depuis la configuration
            String webhookUrl = ConfigManager.webhook;
            if (webhookUrl.isEmpty()) {
                System.err.println("Aucun webhook défini dans la configuration.");
                return;
            }

            URL url = new URL(webhookUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("User-Agent", "Mozilla/5.0");
            connection.setDoOutput(true);

            // Génère l'URL de l'avatar avec un paramètre de cache-buster
            String avatarUrl = "https://minotar.net/avatar/" + playerName + "?rand=" + System.currentTimeMillis();

            // Crée le payload JSON
            String sanitizedMessage = message.replace("\n", "\\n").replace("\"", "\\\"");
            String sanitizedPlayerName = playerName.replace("\n", "").replace("\"", "\\\"");
            String jsonPayload = String.format(
                    "{\"content\": \"%s\", \"username\": \"%s\", \"avatar_url\": \"%s\"}",
                    sanitizedMessage,
                    sanitizedPlayerName,
                    avatarUrl
            );

            // Log pour debug
            //  System.out.println("Payload : " + jsonPayload);
            // System.out.println("Avatar URL utilisé : " + avatarUrl);

            // Envoie la requête
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonPayload.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            int responseCode = connection.getResponseCode();
            if (responseCode != 204) {
                System.err.println("Échec de l'envoi du message au Webhook. Code : " + responseCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}